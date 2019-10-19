package pe.isil.tiendaperu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class IniciarSesionActivity extends AppCompatActivity implements View.OnClickListener {
    Button mbtnIniciarSesion;
    ImageView mivFoto;
    EditText metUsuario, metClave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);
        mbtnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        mivFoto = findViewById(R.id.ivFoto);
        metUsuario = findViewById(R.id.etUsuario);
        metClave = findViewById(R.id.etClave);

        mbtnIniciarSesion.setOnClickListener(this);

        mostrarImagen();
    }

    private void mostrarImagen() {
        String ruta = "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/5ddc5cd5-308e-4a65-9cfe-7a2d8465d366/d5mhpr5-032e6dc8-2656-48bf-b2df-9b0fdc8ccedc.png?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7InBhdGgiOiJcL2ZcLzVkZGM1Y2Q1LTMwOGUtNGE2NS05Y2ZlLTdhMmQ4NDY1ZDM2NlwvZDVtaHByNS0wMzJlNmRjOC0yNjU2LTQ4YmYtYjJkZi05YjBmZGM4Y2NlZGMucG5nIn1dXSwiYXVkIjpbInVybjpzZXJ2aWNlOmZpbGUuZG93bmxvYWQiXX0.XtTFXW1TRLeszQred4xNz6CofSEVHZATSsAe1F1lb9E";
        Picasso.get().load(ruta).into(mivFoto);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_inicio_sesion, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // item.getItemId() ---> obtiene el id de la opción  de menu que recibio el evento
        switch (item.getItemId()) {
            case R.id.nav_acerca_de:
                mostrarAcercade();
                return true;
            case R.id.nav_ayuda:
                mostrarAyuda();
                return true;
            case R.id.nav_terminos:
                mostrarTerminos();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void mostrarTerminos() {
        startActivity(new Intent(this,TerminosActivity.class));
    }

    private void mostrarAyuda() {
        startActivity(new Intent(this,AyudaActivity.class));
    }

    private void mostrarAcercade() {
        startActivity(new Intent(this,AcercadeActivity.class));
    }

    @Override
    public void onClick(View v) {
        final String usuario = metUsuario.getText().toString();
        final String clave = metClave.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(this);// Hace referencia al activity en donde esta el fragment
        String url = Total.rutaServidor + "/servicioproductos.php";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("RESPUESTA",response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR",error.getMessage());
            }
        }){
            //dentro de las llaves pondremos los parametros que enviaremos al servidor

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("usuario",usuario);
                map.put("clave",clave);

                return map;
            }
        };

// Add the request to the RequestQueue.
        queue.add(stringRequest);




        //startActivity(new Intent(this,EscritorioActivity.class));
    }
}
