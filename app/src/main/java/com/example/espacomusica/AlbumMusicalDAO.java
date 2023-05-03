package com.example.espacomusica;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class AlbumMusicalDAO {
    private Conexao conexao;
    private SQLiteDatabase banco;

    public AlbumMusicalDAO (Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    public long inserir(AlbumMusical albumMusical){
        ContentValues values = new ContentValues();
        values.put("banda", albumMusical.getBanda() );
        values.put("album", albumMusical.getAlbum());
        values.put("ano", albumMusical.getAno());
        values.put("pais", albumMusical.getPais());
        return banco.insert("album_musical", null, values);
    }

    public List<AlbumMusical> obterTodos(){
        List<AlbumMusical> albuns = new ArrayList<>();
        Cursor cursor = banco.query("album_musical", new String[]{"id", "banda", "album", "ano", "pais"}, null,
                null, null, null, null);

        while (cursor.moveToNext()){
            AlbumMusical a = new AlbumMusical();
            a.setId(cursor.getInt(0));
            a.setBanda(cursor.getString(1));
            a.setAlbum(cursor.getString(2));
            a.setAno(cursor.getString(3));
            a.setPais(cursor.getString(4));
            albuns.add(a);
        }
        return albuns;
    }
    public void excluir (AlbumMusical a){
        banco.delete("album_musical", "id = ?", new String[] {a.getId().toString()});
    }

    public void atualizar (AlbumMusical albumMusical){
        ContentValues values = new ContentValues();
        values.put("banda", albumMusical.getBanda());
        values.put("album", albumMusical.getAlbum());
        values.put("ano", albumMusical.getAno());
        values.put("pais", albumMusical.getPais());
        banco.update("album_musical", values, "id = ?", new String[]{albumMusical.getId().toString()});
    }

}


























