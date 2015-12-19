package kr.ac.kookmin.eomjenogho.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Read extends AppCompatActivity {

    ArrayList<Data> listTag;
    ArrayList<Data> listLat;
    ArrayList<String> outputList;
    ArrayAdapter<String> adapter;

    ListView listView;
    Button btnBack;
    Button btnTagResult;
    Button btnLatlngResult;
    Button btnAllList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        listView = (ListView) findViewById(R.id.listView);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnTagResult = (Button) findViewById(R.id.btnTagResult);
        btnLatlngResult = (Button) findViewById(R.id.btnLatlngResult);
        btnAllList = (Button) findViewById(R.id.btnAllList);

        Intent intent = getIntent();
        listTag = (ArrayList<Data>) intent.getSerializableExtra("listTag");
        listLat = (ArrayList<Data>) intent.getSerializableExtra("listLat");

        outputList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, outputList);
        listView.setAdapter(adapter);

        // 사건 통계 버튼
        btnTagResult.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listTag.size() != 0) {
                    outputList.clear();
                    makeTagList();
                    adapter.notifyDataSetChanged();
                } else
                    Toast.makeText(getApplicationContext(), "입력된 데이터가 없습니다", Toast.LENGTH_SHORT).show();

            }
        });

        // 위치 통계 버튼
        btnLatlngResult.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listLat.size() != 0) {
                    outputList.clear();
                    makeLatlngList();
                    adapter.notifyDataSetChanged();
                } else
                    Toast.makeText(getApplicationContext(), "입력된 데이터가 없습니다", Toast.LENGTH_SHORT).show();
            }
        });

        // 전체 기록 버튼
        btnAllList.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (listTag.size() != 0) {
                    outputList.clear();
                    makeAllList();
                    adapter.notifyDataSetChanged();
                } else
                    Toast.makeText(getApplicationContext(),"입력된 데이터가 없습니다",Toast.LENGTH_SHORT).show();
            }
        });

        // 뒤로가기 버튼
        btnBack.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    //Tag가 같은 사건의 수를 구하여 ArrayList에 추가한다
    public void makeTagList() {
        String s1 = listTag.get(0).getTag();
        String s2;
        int count = 1;
        int j = 0;
        int size = listTag.size();

        if (size == 1) {
            outputList.add(j, s1 + ": " + count);
        } else {
            for (int i = 1; i < size; i++) {
                s2 = listTag.get(i).getTag();

                if (s1.equals(s2) && i == size - 1) {
                    count++;
                    outputList.add(j, s2 + ": " + count);
                } else if (s1.equals(s2)) {
                    count++;
                } else {
                    if (size == 2) {
                        outputList.add(j, s1 + ": " + count);
                        j++;
                        outputList.add(j, s2 + ": " + count);
                    } else if (i == size - 1) {
                        outputList.add(j, s1 + ": " + count);
                        j++;
                        count = 1;
                        outputList.add(j, s2 + ": " + count);
                    } else {
                        outputList.add(j, s1 + ": " + count);
                        j++;
                        count = 1;
                        s1 = s2;
                    }
                }
            }
        }
    }

    // Lat, Lng가 같은 사건의 수를 구하여 ArrayList에 추가한다
    public void makeLatlngList() {
        double lat1 = listLat.get(0).getLat();
        double lng1 = listLat.get(0).getLng();
        double lat2;
        double lng2;
        int count = 1;
        int j = 0;
        int size = listLat.size();

        if (size == 1) {
            outputList.add(j, "(" + lat1 + ", " + lng1 + ") : " + count);
        } else {
            for (int i = 1; i < size; i++) {
                lat2 = listLat.get(i).getLat();
                lng2 = listLat.get(i).getLng();

                if (lat1 == lat2 && lng1 == lng2 && i == size - 1) {
                    count++;
                    outputList.add(j, "(" + lat1 + ", " + lng1 + ") : " + count);
                } else if (lat1 == lat2 && lng1 == lng2) {
                    count++;
                } else if (lat1 != lat2 || lng1 != lng2) {
                    if (size == 2) {
                        outputList.add(j, "(" + lat1 + ", " + lng1 + ") : " + count);
                        j++;
                        outputList.add(j, "(" + lat2 + ", " + lng2 + ") : " + count);
                    } else if (i == size - 1) {
                        outputList.add(j, "(" + lat1 + ", " + lng1 + ") : " + count);
                        j++;
                        count = 1;
                        outputList.add(j, "(" + lat2 + ", " + lng2 + ") : " + count);
                    } else {
                        outputList.add(j, "(" + lat1 + ", " + lng1 + ") : " + count);
                        j++;
                        count = 1;
                        lat1 = lat2;
                        lng1 = lng2;
                    }
                }
            }
        }
    }

    // 모든 사건을 ArrayList에 추가한다
    public void makeAllList(){
        for (int i=0; i<listTag.size(); i++){
            String s = "Tag: " + listTag.get(i).getTag() + "\n Story: " + listTag.get(i).getStory();

            outputList.add(i,s);
        }
    }
}
