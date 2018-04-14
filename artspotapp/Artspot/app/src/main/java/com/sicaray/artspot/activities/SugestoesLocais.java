package com.sicaray.artspot.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.sicaray.artspot.R;
import com.sicaray.artspot.data.Local;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SugestoesLocais extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{
    public GoogleApiClient mGoogleApiClient;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    public static final String TAG = SugestoesLocais.class.getSimpleName();
    RequestQueue requestQueue;
    ListView listView;
    SugestoesLocaisAdapter adapter;
    ArrayList<Local> listaLocais;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugestoes_locais);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Locais Sugeridos");

        //create an instance of GoogleApiClient
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        //Fila de requests do volley
        requestQueue = Volley.newRequestQueue(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        if(!mGoogleApiClient.isConnected())
            mGoogleApiClient.connect();

        //Coloca os locais na listView
        listaLocais = new ArrayList<Local>();
        adapter = new SugestoesLocaisAdapter(this, listaLocais);
        listView = (ListView) findViewById(R.id.sugestoes_locais_list);
        listView.setAdapter(adapter);

        //Seta acao pra quando clica o item da lista
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Local listItem = (Local) listView.getItemAtPosition(position);
                Intent i = new Intent(listView.getContext(), ExibicaoLocal.class);
                i.putExtra("Local", listItem);
                startActivity(i);
            }
        });

        //Puxa sugestões de locais do servidor e coloca na lista
        SharedPreferences sp = getSharedPreferences("ConfigFile", Context.MODE_PRIVATE);
        String radius = sp.getString("radius", "none");
        String latitude = sp.getString("latitude", "none");
        String longitude = sp.getString("longitude", "none");

        //TODO: pegar posição gps do usuário
        if(latitude.equals("none"))
        {
            radius = "30";
            latitude = "-8.058431";
            longitude = "-34.884171";
        }

        RequestLocais(this, radius, latitude, longitude);
    }

    public void RequestLocais(final Context context, final String radius, final String latitude, final String longitude)
    {
        //Seta URL
        String requestURL = getString(R.string.server_url) + "api/suggestion?radius=" + radius + "&latitude=" + latitude + "&longitude=" + longitude;

        //Request GET
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(requestURL,

                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        //Extrai todos os locais e adiciona na lista para exibição
                        for(int i = 0; i < response.length(); i++)
                        {
                            JSONObject jsonObjectPlace = null;
                            try {
                                jsonObjectPlace = response.getJSONObject(i);
                                Local local = new Local(jsonObjectPlace);
                                listaLocais.add(local);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        adapter.notifyDataSetChanged();
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

                //Coloca o auth_token se tiver
                SharedPreferences sharedPreferences = getSharedPreferences("ConfigFile", Context.MODE_PRIVATE);
                String auth_token = sharedPreferences.getString("auth_token", "none");
                if(!auth_token.equals("none"))
                    params.put("X-ArtSpot-Auth-Token", auth_token);

                return params;
            }
        };

        requestQueue.add(jsonArrayRequest);
    }

    protected void onPause()
    {
        super.onPause();
        if (mGoogleApiClient.isConnected())
        {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        /*if (location == null)
        {
            builder.setMessage("Infelizmente não conseguimos acessar sua localização. Verifique se seu GPS está ligado e tente novamente.");
            AlertDialog alerta = builder.create();
            alerta.show();
        }

        else
        {
            System.out.println("latitude:");
            System.out.println(String.valueOf(location.getLatitude()));
            System.out.println("longitude:");
            System.out.println(String.valueOf(location.getLongitude()));
        };*/
    }

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

    public class SugestoesLocaisAdapter extends ArrayAdapter<Local>
    {
        private final Context context;
        private final ArrayList<Local> itemsArrayList;

        public SugestoesLocaisAdapter(Context context, ArrayList<Local> itemsArrayList)
        {
            super(context, R.layout.item_sugestoes_locais, itemsArrayList);

            this.context = context;
            this.itemsArrayList = itemsArrayList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            // 1. Create inflater
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 2. Get rowView from inflater
            View rowView = inflater.inflate(R.layout.item_sugestoes_locais, parent, false);

            // 3. Get the two text view from the rowView
            TextView nomeView = (TextView) rowView.findViewById(R.id.item_sugestoes_locais_nome);
            TextView enderecoView = (TextView) rowView.findViewById(R.id.item_sugestoes_locais_endereco);

            // 4. Set the text for textView
            nomeView.setText(itemsArrayList.get(position).getNome());
            enderecoView.setText(itemsArrayList.get(position).getEndereco());

            // 5. return rowView
            return rowView;
        }
    }
}
