package com.example.alice_wang.datacollect;

/**
 * Created by alice_wang on 16/8/10.
 */

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class DataCollectingVKboard extends Activity implements SensorEventListener{
    private SensorManager sensorManager;//Acceleration
    private SensorManager sensorManager2;//Gyroscope
    RelativeLayout relativeLayout;
    TextView tv;
    TextView username;
    TextView usercount;
    Button bco;
    Button bca;
    Button q;
    Button w;
    Button e;
    Button r;
    Button t;
    Button y;
    Button u;
    Button i;
    Button o;
    Button p;
    Button a;
    Button s;
    Button d;
    Button f;
    Button g;
    Button h;
    Button j;
    Button k;
    Button l;
    Button z;
    Button x;
    Button c;
    Button v;
    Button b;
    Button n;
    Button m;
    ArrayList tl = new ArrayList();//存放按键按下和抬起的时间
    ArrayList ac = new ArrayList();//存放按键按下和抬起的加速度
    ArrayList gy = new ArrayList();//存放按键按下和抬起的转速
    ArrayList accc = new ArrayList();
    ArrayList gyyy = new ArrayList();
    ArrayList everyac = new ArrayList();//存放每一个加速度
    ArrayList everygy = new ArrayList();//存放每一个转速
    ArrayList duringmaxac = new ArrayList();//存放按键按下到抬起的最大加速度
    ArrayList duringminac = new ArrayList();//存放按键按下到抬起的最小加速度
    ArrayList duringmaxgy = new ArrayList();//存放按键按下到抬起的最大转速
    ArrayList duringmingy = new ArrayList();//存放按键按下到抬起的最小转速
    ArrayList aveAcc = new ArrayList();//存放平均加速度
    ArrayList aveGyr = new ArrayList();//存放平均转速
    ArrayList prs = new ArrayList();//存放按下和抬起的触摸压力
    ArrayList size = new ArrayList();//存放按下和抬起的触摸面积
    boolean flag = false;
    int flagtest = 0;
    int count = 0;
    int count1 = 1;


    //获取按下时的时间
    public String getDateDown(){
        long time = System.currentTimeMillis();
        String date = "down:"+time;
        return date;
    }

    //获取抬起时的时间
    public String getDateUp(){

        long time = System.currentTimeMillis();
        String date = "up:"+time;
        return date;
    }

    //获得加速度计数据
    public String getAcceleration(SensorEvent event){
        String acc="Acceleration  X axis" + event.values[0] +
                "   Y axis" + event.values[1] +
                "   Z axis" + event.values[2];
        return acc;
    }

    //获得陀螺仪数据
    public String getGyroscope(SensorEvent event){
        String gyr="Gyroscope X axis" + event.values[0] +
                "   Y axis" + event.values[1] +
                "   Z axis" + event.values[2];
        return gyr;
    }

    //获得三轴的合加速度/转速
    public double getResultant(SensorEvent event){
        double value = Math.sqrt((event.values[0])*(event.values[0]) +
                (event.values[1])*(event.values[1]) +
                (event.values[2])*(event.values[2]));
        return value;
    }

    //获取每一个加速度、角速度
    public String arrayEvery(ArrayList sample){
        String everyone = "";
        for(int i=0; i<sample.size();i++){
            everyone = everyone + "Sample " +i +"  "+sample.get(i)+"    ";
        }
        return everyone;
    }


    //获取arraylist中最大数值(double)
    public double arrayMax(ArrayList sample){
        double maxvalue =(Double) sample.get(0);
        Log.i("sampleinfo","sample number is"+sample.size());
        for(int i=0; i<sample.size();i++){
            if((Double) sample.get(i) > maxvalue)
                maxvalue = (Double) sample.get(i);
        }
        return maxvalue;
    }

    //获取arraylist中最小数值(double)
    public double arrayMin(ArrayList sample){
        double minvalue =(Double) sample.get(0);
        for(int i=0; i<sample.size();i++){
            if((Double) sample.get(i) < minvalue)
                minvalue = (Double) sample.get(i);
        }
        return minvalue;
    }

    //获取平均值(double)
    public double averageValue(ArrayList sample){
        double sum=0;
        for(int i=0; i<sample.size();i++){
            sum = sum + (Double) sample.get(i);
        }
        return sum/sample.size();
    }

    public void test(ArrayList sample){
        Log.v("test","size="+sample.size());
        Log.v("value","value[0]="+sample.get(0));
        Log.v("value","value[1]="+sample.get(1));
        Log.v("value","value[2]="+sample.get(2));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_colletionvkboard);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager2 = (SensorManager) getSystemService(SENSOR_SERVICE);
        relativeLayout = (RelativeLayout)findViewById(R.id.relativeLayout1);
        tv = (TextView) findViewById(R.id.textv);
        bco = (Button) findViewById(R.id.btnconfirm);
        bca = (Button) findViewById(R.id.btncancel);
        q = (Button)findViewById(R.id.q);
        w = (Button)findViewById(R.id.w);
        e = (Button)findViewById(R.id.e);
        r = (Button)findViewById(R.id.r);
        t = (Button)findViewById(R.id.t);
        y = (Button)findViewById(R.id.y);
        u = (Button)findViewById(R.id.u);
        i = (Button)findViewById(R.id.i);
        o = (Button)findViewById(R.id.o);
        p = (Button)findViewById(R.id.p);
        a = (Button)findViewById(R.id.a);
        s = (Button)findViewById(R.id.s);
        d = (Button)findViewById(R.id.d);
        f = (Button)findViewById(R.id.f);
        g = (Button)findViewById(R.id.g);
        h = (Button)findViewById(R.id.h);
        j = (Button)findViewById(R.id.j);
        k = (Button)findViewById(R.id.k);
        l = (Button)findViewById(R.id.l);
        z = (Button)findViewById(R.id.z);
        x = (Button)findViewById(R.id.x);
        c = (Button)findViewById(R.id.c);
        v = (Button)findViewById(R.id.v);
        b = (Button)findViewById(R.id.b);
        n = (Button)findViewById(R.id.n);
        m = (Button)findViewById(R.id.m);

        username = (TextView)findViewById(R.id.username);
        usercount = (TextView)findViewById(R.id.usercount);


        username.setText("username: "+MainActivity.DataName);
        usercount.setText("usercount: "+count);



        q.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                tv.setText(tv.getText()+"q");
            }
        });

        q.setOnTouchListener(new View.OnTouchListener(){

            public boolean onTouch(View v, MotionEvent event){

                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        tl.add("Q " + getDateDown());
                        prs.add("Q Down " + event.getPressure());
                        size.add("Q Down " + event.getSize());
                        flagtest = 1;
                        flag = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        flag = true;
                        tl.add("Q " + getDateUp());
                        prs.add("Q Up " + event.getPressure());
                        size.add("Q Up " + event.getSize());
                        flagtest = 2;
                        everyac.add("Q "+arrayEvery(accc));
                        everygy.add("Q "+arrayEvery(gyyy));
                        duringmaxac.add("Q " + arrayMax(accc));
                        duringminac.add("Q " + arrayMin(accc));
                        duringmaxgy.add("Q " + arrayMax(gyyy));
                        duringmingy.add("Q " + arrayMin(gyyy));
                        aveAcc.add("Q " + averageValue(accc));
                        accc.clear();
                        aveGyr.add("Q " + averageValue(gyyy));
                        gyyy.clear();

                }
                return false;
            }
        });
        w.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(tv.getText() + "w");
            }
        });
        w.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        flag = true;
                        tl.add("W " + getDateDown());
                        prs.add("W Up " + event.getPressure());
                        size.add("W Up " + event.getSize());
                        flagtest = 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        flagtest = 2;
                        flag = true;
                        tl.add("W " + getDateUp());
                        prs.add("W Down " + event.getPressure());
                        size.add("W Down " + event.getSize());
                        everyac.add("W " + arrayEvery(accc));
                        everygy.add("W " + arrayEvery(gyyy));
                        duringmaxac.add("W " + arrayMax(accc));
                        duringminac.add("W " + arrayMin(accc));
                        duringmaxgy.add("W " + arrayMax(gyyy));
                        duringmingy.add("W " + arrayMin(gyyy));
                        aveAcc.add("W " + averageValue(accc));
                        accc.clear();
                        aveGyr.add("W " + averageValue(gyyy));
                        gyyy.clear();
                }
                return false;
            }
        });
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(tv.getText() + "e");
            }
        });
        e.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        tl.add("E " + getDateDown());
                        prs.add("E Down " + event.getPressure());
                        size.add("E Down " + event.getSize());
                        flag = true;
                        flagtest = 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        flagtest = 2;
                        flag = true;
                        tl.add("E " + getDateUp());
                        prs.add("E Up " + event.getPressure());
                        size.add("E Up " + event.getSize());
                        everyac.add("E " + arrayEvery(accc));
                        everygy.add("E " + arrayEvery(gyyy));
                        duringmaxac.add("E " + arrayMax(accc));
                        duringminac.add("E " + arrayMin(accc));
                        duringmaxgy.add("E " + arrayMax(gyyy));
                        duringmingy.add("E " + arrayMin(gyyy));
                        aveAcc.add("E " + averageValue(accc));
                        accc.clear();
                        aveGyr.add("E " + averageValue(gyyy));
                        gyyy.clear();
                }
                return false;
            }
        });
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(tv.getText() + "r");
            }
        });
        r.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        tl.add("R " + getDateDown());
                        prs.add("R Down " + event.getPressure());
                        size.add("R Down " + event.getSize());
                        flag = true;
                        flagtest = 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        flagtest = 2;
                        flag = true;
                        tl.add("R " + getDateUp());
                        prs.add("R Up " + event.getPressure());
                        size.add("R Up " + event.getSize());
                        everyac.add("R " + arrayEvery(accc));
                        everygy.add("R " + arrayEvery(gyyy));
                        duringmaxac.add("R " + arrayMax(accc));
                        duringminac.add("R " + arrayMin(accc));
                        duringmaxgy.add("R " + arrayMax(gyyy));
                        duringmingy.add("R " + arrayMin(gyyy));
                        aveAcc.add("R " + averageValue(accc));
                        accc.clear();
                        aveGyr.add("R " + averageValue(gyyy));
                        gyyy.clear();
                }
                return false;
            }
        });
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(tv.getText() + "t");
            }
        });
        t.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        tl.add("T " + getDateDown());
                        prs.add("T Down " + event.getPressure());
                        size.add("T Down " + event.getSize());
                        flag = true;
                        flagtest = 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        flagtest = 2;
                        flag = true;
                        tl.add("T " + getDateUp());
                        prs.add("T Up " + event.getPressure());
                        size.add("T Up " + event.getSize());
                        everyac.add("T " + arrayEvery(accc));
                        everygy.add("T " + arrayEvery(gyyy));
                        duringmaxac.add("T " + arrayMax(accc));
                        duringminac.add("T " + arrayMin(accc));
                        duringmaxgy.add("T " + arrayMax(gyyy));
                        duringmingy.add("T " + arrayMin(gyyy));
                        aveAcc.add("T " + averageValue(accc));
                        accc.clear();
                        aveGyr.add("T " + averageValue(gyyy));
                        gyyy.clear();
                }
                return false;
            }
        });
        y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(tv.getText() + "y");
            }
        });
        y.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        tl.add("Y " + getDateDown());
                        prs.add("Y Down " + event.getPressure());
                        size.add("Y Down " + event.getSize());
                        flag = true;
                        flagtest = 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        flagtest = 2;
                        flag = true;
                        tl.add("Y " + getDateUp());
                        prs.add("Y Up " + event.getPressure());
                        size.add("Y Up " + event.getSize());
                        everyac.add("Y " + arrayEvery(accc));
                        everygy.add("Y " + arrayEvery(gyyy));
                        duringmaxac.add("Y " + arrayMax(accc));
                        duringminac.add("Y " + arrayMin(accc));
                        duringmaxgy.add("Y " + arrayMax(gyyy));
                        duringmingy.add("Y " + arrayMin(gyyy));
                        aveAcc.add("Y " + averageValue(accc));
                        accc.clear();
                        aveGyr.add("Y " + averageValue(gyyy));
                        gyyy.clear();
                }
                return false;
            }
        });
        u.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(tv.getText() + "u");
            }
        });
        u.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        tl.add("U " + getDateDown());
                        prs.add("U Down " + event.getPressure());
                        size.add("U Down " + event.getSize());
                        flag = true;
                        flagtest = 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        flagtest = 2;
                        flag = true;
                        tl.add("U " + getDateUp());
                        prs.add("U Up " + event.getPressure());
                        size.add("U Up " + event.getSize());
                        everyac.add("U " + arrayEvery(accc));
                        everygy.add("U " + arrayEvery(gyyy));
                        duringmaxac.add("U " + arrayMax(accc));
                        duringminac.add("U " + arrayMin(accc));
                        duringmaxgy.add("U " + arrayMax(gyyy));
                        duringmingy.add("U " + arrayMin(gyyy));
                        aveAcc.add("U " + averageValue(accc));
                        accc.clear();
                        aveGyr.add("U " + averageValue(gyyy));
                        gyyy.clear();
                }
                return false;
            }
        });
        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(tv.getText() + "i");
            }
        });
        i.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        tl.add("I " + getDateDown());
                        prs.add("I Down " + event.getPressure());
                        size.add("I Down " + event.getSize());
                        flag = true;
                        flagtest = 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        flagtest = 2;
                        flag = true;
                        tl.add("I " + getDateUp());
                        prs.add("I Up " + event.getPressure());
                        size.add("I Up " + event.getSize());
                        everyac.add("I " + arrayEvery(accc));
                        everygy.add("I " + arrayEvery(gyyy));
                        duringmaxac.add("I " + arrayMax(accc));
                        duringminac.add("I " + arrayMin(accc));
                        duringmaxgy.add("I " + arrayMax(gyyy));
                        duringmingy.add("I " + arrayMin(gyyy));
                        aveAcc.add("I " + averageValue(accc));
                        accc.clear();
                        aveGyr.add("I " + averageValue(gyyy));
                        gyyy.clear();
                }
                return false;
            }
        });
        o.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(tv.getText() + "o");
            }
        });
        o.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        tl.add("O " + getDateDown());
                        prs.add("O Down " + event.getPressure());
                        size.add("O Down " + event.getSize());
                        flag = true;
                        flagtest = 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        flagtest = 2;
                        flag = true;
                        tl.add("O " + getDateUp());
                        prs.add("O Up " + event.getPressure());
                        size.add("O Up " + event.getSize());
                        everyac.add("O " + arrayEvery(accc));
                        everygy.add("O " + arrayEvery(gyyy));
                        duringmaxac.add("O " + arrayMax(accc));
                        duringminac.add("O " + arrayMin(accc));
                        duringmaxgy.add("O " + arrayMax(gyyy));
                        duringmingy.add("O " + arrayMin(gyyy));
                        aveAcc.add("O " + averageValue(accc));
                        accc.clear();
                        aveGyr.add("O " + averageValue(gyyy));
                        gyyy.clear();
                }
                return false;
            }
        });
        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(tv.getText() + "p");
            }
        });
        p.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        tl.add("P " + getDateDown());
                        prs.add("P Down " + event.getPressure());
                        size.add("P Down " + event.getSize());
                        flag = true;
                        flagtest = 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        flagtest = 2;
                        flag = true;
                        tl.add("P " + getDateUp());
                        prs.add("P Up " + event.getPressure());
                        size.add("P Up " + event.getSize());
                        everyac.add("P " + arrayEvery(accc));
                        everygy.add("P " + arrayEvery(gyyy));
                        duringmaxac.add("P " + arrayMax(accc));
                        duringminac.add("P " + arrayMin(accc));
                        duringmaxgy.add("P " + arrayMax(gyyy));
                        duringmingy.add("P " + arrayMin(gyyy));
                        aveAcc.add("P " + averageValue(accc));
                        accc.clear();
                        aveGyr.add("P " + averageValue(gyyy));
                        gyyy.clear();
                }
                return false;
            }
        });
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(tv.getText() + "a");
            }
        });
        a.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        tl.add("A " + getDateDown());
                        prs.add("A Down " + event.getPressure());
                        size.add("A Down " + event.getSize());
                        flag = true;
                        flagtest = 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        flagtest = 2;
                        flag = true;
                        tl.add("A " + getDateUp());
                        prs.add("A Up " + event.getPressure());
                        size.add("A Up " + event.getSize());
                        everyac.add("A " + arrayEvery(accc));
                        everygy.add("A " + arrayEvery(gyyy));
                        duringmaxac.add("A " + arrayMax(accc));
                        duringminac.add("A " + arrayMin(accc));
                        duringmaxgy.add("A " + arrayMax(gyyy));
                        duringmingy.add("A " + arrayMin(gyyy));
                        aveAcc.add("A " + averageValue(accc));
                        accc.clear();
                        aveGyr.add("A " + averageValue(gyyy));
                        gyyy.clear();
                }
                return false;
            }
        });
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(tv.getText() + "s");
            }
        });
        s.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        tl.add("S " + getDateDown());
                        prs.add("S Down " + event.getPressure());
                        size.add("S Down " + event.getSize());
                        flag = true;
                        flagtest = 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        flagtest = 2;
                        flag = true;
                        tl.add("S " + getDateUp());
                        prs.add("S Up " + event.getPressure());
                        size.add("S Up " + event.getSize());
                        everyac.add("S " + arrayEvery(accc));
                        everygy.add("S " + arrayEvery(gyyy));
                        duringmaxac.add("S " + arrayMax(accc));
                        duringminac.add("S " + arrayMin(accc));
                        duringmaxgy.add("S " + arrayMax(gyyy));
                        duringmingy.add("S " + arrayMin(gyyy));
                        aveAcc.add("S " + averageValue(accc));
                        accc.clear();
                        aveGyr.add("S " + averageValue(gyyy));
                        gyyy.clear();
                }
                return false;
            }
        });
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(tv.getText() + "d");
            }
        });
        d.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        tl.add("D " + getDateDown());
                        prs.add("D Down " + event.getPressure());
                        size.add("D Down " + event.getSize());
                        flag = true;
                        flagtest = 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        flagtest = 2;
                        flag = true;
                        tl.add("D " + getDateUp());
                        prs.add("D Up " + event.getPressure());
                        size.add("D Up " + event.getSize());
                        everyac.add("D " + arrayEvery(accc));
                        everygy.add("D " + arrayEvery(gyyy));
                        duringmaxac.add("D " + arrayMax(accc));
                        duringminac.add("D " + arrayMin(accc));
                        duringmaxgy.add("D " + arrayMax(gyyy));
                        duringmingy.add("D " + arrayMin(gyyy));
                        aveAcc.add("D " + averageValue(accc));
                        accc.clear();
                        aveGyr.add("D " + averageValue(gyyy));
                        gyyy.clear();
                }
                return false;
            }
        });
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(tv.getText() + "f");
            }
        });
        f.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event){
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        tl.add("F " + getDateDown());
                        prs.add("F Down " + event.getPressure());
                        size.add("F Down " + event.getSize());
                        flag = true;
                        flagtest = 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        flagtest = 2;
                        flag = true;
                        tl.add("F " + getDateUp());
                        prs.add("F Up " + event.getPressure());
                        size.add("F Up " + event.getSize());
                        everyac.add("F "+arrayEvery(accc));
                        everygy.add("F "+arrayEvery(gyyy));
                        duringmaxac.add("F " + arrayMax(accc));
                        duringminac.add("F " + arrayMin(accc));
                        duringmaxgy.add("F " + arrayMax(gyyy));
                        duringmingy.add("F " + arrayMin(gyyy));
                        aveAcc.add("F " + averageValue(accc));
                        accc.clear();
                        aveGyr.add("F " + averageValue(gyyy));
                        gyyy.clear();
                }
                return false;
            }
        });
        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(tv.getText() + "g");
            }
        });
        g.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        tl.add("G " + getDateDown());
                        prs.add("G Down " + event.getPressure());
                        size.add("G Down " + event.getSize());
                        flag = true;
                        flagtest = 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        flagtest = 2;
                        flag = true;
                        tl.add("G " + getDateUp());
                        prs.add("G Up " + event.getPressure());
                        size.add("G Up " + event.getSize());
                        everyac.add("G " + arrayEvery(accc));
                        everygy.add("G " + arrayEvery(gyyy));
                        duringmaxac.add("G " + arrayMax(accc));
                        duringminac.add("G " + arrayMin(accc));
                        duringmaxgy.add("G " + arrayMax(gyyy));
                        duringmingy.add("G " + arrayMin(gyyy));
                        aveAcc.add("G " + averageValue(accc));
                        accc.clear();
                        aveGyr.add("G " + averageValue(gyyy));
                        gyyy.clear();
                }
                return false;
            }
        });
        h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(tv.getText() + "h");
            }
        });
        h.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        tl.add("H " + getDateDown());
                        prs.add("H Down " + event.getPressure());
                        size.add("H Down " + event.getSize());
                        flag = true;
                        flagtest = 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        flagtest = 2;
                        flag = true;
                        tl.add("H " + getDateUp());
                        prs.add("H Up " + event.getPressure());
                        size.add("H Up " + event.getSize());
                        everyac.add("H " + arrayEvery(accc));
                        everygy.add("H " + arrayEvery(gyyy));
                        duringmaxac.add("H " + arrayMax(accc));
                        duringminac.add("H " + arrayMin(accc));
                        duringmaxgy.add("H " + arrayMax(gyyy));
                        duringmingy.add("H " + arrayMin(gyyy));
                        aveAcc.add("H " + averageValue(accc));
                        accc.clear();
                        aveGyr.add("H " + averageValue(gyyy));
                        gyyy.clear();
                }
                return false;
            }
        });
        j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(tv.getText() + "j");
            }
        });
        j.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        tl.add("J " + getDateDown());
                        prs.add("J Down " + event.getPressure());
                        size.add("J Down " + event.getSize());
                        flag = true;
                        flagtest = 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        flagtest = 2;
                        flag = true;
                        tl.add("J " + getDateUp());
                        prs.add("J Up " + event.getPressure());
                        size.add("J Up " + event.getSize());
                        everyac.add("J " + arrayEvery(accc));
                        everygy.add("J " + arrayEvery(gyyy));
                        duringmaxac.add("J " + arrayMax(accc));
                        duringminac.add("J " + arrayMin(accc));
                        duringmaxgy.add("J " + arrayMax(gyyy));
                        duringmingy.add("J " + arrayMin(gyyy));
                        aveAcc.add("J " + averageValue(accc));
                        accc.clear();
                        aveGyr.add("J " + averageValue(gyyy));
                        gyyy.clear();
                }
                return false;
            }
        });
        k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(tv.getText() + "k");
            }
        });
        k.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        tl.add("K " + getDateDown());
                        prs.add("K Down " + event.getPressure());
                        size.add("K Down " + event.getSize());
                        flag = true;
                        flagtest = 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        flagtest = 2;
                        flag = true;
                        tl.add("K " + getDateUp());
                        prs.add("K Up " + event.getPressure());
                        size.add("K Up " + event.getSize());
                        everyac.add("K " + arrayEvery(accc));
                        everygy.add("K " + arrayEvery(gyyy));
                        duringmaxac.add("K " + arrayMax(accc));
                        duringminac.add("K " + arrayMin(accc));
                        duringmaxgy.add("K " + arrayMax(gyyy));
                        duringmingy.add("K " + arrayMin(gyyy));
                        aveAcc.add("K " + averageValue(accc));
                        accc.clear();
                        aveGyr.add("K " + averageValue(gyyy));
                        gyyy.clear();
                }
                return false;
            }
        });
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(tv.getText() + "l");
            }
        });
        l.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        tl.add("L " + getDateDown());
                        prs.add("L Down " + event.getPressure());
                        size.add("L Down " + event.getSize());
                        flag = true;
                        flagtest = 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        flagtest = 2;
                        flag = true;
                        tl.add("L " + getDateUp());
                        prs.add("L Up " + event.getPressure());
                        size.add("L Up " + event.getSize());
                        everyac.add("L " + arrayEvery(accc));
                        everygy.add("L " + arrayEvery(gyyy));
                        duringmaxac.add("L " + arrayMax(accc));
                        duringminac.add("L " + arrayMin(accc));
                        duringmaxgy.add("L " + arrayMax(gyyy));
                        duringmingy.add("L " + arrayMin(gyyy));
                        aveAcc.add("L " + averageValue(accc));
                        accc.clear();
                        aveGyr.add("L " + averageValue(gyyy));
                        gyyy.clear();
                }
                return false;
            }
        });
        z.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(tv.getText() + "z");
            }
        });
        z.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        tl.add("Z " + getDateDown());
                        prs.add("Z Down " + event.getPressure());
                        size.add("Z Down " + event.getSize());
                        flag = true;
                        flagtest = 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        flagtest = 2;
                        flag = true;
                        tl.add("Z " + getDateUp());
                        prs.add("Z Up " + event.getPressure());
                        size.add("Z Up " + event.getSize());
                        everyac.add("Z " + arrayEvery(accc));
                        everygy.add("Z " + arrayEvery(gyyy));
                        duringmaxac.add("Z " + arrayMax(accc));
                        duringminac.add("Z " + arrayMin(accc));
                        duringmaxgy.add("Z " + arrayMax(gyyy));
                        duringmingy.add("Z " + arrayMin(gyyy));
                        aveAcc.add("Z " + averageValue(accc));
                        accc.clear();
                        aveGyr.add("Z " + averageValue(gyyy));
                        gyyy.clear();
                }
                return false;
            }
        });
        x.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                tv.setText(tv.getText()+"x");
            }
        });
        x.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event){
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        tl.add("X " + getDateDown());
                        prs.add("X Down " + event.getPressure());
                        size.add("X Down " + event.getSize());
                        flag = true;
                        flagtest = 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        flagtest = 2;
                        flag = true;
                        tl.add("X " + getDateUp());
                        prs.add("X Up " + event.getPressure());
                        size.add("X Up " + event.getSize());
                        everyac.add("X "+arrayEvery(accc));
                        everygy.add("X "+arrayEvery(gyyy));
                        duringmaxac.add("X " + arrayMax(accc));
                        duringminac.add("X " + arrayMin(accc));
                        duringmaxgy.add("X " + arrayMax(gyyy));
                        duringmingy.add("X " + arrayMin(gyyy));
                        aveAcc.add("X " + averageValue(accc));
                        accc.clear();
                        aveGyr.add("X " + averageValue(gyyy));
                        gyyy.clear();
                }
                return false;
            }
        });
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(tv.getText() + "c");
            }
        });
        c.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        tl.add("C " + getDateDown());
                        prs.add("C Down " + event.getPressure());
                        size.add("C Down " + event.getSize());
                        flag = true;
                        flagtest = 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        flagtest = 2;
                        flag = true;
                        tl.add("C " + getDateUp());
                        prs.add("C Up " + event.getPressure());
                        size.add("C Up " + event.getSize());
                        everyac.add("C " + arrayEvery(accc));
                        everygy.add("C " + arrayEvery(gyyy));
                        duringmaxac.add("C " + arrayMax(accc));
                        duringminac.add("C " + arrayMin(accc));
                        duringmaxgy.add("C " + arrayMax(gyyy));
                        duringmingy.add("C " + arrayMin(gyyy));
                        aveAcc.add("C " + averageValue(accc));
                        accc.clear();
                        aveGyr.add("C " + averageValue(gyyy));
                        gyyy.clear();
                }
                return false;
            }
        });
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(tv.getText() + "v");
            }
        });
        v.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        tl.add("V " + getDateDown());
                        prs.add("V Down " + event.getPressure());
                        size.add("V Down " + event.getSize());
                        flag = true;
                        flagtest = 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        flagtest = 2;
                        flag = true;
                        tl.add("V " + getDateUp());
                        prs.add("V Up " + event.getPressure());
                        size.add("V Up " + event.getSize());
                        everyac.add("V " + arrayEvery(accc));
                        everygy.add("V " + arrayEvery(gyyy));
                        duringmaxac.add("V " + arrayMax(accc));
                        duringminac.add("V " + arrayMin(accc));
                        duringmaxgy.add("V " + arrayMax(gyyy));
                        duringmingy.add("V " + arrayMin(gyyy));
                        aveAcc.add("V " + averageValue(accc));
                        accc.clear();
                        aveGyr.add("V " + averageValue(gyyy));
                        gyyy.clear();
                }
                return false;
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(tv.getText() + "b");
            }
        });
        b.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        tl.add("B " + getDateDown());
                        prs.add("B Down " + event.getPressure());
                        size.add("B Down " + event.getSize());
                        flag = true;
                        flagtest = 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        flagtest = 2;
                        flag = true;
                        tl.add("B " + getDateUp());
                        prs.add("B Up " + event.getPressure());
                        size.add("B Up " + event.getSize());
                        everyac.add("B " + arrayEvery(accc));
                        everygy.add("B " + arrayEvery(gyyy));
                        duringmaxac.add("B " + arrayMax(accc));
                        duringminac.add("B " + arrayMin(accc));
                        duringmaxgy.add("B " + arrayMax(gyyy));
                        duringmingy.add("B " + arrayMin(gyyy));
                        aveAcc.add("B " + averageValue(accc));
                        accc.clear();
                        aveGyr.add("B " + averageValue(gyyy));
                        gyyy.clear();
                }
                return false;
            }
        });
        n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(tv.getText() + "n");
            }
        });
        n.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        tl.add("N " + getDateDown());
                        prs.add("N Down " + event.getPressure());
                        size.add("N Down " + event.getSize());
                        flag = true;
                        flagtest = 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        flagtest = 2;
                        flag = true;
                        tl.add("N " + getDateUp());
                        prs.add("N Up " + event.getPressure());
                        size.add("N Up " + event.getSize());
                        everyac.add("N " + arrayEvery(accc));
                        everygy.add("N " + arrayEvery(gyyy));
                        duringmaxac.add("N " + arrayMax(accc));
                        duringminac.add("N " + arrayMin(accc));
                        duringmaxgy.add("N " + arrayMax(gyyy));
                        duringmingy.add("N " + arrayMin(gyyy));
                        aveAcc.add("N " + averageValue(accc));
                        accc.clear();
                        aveGyr.add("N " + averageValue(gyyy));
                        gyyy.clear();
                }
                return false;
            }
        });
        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(tv.getText() + "m");
            }
        });
        m.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        tl.add("M " + getDateDown());
                        prs.add("M Down " + event.getPressure());
                        size.add("M Down " + event.getSize());
                        flag = true;
                        flagtest = 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        flagtest = 2;
                        flag = true;
                        tl.add("M " + getDateUp());
                        prs.add("M Up " + event.getPressure());
                        size.add("M Up " + event.getSize());
                        everyac.add("M " + arrayEvery(accc));
                        everygy.add("M " + arrayEvery(gyyy));
                        duringmaxac.add("M " + arrayMax(accc));
                        duringminac.add("M " + arrayMin(accc));
                        duringmaxgy.add("M " + arrayMax(gyyy));
                        duringmingy.add("M " + arrayMin(gyyy));
                        aveAcc.add("M " + averageValue(accc));
                        accc.clear();
                        aveGyr.add("M " + averageValue(gyyy));
                        gyyy.clear();
                }
                return false;
            }
        });
        bca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("");

                tl.clear();
                ac.clear();
                gy.clear();
                everyac.clear();
                everygy.clear();
                prs.clear();
                size.clear();
                duringmaxac.clear();
                duringmaxgy.clear();
                duringminac.clear();
                duringmingy.clear();
                aveAcc.clear();
                aveGyr.clear();
            }
        });



        bco.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub
                SQLiteDatabase db;


                //open or create test.db
                    count++;
                    if(SwitchActivity.step == 1){
                        db = openOrCreateDatabase("traindc_vk.db", Context.MODE_PRIVATE, null);
                    }else{
                        db = openOrCreateDatabase("testdc_vk.db", Context.MODE_PRIVATE, null);
                    }

                db.execSQL("CREATE TABLE IF NOT EXISTS personsvk (_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR," +
                        "password VARCHAR, sex VARCHAR, age VARCHAR, count VARCHAR, time TEXT, acceleration TEXT," +
                        " gyroscope TEXT, everyacceleration TEXT, everygyroscope TEXT, maxAcceleration TEXT, minAcceleration TEXT, maxGyroscope TEXT, minGyroscope TEXT, " +
                        "averageAcc TEXT, averageGyr TEXT, pressure VARCHAR, size VARCHAR)");
                Data person = new Data(MainActivity.DataName, MainActivity.DataPsw);
                    person.putInData(MainActivity.sex, MainActivity.age, count1, tl, ac, gy, everyac,everygy,duringmaxac, duringminac, duringmaxgy, duringmingy, aveAcc, aveGyr, prs, size);

                ContentValues cv = new ContentValues();

                Log.v("tl",""+tl.size());
                Log.v("ac",""+ac.size());
                Log.v("gy",""+gy.size());
                Log.v("duringmaxac",""+duringmaxac.size());
                Log.v("duringminac",""+duringminac.size());
                Log.v("duringmaxgy",""+duringmaxgy.size());
                Log.v("duringmingy",""+duringmingy.size());
                Log.v("averageacc",""+aveAcc.size());
                Log.v("averagegyy",""+aveGyr.size());
                Log.v("1",""+MainActivity.sex);
                Log.v("2",""+MainActivity.age);

                for(int k = 0;k < person.time.size(); k++){
                    cv.put("name", person.name);
                    cv.put("password", person.password);
                    cv.put("sex", person.sex);
                    cv.put("age", person.age);
                    cv.put("count", person.lcount);
                    cv.put("time", (String)person.time.get(k));
                    try {
                        cv.put("acceleration", (String) person.acceleration.get(k));
                    }catch(Exception e){
                        cv.put("acceleration", "");
                    }
                    try {
                        cv.put("gyroscope", (String) person.gyroscope.get(k));
                    }catch(Exception e) {
                        cv.put("gyroscope","");
                    }
                    cv.put("everyacceleration",(String)person.everyacceleration.get(k/2));
                    cv.put("everygyroscope",(String)person.everygyroscope.get(k/2));
                    cv.put("maxAcceleration",(String)person.maxacceleration.get(k/2));
                    cv.put("minAcceleration",(String)person.minacceleration.get(k/2));
                    cv.put("maxGyroscope",(String)person.maxgyroscope.get(k/2));
                    cv.put("minGyroscope",(String)person.mingyroscope.get(k/2));
                    cv.put("averageAcc",(String)person.averageacc.get(k/2));
                    cv.put("averageGyr",(String)person.averagegyr.get(k/2));
                    cv.put("pressure", (String) person.pressure.get(k));
                    cv.put("size", (String) person.size.get(k));
                    //insert
                    db.insert("personsvk", null, cv);
                }
                    count1++;

                db.close();

                tv.setText("");
                usercount.setText("usercount: "+count);


            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager2.registerListener(this,
                sensorManager2.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        sensorManager2.unregisterListener(this);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        switch (event.sensor.getType()){
            case Sensor.TYPE_ACCELEROMETER:
                if(flag){
                    //按下
                    if(flagtest == 1){
                        ac.add(getAcceleration(event));
                    }
                    //抬起
                    if(flagtest == 2){
                        ac.add(getAcceleration(event));
                    }
                }
                if(flagtest == 1){
                    accc.add(getResultant(event));
                }

                break;
            case Sensor.TYPE_GYROSCOPE:
                if(flag){
                    //按下
                    if(flagtest == 1) {
                        gy.add(getGyroscope(event));
                    }
                    //抬起
                    if(flagtest == 2){
                        gy.add(getGyroscope(event));
                    }
                }
                if(flagtest == 1){
                    gyyy.add(getResultant(event));
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                flag = false;
                break;
            default:
                break;
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}