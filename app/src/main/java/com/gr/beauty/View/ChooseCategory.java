package com.gr.beauty.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import com.gr.beauty.R;

/**

 * Created by gaurav on 11/3/17.

 */

public class ChooseCategory extends AppCompatActivity{

    private TextView salonCategory;
    private TextView customerCategory;
    private TypeWriter typeWriter;

    public static final String CategoryPreferences = "CategoryPrefs" ;
    public static final String Category = "Category";

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_category);

        sharedPreferences = getSharedPreferences(CategoryPreferences,MODE_PRIVATE);
        if(sharedPreferences.getString(Category,"").equals("Customer")) {
            startActivity(new Intent(this, CustomerLogin.class));
            finish();
        }
        else if(sharedPreferences.getString(Category,"").equals("Salon")){
            startActivity(new Intent(this,SalonLogin.class));
            finish();
        }

        initialization();
    }

    public void initialization(){

        typeWriter = (TypeWriter) findViewById(R.id.typeWriter);
        typeWriter.setText("");
        typeWriter.setCharacterDelay(70);
        typeWriter.animateText("SELECT YOUR CATEGORY");

        salonCategory = (TextView)findViewById(R.id.salonCategory);
        customerCategory = (TextView)findViewById(R.id.customerCategory);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                salonCategory.setVisibility(View.VISIBLE);
                customerCategory.setVisibility(View.VISIBLE);
            }
        },1600);

        sharedPreferences = getSharedPreferences(CategoryPreferences,MODE_PRIVATE);
    }

    public void onSalonCategoryClick(View view)
    {
        Intent intent=new Intent(this,SalonLogin.class);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putString(Category,"Salon");
        editor.commit();
        startActivity(intent);
        finish();
    }

    public void onCustomerCategoryClick(View view)
    {
        Intent intent=new Intent(this,CustomerLogin.class);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putString(Category,"Customer");
        editor.commit();
        startActivity(intent);
        finish();
    }

}