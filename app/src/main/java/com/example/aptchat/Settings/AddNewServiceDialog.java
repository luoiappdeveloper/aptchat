package com.example.aptchat.Settings;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.aptchat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;

import static com.example.aptchat.MainActivity.dba;

public class AddNewServiceDialog extends DialogFragment implements AdapterView.OnItemSelectedListener {
        EditText clientName ;
        EditText services;
        ServicesSettings mServicesSettings;

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = requireActivity().getLayoutInflater();
            final View view = inflater.inflate(R.layout.add_a_new_service_dialog,null);
            builder.setView(view);

            /**
             * Tạo Hashmap chứa 1 service mới để bỏ lên database
             */
            final HashMap<String,String> service = new HashMap<>();
            /**
             * Lấy edit text của tên service và service Type
             */
            final EditText serviceNameeditText = (EditText) view.findViewById(R.id.settings_service_name_input);





            /**
             * Tạo 2 spinner giờ và phút
             * Chọn giờ chọn phút xong thì sẽ lấy giờ x4 + phút để tính ra duration.
             * Duration là 1 số int, vd 1h30p tương đương duration  = 6
             */
            final Spinner hoursSpinner = view.findViewById(R.id.add_new_services_hours_spinner);
            ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getContext(),R.array.hours,android.R.layout.simple_spinner_item);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            hoursSpinner.setAdapter(arrayAdapter);

            final Spinner minutesSpinner = view.findViewById(R.id.add_new_services_minutes_spinner);
            ArrayAdapter<CharSequence> minutesArrayAdapter = ArrayAdapter.createFromResource(getContext(),R.array.minutes,android.R.layout.simple_spinner_item);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            minutesSpinner.setAdapter(minutesArrayAdapter);


            builder.setMessage(R.string.add_a_new_service_string)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            /**
                             * Lấy vị trí của spinner để thành integer, vị trí hour x4 + vị trí minute
                             */
                            try{

                                String serviceName = serviceNameeditText.getText().toString();


                               int hours= hoursSpinner.getSelectedItemPosition() * 4;
                               int minute= minutesSpinner.getSelectedItemPosition();
                               int serviceDuration = hours + minute ;
                               if (serviceDuration == 0){


                               }



                            service.put("ServiceName",serviceName);
                            service.put("ServiceDuration",serviceDuration+"");

                            db.collection("SALON").document(dba).collection(ServicesSettings.SERVICES).add(service).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if(task.isSuccessful()){
                                        showToast("New Services Succesfully Added");
                                    } else{
                                        showToast("Please check your connection and try again later");

                                    }
                                }
                            });

                            }catch (Exception e){
                                showToast("Please check your connection and try again later");
                            }
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });

            // Create the AlertDialog object and return it
            return builder.create();
        }
    public void showToast (String toast){



    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

