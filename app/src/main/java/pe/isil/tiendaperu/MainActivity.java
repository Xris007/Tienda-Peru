package pe.isil.tiendaperu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button mbtnCrearCuenta;
    TextView mtvIniciarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mbtnCrearCuenta = findViewById(R.id.btnCrearCuenta);
        mtvIniciarSesion = findViewById(R.id.tvIniciarSesion);


        mbtnCrearCuenta.setOnClickListener(this);
        mtvIniciarSesion.setOnClickListener(this);
        mostrarIP();
    }

    private void mostrarIP() {
        final TextView mtvIP = findViewById(R.id.tvIP);
// ...

// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://api.ipify.org/";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        mtvIP.setText(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mtvIP.setText(error.getMessage());
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCrearCuenta:
                startActivity(new Intent(this,CrearCuentaActivity.class));
                break;
            case R.id.tvIniciarSesion:
                startActivity(new Intent(this,IniciarSesionActivity.class));
                break;
        }

    }
}
