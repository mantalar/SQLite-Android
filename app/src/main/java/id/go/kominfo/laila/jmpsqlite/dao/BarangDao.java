package id.go.kominfo.laila.jmpsqlite.dao;

import java.util.List;

import id.go.kominfo.laila.jmpsqlite.model.Barang;

public interface BarangDao {
    void insert(Barang barang);
    void update(Barang barang);
    void delete(int id);
    Barang getBarangById(int id);
    List<Barang> getAllBarang();
}
