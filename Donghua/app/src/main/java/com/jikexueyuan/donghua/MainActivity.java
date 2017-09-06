package com.jikexueyuan.donghua;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TranslateAnimation ta;
//    private AnimationSet as;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        rootView= (LinearLayout) findViewById(R.id.root);

        ta=new TranslateAnimation(0,500,0,500);
//        as=new TranslateAnimation(0,0,0,1000);
        ta.setDuration(1000);
        findViewById(R.id.moveJava).setOnClickListener(this);
        findViewById(R.id.moveXml).setOnClickListener(this);
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.imageView).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.moveJava:
//                Animation-----Java code
                viewAnimation(view);
                break;
            case R.id.moveXml:
//                viewAnimation----XML
                view.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.move_1));
             break;
            case R.id.button:
                    /*FrameAnimator---XML*/
                frameAnimatorXML(view);
                break;
            case R.id.button2:
                    /*FrameAnimator---Java Code*/
                frameAnimatorCode(view);
                break;
            case R.id.imageView:
                imageAnimateFrame(view);
                imageAnimate(view);
        }
    }
    private void viewAnimation(View v) {

        AnimationSet as = new AnimationSet(true);
        TranslateAnimation ta1 = new TranslateAnimation(0,500,0,0);
        ta1.setDuration(1000);
        as.addAnimation(ta1);
        TranslateAnimation ta2 = new TranslateAnimation(0,0,0,500);
        ta2.setDuration(1000);
        ta2.setStartOffset(1000);
        as.addAnimation(ta2);
        v.startAnimation(as);
    }

    /*FrameAnimator---Java Code*/
    private void frameAnimatorCode(View v) {
        AnimatorSet set = new AnimatorSet();
        set.setDuration(1000);
        set.playSequentially(ObjectAnimator.ofFloat(v, "translationX", 0, 500),
                ObjectAnimator.ofFloat(v, "translationY", 0, 500),
                ObjectAnimator.ofFloat(v, "translationY", 500, 0),
                ObjectAnimator.ofFloat(v, "translationX", 500, 0));
        set.start();
    }

    /*FrameAnimator---XML*/
    private void frameAnimatorXML(View v) {
        AnimatorSet animator = (AnimatorSet) AnimatorInflater.loadAnimator(this,R.animator.animator_yids);
        animator.setTarget(v);
        animator.start();
    }

    /*图片翻转----FrameAnimatorAnimation*/
    private void imageAnimateFrame(View v){
        AnimatorSet set = new AnimatorSet();
        set.setDuration(1000);
        set.playSequentially(ObjectAnimator.ofFloat(v, "rotationY", 0f, 180f),
                ObjectAnimator.ofFloat(v, "rotationY",180f,0f));
        set.start();
    }

    /*图片翻转----ViewAnimation*/
    private void imageAnimate(View v) {

        AnimationSet as = new AnimationSet(true);
        //首次翻转，使用自定义动画 ImageAnim3D
        ImageAnim3D imgAnim3D = new ImageAnim3D(false);
        imgAnim3D.setDuration(1000);
        v.measure(0, 0);
        imgAnim3D.setCenter(v.getWidth() / 2, v.getHeight() / 2);
        imgAnim3D.setFillAfter(true);
        as.addAnimation(imgAnim3D);
        //翻转回来
        ImageAnim3D imgAnim3D2 = new ImageAnim3D(true);
        imgAnim3D2.setDuration(1000);
        imgAnim3D2.setStartOffset(1000);
        v.measure(0, 0);
        imgAnim3D2.setCenter(v.getWidth() / 2, v.getHeight() / 2);
        as.addAnimation(imgAnim3D2);

        v.startAnimation(as);

    }

}
