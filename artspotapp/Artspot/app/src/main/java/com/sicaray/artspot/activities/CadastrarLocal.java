package com.sicaray.artspot.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
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
import com.sicaray.artspot.data.Local;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CadastrarLocal extends AppCompatActivity
{
    RequestQueue requestQueue;
    boolean isEditingLocal;
    Local localEditado;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_local);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Novo Local");

        //Fila de requests do volley
        requestQueue = Volley.newRequestQueue(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        //Recebe o local da outra intent
        Intent i = getIntent();
        Local local = (Local)i.getSerializableExtra("Local");

        if(local != null)
        {
            isEditingLocal = true;
            EditText nome = (EditText)findViewById(R.id.nomeLocal);
            EditText descricao = (EditText)findViewById(R.id.descricaoLocal);
            EditText endereco = (EditText)findViewById(R.id.enderecoLocal);
            EditText horario = (EditText)findViewById(R.id.horarioLocal);
            EditText contato = (EditText)findViewById(R.id.contatoLocal);
            nome.setText(local.getNome());
            descricao.setText(local.getDescricao());
            endereco.setText(local.getEndereco());
            horario.setText(local.getHorario());
            contato.setText(local.getContato());
            localEditado = local;
        }

        else
            isEditingLocal = false;

    }

    public void OnClickButtonSalvar(View View)
    {
        Geocoder geocoder;
        geocoder = new Geocoder(this, Locale.getDefault());

        //Só deixa cadastrar se essa variável permanecer verdadeira
        boolean cadastroOK = true;

        //Recebe os dados digitados pelo usuário
        EditText nome = (EditText)findViewById(R.id.nomeLocal);
        EditText descricao = (EditText)findViewById(R.id.descricaoLocal);
        EditText endereco = (EditText)findViewById(R.id.enderecoLocal);
        EditText horario = (EditText)findViewById(R.id.horarioLocal);
        EditText contato = (EditText)findViewById(R.id.contatoLocal);

        //Checa se todos os campos foram preenchidos
        if(nome.getText().toString().isEmpty() || endereco.getText().toString().isEmpty() || contato.getText().toString().isEmpty()
                || descricao.getText().toString().isEmpty() || horario.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Pelo menos um dos campos está em branco!", Toast.LENGTH_SHORT).show();
            cadastroOK = false;
        }

        //Dado um endereço, pegar as coordenadas geográficas
        String latitude = null;
        String longitude = null;
        try
        {
            ArrayList<Address> adresses = (ArrayList<Address>) geocoder.getFromLocationName(endereco.getText().toString(),1);

            if(!adresses.isEmpty())
            {
                latitude = Double.toString(adresses.get(0).getLatitude());
                longitude = Double.toString(adresses.get(0).getLongitude());
            }
            else
            {
                cadastroOK = false;
                Toast.makeText(this, "Endereço não encontrado. Por favor insira o endereço novamente.", Toast.LENGTH_SHORT).show();
            }
        }
        catch (IOException e)
        {
            cadastroOK = false;
            Toast.makeText(this, "Um problema ocorreu. Verifique se seu aparelho esta conectado a internet.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        if(cadastroOK)
        {
            SharedPreferences sharedPreferences = getSharedPreferences("ConfigFile", Context.MODE_PRIVATE);
            String auth_token = sharedPreferences.getString("auth_token", "none");

            if(isEditingLocal)
                RequestEditarLocal(this, auth_token, localEditado.getId(), nome.getText().toString(), descricao.getText().toString(), endereco.getText().toString(), horario.getText().toString(), contato.getText().toString(), latitude, longitude);
            else
                RequestCriarLocal(this, auth_token, nome.getText().toString(), descricao.getText().toString(), endereco.getText().toString(), horario.getText().toString(), contato.getText().toString(), latitude, longitude);
        }
    }

    public void RequestEditarLocal(final Context context, final String auth_token, String id, String nome, String descricao, String endereco, String horario, String contato, String latitude, String longitude)
    {
        //Seta URL
        String requestURL = getString(R.string.server_url) + "api/places/" + id;

        //Cria json para ser enviado no body
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", nome);
            jsonObject.put("description", descricao);
            jsonObject.put("address", endereco);
            jsonObject.put("working_hours", horario);
            jsonObject.put("contact", contato);
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
                        Toast.makeText(context, "Local editado com sucesso!", Toast.LENGTH_SHORT).show();
                        finish(); //volta para meus locais
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

    public void RequestCriarLocal(final Context context, final String auth_token, String nome, String descricao, String endereco, String horario, String contato, String latitude, String longitude)
    {
        //Seta URL
        String requestURL = getString(R.string.server_url) + "api/places";

        //Cria json para ser enviado no body
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", nome);
            jsonObject.put("description", descricao);
            jsonObject.put("address", endereco);
            jsonObject.put("working_hours", horario);
            jsonObject.put("contact", contato);
            jsonObject.put("latitude", latitude);
            jsonObject.put("longitude", longitude);
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
                        Toast.makeText(context, "Novo local cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                        finish(); //volta para meus locais
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
