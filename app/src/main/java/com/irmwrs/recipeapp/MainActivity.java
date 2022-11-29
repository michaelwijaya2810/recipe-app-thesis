package com.irmwrs.recipeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;
import com.irmwrs.recipeapp.Class.Recipe;
import com.irmwrs.recipeapp.fragments.RecipeListFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener{

    NavigationBarView bottomNav;
    Server server = new Server();
    Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.menu_3);

        bottomNav.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_1:
                server.getAllRecipe().enqueue(new Callback<List<Recipe>>() {
                    @Override
                    public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                        if (!response.isSuccessful()){
                            Toast.makeText(getApplicationContext(), String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        fragment = new RecipeListFragment(response.body(), MainActivity.this);
                        //Go to Recipe
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, fragment)
                                .commit();
                    }

                    @Override
                    public void onFailure(Call<List<Recipe>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                return true;
            case R.id.menu_2:
                //Go to Order
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.fragment_container, fragment)
//                        .commit();
                return true;
            case R.id.menu_3:
                //Go to Home
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.fragment_container, fragment)
//                        .commit();
                return true;
            case R.id.menu_4:
                //Go to History
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.fragment_container, fragment)
//                        .commit();
                return true;
            case R.id.menu_5:
                //Go to Settings
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.fragment_container, fragment)
//                        .commit();
                return true;
        }
        return false;
    }

}