package com.chrisstek.geekquizz.ui;

import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;

import androidx.databinding.DataBindingUtil;

import com.chrisstek.geekquizz.R;
import com.chrisstek.geekquizz.databinding.SkuDetailsRowBinding;
import com.chrisstek.geekquizz.databinding.SkuDetailsRowHeaderBinding;
import com.chrisstek.geekquizz.ui.purchase.PurchaseFragment;
import com.chrisstek.geekquizz.ui.purchase.PurchaseViewModel;

import java.util.List;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.ViewHolder> {
    static public final int VIEW_TYPE_HEADER = 0;
    static public final int VIEW_TYPE_ITEM = 1;

    private final List<Item> inventoryList;
    private final PurchaseViewModel purchaseViewModel;
    private final PurchaseFragment purchaseFragment;

    public PurchaseAdapter(@NonNull List<Item> inventoryList, @NonNull PurchaseViewModel purchaseViewModel, @NonNull PurchaseFragment purchaseFragment) {
        this.inventoryList = inventoryList;
        this.purchaseFragment = purchaseFragment;
        this.purchaseViewModel = purchaseViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        SkuDetailsRowBinding skuDetailsRowBinding = null;
        SkuDetailsRowHeaderBinding skuDetailsRowHeaderBinding = null;

        switch (viewType) {
            case VIEW_TYPE_HEADER:
                skuDetailsRowHeaderBinding = DataBindingUtil.inflate(layoutInflater, R.layout.sku_details_row_header,parent,false);
                view = skuDetailsRowHeaderBinding.getRoot();
                break;
            default: //SKU ITEM
                skuDetailsRowBinding = DataBindingUtil.inflate(layoutInflater, R.layout.sku_details_row, parent, false);
                view = skuDetailsRowBinding.getRoot();
                break;
        }
        return new ViewHolder(view, viewType, skuDetailsRowHeaderBinding, skuDetailsRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        final SkuDetailsRowBinding itemBinding;
        final SkuDetailsRowHeaderBinding headerBinding;

        public ViewHolder(View v, int viewType, SkuDetailsRowHeaderBinding inventoryHeaderBinding,
                          SkuDetailsRowBinding skuDetailsRowBinding) {
            super(v);
            this.headerBinding = inventoryHeaderBinding;
            this.itemBinding = skuDetailsRowBinding;
        }

        void bind(Item item, PurchaseViewModel purchaseViewModel, PurchaseFragment purchaseFragment) {
            switch (item.viewType) {
                case VIEW_TYPE_HEADER:
                    headerBinding.title.setText(item.getTitleOrSku());
                    headerBinding.title.setMovementMethod(LinkMovementMethod.getInstance());
                    headerBinding.setLifecycleOwner(purchaseFragment);
                    headerBinding.executePendingBindings();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * An item to be displayed in our RecyclerView. Each item contains a single string: either
     * the title of a header or a reference to a SKU, depending on what the type of the view is.
     */
    public static class Item {
        public Item(@NonNull CharSequence titleOrSku, int viewType) {
            this.titleOrSku = titleOrSku;
            this.viewType = viewType;
        }

        public @NonNull
        CharSequence getTitleOrSku() {
            return titleOrSku;
        }

        public int getViewType() {
            return viewType;
        }

        private final @NonNull
        CharSequence titleOrSku;
        private final int viewType;
    }
}
