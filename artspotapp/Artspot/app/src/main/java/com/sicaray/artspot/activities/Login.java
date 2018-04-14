package com.sicaray.artspot.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sicaray.artspot.R;
import com.sicaray.artspot.data.Local;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity
{
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Entrar");

        //Fila de requests do volley
        requestQueue = Volley.newRequestQueue(this);
    }

    public void OnClickButtonEntrar(View View)
    {
        EditText editTextEmail = (EditText)findViewById(R.id.tela_login_campo_email);
        EditText editTextPassword = (EditText)findViewById(R.id.tela_login_campo_senha);

        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        RequestAutenticar(this, email, password);
    }

    public void RequestAutenticar(final Context context, String email, String password)
    {
        //Seta URL
        String requestURL = getString(R.string.server_url) + "api/users/sign_in";

        //Cria json para ser enviado no body
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("password", password);
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
                        //Recebe resposta json do servidor
                        String auth_token = null;
                        String status = null;
                        String reason = null;

                        try {
                            auth_token = response.getString("auth_token");
                            status = response.getString("status");
                            reason = response.getString("reason");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //Se autenticou corretamente
                        if(status.equals("success"))
                        {
                            //Armazena informações do novo usuário logado
                            SharedPreferences sharedPreferences = getSharedPreferences("ConfigFile", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("auth_token", auth_token);
                            editor.commit();

                            //Pede as outras informações do usuário
                            RequestUserInfo(context, auth_token);
                        }

                        //Falha de autenticação
                        else
                            Toast.makeText(context, "Falha de autenticação. " + reason, Toast.LENGTH_SHORT).show();
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(context, "Falha de comunicação com o servidor.", Toast.LENGTH_LONG).show();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    public void RequestUserInfo(final Context context, String auth_token)
    {
        //Seta URL
        String requestURL = getString(R.string.server_url) + "api/users/profile";

        //Request GET
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(requestURL, null,

                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        //Armazena informações baixadas
                        SharedPreferences sharedPreferences = getSharedPreferences("ConfigFile", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        try {
                            editor.putString("id", response.getString("id"));
                            editor.putString("name", response.getString("name"));
                            editor.putString("email", response.getString("email"));
                            editor.putString("city", response.getString("city"));
                            editor.putString("latitude", response.getString("latitude"));
                            editor.putString("longitude", response.getString("longitude"));
                            editor.putString("radius", response.getString("radius"));
                            editor.commit();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(context, "Você entrou com sucesso!", Toast.LENGTH_SHORT).show();

                        //Volta para a vitrine
                        finish();
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(context, "Falha de comunicação com o servidor.", Toast.LENGTH_LONG).show();
                    }
                }
        )//Seta Header
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                SharedPreferences sharedPreferences = getSharedPreferences("ConfigFile", Context.MODE_PRIVATE);
                Map<String, String>  params = new HashMap<String, String>();
                params.put("X-ArtSpot-Auth-Token", sharedPreferences.getString("auth_token", "none"));
                return params;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }
}
