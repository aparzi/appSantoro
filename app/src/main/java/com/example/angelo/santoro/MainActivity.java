package com.example.angelo.santoro;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
        navigationView.setNavigationItemSelectedListener(this);

        final Button buttonVai = (Button) findViewById(R.id.buttonVai);
        buttonVai.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final Spinner spinnerPartenza = (Spinner) findViewById(R.id.spinner);
                final Spinner spinnerDestinazione = (Spinner) findViewById(R.id.spinner2);
                String partenza = spinnerPartenza.getSelectedItem().toString();
                String destinazione = spinnerDestinazione.getSelectedItem().toString();
                if (checkChoice(partenza, destinazione)) {
                    showAlertDialog();
                    //Toast.makeText(MainActivity.this, "Partenza e Destinazione non possono coincidere", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, OrariActivity.class);
                    intent.putExtra("paesePartenza", partenza);
                    intent.putExtra("paeseDestinazione", destinazione);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().findItem(R.id.nav_news).setCheckable(false);
        navigationView.getMenu().findItem(R.id.nav_fermate).setCheckable(false);
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
            intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_news) {
            intent = new Intent(MainActivity.this, NewsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_fermate) {
            intent = new Intent(MainActivity.this, FermateActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_contact) {
            intent = new Intent(Intent.ACTION_VIEW);
            Uri data = Uri.parse("mailto:autolineesantoro@gmail.com?subject=Email utente app Santoro");
            intent.setData(data);
            startActivity(intent);
        } else if (id == R.id.nav_info) {
            intent = new Intent(MainActivity.this, InfoActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // controlla se la partenza e la destinazione sono uguali
    private boolean checkChoice (String pPartenza, String pDestinazione) {
        if (pPartenza.equals(pDestinazione)) {
            return true;
        }
        return false;
    }

    private void showAlertDialog () {
        AlertDialog.Builder miaAlert = new AlertDialog.Builder(this);
        miaAlert.setIcon(R.mipmap.ic_error);
        miaAlert.setTitle("ATTENZIONE");
        miaAlert.setMessage("Partenza e Destinazione non possono coincidere.");
        AlertDialog alert = miaAlert.create();
        alert.show();
    }
}
