package com.chrisstek.geekquizz.ui.purchase;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.chrisstek.geekquizz.R;

import java.util.HashMap;
import java.util.Map;

import static com.chrisstek.geekquizz.ui.purchase.PurchaseRepository.GEMS_10K;
import static com.chrisstek.geekquizz.ui.purchase.PurchaseRepository.GEMS_20K;
import static com.chrisstek.geekquizz.ui.purchase.PurchaseRepository.GEMS_5K;

public class PurchaseViewModel extends ViewModel {
    static final String TAG = PurchaseViewModel.class.getSimpleName();
    static final private Map<String, Integer> skuToResourceIdMap = new HashMap<>();
    private PurchaseRepository repository;

    static {
        skuToResourceIdMap.put(GEMS_5K, R.drawable.gems_costal_small);
        skuToResourceIdMap.put(GEMS_10K, R.drawable.gems_costal_med);
        skuToResourceIdMap.put(GEMS_20K, R.drawable.gems_costal);
    }

    public PurchaseViewModel(@NonNull PurchaseRepository repository) {
        super();
        this.repository = repository;
    }

    public SkuDetails getSkuDetails(String sku) {
        return new SkuDetails(sku, repository);
    }

    public LiveData<Boolean> isPurchased(String sku) {
        return repository.isPurchased(sku);
    }

    public void sendMessage(int message) {
        repository.sendMessage(message);
    }

    public LiveData<Boolean> getBillingFlowInProcess() {
        return repository.getBillingFlowInProcess();
    }

    /**
     * Starts a billing flow for purchasing Gems.
     * @param sku identyfier.
     */
    public void purchaseGems(String sku){
        repository.buyGems(sku);
    }

    static public class SkuDetails {
        final public String sku;
        final public LiveData<String> title;
        final public LiveData<String> description;
        final public LiveData<String> price;
        final public int iconDrawableId;

        SkuDetails(@NonNull String sku, PurchaseRepository repository) {
            this.sku = sku;
            title = repository.getSkuTitle(sku);
            description = repository.getSkuDescription(sku);
            price = repository.getSkuPrice(sku);
            iconDrawableId = skuToResourceIdMap.get(sku);
        }
    }

    public static class PurchaseViewModelFactory implements
            ViewModelProvider.Factory {
        private final PurchaseRepository repository;

        public PurchaseViewModelFactory(PurchaseRepository tdr) {
            repository = tdr;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(PurchaseViewModel.class)) {
                return (T) new PurchaseViewModel(repository);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }

}