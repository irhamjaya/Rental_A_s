package com.hino.rental;

import java.util.ArrayList;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ViewData extends ListActivity implements OnItemLongClickListener {

    //inisialisasi kontroller
    private DBDataSource dataSource;

    //inisialisasi arraylist
    private ArrayList<Rental> values;
    private Button editButton;
    private Button delButton;
    private Button btnCari;
    EditText mEdit;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        dataSource = new DBDataSource(this);
        // buka kontroller
        dataSource.open();

        // ambil semua data barang
        values = dataSource.getAllMobil();

        // masukkan data barang ke array adapter
        ArrayAdapter<Rental> adapter = new ArrayAdapter<Rental>(this,
                android.R.layout.simple_list_item_1, values);

        // set adapter pada list
        setListAdapter(adapter);

        // mengambil listview untuk diset onItemLongClickListener
        ListView lv = (ListView) findViewById(android.R.id.list);
        lv.setOnItemLongClickListener(this);

        btnCari = (Button) findViewById(R.id.cari);
        mEdit   = (EditText)findViewById(R.id.edittext);

        btnCari.setOnClickListener(new View.OnClickListener() {
                public void onClick (View v){
                dataSource = new DBDataSource(ViewData.this);
                // buka kontroller
                dataSource.open();
                    Log.v("EditText", mEdit.getText().toString());

                // ambil semua data barang
                values = dataSource.getAllMobilFilter(mEdit.getText().toString());

                // masukkan data barang ke array adapter
                ArrayAdapter<Rental> adapter = new ArrayAdapter<Rental>(ViewData.this,
                        android.R.layout.simple_list_item_1, values);

                // set adapter pada list
                setListAdapter(adapter);

                // mengambil listview untuk diset onItemLongClickListener
                ListView lv = (ListView) findViewById(android.R.id.list);
                lv.setOnItemLongClickListener(ViewData.this);
            }
        });
    }





    //apabila ada long click
    @Override
    public boolean onItemLongClick(final AdapterView<?> adapter, View v, int pos,
                                   final long id) {

        //tampilkan alert dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_dialog_view);
        dialog.setTitle("Pilih Aksi");
        dialog.show();
        final Rental b = (Rental) getListAdapter().getItem(pos);
        editButton = (Button) dialog.findViewById(R.id.button_edit_data);
        delButton = (Button) dialog.findViewById(R.id.button_delete_data);

        //apabila tombol edit diklik
        editButton.setOnClickListener(
                new OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        switchToEdit(b.getId());
                        dialog.dismiss();
                    }
                }
        );

        //apabila tombol delete di klik
        delButton.setOnClickListener(
                new OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        // Delete barang
                        dataSource.deleteMobil(b.getId());
                        dialog.dismiss();
                        finish();
                        startActivity(getIntent());
                    }
                }
        );

        return true;

    }

    public void switchToEdit(long id)
    {
        Rental b = dataSource.getMobil(id);
        Intent i = new Intent(this, EditData.class);
        Bundle bun = new Bundle();
        bun.putLong("id", b.getId());
        bun.putString("nama", b.getnama_mobil());
        bun.putString("stok", b.getstok());
        bun.putString("harga", b.getbiaya());
        i.putExtras(bun);
        finale();
        startActivity(i);
    }

    public void finale()
    {
        ViewData.this.finish();
        dataSource.close();
    }
    @Override
    protected void onResume() {
        dataSource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        dataSource.close();
        super.onPause();
    }

}