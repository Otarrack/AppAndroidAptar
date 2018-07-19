package com.florian.projet.view;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.florian.projet.R;
import com.florian.projet.view.fragment.ArticlePerformanceFragment;
import com.florian.projet.view.fragment.FavoriteArticleFragment;
import com.florian.projet.view.fragment.FavoritePresseFragment;
import com.florian.projet.view.fragment.MachinePerformanceFragment;
import com.florian.projet.view.fragment.PressePerformanceFragment;

public class MyDrawerMenu implements NavigationView.OnNavigationItemSelectedListener {
    private AppCompatActivity activity;
    private FragmentManager fragmentManager;

    public MyDrawerMenu(AppCompatActivity context) {
        this.activity = context;
        this.fragmentManager = activity.getSupportFragmentManager();

        initMenu();
    }

    private void initMenu() {
        NavigationView navigationView = activity.findViewById(R.id.main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_favorites_presse));
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = new Fragment();


        if (id == R.id.nav_favorites_presse) {
            fragment = FavoritePresseFragment.newInstance();
            activity.setTitle(R.string.menu_favorite_presse_title);

        } else if (id == R.id.nav_favorites_article) {
            fragment = FavoriteArticleFragment.newInstance();
            activity.setTitle(R.string.menu_favorite_article_title);

        } else if (id == R.id.nav_perf_percent) {
            fragment = MachinePerformanceFragment.newInstance();
            activity.setTitle(R.string.menu_perf_percent_title);

        } else if (id == R.id.nav_perf_article) {
            fragment = ArticlePerformanceFragment.newInstance();
            activity.setTitle(R.string.menu_perf_article_title);

        } else if (id == R.id.nav_perf_presse) {
            fragment = PressePerformanceFragment.newInstance();
            activity.setTitle(R.string.menu_perf_presse_title);
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment_container, fragment);
        fragmentTransaction.commit();

        DrawerLayout drawer = activity.findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
