package com.example.clinica_fisioterapeutica.Controllers.Turnos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clinica_fisioterapeutica.Models.Reserva;
import com.example.clinica_fisioterapeutica.R;

public class AdapterTurno extends RecyclerView.Adapter<AdapterTurno.AdapterTurnoHolder> implements View.OnClickListener{

    private View.OnClickListener listener;
    Reserva[] lista;

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public AdapterTurno(Reserva[] l){
        super();
        lista=l;
    }

    @Override
    public void onClick(View view) {
        listener.onClick(view);
    }

    @NonNull
    @Override
    public AdapterTurno.AdapterTurnoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_turno,parent,false);
        v.setOnClickListener(listener);
        return new AdapterTurno.AdapterTurnoHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTurno.AdapterTurnoHolder holder, int position) {


        holder.tvfechaCadena.setText("---------" + lista[position].getFecha() + "--------");
        holder.tvFisioterapeuta.setText("---------" + lista[position].getIdEmpleado().getNombre() + "--------");
        holder.tvhoraInicioCadena.setText("Hora Inicio: " + lista[position].getHoraInicio());
        holder.tvhoraFinCadena.setText("Hora Fin: " + lista[position].getHoraFin());

    }

    @Override
    public int getItemCount() {
        return lista.length;
    }

    public class AdapterTurnoHolder extends RecyclerView.ViewHolder {
        TextView tvfechaCadena;
        TextView tvhoraInicioCadena;
        TextView tvhoraFinCadena;
        TextView tvFisioterapeuta;

        public AdapterTurnoHolder(@NonNull View itemView) {
            super(itemView);
            tvfechaCadena = itemView.findViewById(R.id.txtfechaCadena);
            tvhoraFinCadena = itemView.findViewById(R.id.txthoraFinCadena);
            tvhoraInicioCadena = itemView.findViewById(R.id.txthoraInicioCadena);
            tvFisioterapeuta = itemView.findViewById(R.id.txtFisioterapeuta);
        }
    }
}
