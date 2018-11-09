package cl.avarti.fifoon;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.ImageView;
import android.widget.TextView;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.blikoon.qrcodescanner.QrCodeActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;



public class form_Salida extends AppCompatActivity {

    private AsyncHttpClient cliente2;
    EditText ubicacion;
    String param;
    RadioButton picking;
    RadioButton dañado;
    Button guardar;
    Button mic_ubi_sal;
    Button qr_ubi_sal;
    String validar_ubi;
    ImageView correcto_ubi;
    ImageView incorrecto_ubi;
    private static final String ALMA_URL="http://fifo.esy.es/salida.php?";
    private AsyncHttpClient cliente;
    private static final int REQ_CODE_SPEECH_INPUT=100;
    private static final int REQUEST_CODE_QR_SCAN = 101;

    private TextView datologin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form__salida);


        datologin = (TextView)findViewById(R.id.parametrologinsalida);

        correcto_ubi =(ImageView)findViewById(R.id.correcto_ubi_salida);
        incorrecto_ubi = (ImageView)findViewById(R.id.incorrecto_ubi_salida);

        validar_ubi = new String();

        //TRAER DATO USUARIO LOGIN A ESTE ACTIVITY
         param = getIntent().getStringExtra("param");
        //INSERTAR DATO OBTENIDO EN TEXTVIEW
        datologin.setText("Bienvenido: " + param);
        cliente = new AsyncHttpClient();
        cliente2 = new AsyncHttpClient();
        ubicacion = (EditText) findViewById(R.id.txt_ubi_org);
        picking = (RadioButton) findViewById(R.id.radio_picking);
        dañado = (RadioButton) findViewById(R.id.radio_dañada);
        guardar = (Button) findViewById(R.id.btn_guardar_salida);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerConsulta();
            }
        });
        mic_ubi_sal = (Button) findViewById(R.id.btn_mic_salida);
        mic_ubi_sal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerVoz1();
            }
        });
        qr_ubi_sal = (Button) findViewById(R.id.btn_qr_salida);
        qr_ubi_sal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(form_Salida.this, QrCodeActivity.class);
                startActivityForResult(i, REQUEST_CODE_QR_SCAN);
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

            if (validar_ubi.isEmpty()) {
                Toast.makeText(this, "No hay mercadería en ubicación", Toast.LENGTH_SHORT).show();
                incorrecto_ubi.setVisibility(View.VISIBLE);
                correcto_ubi.setVisibility(View.INVISIBLE);
            }else{
                correcto_ubi.setVisibility(View.VISIBLE);
                incorrecto_ubi.setVisibility(View.INVISIBLE);

                String parametros = "ubicacion=" + ubi + "&comentario=" + comentario + "&usuario=" + param;
                cliente.get(ALMA_URL + parametros, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        if (statusCode == 200) {
                            Toast.makeText(form_Salida.this, "Salida Registrada Correctamente", Toast.LENGTH_SHORT).show();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE_SPEECH_INPUT){
            if (resultCode == RESULT_OK && null != data)
            {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                ubicacion.setText(result.get(0));
            }
        }
        if (requestCode == REQUEST_CODE_QR_SCAN) {
            if (data != null) {
                String lectura = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
                Toast.makeText(getApplicationContext(), "Leído: " + lectura, Toast.LENGTH_SHORT).show();
                ubicacion.setText(lectura);

            }}
        else if (requestCode == REQUEST_CODE_QR_SCAN && data == null)
        {
            Toast.makeText(getApplicationContext(), "No se pudo obtener una respuesta, intente nuevamente.", Toast.LENGTH_SHORT).show();
            String resultado = data.getStringExtra("com.blikoon.qrcodescanner.error_decoding_image");
            return;
        }
    }

    private void obtenerConsulta()
    {
        String ubicar = ubicacion.getText().toString();
        String url = "http://fifo.esy.es/obtenerUbicacion.php?";
        String parametros ="ubicacion="+ubicar+"&usuario="+ param;
        cliente2.get(url+parametros, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200)
                {
                    obtenerUbicacion(new String (responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
    private void obtenerUbicacion(String respuesta){
        try{
            JSONArray jsonArreglo = new JSONArray(respuesta);
            for (int i = 0; i <jsonArreglo.length(); i++){
                validar_ubi = jsonArreglo.getJSONObject(i).getString("Ubicacion");
            }
            salida();
        }catch (Exception e1)
        {e1.printStackTrace();}
    }

}
