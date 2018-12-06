package cl.avarti.fifoon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuSidebarOperador extends AppCompatActivity {

    Button btnsidebar;
    String param;
    private TextView datologin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_sidebar_operador);

        btnsidebar = (Button)findViewById(R.id.btnsidebarout);
        datologin = (TextView)findViewById(R.id.txtusersidebar);

        Button btnexportarexcel = (Button)findViewById(R.id.btnexportarexcel);

        //TRAER DATO USUARIO LOGIN A ESTE ACTIVITY
        param = getIntent().getStringExtra("param");
        //INSERTAR DATO OBTENIDO EN TEXTVIEW
        datologin.setText(param.toUpperCase());

        btnsidebar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abc = new Intent(MenuSidebarOperador.this, Menuoperador.class);
                abc.putExtra("dato",getIntent().getStringExtra("param"));
                startActivity(abc);
            }
        });
        btnexportarexcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuSidebarOperador.this, exportarexcel.class);
                startActivity(intent);
            }
        });

    }

}
