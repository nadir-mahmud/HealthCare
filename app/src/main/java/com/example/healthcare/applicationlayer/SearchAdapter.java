package com.example.healthcare.applicationlayer;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthcare.R;
import com.example.healthcare.entity.Account;
import com.example.healthcare.entity.Doctor;
import com.example.healthcare.entity.DoctorInfoModel;
import com.example.healthcare.presentationlayer.DoctorDetailsPL;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.security.GeneralSecurityException;
import java.util.List;

public class SearchAdapter extends FirestoreRecyclerAdapter<Doctor,SearchAdapter.MyViewHolder> {

    private RecyclerViewClickInterface2 recyclerViewClick_interface;
    Context context;
    String nameString;

    public SearchAdapter(Context context, @NonNull FirestoreRecyclerOptions<Doctor> doctors , RecyclerViewClickInterface2 recyclerViewClick_interface ) {
        super(doctors);
        this.recyclerViewClick_interface = recyclerViewClick_interface;
        this.context = context;


    }


    @NonNull
    @Override
    public SearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.doctor_cardview,parent,false);

        return new SearchAdapter.MyViewHolder(view,recyclerViewClick_interface);
    }




    public void onBindViewHolder(@NonNull SearchAdapter.MyViewHolder holder, int position,@NonNull Doctor doctor) {
        Encryption encryption = new Encryption();
        try {

            holder.name.setText(doctor.getName());
            holder.degree.setText(encryption.decryptData(doctor.getDegree()));
            holder.experience.setText(encryption.decryptData(doctor.getExperience()));
            holder.department.setText(doctor.getDepartment());
            String rating = encryption.decryptData(doctor.getRating());
            holder.ratingBar.setRating(Float.parseFloat(rating));
            holder.doctor.setImageResource(Integer.parseInt(encryption.decryptData(doctor.getImage())));
            holder.doctor.setTag(Integer.parseInt(encryption.decryptData(doctor.getImage())));
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }



    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name, degree, department, experience;
        RatingBar ratingBar;
        ImageView doctor;

        public MyViewHolder(@NonNull View itemView, RecyclerViewClickInterface2 recyclerViewClick_interface) {
            super(itemView);

            name = itemView.findViewById(R.id.doctor_name_id);
            degree = itemView.findViewById(R.id.doctor_degree_id);
            department = itemView.findViewById(R.id.specialist);
            experience = itemView.findViewById(R.id.experience_id);
            ratingBar = itemView.findViewById(R.id.ratingbar_id);
            doctor = itemView.findViewById(R.id.doctor_profile_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewClick_interface !=null)
                    {
                        int pos=getAdapterPosition();
                        if (pos !=RecyclerView.NO_POSITION)
                        {
                            Doctor doctor1 = new Doctor();
                            doctor1.setName(name.getText().toString());
                            doctor1.setDegree(degree.getText().toString());
                            doctor1.setDepartment(department.getText().toString());
                            doctor1.setExperience(experience.getText().toString());
                            doctor1.setRating(String.valueOf(ratingBar.getRating()));


                            recyclerViewClick_interface.onItemClick(pos,doctor1,(int) doctor.getTag());
                        }
                    }
                }
            });

        }
    }


}
