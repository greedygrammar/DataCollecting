package com.example.alice_wang.datacollect;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
    private EditText edname;
    private EditText edpassword;

    private Button btregister;
    private Button btlogin;
    public static String DataName=null;
    public static String DataPsw=null;
    public static String sex=null;
    public static String age=null;

    // a sqlite databse
    public static SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edname = (EditText) findViewById(R.id.edname);
        edpassword = (EditText) findViewById(R.id.edpassword);
        btregister = (Button) findViewById(R.id.btregister);
        btlogin = (Button) findViewById(R.id.btlogin);

        //create user database
        db = SQLiteDatabase.openOrCreateDatabase(MainActivity.this.getFilesDir().toString()
                + "/testname.dbs", null);
        db.execSQL("create table if not exists tb_user1( name varchar(30) primary key,password varchar(30),sex varchar(30),age varchar(16))");

        //jump to register
        btregister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, RegistersActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        //jump to login
        btlogin.setOnClickListener(new LoginListener());
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        db.close();
    }


    class LoginListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            String name = edname.getText().toString();
            String password = edpassword.getText().toString();
            if (name.equals("") || password.equals("")) {

                // alert
                new AlertDialog.Builder(MainActivity.this).setTitle("错误")
                        .setMessage("帐号或密码不能空").setPositiveButton("确定", null)
                        .show();
            } else {
                isUserinfo(name, password);
            }
        }

        // to judge weather input the right info or not
        public Boolean isUserinfo(final String name,final String pwd) {                        //pwd声明为final类型
            try{
                String str="select * from tb_user1 where name=? and password=?";
                final Cursor cursor = db.rawQuery(str, new String []{name,pwd});
                if(cursor.getCount()<=0){
                    new AlertDialog.Builder(MainActivity.this).setTitle("错误")
                            .setMessage("帐号或密码错误！").setPositiveButton("确定", null)
                            .show();
                    return false;
                }else{
                    new AlertDialog.Builder(MainActivity.this).setTitle("正确")
                            .setMessage("成功登录").
                            setPositiveButton("确定", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which){
                                    //jump to switch activity
                                    DataName=name;
                                    DataPsw=pwd;
                                    cursor.moveToFirst();
                                    sex=cursor.getString(cursor.getColumnIndex("sex"));
                                    age=cursor.getString(cursor.getColumnIndex("age"));
                                    Intent intent = new Intent();
                                    intent.setClass(MainActivity.this, SwitchActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);

                                }
                            }).show();
                    return true;
                }
            }catch(SQLiteException e){
                createDb();
            }
            return false;
        }

    }
    // create user database in case of exception
    public void createDb() {
        db.execSQL("create table if not exists tb_user1( name varchar(30) primary key,password varchar(30),sex varchar(10),age varchar(16))");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}