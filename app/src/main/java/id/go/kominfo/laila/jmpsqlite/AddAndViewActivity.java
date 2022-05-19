package id.go.kominfo.laila.jmpsqlite;

import static id.go.kominfo.laila.jmpsqlite.MainActivity.MODE_ADD;
import static id.go.kominfo.laila.jmpsqlite.MainActivity.MODE_VIEW;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import id.go.kominfo.laila.jmpsqlite.helper.BarangDB;
import id.go.kominfo.laila.jmpsqlite.model.Barang;

public class AddAndViewActivity extends AppCompatActivity {
    private TextInputEditText tieNama;
    private TextInputEditText tieHarga;
    private TextInputEditText tieJumlah;
    private Button btSimpan;

    private Barang tempBarang;
    private int mode = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_and_view);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tieNama = findViewById(R.id.tie_nama);
        tieHarga = findViewById(R.id.tie_harga);
        tieJumlah = findViewById(R.id.tie_jumlah);

        btSimpan = findViewById(R.id.bt_simpan);
        btSimpan.setOnClickListener(this::simpanBarang);
    }

    private void simpanBarang(View view) {
        if (tieNama.getText().toString().isEmpty()) {
            Toast.makeText(this, "Nama Barang tidak boleh kosong",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Barang barang = new Barang(
                tieNama.getText().toString(),
                Double.parseDouble(tieHarga.getText().toString()),
                Double.parseDouble(tieJumlah.getText().toString())
        );

        new BarangDB(this).insert(barang);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        assert getSupportActionBar() !=null;

        mode = getIntent().getIntExtra("mode", 0);

        if (mode == MODE_VIEW) { //mode tampil
            getSupportActionBar().setTitle("View Data");

            Barang barang = (Barang) getIntent().getSerializableExtra("barang");

            tieNama.setText(barang.getNama());
            tieHarga.setText(String.valueOf(barang.getHarga()));
            tieJumlah.setText(String.valueOf(barang.getJumlah()));

            tempBarang = (Barang) getIntent().getSerializableExtra("barang");

            //sembunyikan tombol simpan
            btSimpan.setVisibility(View.GONE);
        }
        else if(mode == MODE_ADD){
            getSupportActionBar().setTitle("Add Data");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        int id = tempBarang.getId();
        Barang barang = new Barang(
                id,
                tieNama.getText().toString(),
                Double.parseDouble(tieHarga.getText().toString()),
                Double.parseDouble(tieJumlah.getText().toString())
        );

        if (mode == MODE_ADD) {
            super.onBackPressed();
        }
        else if(!tempBarang.equals(barang)){
            new AlertDialog.Builder(this)
                    .setTitle("Konfirmasi Perubahan Data")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setMessage("Simpan Perubahan?")
                    .setNeutralButton("Cancel", null)
                    .setNegativeButton("No", (dialog, which) -> super.onBackPressed())
                    .setPositiveButton("Yes", (dialog, which) -> {
                        new BarangDB(this).update(barang);
                        super.onBackPressed();
                    })
                    .show();
        }
        else
            super.onBackPressed();

    }
}