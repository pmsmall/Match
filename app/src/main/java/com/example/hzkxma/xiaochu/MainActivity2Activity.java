package com.example.hzkxma.xiaochu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2Activity extends Activity {
    static int[][] grid = new int[8][7];
    int[][] buttonId = {{R.id.button, R.id.button2, R.id.button3, R.id.button4, R.id.button5}, {R.id.button6, R.id.button7, R.id.button8, R.id.button9, R.id.button10},
            {R.id.button11, R.id.button12, R.id.button13, R.id.button14, R.id.button15}, {R.id.button16, R.id.button17, R.id.button18, R.id.button19, R.id.button20},
            {R.id.button21, R.id.button22, R.id.button23, R.id.button24, R.id.button25}, {R.id.button26, R.id.button27, R.id.button28, R.id.button29, R.id.button30}};
    static Button button[][] = new Button[6][5];
    Button Button1, Button2;
    int img[] = {R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five};
    int x1 = 0, y1 = 0, x2 = 0, y2 = 0, firstMsg = 0, secondMsg = 0;
    static TextView gradeText,stepText;
    static int grade=0,step=50;
    static boolean pressInformation = false,stepInformation=true;
    static AlertDialog.Builder builder2;

    /*
    界面初始化
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);
        gradeText=(TextView)findViewById(R.id.textView3);
        stepText=(TextView)findViewById(R.id.textView);
        builder2 = new AlertDialog.Builder(this);

        random();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                this.button[i][j] = (Button) findViewById(buttonId[i][j]);
                figure(i, j);
            }
        }
        listener lis = new listener(buttonId, button, grid);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                this.button[i][j].setOnClickListener(lis);
            }
        }

        Button reset=(Button)findViewById(R.id.button31);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                random();
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 5; j++) {
                        figure(i, j);
                    }
                }
                grade=-10;
                step=51;
                gradeText();
                stepText();
            }
        });
    }
    /*
    生成随机数
     */
    public void random() {
        int random, cols, rows;
        for (cols = 1; cols < 7; cols++) {
            for (rows = 1; rows < 6; rows++) {
                random = (int) (Math.random() * 5);
                if ((cols > 1 && random == this.grid[cols - 1][rows] && random == this.grid[cols - 2][rows]) ||
                        (rows > 1 && random == this.grid[cols][rows - 1] && random == this.grid[cols][rows - 2])) {
                    rows--;
                }
                this.grid[cols][rows] = random;
            }
        }
    }
    /*
    配图片
     */
    public void figure(int x, int y) {
        button[x][y].setBackgroundResource(img[grid[x + 1][y + 1]]);
    }
    /*
    交换图片
     */
    public void change(int x, int y, Button button) {
        Log.v("123", "进入change");
        if (pressInformation) {
            String n = "第二次点击";
            Log.v("123", n);
            x1 = x2;
            y1 = y2;
            firstMsg = secondMsg;
            Button1 = Button2;
            x2 = x;
            y2 = y;
            Button2 = button;
            secondMsg = grid[x2][y2];
            pressInformation = false;
            if (((x1 - x2 == 1 || x2 - x1 == 1) && y1 == y2) || ((y1 - y2 == 1 || y2 - y1 == 1) && x1 == x2)) {
                if(step>0){
                grid[x2][y2] = firstMsg;
                grid[x1][y1] = secondMsg;

                figure(x1 - 1, y1 - 1);
                figure(x2 - 1, y2 - 1);
                stepText();}
                else{
                    builder2.setMessage("没有精力!");
                    builder2.show();
                }
            }
        } else {
            String n1 = "第一次点击";
            Log.v("123", n1);
            x2 = x;
            y2 = y;
            Button2 = button;
            secondMsg = grid[x2][y2];
            pressInformation = true;
        }
    }
    /*
    补齐
     */
    public void recompose() {
        for (int cols = 1; cols <= 6; cols++) {
            for (int rows = 1; rows <= 5; rows++) {
                if (cols > 1 && button[cols - 1][rows - 1].getVisibility() == View.INVISIBLE && (button[cols - 2][rows - 1].getVisibility() == View.VISIBLE)) {
                    grid[cols][rows] = grid[cols - 1][rows];
                    figure(cols - 1, rows - 1);
                    button[cols - 1][rows - 1].setVisibility(View.VISIBLE);
                    button[cols - 2][rows - 1].setVisibility(View.INVISIBLE);
                }
                if (cols == 1 && button[cols - 1][rows - 1].getVisibility() == View.INVISIBLE) {
                    grid[cols][rows] = (int) (Math.random() * 5);
                    figure(cols - 1, rows - 1);
                    button[cols - 1][rows - 1].setVisibility(View.VISIBLE);
                }
            }
        }
        exam();
    }
    /*
    消除
     */
    public void setInvisible(int cols,int rows){
        if (cols>1&&cols<6&&grid[cols][rows] == grid[cols + 1][rows] && grid[cols][rows] == grid[cols - 1][rows]) {
            button[cols - 1][rows - 1].setVisibility(View.INVISIBLE);
            button[cols][rows - 1].setVisibility(View.INVISIBLE);
            button[cols - 2][rows - 1].setVisibility(View.INVISIBLE);
            gradeText();
        }
        if (rows>1&&rows<5&&grid[cols][rows] == grid[cols][rows + 1] && grid[cols][rows] == grid[cols][rows - 1]) {
            button[cols - 1][rows - 1].setVisibility(View.INVISIBLE);
            button[cols - 1][rows].setVisibility(View.INVISIBLE);
            button[cols - 1][rows - 2].setVisibility(View.INVISIBLE);
            gradeText();
        }
    }
    /*
    检查图案
     */
    public void exam() {
        for (int cols = 1; cols <= 6; cols++) {
            for (int rows = 1; rows <= 5; rows++) {
                if (button[cols - 1][rows - 1].getVisibility() == View.INVISIBLE) {
                    recompose();
                } else {
                    setInvisible(cols,rows);
                }
            }
        }
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
    /*
    重写onBackPressed使弹出提示框
     */
    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("真的要退出吗？").setNegativeButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                grade=0;
                step=50;
                BackPressed();
            }
        }).setPositiveButton("取消",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();

    }
    public void BackPressed(){
        super.onBackPressed();
    }
    public void grade(){
        grade+=10;
    }
    public void gradeText(){
        grade();
        gradeText.setText(grade+"");
    }
    public void step(){
        step--;
    }
    public void stepText(){
        step();
        stepText.setText("剩余精力："+step);
    }
}


/*
监听器
 */
class listener implements View.OnClickListener {
    Button[][] button;

    int[][] buttonId, grid;

    MainActivity2Activity act = new MainActivity2Activity();

    public listener(int[][] buttonId, Button[][] button, int[][] grid) {
        this.buttonId = buttonId;
        this.button = button;
        this.grid = grid;
    }
    @Override
    public void onClick(View v) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (v.getId() == buttonId[i][j]) {
                    String n = "找到按钮";
                    Log.v("123", n);
                    act.change(i + 1, j + 1,button[i][j]);
                }
            }
        }
        for (int cols = 1; cols <= 6; cols++) {
            for (int rows = 1; rows <= 5; rows++) {
                act.setInvisible(cols,rows);
            }
            if(cols==6){
                act.recompose();
                act.exam();
            }
        }

    }
}