package cl.avarti.fifoon;

import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.TextView;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;

import java.lang.reflect.Array;

import cz.msebera.android.httpclient.Header;



public class form_Salida extends AppCompatActivity {
    EditText ubicacion;
    RadioButton picking;
    RadioButton dañado;
    Button guardar;
    private static final String ALMA_URL="http://fifo.esy.es/salida.php?";
    private AsyncHttpClient cliente;

    private TextView datologin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form__salida);


        datologin = (TextView)findViewById(R.id.parametrologinsalida);

        //TRAER DATO USUARIO LOGIN A ESTE ACTIVITY
        String param = getIntent().getStringExtra("param");
        //INSERTAR DATO OBTENIDO EN TEXTVIEW
        datologin.setText("Bienvenido: " + param);

        ubicacion = (EditText) findViewById(R.id.txt_ubicacion);
        picking = (RadioButton) findViewById(R.id.radio_picking);
        dañado = (RadioButton) findViewById(R.id.radio_dañada);
        guardar = (Button) findViewById(R.id.btn_guardar_salida);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salida();
            }
        });


    }
    void salida(){
    String comentario = "";
        if (ubicacion.getText().toString().isEmpty()){
            Toast.makeText(form_Salida.this, "Hay Campos Vacios", Toast.LENGTH_SHORT).show();
        }else {
            String ubi = ubicacion.getText().toString();
            if (picking.isChecked() == true){
             comentario = "Picking";
            }else if (dañado.isChecked() == true){
                comentario = "Mercaderia dañada";
            }

            String usuario = getIntent().getStringExtra("param");

            String parametros =  "ubicacion="+ubi+"&comentario="+comentario+"&usuario="+usuario;
            cliente.get(ALMA_URL + parametros, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (statusCode == 200){
                        Toast.makeText(form_Salida.this, "Registro Agregado Correctamente", Toast.LENGTH_SHORT).show();
                        ubicacion.setText("");


                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });
        }
    }

}
