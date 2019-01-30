package com.hino.rental;

public class Rental {

    private long id;
    private String nama_mobil;
    private String stok;
    private String biaya;

    public Rental()
    {

    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the nama_mobil
     */
    public String getnama_mobil() {
        return nama_mobil;
    }

    /**
     * @param nama_mobil the nama_mobil to set
     */
    public void setnama_mobil(String nama_mobil) {
        this.nama_mobil = nama_mobil;
    }

    /**
     * @return the stok
     */
    public String getstok() {
        return stok;
    }

    /**
     * @param stok the stok to set
     */
    public void setstok(String stok) {
        this.stok = stok;
    }

    /**
     * @return the biaya
     */
    public String getbiaya() {
        return biaya;
    }

    /**
     * @param biaya the biaya to set
     */
    public void setbiaya(String biaya) {
        this.biaya = biaya;
    }

    @Override
    public String toString()
    {
        return "Nama: \t"+ nama_mobil + "\n" + "Biaya:\t\t" + stok + "\n" + "Stok: \t" + biaya;
    }
}