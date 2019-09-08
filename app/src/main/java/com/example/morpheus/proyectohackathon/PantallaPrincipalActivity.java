package com.example.morpheus.proyectohackathon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.morpheus.proyectohackathon.Fragments.MenuInicial;
import com.google.android.material.navigation.NavigationView;

public class PantallaPrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    private NavigationView navigationView;
    private Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        {
            @Override
            //*OCULTA EL TECLADO CUANDO PIERDE EL FOCO*/
            public void onDrawerClosed(View drawerView)
            {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
            @Override
            public void onDrawerOpened(View drawerView)
            {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = new MenuInicial();
        fragmentManager.beginTransaction().replace(R.id.contentPrincial, fragment).commit();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //View header = navigationView.getHeaderView(0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
