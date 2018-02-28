package com.example.qklahpita.draw;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_PERMISSION = 1;
    private FloatingActionButton fbCamera;
    private FloatingActionButton fbBrush;
    private FloatingActionMenu fbMenu;
    private GridView gvImages;
    private LinearLayout lltext;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupPermission();
        setupUI();
    }

    private void setupUI() {
        fbCamera = findViewById(R.id.fb_camera);
        fbBrush = findViewById(R.id.fb_brush);
        fbMenu = findViewById(R.id.fb_menu);
        gvImages = findViewById(R.id.gv_images);
        lltext = findViewById(R.id.ll_text);

        GridImageAdapter gridImageAdapter = new GridImageAdapter(this);
        gvImages.setAdapter(gridImageAdapter);
        if (gridImageAdapter.getCount() > 0) lltext.setVisibility(View.INVISIBLE);
        else lltext.setVisibility(View.VISIBLE);
        gvImages.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int pos = (int) view.getTag();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Delete?")
                        .setPositiveButton("Y", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ImageUtils.deleteImage(pos, MainActivity.this);
                                onStart();
                            }
                        })
                        .setNegativeButton("N", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
                return false;
            }
        });

        fbCamera.setOnClickListener(this);
        fbBrush.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        GridImageAdapter gridImageAdapter = new GridImageAdapter(this);
        gvImages.setAdapter(gridImageAdapter);
        if (gridImageAdapter.getCount() > 0) lltext.setVisibility(View.INVISIBLE);
        else lltext.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, DrawActivity.class);
        if (view.getId() == R.id.fb_camera) {
            intent.putExtra("camera_mode", true);
        } else {
            intent.putExtra("camera_mode", false);
        }

        startActivity(intent);

        fbMenu.close(false);
    }

    private void setupPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_PERMISSION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Warning")
                        .setMessage("Can't run app without permission. Grant permission?")
                        .setPositiveButton("Y", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(
                                        MainActivity.this,
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        REQUEST_PERMISSION);

                            }
                        })
                        .setNegativeButton("N", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                MainActivity.this.finish();
                            }
                        })
                        .show();
            }
        }
    }
}
