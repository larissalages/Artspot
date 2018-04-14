package com.sicaray.artspot.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sicaray.artspot.R;
import com.sicaray.artspot.data.Peca;
import com.sicaray.artspot.util.DownloadImageTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ExibicaoPeca extends AppCompatActivity
{
    RequestQueue requestQueue;
    ArrayList<String> listaTags;
    ArrayList<String> listaLocais;
    MinhasTagsAdapter adapterTags;
    MeusLocaisAdapter adapterLocais;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibicao_peca);

        //Fila de requests do volley
        requestQueue = Volley.newRequestQueue(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        //Recebe a peça da outra intent
        Intent i = getIntent();
        Peca peca = (Peca)i.getSerializableExtra("Peca");

        //Pega o nome do usuario que cadastrou a peca e seta no textView
        RequestUserDono(this, peca.getIdDono());

        //Baixa e exibe a imagem da peça
        ImageView imageView = (ImageView) findViewById(R.id.exibicao_peca_image_url);
        DownloadImageTask downloadImageTask = new DownloadImageTask(getString(R.string.server_url_no_slash) + peca.getImageUrl(), imageView);
        downloadImageTask.execute();

        //Exibe a lista de Tags da peça
        listaTags = peca.getListaTags();
        adapterTags = new MinhasTagsAdapter(this, listaTags);
        ListView listaTagsView = (ListView) findViewById(R.id.exibicao_peca_listaTags);
        listaTagsView.setAdapter(adapterTags);
        setListViewHeightBasedOnChildren(listaTagsView);

        //Exibe a lista de locais da peça
        listaLocais = new ArrayList<String>();
        adapterLocais = new MeusLocaisAdapter(this, listaLocais);
        ListView listaLocaisView = (ListView) findViewById(R.id.exibicao_peca_lista_locais);
        listaLocaisView.setAdapter(adapterLocais);

        //Faz request do nome de cada local e adiciona em listaLocais
        ArrayList<String> places_ids =  peca.getListaPlacesIds();
        for(int cont = 0; cont < places_ids.size(); cont++)
            RequestNomeLocal(this, places_ids.get(cont));
    }

    public void RequestNomeLocal(final Context context, final String id_local)
    {
        //Seta URL
        String requestURL = getString(R.string.server_url) + "api/places/" + id_local;

        //Request GET
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(requestURL, null,

                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try {
                            listaLocais.add(response.getString("name"));
                            adapterLocais.notifyDataSetChanged();
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
                        Toast.makeText(context, "Falha de comunicação com o servidor ao verificar nome do local. " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    public void RequestUserDono(final Context context, final String id_dono)
    {
        //Seta URL
        String requestURL = getString(R.string.server_url) + "api/users/" + id_dono;

        //Request GET
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(requestURL, null,

                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        TextView textViewNome = (TextView) findViewById(R.id.exibicao_peca_nome_user);

                        try {
                            textViewNome.setText(response.getString("name"));
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
                        Toast.makeText(context, "Falha de comunicação com o servidor ao verificar dono. " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    //Resolve o problema de uma listView dentro de um scrollView
    public static void setListViewHeightBasedOnChildren(ListView listView)
    {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public class MinhasTagsAdapter extends ArrayAdapter<String>
    {
        private final Context context;
        private final ArrayList<String> tagsArrayList;

        public MinhasTagsAdapter(Context context, ArrayList<String> tagsArrayList)
        {
            super(context, R.layout.item_pecas_tags, tagsArrayList);

            this.context = context;
            this.tagsArrayList = tagsArrayList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            // 1. Create inflater
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 2. Get rowView from inflater
            View rowView = inflater.inflate(R.layout.item_pecas_tags, parent, false);

            // 3. Get the two text view from the rowView
            TextView nomeTagView = (TextView) rowView.findViewById(R.id.item_tag);

            // 4. Set the text for textView
            nomeTagView.setText(tagsArrayList.get(position));

            // 5. return rowView
            return rowView;
        }
    }

    public class MeusLocaisAdapter extends ArrayAdapter<String>
    {
        private final Context context;
        private final ArrayList<String> locaisArrayList;

        public MeusLocaisAdapter(Context context, ArrayList<String> locaisArrayList)
        {
            super(context, R.layout.item_local_exibicao_peca, locaisArrayList);

            this.context = context;
            this.locaisArrayList = locaisArrayList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            // 1. Create inflater
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 2. Get rowView from inflater
            View rowView = inflater.inflate(R.layout.item_local_exibicao_peca, parent, false);

            // 3. Get the two text view from the rowView
            TextView nomeLocalView = (TextView) rowView.findViewById(R.id.exibicao_peca_nome_local);

            // 4. Set the text for textView
            nomeLocalView.setText(locaisArrayList.get(position));

            // 5. return rowView
            return rowView;
        }
    }
}
