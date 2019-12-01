package com.example.clinica_fisioterapeutica.Controllers.Fichas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clinica_fisioterapeutica.Models.FichaClinica;
import com.example.clinica_fisioterapeutica.R;

public class AdapterListaFicha extends RecyclerView.Adapter<AdapterListaFicha.AdapterFichaClinica> implements View.OnClickListener {

    private View.OnClickListener listener;
    FichaClinica[] lista;


    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public AdapterListaFicha(FichaClinica[] l){
        super();
        lista=l;
    }
    @NonNull
    @Override
    public AdapterFichaClinica onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        
        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista_ficha,parent,false);
        v.setOnClickListener(listener);
        return new AdapterFichaClinica(v);
                
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFichaClinica holder, int position) {
        holder.tvFisioterapeuta.setText(lista[position].getIdEmpleado().getNombre());
        holder.tvPaciente.setText(lista[position].getIdCliente().getNombre());
        holder.tvObservacion.setText(lista[position].getObservacion());
        try {
            holder.tvFecha.setText(lista[position].getFechaHora()!=null?lista[position].getFechaHora():"");
        } catch (Exception e) {
            holder.tvFecha.setText("No registrado");
        }

    }

    @Override
    public int getItemCount() {
        return lista.length;
    }

    @Override
    public void onClick(View view) {
        listener.onClick(view);
    }

    public static class AdapterFichaClinica extends RecyclerView.ViewHolder{
        TextView tvFisioterapeuta;
        TextView tvPaciente;
        TextView tvObservacion;
        TextView tvFecha;
        public AdapterFichaClinica(View view){
            super(view);
            tvFisioterapeuta = view.findViewById(R.id.txtNombreFisioterapeuta);
            tvPaciente = view.findViewById(R.id.txtNombrePaciente);
            tvObservacion = view.findViewById(R.id.txtObservacion);
            tvFecha = view.findViewById(R.id.txtFecha);
        }
    }

}
