package com.example.angelo.santoro;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class FermateActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Intent intent = null;
    private NavigationView navigationView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fermate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().findItem(R.id.nav_fermate).setChecked(true);
        navigationView.setNavigationItemSelectedListener(this);
        this.citySelected();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;
        if (id == R.id.nav_home) {
            intent = new Intent(FermateActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_news) {
            intent = new Intent(FermateActivity.this, NewsActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_fermate) {
            intent = new Intent(FermateActivity.this, FermateActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_contact) {
            intent = new Intent(Intent.ACTION_VIEW);
            Uri data = Uri.parse("mailto:autolineesantoro@gmail.com?subject=Email utente app Santoro");
            intent.setData(data);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_info) {
            intent = new Intent(FermateActivity.this, InfoActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    private void citySelected() {
        final Button btnCb = (Button) findViewById(R.id.cb);
        final Button btnCdp = (Button) findViewById(R.id.cdp);
        final Button btnSg = (Button) findViewById(R.id.sg);
        final Button btnToro = (Button) findViewById(R.id.toro);
        // se l'utente sceglie campobasso
        btnCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(FermateActivity.this, ViewFermate.class);
                intent.putExtra("id_paese", 1);
                navigationView.getMenu().findItem(R.id.nav_fermate).setChecked(false);
                startActivity(intent);
                finish();
            }
        });
        // se l'utente sceglie campodipietra
        btnCdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(FermateActivity.this, ViewFermate.class);
                intent.putExtra("id_paese", 2);
                navigationView.getMenu().findItem(R.id.nav_fermate).setChecked(false);
                startActivity(intent);
                finish();
            }
        });
        // se l'utente sceglie san giovanni
        btnSg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(FermateActivity.this, ViewFermate.class);
                intent.putExtra("id_paese", 3);
                navigationView.getMenu().findItem(R.id.nav_fermate).setChecked(false);
                startActivity(intent);
                finish();
            }
        });
        // se l'utente sceglie toro
        btnToro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(FermateActivity.this, ViewFermate.class);
                intent.putExtra("id_paese", 4);
                navigationView.getMenu().findItem(R.id.nav_fermate).setChecked(false);
                startActivity(intent);
                finish();
            }
        });
    }
}
