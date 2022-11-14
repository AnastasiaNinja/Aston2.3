package com.example.aston23;

import static java.lang.System.err;
import static java.lang.System.in;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.telecom.Call;
import android.util.Log;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aston23.databinding.ActivityMainBinding;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.security.auth.callback.Callback;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        EditText editText = binding.editText;
        ImageView imageView = binding.imageView;

        editText.setOnEditorActionListener((textView, i, keyEvent) -> {
            String url =  editText.getText().toString();
            imageView.setImageBitmap(null);
            if(url.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Failed to load image", Toast.LENGTH_LONG).show();
            } else {
            LoadImage loadImage = new LoadImage(imageView);
            loadImage.execute(url);
            }
            return true;
        });
    }

    private class LoadImage extends AsyncTask<String, Void, Bitmap>{
        ImageView image;
        public LoadImage(ImageView imageView) {
           this.image = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String urlLink = strings[0];
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new URL(urlLink).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            image.setImageBitmap(bitmap);
        }
    }
}