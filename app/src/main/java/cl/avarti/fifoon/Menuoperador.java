package cl.avarti.fifoon;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Menuoperador extends AppCompatActivity implements View.OnClickListener {

    Button btnsidebar;

    private TextView datologin;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_CONTACTS = 1;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private static final int MY_PERMISSIONS_REQUEST_INTERNET = 1;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuoperador);

        Button btn_alma = (Button)findViewById(R.id.btn_almacenar);
        Button btn_mover = (Button)findViewById(R.id.btn_mover);
        Button btn_consultar = (Button)findViewById(R.id.btn_consultar);
        Button btn_salida = (Button)findViewById(R.id.btn_salida);
        btn_alma.setOnClickListener(this);
        btn_consultar.setOnClickListener(this);
        btn_mover.setOnClickListener(this);
        btn_salida.setOnClickListener(this);
        datologin = (TextView)findViewById(R.id.parametrologin);
        btnsidebar = (Button)findViewById(R.id.btnsidebarin);

        //TRAER DATO USUARIO LOGIN A ESTE ACTIVITY
        String dato = getIntent().getStringExtra("dato");
        String parametroActivity = getIntent().getStringExtra("parametro");
        //INSERTAR DATO OBTENIDO EN TEXTVIEW
        datologin.setText("Bienvenido: " + dato);

        btnsidebar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abc = new Intent(Menuoperador.this, MenuSidebarOperador.class);
                abc.putExtra("param",getIntent().getStringExtra("dato"));
                startActivity(abc);
            }
        });

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_CONTACTS},
                        MY_PERMISSIONS_REQUEST_WRITE_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CALL_PHONE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_CALL_PHONE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.INTERNET)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.INTERNET},
                        MY_PERMISSIONS_REQUEST_INTERNET);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

    }
    public void onClick(View v)
    {
        if(v.getId()==R.id.btn_almacenar)
        {
            Intent intent = new Intent(Menuoperador.this, FormAlmacenar.class);
            intent.putExtra("param",getIntent().getStringExtra("dato"));
            startActivity(intent);
        }
        if(v.getId()==R.id.btn_mover)
        {
            Intent intent = new Intent(Menuoperador.this, FormMover.class);
            intent.putExtra("param",getIntent().getStringExtra("dato"));
            startActivity(intent);
        }
        if(v.getId()==R.id.btn_consultar)
        {
            Intent intent = new Intent(Menuoperador.this, form_consultar.class);
            intent.putExtra("param",getIntent().getStringExtra("dato"));
            startActivity(intent);
        }
        if(v.getId()==R.id.btn_salida)
        {
            Intent intent = new Intent(Menuoperador.this, form_Salida.class);
            intent.putExtra("param",getIntent().getStringExtra("dato"));
            startActivity(intent);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}
