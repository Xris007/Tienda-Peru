package pe.isil.tiendaperu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class TerminosActivity extends AppCompatActivity implements View.OnClickListener {
    RadioButton mrbReglas, mrbDerechos, mrbReconomientos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminos);

        mrbReglas = findViewById(R.id.rbReglas);
        mrbDerechos = findViewById(R.id.rbDerechos);
        mrbReconomientos = findViewById(R.id.rbReconocimientos);

        mrbReglas.setOnClickListener(this);
        mrbDerechos.setOnClickListener(this);
        mrbReconomientos.setOnClickListener(this);

        mrbReglas.setChecked(true);
        mostrarReglas();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rbReglas:
                mostrarReglas();
                break;
            case R.id.rbDerechos:
                mostrarDerechos();
                break;
            case R.id.rbReconocimientos:
                mostrarReconociemto();
                break;

        }
    }

    private void mostrarReglas() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.llContenedor, new ReglasFragment())
                .commit();
    }
    private void mostrarDerechos() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.llContenedor, new DerechosFragment())
                .commit();
    }
    private void mostrarReconociemto() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.llContenedor, new ReconocimientoFragment())
                .commit();
    }
}
