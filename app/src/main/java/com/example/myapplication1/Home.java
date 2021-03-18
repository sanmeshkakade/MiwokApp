package com.example.myapplication1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.home);

        //Find the view pager that allows user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        //Create an adapter that knows which fragment shold be shown on each page
        SimpleFragmentPageAdapter adapter = new SimpleFragmentPageAdapter(getSupportFragmentManager());

        //set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

//        //finding view by id
//        TextView numbers = findViewById(R.id.numbers);
//        TextView familiy = findViewById(R.id.family);
//        TextView colors = findViewById(R.id.colors);
//        TextView phrases = findViewById(R.id.phrases);
//
//        //setting onclick method
//        numbers.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //creating intent to numbers activity
//                Intent numberIntent = new Intent(Home.this,NumbersActivity.class);
//                //start activity
//                startActivity(numberIntent);
//            }
//        });
//        phrases.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //creating intent to phrase activity
//                Intent phraseIntent = new Intent(Home.this,PhrasesActivity.class);
//                //start activity
//                startActivity(phraseIntent);
//            }
//        });
//        familiy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //creating intent to familiy activity
//                Intent familiyIntent = new Intent(Home.this,FamiliyMembersActivity.class);
//                //start activity
//                startActivity(familiyIntent);
//            }
//        });
//        colors.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //creating intent to colors activity
//                Intent colorsIntent = new Intent(Home.this,ColorsActivity.class);
//                //start activity
//                startActivity(colorsIntent);
//            }
//        });


    }
}
