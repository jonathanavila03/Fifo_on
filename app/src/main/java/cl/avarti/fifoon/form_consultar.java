package cl.avarti.fifoon;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import android.widget.Button;
import android.widget.EditText;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;

import java.lang.reflect.Array;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;





public class form_consultar extends AppCompatActivity {
    private AsyncHttpClient cliente;
    private String valor_prod;
    private Array Ubicacion;
    EditText ubicacion;
    EditText producto;
    Button consultar;
    private TextView datologin;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_consultar);
       builder = new AlertDialog.Builder(this);

        datologin = (TextView)findViewById(R.id.parametrologinconsul);

        //TRAER DATO USUARIO LOGIN A ESTE ACTIVITY
        String param = getIntent().getStringExtra("param");
        //INSERTAR DATO OBTENIDO EN TEXTVIEW
        datologin.setText("Bienvenido: " + param);
        ubicacion = (EditText) findViewById(R.id.txt_ubi_org);
        cliente = new AsyncHttpClient();
        consultar = (Button) findViewById(R.id.btn_consu);
        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerConsulta();

            }
        });

    }

    private void obtenerConsulta()
    {
        String url = "http://fifo.esy.es/obtenerDatos.php?";
        String parametros ="ubicacion="+ubicacion.getText().toString();
        cliente.get(url+parametros, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200)
                {
                    obtenerProducto(new String (responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
    private void obtenerProducto(String respuesta){
        try{
            JSONArray jsonArreglo = new JSONArray(respuesta);
            for (int i = 0; i <jsonArreglo.length(); i++){
                valor_prod = jsonArreglo.getJSONObject(i).getString("DescripcionProducto");
            }
            builder.setTitle("Producto");
            builder.setMessage(valor_prod);
            builder.setPositiveButton("OK", null);
            builder.create();
            builder.show();
        }catch (Exception e1)
        {e1.printStackTrace();}
    }
}
