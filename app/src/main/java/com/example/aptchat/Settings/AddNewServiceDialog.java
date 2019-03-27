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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;

import static com.example.aptchat.MainActivity.dba;

public class AddNewServiceDialog extends DialogFragment implements AdapterView.OnItemSelectedListener {
        EditText clientName ;
        EditText services;

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = requireActivity().getLayoutInflater();
            final View view = inflater.inflate(R.layout.add_a_new_service_dialog,null);
            builder.setView(view);



            builder.setMessage(R.string.add_a_new_service_string)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            HashMap<String,String> service = new HashMap<>();
                            EditText serviceNameeditText = (EditText) view.findViewById(R.id.settings_service_name_input);
                            EditText serviceTypeEditText = (EditText) view.findViewById(R.id.settings_service_type_input);
                            String serviceName = serviceNameeditText.getText().toString();
                            String serviceType = serviceTypeEditText.getText().toString();

                            Spinner hoursSpinner = view.findViewById(R.id.add_new_services_hours_spinner);
                            ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getContext(),R.array.hours,android.R.layout.simple_spinner_item);
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            hoursSpinner.setAdapter(arrayAdapter);
                            hoursSpinner.getOnItemSelectedListener();
                            Spinner minutesSpinner = view.findViewById(R.id.add_new_services_minutes_spinner);

                            try{
                                int serviceDurationInt = Integer.parseInt(serviceDuration);
                            }catch (NumberFormatException e){
                                Toast.makeText(getContext(),"Duration ")
                            }

                            service.put("ServiceName",serviceName);
                            service.put("ServiceDuration",serviceDuration);
                            service.put("ServiceType",serviceType);
                            db.collection("SALON").document(dba).collection(ServicesSettings.SERVICES).add()

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
}
