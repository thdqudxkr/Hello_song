package org.androidtown.hello_song;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void onBackButtonClicked(View v) {
        Toast.makeText(getApplicationContext(),"Pressed Backward Button!",Toast.LENGTH_LONG).show();
        finish();
    }

}
