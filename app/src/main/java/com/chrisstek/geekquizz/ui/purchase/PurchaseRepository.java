package com.chrisstek.geekquizz.ui.purchase;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.chrisstek.geekquizz.R;
import com.chrisstek.geekquizz.billing.BillingDataSource;
import com.chrisstek.geekquizz.ui.SingleMediatorLiveEvent;

import java.util.List;

public class PurchaseRepository {
    public static final String GEMS_5K = "sku_small_gems";
    public static final String GEMS_10K = "sku_med_gems";
    public static final String GEMS_20K = "sku_large_gems";

    public static final String[] INAPP_SKUS = new String[]{GEMS_5K, GEMS_10K, GEMS_20K};

    final BillingDataSource billingDataSource;
    final SingleMediatorLiveEvent<Integer> gameMessages;
    final SingleMediatorLiveEvent<Integer> allMessages = new SingleMediatorLiveEvent<>();

    public PurchaseRepository(BillingDataSource billingDataSource){
        this.billingDataSource = billingDataSource;

        gameMessages = new SingleMediatorLiveEvent<>();
        setupMessagesSingleMediatorLiveEvent();

        // Since both are tied to application lifecycle
        billingDataSource.observeConsumedPurchases().observeForever(skuList -> {
            for ( String sku: skuList ) {
                switch (sku){
                    case GEMS_5K:
                        //Call update Gemas.
                        break;

                    case GEMS_10K:
                        break;

                    case GEMS_20K:
                        break;
                }
            }
        });
    }

    /**
     * Sets up the event that we can use to send messages up to the UI to be used in Snackbars. This
     * SingleMediatorLiveEvent observes changes in SingleLiveEvents coming from the rest of the game
     * and combines them into a single source with new purchase events from the BillingDataSource.
     * Since the billing data source doesn't know about our SKUs, it also transforms the known SKU
     * strings into useful String messages.
     */
    void setupMessagesSingleMediatorLiveEvent() {
        final LiveData<List<String>> billingMessages = billingDataSource.observeNewPurchases();
        allMessages.addSource(gameMessages, allMessages::setValue);
        allMessages.addSource(billingMessages,
                stringList -> {
                    // TODO: Handle multi-line purchases better
                    for (String s: stringList) {
                        switch (s) {
                            case GEMS_5K:
                                allMessages.setValue(R.string.acquire_5k_gems);
                                break;
                            case GEMS_10K:
                                allMessages.setValue(R.string.acquire_10k_gems);
                                break;
                            case GEMS_20K:
                                // this makes sure that upgraded and downgraded subscriptions are
                                // reflected correctly in the app UI
                                //billingDataSource.refreshPurchasesAsync();
                                allMessages.setValue(R.string.acquire_20k_gems);
                                break;
                        }
                    }
                });
    }

    public void buyWallpaperSubtractGems(int wallpaperCost, Context context){
        final LiveData<Integer> currentAmountGems = getCurrentAmountGems();
        currentAmountGems.observeForever(new Observer<Integer>() {
            @Override
            public void onChanged(Integer currentGems) {
                if (currentGems < wallpaperCost){
                    Toast.makeText(context, "No tienes Gemas suficientes", Toast.LENGTH_LONG).show();
                } else {
                    //Call Substract Gems Service
                }
                currentAmountGems.removeObserver(this);
            }
        });
    }
    public LiveData<Integer> getCurrentAmountGems(){
        final MediatorLiveData<Integer> result = new MediatorLiveData<>();
        return result;
    }

    /**
     * Return LiveData that indicates whether the sku is currently purchased.
     *
     * @param sku the SKU to get and observe the value for
     * @return LiveData that returns true if the sku is purchased.
     */
    public LiveData<Boolean> isPurchased(String sku) {
        return billingDataSource.isPurchased(sku);
    }

    public final LifecycleObserver getBillingLifecycleObserver() {
        return billingDataSource;
    }

    // There's lots of information in SkuDetails, but our app only needs a few things, since our
    // goods never go on sale, have introductory pricing, etc.
    public final LiveData<String> getSkuTitle(String sku) {
        return billingDataSource.getSkuTitle(sku);
    }

    public final LiveData<String> getSkuPrice(String sku) {
        return billingDataSource.getSkuPrice(sku);
    }

    public final LiveData<String> getSkuDescription(String sku) {
        return billingDataSource.getSkuDescription(sku);
    }

    public final LiveData<Integer> getMessages() {
        return allMessages;
    }

    public final void sendMessage(int resId) {
        gameMessages.postValue(resId);
    }

    public final LiveData<Boolean> getBillingFlowInProcess() {
        return billingDataSource.getBillingFlowInProcess();
    }

    public final void buyGems(String sku){
        billingDataSource.consumeInappPurchase(sku);
    }

}
