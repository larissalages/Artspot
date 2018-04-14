package com.sicaray.artspot.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.sicaray.artspot.data.Local;
import com.sicaray.artspot.data.Peca;
import com.sicaray.artspot.util.DownloadImageTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MinhasPecas extends AppCompatActivity
{
    ListView listView;
    RequestQueue requestQueue;
    ArrayList<Peca> listaItens;
    MinhasPecasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minhas_pecas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Minhas Peças");

        //Fila de requests do volley
        requestQueue = Volley.newRequestQueue(this);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        //Coloca as peças na listView
        listaItens = new ArrayList<Peca>();
        adapter = new MinhasPecasAdapter(this, listaItens);
        listView = (ListView) findViewById(R.id.minhas_pecas_list);
        listView.setAdapter(adapter);

        //Puxa as peças do usuário do servidor e carrega na listview
        SharedPreferences sharedPreferences = getSharedPreferences("ConfigFile", Context.MODE_PRIVATE);
        String auth_token = sharedPreferences.getString("auth_token", "none");

        if(!auth_token.equals("none"))
            RequestPecas(this, auth_token);
    }

    public void RequestPecas(final Context context, final String auth_token)
    {
        //Seta URL
        String requestURL = getString(R.string.server_url) + "api/images";

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
                            JSONObject jsonObjectPeca = null;
                            try {
                                jsonObjectPeca = response.getJSONObject(i);
                                Peca peca = new Peca(jsonObjectPeca);
                                listaItens.add(peca);
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
                        //Toast.makeText(context, "Falha de comunicação com o servidor. " + error.getMessage(), Toast.LENGTH_LONG).show();
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

        requestQueue.add(jsonArrayRequest);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.options_menu_add, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        //Para cada botao executa uma acao
        if (id == R.id.options_menu_button_add)
        {
            Intent intent = new Intent(this, CadastrarPeca.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void RequestDeletarPeca(final Context context, final String auth_token, final String id, final int arrayPosition)
    {
        //Seta URL
        String requestURL = getString(R.string.server_url) + "api/images/" + id;

        //Faz Request PUT
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, requestURL, null,

                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        listaItens.remove(arrayPosition);
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
                params.put("X-ArtSpot-Auth-Token", auth_token);
                return params;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }

    public class MinhasPecasAdapter extends ArrayAdapter<Peca>
    {

        private final Context context;
        private final ArrayList<Peca> itemsArrayList;

        public MinhasPecasAdapter(Context context, ArrayList<Peca> itemsArrayList)
        {
            super(context, R.layout.item_minhas_pecas, itemsArrayList);

            this.context = context;
            this.itemsArrayList = itemsArrayList;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {
            // 1. Create inflater
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 2. Get rowView from inflater
            View rowView = inflater.inflate(R.layout.item_minhas_pecas, parent, false);

            // Seta numero de likes e dislikes
            TextView numLikesView = (TextView) rowView.findViewById(R.id.item_minhas_pecas_numero_likes);
            TextView numDislikesView = (TextView) rowView.findViewById(R.id.item_minhas_pecas_numero_dislikes);
            numLikesView.setText(itemsArrayList.get(position).getNumLikes());
            numDislikesView.setText(itemsArrayList.get(position).getNumDislikes());

            //Mostrar a imagem da peca (se clicar vai para edição de peça)
            ImageView imagePeca = (ImageView) rowView.findViewById(R.id.item_minhas_pecas_foto_peca);

            imagePeca.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Peca listItem = (Peca) listView.getItemAtPosition(position);
                    Intent i = new Intent(listView.getContext(), CadastrarPeca.class);
                    i.putExtra("Peca", listItem);
                    startActivity(i);
                }
            });

            //Faz download da imagem da peça
            DownloadImageTask downloadImageTask = new DownloadImageTask(getString(R.string.server_url_no_slash) + itemsArrayList.get(position).getImageUrl(), imagePeca);
            downloadImageTask.execute();

            //Botão de deletar local
            Button deleteButton = (Button) rowView.findViewById(R.id.item_minhas_pecas_button_delete);

            deleteButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    //Pergunta se realmente quer deletar
                    new AlertDialog.Builder(context)
                            .setTitle("Apagar peça")
                            .setMessage("Tem certeza que gostaria de apagar esta peça? Essa ação não pode ser desfeita.")
                            .setPositiveButton("Apagar", new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    SharedPreferences sharedPreferences = getSharedPreferences("ConfigFile", Context.MODE_PRIVATE);
                                    String auth_token = sharedPreferences.getString("auth_token", "none");
                                    String id = itemsArrayList.get(position).getId();
                                    RequestDeletarPeca(context, auth_token, id, position);
                                }
                            })
                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int which) {
                                    //faz nada
                                }
                            })
                            .setIcon(R.drawable.ic_alerta)
                            .show();
                }
            });

            // 5. return rowView
            return rowView;
        }
    }
}
