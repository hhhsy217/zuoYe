package com.jikexueyuan.guessnumber;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Scanner;

public class GuessNumber extends AppCompatActivity implements OnClickListener {
    private EditText et;
    private TextView tv;
    private Button bt1, bt2;
    public int a, b, count = 0;
    String c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_number);
        et = (EditText) findViewById(R.id.et);
        tv = (TextView) findViewById(R.id.tv);

        bt1 = (Button) findViewById(R.id.bt1);
        bt2 = (Button) findViewById(R.id.bt2);
        bt1.setOnClickListener(this);

        a = (int) (Math.random() * 100 + 1);
        bt2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                a = (int) (Math.random() * 100 + 1);
            }
        });
    }

    public void onClick(View v){

        count++;
        if (v == bt1) {
            c = et.getText().toString();
            if (TextUtils.isEmpty(c)) {
                Toast.makeText(this, "数字不能为空", Toast.LENGTH_SHORT).show();
            } else {
                int b = Integer.parseInt(c);
                if (a == b) {
                    tv.setText("你猜了" + count + "次猜中！");

                } else if (b > a) {
                    tv.setText("大了！");

                } else if (b < a) {
                    tv.setText("小了！");

                }
                et.setText("");//清空EditText
            }
        }
    }
}

