package com.example.alice_wang.datacollect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by alice_wang on 16/8/10.
 */
public class SwitchActivity extends Activity {
    public static int step = 0;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.switch_page);
        Button bttrain = (Button)findViewById(R.id.button_train);
        bttrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step = 1;
                Intent intent = new Intent(SwitchActivity.this, DataCollecting.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(new Intent(intent));
            }
        });
        Button bttest = (Button)findViewById(R.id.button_test);
        bttest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step = 2;
                Intent intent = new Intent(SwitchActivity.this,DataCollecting.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(new Intent(intent));
            }
        });

    }
}