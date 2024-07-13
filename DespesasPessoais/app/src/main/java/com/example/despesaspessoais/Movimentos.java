package com.example.despesaspessoais;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.despesaspessoais.databinding.ActivityMovimentosBinding;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Movimentos extends AppCompatActivity {

    private ActivityMovimentosBinding binding;
    RecyclerView recyclerViewMovimentos;
    TextView textViewEntradas, textViewSaidas, textViewTotal;
    ArrayList<MovimentoObj> movimentoObjs;
    DataBaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovimentosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());

        recyclerViewMovimentos = findViewById(R.id.rcwMovimentos);
        textViewEntradas = findViewById(R.id.tvwEntradas);
        textViewSaidas = findViewById(R.id.tvwSaidas);
        textViewTotal = findViewById(R.id.tvwTotal);

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Movimentos.this, Lancamento.class);
                startActivity(i);
            }
        });
        helper = new DataBaseHelper(getApplicationContext());
        carregaMovimentos();
    }

    private void carregaMovimentos() {
        movimentoObjs = new ArrayList<MovimentoObj>();
        movimentoObjs = helper.carregaMovimentos();
        recyclerViewMovimentos.setAdapter(null);
        if(movimentoObjs.size() > 0) {
            recyclerViewMovimentos.setAdapter(new AdapterMovimentos(movimentoObjs));
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerViewMovimentos.setLayoutManager(layoutManager);
        }

        String strTotalEntradas = helper.totalMovimentos("Entrada");
        double dblEntradas = 0.0;
        try {
            dblEntradas = Double.parseDouble(strTotalEntradas);
            textViewEntradas.setText("Entradas: " + new DecimalFormat("R$ #,##0.00").format(dblEntradas));
        } catch (Exception e) {
            e.printStackTrace();
            textViewEntradas.setText("Entradas: R$ 0,00");
        }

        String strTotalSaidas = helper.totalMovimentos("Saída");
        double dblSaidas = 0.0;
        try {
            dblSaidas = Double.parseDouble(strTotalSaidas);
            textViewSaidas.setText("Saídas: " + new DecimalFormat("R$ #,##0.00").format(dblSaidas));
        } catch (Exception e) {
            e.printStackTrace();
            textViewSaidas.setText("Saídas: R$ 0,00");
        }

        textViewTotal.setText("Saldo: " + new DecimalFormat("R$ #,##0.00").format(dblEntradas - dblSaidas));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_movimentos, menu);
        return true;
    }

    public class AdapterMovimentos extends RecyclerView.Adapter {

        ArrayList<MovimentoObj> movimentoObjs;
        public AdapterMovimentos(ArrayList<MovimentoObj> movimentoObjs) {
            this.movimentoObjs = movimentoObjs;
        }
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.row_movimentos, parent, false);
            ViewHolderMovimentos viewHolderMovimentos = new ViewHolderMovimentos(view);
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            ((ViewHolderMovimentos) holder).textViewDescricao.setText(movimentoObjs.get(position).getDescricao());

            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = null;
            try {
                date = inputFormat.parse(movimentoObjs.get(position).getData());
                String dataFormatada = outputFormat.format(date);
                ((ViewHolderMovimentos) holder).textViewData.setText(dataFormatada);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            double doubleValor = Double.parseDouble(movimentoObjs.get(position).getValor());
            ((ViewHolderMovimentos) holder).textViewValor.setText(new DecimalFormat("R$ #,##0.00").format(doubleValor));

            String strTipo = movimentoObjs.get(position).getTipo();
            if(strTipo.equals("Saída")){
                ((ViewHolderMovimentos) holder).imageViewTipo.setImageResource(android.R.drawable.button_onoff_indicator_off);
            } else {
                ((ViewHolderMovimentos) holder).imageViewTipo.setImageResource(android.R.drawable.ic_input_add);
            }
                ((ViewHolderMovimentos) holder).imageButtonEditar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Movimentos.this, Lancamento.class);
                        i.putExtra("id", movimentoObjs.get(position).getId());
                        i.putExtra("data", movimentoObjs.get(position).getData());
                        i.putExtra("valor", movimentoObjs.get(position).getValor());
                        i.putExtra("descricao", movimentoObjs.get(position).getDescricao());
                        i.putExtra("tipo", movimentoObjs.get(position).getTipo());
                        startActivity(i);
                    }
                });
                ((ViewHolderMovimentos) holder).imageButtonExcluir.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(Movimentos.this);
                        dialog.setTitle("Confirma a exclusão?");
                        dialog.setMessage("Deseja excluir o lançamento " + movimentoObjs.get(position).getDescricao() + "?");
                        dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                
                            }
                        });
                        dialog.setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(helper.excluirLancamento(movimentoObjs.get(position).getId())) {
                                    carregaMovimentos();
                                    Toast.makeText(Movimentos.this, "Lançamento excluído com sucesso", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Movimentos.this, "Falha ao excluir lançamento", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        dialog.show();
                    }
                });
        }

        @Override
        public int getItemCount() {
            return movimentoObjs.size();
        }
    }

    public class ViewHolderMovimentos extends RecyclerView.ViewHolder {

        TextView textViewData, textViewValor, textViewDescricao;
        ImageView imageViewTipo;
        ImageButton imageButtonEditar, imageButtonExcluir;

        public ViewHolderMovimentos(@NonNull View itemView) {
            super(itemView);
            textViewData = itemView.findViewById(R.id.tvwData);
            textViewValor = itemView.findViewById(R.id.tvwValor);
            textViewDescricao = itemView.findViewById(R.id.tvwDescricao);

            imageViewTipo = itemView.findViewById(R.id.imgTipo);
            imageButtonEditar = itemView.findViewById(R.id.btnEditar);
            imageButtonExcluir = itemView.findViewById(R.id.btnExcluir);

            imageButtonEditar.setImageResource(android.R.drawable.ic_menu_edit);
            imageButtonExcluir.setImageResource(android.R.drawable.ic_menu_delete);
        }
    }

    @Override
    protected void onResume() {
        carregaMovimentos();
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logoff) {
            SharedPreferences preferences = getSharedPreferences("Arquivo_preferencias", 0);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("usuario");
            editor.remove("senha");
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}