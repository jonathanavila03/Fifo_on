package cl.avarti.fifoon;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Menuoperador extends AppCompatActivity implements View.OnClickListener {

    Button btnsidebar;

    private TextView datologin;

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
        datologin = (TextView)findViewById(R.id.parametrologin);
        btnsidebar = (Button)findViewById(R.id.btnsidebarin);


        btnsidebar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abc = new Intent(Menuoperador.this, MenuSidebarOperador.class);
                startActivity(abc);
            }
        });

        //TRAER DATO USUARIO LOGIN A ESTE ACTIVITY
        String dato = getIntent().getStringExtra("dato");
        String parametroActivity = getIntent().getStringExtra("parametro");
        //INSERTAR DATO OBTENIDO EN TEXTVIEW
        datologin.setText("Bienvenido: " + dato);

    }
    public void onClick(View v)
    {
        if(v.getId()==R.id.btn_almacenar)
        {
            Intent intent = new Intent(Menuoperador.this, FormAlmacenar.class);
            intent.putExtra("param",getIntent().getStringExtra("dato"));
            startActivity(intent);
        }
        if(v.getId()==R.id.btn_mover)
        {
            Intent intent = new Intent(Menuoperador.this, FormMover.class);
            intent.putExtra("param",getIntent().getStringExtra("dato"));
            startActivity(intent);
        }
        if(v.getId()==R.id.btn_consultar)
        {
            Intent intent = new Intent(Menuoperador.this, form_consultar.class);
            intent.putExtra("param",getIntent().getStringExtra("dato"));
            startActivity(intent);
        }
        if(v.getId()==R.id.btn_salida)
        {
            Intent intent = new Intent(Menuoperador.this, form_Salida.class);
            intent.putExtra("param",getIntent().getStringExtra("dato"));
            startActivity(intent);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}
