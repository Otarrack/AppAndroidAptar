package com.florian.projet.viewModel;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.florian.projet.R;
import com.florian.projet.view.PlanningFragment;
import com.florian.projet.view.ProductionFragment;
import com.florian.projet.view.QualityFragment;
import com.florian.projet.view.ProductivityFragment;
import com.florian.projet.view.TechniqueFragment;

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return false;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = new Fragment();

        if (id == R.id.nav_production) {
            fragment = new ProductionFragment();
            activity.setTitle(R.string.production);

        } else if (id == R.id.nav_productivity) {
            fragment = new ProductivityFragment();
            activity.setTitle(R.string.productivity);

        } else if (id == R.id.nav_planning) {
            fragment = new PlanningFragment();
            activity.setTitle(R.string.planning);

        } else if (id == R.id.nav_technique) {
            fragment = new TechniqueFragment();
            activity.setTitle(R.string.technique);

        } else if (id == R.id.nav_quality) {
            fragment = new QualityFragment();
            activity.setTitle(R.string.quality);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment_container, fragment);
        fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
