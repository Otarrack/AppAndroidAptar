package com.florian.projet.view;

import android.graphics.Typeface;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.florian.projet.R;
import com.florian.projet.view.fragment.menu.ArticleFragment;
import com.florian.projet.view.fragment.menu.MachineFragment;
import com.florian.projet.view.fragment.menu.SiteFragment;

public class MyDrawerMenu implements NavigationView.OnNavigationItemSelectedListener {
    private AppCompatActivity activity;
    private FragmentManager fragmentManager;

    public MyDrawerMenu(AppCompatActivity context) {
        this.activity = context;
        this.fragmentManager = activity.getSupportFragmentManager();

        initMenu();
    }

    private void initMenu() {
        NavigationView navigationView = activity.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_site));
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = new Fragment();


        if (id == R.id.nav_site) {
            fragment = SiteFragment.newInstance();
            activity.setTitle(R.string.menu_site_title);

        } else if (id == R.id.nav_machine) {
            fragment = MachineFragment.newInstance();
            activity.setTitle(R.string.menu_machine_title);

        } else if (id == R.id.nav_article) {
            fragment = ArticleFragment.newInstance();
            activity.setTitle(R.string.menu_article_title);

        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment_container, fragment);
        fragmentTransaction.commit();

        DrawerLayout drawer = activity.findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
