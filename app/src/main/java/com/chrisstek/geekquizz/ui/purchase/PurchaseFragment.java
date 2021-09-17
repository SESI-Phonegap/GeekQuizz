package com.chrisstek.geekquizz.ui.purchase;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chrisstek.geekquizz.GeekQuizzApplication;
import com.chrisstek.geekquizz.R;
import com.chrisstek.geekquizz.databinding.FragmentPurchaseBinding;
import com.chrisstek.geekquizz.ui.PurchaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class PurchaseFragment extends Fragment {

    private static final String TAG = PurchaseFragment.class.getSimpleName();
    private PurchaseViewModel purchaseViewModel;
    private FragmentPurchaseBinding binding;
    private final List<PurchaseAdapter.Item> inventoryList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_purchase, container, false);
        binding.setLifecycleOwner(this);
        makeInventoryList();
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PurchaseViewModel.PurchaseViewModelFactory purchaseViewModelFactory =
                new PurchaseViewModel.PurchaseViewModelFactory(((GeekQuizzApplication) getActivity().getApplication()).appContainer.purchaseRepository);
        purchaseViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(PurchaseViewModel.class);
        binding.setMpvm(purchaseViewModel);
        binding.list.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.list.setAdapter(new PurchaseAdapter(inventoryList, purchaseViewModel, this));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * While this list is hard-coded here, it could just as easily come from a server, allowing
     * you to add new SKUs to your app without having to update your app.
     */
    void makeInventoryList() {
        inventoryList.add(new PurchaseAdapter.Item(getString(R.string.header_gemas), PurchaseAdapter.VIEW_TYPE_HEADER));
        inventoryList.add(new PurchaseAdapter.Item(PurchaseRepository.GEMS_5K, PurchaseAdapter.VIEW_TYPE_ITEM));
        inventoryList.add(new PurchaseAdapter.Item(PurchaseRepository.GEMS_10K, PurchaseAdapter.VIEW_TYPE_ITEM));
        inventoryList.add(new PurchaseAdapter.Item(PurchaseRepository.GEMS_20K, PurchaseAdapter.VIEW_TYPE_ITEM));
    }

    public void purchaseGems(String sku){
        purchaseViewModel.purchaseGems(sku);
    }

    public LiveData<CharSequence> skuTitle(final @NonNull String sku) {
        PurchaseViewModel.SkuDetails skuDetails = purchaseViewModel.getSkuDetails(sku);
        final LiveData<String> skuTitleLiveData = skuDetails.title;
        final LiveData<Boolean> isPurchasedLiveData = purchaseViewModel.isPurchased(sku);
        final MediatorLiveData<CharSequence> result = new MediatorLiveData<>();
        result.addSource(skuTitleLiveData, title -> combineTitleSkuAndIsPurchasedData(result, skuTitleLiveData, isPurchasedLiveData, sku));
        result.addSource(isPurchasedLiveData, isPurchased -> combineTitleSkuAndIsPurchasedData(result, skuTitleLiveData, isPurchasedLiveData, sku));
        return result;
    }

    private void combineTitleSkuAndIsPurchasedData(
            MediatorLiveData<CharSequence> result,
            LiveData<String> skuTitleLiveData,
            LiveData<Boolean> isPurchasedLiveData,
            @NonNull String sku
    ) {
        String skuTitle = skuTitleLiveData.getValue();
        Boolean isPurchased = isPurchasedLiveData.getValue();
        // don't emit until we have all of our data
        if (null == skuTitle || null == isPurchased) {
            return;
        }
        if ( isPurchased && ( sku.equals(PurchaseRepository.GEMS_10K) ||
                sku.equals(PurchaseRepository.GEMS_5K) || sku.equals(PurchaseRepository.GEMS_20K))) {

            SpannableString titleSpannable = new SpannableString(skuTitle);
            titleSpannable.setSpan(new URLSpan(String.format(
                    sku,
                    getContext().getPackageName())), 0, titleSpannable.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            result.setValue(titleSpannable);
        } else {
            // empty SpannableString needs to be used to clear spannables
            SpannableString titleSpannable = new SpannableString(skuTitle);
            result.setValue(titleSpannable);
        }
    }
}