package com.irmwrs.recipeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class RecipeDetailActivity extends AppCompatActivity {

    TabLayout tabLayout;
    TabLayoutMediator mediator;
    ViewPager2 recipeDetailPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        tabLayout = findViewById(R.id.tab_layout);
        recipeDetailPager = findViewById(R.id.recipe_detail_pager);
        mediator = new TabLayoutMediator(tabLayout, recipeDetailPager,
                (tab, position) -> {
            if (position == 0) {
                tab.setText("Ingredients");
            } else if (position == 1) {
                tab.setText("Steps");
            }
        });
        mediator.attach();

    }
}