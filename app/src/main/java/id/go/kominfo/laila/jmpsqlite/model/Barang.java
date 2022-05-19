package id.go.kominfo.laila.jmpsqlite.model;

import java.io.Serializable;
import java.util.Objects;

public class Barang  implements Serializable {
    private int id;
    private String nama;
    private double harga;
    private double jumlah;

    public Barang(int id, String nama, double harga, double jumlah) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.jumlah = jumlah;
    }

    public Barang(String nama, double harga, double jumlah) {
        this.nama = nama;
        this.harga = harga;
        this.jumlah = jumlah;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public double getJumlah() {
        return jumlah;
    }

    public void setJumlah(double jumlah) {
        this.jumlah = jumlah;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Barang barang = (Barang) o;
        return id == barang.id && Double.compare(barang.harga, harga) == 0 && Double.compare(barang.jumlah, jumlah) == 0 && nama.equals(barang.nama);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nama, harga, jumlah);
    }

    @Override
    public String toString() {
        return "Barang{" +
                "id=" + id +
                ", nama='" + nama + '\'' +
                ", harga=" + harga +
                ", jumlah=" + jumlah +
                '}';
    }
}
