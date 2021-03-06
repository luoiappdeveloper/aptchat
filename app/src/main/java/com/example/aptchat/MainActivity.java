package com.example.aptchat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.aptchat.Settings.ServicesSettings;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements HorizontalScroll.ScrollViewListener, VerticalScroll.ScrollViewListener {

    private static int SCREEN_HEIGHT;
    private static int SCREEN_WIDTH;
    private static int WIDTH_SIZE = 10;
    private static int WIDTH_SIZEAC= 8;
    private static int HEIGHT_SIZE = 10;
    public static String dba = "USA NAILS";

    RelativeLayout relativeLayoutMain;

    RelativeLayout relativeLayoutA;
    RelativeLayout relativeLayoutB;
    RelativeLayout relativeLayoutC;
    RelativeLayout relativeLayoutD;

    private static TableLayout tableLayoutA;
    private static TableLayout tableLayoutB;
    private static TableLayout tableLayoutC;
    private static TableLayout tableLayoutD;

    TableRow tableRow;
    TableRow tableRowB;

    HorizontalScroll horizontalScrollViewB;
    HorizontalScroll horizontalScrollViewD;

    VerticalScroll scrollViewC;
    VerticalScroll scrollViewD;
    private DrawerLayout drawerLayout;

    Calendar rightnow = Calendar.getInstance();
    public static Calendar currentDay = Calendar.getInstance();
    static String dateToDatabase = currentDay.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault())
             + currentDay.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault())
             + currentDay.get(Calendar.DAY_OF_MONTH)
             + currentDay.get(Calendar.YEAR);

    static ArrayList<String> thoCoHen ;

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    /*
         This is for counting how many columns are added in the row.
    */
    int tableColumnCountB= 0;

    /*
         This is for counting how many row is added.
    */
    int tableRowCountC= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.baseline_menu_24);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        drawerLayout.closeDrawers();
                        Intent intent = new Intent(getApplicationContext(),ServicesSettings.class);
                        startActivity(intent);


                        return true;
                    }
                });
        /*
            Mandatory Content
         */

        relativeLayoutMain= findViewById(R.id.relativeLayoutMain);
        getScreenDimension();
        initializeRelativeLayout();
        initializeScrollers();
        initializeTableLayout();
        horizontalScrollViewB.setScrollViewListener(this);
        horizontalScrollViewD.setScrollViewListener(this);
        scrollViewC.setScrollViewListener(this);
        scrollViewD.setScrollViewListener(this);
        addRowToTableA();
        initializeRowForTableB();

        /*
            Till Here.
         */

        Button datePickerButton = findViewById(R.id.date);
        String todaytext = currentDay.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault()) +
                ", " + currentDay.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()) +
                " " + currentDay.get(Calendar.DAY_OF_MONTH) +
                " " + currentDay.get(Calendar.YEAR);

        datePickerButton.setText(todaytext);
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "Date Picker");
            }
        });

        /*  There is two unused functions
            Have a look on these functions and try to recreate and use it.
            createCompleteColumn();
            createCompleteRow();
        */
        buildCellsForTableD();
    }



    private void getScreenDimension(){
        WindowManager wm= (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        SCREEN_WIDTH= size.x;
        SCREEN_HEIGHT = size.y;
    }

    private void initializeRelativeLayout(){
        relativeLayoutA= new RelativeLayout(getApplicationContext());
        relativeLayoutA.setId(R.id.relativeLayoutA);
        relativeLayoutA.setPadding(0,0,0,0);

        relativeLayoutB= new RelativeLayout(getApplicationContext());
        relativeLayoutB.setId(R.id.relativeLayoutB);
        relativeLayoutB.setPadding(0,0,0,0);

        relativeLayoutC= new RelativeLayout(getApplicationContext());
        relativeLayoutC.setId(R.id.relativeLayoutC);
        relativeLayoutC.setPadding(0,0,0,0);

        relativeLayoutD= new RelativeLayout(getApplicationContext());
        relativeLayoutD.setId(R.id.relativeLayoutD);
        relativeLayoutD.setPadding(0,0,0,0);

        relativeLayoutA.setLayoutParams(new RelativeLayout.LayoutParams(SCREEN_WIDTH/WIDTH_SIZEAC,SCREEN_HEIGHT/HEIGHT_SIZE));
        this.relativeLayoutMain.addView(relativeLayoutA);


        RelativeLayout.LayoutParams layoutParamsRelativeLayoutB= new RelativeLayout.LayoutParams(SCREEN_WIDTH- (SCREEN_WIDTH/WIDTH_SIZE), SCREEN_HEIGHT/HEIGHT_SIZE);
        layoutParamsRelativeLayoutB.addRule(RelativeLayout.RIGHT_OF, R.id.relativeLayoutA);
        relativeLayoutB.setLayoutParams(layoutParamsRelativeLayoutB);
        this.relativeLayoutMain.addView(relativeLayoutB);

        RelativeLayout.LayoutParams layoutParamsRelativeLayoutC= new RelativeLayout.LayoutParams(SCREEN_WIDTH/WIDTH_SIZEAC, SCREEN_HEIGHT - (SCREEN_HEIGHT/HEIGHT_SIZE));
        layoutParamsRelativeLayoutC.addRule(RelativeLayout.BELOW, R.id.relativeLayoutA);
        relativeLayoutC.setLayoutParams(layoutParamsRelativeLayoutC);
        this.relativeLayoutMain.addView(relativeLayoutC);

        RelativeLayout.LayoutParams layoutParamsRelativeLayoutD= new RelativeLayout.LayoutParams(SCREEN_WIDTH- (SCREEN_WIDTH/WIDTH_SIZE), SCREEN_HEIGHT - (SCREEN_HEIGHT/HEIGHT_SIZE));
        layoutParamsRelativeLayoutD.addRule(RelativeLayout.BELOW, R.id.relativeLayoutB);
        layoutParamsRelativeLayoutD.addRule(RelativeLayout.RIGHT_OF, R.id.relativeLayoutC);
        relativeLayoutD.setLayoutParams(layoutParamsRelativeLayoutD);
        this.relativeLayoutMain.addView(relativeLayoutD);

    }

    private void initializeScrollers(){
        horizontalScrollViewB= new HorizontalScroll(getApplicationContext());
        horizontalScrollViewB.setPadding(0,0,0,0);

        horizontalScrollViewD= new HorizontalScroll(getApplicationContext());
        horizontalScrollViewD.setPadding(0,0,0,0);

        scrollViewC= new VerticalScroll(getApplicationContext());
        scrollViewC.setPadding(0,0,0,0);

        scrollViewD= new VerticalScroll(getApplicationContext());
        scrollViewD.setPadding(0,0,0,0);

        horizontalScrollViewB.setLayoutParams(new ViewGroup.LayoutParams(SCREEN_WIDTH- (SCREEN_WIDTH/WIDTH_SIZE), SCREEN_HEIGHT/HEIGHT_SIZE));
        scrollViewC.setLayoutParams(new ViewGroup.LayoutParams(SCREEN_WIDTH/WIDTH_SIZEAC ,SCREEN_HEIGHT - (SCREEN_HEIGHT/HEIGHT_SIZE)));
        scrollViewD.setLayoutParams(new ViewGroup.LayoutParams(SCREEN_WIDTH- (SCREEN_WIDTH/WIDTH_SIZE), SCREEN_HEIGHT - (SCREEN_HEIGHT/HEIGHT_SIZE) ));
        horizontalScrollViewD.setLayoutParams(new ViewGroup.LayoutParams(SCREEN_WIDTH- (SCREEN_WIDTH/WIDTH_SIZE), SCREEN_HEIGHT - (SCREEN_HEIGHT/HEIGHT_SIZE) ));

        this.relativeLayoutB.addView(horizontalScrollViewB);
        this.relativeLayoutC.addView(scrollViewC);
        this.scrollViewD.addView(horizontalScrollViewD);
        this.relativeLayoutD.addView(scrollViewD);

    }

    private  void initializeTableLayout(){
        tableLayoutA= new TableLayout(getApplicationContext());
        tableLayoutA.setPadding(0,0,0,0);
        tableLayoutB= new TableLayout(getApplicationContext());
        tableLayoutB.setPadding(0,0,0,0);
        tableLayoutB.setId(R.id.tableLayoutB);
        tableLayoutC= new TableLayout(getApplicationContext());
        tableLayoutC.setPadding(0,0,0,0);
        tableLayoutD= new TableLayout(getApplicationContext());
        tableLayoutD.setPadding(0,0,0,0);

        TableLayout.LayoutParams layoutParamsTableLayoutA= new TableLayout.LayoutParams(SCREEN_WIDTH/WIDTH_SIZEAC, SCREEN_HEIGHT/HEIGHT_SIZE);
        tableLayoutA.setLayoutParams(layoutParamsTableLayoutA);
        tableLayoutA.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        this.relativeLayoutA.addView(tableLayoutA);

        TableLayout.LayoutParams layoutParamsTableLayoutB= new TableLayout.LayoutParams(SCREEN_WIDTH -(SCREEN_WIDTH/WIDTH_SIZE), SCREEN_HEIGHT/HEIGHT_SIZE);
        tableLayoutB.setLayoutParams(layoutParamsTableLayoutB);
        tableLayoutB.setBackgroundColor(getResources().getColor(R.color.colorSecondary));
        this.horizontalScrollViewB.addView(tableLayoutB);

        TableLayout.LayoutParams layoutParamsTableLayoutC= new TableLayout.LayoutParams(SCREEN_WIDTH/WIDTH_SIZEAC, SCREEN_HEIGHT - (SCREEN_HEIGHT/HEIGHT_SIZE));
        tableLayoutC.setLayoutParams(layoutParamsTableLayoutC);
        tableLayoutC.setBackgroundColor(getResources().getColor(R.color.colorSecondary));
        this.scrollViewC.addView(tableLayoutC);

        TableLayout.LayoutParams layoutParamsTableLayoutD= new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
        tableLayoutD.setLayoutParams(layoutParamsTableLayoutD);
        tableLayoutD.setId(R.id.id_tableLayoutD);
        this.horizontalScrollViewD.addView(tableLayoutD);

    }

    @Override
    public void onScrollChanged(HorizontalScroll scrollView, int x, int y, int oldx, int oldy) {
        if(scrollView == horizontalScrollViewB){
            horizontalScrollViewD.scrollTo(x,y);
        }
        else if(scrollView == horizontalScrollViewD){
            horizontalScrollViewB.scrollTo(x, y);
        }

    }

    @Override
    public void onScrollChanged(VerticalScroll scrollView, int x, int y, int oldx, int oldy) {
        if(scrollView == scrollViewC){
            scrollViewD.scrollTo(x,y);
        }
        else if(scrollView == scrollViewD){
            scrollViewC.scrollTo(x,y);
        }
    }

    private void addRowToTableA(){
        tableRow= new TableRow(getApplicationContext());
        TableRow.LayoutParams layoutParamsTableRow= new TableRow.LayoutParams(SCREEN_WIDTH/WIDTH_SIZEAC, SCREEN_HEIGHT/HEIGHT_SIZE);
        tableRow.setLayoutParams(layoutParamsTableRow);
        TextView label_date = new TextView(getApplicationContext());
        label_date.setText("");
        tableRow.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
        label_date.setTextSize(getResources().getDimension(R.dimen.cell_text_size));
        tableRow.addView(label_date);
        this.tableLayoutA.addView(tableRow);
    }

    private void initializeRowForTableB(){
        tableRowB= new TableRow(getApplicationContext());
        tableRow.setPadding(0,0,0,0);
        this.tableLayoutB.addView(tableRowB);
    }

    private synchronized void addColumnsToTableB(String text, final int id){
        tableRow= new TableRow(getApplicationContext());
        TableRow.LayoutParams layoutParamsTableRow= new TableRow.LayoutParams(SCREEN_WIDTH/WIDTH_SIZE, SCREEN_HEIGHT/HEIGHT_SIZE);
        tableRow.setPadding(3,3,3,4);
        tableRow.setLayoutParams(layoutParamsTableRow);
        TextView label_date = new TextView(getApplicationContext());
        label_date.setText(text);
        label_date.setTextSize(getResources().getDimension(R.dimen.cell_text_size));
        this.tableRow.addView(label_date);
        this.tableRow.setTag(id);
        this.tableRowB.addView(tableRow);
        tableColumnCountB++;
    }

    private synchronized void addRowToTableC(String text){
        TableRow tableRow1= new TableRow(getApplicationContext());
        TableRow.LayoutParams layoutParamsTableRow1= new TableRow.LayoutParams(SCREEN_WIDTH/WIDTH_SIZEAC, SCREEN_HEIGHT/HEIGHT_SIZE);
        tableRow1.setPadding(3,3,3,4);
        tableRow1.setLayoutParams(layoutParamsTableRow1);
        TextView label_date = new TextView(getApplicationContext());
        label_date.setText(text);
        label_date.setTextSize(getResources().getDimension(R.dimen.cell_text_size));
        tableRow1.addView(label_date);

        TableRow tableRow= new TableRow(getApplicationContext());
        TableRow.LayoutParams layoutParamsTableRow= new TableRow.LayoutParams(SCREEN_WIDTH/WIDTH_SIZEAC, SCREEN_HEIGHT/HEIGHT_SIZE);
        tableRow.setPadding(0,0,0,0);
        tableRow.setLayoutParams(layoutParamsTableRow);
        tableRow.addView(tableRow1);
        this.tableLayoutC.addView(tableRow, tableRowCountC);
        tableRowCountC++;
    }

    private synchronized void initializeRowForTableD(int pos){
        TableRow tableRowD= new TableRow(getApplicationContext());
        TableRow.LayoutParams layoutParamsTableRow= new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, SCREEN_HEIGHT/HEIGHT_SIZE);
        tableRowD.setPadding(0,0,0,0);
        tableRowD.setLayoutParams(layoutParamsTableRow);
        this.tableLayoutD.addView(tableRowD, pos);
    }

    private synchronized void addColumnToTableAtD(final int rowPos, String text,int techid, int col){
        // tạo 1 tablerow mới, hoàn toàn trống, ở vị trí rowpos trong tablelayoutD
        final TableRow tableRowAdd= (TableRow) this.tableLayoutD.getChildAt(rowPos);
        //tạo 1 tablerow mới (sẽ dc tính như là 1 cell)
        tableRow= new TableRow(getApplicationContext());
        TableRow.LayoutParams layoutParamsTableRow= new TableRow.LayoutParams(SCREEN_WIDTH/WIDTH_SIZE, SCREEN_HEIGHT/HEIGHT_SIZE);
        tableRow.setPadding(3,3,3,4);
        tableRow.setBackground(getResources().getDrawable(R.drawable.cell_bacground));
        tableRow.setLayoutParams(layoutParamsTableRow);
        TextView label_date = new TextView(getApplicationContext());
        label_date.setText(text);
        label_date.setTextSize(getResources().getDimension(R.dimen.cell_text_size));
        //settag ko có tác dụng gì hết, vì tag là thuộc tính của view, thông tin này ẩn, dùng để referral sau đó thôi chứ ko xài làm gì cả, xóa line này cũng ko ảnh hưởng gì
        tableRow.setTag(label_date);
        // add textview vô cell, xóa line này thì ko có textview nào nằm trong tablerow này cả
        //  this.tableRow.addView(label_date);
        //add  từng cell vô trong tablerowadd line.
       View cell = new View(getApplicationContext());
        final int id = 1000+col;
        cell.setId(id);
        this.tableRow.addView(cell);






        tableRowAdd.addView(tableRow);
        cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeApt(id);
            }
        });



    }
    private void buildCellsForTableD() {


        for(int i=0; i<30; i++){
            int hour = i/4 + 8;
            int minute =(i%4) * 15;
            DateFormat df = new SimpleDateFormat("HH:mm");
            Calendar calendar = currentDay;
            calendar.set(Calendar.HOUR_OF_DAY,hour);
            calendar.set(Calendar.MINUTE,minute);

            String timeline = df.format(calendar.getTime());

            addColumnsToTableB(timeline, i);
        }
        for(int i=0; i<9; i++){
            initializeRowForTableD(i);
            addRowToTableC("Row"+ i);
            for(int j=0; j<tableColumnCountB; j++){
                addColumnToTableAtD(i, "",i,j);
            }
        }
    }

    private void makeApt(int cellid) {
        OnClickDialog aptDialog = new OnClickDialog();
        Bundle data = new Bundle();

        FragmentManager fm = getSupportFragmentManager();
        aptDialog.show(fm, "ssdf");
    }
    public void displayAnAppt (String clientname, String services){




    }
    public ArrayList<String> getTechNameWhoHaveApptThisDay() {
        /**
         * Truy xuất vào ngày hiện tại trên database, lấy tất cả document của thợ nào có hẹn ngày hôm đó cho vào array tên thợ
         * mục đích là để bên dưới sẽ call database 1 lần nữa, và mở document của thợ nào có hẹn bằng 1 method khác
         */
        thoCoHen.clear();

        db.collection("SALONS").document(dba).collection("DATE").document(dateToDatabase).collection("APT")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                thoCoHen.add(document.getId());
                                Log.d("mytag", "Load dc het ten tho ", task.getException());
                        } }
                        else {
                                Log.d("mytag", "Error getting documents: ", task.getException());
                            }
                    }
                });

        if (thoCoHen.size() != 0) {
            for (int i = 0; i < thoCoHen.size(); i++) {
               Log.d("tenthocohen",thoCoHen.get(i));
            }
        }
        return thoCoHen;

    }

    /**
     *
     * ĐỂ DÀNH CHƯA XÀI TỚI
     *
    private void createCompleteColumn(String value){
        int i=0;
        int j=tableRowCountC-1;
        for(int k=i; k<=j; k++){
            addColumnToTableAtD(k, value);
        }
    }

    private void createCompleteRow(String value){
        initializeRowForTableD(0);
        int i=0;
        int j=tableColumnCountB-1;
        int pos= tableRowCountC-1;
        for(int k=i; k<=j; k++){
            addColumnToTableAtD(pos, value);
        }
    }
*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
