package cl.avarti.fifoon;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
import com.loopj.android.http.*;

import com.blikoon.qrcodescanner.QrCodeActivity;

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

    private static final String ALMA_URL="http://52.67.142.80/jsonfifon/almacenar.php?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_almacenar);

        edit_producto = (EditText) findViewById(R.id.txt_producto);
        edit_ubicacion= (EditText) findViewById(R.id.txt_ubicacion);
        edit_fechav = (EditText) findViewById(R.id.txt_fechav);
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
    }
}
