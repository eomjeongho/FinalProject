package kr.ac.kookmin.eomjenogho.finalproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Write extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        Button btnMake = (Button) findViewById(R.id.make);
        btnMake.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                EditText editTextTag = (EditText) findViewById(R.id.Tag);
            }
        });
    }
}
