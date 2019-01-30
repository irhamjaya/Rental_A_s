package com.hino.rental;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditData extends Activity implements OnClickListener{

    private DBDataSource dataSource;

    private long id;
    private String harga;
    private String stok;
    private String nama;

    private EditText edNama;
    private EditText edStok;
    private EditText edBiaya;

    private TextView txId;

    private Button btnSave;
    private Button btnCancel;

    private Rental barang;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);
        //inisialisasi variabel
        edNama = (EditText) findViewById(R.id.editText_nama);
        edStok = (EditText) findViewById(R.id.editText_harga);
        edBiaya = (EditText) findViewById(R.id.editText_stok);
        txId = (TextView) findViewById(R.id.text_id_barang);
        //buat sambungan baru ke database
        dataSource = new DBDataSource(this);
        dataSource.open();
        // ambil data barang dari extras
        Bundle bun = this.getIntent().getExtras();
        id = bun.getLong("id");
        harga = bun.getString("harga");
        stok = bun.getString("stok");
        nama = bun.getString("nama");
        //masukkan data-data barang tersebut ke field editor
        txId.append(String.valueOf(id));
        edNama.setText(nama);
        edStok.setText(harga);
        edBiaya.setText(stok);

        //set listener pada tombol
        btnSave = (Button) findViewById(R.id.button_save_update);
        btnSave.setOnClickListener(this);
        btnCancel = (Button) findViewById(R.id.button_cancel_update);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch(v.getId())
        {
            // apabila tombol save diklik (update barang)
            case R.id.button_save_update :
                barang = new Rental();
                barang.setbiaya(edStok.getText().toString());
                barang.setnama_mobil(edNama.getText().toString());
                barang.setstok(edBiaya.getText().toString());
                barang.setId(id);
                dataSource.updateMobil(barang);
                Intent i = new Intent(this, ViewData.class);
                startActivity(i);
                EditData.this.finish();
                dataSource.close();
                break;
            // apabila tombol cancel diklik, finish activity
            case R.id.button_cancel_update :
                Intent i2 = new Intent(this, ViewData.class);
                startActivity(i2);
                finish();
                dataSource.close();
                break;
        }
    }
}