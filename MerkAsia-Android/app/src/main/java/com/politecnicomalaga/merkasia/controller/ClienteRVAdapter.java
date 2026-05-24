package com.politecnicomalaga.merkasia.controller;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.politecnicomalaga.merkasia.R;
import com.politecnicomalaga.merkasia.model.Cliente;

import java.util.List;


public class ClienteRVAdapter extends RecyclerView.Adapter<ClienteViewHolder> {

    private final List<Cliente> data;

    public ClienteRVAdapter(List<Cliente> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ClienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ClienteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_client,
            parent, false));
    }

    // Adapter — solo entrega el dato
    @Override
    public void onBindViewHolder(@NonNull ClienteViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

}
