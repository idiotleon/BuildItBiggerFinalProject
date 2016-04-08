package com.leontheprofessional.joketeller;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by v-ylangu on 4/5/2016.
 */
public class AndroidLibJokeTellerActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        String joke = getIntent().getStringExtra("joke");
        Toast.makeText(AndroidLibJokeTellerActivity.this, joke, Toast.LENGTH_SHORT).show();
    }
}
