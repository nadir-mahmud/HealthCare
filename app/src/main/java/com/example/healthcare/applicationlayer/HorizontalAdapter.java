package com.example.healthcare.applicationlayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthcare.R;
import com.example.healthcare.entity.Doctor;
import com.example.healthcare.entity.DoctorInfoModel;

import java.security.GeneralSecurityException;
import java.util.ArrayList;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {
    private final RecyclerViewClickInterface2 recyclerViewClick_interface;
    Context context;
    ArrayList<DoctorInfoModel> doctorInfoModels;
    int[] doctorImg;

    public  HorizontalAdapter(Context context, ArrayList<DoctorInfoModel> doctorInfoModels,int[] doctorimg,RecyclerViewClickInterface2 recyclerViewClick_interface){
        this.context=context;
        this.doctorInfoModels=doctorInfoModels;
        this.recyclerViewClick_interface=recyclerViewClick_interface;
        doctorImg = doctorimg;


    }




    @NonNull
    @Override
    public HorizontalAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.activity_sample_doctor_card_view,parent,false);
        return new HorizontalAdapter.MyViewHolder(view, recyclerViewClick_interface);
    }

    public void onBindViewHolder(@NonNull HorizontalAdapter.MyViewHolder holder, int position) {
        Encryption encryption = new Encryption();
        try {
            holder.name.setText(doctorInfoModels.get(position).getName());
            holder.service_area.setText(doctorInfoModels.get(position).getDepartment());
            String rating = encryption.decryptData(doctorInfoModels.get(position).getRating());
            holder.ratingvalue.setText(rating);
            //Toast.makeText(context, rating,Toast.LENGTH_SHORT).show();
            String image = encryption.decryptData(doctorInfoModels.get(position).getImage());
            holder.docimg.setImageResource(Integer.parseInt(image));
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return doctorInfoModels.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView docimg;
        TextView name,service_area,ratingvalue;

        public MyViewHolder(@NonNull View itemView, RecyclerViewClickInterface2 recyclerViewClick_interface) {
            super(itemView);
            name=itemView.findViewById(R.id.hname_id);
            service_area=itemView.findViewById(R.id.h_servicearea);
            ratingvalue=itemView.findViewById(R.id.rating_sample);
            docimg=itemView.findViewById(R.id.himage_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewClick_interface !=null)
                    {
                        int pos=getAdapterPosition();
                        if (pos !=RecyclerView.NO_POSITION)
                        {

                            recyclerViewClick_interface.onItemClick(pos,new Doctor(),5);
                        }
                    }
                }
            });

        }
    }
}
