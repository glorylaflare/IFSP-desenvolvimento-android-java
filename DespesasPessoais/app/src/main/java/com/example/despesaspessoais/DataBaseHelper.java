package com.example.despesaspessoais;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DataBaseHelper {
    SQLiteDatabase database;
    Context context;
    public DataBaseHelper(Context context) {
        this.context = context;
    }

    public void criaBanco() {
        database = context.openOrCreateDatabase("banco_de_dados", context.MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS usuarios (id INTEGER PRIMARY KEY, nome VARCHAR, usuario VARCHAR, senha VARCHAR)");
        database.execSQL("CREATE TABLE IF NOT EXISTS movimentos (id INTEGER PRIMARY KEY, data VARCHAR, valor VARCHAR, descricao VARCHAR, tipo VARCHAR)");
        database.close();
    }

    public int consultaUsuario(String strUsuario, String strSenha) {
        int usuarios = 0;
        database = context.openOrCreateDatabase("banco_de_dados", context.MODE_PRIVATE, null);
        String selectQuery = "SELECT usuario, senha FROM usuarios WHERE usuario = ? AND senha = ?";
        @SuppressLint("Recycle")
        Cursor cursor = database.rawQuery(selectQuery, new String[]{strUsuario, strSenha});

        if(cursor.moveToFirst()) {
            usuarios = cursor.getCount();
        }
        database.close();

        return usuarios;
    }

    public boolean inserirUsuario(String strNome, String strUsuario, String strSenha) {
        boolean operacao = false;
        try {
            database = context.openOrCreateDatabase("banco_de_dados", context.MODE_PRIVATE, null);
            database.execSQL("INSERT INTO usuarios (nome, usuario, senha) VALUES ('"+strNome+"','"+strUsuario+"','"+strSenha+"')");
            database.close();
            operacao = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return operacao;
    }

    public boolean inserirLancamento(String strDataFormatoBanco, String strValor, String strDescricao, String strTipo) {
        boolean operacao = false;
        try {
            database = context.openOrCreateDatabase("banco_de_dados", context.MODE_PRIVATE, null);
            database.execSQL("INSERT INTO movimentos (data, valor, descricao, tipo) VALUES ('"+strDataFormatoBanco+"','"+strValor+"','"+strDescricao+"','"+strTipo+"')");
            database.close();
            operacao = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return operacao;
    }

    public ArrayList<MovimentoObj> carregaMovimentos() {
        ArrayList<MovimentoObj> lista = null;
        try {
            database = context.openOrCreateDatabase("banco_de_dados", context.MODE_PRIVATE, null);
            String selectQuery = "SELECT id, data, valor, descricao, tipo FROM movimentos ORDER BY data DESC";
            Cursor cursor = database.rawQuery(selectQuery, null);
            lista = new ArrayList<MovimentoObj>();
            if(cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(0);
                    String data = cursor.getString(1);
                    String valor = cursor.getString(2);
                    String descricao = cursor.getString(3);
                    String tipo = cursor.getString(4);

                    MovimentoObj movimentoObj = new MovimentoObj(id, data, valor, descricao, tipo);
                    lista.add(movimentoObj);
                } while (cursor.moveToNext());
            }
            database.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public String totalMovimentos(String strTipo) {
        String total = "";
        try {
            database = context.openOrCreateDatabase("banco_de_dados", context.MODE_PRIVATE, null);
            String selectQuery = "SELECT tipo, SUM(valor) FROM movimentos WHERE tipo = ?";
            Cursor cursor = database.rawQuery(selectQuery, new String[]{strTipo});
            cursor.moveToFirst();
            total = cursor.getString(1);
            database.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    public boolean excluirLancamento(int id) {
        boolean operacao = false;
        try {
            database = context.openOrCreateDatabase("banco_de_dados", context.MODE_PRIVATE, null);
            database.execSQL("DELETE FROM movimentos WHERE id = '" + id + "'");
            database.close();
            operacao = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return operacao;
    }

    public boolean atualizarLancamento(String strDataFormatoBanco, String strValor, String strDescricao, String strTipo, int id) {
        boolean operacao = false;
        try {
            database = context.openOrCreateDatabase("banco_de_dados", context.MODE_PRIVATE, null);
            database.execSQL("UPDATE movimentos SET data =?, valor =?, descricao =?, tipo =? WHERE id =?",
                    new String[]{strDataFormatoBanco, strValor, strDescricao, strTipo, String.valueOf(id)});
            database.close();
            operacao = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return operacao;
    }
}
