package com.example.aptchat.Settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.aptchat.MainActivity;
import com.example.aptchat.Objects.Services;
import com.example.aptchat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static com.example.aptchat.MainActivity.dba;

public class ServicesSettings extends AppCompatActivity {

    //list of services provided by business
    ArrayList<Services> servicesArrayList;
    private ListView listView;
    private ServicesItemListViewAdapter mAdapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private MainActivity m;
    public static String SERVICES = "SERVICES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_settings);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.services_settings_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        listView = findViewById(R.id.list_view_services_settings);
        servicesArrayList = new ArrayList<>();

        /**
         * Vào database lấy services list xuống bỏ vào servicesArrayList
         * Nếu ko có thì try catch
         */
        // LẤy tên business từ main activity
                try {
            db.collection("SALON").document(MainActivity.dba).collection(SERVICES)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                     @Override
                                                     public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                         if (task.isSuccessful()) {

                                                             for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                                                 String serviceName = documentSnapshot.get("ServiceName").toString();
                                                                 int serviceDuration = (Integer) documentSnapshot.get("ServiceDuration");
                                                                 String serviceType = documentSnapshot.get("ServiceType").toString();
                                                                 servicesArrayList.add(new Services(serviceName, serviceDuration, serviceType));
                                                             }

                                                         } else {
                                                             Log.d("mytag", "Error getting documents: ", task.getException());
                                                         }
                                                     }
                                                 }
            );
        } catch (NullPointerException e) {

            Toast.makeText(getApplicationContext(), "Please ADD A SERVICE or check your connection and try again", Toast.LENGTH_LONG).show();
        }


        if (servicesArrayList.size() != 0) {
            mAdapter = new ServicesItemListViewAdapter(this, servicesArrayList);
            listView.setAdapter(mAdapter);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.services_settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_new_services_button) {







            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
