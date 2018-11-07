package cl.avarti.fifoon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuSidebarOperador extends AppCompatActivity {

    Button btnsidebar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_sidebar_operador);

        btnsidebar = (Button)findViewById(R.id.btnsidebarout);

        Button btnexportarexcel = (Button)findViewById(R.id.btnexportarexcel);

        btnsidebar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abc = new Intent(MenuSidebarOperador.this, Menuoperador.class);
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
