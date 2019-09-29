package com.rbysoft.myovertimebd.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rbysoft.myovertimebd.Model.OverTime;
import com.rbysoft.myovertimebd.R;

import java.util.ArrayList;

public class OvertimeAdapter extends ArrayAdapter<OverTime> {
    ArrayList<OverTime>Data;
    Context context;

    public OvertimeAdapter(Context context,ArrayList<OverTime>Data) {
        super(context, R.layout.row_info,Data);
    }

    public static class ViewHolder{
        TextView tv_Date;
        TextView tv_Regular;
        TextView tv_Day;
        TextView tv_Night;
        TextView tv_Off;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        OverTime overTime= getItem(position);
        ViewHolder holder;
        final View result;

        if (convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_info,parent,false);

            holder.tv_Date = convertView.findViewById(R.id.tvDate);
            holder.tv_Regular = convertView.findViewById(R.id.tvRegular);
            holder.tv_Day = convertView.findViewById(R.id.tvDay);
            holder.tv_Night = convertView.findViewById(R.id.tvNight);
            holder.tv_Off = convertView.findViewById(R.id.tvOff);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.tv_Date.setText(String.valueOf(overTime.getDate()));
        holder.tv_Regular.setText(String.valueOf(overTime.getRegular()));
        holder.tv_Day.setText(String.valueOf(overTime.getDay()));
        holder.tv_Night.setText(String.valueOf(overTime.getNight()));
        holder.tv_Off.setText(String.valueOf(overTime.getOff()));
        return convertView;
    }
}
