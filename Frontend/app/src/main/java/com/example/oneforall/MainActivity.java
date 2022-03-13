package com.example.oneforall;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    String path;

    ActivityResultLauncher <String> activityResult = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            path = result.toString();
            readActivity();
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button ConvertToPdf= findViewById(R.id.button);

        ConvertToPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Filechooser();
                }
        });

    }
    public void Filechooser()
    {
        try{
            activityResult.launch("*/*");
        }
        catch(android.content.ActivityNotFoundException ex){
            Toast.makeText(this, "Please install a file manager", Toast.LENGTH_SHORT).show();
        }
    }
    public void readActivity(){

        Intent a= new Intent(MainActivity.this, MainActivity2.class);
        a.putExtra("path",path);
        startActivity(a);
    }



}
