package com.example.hzkxma.xiaochu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
    Button loadButton;
    EditText userName, key;
    String name1="12345" ,key1="12345";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = (EditText)findViewById(R.id.editText);
        key = (EditText)findViewById(R.id.editText2);
        loadButton = (Button)findViewById(R.id.Button);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userName.getText().toString().equals(name1)&&key.getText().toString().equals(key1)) {
                    dialog1();
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this,MainActivity2Activity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void dialog1(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("登录成功！");
        builder.show();
    }
}
