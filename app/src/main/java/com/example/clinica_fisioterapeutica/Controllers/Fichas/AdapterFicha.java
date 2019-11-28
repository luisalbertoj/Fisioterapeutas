package com.example.clinica_fisioterapeutica.Controllers.Fichas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clinica_fisioterapeutica.Models.Persona;
import com.example.clinica_fisioterapeutica.R;

public class AdapterFicha extends RecyclerView.Adapter<AdapterFicha.AdapterPersonaHolder> implements View.OnClickListener {

    private View.OnClickListener listener;
    Persona [] lista;


    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public AdapterFicha(Persona[] l){
        super();
        lista=l;
    }
    @NonNull
    @Override
    public AdapterPersonaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        
        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista,parent,false);
        v.setOnClickListener(listener);
        return new AdapterPersonaHolder(v);
                
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPersonaHolder holder, int position) {
        holder.tvNombre.setText(lista[position].getNombre());

        holder.tvIdPersona.setText(lista[position].getIdPersona().toString());
    }

    @Override
    public int getItemCount() {
        return lista.length;
    }

    @Override
    public void onClick(View view) {
        listener.onClick(view);
    }

    public static class AdapterPersonaHolder extends RecyclerView.ViewHolder{
        TextView tvIdPersona;
        TextView tvNombre;
        public AdapterPersonaHolder(View view){
            super(view);
            tvNombre = view.findViewById(R.id.txtIdPersonaItem);
            tvIdPersona = view.findViewById(R.id.txtNombrePersonaItem);
        }
    }

}
