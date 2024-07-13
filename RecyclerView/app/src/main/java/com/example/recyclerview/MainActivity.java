package com.example.recyclerview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    String[] estados = {
            "Acre (AC)",
            "Alagoas (AL)",
            "Amapá (AP)",
            "Amazonas (AM)",
            "Bahia (BA)",
            "Ceará (CE)",
            "Distrito Federal (DF)",
            "Espírito Santo (ES)",
            "Goiás (GO)",
            "Maranhão (MA)",
            "Mato Grosso (MT)",
            "Mato Grosso do Sul (MS)",
            "Minas Gerais (MG)",
            "Pará (PA)",
            "Paraíba (PB)",
            "Paraná (PR)",
            "Pernambuco (PE)",
            "Piauí (PI)",
            "Rio de Janeiro (RJ)",
            "Rio Grande do Norte (RN)",
            "Rio Grande do Sul (RS)",
            "Rondônia (RO)",
            "Roraima (RR)",
            "Santa Catarina (SC)",
            "São Paulo (SP)",
            "Sergipe (SE)",
            "Tocantins (TO)"
    };

    String[] capitais = {
            "Rio Branco",
            "Maceio",
            "Macapá",
            "Manaus",
            "Salvador",
            "Fortaleza",
            "Brasília",
            "Vitória",
            "Goiânia",
            "São Luis",
            "Cuiabá",
            "Campo Grande",
            "Belo Horizonte",
            "Belém",
            "João Pessoa",
            "Curitiba",
            "Recife",
            "Teresina",
            "Rio de Janeiro",
            "Natal",
            "Porto Alegre",
            "Porto Velho",
            "Boa Vista",
            "Florianópolis",
            "São Paulo",
            "Aracaju",
            "Palmas"
    };

    String[] populacao = {
            "830.018",
            "3.127.683",
            "733.759",
            "3.941.613",
            "14.141.626",
            "8.794.957",
            "2.817.381",
            "3.833.712",
            "7.056.495",
            "6.775.805",
            "3.658.649",
            "2.757.013",
            "20.538.718",
            "8.121.025",
            "3.974.687",
            "11.444.380",
            "9.058.931",
            "3.271.199",
            "16.054.524",
            "3.302.729",
            "10.882.965",
            "1.581.196",
            "636.707",
            "7.610.361",
            "44.411.238",
            "3.127.683",
            "1.511.460"
    };

    String[] pib = {
            "21.374.000",
            "76.266.000",
            "20.100.000",
            "131.531.000",
            "352.618.000",
            "194.885.000",
            "286.944.000",
            "186.337.000",
            "269.628.000",
            "124.981.000",
            "233.390.000",
            "142.204.000",
            "857.593.000",
            "262.905.000",
            "77.470.000",
            "549.973.000",
            "220.814.000\t",
            "64.028.000",
            "949.301.000",
            "80.181.000",
            "581.284.000",
            "58.170.000",
            "18.203.000",
            "428.571.000",
            "2.719.751.000",
            "51.861.000",
            "51.781.000"
    };

    RecyclerView recyclerView;
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

        recyclerView = findViewById(R.id.rcwEstados);
        recyclerView.setAdapter(new AdapterEstados(estados,capitais));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
        );
        recyclerView.setLayoutManager(layoutManager);
    }

    private int getBrasao(int position) {
        int intBrasao = R.mipmap.ic_launcher;
        switch (position){
            case 0:
                intBrasao = R.drawable.a;
                break;
            case 1:
                intBrasao = R.drawable.b;
                break;
            case 2:
                intBrasao = R.drawable.c;
                break;
            case 3:
                intBrasao = R.drawable.d;
                break;
            case 4:
                intBrasao = R.drawable.e;
                break;
            case 5:
                intBrasao = R.drawable.f;
                break;
            case 6:
                intBrasao = R.drawable.g;
                break;
            case 7:
                intBrasao = R.drawable.h;
                break;
            case 8:
                intBrasao = R.drawable.i;
                break;
            case 9:
                intBrasao = R.drawable.j;
                break;
            case 10:
                intBrasao = R.drawable.k;
                break;
            case 11:
                intBrasao = R.drawable.l;
                break;
            case 12:
                intBrasao = R.drawable.m;
                break;
            case 13:
                intBrasao = R.drawable.n;
                break;
            case 14:
                intBrasao = R.drawable.o;
                break;
            case 15:
                intBrasao = R.drawable.p;
                break;
            case 16:
                intBrasao = R.drawable.q;
                break;
            case 17:
                intBrasao = R.drawable.r;
                break;
            case 18:
                intBrasao = R.drawable.s;
                break;
            case 19:
                intBrasao = R.drawable.t;
                break;
            case 20:
                intBrasao = R.drawable.u;
                break;
            case 21:
                intBrasao = R.drawable.v;
                break;
            case 22:
                intBrasao = R.drawable.w;
                break;
            case 23:
                intBrasao = R.drawable.x;
                break;
            case 24:
                intBrasao = R.drawable.y;
                break;
            case 25:
                intBrasao = R.drawable.z;
                break;
            case 26:
                intBrasao = R.drawable.zz;
                break;
        }
        return intBrasao;
    }

    public class AdapterEstados extends RecyclerView.Adapter{

        String[] estados;
        String[] capitais;

        public AdapterEstados(String[] estados, String[] capitais){
            this.estados = estados;
            this.capitais = capitais;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.
                    from(getApplicationContext()).
                    inflate(R.layout.row_estados, parent, false);
            ViewHolderEstados viewHolderEstados = new ViewHolderEstados(view);
            return viewHolderEstados;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((ViewHolderEstados) holder).textViewEstado.setText(estados[position]);
            ((ViewHolderEstados) holder).textViewCapital.setText(capitais[position]);
            ((ViewHolderEstados) holder).textViewPopulation.setText(String.format("População: %s hab. (IBGE 2022)", populacao[position]));
            ((ViewHolderEstados) holder).textViewPIB.setText(String.format("PIB: R$ %s (IBGE 2021)", pib[position]));
            ((ViewHolderEstados) holder).imageViewBrasao.setImageResource(getBrasao(position));
            ((ViewHolderEstados) holder).constraintLayout.setVisibility(View.GONE);
        }

        @Override
        public int getItemCount() {
            return estados.length;
        }
    }

    public class ViewHolderEstados extends RecyclerView.ViewHolder {

        TextView textViewEstado;
        TextView textViewCapital;
        TextView textViewPopulation;
        TextView textViewPIB;
        ImageView imageViewBrasao;
        Button btnInfo;
        ConstraintLayout constraintLayout;

        public ViewHolderEstados(@NonNull View itemView) {
            super(itemView);
            textViewEstado = itemView.findViewById(R.id.textViewEstado);
            textViewCapital = itemView.findViewById(R.id.textViewCapital);
            textViewPopulation = itemView.findViewById(R.id.textViewPopulacao);
            textViewPIB = itemView.findViewById(R.id.textViewPIB);
            imageViewBrasao = itemView.findViewById(R.id.imageViewBrasao);
            btnInfo = itemView.findViewById(R.id.btnInfo);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);

            btnInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(constraintLayout.getVisibility() == View.VISIBLE) {
                        constraintLayout.setVisibility(View.GONE);
                    } else {
                        constraintLayout.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }
}

/*
Desafio, adicionar informações para cada estado;
 */