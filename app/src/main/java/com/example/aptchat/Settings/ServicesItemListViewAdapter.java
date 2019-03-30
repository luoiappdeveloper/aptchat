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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aptchat.MainActivity;
import com.example.aptchat.Objects.Services;
import com.example.aptchat.Objects.Technician;
import com.example.aptchat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.aptchat.Settings.ServicesSettings.SERVICES;

public class ServicesItemListViewAdapter extends ArrayAdapter<Services> {
    private Context mContext;
    private List<Services> servicesList = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ServicesSettings ss =new ServicesSettings();


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
        final String serviceName = currentService.getName();
        name.setText(serviceName);

        TextView duration =  listItem.findViewById(R.id.services_list_item_duration);
        String serviceDuration = currentService.getDuration()+"";
        duration.setText(serviceDuration);

        TextView type = listItem.findViewById(R.id.services_list_item_type);
        String serviceType = currentService.getType();
        type.setText(serviceType);


        Button deleteService = listItem.findViewById(R.id.service_setting_delete_button);
        deleteService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete a field
                Map<String,Object> updates = new HashMap<>();
                updates.put(serviceName, FieldValue.delete());

                db.collection("SALON").document(MainActivity.dba).collection(SERVICES).document("ALL SERVICES")
                        .update(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                        Toast.makeText(getContext(),"Service deleted",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });

        return listItem;
    }
}
