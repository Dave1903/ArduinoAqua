package com.thedevlopershome.arduinoaqua;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import me.itangqi.waveloadingview.WaveLoadingView;

public class AquaMainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

          sharedPreferences= getSharedPreferences("userData", Activity.MODE_PRIVATE);

        String firstTime=sharedPreferences.getString("height","null");

        if(firstTime.equals("null"))
        {
            Intent intent=new Intent(AquaMainActivity.this,GetValueActivity.class);
            startActivity(intent);

        }


        setContentView(R.layout.activity_aqua_main);

        final WaveLoadingView mWaveLoadingView = (WaveLoadingView) findViewById(R.id.waveLoadingView);
        mWaveLoadingView.setShapeType(WaveLoadingView.ShapeType.CIRCLE);
        mWaveLoadingView.setTopTitle(" ");
        mWaveLoadingView.setCenterTitleColor(Color.BLACK);
        mWaveLoadingView.setBottomTitleSize(18);
        mWaveLoadingView.setProgressValue(100);
        mWaveLoadingView.setBorderWidth(10);
        mWaveLoadingView.setAmplitudeRatio(60);
        mWaveLoadingView.setWaveColor(Color.parseColor("#537b8d"));
        mWaveLoadingView.setBorderColor(Color.TRANSPARENT);
        mWaveLoadingView.setTopTitleStrokeColor(Color.BLUE);
        mWaveLoadingView.setTopTitleStrokeWidth(3);
        mWaveLoadingView.setAnimDuration(3000);
        mWaveLoadingView.pauseAnimation();
        mWaveLoadingView.resumeAnimation();
        mWaveLoadingView.cancelAnimation();
        mWaveLoadingView.startAnimation();

        TextView data1=(TextView)findViewById(R.id.data1);
        TextView data2=(TextView)findViewById(R.id.data2);
        TextView data3=(TextView)findViewById(R.id.data3);

        data2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AquaMainActivity.this,DataAnalysisActivity.class);
                startActivity(intent);
            }
        });


        new MyAsyncTaskAqua(this, new MyAsyncTaskAqua.AsyncResponse() {
            @Override
            public void processFinish(DetailsData output) {
                float height= Float.parseFloat(sharedPreferences.getString("height","0"));
                int percent= (int) (((height-output.y)/height)*100);
                mWaveLoadingView.setProgressValue(percent);
                mWaveLoadingView.setCenterTitle(percent+"%");
            }
        },1).execute();



        data3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MyAsyncTaskAqua(AquaMainActivity.this, new MyAsyncTaskAqua.AsyncResponse() {
                    @Override
                    public void processFinish(DetailsData output) {
                        float height= Float.parseFloat(sharedPreferences.getString("height","0"));
                        int percent= (int) (((height-output.y)/height)*100);
                        mWaveLoadingView.setProgressValue(percent);
                        mWaveLoadingView.setCenterTitle(percent+"%");

                    }
                },1).execute();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        sharedPreferences= getSharedPreferences("userData", Activity.MODE_PRIVATE);

        String firstTime=sharedPreferences.getString("height","null");

        if(firstTime.equals("null"))
        {
            Intent intent=new Intent(AquaMainActivity.this,GetValueActivity.class);
            startActivity(intent);

        }


    }


    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }




}
