package com.sicaray.artspot.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;

public class DownloadImageTask extends AsyncTask<Void, Void, Bitmap>
{
    ImageView imageView;
    String imageUrl;

    //Construtor Passa a ImageView que você quer carregar a imagem baixada
    public DownloadImageTask(String imageUrl, ImageView imageView)
    {
        this.imageUrl = imageUrl;
        this.imageView = imageView;
    }

    //Faz o download da imagem contida na url passada por parametro
    protected Bitmap doInBackground(Void... params)
    {
        Bitmap bitmap = null;
        try {
            InputStream in = new java.net.URL(imageUrl).openStream();
            bitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    //Carrega a imagem no imageView passado
    protected void onPostExecute(Bitmap result)
    {
        imageView.setImageBitmap(result);
    }
}
