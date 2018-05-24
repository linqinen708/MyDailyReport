package com.qyd.mydailyreport.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.linqinen.library.utils.LogT;
import com.qyd.mydailyreport.R;


/**
 * Created by 林 on 2017/9/27.
 * adapter的刷新机制采用插入和移除，而不是普通的notifyDataSetChanged
 */

public abstract class BasicBindingAdapter<M, B extends ViewDataBinding> extends RecyclerView.Adapter {
    protected Context context;
    protected ObservableArrayList<M> items;
    protected ListChangedCallback itemsChangeCallback;

    /**判断viewType 是不是footView*/
    private static final int TYPE_FOOT_VIEW = 1;

    public BasicBindingAdapter(Context context) {
        this.context = context;
        this.items = new ObservableArrayList<>();
        this.itemsChangeCallback = new ListChangedCallback();
    }

    public ObservableArrayList<M> getItems() {
        return items;
    }

    public static class BaseBindingViewHolder extends RecyclerView.ViewHolder {
        public BaseBindingViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class FootViewHolder extends RecyclerView.ViewHolder {
        public FootViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**每次让items + 1 多一个footView*/
    @Override
    public int getItemCount() {
        return this.items.size()+1;
    }

    @Override
    public int getItemViewType(int position) {

        if (position + 1 == items.size()+1 /*&& items.get(position) == null*/) {
            return TYPE_FOOT_VIEW;
        }
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOT_VIEW) {
            final View view = LayoutInflater.from(context).inflate(R.layout.adapter_foot_view, parent, false);
            return new FootViewHolder(view);
        } else {
            B binding = DataBindingUtil.inflate(LayoutInflater.from(this.context), this.getLayoutResId(viewType), parent, false);
            return new BaseBindingViewHolder(binding.getRoot());
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder != null && holder instanceof BaseBindingViewHolder) {
            B binding = DataBindingUtil.getBinding(holder.itemView);
            this.onBindItem(binding, this.items.get(position));
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.items.addOnListChangedCallback(itemsChangeCallback);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.items.removeOnListChangedCallback(itemsChangeCallback);
    }

    //region 处理数据集变化
    protected void onChanged(ObservableArrayList<M> newItems) {
        resetItems(newItems);
        notifyDataSetChanged();
    }

    protected void onItemRangeChanged(ObservableArrayList<M> newItems, int positionStart, int itemCount) {
        resetItems(newItems);
        notifyItemRangeChanged(positionStart, itemCount);
    }

    protected void onItemRangeInserted(ObservableArrayList<M> newItems, int positionStart, int itemCount) {
        resetItems(newItems);
        notifyItemRangeInserted(positionStart, itemCount);
    }

    protected void onItemRangeMoved(ObservableArrayList<M> newItems) {
        resetItems(newItems);
        notifyDataSetChanged();
    }

    protected void onItemRangeRemoved(ObservableArrayList<M> newItems, int positionStart, int itemCount) {
        resetItems(newItems);
        notifyItemRangeRemoved(positionStart, itemCount);
    }

    protected void resetItems(ObservableArrayList<M> newItems) {
        this.items = newItems;
    }
    //endregion

    protected abstract
    @LayoutRes
    int getLayoutResId(int viewType);

    protected abstract void onBindItem(B binding, M item);

    private class ListChangedCallback extends ObservableArrayList.OnListChangedCallback<ObservableArrayList<M>> {
        @Override
        public void onChanged(ObservableArrayList<M> newItems) {
            LogT.i("onChanged:");
            BasicBindingAdapter.this.onChanged(newItems);
        }

        @Override
        public void onItemRangeChanged(ObservableArrayList<M> newItems, int i, int i1) {
            LogT.i("onItemRangeChanged:");
            BasicBindingAdapter.this.onItemRangeChanged(newItems, i, i1);
        }

        @Override
        public void onItemRangeInserted(ObservableArrayList<M> newItems, int i, int i1) {
            LogT.i("onItemRangeInserted:");
            BasicBindingAdapter.this.onItemRangeInserted(newItems, i, i1);
        }

        @Override
        public void onItemRangeMoved(ObservableArrayList<M> newItems, int i, int i1, int i2) {
            LogT.i("onItemRangeMoved:");
            BasicBindingAdapter.this.onItemRangeMoved(newItems);
        }

        @Override
        public void onItemRangeRemoved(ObservableArrayList<M> sender, int positionStart, int itemCount) {
            LogT.i("onItemRangeRemoved:");
            BasicBindingAdapter.this.onItemRangeRemoved(sender, positionStart, itemCount);
        }
    }
}
