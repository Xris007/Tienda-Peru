package pe.isil.tiendaperu;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductoFragment extends Fragment {
    TextView mtvDescripcion;
    ListView mlvProductos;
    String idcategoria;
    ArrayList<HashMap<String,String>> arrayList;

    public ProductoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_producto, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mtvDescripcion = view.findViewById(R.id.tvDescripcion);
        mlvProductos = view.findViewById(R.id.lvProductos);

        Bundle bundle = getArguments();
        //Así el fragment recibe los valores que le envian

        idcategoria = bundle.getString("idcategoria");
        String nombre = bundle.getString("nombre");
        String descripcion = bundle.getString("descripcion");

        mtvDescripcion.setText(descripcion);
        getActivity().setTitle(nombre);


        leerDatos();

    }
    private void leerDatos() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity());// Hace referencia al activity en donde esta el fragment
        String url = Total.rutaServidor + "/servicioproductos.php";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("PRODUCTOS",response);
                        mostrarLista(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("CATEGORIAS",error.getMessage());
            }
        }){
            //dentro de las llaves pondremos los parametros que enviaremos al servidor

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("caty",idcategoria);

                return map;
            }
        };

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void mostrarLista(String response) {
        try {

            //Esto es para que el texto sea interpretado en formato JSON
            JSONArray jsonArray = new JSONArray(response);
            arrayList = new ArrayList<>();

            for(int i = 0; i< jsonArray.length(); i++){
                //jsonObjet representará a cada fila del jsonArray
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String idcategoria = jsonObject.getString("idproducto");
                String nombre = jsonObject.getString("nombre");
                String detalle = jsonObject.getString("detalle");
                String precio = jsonObject.getString("precio");
                String imagenchica = jsonObject.getString("imagenchica");
                HashMap<String,String> map = new HashMap<>();

                map.put("cod",idcategoria);
                map.put("nom",nombre);
                map.put("det",detalle);
                map.put("pre", precio);
                map.put("ich", imagenchica);

                arrayList.add(map);
            }
            /*
            ListAdapter listAdapter = new SimpleAdapter(
                    getActivity(),arrayList,R.layout.item_productos,
                    new String[]{"cod","nom","det","pre"},
                    new int[]{R.id.tvIdProducto,R.id.tvNombre,R.id.tvDetalle,R.id.tvPrecio}
            );

            mlvProductos.setAdapter(listAdapter);
            */

            ProductosAdapter productosAdapter = new ProductosAdapter(getActivity(),arrayList);
            mlvProductos.setAdapter(productosAdapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
