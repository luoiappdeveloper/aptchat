package com.example.aptchat.Settings;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.aptchat.Objects.Services;
import com.example.aptchat.Objects.Technician;
import com.example.aptchat.R;

import java.util.ArrayList;
import java.util.List;

public class ServicesItemListViewAdapter extends ArrayAdapter<Services> {
    private Context mContext;
    private List<Services> servicesList = new ArrayList<>();


    public ServicesItemListViewAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<Services> list) {
        super(context,0,list);
        mContext = context;
        servicesList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.services_list_item,parent,false);

        Services currentService = servicesList.get(position);


        TextView name = listItem.findViewById(R.id.services_list_item_service_name);
        name.setText(currentService.getName());

        TextView duration =  listItem.findViewById(R.id.services_list_item_duration);
        duration.setText(""+currentService.getDuration());

        TextView type = listItem.findViewById(R.id.services_list_item_type);
        type.setText(currentService.getType());
        return listItem;
    }
}
