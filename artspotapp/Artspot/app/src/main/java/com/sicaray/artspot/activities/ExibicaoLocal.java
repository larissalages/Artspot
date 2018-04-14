package com.sicaray.artspot.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sicaray.artspot.R;
import com.sicaray.artspot.data.Local;
import com.sicaray.artspot.data.Peca;
import com.sicaray.artspot.util.DownloadImageTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ExibicaoLocal extends FragmentActivity implements OnMapReadyCallback
{

    private GoogleMap mMap;
    RequestQueue requestQueue;
    Local local;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibicao_local);

        //Fila de requests do volley
        requestQueue = Volley.newRequestQueue(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        //Recebe o local da outra intent
        Intent i = getIntent();
        local = (Local)i.getSerializableExtra("Local");

        //Seta os textViews com as informações do local
        TextView viewNome = (TextView) findViewById(R.id.exibicao_local_nome);
        viewNome.setText(local.getNome());
        TextView viewDescricao = (TextView) findViewById(R.id.exibicao_local_descricao);
        viewDescricao.setText(local.getDescricao());
        TextView viewEndereco = (TextView) findViewById(R.id.exibicao_local_endereco);
        viewEndereco.setText(local.getEndereco());
        TextView viewHorario = (TextView) findViewById(R.id.exibicao_local_horario);
        viewHorario.setText(local.getHorario());
        TextView viewContato = (TextView) findViewById(R.id.exibicao_local_contato);
        viewContato.setText(local.getContato());

        ImageView imageView1 = (ImageView) findViewById(R.id.item_minhas_pecas_foto_peca);
        ImageView imageView2 = (ImageView) findViewById(R.id.item_minhas_pecas_foto_peca2);
        ImageView imageView3 = (ImageView) findViewById(R.id.item_minhas_pecas_foto_peca3);

        //Mostrar a imagem da peca
        ArrayList<String> id_pecas = local.getListaPecasIds();
        if(id_pecas.size() >= 1)
            RequestImagePeca(this, id_pecas.get(0), imageView1);
        if(id_pecas.size() >= 2)
            RequestImagePeca(this, id_pecas.get(1), imageView2);
        if(id_pecas.size() >= 3)
            RequestImagePeca(this, id_pecas.get(2), imageView3);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.exibicao_local_mapa);
        mapFragment.getMapAsync(this);
    }

    public void RequestImagePeca(final Context context, final String id_peca, final ImageView imageView)
    {
        //Seta URL
        String requestURL = getString(R.string.server_url) + "api/images/" + id_peca;

        //Request GET
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(requestURL, null,

                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try {
                            Peca peca = new Peca(response);
                            DownloadImageTask downloadImageTask = new DownloadImageTask(getString(R.string.server_url_no_slash) + peca.getImageUrl(), imageView);
                            downloadImageTask.execute();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        //Recebe da variavel local as informacoes do local
        String nomeLocal = local.getNome();
        double latitude = Double.parseDouble(local.getLatitude());
        double longitude = Double.parseDouble(local.getLongitude());

        //---------------------------------------------------------------------------------------------------------
        //Pegar Lat e Long e dizer qual é esse endereco
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

            //Ajeitar a posicao e o zoom da camera:
            CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude));
            CameraUpdate zoom= CameraUpdateFactory.zoomTo(16);

            mMap.moveCamera(center);
            mMap.animateCamera(zoom);

            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latitude, longitude))
                    .title(nomeLocal));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
