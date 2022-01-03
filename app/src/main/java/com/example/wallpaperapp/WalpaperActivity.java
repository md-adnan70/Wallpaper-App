
package com.example.wallpaperapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.IOException;

public class WalpaperActivity extends AppCompatActivity {

    private ImageView wallpaperIV;
    private Button setWallpaerBtn;
    private String imageUrl;
    WallpaperManager wallpaperManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walpaper);
        wallpaperIV = findViewById(R.id.idIVWallpaper);
        setWallpaerBtn = findViewById(R.id.idBtnSetWallpaper);
        imageUrl = getIntent().getStringExtra("imageUrl");
        Glide.with(this).load(imageUrl).into(wallpaperIV);
        wallpaperManager = WallpaperManager.getInstance(getApplicationContext());

        setWallpaerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(WalpaperActivity.this).asBitmap().load(imageUrl).listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        Toast.makeText(WalpaperActivity.this, "Failed to load image..", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        try {
                            wallpaperManager.setBitmap(resource);
                        }catch (IOException e){
                            e.printStackTrace();
                            Toast.makeText(WalpaperActivity.this, "Fail to set wallpaper..", Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                }).submit();
            }
        });
        Toast.makeText(this, "Wallpaper set to home screen", Toast.LENGTH_LONG).show();


    }
}