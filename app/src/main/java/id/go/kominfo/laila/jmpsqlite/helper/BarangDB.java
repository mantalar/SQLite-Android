package id.go.kominfo.laila.jmpsqlite.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import id.go.kominfo.laila.jmpsqlite.dao.BarangDao;
import id.go.kominfo.laila.jmpsqlite.model.Barang;

public class BarangDB extends SQLiteOpenHelper implements BarangDao {
    private static final String DBNAME = "inventaris.db";
    private static final int DBVERSION = 1;

    public BarangDB(@Nullable Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists barang(" +
                "id integer primary key autoincrement," +
                "nama text," +
                "harga decimal," +
                "jumlah decimal)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL("drop table if exists barang");
            onCreate(db);
        }
    }

    @Override
    public void insert(Barang barang) {
        String sql = "insert into barang values(?,?,?,?)";
        getWritableDatabase().execSQL(sql, new Object[]{
                null,
                barang.getNama(),
                barang.getHarga(),
                barang.getJumlah()
        });
    }

    @Override
    public void update(Barang barang) {
        String sql = "update barang set nama = ?, harga = ?, jumlah = ? where id = ?";
        getWritableDatabase().execSQL(sql, new Object[]{
                barang.getNama(),
                barang.getHarga(),
                barang.getJumlah(),
                barang.getId()
        });
    }

    @Override
    public void delete(int id) {
        String sql = "delete from barang where id = ?";
        getWritableDatabase().execSQL(sql, new Object[]{id});
    }

    @Override
    public Barang getBarangById(int id) {
        Barang result = null;
        String sql = "select * from barang where id = ?";
        Cursor cursor = getReadableDatabase().rawQuery(sql, new String[]{String.valueOf(id)});
        if(cursor.moveToFirst())
            result = new Barang(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getDouble(2),
                    cursor.getDouble(3)
            );

        cursor.close();
        return result;
    }

    @Override
    public List<Barang> getAllBarang() {
        List<Barang> result = new ArrayList<>();
        String sql = "select * from barang";
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        while(cursor.moveToNext())
            result.add(new Barang(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getDouble(2),
                    cursor.getDouble(3)
            ));

        cursor.close();
        return result;
    }
}
