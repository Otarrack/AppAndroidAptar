package com.florian.projet.view;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.florian.projet.R;
import com.florian.projet.viewModel.MainAsyncTask;
import com.florian.projet.viewModel.MyDrawerMenu;

public class MainActivity extends AppCompatActivity {
    MyDrawerMenu myDrawerMenu;
    MainAsyncTask mainAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDrawerMenu();

        mainAsyncTask = new MainAsyncTask();
        mainAsyncTask.execute();
        mainAsyncTask.isThreadRunnning.set(true);
    }

    private void initDrawerMenu() {
        this.myDrawerMenu = new MyDrawerMenu(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
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

    @Override
    protected void onDestroy() {
        mainAsyncTask.isThreadRunnning.set(false);
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        mainAsyncTask.isThreadPausing.set(true);
        super.onPause();
    }

    @Override
    protected void onResume() {
        mainAsyncTask.isThreadPausing.set(false);
        super.onResume();
    }
}