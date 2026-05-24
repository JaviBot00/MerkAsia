package com.politecnicomalaga.merkasia.controller;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.politecnicomalaga.merkasia.R;
import com.politecnicomalaga.merkasia.model.Producto;

import java.util.List;


public class ProductoRVAdapter extends RecyclerView.Adapter<ProductoViewHolder> {

    private final List<Producto> data;

    public ProductoRVAdapter(List<Producto> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_products,
            parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

}
