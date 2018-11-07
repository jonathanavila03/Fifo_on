package cl.avarti.fifoon;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.blikoon.qrcodescanner.QrCodeActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.Locale;
import cz.msebera.android.httpclient.Header;


public class FormMover extends AppCompatActivity {
    EditText ubi_antigua;
    EditText ubi_nueva;
    Button btn_guardar;
    Button btn_qr_ant;
    Button btn_qr_nue;
    Button btn_mic_nue;
    Button btn_mic_ant;
    String param;
    private static final String URL="http://fifo.esy.es/mover.php?";
    private AsyncHttpClient moverget;
    private static final int REQ_CODE_SPEECH_INPUT=100;
    private static final int REQ_CODE_SPEECH_INPUT2=102;
    private static final int REQUEST_CODE_QR_SCAN = 101;
    private static final int REQUEST_CODE_QR_SCAN2 = 103;
    String variables;

    private TextView datologin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_mover);

        moverget = new AsyncHttpClient();
        datologin = (TextView)findViewById(R.id.parametrologinmove);
        //TRAER DATO USUARIO LOGIN A ESTE ACTIVITY
        param = getIntent().getStringExtra("param");
        //INSERTAR DATO OBTENIDO EN TEXTVIEW
        datologin.setText("Bienvenido: " + param);


        ubi_antigua =(EditText) findViewById(R.id.txt_ubi_ant);
        ubi_nueva = (EditText) findViewById(R.id.txt_ubi_nue);
        btn_guardar= (Button) findViewById(R.id.btn_guardar_mover);
        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mover();
            }
        });
        btn_qr_ant = (Button) findViewById(R.id.qr_ant);
        btn_qr_ant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FormMover.this, QrCodeActivity.class);
                startActivityForResult(i, REQUEST_CODE_QR_SCAN);
            }
        });
        btn_qr_nue = (Button) findViewById(R.id.qr_nue);
        btn_qr_nue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FormMover.this, QrCodeActivity.class);
                startActivityForResult(i, REQUEST_CODE_QR_SCAN2);
            }
        });
        btn_mic_ant = (Button) findViewById(R.id.btn_micubiactual);
        btn_mic_ant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerVoz1();
            }
        });
        btn_mic_nue = (Button) findViewById(R.id.btn_micubinuevo);
        btn_mic_nue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerVoz2();
            }
        });
    }

   private void mover(){
        if (ubi_antigua.getText().toString().isEmpty() || ubi_nueva.getText().toString().isEmpty()|| param.isEmpty()){
            Toast.makeText(FormMover.this, "Hay Campos Vacios", Toast.LENGTH_SHORT).show();
        }else {
            String ubica_ant = ubi_antigua.getText().toString();
            String ubica_nueva = ubi_nueva.getText().toString();
            variables =  "ubi_nueva="+ubica_nueva+"&ubi_antigua="+ubica_ant+"&usuario="+param;

           moverget.get(URL + variables, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (statusCode == 200)
                    {
                        Toast.makeText(FormMover.this, "El movimiento fue ejecutado correctamente", Toast.LENGTH_SHORT).show();
                        ubi_nueva.setText("");
                        ubi_antigua.setText("");
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });
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
    private void obtenerVoz2(){
        Intent intent2 = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent2.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent2.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent2.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hola favor dime los parametros");
        try{
            startActivityForResult(intent2,REQ_CODE_SPEECH_INPUT2);
        }
        catch (ActivityNotFoundException e1){

        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_QR_SCAN) {
            if (data != null) {
                String lectura = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
                Toast.makeText(getApplicationContext(), "Leído: " + lectura, Toast.LENGTH_SHORT).show();
                ubi_antigua.setText(lectura);

            }}
        else if (requestCode == REQUEST_CODE_QR_SCAN && data == null)
        {
            Toast.makeText(getApplicationContext(), "No se pudo obtener una respuesta, intente nuevamente.", Toast.LENGTH_SHORT).show();
            String resultado = data.getStringExtra("com.blikoon.qrcodescanner.error_decoding_image");
            return;
        }
        if (requestCode == REQUEST_CODE_QR_SCAN2) {
            if (data != null) {
                String lectura = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
                Toast.makeText(getApplicationContext(), "Leído: " + lectura, Toast.LENGTH_SHORT).show();
                ubi_nueva.setText(lectura);

            }}
        else if (requestCode == REQUEST_CODE_QR_SCAN2 && data == null)
        {
            Toast.makeText(getApplicationContext(), "No se pudo obtener una respuesta, intente nuevamente.", Toast.LENGTH_SHORT).show();
            String resultado = data.getStringExtra("com.blikoon.qrcodescanner.error_decoding_image");
            return;
        }

        if (requestCode == REQ_CODE_SPEECH_INPUT){
            if (resultCode == RESULT_OK && null != data)
            {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                ubi_antigua.setText(result.get(0));
            }
        }
        if (requestCode == REQ_CODE_SPEECH_INPUT2){
            if (resultCode == RESULT_OK && null != data)
            {
                ArrayList<String> result2 = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                ubi_nueva.setText(result2.get(0));
            }
        }

    }
}
