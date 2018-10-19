package cl.avarti.fifoon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menuoperador extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuoperador);

        Button btn_alma = (Button)findViewById(R.id.btn_almacenar);
        btn_alma.setOnClickListener(this);
    }
    public void onClick(View v)
    {
        if(v.getId()==R.id.btn_almacenar)
        {
            Intent intent = new Intent(Menuoperador.this, FormAlmacenar.class);
            startActivity(intent);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
