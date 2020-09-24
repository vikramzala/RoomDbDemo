package com.example.roomdbdemo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.roomdbdemo.R;
import com.example.roomdbdemo.db.DataEntity;
import com.example.roomdbdemo.db.DataListViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditDataActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText edtFirstName, edtLastName, edtMobile, edtBirthDate;
    private Button btnUpdate;
    private Calendar cal;
    private DatePickerDialog DatePickerDialog2;
    private String date;
    private DataListViewModel viewModel;
    String fName, lName, BirthDate, Mobile;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);
        viewModel = ViewModelProviders.of(EditDataActivity.this).get(DataListViewModel.class);
        Intent intent = getIntent();
        fName = intent.getStringExtra("fName");
        lName = intent.getStringExtra("lName");
        BirthDate = intent.getStringExtra("BirthDate");
        Mobile = intent.getStringExtra("Mobile");
        id = Integer.parseInt(intent.getStringExtra("id"));
        initUI();
    }

    private void initUI() {
        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        edtMobile = findViewById(R.id.edtMobile);
        edtBirthDate = findViewById(R.id.edtBirthDate);
        btnUpdate = findViewById(R.id.btnUpdate);
        edtBirthDate.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        edtFirstName.setText("" + fName);
        edtLastName.setText("" + lName);
        edtBirthDate.setText("" + BirthDate);
        edtMobile.setText("" + Mobile);
    }

    private void selectDate() {
        final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        cal = Calendar.getInstance();
        DatePickerDialog2 = new DatePickerDialog(EditDataActivity.this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                cal.set(year, monthOfYear, dayOfMonth);
                date = formatter.format(cal.getTime());
                edtBirthDate.setText(date);

            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        DatePickerDialog2.show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edtBirthDate:
                selectDate();
                break;
            case R.id.btnUpdate:
                if (isValid()) {
                    Log.e("Room >>>", "Call for Room Insertion");
                    DataEntity entity = new DataEntity(edtFirstName.getText().toString().trim(), edtLastName.getText().toString().trim(), edtMobile.getText().toString().trim(), edtBirthDate.getText().toString().trim());
                    viewModel.updateById(edtFirstName.getText().toString().trim(), edtLastName.getText().toString().trim(), edtMobile.getText().toString().trim(), edtBirthDate.getText().toString().trim(), id);
                    Toast.makeText(EditDataActivity.this, "Data Insertion Done ", Toast.LENGTH_LONG).show();
                    edtFirstName.setText("");
                    edtLastName.setText("");
                    edtMobile.setText("");
                    edtBirthDate.setText("");
                    startActivity(new Intent(EditDataActivity.this,MainActivity.class));
                }
                break;
        }
    }

    private boolean isValid() {
        boolean b = false;
        if (edtFirstName.getText().toString().trim().length() == 0) {
            edtFirstName.setError("Please Enter First Name");
            edtFirstName.requestFocus();
            b = false;
        } else if (edtLastName.getText().toString().trim().length() == 0) {
            edtLastName.setError("Please Enter Last Name");
            edtLastName.requestFocus();
            b = false;
        } else if ((edtMobile.getText().toString().trim().length() == 0) || (edtMobile.getText().toString().trim().length() < 10)) {
            edtMobile.setError("Please Enter Mobile");
            edtMobile.requestFocus();
            b = false;
        } else if (edtBirthDate.getText().toString().trim().length() == 0) {
            edtBirthDate.setError("Please Enter BirthDate");
            edtBirthDate.requestFocus();
            b = false;
        } else {
            b = true;
        }
        return b;
    }
}