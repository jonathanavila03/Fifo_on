package cl.avarti.fifoon;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class FormAlmacenar extends AppCompatActivity {
    EditText edit_producto;
    EditText edit_ubicacion;
    EditText edit_fechav;
    Button btn_gua_alma;
    Button btn_gua;
    private static final String REGISTER_URL="http://52.67.142.80/jsonfifon/almacenar.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_almacenar);
        edit_producto = (EditText) findViewById(R.id.txt_producto);
        edit_ubicacion= (EditText) findViewById(R.id.txt_ubicacion);
        edit_fechav = (EditText) findViewById(R.id.txt_fechav);
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
                Intent intentMenu =new Intent(FormAlmacenar.this,Menuoperador.class);
                startActivity(intentMenu);
            }
        });
    }

    private void almacenar() {
        String producto = edit_producto.getText().toString().trim().toLowerCase();
        String ubicacion = edit_ubicacion.getText().toString().trim().toLowerCase();
        String fechav = edit_fechav.getText().toString().trim().toLowerCase();
        almacenar_pro(producto, ubicacion, fechav);
    }

    private void almacenar_pro(String producto, String ubicacion, String fechav){
        String urlSuffix = "?producto=" + producto + "&ubicacion=" + ubicacion + "&fechav=" + fechav;
        class AlmacenarEntrada extends AsyncTask<String, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(FormAlmacenar.this, "Espere PorFavor", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),"Entrada Registrada", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferReader=null;
                try {
                    URL url=new URL(REGISTER_URL+s);
                    HttpURLConnection con=(HttpURLConnection)url.openConnection();
                    bufferReader=new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String result;
                    result=bufferReader.readLine();
                    return  result;

                }catch (Exception e){
                    return null;
                }
            }

        }
        AlmacenarEntrada ur=new AlmacenarEntrada();
        ur.execute(urlSuffix);
    }
}
