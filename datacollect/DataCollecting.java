package com.example.alice_wang.datacollect;

/**
 * Created by alice_wang on 16/8/10.
 */
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;


public class DataCollecting extends Activity implements SensorEventListener{
    private SensorManager sensorManager;//Acceleration
    private SensorManager sensorManager2;//Gyroscope
    RelativeLayout relativeLayout;
    TextView tv;
    TextView username;
    TextView usercount;
    Button bco;
    Button bca;
    Button one;
    Button two;
    Button three;
    Button four;
    Button five;
    Button six;
    Button seven;
    Button eight;
    Button nine;
    Button zero;
    Button del;
    Button enter;
    ArrayList tl = new ArrayList();// to get the time at touchdown and touchup
    ArrayList ac = new ArrayList();// to get the acceleration at touchdown and touchup
    ArrayList gy = new ArrayList();// to get the gyroscope at touchdown and touchup
    ArrayList accc = new ArrayList();// to store the result of X,Y,Z's acceleration
    ArrayList acccc = new ArrayList();// to store every acceleration
    ArrayList gyyy = new ArrayList();// to store the result of X,Y,Z's gyroscope
    ArrayList gyyyy = new ArrayList();// to store every gyroscope
    ArrayList everyac = new ArrayList();// to formally store every acceleration
    ArrayList everygy = new ArrayList();// to formally store every gyroscope
    ArrayList duringmaxac = new ArrayList();//to store the max acceleration
    ArrayList duringminac = new ArrayList();//to store the min acceleration
    ArrayList duringmaxgy = new ArrayList();//to store the max gyroscope
    ArrayList duringmingy = new ArrayList();//to store the min gyroscope
    ArrayList aveAcc = new ArrayList();//to store the average acceleration
    ArrayList aveGyr = new ArrayList();//to store the average gyroscope
    ArrayList prs = new ArrayList();//to get the pressure at touchdown and touchup
    ArrayList size = new ArrayList();//to get the size at touchdown and touchup
    boolean flag = false;
    int flagtest = 0;
    int count = 0;
    int count1 = 1;

    //to get time down
    public String getDateDown(){
        //get the current time with ms
        long time = System.currentTimeMillis();
        String date = "down:"+time;
        return date;
    }

    //to get time up
    public String getDateUp(){

        long time = System.currentTimeMillis();
        String date = "up:"+time;
        return date;
    }

    //to get accelertion data in three axis
    public String getAcceleration(SensorEvent event){
        String acc="Acceleration  X axis" + event.values[0] +
                "   Y axis" + event.values[1] +
                "   Z axis" + event.values[2];
        Log.i("yqq","yqq----"+acc);
        return acc;
    }

    //to get gyroscope data in three axis
    public String getGyroscope(SensorEvent event){
        String gyr="Gyroscope X axis" + event.values[0] +
                "   Y axis" + event.values[1] +
                "   Z axis" + event.values[2];
        Log.i("yqq","yqq----"+gyr);
        return gyr;
    }


    //to get the resultant from three axis
    public double getResultant(SensorEvent event){
        double value = Math.sqrt((event.values[0])*(event.values[0]) +
                (event.values[1])*(event.values[1]) +
                (event.values[2])*(event.values[2]));
        return value;
    }

    //to get every acceleration
    public String getEveryAcc(SensorEvent event){
        double value = Math.sqrt((event.values[0])*(event.values[0]) +
                (event.values[1])*(event.values[1]) +
                (event.values[2])*(event.values[2]));
        String acc="Acceleration  X axis" + event.values[0] +
                "   Y axis" + event.values[1] +
                "   Z axis" + event.values[2] +"  Resultant"+value;
        Log.i("wyh","acc "+acc);

        return acc;
    }
    //to get every gyroscope
    public String getEveryGyy(SensorEvent event){
        double value = Math.sqrt((event.values[0])*(event.values[0]) +
                (event.values[1])*(event.values[1]) +
                (event.values[2])*(event.values[2]));
        String gyr="Gyroscope X axis" + event.values[0] +
                "   Y axis" + event.values[1] +
                "   Z axis" + event.values[2] +"  Resultant"+value;
        Log.i("wyh","gyr "+gyr);

        return gyr;
    }

