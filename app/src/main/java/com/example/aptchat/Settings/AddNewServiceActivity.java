package com.example.aptchat.Settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.aptchat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import static com.example.aptchat.MainActivity.dba;

public class AddNewServiceActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_a_new_service_dialog);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /**
         * Tạo Hashmap chứa 1 service mới để bỏ lên database
         */
        final HashMap<String,String> service = new HashMap<>();
        /**
         * Lấy edit text của tên service và service Type
         */
        final EditText serviceNameeditText = findViewById(R.id.settings_service_name_input);





        /**
         * Tạo 2 spinner giờ và phút
         * Chọn giờ chọn phút xong thì sẽ lấy giờ x4 + phút để tính ra duration.
         * Duration là 1 số int, vd 1h30p tương đương duration  = 6
         */
        final Spinner hoursSpinner = findViewById(R.id.add_new_services_hours_spinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,R.array.hours,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hoursSpinner.setAdapter(arrayAdapter);

        final Spinner minutesSpinner = findViewById(R.id.add_new_services_minutes_spinner);
        ArrayAdapter<CharSequence> minutesArrayAdapter = ArrayAdapter.createFromResource(this,R.array.minutes,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        minutesSpinner.setAdapter(minutesArrayAdapter);
        minutesSpinner.setSelection(2);

        final Spinner serviceTypeSpinner = findViewById(R.id.add_new_services_group_selection_spinner);
        ArrayAdapter<CharSequence> serviceTypearrayAdapter = ArrayAdapter.createFromResource(this,R.array.type_to_select,android.R.layout.simple_spinner_item);
        serviceTypearrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        serviceTypeSpinner.setAdapter(serviceTypearrayAdapter);

        Button OKbutton = findViewById(R.id.add_a_new_service_ok_button);
        OKbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serviceName = serviceNameeditText.getText().toString();

                int hours= hoursSpinner.getSelectedItemPosition() * 4;
                int minute= minutesSpinner.getSelectedItemPosition();
                int serviceDuration = hours + minute ;
                if (serviceDuration == 0){
                    Toast.makeText(getApplicationContext(),R.string.service_duration_is_not_valid,Toast.LENGTH_LONG).show();

                }else if( serviceName.length() < 1 ){
                    Toast.makeText(getApplicationContext(),R.string.service_name_is_not_valid,Toast.LENGTH_LONG).show();

                }else{

                    String serviceType = serviceTypeSpinner.getSelectedItem().toString();

                    service.put("ServiceName",serviceName);
                    service.put("ServiceDuration",serviceDuration+"");
                    service.put("ServiceType",serviceType);

                    db.collection("SALON").document(dba).collection(ServicesSettings.SERVICES).document(serviceType)
                            .set(service).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(AddNewServiceActivity.this, "Service Successfully added. You can add another service", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(AddNewServiceActivity.this, "Service is not added, please check your connection and try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            }
            }

      });
    }
}
