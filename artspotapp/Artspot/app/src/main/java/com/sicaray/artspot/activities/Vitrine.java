package com.sicaray.artspot.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.sicaray.artspot.R;
import com.sicaray.artspot.data.Peca;
import com.sicaray.artspot.util.DownloadImageTask;
import com.sicaray.artspot.util.OnSwipeTouchListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Vitrine extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{
    TextView drawerUsername, drawerEmail;
    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    RequestQueue requestQueue;
    Peca peca = null;

    //Pro GPS
    Location userLocation;
    public GoogleApiClient mGoogleApiClient;
    public static final String TAG = CadastrarUsuario.class.getSimpleName();
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    Geocoder geocoder;
    Location location;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_vitrine);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //create an instance of GoogleApiClient
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        //Coloquei so no onCreate pra ficar menos chato pro usuario
        mGoogleApiClient.connect();

        //Fila de requests do volley
        requestQueue = Volley.newRequestQueue(this);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset)
            {
                super.onDrawerSlide(drawerView, slideOffset);

                //setContentView(R.layout.activity_main);
                drawerUsername = (TextView) findViewById(R.id.drawer_header_username);
                drawerEmail = (TextView) findViewById(R.id.drawer_header_email);

                //Seta o nome de usuário e email do usuário logado pra aparecer no menu lateral
                SharedPreferences sharedPreferences = getSharedPreferences("ConfigFile", Context.MODE_PRIVATE);
                String name = sharedPreferences.getString("name", "none");
                String email = sharedPreferences.getString("email", "none");
                String auth_token = sharedPreferences.getString("auth_token", "none");

                //Se não estiver logado faz não aparecer
                if(name.equals("none"))
                {
                    drawerUsername.setVisibility(View.INVISIBLE);
                    drawerEmail.setVisibility(View.INVISIBLE);
                    navigationView.getMenu().findItem(R.id.drawer_item_meus_locais).setVisible(false);
                    navigationView.getMenu().findItem(R.id.drawer_item_minhas_pecas).setVisible(false);
                    navigationView.getMenu().findItem(R.id.drawer_item_entrar).setVisible(true);
                    navigationView.getMenu().findItem(R.id.drawer_item_sair).setVisible(false);
                    navigationView.getMenu().findItem(R.id.drawer_item_configuracoes).setVisible(false);
                    navigationView.getMenu().findItem(R.id.drawer_item_debug).setVisible(true);
                }

                //Se estiver logado coloca o nome no header
                else
                {
                    drawerUsername.setVisibility(View.VISIBLE);
                    drawerEmail.setVisibility(View.VISIBLE);
                    drawerUsername.setText(name);
                    drawerEmail.setText(email);
                    navigationView.getMenu().findItem(R.id.drawer_item_meus_locais).setVisible(true);
                    navigationView.getMenu().findItem(R.id.drawer_item_minhas_pecas).setVisible(true);
                    navigationView.getMenu().findItem(R.id.drawer_item_entrar).setVisible(false);
                    navigationView.getMenu().findItem(R.id.drawer_item_sair).setVisible(true);
                    navigationView.getMenu().findItem(R.id.drawer_item_configuracoes).setVisible(true);
                    navigationView.getMenu().findItem(R.id.drawer_item_debug).setVisible(true);
                }
            }
        };

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Pega as informações do usuário
        SharedPreferences sp = getSharedPreferences("ConfigFile", Context.MODE_PRIVATE);
        String name = sp.getString("name", "none");
        String email = sp.getString("email", "none");
        String password = sp.getString("password", "none");
        String radius = sp.getString("radius", "none");
        String latitude = sp.getString("latitude", "none");
        String longitude = sp.getString("longitude", "none");

        //Esqueçe o token de autenticação
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("email", "none");

        //Se tem informações do usuário loga
        if(!email.equals("none") && !password.equals("none"))
            RequestAutenticar(this, email, password);

        //Se está deslogado seta raio padrão e coordenadas fixas para caso o GPS esteja desligado
        if(radius.equals("none"))
        {
            radius = "30";
            latitude = "-8.058431";
            longitude = "-34.884171";
        }

        //Se o GPS está ligado pega coordenadas do GPS
        if(userLocation != null)
        {
            latitude = Double.toString(userLocation.getLatitude());
            longitude = Double.toString(userLocation.getLongitude());
        }

        //Se não tem peça carregada na vitrine faz o request de uma
        if(peca == null)
            RequestPeca(this, radius, latitude, longitude);

        //Seta movimentacao de swipe
        final ImageView imageView = (ImageView) findViewById(R.id.image_peca);
        imageView.setOnTouchListener(new OnSwipeTouchListener(this)
        {
            public void onSwipeRight() {
                OnClickButtonDislike(imageView);
            }
            public void onSwipeLeft() {
                OnClickButtonLike(imageView);
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        /*SharedPreferences sharedPreferences = getSharedPreferences("ConfigFile", Context.MODE_PRIVATE);
        String auth_token = sharedPreferences.getString("auth_token", "none");

        //Se possui um auth_token manda deslogar sem apagar os dados
        if(!auth_token.equals("none"))
            RequestSair(this, auth_token, false);*/
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        SharedPreferences sharedPreferences = getSharedPreferences("ConfigFile", Context.MODE_PRIVATE);
        String auth_token = sharedPreferences.getString("auth_token", "none");

        //Se possui um auth_token manda deslogar sem apagar os dados
        if(!auth_token.equals("none"))
            RequestSair(this, auth_token, false);
    }

    @Override
    public void onBackPressed()
    {
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.options_menu_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.about_button)
        {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            String about_message = this.getResources().getString(R.string.about_message);
            builder1.setMessage(about_message);
            builder1.setCancelable(true);
            builder1.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.drawer_item_minhas_pecas) {
            Intent intentPecas = new Intent(this, MinhasPecas.class);
            startActivity(intentPecas);
        } else if (id == R.id.drawer_item_meus_locais) {
            Intent intentLocal = new Intent(this, MeusLocais.class);
            startActivity(intentLocal);
        } else if (id == R.id.drawer_item_configuracoes) {
            Intent intent = new Intent(this, Configuracoes.class);
            startActivity(intent);
        } else if (id == R.id.drawer_item_entrar) {
            Intent intent = new Intent(this, Entrar.class);
            startActivity(intent);

        } else if (id == R.id.drawer_item_sair) {
            SharedPreferences sharedPreferences = getSharedPreferences("ConfigFile", Context.MODE_PRIVATE);
            String auth_token = sharedPreferences.getString("auth_token", "none");

            //Se possui um auth_token manda deslogar
            if(!auth_token.equals("none"))
                RequestSair(this, auth_token, true);

        } else if (id == R.id.drawer_item_debug) {
            Intent intent = new Intent(this, TesteDebug.class);
            startActivity(intent);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onConnected(Bundle bundle)
    {
        //Pega a localização do usuario pelo GPS
        location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        //Se o GPS não estiver ligado, sugere que o usuário ligue
        if (location == null)
        {
            new AlertDialog.Builder(this)
                    .setTitle("Busca Geolocalizada")
                    .setMessage("Para uma melhor experiencia no aplicativo considere ativar o GPS.")
                    .setPositiveButton("Ativar", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton("Agora não", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.cancel();
                        }
                    })
                    .setIcon(R.drawable.ic_alerta)
                    .show();
        }

        //Guarda a localização do usuário
        else
        {
            Log.d("GEOLOCALIZACAO", "Nova localização adquirida");
            userLocation = location;
        }
    }

    protected void onPause()
    {
        super.onPause();
        if (mGoogleApiClient.isConnected())
        {
            mGoogleApiClient.disconnect();
        }
    }

    public void onConnectionSuspended(int i)
    {
        Log.i(TAG, "Location services suspended. Please reconnect.");
    }

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
        }

        else
        {
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    public void OnClickButtonLike(View view)
    {
        if(peca != null)
            RequestLike(this);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.pull_in_right);
        View v = findViewById(R.id.image_peca);
        v.startAnimation(animation);
    }

    public void OnClickButtonDislike(View view)
    {
        if(peca != null)
            RequestDislike(this);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.pull_in_left);
        View v = findViewById(R.id.image_peca);
        v.startAnimation(animation);
    }

    public void OnClickButtonSugestao(View view)
    {
        Intent intent = new Intent(this, SugestoesLocais.class);
        startActivity(intent);
    }

    public void OnClickButtonInfo(View view)
    {
        Intent intent = new Intent(this, ExibicaoPeca.class);
        intent.putExtra("Peca", peca);
        startActivity(intent);
    }

    public void RequestPeca(final Context context, String radius, String latitude, String longitude)
    {
        //Seta URL
        String requestURL = getString(R.string.server_url) + "api/showcase?radius=" + radius + "&latitude=" + latitude + "&longitude=" + longitude;

        //Request GET
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(requestURL, null,

                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Peca nova_peca = null;
                        try {
                            nova_peca = new Peca(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        peca = nova_peca;

                        //Mostrar a imagem da peca
                        ImageView imageView = (ImageView) findViewById(R.id.image_peca);
                        DownloadImageTask downloadImageTask = new DownloadImageTask(getString(R.string.server_url_no_slash) + peca.getImageUrl(), imageView);
                        downloadImageTask.execute();
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(context, "Falha de comunicação com o servidor ao baixar peça. " + error.getMessage(), Toast.LENGTH_LONG).show();

                        new AlertDialog.Builder(context)
                                .setCancelable(false)
                                .setIcon(R.drawable.ic_alerta)
                                .setTitle("Problemas de Conexão")
                                .setMessage("Estamos com problemas para encontrar o servidor. Verifique sua conexão com a internet.")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(context, Vitrine.class));
                                        finish();
                                    }
                                })
                                .show();
                    }
                }
        )//Seta Header
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String>  params = new HashMap<String, String>();

                //Coloca o auth_token se tiver
                SharedPreferences sharedPreferences = getSharedPreferences("ConfigFile", Context.MODE_PRIVATE);
                String auth_token = sharedPreferences.getString("auth_token", "none");
                if(!auth_token.equals("none"))
                    params.put("X-ArtSpot-Auth-Token", auth_token);

                return params;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }

    public void RequestLike(final Context context)
    {
        //Seta URL
        String requestURL = getString(R.string.server_url) + "api/showcase/like/" + peca.getId();

        //Request GET
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(requestURL, null,

                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Peca nova_peca = null;
                        try {
                            nova_peca = new Peca(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        peca = nova_peca;

                        //Mostrar a imagem da peca
                        /*((ImageView)findViewById(R.id.image_peca)).setBackgroundResource(R.drawable.blank_bg);
                        Animation animation = AnimationUtils.loadAnimation(context, R.anim.pull_in_right);
                        View v = findViewById(R.id.image_peca);
                        v.startAnimation(animation);*/
                        ImageView imageView = (ImageView) findViewById(R.id.image_peca);
                        DownloadImageTask downloadImageTask = new DownloadImageTask(getString(R.string.server_url_no_slash) + peca.getImageUrl(), imageView);
                        downloadImageTask.execute();
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(context, "Falha de comunicação com o servidor ao baixar peça. " + error.getMessage(), Toast.LENGTH_LONG).show();

                        new AlertDialog.Builder(context)
                                .setCancelable(false)
                                .setIcon(R.drawable.ic_alerta)
                                .setTitle("Problemas de Conexão")
                                .setMessage("Estamos com problemas para encontrar o servidor. Verifique sua conexão com a internet.")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(context, Vitrine.class));
                                        finish();
                                    }
                                })
                                .show();
                    }
                }
        )//Seta Header
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String>  params = new HashMap<String, String>();

                //Coloca o auth_token se tiver
                SharedPreferences sharedPreferences = getSharedPreferences("ConfigFile", Context.MODE_PRIVATE);
                String auth_token = sharedPreferences.getString("auth_token", "none");
                if(!auth_token.equals("none"))
                    params.put("X-ArtSpot-Auth-Token", auth_token);

                return params;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }

    public void RequestDislike(final Context context)
    {
        //Seta URL
        String requestURL = getString(R.string.server_url) + "api/showcase/hate/" + peca.getId();

        //Request GET
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(requestURL, null,

                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Peca nova_peca = null;
                        try {
                            nova_peca = new Peca(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        peca = nova_peca;

                        //Mostrar a imagem da peca
                        ImageView imageView = (ImageView) findViewById(R.id.image_peca);
                        DownloadImageTask downloadImageTask = new DownloadImageTask(getString(R.string.server_url_no_slash) + peca.getImageUrl(), imageView);
                        downloadImageTask.execute();
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(context, "Falha de comunicação com o servidor ao baixar peça. " + error.getMessage(), Toast.LENGTH_LONG).show();

                        new AlertDialog.Builder(context)
                                .setCancelable(false)
                                .setIcon(R.drawable.ic_alerta)
                                .setTitle("Problemas de Conexão")
                                .setMessage("Estamos com problemas para encontrar o servidor. Verifique sua conexão com a internet.")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(context, Vitrine.class));
                                        finish();
                                    }
                                })
                                .show();
                    }
                }
        )//Seta Header
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String>  params = new HashMap<String, String>();

                //Coloca o auth_token se tiver
                SharedPreferences sharedPreferences = getSharedPreferences("ConfigFile", Context.MODE_PRIVATE);
                String auth_token = sharedPreferences.getString("auth_token", "none");
                if(!auth_token.equals("none"))
                    params.put("X-ArtSpot-Auth-Token", auth_token);

                return params;
            }
        };

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

                            Toast.makeText(context, "Usuário logado", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(context, "Falha de comunicação com o servidor ao logar. " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    public void RequestSair(final Context context, final String auth_token, final boolean eraseInfo)
    {
        //Seta URL
        String requestURL = getString(R.string.server_url) + "api/users/sign_out";

        //Faz Request POST
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, requestURL, null,

                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(context, "Falha de comunicação com o servidor ao deslogar. " + error.getMessage(), Toast.LENGTH_LONG).show();
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

        //Apaga dados do usuário deslogado
        SharedPreferences sharedPreferences = getSharedPreferences("ConfigFile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(eraseInfo)
        {
            editor.putString("id", "none");
            editor.putString("name", "none");
            editor.putString("email", "none");
            editor.putString("password", "none");
            editor.putString("city", "none");
            editor.putString("latitude", "none");
            editor.putString("longitude", "none");
            editor.putString("radius", "none");
            editor.putString("auth_token", "none");
            editor.commit();
        }

        requestQueue.add(jsonObjectRequest);
    }
}