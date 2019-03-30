package com.example.aptchat.Settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.aptchat.MainActivity;
import com.example.aptchat.Objects.Services;
import com.example.aptchat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.aptchat.MainActivity.dba;

public class ServicesSettings extends AppCompatActivity {

    //list of services provided by business
    static ArrayList<Services> servicesArrayList;
    private static GridView listView;
    private ServicesItemListViewAdapter mAdapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private MainActivity m;
    public static String SERVICES = "SERVICES";
    private String TAG = "Tag";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_settings);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.services_settings_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("SERVICE SETTINGS");


        listView = findViewById(R.id.list_view_services_settings);
        servicesArrayList = new ArrayList<>();





        /**
         *On DATA CHange listener.
         * khi tạo mới hoặc xóa service thì thằng này chạy lại.
         * Vấn đề là khi xóa cái cuối cùng thì list ko xóa
         */

        final DocumentReference docRef = db.collection("SALON").document(MainActivity.dba).collection(SERVICES).document("ALL SERVICES");
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    servicesArrayList.clear();
                    getServicesFromFirestore();
                } else if ( snapshot == null ){
                    servicesArrayList.clear();
                    mAdapter = new ServicesItemListViewAdapter(ServicesSettings.this, servicesArrayList);
                    listView.setAdapter(mAdapter);
                }else{
                    Log.w(TAG, "Listen failed.", e);
                }
            }
        });




    }

    public void getServicesFromFirestore() {
        /**
         * Vào database lấy services list xuống bỏ vào servicesArrayList
         * Nếu ko có thì try catch
         */

            db.collection("SALON").document(MainActivity.dba).collection(SERVICES).document("ALL SERVICES")
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()){
                        /**
                         *  Tạo 1 Map<String, Object>, mỗi phần từ trong map là 1 service, (1 field trên database)
                         *  Mà 1 field này lại là 1 Map <String, String> Mỗi cái có chứa 3 thành phần.
                         */
                            Map<String,Object> allservice = documentSnapshot.getData();
                            if (allservice != null){
                                /**
                                 * Vòng lặp for chạy cho từng field, mỗi field này là 1 object, phải cast cái object này thành Map<string,string>
                                 *  Vì mình đã biết các key nằm trong map này là "ServiceName, ServiceDuration, ServiceType"
                                 *  Có thể dễ dàng lấy dữ liệu ra như 1 hashmap bình thường
                                 *  Sau đó bỏ vô arraylist.
                                 *
                                 */
                                for(Map.Entry<String,Object> entry : allservice.entrySet()){
                                    //cast object thành map
                                   Map<String,String> field = (Map<String,String>)entry.getValue();
                                   //lấy dữ liệu trong map
                                   String serviceName = field.get("ServiceName");
                                   int serviceDuration = Integer.parseInt(field.get("ServiceDuration"));
                                   String serviceType = field.get("ServiceType");

                                   servicesArrayList.add(new Services(serviceName,serviceDuration,serviceType));

                                }
                                if (servicesArrayList.size() > 0) {
                                    mAdapter = new ServicesItemListViewAdapter(ServicesSettings.this, servicesArrayList);
                                    listView.setAdapter(mAdapter);

                                }


                            }

                }
            }
            });



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
         Intent intent= new Intent(getApplicationContext(),AddNewServiceActivity.class);
         startActivity(intent);

            return true;
        }
        if (id == R.id.service_setting_refresh_button){
            servicesArrayList.clear();
            listView.setAdapter(null);
            getServicesFromFirestore();
        }

        return super.onOptionsItemSelected(item);
    }
}
