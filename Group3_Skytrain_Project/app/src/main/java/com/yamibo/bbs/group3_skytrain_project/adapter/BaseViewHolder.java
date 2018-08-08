package com.yamibo.bbs.group3_skytrain_project.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.lang.reflect.Type;

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder{
    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bind(T object);
}
