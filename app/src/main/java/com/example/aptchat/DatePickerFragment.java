package com.example.aptchat;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Use the current date as the default date in the date picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);


        //Create a new DatePickerDialog instance and return it
        /*
            DatePickerDialog Public Constructors - Here we uses first one
            public DatePickerDialog (Context context, DatePickerDialog.OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth)
            public DatePickerDialog (Context context, int theme, DatePickerDialog.OnDateSetListener listener, int year, int monthOfYear, int dayOfMonth)
         */
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }


    public void onDateSet(DatePicker view, int year, int month, int day) {
        MainActivity main = new MainActivity();
        Calendar cal = Calendar.getInstance();

        /**
         * Yêu cầu 1:
         * phải trả dc ngày đã chọn dưới dạng string có thứ tháng, ngày, năm
         * Done
         *
         */
        cal.set(year, month, day);

        //Do something with the date chosen by the user
        TextView tv = (TextView) getActivity().findViewById(R.id.date);
        String todaytext = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault()) +
                ", " + cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()) +
                "/" + cal.get(Calendar.DAY_OF_MONTH) +
                "/" + cal.get(Calendar.YEAR);
        tv.setText(todaytext);
        /**
         * yÊU CẦU 2
         *
         *
         * phải trả dc ngày đã chọn về lại mainactivity dưới dạng date object
         * trên nguyên tăc, phải dùng intent hoặc bundle, nhưng mà khó cái là thằng này mình
         * ko làm method riêng dc, vì cái gì cũng bắt buộc bị kẹt trong method onDateSet này
         *
         * thường thì mình sẽ dc chọn option on ok listener cho dialog, nhưng mà cái thằng này nó ko cho
         * nên phải đi đường vòng
         *
         * Cuối cùng thì cách đơn giản nhất, là tạo method onDateChanged bên MainActivity
         * Muốn làm gì thì cứ bỏ vô method này bên MainActivity cho khỏe
         * bên đây nó chỉ gọi thôi, như kiểu onClickListener vậy.
         * pass thêm 3 biến year, month, day, để nó có thông tin về ngày đã pick trả về bên kia
         * muốn làm gì với ngày đó thì làm
         */


    }
}


