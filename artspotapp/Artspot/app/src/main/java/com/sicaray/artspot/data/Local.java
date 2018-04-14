package com.sicaray.artspot.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Local implements Serializable
{
    private String id;
    private String nome;
    private String descricao;
    private String endereco;
    private String latitude;
    private String longitude;
    private String contato;
    private String horario;
    private ArrayList<String> listaPecasIds;

    public Local(JSONObject jsonObject) throws JSONException
    {
        super();

        this.id = jsonObject.getString("id");
        this.nome = jsonObject.getString("name");
        this.descricao = jsonObject.getString("description");
        this.endereco = jsonObject.getString("address");
        this.latitude = jsonObject.getString("latitude");
        this.longitude = jsonObject.getString("longitude");
        this.contato = jsonObject.getString("contact");
        this.horario = jsonObject.getString("working_hours");

        //Extrai a Lista de peças do json
        listaPecasIds = new ArrayList<String>();
        JSONArray jsonArrayPecas = jsonObject.getJSONArray("image_ids");
        for (int i = 0; i < jsonArrayPecas.length(); i++)
        {
            JSONObject jsonObjectPeca = jsonArrayPecas.getJSONObject(i);
            String peca = jsonObjectPeca.getString("image_id");
            listaPecasIds.add(peca);
        }
    }

    public String getId()
    {
        return id;
    }

    public String getNome()
    {
        return nome;
    }

    public String getDescricao()
    {
        return descricao;
    }

    public String getEndereco()
    {
        return endereco;
    }

    public String getLatitude()
    {
        return latitude;
    }

    public String getLongitude()
    {
        return longitude;
    }

    public String getContato()
    {
        return contato;
    }

    public String getHorario()
    {
        return horario;
    }

    public ArrayList<String> getListaPecasIds()
    {
        return listaPecasIds;
    }
}
