package pe.isil.tiendaperu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class AyudaActivity extends AppCompatActivity {

    ListView mlvAyuda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);

        mlvAyuda = findViewById(R.id.lvAyuda);

        String[] consejos = new String[]{

                "Amor prohibido murmuran por las calles",
                "Porque somos de distintas sociedades",
                "Amor prohibido nos dice todo el mundo ",
                "El dinero no importa en ti y en mí, ni en el corazón",
                "Oh, oh baby"

        };

        ListAdapter listAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1,consejos
        );
        mlvAyuda.setAdapter(listAdapter);

    }
}
