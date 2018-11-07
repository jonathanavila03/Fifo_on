package cl.avarti.fifoon;


import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.*;


import com.blikoon.qrcodescanner.QrCodeActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class FormAlmacenar extends AppCompatActivity {
    private static final int REQUEST_CODE_QR_SCAN = 101;

    private static final String TAG = "FormAlmacenar";
    private EditText mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private AsyncHttpClient cliente;
    EditText edit_producto;
    EditText edit_ubicacion;
    EditText edit_fechav;
    Button btn_gua_alma;
    Button btn_gua;
    Button btn_qr;
    Button grabar_prod;
    Button grabar_ubi;
    Button btn_calendar;
    private TextView datologin;
    private static final int RECOGNIZE_SPEECH_ACTIVITY = 1;

    private static final String ALMA_URL="http://fifo.esy.es/almacenar.php?";
    private static final int REQ_CODE_SPEECH_INPUT=100;
    private static final int REQ_CODE_SPEECH_INPUT2=102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_almacenar);

        datologin = (TextView)findViewById(R.id.parametrologinalma);

        edit_producto = (EditText) findViewById(R.id.txt_producto);
        edit_ubicacion= (EditText) findViewById(R.id.txt_ubicacion);
        edit_fechav = (EditText) findViewById(R.id.txt_fechav);
        btn_calendar = (Button) findViewById(R.id.btcalendar);

        //TRAER DATO USUARIO LOGIN A ESTE ACTIVITY
        String param = getIntent().getStringExtra("param");
        //INSERTAR DATO OBTENIDO EN TEXTVIEW
        datologin.setText("Bienvenido: " + param);


        btn_calendar = (Button) findViewById(R.id.btcalendar);

        btn_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        grabar_prod = (Button) findViewById(R.id.btn_micproducto);
        grabar_prod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerVoz1();
            }
        });
        grabar_ubi = (Button) findViewById(R.id.btn_micubicacion);
        grabar_ubi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerVoz2();
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

        mDisplayDate = (EditText)findViewById(R.id.txt_fechav);
        btn_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal =Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        FormAlmacenar.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        day,month,year);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd/mm/aaaa: " + day + "/" + month + "/" + year);

                String fecha = day + "/" + month + "/" + year;
                edit_fechav.setText(fecha);
            }
        };


    }

   private void almacenar(){

        if (edit_producto.getText().toString().isEmpty() || edit_ubicacion.getText().toString().isEmpty() || edit_fechav.getText().toString().isEmpty()){
            Toast.makeText(FormAlmacenar.this, "Hay Campos Vacios", Toast.LENGTH_SHORT).show();
        }else {
            String produ = edit_producto.getText().toString();
            String ubi = edit_ubicacion.getText().toString();
            String fechav = edit_fechav.getText().toString();
            String usr = getIntent().getStringExtra("param");

            String parametros =  "producto="+produ+"&ubicacion="+ubi+"&fechav="+fechav+"&usuario="+usr;
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

        if (requestCode == REQUEST_CODE_QR_SCAN) {
            if (data != null) {
                String lectura = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
                Toast.makeText(getApplicationContext(), "Leído: " + lectura, Toast.LENGTH_SHORT).show();
                edit_ubicacion.setText(lectura);

            }}
            else if (requestCode == REQUEST_CODE_QR_SCAN && data == null)
            {
                Toast.makeText(getApplicationContext(), "No se pudo obtener una respuesta, intente nuevamente.", Toast.LENGTH_SHORT).show();
                String resultado = data.getStringExtra("com.blikoon.qrcodescanner.error_decoding_image");
                return;
            }

         if (requestCode == REQ_CODE_SPEECH_INPUT){
            if (resultCode == RESULT_OK && null != data)
            {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                edit_producto.setText(result.get(0));
            }
         }
        if (requestCode == REQ_CODE_SPEECH_INPUT2){
            if (resultCode == RESULT_OK && null != data)
            {
                ArrayList<String> result2 = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                edit_ubicacion.setText(result2.get(0));
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



}
