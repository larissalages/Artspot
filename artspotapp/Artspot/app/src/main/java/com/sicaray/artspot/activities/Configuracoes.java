package com.sicaray.artspot.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sicaray.artspot.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Configuracoes extends AppCompatActivity
{
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Configurações");

        //Fila de requests do volley
        requestQueue = Volley.newRequestQueue(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        //Preenche os formularios com as preferencias do usuário
        SharedPreferences sharedPreferences = getSharedPreferences("ConfigFile", Context.MODE_PRIVATE);
        String srtNome = sharedPreferences.getString("name", "none");
        String srtCidade = sharedPreferences.getString("city", "none");
        String srtRaio = sharedPreferences.getString("radius", "none");

        EditText nomeUser = (EditText) findViewById(R.id.tela_configuracoes_nome);
        nomeUser.setText(srtNome);

        EditText localInserido = (EditText) findViewById(R.id.tela_configuracoes_local);
        localInserido.setText(srtCidade);

        EditText raioInserido = (EditText) findViewById(R.id.tela_configuracoes_raio);
        raioInserido.setText(srtRaio);
    }

    public void OnClickButtonSalvar(View view)
    {
        EditText nomeUser = (EditText) findViewById(R.id.tela_configuracoes_nome);
        String sNome = nomeUser.getText().toString();

        EditText localInserido = (EditText) findViewById(R.id.tela_configuracoes_local);
        String sNomeLocal = localInserido.getText().toString();

        EditText raioInserido = (EditText) findViewById(R.id.tela_configuracoes_raio);
        String sRaio = raioInserido.getText().toString();

        Geocoder geocoder;
        geocoder = new Geocoder(this, Locale.getDefault());

        boolean salvarOk = true;

        if (sNome.isEmpty() || sNomeLocal.isEmpty() || sRaio.isEmpty())
        {
            Toast.makeText(this, "Pelo menos um dos campos está em branco!", Toast.LENGTH_SHORT).show();
            salvarOk = false;
        }

        //Dado um endereço, pegar as coordenadas geográficas
        //Dado um endereço, pegar as coordenadas geográficas
        double latitude = 0.0;
        double longitude = 0.0;
        try
        {
            ArrayList<Address> adresses = (ArrayList<Address>) geocoder.getFromLocationName(sNomeLocal, 1);

            if(!adresses.isEmpty())
            {
                latitude = adresses.get(0).getLatitude();
                longitude = adresses.get(0).getLongitude();
            }
            else
            {
                salvarOk = false;
                Toast.makeText(this, "Endereço não encontrado. Por favor insira o endereço novamente.", Toast.LENGTH_SHORT).show();
            }
        }
        catch (IOException e)
        {
            salvarOk = false;
            Toast.makeText(this, "Um problema ocorreu. Verifique se seu aparelho esta conectado a internet.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        if (salvarOk)
        {
            //Armazena informações do local
            SharedPreferences sharedPreferences = getSharedPreferences("ConfigFile", Context.MODE_PRIVATE);
            String auth_token = sharedPreferences.getString("auth_token", "none");

            //Faz o request para modificar no servidor
            RequestEditarUsuario(this, auth_token, sNome, sRaio, sNomeLocal, Double.toString(latitude), Double.toString(longitude));
        }
    }

    public void RequestEditarUsuario(final Context context, final String auth_token, final String name, final String radius, final String city, final String latitude, final String longitude)
    {
        //Seta URL
        String requestURL = getString(R.string.server_url) + "api/users/profile";

        //Cria json para ser enviado no body
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("radius", radius);
            jsonObject.put("address", city);
            jsonObject.put("city", city);
            jsonObject.put("latitude", latitude);
            jsonObject.put("longitude", longitude);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Faz Request PUT
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, requestURL, jsonObject,

                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        SharedPreferences sharedPreferences = getSharedPreferences("ConfigFile", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("address", city);
                        editor.putString("city", city);
                        editor.putString("latitude", latitude);
                        editor.putString("longitude", longitude);
                        editor.putString("radius", radius);
                        editor.putString("name", name);
                        editor.commit();

                        Toast.makeText(context, "Perfil editado com sucesso!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(context, Vitrine.class);
                        startActivity(i);
                        finish();
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
                params.put("X-ArtSpot-Auth-Token", auth_token);
                return params;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }
}

