package com.example.oneforall;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity2 extends AppCompatActivity {
    private static final String READ_EXTERNAL_STORAGE = "1";
    private static final String WRITE_EXTERNAL_STORAGE = "1";
    String[] permission= {READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE};
    ActivityResultLauncher<Intent> activityResultLauncher;

    private final String url="https://9800-122-163-252-210.ngrok.io";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Bundle a = getIntent().getExtras();
        String path = a.getString("path");
        String uri = a.getString("uri");
        String abs =path.substring(5);

        TextView textView = findViewById(R.id.textView2);

        File file = new File(abs);
        StringBuilder tContents = new StringBuilder();
        try {
            BufferedReader br=new BufferedReader(new FileReader(file));
            String s ;
            while((s=br.readLine())!=null){
                tContents.append(s).append("/n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            textView.setText(tContents);
        }
//************************************************************************************************************
        ImageButton ConvertToDocx= (ImageButton) findViewById(R.id.todocx);

        ConvertToDocx.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (file.exists()) {
                        if (checkPermission()) {
                            OkHttpClient client = new OkHttpClient();
                            RequestBody formBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                                    .addFormDataPart("file", file.getName(), RequestBody.create(file, MediaType.parse("text/txt")))
                            .addFormDataPart("file", file.getName(), RequestBody.create(file, MediaType.parse("Image/jpg")))
                            .addFormDataPart("file", file.getName(), RequestBody.create(file, MediaType.parse("Image/jpeg")))
                            .addFormDataPart("file", file.getName(), RequestBody.create(file, MediaType.parse("Image/png"))).build();
                            Request request = new Request.Builder().url(url).post(formBody).build();
                            client.newCall(request).enqueue(new Callback() {
                                @Override
                                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                    Toast.makeText(MainActivity2.this, "network not found", Toast.LENGTH_LONG).show();
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
        ImageButton ConvertToPdf= (ImageButton) findViewById(R.id.topdf);

        ConvertToPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (file.exists()) {
                    if (checkPermission()) {
                        OkHttpClient client = new OkHttpClient();
                        RequestBody formBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                                .addFormDataPart("file", file.getName(), RequestBody.create(file, MediaType.parse("text/txt")))
                                .addFormDataPart("file", file.getName(), RequestBody.create(file, MediaType.parse("Image/jpg")))
                                .addFormDataPart("file", file.getName(), RequestBody.create(file, MediaType.parse("Image/jpeg")))
                                .addFormDataPart("file", file.getName(), RequestBody.create(file, MediaType.parse("Image/png"))).build();
                        Request request = new Request.Builder().url(url).post(formBody).build();
                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                Toast.makeText(MainActivity2.this, "network not found", Toast.LENGTH_LONG).show();
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
        ImageButton ConvertToJpg= (ImageButton) findViewById(R.id.tojpg);

        ConvertToJpg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (file.exists()) {
                    if (checkPermission()) {
                        OkHttpClient client = new OkHttpClient();
                        RequestBody formBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                                .addFormDataPart("file", file.getName(), RequestBody.create(file, MediaType.parse("text/txt")))
                                .addFormDataPart("file", file.getName(), RequestBody.create(file, MediaType.parse("Image/jpg")))
                                .addFormDataPart("file", file.getName(), RequestBody.create(file, MediaType.parse("Image/jpeg")))
                                .addFormDataPart("file", file.getName(), RequestBody.create(file, MediaType.parse("Image/png"))).build();
                        Request request = new Request.Builder().url(url).post(formBody).build();
                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                Toast.makeText(MainActivity2.this, "network not found", Toast.LENGTH_LONG).show();
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
            ActivityCompat.requestPermissions(MainActivity2.this, permission,0);
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