package com.rbysoft.myovertimebd;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rbysoft.myovertimebd.Model.NotificationModel;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.viewHolder> {

    ArrayList<NotificationModel> mynotifications;

    public NotificationAdapter(ArrayList<NotificationModel> mynotifications) {
        this.mynotifications = mynotifications;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notificationitem,parent,false);
        return new viewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        NotificationModel temp= mynotifications.get(position);

        holder.title.setText(temp.getTitle());
        holder.details.setText(temp.getDetails());



    }

    @Override
    public int getItemCount() {

        if (mynotifications.isEmpty()){
            return 0;
        }
        else{
            return mynotifications.size();
        }

    }

    public  class  viewHolder extends RecyclerView.ViewHolder{
         TextView title,details;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.textView7);
            details=itemView.findViewById(R.id.textview8);


        }
    }
}
