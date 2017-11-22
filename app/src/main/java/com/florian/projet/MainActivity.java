package com.florian.projet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.florian.projet.ViewModel.MyDrawerMenu;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    MyDrawerMenu myDrawerMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.myDrawerMenu = new MyDrawerMenu(this);

        final ListView listHome = (ListView) findViewById(R.id.listHome);

        final ArrayList<HashMap<String, String>> listItem = new ArrayList<>();
        HashMap<String,String> map;

        map = new HashMap<>();
        map.put("item_titre",getString(R.string.production));
        map.put("item_description","...");
        map.put("item_img",String.valueOf(R.drawable.production));
        listItem.add(map);

        map = new HashMap<>();
        map.put("item_titre",getString(R.string.staff));
        map.put("item_description","...");
        map.put("item_img",String.valueOf(R.drawable.staff));
        listItem.add(map);

        map = new HashMap<>();
        map.put("item_titre",getString(R.string.planning));
        map.put("item_description","...");
        map.put("item_img",String.valueOf(R.drawable.planning));
        listItem.add(map);

        map = new HashMap<>();
        map.put("item_titre",getString(R.string.technique));
        map.put("item_description","...");
        map.put("item_img",String.valueOf(R.drawable.technique));
        listItem.add(map);

        map = new HashMap<>();
        map.put("item_titre",getString(R.string.quality));
        map.put("item_description","...");
        map.put("item_img",String.valueOf(R.drawable.quality));

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return myDrawerMenu.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return myDrawerMenu.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}