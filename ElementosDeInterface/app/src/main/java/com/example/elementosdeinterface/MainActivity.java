package com.example.elementosdeinterface;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    TextView retornoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        retornoText = findViewById(R.id.retornoText);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button button = findViewById(R.id.btnChangeActivity);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(MainActivity.this, SecondaryActivity.class));

                /*
                Intent i = new Intent(MainActivity.this, SecondaryActivity.class);
                i.putExtra("apelido", "Marcelo");
                startActivity(i);
                */

                Intent i = new Intent(MainActivity.this, SecondaryActivity.class);
                activityResultLauncher.launch(i);
            }
        });
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == 1){
                        Intent intent = result.getData();
                        if(intent != null){
                            String stringRetorno = intent.getStringExtra("retorno");
                            retornoText.setText(stringRetorno);
                        }
                    }
                }
            }
    );

    /*
        CheckBox ckbTv = findViewById(R.id.ckb_tv);
        CheckBox ckbGeladeira = findViewById(R.id.ckb_geladeira);
        CheckBox ckbMicroondas = findViewById(R.id.ckb_microondas);

        Button btnOk = findViewById(R.id.btn_ok);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ckbTv.isChecked();
                Toast.makeText(MainActivity.this, "A tv está " + ckbTv.isChecked(), Toast.LENGTH_LONG).show();
                Toast.makeText(MainActivity.this, "A geladeira está " + ckbGeladeira.isChecked(), Toast.LENGTH_LONG).show();
            }
        });
        */
}