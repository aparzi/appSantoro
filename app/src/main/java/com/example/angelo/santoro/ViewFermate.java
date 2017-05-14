package com.example.angelo.santoro;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewFermate extends AppCompatActivity {

    private String Remote_address = null;
    private ArrayAdapter<String> adapter = null;
    private RequestQueue mRequestQueue = null;
    private ListView listView = null;

    private Response.Listener<JSONArray> getListener = new Response.Listener<JSONArray>()
    {
        @Override
        public void onResponse(JSONArray response) {

            for (int i=0; i<response.length(); i++) {
                try {
                    JSONObject j=response.getJSONObject(i);
                    adapter.add(j.getString("nome_fermata"));
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
        Toast.makeText(ViewFermate.this, "Errore di rete", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_fermate);
        this.setImageMap();
        this.setRemoteAddress();
        adapter = new ArrayAdapter<String>(this, R.layout.layout_riga_fermate,R.id.testo_fermata);
        listView = (ListView) findViewById(R.id.list_fermate);

        listView.setAdapter(adapter);

        mRequestQueue= Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(this.Remote_address, getListener,errorListener);
        mRequestQueue.add(request);
    }

    private void setRemoteAddress() {
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            int id_paese = (int) intent.getExtras().get("id_paese");
            this.Remote_address = "http://www.angeloparziale.it/servizi/fermate/"+ id_paese +"";
        } else {
            this.Remote_address = null;
        }
    }

    private void setImageMap () {
        Intent intent = getIntent();
        ImageView imageMap = (ImageView) findViewById(R.id.imgMap);
        if (intent.getExtras() != null) {
            int id_paese = (int) intent.getExtras().get("id_paese");
            if (id_paese == 1) {
                Drawable drawable  = getResources().getDrawable(R.drawable.map_cb);
                imageMap.setImageDrawable(drawable);
            } else if (id_paese == 2) {
                Drawable drawable  = getResources().getDrawable(R.drawable.map_cdp);
                imageMap.setImageDrawable(drawable);
            } else if (id_paese == 3) {
                Drawable drawable  = getResources().getDrawable(R.drawable.map_sg);
                imageMap.setImageDrawable(drawable);
            } else if (id_paese == 4) {
                Drawable drawable  = getResources().getDrawable(R.drawable.map_toro);
                imageMap.setImageDrawable(drawable);
            }
        }
    }
}
