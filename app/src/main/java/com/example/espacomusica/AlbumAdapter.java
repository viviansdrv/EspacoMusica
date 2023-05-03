package com.example.espacomusica;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AlbumAdapter extends BaseAdapter{

    private List<AlbumMusical> albumMusical;
    private Activity activity;

    public AlbumAdapter(Activity activity, List<AlbumMusical> albumMusical) {
        this.activity = activity;
        this.albumMusical = albumMusical;
    }

    @Override
    public int getCount() { return albumMusical.size(); }

    @Override
    public Object getItem(int i) { return albumMusical.get(i); }

    @Override
    public long getItemId(int i) {
        return albumMusical.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View viewAdapter = activity.getLayoutInflater().inflate(R.layout.item, viewGroup, false);

        TextView banda = viewAdapter.findViewById(R.id.txt_banda);
        TextView album = viewAdapter.findViewById(R.id.txt_album);
        TextView ano = viewAdapter.findViewById(R.id.txt_ano);
        TextView pais = viewAdapter.findViewById(R.id.txt_pais);

        AlbumMusical a = albumMusical.get(i);
        banda.setText(a.getBanda());
        album.setText(a.getAlbum());
        ano.setText(a.getAno());
        pais.setText(a.getPais());

        return viewAdapter;
    }

}
