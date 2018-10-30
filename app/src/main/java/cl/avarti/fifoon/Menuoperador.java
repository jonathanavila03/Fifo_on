package cl.avarti.fifoon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menuoperador extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuoperador);

        Button btn_alma = (Button)findViewById(R.id.btn_almacenar);
        Button btn_mover = (Button)findViewById(R.id.btn_mover);
        Button btn_consultar = (Button)findViewById(R.id.btn_consultar);
        Button btn_salida = (Button)findViewById(R.id.btn_salida);
        btn_alma.setOnClickListener(this);
        btn_consultar.setOnClickListener(this);
        btn_mover.setOnClickListener(this);
        btn_salida.setOnClickListener(this);

    }
    public void onClick(View v)
    {
        if(v.getId()==R.id.btn_almacenar)
        {
            Intent intent = new Intent(Menuoperador.this, FormAlmacenar.class);
            startActivity(intent);
        }
        if(v.getId()==R.id.btn_mover)
        {
            Intent intent = new Intent(Menuoperador.this, FormMover.class);
            startActivity(intent);
        }
        if(v.getId()==R.id.btn_consultar)
        {
            Intent intent = new Intent(Menuoperador.this, form_consultar.class);
            startActivity(intent);
        }
        if(v.getId()==R.id.btn_salida)
        {
            Intent intent = new Intent(Menuoperador.this, form_Salida.class);
            startActivity(intent);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}
