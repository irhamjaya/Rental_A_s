package com.hino.rental;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBDataSource {

    //inisialiasi SQLite Database
    private SQLiteDatabase database;

    //inisialisasi kelas DBHelper
    private DBHelper dbHelper;

    //ambil semua nama kolom
    private String[] allColumns = { DBHelper.COLUMN_ID,
            DBHelper.COLUMN_NAMA, DBHelper.COLUMN_STOK,DBHelper.COLUMN_BIAYA};

    //DBHelper diinstantiasi pada constructor
    public DBDataSource(Context context)
    {
        dbHelper = new DBHelper(context);
    }

    //membuka/membuat sambungan baru ke database
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    //menutup sambungan ke database
    public void close() {
        dbHelper.close();
    }

    //method untuk create/insert barang ke database
    public Rental createMobil(String nama, String stok, String harga) {

        // membuat sebuah ContentValues, yang berfungsi
        // untuk memasangkan data dengan nama-nama
        // kolom pada database
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_NAMA, nama);
        values.put(DBHelper.COLUMN_STOK, stok);
        values.put(DBHelper.COLUMN_BIAYA, harga);

        // mengeksekusi perintah SQL insert data
        // yang akan mengembalikan sebuah insert ID
        long insertId = database.insert(DBHelper.TABLE_NAME, null,
                values);

        // setelah data dimasukkan, memanggil
        // perintah SQL Select menggunakan Cursor untuk
        // melihat apakah data tadi benar2 sudah masuk
        // dengan menyesuaikan ID = insertID
        Cursor cursor = database.query(DBHelper.TABLE_NAME,
                allColumns, DBHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);

        // pindah ke data paling pertama
        cursor.moveToFirst();

        // mengubah objek pada kursor pertama tadi
        // ke dalam objek barang
        Rental newMobil = cursorToMobil(cursor);

        //close cursor
        cursor.close();

        //mengembalikan barang baru
        return newMobil;
    }

    private Rental cursorToMobil(Cursor cursor)
    {
        // buat objek barang baru
        Rental barang = new Rental();
        // debug LOGCAT
        //Log.v("info", "The getLONG "+cursor.getLong(0));
        //Log.v("info", "The setLatLng "+cursor.getString(1)+","+cursor.getString(2));

        // Set atribut pada objek barang dengan
        // data kursor yang diambil dari database
        barang.setId(cursor.getLong(0));
        barang.setnama_mobil(cursor.getString(1));
        barang.setstok(cursor.getString(2));
        barang.setbiaya(cursor.getString(3));

        //kembalikan sebagai objek barang
        return barang;
    }

    //mengambil semua data barang
    public ArrayList<Rental> getAllMobil() {
        ArrayList<Rental> daftarMobil = new ArrayList<Rental>();

        // select all SQL query

        Cursor cursor = database.query(DBHelper.TABLE_NAME,
                allColumns, null, null, null, null, null);

        // pindah ke data paling pertama
        cursor.moveToFirst();
        // jika masih ada data, masukkan data barang ke
        // daftar barang
        while (!cursor.isAfterLast()) {
            Rental barang = cursorToMobil(cursor);
            daftarMobil.add(barang);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return daftarMobil;
    }

    //ambil satu barang sesuai id
    public ArrayList<Rental> getAllMobilFilter(String nama) {
        ArrayList<Rental> daftarMobil = new ArrayList<Rental>();

        // select all SQL query

        Cursor cursor = database.query(DBHelper.TABLE_NAME,
                allColumns,  "nama_mobil like '%"+nama+"%'", null, null, null, null);

        // pindah ke data paling pertama
        cursor.moveToFirst();
        // jika masih ada data, masukkan data barang ke
        // daftar barang
        while (!cursor.isAfterLast()) {
            Rental barang = cursorToMobil(cursor);
            daftarMobil.add(barang);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return daftarMobil;
    }

    //ambil satu barang sesuai id
    public Rental getMobil(long id)
    {
        Rental barang = new Rental(); //inisialisasi barang
        //select query
        Cursor cursor = database.query(DBHelper.TABLE_NAME, allColumns, "_id ="+id, null, null, null, null);
        //ambil data yang pertama
        cursor.moveToFirst();
        //masukkan data cursor ke objek barang
        barang = cursorToMobil(cursor);
        //tutup sambungan
        cursor.close();
        //return barang
        return barang;
    }

    //update barang yang diedit
    public void updateMobil(Rental b)
    {
        //ambil id barang
        String strFilter = "_id=" + b.getId();
        //memasukkan ke content values
        ContentValues args = new ContentValues();
        //masukkan data sesuai dengan kolom pada database
        args.put(DBHelper.COLUMN_NAMA, b.getnama_mobil());
        args.put(DBHelper.COLUMN_STOK, b.getstok());
        args.put(DBHelper.COLUMN_BIAYA, b.getbiaya());
        //update query
        database.update(DBHelper.TABLE_NAME, args, strFilter, null);
    }

    // delete barang sesuai ID
    public void deleteMobil(long id)
    {
        String strFilter = "_id=" + id;
        database.delete(DBHelper.TABLE_NAME, strFilter, null);
    }
}