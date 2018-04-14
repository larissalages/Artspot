package com.sicaray.artspot.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Peca implements Serializable
{
    private String id;
    private String idDono;
    private String imageUrl;
    private String numLikes;
    private String numDislikes;
    private ArrayList<String> listaTags;
    private ArrayList<String> listaPlacesIds;

    public Peca(JSONObject jsonObject) throws JSONException
    {
        super();
        this.id = jsonObject.getString("id");
        this.idDono = jsonObject.getString("user_id");
        this.imageUrl = jsonObject.getString("image_uri");
        this.numLikes = jsonObject.getString("likes");
        this.numDislikes = jsonObject.getString("hates");

        //Extrai a Lista de tags
        listaTags = new ArrayList<String>();
        JSONArray jsonArrayTags = jsonObject.getJSONArray("tags");
        for (int i = 0; i < jsonArrayTags.length(); i++)
        {
            JSONObject jsonObjectTag = jsonArrayTags.getJSONObject(i);
            String tag = jsonObjectTag.getString("tag");
            listaTags.add(tag);
        }

        //Extrai a Lista de locais onde a peça se encontra
        listaPlacesIds = new ArrayList<String>();
        JSONArray jsonArrayPlaces = jsonObject.getJSONArray("place_ids");
        for (int i = 0; i < jsonArrayPlaces.length(); i++)
        {
            JSONObject jsonObjectPlace = jsonArrayPlaces.getJSONObject(i);
            String place = jsonObjectPlace.getString("place_id");
            listaPlacesIds.add(place);
        }
    }

    public String getId()
    {
        return id;
    }

    public String getIdDono()
    {
        return idDono;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public String getNumLikes()
    {
        return numLikes;
    }

    public String getNumDislikes()
    {
        return numDislikes;
    }

    public ArrayList<String> getListaTags()
    {
        return listaTags;
    }

    public ArrayList<String> getListaPlacesIds()
    {
        return listaPlacesIds;
    }
}