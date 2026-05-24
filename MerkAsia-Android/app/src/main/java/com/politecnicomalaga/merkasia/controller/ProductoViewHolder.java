package com.politecnicomalaga.merkasia.controller;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.politecnicomalaga.merkasia.R;
import com.politecnicomalaga.merkasia.model.Producto;

public class ProductoViewHolder extends RecyclerView.ViewHolder {

    private final TextView tvDescription;
    private final TextView tvPrice;

    public ProductoViewHolder(@NonNull View itemView) {
        super(itemView);
        tvDescription = itemView.findViewById(R.id.tvDescription);
        tvPrice = itemView.findViewById(R.id.tvPrice);
    }

    // ViewHolder — toda la lógica de presentación aquí
    public void bind(Producto p) {
        tvDescription.setText(p.getIdProducto() + " " + p.getDescripcion());
        tvPrice.setText(String.valueOf(p.getPrecioUnitario()));
    }
}
