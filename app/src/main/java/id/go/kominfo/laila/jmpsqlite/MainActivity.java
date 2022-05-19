package id.go.kominfo.laila.jmpsqlite;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import id.go.kominfo.laila.jmpsqlite.adapter.BarangAdapter;
import id.go.kominfo.laila.jmpsqlite.helper.BarangDB;
import id.go.kominfo.laila.jmpsqlite.model.Barang;

public class MainActivity extends AppCompatActivity {
    public static final int MODE_VIEW = 0;
    public static final int MODE_ADD = 1;

    private final List<Barang> listBarang = new ArrayList<>();
    private BarangAdapter adapter;
    private BarangDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle("Demo SQLite - JMP");

        //buat object listview
        ListView listView = findViewById(R.id.listview);
        //buat object BarangAdapter
        adapter = new BarangAdapter(this, listBarang);
        //pasang adapter untuk listview
        listView.setAdapter(adapter);

        //pasang listener pada listview
        listView.setOnItemClickListener(this::tampilBarang);
        listView.setOnItemLongClickListener(this::hapusBarang);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this::tambahBarang);

        //buat object dari kelas BarangDB (SQLiteOpenHelper)
        db = new BarangDB(this);
    }

    private boolean hapusBarang(AdapterView<?> adapterView, View view, int i, long l) {
        //ambil data barang yang mau dihapus
        Barang barang = (Barang) adapterView.getAdapter().getItem(i);

        new AlertDialog.Builder(this)
                .setTitle("Konfirmasi")
                .setMessage(String.format("Hapus Barang %s ?", barang.getNama()))
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", (dialog, which) -> {
                    listBarang.remove(barang);
                    db.delete(barang.getId());
                    adapter.notifyDataSetChanged();
                })
                .show();

        return true;
    }

    private void tampilBarang(AdapterView<?> adapterView, View view, int i, long l) {
        //ambil data barang yang mau ditampilkan
        Barang barang = (Barang) adapterView.getAdapter().getItem(i);

        //tampilkan AddAndViewActivity dalam mode tampil data
        Intent intent = new Intent(this, AddAndViewActivity.class);
        intent.putExtra("mode", MODE_VIEW);
        intent.putExtra("barang", barang);
        startActivity(intent);
    }

    private void tambahBarang(View view) {
        //tampilkan AddAndViewActivity dalam mode tambah data
        Intent intent = new Intent(this, AddAndViewActivity.class);
        intent.putExtra("mode", MODE_ADD);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //kosongkan listBarang
        listBarang.clear();
        //load data barang dari table ke dalam listBarang
        listBarang.addAll(db.getAllBarang());
        //tampilkan perubahan data di listView
        adapter.notifyDataSetChanged();
    }
}