package com.sicaray.artspot.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sicaray.artspot.R;
import com.sicaray.artspot.util.DownloadImageTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
    Tela destinada aos desenvolvedores apenas para testes. Pode ser acessada pelo menu da barra
    lateral da vitrine. Essa opção deve ser desabilitada no release final.
 */

public class TesteDebug extends AppCompatActivity
{
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_debug);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Modo Debug");

        requestQueue = Volley.newRequestQueue(this);

        //Obter localização via wifi/3G
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if(!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));

        LocationListener locationListener = new LocationListener()
        {
            public void onLocationChanged(Location location)
            {
                TextView editTextLocal = (TextView) findViewById(R.id.tela_teste_debug_textView);
                String address = "Latutude: " + Double.toString(location.getLatitude()) + " Longitude: " + Double.toString(location.getLongitude());
                editTextLocal.setText(address);
                Log.d("COORDINATES", address);
            }

            public void onStatusChanged(String provider, int status, Bundle extras){}
            public void onProviderEnabled(String provider){}
            public void onProviderDisabled(String provider) {}
        };

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        else
            Log.d("COORDINATES", "Permission Denied");
    }

    public void OnClickButtonRequest(View view)
    {
        SharedPreferences sharedPreferences = getSharedPreferences("ConfigFile", Context.MODE_PRIVATE);
        String auth_token = sharedPreferences.getString("auth_token", "none");

        //GETRequest(this);
        //POSTRequest(this);
    }

    public void POSTRequest(final Context context)
    {
        final TextView textView = (TextView) findViewById(R.id.tela_teste_debug_textView);

        //Seta URL
        String requestURL = "http://httpbin.org/post";

        //Cria json para ser enviado no body
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", "Anderson Urbano");
            jsonObject.put("email", "aafu@cin.ufpe.br");
            jsonObject.put("password", "123456");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Faz Request POST
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(requestURL, jsonObject,

                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        textView.setText(response.toString());
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(context, "Falha de comunicação com o servidor. " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        )//Seta Header
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String>  params = new HashMap<String, String>();
                //params.put("Content-Type", "application/json");
                params.put("X-ArtSpot-Auth-Token", "azcsabqsqhsdbqk");
                return params;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }

    public void GETRequest(final Context context)
    {
        final TextView textView = (TextView) findViewById(R.id.tela_teste_debug_textView);

        //Seta URL
        String requestURL = "http://httpbin.org/get";

        //Request GET
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(requestURL, null,

                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        textView.setText(response.toString());
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(context, "Falha de comunicação com o servidor. " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    public void GETRequestJSONArray(final Context context)
    {
        final TextView textView = (TextView) findViewById(R.id.tela_teste_debug_textView);

        //Seta URL
        String requestURL = "http://httpbin.org/get";

        //Request GET
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(requestURL,

                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        textView.setText(response.toString());
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(context, "Falha de comunicação com o servidor. " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );

        requestQueue.add(jsonArrayRequest);
    }
}
