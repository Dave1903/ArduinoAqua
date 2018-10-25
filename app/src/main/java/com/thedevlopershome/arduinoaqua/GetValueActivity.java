package com.thedevlopershome.arduinoaqua;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.w3c.dom.Text;

public class GetValueActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_value);

          sharedPreferences= getSharedPreferences("userData", Activity.MODE_PRIVATE);


         final MaterialEditText length = (MaterialEditText) findViewById(R.id.length);
         final MaterialEditText width = (MaterialEditText) findViewById(R.id.width);
         final MaterialEditText height = (MaterialEditText) findViewById(R.id.height);

         TextView button = (TextView)findViewById(R.id.requestButton);

         button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 SharedPreferences.Editor editor=sharedPreferences.edit();
                 editor.putString("height",height.getText().toString());
                 editor.putString("length",length.getText().toString());
                 editor.putString("width",width.getText().toString());
                 editor.putInt("dataCheck",1);
                 editor.apply();
                 Intent intent=new Intent(GetValueActivity.this,AquaMainActivity.class);
                 startActivity(intent);

             }
         });



    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }



}
