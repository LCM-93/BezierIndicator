package com.lcm.bezierbottomIndicator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    private BezierBottomIndicator bezierBottomIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bezierBottomIndicator = (BezierBottomIndicator) findViewById(R.id.bezierBottomIndicator);



    }
}
