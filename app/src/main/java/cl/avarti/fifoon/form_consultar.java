package cl.avarti.fifoon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.TextView;

import android.widget.Button;
import android.widget.EditText;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;

import java.lang.reflect.Array;

import cz.msebera.android.httpclient.Header;





public class form_consultar extends AppCompatActivity {
    private AsyncHttpClient cliente;
    private String Producto;
    private Array Ubicacion;
    EditText ubicacion;
    EditText producto;
    Button consultar;
    private TextView datologin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_consultar);

        datologin = (TextView)findViewById(R.id.parametrologinconsul);

        //TRAER DATO USUARIO LOGIN A ESTE ACTIVITY
        String param = getIntent().getStringExtra("param");
        //INSERTAR DATO OBTENIDO EN TEXTVIEW
        datologin.setText("Bienvenido: " + param);

    }

    private void obtenerConsulta()
    {
        String url = "http://fifo.esy.es/obtenerDatos.php";
        cliente.get(url, new AsyncHttpResponseHandler() {
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

            }
        }catch (Exception e1)
        {e1.printStackTrace();}
    }
    private void obtenerUbicaciones(String respuesta){
        try{
            JSONArray jsonArreglo = new JSONArray(respuesta);
            for (int i = 0; i <jsonArreglo.length(); i++){

            }
        }catch (Exception e1)
        {e1.printStackTrace();}
    }
}
