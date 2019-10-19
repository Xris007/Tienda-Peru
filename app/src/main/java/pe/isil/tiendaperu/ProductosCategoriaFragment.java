package pe.isil.tiendaperu;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

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


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductosCategoriaFragment extends Fragment implements AdapterView.OnItemClickListener {
    ArrayList<HashMap<String,String>> arrayList;
    ListView mlvCategorias;

    public ProductosCategoriaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_productos_categoria, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mlvCategorias = view.findViewById(R.id.lvCategorias);

        leerDatos();

    }

    private void leerDatos() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity());// Hace referencia al activity en donde esta el fragment
        String url = Total.rutaServidor + "/serviciocategorias.php";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("CATEGORIAS",response);
                        mostrarLista(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("CATEGORIAS",error.getMessage());
            }
        });

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
                String idcategoria = jsonObject.getString("idcategoria");
                String nombre = jsonObject.getString("nombre");
                String descripcion = jsonObject.getString("descripcion");
                HashMap<String,String> map = new HashMap<>();
                map.put("cod",idcategoria);
                map.put("nom",nombre);
                map.put("des",descripcion);
                arrayList.add(map);
            }
            ListAdapter listAdapter = new SimpleAdapter(
                    getActivity(),arrayList,R.layout.item_categoria,
                    new String[]{"cod","nom","des"},
                    new int[]{R.id.tvIdCategoria,R.id.tvNombre,R.id.tvDescripcion}
            );
            mlvCategorias.setAdapter(listAdapter);
            mlvCategorias.setOnItemClickListener(this);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(getActivity(), "Posicion: "+ (position+1), Toast.LENGTH_SHORT).show();
        HashMap<String,String> map = arrayList.get(position);
        //Se obtiene enun HashMAp el elemento del arrayList seleccionado

        String codigo = map.get("cod");
        String nombre = map.get("nom");
        String descripcion = map.get("des");

        //Se obtiene el contenido de un campo del HashMap
        Toast.makeText(getActivity(), nombre, Toast.LENGTH_SHORT).show();

        //Bundle para almacenar datos
        Bundle bundle = new Bundle();
        bundle.putString("idcategoria", codigo);
        bundle.putString("nombre", nombre);
        bundle.putString("descripcion", descripcion);

        ProductoFragment productoFragment = new ProductoFragment();

        productoFragment.setArguments(bundle);

        //Para llamar desde un fragment a otro fragment
        getFragmentManager().beginTransaction()
                .replace(R.id.contenedor_principal, productoFragment)
                .commit();

        //Con setTitle se cambia el titulo de Activity
        getActivity().setTitle(nombre);
    }
}
