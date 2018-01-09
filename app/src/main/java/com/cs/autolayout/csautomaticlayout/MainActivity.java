package com.cs.autolayout.csautomaticlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.cs.autolayout.csautomaticlayout.layout.CSLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    public CSLayout csLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        csLayout = (CSLayout) findViewById(R.id.csLayout);
        csLayout.addItemListener(new CSLayout.ItemListener() {
            @Override
            public void registerListener(int position, TextView textView) {
                Toast.makeText(MainActivity.this, "position:" + position + "->" + textView.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void click(View view) {
        String[] strings = {"sfsdfsfsdfsfs", "hahaa", "klllll", "just do it", "hello world", "lalallaal", "bottom", "top", "right"};
        csLayout.loadView(strings);
    }
}
