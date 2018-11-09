package cl.avarti.fifoon;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.net.URI;
import java.net.URL;

public class exportarexcel extends AppCompatActivity {

    Button descargar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exportarexcel);

        Button descargar = (Button) findViewById(R.id.btn_export_excel);

        descargar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://fifo.esy.es/Excel.php"));
                startActivity(intent);
            }
        });


    }



}
