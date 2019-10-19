package pe.isil.tiendaperu;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductosAdapter extends ArrayAdapter {

    Activity activity;
    ArrayList arrayList;
    public ProductosAdapter(Activity activity, ArrayList arrayList) {
        super(activity, R.layout.item_productos, arrayList);
        this.activity = activity;
        this.arrayList = arrayList;
    }


    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {

        View rootView = activity.getLayoutInflater().inflate(R.layout.item_productos, null);

        TextView mtvIdProducto = rootView.findViewById(R.id.tvIdProducto);
        TextView mtvNombre = rootView.findViewById(R.id.tvNombre);
        TextView mtvDetalle = rootView.findViewById(R.id.tvDetalle);
        TextView mtvPrecio = rootView.findViewById(R.id.tvPrecio);

        ImageView mivFoto = rootView.findViewById(R.id.ivFoto);

        HashMap<String,String> map = (HashMap<String, String>) arrayList.get(position);
        mtvIdProducto.setText(map.get("cod"));
        mtvNombre.setText(map.get("nom"));
        mtvDetalle.setText(map.get("det"));
        mtvPrecio.setText(map.get("pre"));

        String ruta = Total.rutaServidor + map.get("ich");
        Picasso.get().load(ruta).into(mivFoto);




        return rootView;
    }
}
