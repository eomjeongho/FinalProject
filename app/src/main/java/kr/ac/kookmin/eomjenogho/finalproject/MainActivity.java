package kr.ac.kookmin.eomjenogho.finalproject;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    String dbName = "List.db";
    String tableName = "ListTable";
    int dbMode = Context.MODE_PRIVATE;



    EditText eTag;
    EditText eText;
    Button btnWrite;
    Button btnRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = openOrCreateDatabase(dbName,dbMode,null);
        createTable();

        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria cri = new Criteria();
        cri.setAccuracy(Criteria.ACCURACY_FINE);
        cri.setCostAllowed(true);
        cri.setPowerRequirement(Criteria.POWER_MEDIUM);
        cri.setSpeedRequired(true);
        final String bestProvider =
                locationManager.getBestProvider(cri, true);

        eTag = (EditText) findViewById(R.id.editTextTag);
        eText = (EditText) findViewById(R.id.editTextText);
        btnWrite = (Button) findViewById(R.id.btnWrite);
        btnRead = (Button) findViewById(R.id.btnRead);

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputTag = eTag.getText().toString();
                String inputText = eText.getText().toString();
                Location myLocation =
                        locationManager.getLastKnownLocation(bestProvider);
                double lat = myLocation.getLatitude();
                double lng = myLocation.getLongitude();
                insert(inputTag,inputText,lat,lng);
            }
        });


        btnRead.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(),Read.class);
                startActivity(intent);
            }
        });
    }

    private void createTable() {
        try {
            String sql = "create table " + tableName + "(id integer primary key autoincrement, "
                    + "tag text not null, " + "text text not null, "
                    + "lat real not null, " + "lng real not null";
            db.execSQL(sql);
        } catch (android.database.sqlite.SQLiteException e) {
            Log.d("Lab sqlite", "error: " + e);
        }
    }

    public void insert(String tag, String text, double lat, double lng){
        String sql = "insert into " + tableName + "values(NULL, '" + tag + "', '"+ text + "', '"
                + lat + "', '" + lng + "');";
        db.execSQL(sql);
    }
}

