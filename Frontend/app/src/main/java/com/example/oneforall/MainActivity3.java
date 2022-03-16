package com.example.oneforall;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity3 extends AppCompatActivity {

    WebView webView = (WebView) findViewById(R.id.web);
    URI r;
    URL u;
    TextView textView = findViewById(R.id.textView);
    private static final String READ_EXTERNAL_STORAGE = "1";
    private static final String WRITE_EXTERNAL_STORAGE = "1";
    ActivityResultLauncher<Intent> activityResultLauncher;
    String[] permission= {READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE};
    private final String url="https://9800-122-163-252-210.ngrok.io";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Bundle b = getIntent().getExtras();
        String path = b.getString("path");
        textView.setText(path);

        try {
            r=new URI(path);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try {
            u=r.toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://docs.google.com/gview?embedded=true&url=" + u);

        File file = new File(path);
        //************************************************************************************************************
        ImageButton ConvertToDocx= (ImageButton) findViewById(R.id.todoc);

        ConvertToDocx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (file.exists()) {
                    if (checkPermission()) {
                        OkHttpClient client = new OkHttpClient();
                        RequestBody formBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file", file.getName(), RequestBody.create(file, MediaType.parse("text/txt"))).build();
                        Request request = new Request.Builder().url(url).post(formBody).build();
                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                Toast.makeText(MainActivity3.this, "network not found", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            textView.setText(response.body().string());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                            }
                        });
                    } else {
                        requestPermission();
                    }
                } else {
                    textView.setText("File not found");
                }
            }
        });


        //**********************************************************************************************************
        ImageButton ConvertToPdf= (ImageButton) findViewById(R.id.topd);

        ConvertToPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (file.exists()) {
                    if (checkPermission()) {
                        OkHttpClient client = new OkHttpClient();
                        RequestBody formBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file", file.getName(), RequestBody.create(file, MediaType.parse("text/txt"))).build();
                        Request request = new Request.Builder().url(url).post(formBody).build();
                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                Toast.makeText(MainActivity3.this, "network not found", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            textView.setText(response.body().string());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                            }
                        });
                    } else {
                        requestPermission();
                    }
                } else {
                    textView.setText("File not found");
                }
            }
        });

        //********************************************************************************************************************
        ImageButton ConvertToJpg= (ImageButton) findViewById(R.id.tojp);

        ConvertToJpg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (file.exists()) {
                    if (checkPermission()) {
                        OkHttpClient client = new OkHttpClient();
                        RequestBody formBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file", file.getName(), RequestBody.create(file, MediaType.parse("text/txt"))).build();
                        Request request = new Request.Builder().url(url).post(formBody).build();
                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                Toast.makeText(MainActivity3.this, "network not found", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            textView.setText(response.body().string());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                            }
                        });
                    } else {
                        requestPermission();
                    }
                } else {
                    textView.setText("File not found");
                }
            }
        });


//**************************************************************************************************************
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode()== Activity.RESULT_OK){
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.R){
                        if(Environment.isExternalStorageManager()){
                            Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else {
            int result = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
            int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
        }
    }
    void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s",new Object[]{getApplicationContext().getPackageName()})));
                activityResultLauncher.launch(intent);
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                activityResultLauncher.launch(intent);
            }
        } else {
            ActivityCompat.requestPermissions(MainActivity3.this, permission,0);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0:
                if (grantResults.length > 0) {
                    boolean READ_EXTERNAL_STORAGE = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean WRITE_EXTERNAL_STORAGE = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (READ_EXTERNAL_STORAGE && WRITE_EXTERNAL_STORAGE) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "You Denied the Permission", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}

