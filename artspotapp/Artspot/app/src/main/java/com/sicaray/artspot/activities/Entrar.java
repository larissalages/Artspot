package com.sicaray.artspot.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.sicaray.artspot.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Entrar extends AppCompatActivity
{
    CallbackManager callbackManager;
    RequestQueue requestQueue;

    //Dados do usuário
    String id;
    String name;
    String email;
    String password;
    String city;
    String radius;
    String latitude;
    String longitude;
    String auth_token;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Para logar no Facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_entrar);

        //Pra aparecer o toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Entrar");

        //Fila de requests do volley
        requestQueue = Volley.newRequestQueue(this);

        //Context
        final Context context = this;

        LoginButton loginButton = (LoginButton) findViewById(R.id.tela_entrar_button_facebook);

        //Seta permissoes requeridas no facebook
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile", "user_location"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>()
        {
            @Override
            public void onSuccess(LoginResult loginResult)
            {
                //Pede informações do usuário no facebook
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback()
                        {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response)
                            {
                                try {
                                    //Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();

                                    //TODO: fix facebook location issues
                                    name = object.getString("first_name");
                                    if (object.has("last_name"))
                                        name = name + " " + object.getString("last_name");
                                    email = object.getString("email");
                                    password = object.getString("id");
                                    city = "Recife, PE, Brazil";
                                    radius = "30";
                                    latitude = "-8.058431";
                                    longitude = "-34.884171";

                                    RequestCadastrar(context);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                );

                Bundle parameters = new Bundle();

                parameters.putString("fields", "id, first_name, last_name, email, gender, birthday, location"); //picture.type(large)
                request.setParameters(parameters);
                request.executeAsync();

                OnClickButtonFacebook();
            }

            @Override
            public void onCancel()
            {
                Log.d("ENTRAR", "Login com facebook cancelado.");
            }

            @Override
            public void onError(FacebookException e)
            {
                Log.d("ENTRAR", "Login com facebook falhou. " + e.getCause().toString());
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        //Se já logou encerra a activity
        SharedPreferences sharedPreferences = getSharedPreferences("ConfigFile", Context.MODE_PRIVATE);
        if(!sharedPreferences.getString("auth_token", "none").equals("none"))
            finish();
    }

    public void OnClickButtonEntrar(View view)
    {
        Intent i = new Intent(this, Login.class);
        startActivity(i);
    }

    public void OnClickButtonCadastrar(View view)
    {
        Intent i = new Intent(this, CadastrarUsuario.class);
        startActivity(i);
    }

    public void OnClickButtonFacebook()
    {
        finish();
    }

    public void RequestCadastrar(final Context context)
    {
        //Seta URL
        String requestURL = getString(R.string.server_url) + "api/users/sign_up";

        //Cria json para ser enviado no body
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("email", email);
            jsonObject.put("password", password);
            jsonObject.put("password_confirmation", password);
            jsonObject.put("radius", radius);
            jsonObject.put("latitude", latitude);
            jsonObject.put("longitude", longitude);
            jsonObject.put("city", city);
            jsonObject.put("address", city);
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
                        //Manda logar o user
                        RequestAutenticar(context);
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();

                        //Se deu erro talvez o usuário já esteja cadastrado. tenta autenticar
                        RequestAutenticar(context);
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    public void RequestAutenticar(final Context context)
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
                        //Armazena token de autenticação
                        try {
                            auth_token = response.getString("auth_token");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        RequestUserInfo(context);
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    public void RequestUserInfo(final Context context)
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
                        try {
                        String id = response.getString("id");
                        String name = response.getString("name");
                        String email = response.getString("email");
                        String city = response.getString("city");
                        String radius = response.getString("radius");
                        String latitude = response.getString("latitude");
                        String longitude = response.getString("longitude");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //Armazena informações baixadas
                        SharedPreferences sharedPreferences = getSharedPreferences("ConfigFile", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("id", id);
                        editor.putString("name", name);
                        editor.putString("email", email);
                        editor.putString("password", password);
                        editor.putString("city", city);
                        editor.putString("latitude", latitude);
                        editor.putString("longitude", longitude);
                        editor.putString("radius", radius);
                        editor.putString("auth_token", auth_token);
                        editor.commit();

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
                Map<String, String>  params = new HashMap<String, String>();
                params.put("X-ArtSpot-Auth-Token", auth_token);
                return params;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }
}

