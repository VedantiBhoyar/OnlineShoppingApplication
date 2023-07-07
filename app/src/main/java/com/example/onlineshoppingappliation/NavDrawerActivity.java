package com.example.onlineshoppingappliation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.onlineshoppingappliation.ui.cart.CartFragment;
import com.example.onlineshoppingappliation.ui.home.HomeFragment;
import com.google.android.material.navigation.NavigationView;

public class NavDrawerActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();
        loadFragment(new HomeFragment());


        // Setting Value Of Header Email
        View headerView = navigationView.getHeaderView(0);
        TextView EmailAddress = (TextView) headerView.findViewById(R.id.textView4);

        // Getting Email From Login Activity
        String LoginEmail = getIntent().getStringExtra("EmailKey");
        EmailAddress.setText(LoginEmail);

        // Fetching User Name associated with LoginEmail From Sqlite

        Database database = new Database(NavDrawerActivity.this);
        TextView Name = headerView.findViewById(R.id.textView3);
        String LoginUserName=database.userName(LoginEmail);
        Name.setText(LoginUserName);

        // Performing Action after menu is selected
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.home) {
                    loadFragment(new HomeFragment());
                } else if (id == R.id.info) {
                    Intent intent=new Intent(NavDrawerActivity.this,Profile.class);
                    intent.putExtra("UserName",LoginUserName);
                    intent.putExtra("Email",LoginEmail);
                    startActivity(intent);
                } else if (id == R.id.cart) {
                    loadFragment(new CartFragment());

                } else {

                    Toast.makeText(NavDrawerActivity.this, "Sign Out", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(NavDrawerActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        // ft.add(R.id.container, fragment);
        ft.replace(R.id.container, fragment, "NewFragmentTag");
        ft.commit();
        ft.addToBackStack(null);

    }


}