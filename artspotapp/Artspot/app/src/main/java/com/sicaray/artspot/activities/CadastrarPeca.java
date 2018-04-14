package com.sicaray.artspot.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
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

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CadastrarPeca extends AppCompatActivity
{
    private static int RESULT_LOAD_IMAGE = 1;

    ArrayList<Local> listaLocais;
    ArrayList<String> listaIDs;
    LocaisAdapter adapterLocais;
    ListView locaisView;

    ArrayList<String> listaTags;
    TagsAdapter adapterTags;
    ListView TagsView;

    boolean isEditingPeca;
    boolean imageChanged;
    Peca pecaEditada;

    RequestQueue requestQueue;

    //Pra fazer o upload da imagem
    String imageEncoded;
    String imageFilename;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_peca);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Nova Peça");

        //Fila de requests do volley
        requestQueue = Volley.newRequestQueue(this);

        //Prepara botão para carregar imagem da galeria
        Button buttonLoadImage = (Button) findViewById(R.id.cadastrar_peca_button_load_picture);

        buttonLoadImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
                imageChanged = true;
            }
        });

        //Pega token de autenticação
        SharedPreferences sharedPreferences = getSharedPreferences("ConfigFile", Context.MODE_PRIVATE);
        String auth_token = sharedPreferences.getString("auth_token", "none");

        listaTags = new ArrayList<String>();
        listaIDs = new ArrayList<String>();
        listaLocais = new ArrayList<Local>();

        //Recebe a peça da outra intent
        pecaEditada = null;
        isEditingPeca = false;
        imageChanged = true;

        Intent i = getIntent();
        pecaEditada = (Peca) i.getSerializableExtra("Peca");

        //Carrega as informações da peça na activity
        if(pecaEditada != null)
        {
            isEditingPeca = true;
            imageChanged = false;

            //Foto
            ImageView foto = (ImageView) findViewById(R.id.cadastrar_peca_foto);
            DownloadImageTask downloadImageTask = new DownloadImageTask(getString(R.string.server_url_no_slash) + pecaEditada.getImageUrl(), foto);
            downloadImageTask.execute();

            //Tags
            listaTags = pecaEditada.getListaTags();

            //IDs dos locais selecionados
            listaIDs = pecaEditada.getListaPlacesIds();
        }

        //Prepara a lista de locais do usuário para colocar a peça
        adapterLocais = new LocaisAdapter(this, listaLocais);
        locaisView = (ListView) findViewById(R.id.cadastrar_peca_listview_locais);
        locaisView.setAdapter(adapterLocais);
        RequestLocaisUsuario(this, auth_token);

        //Prepara a listview de tags da peça
        adapterTags = new TagsAdapter(this, listaTags);
        TagsView = (ListView) findViewById(R.id.cadastrar_peca_listview_tags);
        TagsView.setAdapter(adapterTags);
        setListViewHeightBasedOnChildren(TagsView);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data)
        {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            //Seta a imagem da foto
            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
            ImageView foto = (ImageView) findViewById(R.id.cadastrar_peca_foto);
            foto.setImageBitmap(bitmap);

            //Encoda a imagem em base64 e guarda
            imageFilename = picturePath;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] b = baos.toByteArray();
            imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        }
    }

    public void OnClickButtonMais(View View)
    {
        EditText editTextTag = (EditText) findViewById(R.id.cadastrar_peca_campo_categoria);
        String strTag = editTextTag.getText().toString();

        //Checa se o campo de tag foi preenchido
        if(!strTag.isEmpty())
        {
            editTextTag.setText("");
            listaTags.add(strTag);
            adapterTags.notifyDataSetChanged();
            setListViewHeightBasedOnChildren(TagsView);
        }

        else
            Toast.makeText(this, "Digite uma categoria.", Toast.LENGTH_SHORT).show();
    }

    public void OnClickButtonSalvar(View View)
    {
        //Só deixa cadastrar se essa variável permanecer verdadeira
        boolean cadastroOK = true;

        ImageView foto = (ImageView) findViewById(R.id.cadastrar_peca_foto);

        //Checa se todos os campos foram preenchidos
        if(foto.getDrawable() == null ||listaTags.isEmpty() || listaIDs.isEmpty())
        {
            Toast.makeText(this, "Coloque pelo menos uma foto, categoria e um local para sua peça.", Toast.LENGTH_SHORT).show();
            cadastroOK = false;
        }

        if(cadastroOK)
        {
            //Manda upar a imagem. Se o request der certo dentro dele já faz o request pra cadastrar peça
            SharedPreferences sharedPreferences = getSharedPreferences("ConfigFile", Context.MODE_PRIVATE);
            String auth_token = sharedPreferences.getString("auth_token", "none");

            //Se é uma nova peça ou está editando uma peça e mudou a foto
            if(!isEditingPeca || (isEditingPeca && imageChanged))
                RequestUploadImage(this, auth_token, imageFilename, imageEncoded);
            //Se está editando uma peça mas não mudou a foto
            else
                RequestEditarPecaSemMudarFoto(this, auth_token, pecaEditada.getId());
        }
    }

    public void RequestLocaisUsuario(final Context context, final String auth_token)
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
                                listaLocais.add(local);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        adapterLocais.notifyDataSetChanged();
                        setListViewHeightBasedOnChildren(locaisView);
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

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

    public void RequestUploadImage(final Context context, final String auth_token, String filename, String imageBase64)
    {
        //Seta URL
        String requestURL = getString(R.string.server_url) + "api/uploads";

        //Cria json para ser enviado no body
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("content_type", "image/jpg");
            jsonObject.put("file_name", filename);
            jsonObject.put("content", imageBase64);
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
                        try {
                            String upload_id = response.getString("id");

                            if(!isEditingPeca)
                                RequestCadastrarPeca(context, auth_token, upload_id);
                            else
                                RequestEditarPeca(context, auth_token, upload_id, pecaEditada.getId());

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
                        Toast.makeText(context, "Falha de comunicação com o servidor ao fazer upload da imagem. " + error.getMessage(), Toast.LENGTH_LONG).show();
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

    public void RequestEditarPeca(final Context context, final String auth_token, final String upload_id, final String peca_id)
    {
        //Seta URL
        String requestURL = getString(R.string.server_url) + "api/images/" + peca_id;

        //Cria json para ser enviado no body
        JSONArray jsonArrayPlaceIds = new JSONArray();
        for(int i = 0; i < listaIDs.size(); i++)
        {
            JSONObject json_place_id = new JSONObject();
            try {
                json_place_id.put("place_id", listaIDs.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArrayPlaceIds.put(json_place_id);
        }

        JSONArray jsonArrayTags = new JSONArray();
        for(int i = 0; i < listaTags.size(); i++)
        {
            JSONObject json_tag = new JSONObject();
            try {
                json_tag.put("tag", listaTags.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArrayTags.put(json_tag);
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("upload_id", upload_id);
            jsonObject.put("place_ids", jsonArrayPlaceIds);
            jsonObject.put("tags", jsonArrayTags);
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
                        Toast.makeText(context, "Peça editada com sucesso!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(context, "Falha de comunicação com o servidor ao editar a peça. " + error.getMessage(), Toast.LENGTH_LONG).show();
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

    public void RequestEditarPecaSemMudarFoto(final Context context, final String auth_token, final String peca_id)
    {
        //Seta URL
        String requestURL = getString(R.string.server_url) + "api/images/" + peca_id;

        //Cria json para ser enviado no body
        JSONArray jsonArrayPlaceIds = new JSONArray();
        for(int i = 0; i < listaIDs.size(); i++)
        {
            JSONObject json_place_id = new JSONObject();
            try {
                json_place_id.put("place_id", listaIDs.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArrayPlaceIds.put(json_place_id);
        }

        JSONArray jsonArrayTags = new JSONArray();
        for(int i = 0; i < listaTags.size(); i++)
        {
            JSONObject json_tag = new JSONObject();
            try {
                json_tag.put("tag", listaTags.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArrayTags.put(json_tag);
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("place_ids", jsonArrayPlaceIds);
            jsonObject.put("tags", jsonArrayTags);
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
                        Toast.makeText(context, "Peça editada com sucesso!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(context, "Falha de comunicação com o servidor ao editar a peça. " + error.getMessage(), Toast.LENGTH_LONG).show();
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

    public void RequestCadastrarPeca(final Context context, final String auth_token, final String upload_id)
    {
        //Seta URL
        String requestURL = getString(R.string.server_url) + "api/images";

        //Cria json para ser enviado no body
        JSONArray jsonArrayPlaceIds = new JSONArray();
        for(int i = 0; i < listaIDs.size(); i++)
        {
            JSONObject json_place_id = new JSONObject();
            try {
                json_place_id.put("place_id", listaIDs.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArrayPlaceIds.put(json_place_id);
        }

        JSONArray jsonArrayTags = new JSONArray();
        for(int i = 0; i < listaTags.size(); i++)
        {
            JSONObject json_tag = new JSONObject();
            try {
                json_tag.put("tag", listaTags.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArrayTags.put(json_tag);
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("upload_id", upload_id);
            jsonObject.put("place_ids", jsonArrayPlaceIds);
            jsonObject.put("tags", jsonArrayTags);
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
                        Toast.makeText(context, "Nova peça cadastrada com sucesso!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(context, "Falha de comunicação com o servidor ao cadastrar a peça. " + error.getMessage(), Toast.LENGTH_LONG).show();
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

    public class TagsAdapter extends ArrayAdapter<String>
    {
        private final Context context;
        private final ArrayList<String> tagsArrayList1;

        public TagsAdapter(Context context, ArrayList<String> tagsArrayList1)
        {
            super(context, R.layout.item_pecas_tags, tagsArrayList1);

            this.context = context;
            this.tagsArrayList1 = tagsArrayList1;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {
            // 1. Create inflater
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 2. Get rowView from inflater
            View rowView = inflater.inflate(R.layout.item_pecas_tags, parent, false);

            // 3. Get the two text view from the rowView
            TextView nomeTagView = (TextView) rowView.findViewById(R.id.item_tag);

            // 4. Set the text for textView
            nomeTagView.setText(tagsArrayList1.get(position));

            //Seta pra quando clicar na tag remover ela
            nomeTagView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    listaTags.remove(position);
                    adapterTags.notifyDataSetChanged();
                    setListViewHeightBasedOnChildren(TagsView);
                }
            });

            // 5. return rowView
            return rowView;
        }
    }

    public class LocaisAdapter extends ArrayAdapter<Local>
    {
        private final Context context;
        private final ArrayList<Local> itemsArrayList;

        public LocaisAdapter(Context context, ArrayList<Local> itemsArrayList)
        {
            super(context, R.layout.item_check_box, itemsArrayList);

            this.context = context;
            this.itemsArrayList = itemsArrayList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            // 1. Create inflater
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 2. Get rowView from inflater
            View rowView = inflater.inflate(R.layout.item_check_box, parent, false);

            //Pega o Checkbox do local
            final CheckBox cb = (CheckBox) rowView.findViewById(R.id.checkbox_local_pecas);

            //Seta o nome do local no checkbox
            cb.setText(itemsArrayList.get(position).getNome());

            //Deixa o checkbox marcado ou não dependendo do ítem
            if(listaIDs.contains(listaLocais.get(position).getId()))
                cb.setChecked(true);
            else
                cb.setChecked(false);

            //Ao clicar no checkbox ele coloca/remove id do local da lista de ids
            final String localId = itemsArrayList.get(position).getId();
            cb.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    //Se tiver selecionado, coloca na lista de IDs
                    if(cb.isChecked())
                    {
                        if(!listaIDs.contains(localId))
                            listaIDs.add(localId);
                    }

                    //Se nao tiver mais selecionado, tira da lista de IDs se ele tiver la
                    else
                    {
                        if(listaIDs.contains(localId))
                            listaIDs.remove(localId);
                    }
                }
            });

            // 5. return rowView
            return rowView;
        }
    }
}
