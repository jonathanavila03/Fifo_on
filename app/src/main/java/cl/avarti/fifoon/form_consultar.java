package cl.avarti.fifoon;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.blikoon.qrcodescanner.QrCodeActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;





public class form_consultar extends AppCompatActivity {
    private AsyncHttpClient cliente;
    private String valor_prod;
    private Array Ubicacion;
    EditText ubicacion;
    EditText producto;
    Button consultar;
    String param;
    String validar_ubi;
    ImageView correcto_ubi;
    ImageView incorrecto_ubi;
    private TextView datologin;
    AlertDialog.Builder builder;
    private static final int REQUEST_CODE_QR_SCAN = 100;
    private static final int REQ_CODE_SPEECH_INPUT = 101;
    Button mic_consu;
    Button qr_consul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_consultar);
        builder = new AlertDialog.Builder(this);
        validar_ubi = new String();

        datologin = (TextView)findViewById(R.id.parametrologinconsul);

        correcto_ubi =(ImageView)findViewById(R.id.correcto_ubi_consulta);
        incorrecto_ubi = (ImageView)findViewById(R.id.incorrecto_ubi_consulta);

        //TRAER DATO USUARIO LOGIN A ESTE ACTIVITY
        String param = getIntent().getStringExtra("param");
        //INSERTAR DATO OBTENIDO EN TEXTVIEW
        datologin.setText("Bienvenido: " + param);
        ubicacion = (EditText) findViewById(R.id.txt_ubi_consul);
        cliente = new AsyncHttpClient();
        consultar = (Button) findViewById(R.id.btn_consu);
        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtConsulta();

            }
        });
        mic_consu = (Button) findViewById(R.id.btn_mic_consultar);
        mic_consu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerVoz1();
            }
        });
        qr_consul = (Button) findViewById(R.id.btn_qr_consultar);
        qr_consul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(form_consultar.this, QrCodeActivity.class);
                startActivityForResult(i, REQUEST_CODE_QR_SCAN);
            }
        });

    }

    private void obtenerConsulta()
    {
        if (ubicacion.getText().toString().isEmpty()){
            Toast.makeText(form_consultar.this, "Hay Campos Vacios", Toast.LENGTH_SHORT).show();
            incorrecto_ubi.setVisibility(View.VISIBLE);
            correcto_ubi.setVisibility(View.INVISIBLE);
        }else{
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_QR_SCAN) {
            if (data != null) {
                String lectura = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
                Toast.makeText(getApplicationContext(), "Leído: " + lectura, Toast.LENGTH_SHORT).show();
                ubicacion.setText(lectura);

            }
        } else if (requestCode == REQUEST_CODE_QR_SCAN && data == null) {
            Toast.makeText(getApplicationContext(), "No se pudo obtener una respuesta, intente nuevamente.", Toast.LENGTH_SHORT).show();
            String resultado = data.getStringExtra("com.blikoon.qrcodescanner.error_decoding_image");
            return;
        }
        if (requestCode == REQ_CODE_SPEECH_INPUT){
            if (resultCode == RESULT_OK && null != data)
            {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                ubicacion.setText(result.get(0));
            }
        }
    }
    private void obtenerVoz1(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hola favor dime los parametros");
        try{
            startActivityForResult(intent,REQ_CODE_SPEECH_INPUT);
        }
        catch (ActivityNotFoundException e1){

        }
    }

    private void obtConsulta()
    {
        String ubicar = ubicacion.getText().toString();
        String url = "http://fifo.esy.es/obtenerUbicacion.php?";
        String parametros ="ubicacion="+ubicar+"&usuario="+ param;
        cliente.get(url+parametros, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200)
                {
                    obtUbicacion(new String (responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
    private void obtUbicacion(String respuesta){
        try{
            JSONArray jsonArreglo = new JSONArray(respuesta);
            for (int i = 0; i <jsonArreglo.length(); i++){
                validar_ubi = jsonArreglo.getJSONObject(i).getString("Ubicacion");
            }
            obtenerConsulta();
        }catch (Exception e1)
        {e1.printStackTrace();}
    }

}
