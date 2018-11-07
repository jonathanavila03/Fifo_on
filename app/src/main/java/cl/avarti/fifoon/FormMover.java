package cl.avarti.fifoon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.TextView;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;


public class FormMover extends AppCompatActivity {
    EditText ubi_antigua;
    EditText ubi_nueva;
    Button btn_guardar;
    private static final String ALMA_URL="http://fifo.esy.es/mover.php?";
    private AsyncHttpClient cliente;

    private TextView datologin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_mover);


        datologin = (TextView)findViewById(R.id.parametrologinmove);

        //TRAER DATO USUARIO LOGIN A ESTE ACTIVITY
        String param = getIntent().getStringExtra("param");
        //INSERTAR DATO OBTENIDO EN TEXTVIEW
        datologin.setText("Bienvenido: " + param);


        ubi_antigua =(EditText) findViewById(R.id.txt_ubicacion);
        ubi_nueva = (EditText) findViewById(R.id.txt_ubi_nue);
        btn_guardar= (Button) findViewById(R.id.btn_guardar);
        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mover();
            }
        });
    }

    void mover(){

        if (ubi_antigua.getText().toString().isEmpty() || ubi_nueva.getText().toString().isEmpty() ){
            Toast.makeText(FormMover.this, "Hay Campos Vacios", Toast.LENGTH_SHORT).show();
        }else {
            String ubica_ant = ubi_antigua.getText().toString();
            String ubica_nueva = ubi_antigua.getText().toString();
            String usuario = "";

            String parametros =  "ubi_nueva="+ubica_ant+"&ubi_antigua="+ubica_nueva+"&usuario="+usuario;
            cliente.get(ALMA_URL + parametros, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (statusCode == 200){
                        Toast.makeText(FormMover.this, "Registro Agregado Correctamente", Toast.LENGTH_SHORT).show();
                        ubi_antigua.setText("");
                        ubi_nueva.setText("");

                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });
        }

    }
}
