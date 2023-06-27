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
import com.example.healthcare.entity.Notification;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private RecyclerViewClickInterface recyclerViewClick_interface;
    Context context;
    List<Notification> notifications;
    int[] doctorImg;



    public NotificationAdapter(Context context, List<Notification> notifications ,RecyclerViewClickInterface recyclerViewClick_interface ) {
        this.recyclerViewClick_interface = recyclerViewClick_interface;
        this.context = context;
        this.notifications = notifications;
    }


    @NonNull
    @Override
    public NotificationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.notificaton_view,parent,false);

        return new NotificationAdapter.MyViewHolder(view,recyclerViewClick_interface);
    }




    public void onBindViewHolder(@NonNull NotificationAdapter.MyViewHolder holder, int position) {
        String name = notifications.get(position).getDoctorName();
        String day = notifications.get(position).getDay();
        holder.notificationContent.setText("You have an appointment with Dr." + name + " on " + day);
        holder.notificationTime.setText(notifications.get(position).getNotificationTime());

    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView notificationContent , notificationTime;

        public MyViewHolder(@NonNull View itemView, RecyclerViewClickInterface recyclerViewClick_interface) {
            super(itemView);
            notificationContent = itemView.findViewById(R.id.notification_content);
            notificationTime = itemView.findViewById(R.id.notification_time);


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