    //record every sample
    public String arrayEvery(ArrayList sample){
        String everyone = "";
        for(int i=0; i<sample.size();i++){
            everyone = everyone + "Sample " +i +"  "+sample.get(i)+"    ";
        }
        return everyone;
    }

    //to get the max valve
    public double arrayMax(ArrayList sample){
        if(!sample.isEmpty()){
        double maxvalue =(Double) sample.get(0);
        for(int i=0; i<sample.size();i++){
            if((Double) sample.get(i) > maxvalue)
                maxvalue = (Double) sample.get(i);
        }
        return maxvalue;}
        else {
            return 0;
        }
    }

    //to get the min valve
    public double arrayMin(ArrayList sample){
        if(!sample.isEmpty()){
        double minvalue =(Double) sample.get(0);
        for(int i=0; i<sample.size();i++){
            if((Double) sample.get(i) < minvalue)
                minvalue = (Double) sample.get(i);
        }
        return minvalue;
        }
        else {
            return 0;
        }
    }

    //to get the average value
    public double averageValue(ArrayList sample){
        double sum=0;
        if(!sample.isEmpty()){
        for(int i=0; i<sample.size();i++){
            sum = sum + (Double) sample.get(i);
        }
        return sum/sample.size();
        }
        else {
            return 0;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_colletion);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager2 = (SensorManager) getSystemService(SENSOR_SERVICE);
        relativeLayout = (RelativeLayout)findViewById(R.id.relativeLayout1);
        tv = (TextView) findViewById(R.id.textv);
        bco = (Button) findViewById(R.id.btnconfirm);
        bca = (Button) findViewById(R.id.btncancel);
        one = (Button) findViewById(R.id.one);
        two = (Button) findViewById(R.id.two);
        three = (Button) findViewById(R.id.three);
        four = (Button) findViewById(R.id.four);
        five = (Button) findViewById(R.id.five);
        six = (Button) findViewById(R.id.six);
        seven = (Button) findViewById(R.id.seven);
        eight = (Button) findViewById(R.id.eight);
        nine = (Button) findViewById(R.id.nine);
        zero = (Button) findViewById(R.id.zero);
        del = (Button) findViewById(R.id.delete) ;
        enter = (Button) findViewById(R.id.confirm);
        username = (TextView)findViewById(R.id.username);
        usercount = (TextView)findViewById(R.id.usercount);


        username.setText("username: "+MainActivity.DataName);
        usercount.setText("usercount: "+count);



        one.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                tv.setText(tv.getText()+"1");
            }
        });

        one.setOnTouchListener(new View.OnTouchListener(){

            public boolean onTouch(View v, MotionEvent event){

                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        tl.add("Num1 " + getDateDown());
                        prs.add("Num1 Down " + event.getPressure());
                        size.add("Num1 Down " + event.getSize());
                        flagtest = 1;
                        flag = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        flag = true;
                        tl.add("Num1 " + getDateUp());
                        prs.add("Num1 Up " + event.getPressure());
                        size.add("Num1 Up " + event.getSize());
                        flagtest = 2;
                        everyac.add("Num1 "+arrayEvery(acccc));
                        everygy.add("Num1 "+arrayEvery(gyyyy));
                        duringmaxac.add("Num1 " + arrayMax(accc));
                        duringminac.add("Num1 " + arrayMin(accc));
                        duringmaxgy.add("Num1 " + arrayMax(gyyy));
                        duringmingy.add("Num1 " + arrayMin(gyyy));
                        aveAcc.add("Num1 " + averageValue(accc));
                        accc.clear();
                        acccc.clear();
                        aveGyr.add("Num1 " + averageValue(gyyy));
                        gyyy.clear();
                        gyyyy.clear();

                }
                return false;
            }
        });
        two.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                tv.setText(tv.getText()+"2");
            }
        });
        two.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event){
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        flag = true;
                        tl.add("Num2 " + getDateDown());
                        prs.add("Num2 Down " + event.getPressure());
                        size.add("Num2 Down " + event.getSize());
                        flagtest = 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        flagtest = 2;
                        flag = true;
                        tl.add("Num2 " + getDateUp());
                        prs.add("Num2 Up " + event.getPressure());
                        size.add("Num2 Up " + event.getSize());
                        everyac.add("Num2 " + arrayEvery(acccc));
                        everygy.add("Num2 "+arrayEvery(gyyyy));
                        duringmaxac.add("Num2 " + arrayMax(accc));
                        duringminac.add("Num2 " + arrayMin(accc));
                        duringmaxgy.add("Num2 " + arrayMax(gyyy));
                        duringmingy.add("Num2 " + arrayMin(gyyy));
                        aveAcc.add("Num2 " + averageValue(accc));
                        accc.clear();
                        acccc.clear();
                        aveGyr.add("Num2 " + averageValue(gyyy));
                        gyyy.clear();
                        gyyyy.clear();
                }
                return false;
            }
        });
        three.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                tv.setText(tv.getText()+"3");
            }
        });
        three.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event){
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        tl.add("Num3 " + getDateDown());
                        prs.add("Num3 Down " + event.getPressure());
                        size.add("Num3 Down " + event.getSize());
                        flag = true;
                        flagtest = 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        flagtest = 2;
                        flag = true;
                        tl.add("Num3 " + getDateUp());
                        prs.add("Num3 Up " + event.getPressure());
                        size.add("Num3 Up " + event.getSize());
                        everyac.add("Num3 " + arrayEvery(acccc));
                        everygy.add("Num3 "+arrayEvery(gyyyy));
                        duringmaxac.add("Num3 " + arrayMax(accc));
                        duringminac.add("Num3 " + arrayMin(accc));
                        duringmaxgy.add("Num3 " + arrayMax(gyyy));
                        duringmingy.add("Num3 " + arrayMin(gyyy));
                        aveAcc.add("Num3 " + averageValue(accc));
                        accc.clear();
                        acccc.clear();
                        aveGyr.add("Num3 " + averageValue(gyyy));
                        gyyy.clear();
                        gyyyy.clear();
                }
                return false;
            }
        });
        four.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                tv.setText(tv.getText()+"4");
            }
        });
        four.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event){
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        tl.add("Num4 " + getDateDown());
                        prs.add("Num4 Down " + event.getPressure());
                        size.add("Num4 Down " + event.getSize());
                        flag = true;
                        flagtest = 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        flagtest = 2;
                        flag = true;
                        tl.add("Num4 " + getDateUp());
                        prs.add("Num4 Up " + event.getPressure());
                        size.add("Num4 Up " + event.getSize());
                        everyac.add("Num4 " + arrayEvery(acccc));
                        everygy.add("Num4 "+ arrayEvery(gyyyy));
                        duringmaxac.add("Num4 " + arrayMax(accc));
                        duringminac.add("Num4 " + arrayMin(accc));
                        duringmaxgy.add("Num4 " + arrayMax(gyyy));
                        duringmingy.add("Num4 " + arrayMin(gyyy));
                        aveAcc.add("Num4 " + averageValue(accc));
                        accc.clear();
                        acccc.clear();
                        aveGyr.add("Num4 " + averageValue(gyyy));
                        gyyy.clear();
                        gyyyy.clear();
                }
                return false;
            }
        });
        five.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                tv.setText(tv.getText()+"5");
            }
        });
        five.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event){
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        tl.add("Num5 " + getDateDown());
                        prs.add("Num5 Down " + event.getPressure());
                        size.add("Num5 Down " + event.getSize());
                        flag = true;
                        flagtest = 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        flagtest = 2;
                        flag = true;
                        tl.add("Num5 " + getDateUp());
                        prs.add("Num5 Up " + event.getPressure());
                        size.add("Num5 Up " + event.getSize());
                        everyac.add("Num5 " + arrayEvery(acccc));
                        everygy.add("Num5 "+ arrayEvery(gyyyy));
                        duringmaxac.add("Num5 " + arrayMax(accc));
                        duringminac.add("Num5 " + arrayMin(accc));
                        duringmaxgy.add("Num5 " + arrayMax(gyyy));
                        duringmingy.add("Num5 " + arrayMin(gyyy));
                        aveAcc.add("Num5 " + averageValue(accc));
                        accc.clear();
                        acccc.clear();
                        aveGyr.add("Num5 " + averageValue(gyyy));
                        gyyy.clear();
                        gyyyy.clear();
                }
                return false;
            }
        });
        six.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                tv.setText(tv.getText()+"6");
            }
        });
        six.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event){
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        tl.add("Num6 " + getDateDown());
                        prs.add("Num6 Down " + event.getPressure());
                        size.add("Num6 Down " + event.getSize());
                        flag = true;
                        flagtest = 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        flagtest = 2;
                        flag = true;
                        tl.add("Num6 " + getDateUp());
                        prs.add("Num6 Up " + event.getPressure());
                        size.add("Num6 Up " + event.getSize());
                        everyac.add("Num6 " + arrayEvery(acccc));
                        everygy.add("Num6 "+ arrayEvery(gyyyy));
                        duringmaxac.add("Num6 " + arrayMax(accc));
                        duringminac.add("Num6 " + arrayMin(accc));
                        duringmaxgy.add("Num6 " + arrayMax(gyyy));
                        duringmingy.add("Num6 " + arrayMin(gyyy));
                        aveAcc.add("Num6 " + averageValue(accc));
                        accc.clear();
                        acccc.clear();
                        aveGyr.add("Num6 " + averageValue(gyyy));
                        gyyy.clear();
                        gyyyy.clear();
                }
                return false;
            }
        });
        seven.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                tv.setText(tv.getText()+"7");
            }
        });
        seven.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event){
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        tl.add("Num7 " + getDateDown());
                        prs.add("Num7 Down " + event.getPressure());
                        size.add("Num7 Down " + event.getSize());
                        flag = true;
                        flagtest = 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        flagtest = 2;
                        flag = true;
                        tl.add("Num7 " + getDateUp());
                        prs.add("Num7 Up " + event.getPressure());
                        size.add("Num7 Up " + event.getSize());
                        everyac.add("Num7 " + arrayEvery(acccc));
                        everygy.add("Num7 "+ arrayEvery(gyyyy));
                        duringmaxac.add("Num7 " + arrayMax(accc));
                        duringminac.add("Num7 " + arrayMin(accc));
                        duringmaxgy.add("Num7 " + arrayMax(gyyy));
                        duringmingy.add("Num7 " + arrayMin(gyyy));
                        aveAcc.add("Num7 " + averageValue(accc));
                        accc.clear();
                        acccc.clear();
                        aveGyr.add("Num7 " + averageValue(gyyy));
                        gyyy.clear();
                        gyyyy.clear();
                }
                return false;
            }
        });
        eight.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                tv.setText(tv.getText()+"8");
            }
        });
        eight.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event){
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        tl.add("Num8 " + getDateDown());
                        prs.add("Num8 Down " + event.getPressure());
                        size.add("Num8 Down " + event.getSize());
                        flag = true;
                        flagtest = 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        flagtest = 2;
                        flag = true;
                        tl.add("Num8 " + getDateUp());
                        prs.add("Num8 Up " + event.getPressure());
                        size.add("Num8 Up " + event.getSize());
                        everyac.add("Num8 " + arrayEvery(acccc));
                        everygy.add("Num8 "+arrayEvery(gyyyy));
                        duringmaxac.add("Num8 " + arrayMax(accc));
                        duringminac.add("Num8 " + arrayMin(accc));
                        duringmaxgy.add("Num8 " + arrayMax(gyyy));
                        duringmingy.add("Num8 " + arrayMin(gyyy));
                        aveAcc.add("Num8 " + averageValue(accc));
                        accc.clear();
                        acccc.clear();
                        aveGyr.add("Num8 " + averageValue(gyyy));
                        gyyy.clear();
                        gyyyy.clear();
                }
                return false;
            }
        });
        nine.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                tv.setText(tv.getText()+"9");
            }
        });
        nine.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event){
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        tl.add("Num9 " + getDateDown());
                        prs.add("Num9 Down " + event.getPressure());
                        size.add("Num9 Down " + event.getSize());
                        flag = true;
                        flagtest = 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        flagtest = 2;
                        flag = true;
                        tl.add("Num9 " + getDateUp());
                        prs.add("Num9 Up " + event.getPressure());
                        size.add("Num9 Up " + event.getSize());
                        everyac.add("Num9 " + arrayEvery(acccc));
                        everygy.add("Num9 "+ arrayEvery(gyyyy));
                        duringmaxac.add("Num9 " + arrayMax(accc));
                        duringminac.add("Num9 " + arrayMin(accc));
                        duringmaxgy.add("Num9 " + arrayMax(gyyy));
                        duringmingy.add("Num9 " + arrayMin(gyyy));
                        aveAcc.add("Num9 " + averageValue(accc));
                        accc.clear();
                        acccc.clear();
                        aveGyr.add("Num9 " + averageValue(gyyy));
                        gyyy.clear();
                        gyyyy.clear();
                }
                return false;
            }
        });
        zero.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                tv.setText(tv.getText()+"0");
            }
        });
        zero.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event){
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        tl.add("Num0 " + getDateDown());
                        prs.add("Num0 Down " + event.getPressure());
                        size.add("Num0 Down " + event.getSize());
                        flag = true;
                        flagtest = 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        flagtest = 2;
                        flag = true;
                        tl.add("Num0 " + getDateUp());
                        prs.add("Num0 Up " + event.getPressure());
                        size.add("Num0 Up " + event.getSize());
                        everyac.add("Num0 " + arrayEvery(acccc));
                        everygy.add("Num0 "+ arrayEvery(gyyyy));
                        duringmaxac.add("Num0 " + arrayMax(accc));
                        duringminac.add("Num0 " + arrayMin(accc));
                        duringmaxgy.add("Num0 " + arrayMax(gyyy));
                        duringmingy.add("Num0 " + arrayMin(gyyy));
                        aveAcc.add("Num0 " + averageValue(accc));
                        accc.clear();
                        acccc.clear();
                        aveGyr.add("Num0 " + averageValue(gyyy));
                        gyyy.clear();
                        gyyyy.clear();
                }
                return false;
            }
        });
        del.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (tv.getText().length()!=0)
                    tv.setText(tv.getText().subSequence(0,tv.getText().length()-1));
            }
        });

        enter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // TODO Auto-generated method stub

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
                String pintext = tv.getText().toString();

                if(pintext.equals("1111")||pintext.equals("5555")||pintext.equals("3244")||pintext.equals("12597384")||pintext.equals("12598416")){
                //to open or create the database for training and testing
                    count++;
                    if(SwitchActivity.step == 1){
                        db = openOrCreateDatabase("traindc.db", Context.MODE_PRIVATE, null);
                    }else{
                        db = openOrCreateDatabase("testdc.db", Context.MODE_PRIVATE, null);
                    }

                db.execSQL("CREATE TABLE IF NOT EXISTS person (_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR," +
                        "password VARCHAR, sex VARCHAR, age VARCHAR, count VARCHAR, time TEXT, acceleration TEXT," +
                        " gyroscope TEXT, everyacceleration TEXT, everygyroscope TEXT, maxAcceleration TEXT, minAcceleration TEXT, maxGyroscope TEXT, minGyroscope TEXT, " +
                        "averageAcc TEXT, averageGyr TEXT, pressure VARCHAR, size VARCHAR)");
                Data person = new Data(MainActivity.DataName, MainActivity.DataPsw);
                    person.putInData(MainActivity.sex, MainActivity.age, count1, tl, ac, gy, everyac,everygy,duringmaxac, duringminac, duringmaxgy, duringmingy, aveAcc, aveGyr, prs, size);

                //ContentValues store the data in key-value
                ContentValues cv = new ContentValues();

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
                    //insert into the table
                    db.insert("person", null, cv);
                }
                    count1++;

                db.close();

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
                    usercount.setText("usercount: "+count);

            }else{
                    Toast.makeText(getApplicationContext(), "输入PIN码错误"+tv.getText(), Toast.LENGTH_SHORT).show();

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
                    if(flagtest == 1){
                        ac.add(getAcceleration(event));
                    }

                    if(flagtest == 2){
                        ac.add(getAcceleration(event));
                    }

                }
                if(flagtest == 1) {
                    acccc.add(getEveryAcc(event));
                    accc.add(getResultant(event));
                }
                break;
            case Sensor.TYPE_GYROSCOPE:
                if(flag){
                    if(flagtest == 1){
                        gy.add(getGyroscope(event));
                    }

                    if(flagtest == 2){
                        gy.add(getGyroscope(event));
                    }

                }
                if(flagtest == 1){
                gyyy.add(getResultant(event));
                gyyyy.add(getEveryGyy(event));
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