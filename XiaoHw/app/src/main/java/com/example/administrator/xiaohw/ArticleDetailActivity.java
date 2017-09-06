package com.example.administrator.xiaohw;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 文章详情
 */
public class ArticleDetailActivity extends AppCompatActivity {

    private TextView tvPostTitle, tvPostDate, tvPostContent;
    private ImageView ibBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        initView();
        initData();
        initMonitor();
    }

    private void initMonitor() {
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();   //注销，返回上一界面
            }
        });

    }

    private void initData() {
        Intent intent = getIntent();
        if (null != intent){
            Bundle bundle = getIntent().getExtras();
            tvPostTitle.setText(bundle.getString("post_title"));
            tvPostDate.setText("发布时间："+bundle.getString("post_date"));
            tvPostContent.setText(bundle.getString("post_content"));
        }
    }

    private void initView() {
        tvPostTitle = (TextView) findViewById(R.id.tvPostTitle);
        tvPostDate = (TextView) findViewById(R.id.tvPostDate);
        tvPostContent = (TextView) findViewById(R.id.tvPostContent);
        ibBack = (ImageView) findViewById(R.id.ibBack);
    }


}
