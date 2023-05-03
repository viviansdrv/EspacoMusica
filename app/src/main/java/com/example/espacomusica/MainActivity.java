package com.example.espacomusica;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {

    private EditText banda;
    private EditText album;
    private EditText ano;
    private EditText pais;

    private AlbumMusicalDAO dao;

    private AlbumMusical albumMusical = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        banda = findViewById(R.id.editBanda);
        album = findViewById(R.id.editAlbum);
        ano = findViewById(R.id.editAno);
        pais = findViewById(R.id.editPais);
        dao = new AlbumMusicalDAO(this);

        Intent it = getIntent();
        if (it.hasExtra("albumMusical1")) {
            albumMusical = (AlbumMusical) it.getSerializableExtra("albumMusical1");
            banda.setText(albumMusical.getBanda());
            album.setText(albumMusical.getAlbum());
            ano.setText(albumMusical.getAno());
            pais.setText(albumMusical.getPais());
        }
    }

    public void salvar (View view) {
        if (albumMusical == null) {
            AlbumMusical albumMusical = new AlbumMusical();
            albumMusical.setBanda(banda.getText().toString());
            albumMusical.setAlbum(album.getText().toString());
            albumMusical.setAno(ano.getText().toString());
            albumMusical.setPais(pais.getText().toString());
            long id = dao.inserir(albumMusical);
            Toast.makeText(this, "Álbum inserido com id: " + id, Toast.LENGTH_SHORT).show();
        } else {
            albumMusical.setBanda(banda.getText().toString());
            albumMusical.setAlbum(album.getText().toString());
            albumMusical.setAno(ano.getText().toString());
            albumMusical.setPais(pais.getText().toString());
            dao.atualizar(albumMusical);
            Toast.makeText(this, "Atualizado com sucesso", Toast.LENGTH_SHORT).show();
        }
    }
}
