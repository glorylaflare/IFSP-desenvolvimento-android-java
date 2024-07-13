package com.example.texto;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button btnGravar;

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

        editText = findViewById(R.id.edtTexto);
        btnGravar = findViewById(R.id.btnSalvar);

        btnGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strTexto = editText.getText().toString();
                gravarTexto(strTexto);
                exportar();
            }
        });
        
        String strTexto = lerArquivo();
        editText.setText(strTexto);
    }

    private void exportar() {
        File caminhoInterno = Environment.getDataDirectory();
        String caminhoArquivoOrigem = "/data/" +
                getApplication().getPackageName() +
                "/files/anotacao.txt";

        File arquivoAtual = new File(caminhoInterno, caminhoArquivoOrigem);

        String caminhoBackup = getApplicationContext().getExternalFilesDir(null) + "/files";
        File dir =  new File(caminhoBackup);
        if(!dir.exists()) dir.mkdirs();
        String nomeArquivoBackup = "backup_anotacao.txt";

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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String lerArquivo() {
        String strRetorno = "";
        try {
            InputStream arquivo = openFileInput("anotacao.txt");
            if(arquivo != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(arquivo);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String linhasArquivo;
                while((linhasArquivo = bufferedReader.readLine()) != null) {
                    strRetorno += linhasArquivo + "\n";
                }
                arquivo.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return strRetorno;
    }

    private void gravarTexto(String strTexto) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                    openFileOutput("anotacao.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(strTexto);
            outputStreamWriter.close();
            Toast.makeText(MainActivity.this, "Arquivo salvo com sucesso!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}