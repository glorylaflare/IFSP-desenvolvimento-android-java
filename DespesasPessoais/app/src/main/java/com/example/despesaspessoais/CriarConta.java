package com.example.despesaspessoais;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

public class CriarConta extends AppCompatActivity {

    EditText editTextNome, editTextUsuario, editTextSenha;
    Button botaoCriarConta;
    DataBaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_criar_conta);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setTitle("Despesas Pessoais - Cadastre-se");

        editTextNome = findViewById(R.id.edtNome);
        editTextUsuario = findViewById(R.id.edtUsuario);
        editTextSenha = findViewById(R.id.edtSenha);
        botaoCriarConta =  findViewById(R.id.btnCadastro);
        botaoCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strNome = editTextNome.getText().toString();
                String strUsuario = editTextUsuario.getText().toString();
                String strSenha = editTextSenha.getText().toString();

                if(strNome.equals("") || strUsuario.equals("") || strSenha.equals("")) {
                    Toast.makeText(CriarConta.this, "Preencha os campos para salvar", Toast.LENGTH_SHORT).show();
                } else {
                    helper = new DataBaseHelper(getApplicationContext());
                    if (helper.inserirUsuario(strNome, strUsuario, strSenha)) {
                        Intent i = new Intent(CriarConta.this, Movimentos.class);
                        startActivity(i);
                        finish();
                        Toast.makeText(CriarConta.this, "Usuário criado com sucesso", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CriarConta.this, "Falha ao criar o usuário", Toast.LENGTH_SHORT).show();
                    };
                }
            }
        });

    }
}