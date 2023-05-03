package com.example.espacomusica;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class ListarAlbuns extends AppCompatActivity {
    private ListView listView;
    private AlbumMusicalDAO dao;
    private List<AlbumMusical> albumMusical;
    private List<AlbumMusical> albunsFiltrados = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_albuns);

        listView = findViewById(R.id.lista_albuns);
        dao = new AlbumMusicalDAO(this);
        albumMusical = dao.obterTodos();
        albunsFiltrados.addAll(albumMusical);
        AlbumAdapter adaptador = new AlbumAdapter(this, albunsFiltrados);
        listView.setAdapter(adaptador);
        registerForContextMenu(listView);
    }
    public boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_principal, menu);

        SearchView sv= (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                procuraAlbuns(s);
                return false;
            }
        });
        return true;
    }
    public void onCreateContextMenu(android.view.ContextMenu menu, android.view.View v, android.view.ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_contexto, menu);
    }
    public void procuraAlbuns(String nome){
        albunsFiltrados.clear();
        for(AlbumMusical a : albumMusical){
            if(a.getBanda().toLowerCase().contains(nome.toLowerCase())){
                albunsFiltrados.add(a);
            }
        }
        listView.invalidateViews();
    }
    public void excluir(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final AlbumMusical albumExcluir = albunsFiltrados.get(menuInfo.position);
        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("Atenção")
                .setMessage("Deseja excluir o album?")
                .setNegativeButton("Não", null)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        albunsFiltrados.remove(albumExcluir);
                        albumMusical.remove(albumExcluir);
                        dao.excluir(albumExcluir); //exclui do bd
                        listView.invalidateViews();
                    }
                }).create();
        dialog.show();
    }
    public void cadastrar(MenuItem item) {
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
    }

    public void atualizar(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final AlbumMusical albumAtualizar = albunsFiltrados.get(menuInfo.position);
        Intent it = new Intent(this, MainActivity.class);
        it.putExtra("albumMusical1", albumAtualizar);
        startActivity(it);
    }
    @Override
    public void onResume(){
        super.onResume();
        albumMusical = dao.obterTodos();
        albunsFiltrados.clear();
        albunsFiltrados.addAll(albumMusical);
        listView.invalidateViews();
    }
}