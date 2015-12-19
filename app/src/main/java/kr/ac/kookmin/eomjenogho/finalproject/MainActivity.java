package kr.ac.kookmin.eomjenogho.finalproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    DatabaseManager manager;

    myLocation location;

    EditText eTag;
    EditText eText;
    Button btnWrite;
    Button btnRead;
    Button btnDel;

    ArrayList<Data> listOrderByTag;
    ArrayList<Data> listOrderByLat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = new DatabaseManager(MainActivity.this, "logList.db", null, 1);

        location = new myLocation(this);

        eTag = (EditText) findViewById(R.id.editTextTag);
        eText = (EditText) findViewById(R.id.editTextStory);
        btnWrite = (Button) findViewById(R.id.btnWrite);
        btnRead = (Button) findViewById(R.id.btnRead);
        btnDel = (Button) findViewById(R.id.btnDel);

        // 작성 버튼
        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String inputTag = eTag.getText().toString();
                String inputText = eText.getText().toString();
                double lat = location.getLatitude();
                double lng = location.getLongitude();

                if(inputTag.equals("") || inputText.equals("")){
                    Toast.makeText(getApplicationContext(),"기록을 먼저 하세요", Toast.LENGTH_LONG).show();
                }
                else {
                    insert(inputTag, inputText, lat, lng);
                    Toast.makeText(getApplicationContext(),"기록이 입력되었습니다",Toast.LENGTH_SHORT).show();
                }
            }
        });

        listOrderByTag = new ArrayList<>();
        listOrderByLat = new ArrayList<>();

        // 통계 버튼 -> ReadActivity
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listOrderByTag.clear();
                listOrderByLat.clear();
                selectAllOrderByTag();
                selectAllOrderByLat();
                Intent intent = new Intent(getApplicationContext(), Read.class);
                intent.putExtra("listTag", listOrderByTag);
                intent.putExtra("listLat",listOrderByLat);
                startActivity(intent);
            }
        });

        // 기록 삭제 버튼
        btnDel.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                selectAllOrderByTag();
                int size = listOrderByTag.size();
                listOrderByTag.clear();

                if(size != 0){
                    manager.remove(db);
                    manager.onCreate(db);
                    Toast.makeText(getApplicationContext(),"기록이 삭제되었습니다", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"기록이 없습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void insert(String tag, String story, double lat, double lng){
        db = manager.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("tag", tag);
        contentValues.put("story", story);
        contentValues.put("lat", lat);
        contentValues.put("lng", lng);
        db.insert("logTable", null, contentValues);
    }

    /*
    Tag를 기준으로 오름차순으로 정렬
     */
    public void selectAllOrderByTag(){
        String sql = "select * from logTable order by tag;";
        db = manager.getReadableDatabase();
        Cursor results = db.rawQuery(sql,null);
        results.moveToFirst();

        int count = 0;
        while (!results.isAfterLast()) {
            String tag = results.getString(1);
            String story = results.getString(2);
            Double lat = Double.parseDouble(results.getString(3));
            Double lng = Double.parseDouble(results.getString(4));

            listOrderByTag.add(count,new Data(tag,story,lat,lng));
            count++;
            results.moveToNext();
        }
        results.close();
    }

    /*
    Lat을 기준으로 오름차순으로 정렬
     */
    public void selectAllOrderByLat(){
        String sql = "select * from logTable order by lat;";
        db = manager.getReadableDatabase();
        Cursor results = db.rawQuery(sql,null);
        results.moveToFirst();

        int count = 0;
        while (!results.isAfterLast()) {
            String tag = results.getString(1);
            String story = results.getString(2);
            Double lat = Double.parseDouble(results.getString(3));
            Double lng = Double.parseDouble(results.getString(4));

            listOrderByLat.add(count,new Data(tag,story,lat,lng));
            count++;
            results.moveToNext();
        }
        results.close();
    }
}