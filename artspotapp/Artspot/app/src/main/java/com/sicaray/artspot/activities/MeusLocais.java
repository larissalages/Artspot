package com.sicaray.artspot.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MeusLocais extends AppCompatActivity
{
    ListView listView;
    RequestQueue requestQueue;
    ArrayList<Local> listaMeusLocais;
    MeusLocaisAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_locais);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Meus Locais");

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

        listaMeusLocais = new ArrayList<Local>();
        adapter = new MeusLocaisAdapter(this, listaMeusLocais);
        listView = (ListView) findViewById(R.id.meus_locais_list);
        listView.setAdapter(adapter);

        //Busca os locais do usuário no backend e coloca na listview
        SharedPreferences sharedPreferences = getSharedPreferences("ConfigFile", Context.MODE_PRIVATE);
        String auth_token = sharedPreferences.getString("auth_token", "none");

        if(!auth_token.equals("none"))
            RequestLocais(this, auth_token);
    }

    public void RequestLocais(final Context context, final String auth_token)
    {
        //Seta URL
        String requestURL = getString(R.string.server_url) + "api/places";

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
                                listaMeusLocais.add(local);
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

        if (id == R.id.options_menu_button_add)
        {
            Intent intent = new Intent(this, CadastrarLocal.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void RequestDeletarLocal(final Context context, final String auth_token, final String id, final int arrayPosition)
    {
        //Seta URL
        String requestURL = getString(R.string.server_url) + "api/places/" + id;

        //Faz Request PUT
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, requestURL, null,

                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        listaMeusLocais.remove(arrayPosition);
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

    public class MeusLocaisAdapter extends ArrayAdapter<Local>
    {
        private final Context context;
        private final ArrayList<Local> itemsArrayList;

        public MeusLocaisAdapter(Context context, ArrayList<Local> itemsArrayList)
        {
            super(context, R.layout.item_meus_locais, itemsArrayList);

            this.context = context;
            this.itemsArrayList = itemsArrayList;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {
            // 1. Create inflater
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 2. Get rowView from inflater
            View rowView = inflater.inflate(R.layout.item_meus_locais, parent, false);

            //Seta o endereço do local
            TextView enderecoView = (TextView) rowView.findViewById(R.id.item_meus_locais_endereco);
            enderecoView.setText(itemsArrayList.get(position).getEndereco());

            //Nome clicavel para editar o local
            TextView nomeView = (TextView) rowView.findViewById(R.id.item_meus_locais_nome);
            nomeView.setText(itemsArrayList.get(position).getNome());

            nomeView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Local listItem = (Local) listView.getItemAtPosition(position);
                    Intent i = new Intent(listView.getContext(), CadastrarLocal.class);
                    i.putExtra("Local", listItem);
                    startActivity(i);
                }
            });

            //Botão de deletar local
            Button deleteButton = (Button) rowView.findViewById(R.id.item_meus_locais_button_delete);
            deleteButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    //Pergunta se realmente quer deletar
                    new AlertDialog.Builder(context)
                            .setTitle("Apagar local")
                            .setMessage("Tem certeza que gostaria de apagar este local? Essa ação não pode ser desfeita.")
                            .setPositiveButton("Apagar", new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    SharedPreferences sharedPreferences = getSharedPreferences("ConfigFile", Context.MODE_PRIVATE);
                                    String auth_token = sharedPreferences.getString("auth_token", "none");
                                    String id = itemsArrayList.get(position).getId();
                                    RequestDeletarLocal(context, auth_token, id, position);
                                }
                            })
                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int which)
                                {
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
