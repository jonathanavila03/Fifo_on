package cl.avarti.fifoon;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.Locale;
import cz.msebera.android.httpclient.Header;


public class form_consultar extends AppCompatActivity {
    private AsyncHttpClient cliente;
    private String valor_prod;
    EditText ubicacion;
    EditText producto;
    Button consultar;
    String param;
    String validar_ubi;
    private TextView datologin;
    private static final int REQ_CODE_SPEECH_INPUT = 101;
    Button mic_consu;
    ListView lvDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_consultar);
        validar_ubi = new String();
        lvDatos = (ListView) findViewById(R.id.lvDatos);
        datologin = (TextView)findViewById(R.id.parametrologinconsul);
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
                obtenerConsulta();

            }
        });
        mic_consu = (Button) findViewById(R.id.btn_mic_consultar);
        mic_consu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerVoz1();
            }
        });
    }

    private void obtenerConsulta()
    {
        if (ubicacion.getText().toString().isEmpty()){
            Toast.makeText(form_consultar.this, "Hay Campos Vacios", Toast.LENGTH_SHORT).show();
        }else{
            String url = "http://35.226.157.199/JSON/obtenerDatos.php?";
            String parametros ="producto="+ubicacion.getText().toString()+"&usr="+datologin;
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
        ArrayList<String> lista = new ArrayList<String>();
        try{
            JSONArray jsonArreglo = new JSONArray(respuesta);
            for (int i = 0; i <jsonArreglo.length(); i++){
                valor_prod = jsonArreglo.getJSONObject(i).getString("Ubicacion");
                lista.add(valor_prod);

            }
            ArrayAdapter<String> a = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
            lvDatos.setAdapter(a);

        }catch (Exception e1)
        {e1.printStackTrace();}
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

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


}
