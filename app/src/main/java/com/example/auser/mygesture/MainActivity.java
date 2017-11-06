package com.example.auser.mygesture;

import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView result,tv1;
    int toleftR,toleftG,toleftB;
    int[] meme = {R.drawable.meme01,
            R.drawable.meme02,
            R.drawable.meme03,
            R.drawable.meme04,
            R.drawable.meme05,
            R.drawable.meme06,
            R.drawable.meme07,
            R.drawable.meme08,
            R.drawable.meme09,
            R.drawable.meme10,
            R.drawable.meme11,
            R.drawable.meme12 };
    GestureOverlayView gestureOverlayView;
    GestureLibrary gestureLibrary;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = (TextView)findViewById(R.id.result);
        tv1 = (TextView)findViewById(R.id.textView01);
        img = (ImageView)findViewById(R.id.imageView01);
        gestureOverlayView = (GestureOverlayView)findViewById(R.id.goView);
        gestureOverlayView.addOnGesturePerformedListener(listener);
        findGestureLib();
    }

    void findGestureLib(){
        gestureLibrary = GestureLibraries.fromRawResource(this,R.raw.gestures);
        if(!gestureLibrary.load()){
            finish();
        }
    }

    GestureOverlayView.OnGesturePerformedListener listener = new GestureOverlayView.OnGesturePerformedListener(){
        @Override
        public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
            ArrayList<Prediction> list = gestureLibrary.recognize(gesture);
            if(list.size() > 0 && list.get(0).score > 5){   //score越高,要求精細度門檻越高
                String name = list.get(0).name;
                double score = list.get(0).score;
                result.setText("手勢名稱:" + name + ",分數=>" + score);
                if(name.equals("toLeft"))
                {
                    toleftR = (int)(Math.random()*255+1);
                    toleftG = (int)(Math.random()*255+1);
                    toleftB = (int)(Math.random()*255+1);
                    tv1.setText("=>[向左改變文字顏色]\n向右改變圖片\n順時針畫圈改變文字大小\nZ字恢復原設定");
                    tv1.setTextColor(Color.rgb(toleftR,toleftG,toleftB));
                    Log.d("Gesture to left","left");
                }else if(name.equals("toRight"))
                {
                    int memeCountsRandom = (int)(Math.random()*meme.length);
                    Log.d("memeCounts", "no." + memeCountsRandom);
                    img.setImageResource(meme[memeCountsRandom]);
                    tv1.setText("向左改變文字顏色\n=>[向右改變圖片]\n順時針畫圈改變文字大小\nZ字恢復原設定");
                }else if(name.equals("clock"))
                {
                    int zTextSize = (int)(Math.random()*25+5);
                    tv1.setText("向左改變文字顏色\n向右改變圖片\n=>[順時針畫圈改變文字大小]\nZ字恢復原設定");
                    tv1.setTextSize(zTextSize);
                }else if(name.equals("Z"))
                {
                    img.setImageResource(R.drawable.meme01);
                    tv1.setText("向左改變文字顏色\n向右改變圖片\n順時針畫圈改變文字大小\n=>[Z字恢復原設定]");
                    tv1.setTextSize(20);
                    tv1.setTextColor(0xff000000);
                }
            }
        }
    };
}
