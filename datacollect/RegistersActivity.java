package com.example.alice_wang.datacollect;

import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
/**
 * Created by alice_wang on 16/8/10.
 */
public class RegistersActivity extends Activity {

    private EditText edname1;
    private EditText edpassword1;
    private Button btregister1;
    private RadioGroup edsex;
    private RadioButton male;
    private RadioButton female;
    private EditText edage;
    //public static String sex;
    //public static String age;
    SQLiteDatabase db;


    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        db.close();
    }


    private class OnCheckedChangeListenerImp implements RadioGroup.OnCheckedChangeListener {

        public void onCheckedChanged(RadioGroup group, int checkedId) {
//            String temp = null;
            if (RegistersActivity.this.male.getId() == checkedId) {
                MainActivity.sex = "male";
            } else if (RegistersActivity.this.female.getId() == checkedId) {
                MainActivity.sex = "female";
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);
        edname1 = (EditText) findViewById(R.id.edname1);
        edpassword1 = (EditText) findViewById(R.id.edpassword1);
        btregister1 = (Button) findViewById(R.id.btregister1);
        edsex = (RadioGroup) findViewById(R.id.sex);
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        edage = (EditText) findViewById(R.id.edage);
        edsex.setOnCheckedChangeListener(new OnCheckedChangeListenerImp());


        btregister1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String name = edname1.getText().toString();
                String password = edpassword1.getText().toString();
                MainActivity.age = edage.getText().toString();
                if (!(name.equals("") && password.equals(""))) {
                    if (addUser(name, password)) {
                        DialogInterface.OnClickListener ss = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // TODO Auto-generated method stub
                                // 跳转到登录界面
                                Intent in = new Intent();
                                in.setClass(RegistersActivity.this,
                                        MainActivity.class);
                                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(in);
                                // 销毁当前activity
                                RegistersActivity.this.onDestroy();
                            }
                        };
                        new AlertDialog.Builder(RegistersActivity.this)
                                .setTitle("注册成功").setMessage("注册成功")
                                .setPositiveButton("确定", ss).show();
                        Log.v("1",""+MainActivity.sex);
                        Log.v("2",""+MainActivity.age);

                    } else {
                        new AlertDialog.Builder(RegistersActivity.this)
                                .setTitle("注册失败").setMessage("注册失败")
                                .setPositiveButton("确定", null).show();
                    }
                } else {
                    new AlertDialog.Builder(RegistersActivity.this)
                            .setTitle("帐号密码不能为空").setMessage("帐号密码不能为空")
                            .setPositiveButton("确定", null).show();
                }

            }
        });

    }

    // 添加用户
    public Boolean addUser(String name, String password) {
        String str = "insert into tb_user1 values(?,?,?,?) ";
        MainActivity main = new MainActivity();
        Log.v("yqq","yqq"+this.getFilesDir().toString());
        db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString()
                + "/testname.dbs", null);
        main.db = db;
        try {
            db.execSQL(str, new String[] { name, password,MainActivity.sex,MainActivity.age});
            return true;
        } catch (Exception e) {
            main.createDb();
        }
        return false;
    }





}

