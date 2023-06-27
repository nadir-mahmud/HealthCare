package com.example.healthcare.applicationlayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthcare.R;
import com.example.healthcare.entity.AppointedPatient;

import java.util.List;

public class HistoryAdapter2 extends RecyclerView.Adapter<HistoryAdapter2.MyViewHolder> {

    private RecyclerViewClickInterface recyclerViewClick_interface;
    Context context;
    List<AppointedPatient> appointedPatients;

    public HistoryAdapter2(Context context, List<AppointedPatient> appointedPatients ,RecyclerViewClickInterface recyclerViewClick_interface ) {
        this.recyclerViewClick_interface = recyclerViewClick_interface;
        this.context = context;
        this.appointedPatients = appointedPatients;
    }

    @NonNull
    @Override
    public HistoryAdapter2.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.history_view2,parent,false);

        return new HistoryAdapter2.MyViewHolder(view,recyclerViewClick_interface);
    }




    public void onBindViewHolder(@NonNull HistoryAdapter2.MyViewHolder holder, int position) {
        holder.doctorName.setText(appointedPatients.get(position).getDoctorName());
        holder.day.setText(appointedPatients.get(position).getDay());
        holder.doctorDegree.setText(appointedPatients.get(position).getDoctorDegree());
        holder.doctorDepartment.setText(appointedPatients.get(position).getDoctorDepartment());
        holder.patientName.setText("Patient Name: " + appointedPatients.get(position).getPatientName());
        holder.serial.setText("Serial: " + appointedPatients.get(position).getSerial());
    }

    @Override
    public int getItemCount() {
        return appointedPatients.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView patientName, doctorName, doctorDegree, doctorDepartment, day, serial;

        public MyViewHolder(@NonNull View itemView, RecyclerViewClickInterface recyclerViewClick_interface) {
            super(itemView);

            serial = itemView.findViewById(R.id.serial_history);
            patientName = itemView.findViewById(R.id.patient_name_hsitory);
            day = itemView.findViewById(R.id.day_history);
            doctorName = itemView.findViewById(R.id.doctor_name_history);
            doctorDegree = itemView.findViewById(R.id.doctor_degree_history);
            doctorDepartment = itemView.findViewById(R.id.doctor_department_history);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewClick_interface !=null)
                    {
                        int pos=getAdapterPosition();
                        if (pos !=RecyclerView.NO_POSITION)
                        {
                            recyclerViewClick_interface.onItemClick(pos);
                        }
                    }
                }
            });

        }
    }


}
