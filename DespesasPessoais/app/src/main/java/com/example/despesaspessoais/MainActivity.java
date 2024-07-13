package com.example.despesaspessoais;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Button botaoCadastrar, botaoEntrar;
    EditText editTextUsuario, editTextSenha;
    Switch switchLembrar;
    SharedPreferences preferences;
    private String ARQUIVO_PREFERENCIA = "Arquivo_preferencias";
    DataBaseHelper helper;

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

        setTitle("Despesas Pessoais - Entrar");

        editTextUsuario = findViewById(R.id.edtUsuario);
        editTextSenha = findViewById(R.id.edtSenha);
        switchLembrar = findViewById(R.id.swtLembrar);

        helper = new DataBaseHelper(getApplicationContext());
        helper.criaBanco();

        preferences = getSharedPreferences(ARQUIVO_PREFERENCIA, 0);
        if(preferences.contains("usuario")) {
            String strUsuario = preferences.getString("usuario", "Sem usuário");
            String strSenha = preferences.getString("senha", "Sem senha");
            editTextUsuario.setText(strUsuario);
            editTextSenha.setText(strSenha);
            switchLembrar.setChecked(true);

            int usuarios = helper.consultaUsuario(strUsuario, strSenha);
            if(usuarios > 0) {
                Intent i = new Intent(MainActivity.this, Movimentos.class);
                startActivity(i);
                finish();
            }
        }

        botaoCadastrar = findViewById(R.id.btnCadastrar);
        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CriarConta.class);
                startActivity(i);
            }
        });

        botaoEntrar = findViewById(R.id.btnEntrar);
        botaoEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strUsuario = editTextUsuario.getText().toString();
                String strSenha = editTextSenha.getText().toString();
                if(strUsuario.equals("")) {
                    editTextUsuario.setError("Campo vazio");
                } else if(strSenha.equals("")) {
                    editTextSenha.setError("Campo vazio");
                } else {
                    int usuarios = helper.consultaUsuario(strUsuario, strSenha);
                    if(usuarios > 0) {
                        if(switchLembrar.isChecked()) {
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("usuario", strUsuario);
                            editor.putString("senha", strSenha);
                            editor.commit();
                        } else {
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.remove("usuario");
                            editor.remove("senha");
                            editor.commit();
                        }

                        Intent i = new Intent(MainActivity.this, Movimentos.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "Usuário ou senha inválidos", Toast.LENGTH_SHORT).show();
                    }
                    
                    Intent i = new Intent(MainActivity.this, Movimentos.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }
}