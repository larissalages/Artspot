package com.sicaray.artspot.activities;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.sicaray.artspot.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class CadastrarUsuario extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{
    RequestQueue requestQueue;
    public GoogleApiClient mGoogleApiClient;
    public static final String TAG = CadastrarUsuario.class.getSimpleName();
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Cadastrar");

        requestQueue = Volley.newRequestQueue(this);

        //create an instance of GoogleApiClient
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        //setUpMapIfNeeded();
        mGoogleApiClient.connect();
    }

    protected void onPause()
    {
        super.onPause();
        if (mGoogleApiClient.isConnected())
        {
            mGoogleApiClient.disconnect();
        }
    }

    public void onConnected(Bundle bundle)
    {
        //Pega a cidade que o usuario esta do GPS
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location != null)
        {
            geocoder = new Geocoder(this, Locale.getDefault());
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            try {
                String cityName;
                ArrayList<Address> adresses = (ArrayList<Address>) geocoder.getFromLocation(latitude,longitude,1);
                if(!adresses.isEmpty())
                {

                    cityName = adresses.get(0).getLocality();
                    //TODO: Mandar latitude, longitude , endereço e cidade pro back
                    //adress.get.getAddressLine(0) -> Pega cidade
                    //adress.get.getAddressLine(1) -> Pega estado
                    //adress.get.getAddressLine(2) -> Pega País
                }
                else
                {
                 cityName = "";

                }
                EditText city = (EditText)findViewById(R.id.tela_cadastrar_campo_cidade);
                city.setText(cityName); //coloca o nome a cidade que o usuário ta nesse campo


            } catch (IOException e) {
                e.printStackTrace();
            }


        }//TODO: else o GPS não ta ligando, manda nada pro back
    }


    //Quando clicar em Cadastrar
    public void OnClickButtonCadastrar(View view)
    {
        boolean cadastroOK = true;

        geocoder = new Geocoder(this, Locale.getDefault());

        EditText nomeCadastro = (EditText)findViewById(R.id.tela_cadastrar_campo_nome);
        EditText emailCadastro = (EditText)findViewById(R.id.tela_cadastrar_campo_email);
        EditText senhaCadastro = (EditText)findViewById(R.id.tela_cadastrar_campo_senha);
        EditText confirmarSenhaCadastro = (EditText)findViewById(R.id.tela_cadastrar_campo_confirmar_senha);
        EditText cidadeCadastro = (EditText)findViewById(R.id.tela_cadastrar_campo_cidade);
        EditText raioCadastro = (EditText)findViewById(R.id.tela_cadastrar_campo_raio);

        String strName = nomeCadastro.getText().toString();
        String strEmail = emailCadastro.getText().toString();
        String strPassword = senhaCadastro.getText().toString();
        String strConfirmPassword = confirmarSenhaCadastro.getText().toString();
        String strCidade = cidadeCadastro.getText().toString();
        String strRaio = raioCadastro.getText().toString();

        //Verifica se preencheu todos os campos
        if(strName.isEmpty() || strEmail.isEmpty() || strPassword.isEmpty() || strConfirmPassword.isEmpty() || strCidade.isEmpty() || strRaio.isEmpty())
        {
            Toast.makeText(this, "Pelo menos um dos campos está em branco.", Toast.LENGTH_SHORT).show();
            cadastroOK = false;
        }

        //Verifica se a senha e a confirmação estão iguais
        if(!strPassword.equals(strConfirmPassword))
        {
            Toast.makeText(this, "O campo confirmação de senha está diferente da senha.", Toast.LENGTH_SHORT).show();
            cadastroOK = false;
        }

        //Checa se o endereço é valido

        double latitude = 0.0;
        double longitude = 0.0;
        try
        {
            ArrayList<Address> adresses = (ArrayList<Address>) geocoder.getFromLocationName(strCidade, 1);

            if(!adresses.isEmpty())
            {
                latitude = adresses.get(0).getLatitude();
                longitude = adresses.get(0).getLongitude();
                //adress.get.getAddressLine(0) -> Pega cidade
                //adress.get.getAddressLine(1) -> Pega estado
                //adress.get.getAddressLine(2) -> Pega País
            }
            else
            {
                cadastroOK = false;
                Toast.makeText(this, "Cidade não encontrada. Por favor insira a cidade novamente.", Toast.LENGTH_SHORT).show();
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
            //Cadastra dados no backend
            RequestCadastrar(this, strName, strEmail, strPassword, strCidade, strRaio, Double.toString(latitude), Double.toString(longitude));
        }
    }

    public void RequestCadastrar(final Context context, String name, final String email, final String password, final String cidade, final String raio, final String latitude, final String longitude)
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
            jsonObject.put("radius", raio);
            jsonObject.put("latitude", latitude);
            jsonObject.put("longitude", longitude);
            jsonObject.put("city", cidade);
            jsonObject.put("address", cidade);
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
                        //Armazena informações do novo usuário cadastrado
                        SharedPreferences sharedPreferences = getSharedPreferences("ConfigFile", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        try {
                            editor.putString("id", response.getString("id"));
                            editor.putString("name", response.getString("name"));
                            editor.putString("email", response.getString("email"));
                            editor.putString("password", password);
                            editor.putString("city", response.getString("city"));
                            editor.putString("latitude", response.getString("latitude"));
                            editor.putString("longitude", response.getString("longitude"));
                            editor.putString("radius", response.getString("radius"));
                            editor.commit();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //Cadastra dados no backend
                        RequestAutenticar(context, email, password);
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
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
                        //Armazena informações do novo usuário logado
                        SharedPreferences sharedPreferences = getSharedPreferences("ConfigFile", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        try {
                            editor.putString("auth_token", response.getString("auth_token"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        editor.commit();

                        //Exibe uma mensagem de sucesso
                        Toast.makeText(context, "Informações enviadas com sucesso.", Toast.LENGTH_SHORT).show();

                        //Volta para a vitrine
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
                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    //Coisas do GPS

    @Override
    public void onConnectionSuspended(int i)
    {
        Log.i(TAG, "Location services suspended. Please reconnect.");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult)
    {
        if (connectionResult.hasResolution())
        {
            try
            {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e)
            {
                e.printStackTrace();
            }
        } else
        {
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }

    }
}

