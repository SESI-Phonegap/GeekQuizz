package com.chrisstek.geekquizz;

import android.app.Application;

import com.chrisstek.geekquizz.billing.BillingDataSource;
import com.chrisstek.geekquizz.ui.purchase.PurchaseRepository;

public class GeekQuizzApplication extends Application {
    public AppContainer appContainer;

    public class AppContainer {
        final BillingDataSource billingDataSource = BillingDataSource.getInstance(GeekQuizzApplication.this, PurchaseRepository.INAPP_SKUS, null, null);
        final public PurchaseRepository purchaseRepository = new PurchaseRepository(billingDataSource);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContainer = new AppContainer();
    }
}
