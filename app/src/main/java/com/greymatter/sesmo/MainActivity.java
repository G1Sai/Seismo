package com.greymatter.sesmo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.greymatter.sesmo.Adapter.ViewPagerAdapter;
import com.greymatter.sesmo.Service.MyService;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView nav_view;

    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    String area;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        area = sharedPref.getString("area","null");

        initializeNav();

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        Intent i = new Intent(this, MyService.class);
        startService(i);

    }

    private void initializeNav() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer);
        nav_view = findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(this);


        toggle = new ActionBarDrawerToggle(this,  drawerLayout, toolbar ,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        switch(item.getItemId()) {
            case R.id.felt:
                startActivity(new Intent(this,PrecautionActivity.class));
                break;
            case  R.id.global:
                startActivity(new Intent(this,GlobalActivity.class));
                break;
            case  R.id.manual:
                startActivity(new Intent(this,ManualActivity.class));
                break;
            case  R.id.terms:
                startActivity(new Intent(this,TermsActivity.class));
                break;
            default:
                Toast.makeText(this, "coming soon...", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

}