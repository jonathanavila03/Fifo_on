package cl.avarti.fifoon;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
import com.loopj.android.http.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import android.widget.DatePicker;

import com.blikoon.qrcodescanner.QrCodeActivity;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class FormAlmacenar extends AppCompatActivity {
    private static final int REQUEST_CODE_QR_SCAN = 101;
    private AsyncHttpClient cliente;
    EditText edit_producto;
    EditText edit_ubicacion;
    EditText edit_fechav;
    Button btn_gua_alma;
    Button btn_gua;
    Button btn_qr;
    Button grabar_prod;
    Button grabar_ubi;
    String strSpeech2Text;
    Button btn_calendar;
    private static final int RECOGNIZE_SPEECH_ACTIVITY = 1;
    private static final String ALMA_URL="http://fifo.esy.es/almacenar.php?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_almacenar);

        edit_producto = (EditText) findViewById(R.id.txt_producto);
        edit_ubicacion= (EditText) findViewById(R.id.txt_ubicacion);
        edit_fechav = (EditText) findViewById(R.id.txt_fechav);
        btn_calendar = (Button) findViewById(R.id.button7);
        btn_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        grabar_prod = (Button) findViewById(R.id.btn_micproducto);
        grabar_prod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabarvoz();
                edit_producto.setText(strSpeech2Text);
                strSpeech2Text = "";
            }
        });
        grabar_ubi = (Button) findViewById(R.id.btn_micubicacion);
        grabar_ubi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabarvoz();
                edit_ubicacion.setText(strSpeech2Text);
                strSpeech2Text = "";
            }
        });
        cliente = new AsyncHttpClient();
        btn_gua_alma = (Button) findViewById(R.id.btn_gua_alma);
        btn_gua_alma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                almacenar();
            }
        });
        btn_gua=(Button)findViewById(R.id.btn_gua);
        btn_gua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                almacenar();
                Intent intent = new Intent(FormAlmacenar.this, Menuoperador.class);
                startActivity(intent);
            }
        });
        btn_qr = (Button) findViewById(R.id.qr);
        btn_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FormAlmacenar.this, QrCodeActivity.class);
                startActivityForResult(i, REQUEST_CODE_QR_SCAN);
            }
        });

    }


    void almacenar(){

        if (edit_producto.getText().toString().isEmpty() || edit_ubicacion.getText().toString().isEmpty() || edit_fechav.getText().toString().isEmpty()){
            Toast.makeText(FormAlmacenar.this, "Hay Campos Vacios", Toast.LENGTH_SHORT).show();
        }else {
            String produ = edit_producto.getText().toString();
            String ubi = edit_ubicacion.getText().toString();
            String fechav = edit_fechav.getText().toString();

            String parametros =  "producto="+produ+"&ubicacion="+ubi+"&fechav="+fechav;
            cliente.get(ALMA_URL + parametros, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (statusCode == 200){
                        Toast.makeText(FormAlmacenar.this, "Registro Agregado Correctamente", Toast.LENGTH_SHORT).show();
                        edit_fechav.setText("");
                        edit_producto.setText("");
                        edit_ubicacion.setText("");
                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });
        }
}
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(getApplicationContext(), "No se pudo obtener una respuesta", Toast.LENGTH_SHORT).show();
            String resultado = data.getStringExtra("com.blikoon.qrcodescanner.error_decoding_image");
            if (resultado != null) {
                Toast.makeText(getApplicationContext(), "No se pudo escanear el código QR", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        if (requestCode == REQUEST_CODE_QR_SCAN) {
            if (data != null) {
                String lectura = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
                Toast.makeText(getApplicationContext(), "Leído: " + lectura, Toast.LENGTH_SHORT).show();
                edit_ubicacion.setText(Toast.LENGTH_SHORT);

            }
        }
        if (requestCode == RECOGNIZE_SPEECH_ACTIVITY) {
            if (resultCode == RESULT_OK && null != data) {
                ArrayList<String> speech = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                strSpeech2Text = speech.get(0);}
            if (resultCode != RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Por Favor volver a intentarlo.", Toast.LENGTH_SHORT).show();}
        }
    }
    void grabarvoz()
    {
        Intent intentActionRecognizeSpeech = new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        // Configura el Lenguaje (Español-México)
        intentActionRecognizeSpeech.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL, "es-MX");
        try {
            startActivityForResult(intentActionRecognizeSpeech,
                    RECOGNIZE_SPEECH_ACTIVITY);
        } catch ( Exception a) {
            Toast.makeText(getApplicationContext(),
                    "Tú dispositivo no soporta el reconocimiento por voz",
                    Toast.LENGTH_SHORT).show();
        }
    }

}
