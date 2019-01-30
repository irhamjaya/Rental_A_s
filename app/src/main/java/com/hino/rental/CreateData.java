package com.hino.rental;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class CreateData extends Activity implements OnClickListener{

    //inisilisasi elemen-elemen pada layout
    private Button buttonSubmit;
    private EditText edNama;
    private EditText edBiaya;
    private EditText edStok;

    //inisialisasi kontroller/Data Source
    private DBDataSource dataSource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_data);

        buttonSubmit = (Button) findViewById(R.id.buttom_submit);
        buttonSubmit.setOnClickListener(this);
        edNama = (EditText) findViewById(R.id.nama_mobil);
        edStok = (EditText) findViewById(R.id.biaya);
        edBiaya = (EditText) findViewById(R.id.stok);

        // instanstiasi kelas DBDataSource
        dataSource = new DBDataSource(this);

        //membuat sambungan baru ke database
        dataSource.open();
    }

    //KETIKA Tombol Submit Diklik
    @Override
    public void onClick(View v) {
        // Inisialisasi data barang
        String nama = null;
        String merk = null;
        String harga = null;
        @SuppressWarnings("unused")

        //inisialisasi barang baru (masih kosong)
                Rental barang = null;
        if(edNama.getText()!=null && edBiaya.getText()!=null && edStok.getText()!=null)
        {
            /* jika field nama, merk, dan harga tidak kosong
             * maka masukkan ke dalam data barang*/
            nama = edNama.getText().toString();
            merk = edBiaya.getText().toString();
            harga = edStok.getText().toString();
        }

        switch(v.getId())
        {
            case R.id.buttom_submit:
                // insert data barang baru
                barang = dataSource.createMobil(nama, merk, harga);

                //konfirmasi kesuksesan
                Toast.makeText(this, "Mobil berhasil di tambahkan\n" +
                        "nama" + barang.getnama_mobil() +
                        "merk" + barang.getstok() +
                        "harga" + barang.getbiaya(), Toast.LENGTH_LONG).show();
                Intent i = new Intent(this, ViewData.class);
                startActivity(i);
                break;
        }

    }
}