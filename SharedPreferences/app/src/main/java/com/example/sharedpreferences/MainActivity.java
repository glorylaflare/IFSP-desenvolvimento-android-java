package com.example.sharedpreferences;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class MainActivity extends AppCompatActivity {

    EditText edtUsuario, edtSenha;
    Button btnLogin, btnExportar;
    CheckBox ckbLembrar;
    boolean bolLembrar = false;
    SharedPreferences preferences;
    private String ARQUIVO_PREFERENCIAS = "Arquivo_Preferencias";

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

        edtUsuario = findViewById(R.id.edtUsuario);
        edtSenha = findViewById(R.id.edtSenha);
        btnLogin = findViewById(R.id.btnLogin);
        ckbLembrar = findViewById(R.id.ckbLembrar);
        btnExportar = findViewById(R.id.btnExportar);

        preferences = getSharedPreferences(ARQUIVO_PREFERENCIAS, 0);
        if(preferences.contains("usuario")) {
            String strUsuarioPreference = preferences.getString("usuario", "Sem usuário.");
            String strSenhaPreference = preferences.getString("senha", "Sem senha.");

            edtUsuario.setText(strUsuarioPreference);
            edtSenha.setText(strSenhaPreference);

            ckbLembrar.setChecked(true);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strUsuario = edtUsuario.getText().toString();
                String strSenha = edtSenha.getText().toString();
                
                if(strUsuario.equals("admin") && strSenha.equals("admin")) {
                    if(bolLembrar) {
                        //salvar o login no SharedPreferences
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("usuario", strUsuario);
                        editor.putString("senha", strSenha);
                        editor.apply();
                    } else {
                        //remover o login no SharedPreferences
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.remove("usuario");
                        editor.remove("senha");
                        editor.apply();
                    }
                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Usuário ou Senha inválido.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ckbLembrar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                bolLembrar = isChecked ? true : false;
            }
        });

        btnExportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File caminhoInterno = Environment.getDataDirectory();
                String caminhoArquivoOrigem = "/data/" + 
                                getApplication().getPackageName() + 
                                "/shared_prefs/" + ARQUIVO_PREFERENCIAS + ".xml";
                
                File arquivoAtual = new File(caminhoInterno, caminhoArquivoOrigem);
                
                String caminhoBackup = getApplicationContext().getExternalFilesDir(null) + "/BKP_Prefs";
                File dir =  new File(caminhoBackup);
                if(!dir.exists()) dir.mkdirs();
                String nomeArquivoBackup = "Shared_Prefs.xml";
                
                File arquivoDestino = new File(caminhoBackup, nomeArquivoBackup);
                FileChannel fileChannelOrigem = null;
                FileChannel fileChannelBackup = null;

                try {
                    fileChannelOrigem = new FileInputStream(arquivoAtual).getChannel();
                    fileChannelBackup = new FileOutputStream(arquivoDestino).getChannel();
                    fileChannelBackup.transferFrom(fileChannelOrigem, 0, fileChannelOrigem.size());
                    fileChannelOrigem.close();
                    fileChannelBackup.close();

                    Toast.makeText(MainActivity.this, "Backup realizado com sucesso.", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}