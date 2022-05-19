package id.go.kominfo.laila.jmpsqlite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import id.go.kominfo.laila.jmpsqlite.R;
import id.go.kominfo.laila.jmpsqlite.model.Barang;

public class BarangAdapter extends BaseAdapter {
    private final Context ctx;
    private final List<Barang> data;

    public BarangAdapter(Context ctx, List<Barang> data) {
        this.ctx = ctx;
        this.data = data;
    }

    private static class ViewHolder{
        TextView tvNama;
        TextView tvHarga;

        public ViewHolder(View view) {
            tvNama = view.findViewById(R.id.tv_nama);
            tvHarga = view.findViewById(R.id.tv_harga);
        }
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(ctx)
                    .inflate(R.layout.item_list, parent, false);

            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else
            holder = (ViewHolder) convertView.getTag();

        Barang barang = data.get(position);

        holder.tvNama.setText(barang.getNama());
        holder.tvHarga.setText(String.valueOf(barang.getHarga()));

        return convertView;
    }
}
