package com.florian.projet.view;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.florian.projet.R;
import com.florian.projet.view.activity.ProductionActivity;
import com.florian.projet.view.activity.ProductivityActivity;
import com.florian.projet.view.activity.QualityActivity;
import com.florian.projet.view.activity.SupplyActivity;
import com.florian.projet.view.activity.TechniqueActivity;

public class MyDrawerMenu implements NavigationView.OnNavigationItemSelectedListener {
    private AppCompatActivity activity;
    private FragmentManager fragmentManager;

    public MyDrawerMenu(AppCompatActivity context) {
        this.activity = context;
        this.fragmentManager = activity.getSupportFragmentManager();

        initMenu();
    }

    private void initMenu() {

        NavigationView navigationView = (NavigationView) activity.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_production));

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        activity.getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return (id == R.id.action_settings);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
            Intent intent;

        if (activity.getClass() == getClassByItemId(id)) {
            DrawerLayout drawer = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;

        } else if (id == R.id.nav_production) {
            intent = new Intent(activity.getBaseContext(),ProductionActivity.class);
            activity.startActivity(intent);

        } else if (id == R.id.nav_productivity) {
            intent = new Intent(activity.getBaseContext(),ProductionActivity.class);
            activity.startActivity(intent);

        } else if (id == R.id.nav_supply) {
            intent = new Intent(activity.getBaseContext(),ProductionActivity.class);
            activity.startActivity(intent);

        } else if (id == R.id.nav_technique) {
            intent = new Intent(activity.getBaseContext(),ProductionActivity.class);
            activity.startActivity(intent);

        } else if (id == R.id.nav_quality) {
            intent = new Intent(activity.getBaseContext(),ProductionActivity.class);
            activity.startActivity(intent);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private Class getClassByItemId(int itemId) {
        switch (itemId) {
            case R.id.nav_production:
                return ProductionActivity.class;

            case R.id.nav_productivity:
                return ProductivityActivity.class;

            case R.id.nav_supply:
                return SupplyActivity.class;

            case R.id.nav_technique:
                return TechniqueActivity.class;

            case R.id.nav_quality:
                return QualityActivity.class;

            default:
                return null;
        }
    }
}
