package com.example.healthcare.applicationlayer;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthcare.R;
import com.example.healthcare.entity.Account;

import java.util.List;

public class MyOwnAdapter extends RecyclerView.Adapter<MyOwnAdapter.MyViewHolder> {

    private RecyclerViewClickInterface recyclerViewClick_interface;
    Context context;
    List<Account> accounts;
    int[] doctorImg;



    public MyOwnAdapter(Context context, List<Account> accounts ,RecyclerViewClickInterface recyclerViewClick_interface ) {
        this.recyclerViewClick_interface = recyclerViewClick_interface;
        this.context = context;
        this.accounts = accounts;
    }


    @NonNull
    @Override
    public MyOwnAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.created_account,parent,false);

        return new MyOwnAdapter.MyViewHolder(view,recyclerViewClick_interface);
    }




    public void onBindViewHolder(@NonNull MyOwnAdapter.MyViewHolder holder, int position) {
        holder.email.setText(accounts.get(position).getEmail());
        holder.role.setText(accounts.get(position).getRole());
        holder.accountPosition.setText(String.valueOf(position + 1));

    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView email, role, accountPosition;

        public MyViewHolder(@NonNull View itemView, RecyclerViewClickInterface recyclerViewClick_interface) {
            super(itemView);
            email = itemView.findViewById(R.id.created_account_email);
            role = itemView.findViewById(R.id.created_account_role);
            accountPosition = itemView.findViewById(R.id.account_position);


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

