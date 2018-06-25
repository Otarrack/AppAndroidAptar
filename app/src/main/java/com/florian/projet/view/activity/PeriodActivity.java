package com.florian.projet.view.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.florian.projet.R;
import com.florian.projet.viewModel.PeriodViewModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.app.DatePickerDialog.*;
import static android.view.View.OnClickListener;

public class PeriodActivity extends AppCompatActivity implements OnClickListener {
    PeriodViewModel periodViewModel;
    private EditText fromDateEditText;
    private EditText toDateEditText;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private DateFormat df;
    private Calendar todayDateCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_period);

        periodViewModel = PeriodViewModel.getInstance();

        df = SimpleDateFormat.getDateInstance();

        fromDateEditText = findViewById(R.id.period_edit_start_date);
        fromDateEditText.setInputType(InputType.TYPE_NULL);
        fromDateEditText.requestFocus();

        toDateEditText = findViewById(R.id.period_edit_end_date);
        toDateEditText.setInputType(InputType.TYPE_NULL);

        fromDateEditText.setText(df.format(periodViewModel.getFromDate().getTime()));
        toDateEditText.setText(df.format(periodViewModel.getToDate().getTime()));

        addFromDateListener();
        addToDateListener();
    }

    private void addFromDateListener() {
        Calendar calendar = Calendar.getInstance();

        fromDateEditText.setOnClickListener(this);

        fromDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Date toDate;
                Calendar fromDateCalendar = Calendar.getInstance();
                Calendar toDateCalendar = Calendar.getInstance();

                try {
                    toDate = df.parse(toDateEditText.getText().toString());
                } catch (ParseException e) {
                    Toast.makeText(getBaseContext(), R.string.period_toast_unvailable_date,Toast.LENGTH_SHORT).show();
                    toDate = periodViewModel.getToDate();
                    e.printStackTrace();
                }
                toDateCalendar.setTime(toDate);

                fromDateCalendar.set(year, monthOfYear, dayOfMonth);

                if (fromDateCalendar.after(todayDateCalendar)) {
                    fromDateCalendar.setTime(todayDateCalendar.getTime());
                }

                if (fromDateCalendar.after(toDateCalendar)) {
                    fromDateCalendar.setTime(toDateCalendar.getTime());
                }

                fromDateEditText.setText(df.format(fromDateCalendar.getTime()));
                periodViewModel.setFromDate(fromDateCalendar.getTime());

            }

        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

    }

    private void addToDateListener() {
        Calendar calendar = Calendar.getInstance();

        toDateEditText.setOnClickListener(this);

        toDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Date fromDate;
                Calendar fromDateCalendar = Calendar.getInstance();
                Calendar toDateCalendar = Calendar.getInstance();

                try {
                    fromDate = df.parse(fromDateEditText.getText().toString());
                } catch (ParseException e) {
                    Toast.makeText(getBaseContext(), R.string.period_toast_unvailable_date,Toast.LENGTH_SHORT).show();
                    fromDate = periodViewModel.getFromDate();
                    e.printStackTrace();
                }
                fromDateCalendar.setTime(fromDate);

                toDateCalendar.set(year, monthOfYear, dayOfMonth);

                if (toDateCalendar.after(todayDateCalendar)) {
                    toDateCalendar.setTime(todayDateCalendar.getTime());
                }

                if (toDateCalendar.before(fromDateCalendar)) {
                    toDateCalendar.setTime(fromDateCalendar.getTime());
                }

                toDateEditText.setText(df.format(toDateCalendar.getTime()));
                periodViewModel.setToDate(toDateCalendar.getTime());
            }

        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClick(View view) {
        if(view == fromDateEditText) {
            fromDatePickerDialog.show();
        } else if(view == toDateEditText) {
            toDatePickerDialog.show();
        }
    }
}
