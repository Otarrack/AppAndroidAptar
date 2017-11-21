package com.florian.projet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final ListView listHome = (ListView) findViewById(R.id.listHome);

        final ArrayList<HashMap<String, String>> listItem = new ArrayList<>();
        HashMap<String,String> map;

        map = new HashMap<>();
        map.put("item_titre",getString(R.string.activite));
        map.put("item_description","...");
        map.put("item_img",String.valueOf(R.drawable.production));
        listItem.add(map);

        map = new HashMap<>();
        map.put("item_titre",getString(R.string.personnel));
        map.put("item_description","...");
        map.put("item_img",String.valueOf(R.drawable.personnel));
        listItem.add(map);

        map = new HashMap<>();
        map.put("item_titre",getString(R.string.planning));
        map.put("item_description","...");
        map.put("item_img",String.valueOf(R.drawable.planning));
        listItem.add(map);

        map = new HashMap<>();
        map.put("item_titre",getString(R.string.technique));
        map.put("item_description","...");
        map.put("item_img",String.valueOf(R.drawable.maintenance));
        listItem.add(map);

        map = new HashMap<>();
        map.put("item_titre",getString(R.string.qualite));
        map.put("item_description","...");
        map.put("item_img",String.valueOf(R.drawable.qualite));

        listItem.add(map);

        SimpleAdapter mSchedule = new SimpleAdapter(this.getBaseContext(), listItem, R.layout.home_item, new String[] {"item_img","item_titre","item_description"}, new int[] {R.id.item_img,R.id.item_titre,R.id.item_description});

        listHome.setAdapter(mSchedule);
        listHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            @SuppressWarnings("unchecked")
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                HashMap<String,String> map = (HashMap<String,String>) listHome.getItemAtPosition(position);

                String titre = map.get("item_titre");
                Intent intent = new Intent(getBaseContext(),MainViewPager.class);
                intent.putExtra("page",titre);
                startActivity(intent);
            }
        });
    }
}