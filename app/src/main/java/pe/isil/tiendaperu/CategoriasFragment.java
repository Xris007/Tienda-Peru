package pe.isil.tiendaperu;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriasFragment extends Fragment {
    ListView mlvCategorias;

    public CategoriasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categorias, container, false);
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
            String[] categorias = new String[jsonArray.length()];
            for(int i = 0; i< jsonArray.length(); i++){
                //jsonObjet representará a cada fila del jsonArray
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String nombre = jsonObject.getString("nombre");
                categorias[i] = nombre;
            }
            ListAdapter listAdapter = new ArrayAdapter<>(
                    getActivity(), android.R.layout.simple_list_item_1,categorias
            );
            mlvCategorias.setAdapter(listAdapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
