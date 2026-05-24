package com.politecnicomalaga.merkasia.controller;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.politecnicomalaga.merkasia.R;
import com.politecnicomalaga.merkasia.model.Cliente;

public class ClienteViewHolder extends RecyclerView.ViewHolder {

    private final TextView tvName;
    private final TextView tvDni;
    private final TextView tvEmail;

    public ClienteViewHolder(@NonNull View itemView) {
        super(itemView);
        tvName = itemView.findViewById(R.id.tvName);
        tvDni = itemView.findViewById(R.id.tvDni);
        tvEmail = itemView.findViewById(R.id.tvEmail);
    }

    // ViewHolder — toda la lógica de presentación aquí
    public void bind(Cliente cliente) {
        tvName.setText(cliente.getNombre() + " " + cliente.getApellidos());
        tvDni.setText("DNI: " + cliente.getDni());
        tvEmail.setText(cliente.getEmail());
    }
}
