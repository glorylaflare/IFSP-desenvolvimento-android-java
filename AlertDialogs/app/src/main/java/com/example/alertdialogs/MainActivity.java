package com.example.alertdialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    AlertDialog.Builder dialog, dialogXml;
    TextView textViewDate, textViewTime;
    Button btnAbrir, btnAbrirDialogXml;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnAbrir = findViewById(R.id.btnAbrir);
        btnAbrirDialogXml = findViewById(R.id.btnDialog);
        btnAbrir.setOnClickListener(this);
        btnAbrirDialogXml.setOnClickListener(this);

        textViewDate = findViewById(R.id.textDate);
        textViewTime = findViewById(R.id.textTime);
        Calendar calendar = Calendar.getInstance();
        int year, month, day, hour, minute;
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        textViewDate.setText(String.format("%d/%d/%d", day, month + 1, year));
        textViewTime.setText(String.format("%d:%d", hour, minute));

        textViewDate.setOnClickListener(this);
        textViewTime.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Calendar calendar = Calendar.getInstance();
        if(v == btnAbrir) {
            dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setTitle("Teste ed Alert Dialog");
            dialog.setMessage("Este é um exemplo para o curso do IFSP");
            dialog.setIcon(android.R.drawable.ic_delete);
            dialog.setPositiveButton("Positivo", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Acontece algo ao tocar no botão positivo.
                    Toast.makeText(MainActivity.this, "Apertou no Positivo", Toast.LENGTH_SHORT).show();
                }
            });
            dialog.setNegativeButton("Negativo", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this, "Apertou no Negativo", Toast.LENGTH_SHORT).show();
                }
            });
            dialog.setNeutralButton("Neutro", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this, "Tocou em Neutro", Toast.LENGTH_SHORT).show();
                }
            });
            dialog.show();
        } else if(v == btnAbrirDialogXml) {
            dialogXml = new AlertDialog.Builder(MainActivity.this);
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.dialog, null);
            dialogXml.setView(view);

            EditText editNome = view.findViewById(R.id.username);
            EditText editSenha = view.findViewById(R.id.password);
            Button btnLogin = view.findViewById(R.id.btnLogin);
            Button btnCancel = view.findViewById(R.id.btnCancel);
            AlertDialog alertDialog = dialogXml.show();
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String strUsername = editNome.getText().toString();
                    String strPassword = editSenha.getText().toString();
                    Toast.makeText(MainActivity.this, strUsername + " " + strPassword, Toast.LENGTH_LONG).show();
                    alertDialog.dismiss();
                }
            });
        } else if(v == textViewDate){
            int year, month, day;
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @SuppressLint("DefaultLocale")
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            textViewDate.setText(String.format("%d/%d/%d", dayOfMonth, month + 1, year));
                        }
                    }, year,month,day);
            datePickerDialog.show();
        } else if(v == textViewTime){
            int hour, minute;
            hour = calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @SuppressLint("DefaultLocale")
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            textViewTime.setText(String.format("%d:%d", hourOfDay, minute));
                        }
                    }, hour, minute, false);
            timePickerDialog.show();
        }
    }
}