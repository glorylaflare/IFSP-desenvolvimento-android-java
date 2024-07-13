package com.example.despesaspessoais;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Lancamento extends AppCompatActivity {

    Button botaoSalvar;
    EditText editTextData, editTextValor, editTextDescricao;
    RadioGroup radioGroupTipo;
    int intAno, intMes, intDia, intHora, intMinuto, intSegudo;
    String strDataFormatoBanco, operacao = "inserir";
    DataBaseHelper helper;

    int id;
    String data,valor,descricao,tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lancamento);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setTitle("Adicionar Lançamentos");

        helper = new DataBaseHelper(getApplicationContext());

        botaoSalvar = findViewById(R.id.btnSalvar);
        editTextData = findViewById(R.id.edtData);
        editTextValor = findViewById(R.id.edtValor);
        editTextDescricao = findViewById(R.id.edtDescricao);
        radioGroupTipo = findViewById(R.id.rdgTipo);

        Calendar calendar = Calendar.getInstance();
        intAno = calendar.get(calendar.YEAR);
        intMes = calendar.get(calendar.MONTH);
        intDia = calendar.get(calendar.DAY_OF_MONTH);
        intHora = calendar.get(calendar.HOUR_OF_DAY);
        intMinuto = calendar.get(calendar.MINUTE);
        intSegudo = calendar.get(calendar.SECOND);

        editTextData.setText(intDia + "/" + (intMes + 1) + "/" + intAno + " " + intHora + ":" + intMinuto + ":" + intSegudo);
        strDataFormatoBanco = intAno + "-" + (intMes + 1) + "-" + intDia + " " + intHora + ":" + intMinuto + ":" + intSegudo;

        editTextData.setFocusable(false);
        editTextData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Lancamento.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editTextData.setText(dayOfMonth +"/"+ (month + 1) +"/"+ year + " " + intHora + ":" + intMinuto + ":" + intSegudo);
                        strDataFormatoBanco = year +"/"+ (month + 1) +"/"+ dayOfMonth + " " + intHora + ":" + intMinuto + ":" + intSegudo;
                    }
                }, intAno, intMes, intDia);
                datePickerDialog.show();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            operacao = "alterar";
            setTitle("Editar movimento");
            id = bundle.getInt("id");
            strDataFormatoBanco = bundle.getString("data");
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = null;
            try {
                date = inputFormat.parse(strDataFormatoBanco);
                String dataFormatada = outputFormat.format(date);
                editTextData.setText(dataFormatada);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            valor = bundle.getString("valor");
            editTextValor.setText(valor);
            descricao = bundle.getString("descricao");
            editTextDescricao.setText(descricao);
            tipo = bundle.getString("tipo");
            if(tipo.equals("Entrada")) {
                radioGroupTipo.check(R.id.rdbEntrada);
            } else {
                radioGroupTipo.check(R.id.rdbSaida);
            }
        };

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strValor = editTextValor.getText().toString();
                String strDescricao = editTextDescricao.getText().toString();
                if(strDescricao.equals("") || strValor.equals("")) {
                    Toast.makeText(Lancamento.this, "Preencha os campos para salvar", Toast.LENGTH_SHORT).show();
                } else {
                    int radioButtonId = radioGroupTipo.getCheckedRadioButtonId();
                    View radioButton = radioGroupTipo.findViewById(radioButtonId);
                    int idRB = radioGroupTipo.indexOfChild(radioButton);
                    RadioButton radio = (RadioButton) radioGroupTipo.getChildAt(idRB);

                    String strTipo = radio.getText().toString();
                    salvar(strDataFormatoBanco, strValor, strDescricao, strTipo);
                }

                finish();
            }

        });
    }

    private void salvar(String strDataFormatoBanco, String strValor, String strDescricao, String strTipo) {
        if(operacao.equals("inserir")) {
            if (helper.inserirLancamento(strDataFormatoBanco, strValor, strDescricao, strTipo)) {
                Toast.makeText(Lancamento.this, "Lançamento inserido", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(Lancamento.this, "Falha ao inserir!", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (helper.atualizarLancamento(strDataFormatoBanco, strValor, strDescricao, strTipo, id)) {
                Toast.makeText(Lancamento.this, "Lançamento atualizado", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(Lancamento.this, "Falha ao atualizar!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}