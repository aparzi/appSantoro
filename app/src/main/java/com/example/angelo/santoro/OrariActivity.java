package com.example.angelo.santoro;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OrariActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayAdapter<String> adapter = null;
    private RequestQueue mRequestQueue = null;
    private GridView gridView = null;
    private String remoteAddress = null;

    private Response.Listener<JSONArray> getListener = new Response.Listener<JSONArray>()
    {
        @Override
        public void onResponse(JSONArray response) {

            for (int i=0; i<response.length(); i++) {
                try {
                    JSONObject j=response.getJSONObject(i);
                    adapter.add(j.getString("orario_partenza"));
                    adapter.add(j.getString("orario_arrivo"));
                }
                catch (JSONException e) {}
            }
        }

    };

    private Response.ErrorListener errorListener=new Response.ErrorListener()
    {
        @Override
        public void onErrorResponse(VolleyError err)
        {
            Toast.makeText(OrariActivity.this, "Errore di rete", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orari);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        adapter = new ArrayAdapter<String>(this, R.layout.layout_riga_orari,R.id.tvOrari);
        gridView = (GridView) findViewById(R.id.gridViewOrari);
        gridView.setAdapter(adapter);

        mRequestQueue= Volley.newRequestQueue(this);
        this.setRemoteAddress();
        JsonArrayRequest request = new JsonArrayRequest(this.getRemoteAddress(), getListener,errorListener);
        mRequestQueue.add(request);
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
            intent = new Intent(OrariActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_news) {
            intent = new Intent(OrariActivity.this, NewsActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_fermate) {
            intent = new Intent(OrariActivity.this, FermateActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_contact) {
            intent = new Intent(Intent.ACTION_VIEW);
            Uri data = Uri.parse("mailto:autolineesantoro@gmail.com?subject=Email utente app Santoro");
            intent.setData(data);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_info) {
            intent = new Intent(OrariActivity.this, InfoActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private String getRemoteAddress() {
        return this.remoteAddress;
    }

    private void setRemoteAddress() {
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            String paese = (String) intent.getExtras().get("paesePartenza");
            if (paese.equals("San Giovanni in Galdo")){
                paese = "San_giovanni_in_galdo";
            }
            this.remoteAddress = "http://www.angeloparziale.it/servizi/orari/"+ paese +"/"+ this.getIdPaeseDestinazione() +"";
        } else {
            this.remoteAddress = null;
        }
    }

    private int getIdPaeseDestinazione() {
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            String paese = (String) intent.getExtras().get("paeseDestinazione");
            switch (paese) {
                case "Campobasso":
                    return 1;
                case "Campodipietra":
                    return 2;
                case "San Giovanni in Galdo":
                    return 3;
                case "Toro":
                    return 4;
            }
        }
        return 0;
    }
}
