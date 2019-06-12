package com.example.newsdemo.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.newsdemo.Fragment.Entertainmentfragment;
import com.example.newsdemo.Fragment.Hotnewsfragment;
import com.example.newsdemo.Fragment.MyHomeFragment;
import com.example.newsdemo.Fragment.Recommendfragment;
import com.example.newsdemo.R;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout linearLayout_recomment;
    private LinearLayout linearLayout_hotnews;
    private LinearLayout linearLayout_entertainment;
    private LinearLayout linearLayout_myhome;

    private Recommendfragment recommendfragment;
    private Hotnewsfragment hotnewsfragment;
    private Entertainmentfragment entertainmentfragment;
    private MyHomeFragment myHomeFragment;

    private List<Fragment> fragmentList = new ArrayList<>();

    private TextView textViewRecommend;
    private TextView textViewHotnews;
    private TextView textViewEntertainment;
    private TextView textViewMyhome;

    private ImageView imgRecommend;
    private ImageView imgHotnews;
    private ImageView imgEntertainment;
    private ImageView imgMyhome;
    private int flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initFragment();

        //接受Intent传递的数据，并返回之前的fragment
        flag = getIntent().getIntExtra("fragment_flag",1);
        if(flag != 1){
            choosefragment(flag);
        }
        linearLayout_recomment.setOnClickListener(this);
        linearLayout_hotnews.setOnClickListener(this);
        linearLayout_entertainment.setOnClickListener(this);
        linearLayout_myhome.setOnClickListener(this);


    }

    private void initView(){
        linearLayout_recomment = findViewById(R.id.layout_recommend);
        linearLayout_hotnews = findViewById(R.id.layout_hotnews);
        linearLayout_entertainment = findViewById(R.id.layout_entertainment);
        linearLayout_myhome = findViewById(R.id.layout_myhome);

        textViewRecommend =  findViewById(R.id.text_recommend);
        textViewHotnews =  findViewById(R.id.text_hotnews);
        textViewEntertainment = findViewById(R.id.text_entertainment);
        textViewMyhome = findViewById(R.id.text_myhome);


        imgRecommend =  findViewById(R.id.img_recommend);
        imgHotnews = findViewById(R.id.img_hotnews);
        imgEntertainment = findViewById(R.id.img_entertainment);
        imgMyhome = findViewById(R.id.img_myhome);

        imgRecommend.setImageResource(R.drawable.recommend_select);
        textViewRecommend.setTextColor(Color.RED);
    }

    private void initFragment() {
        recommendfragment = new Recommendfragment();
        addFragment(recommendfragment);


    }

    /*添加fragment*/
    private void addFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!fragment.isAdded()) {
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.add(R.id.recommend_content, fragment).commit();
            fragmentList.add(fragment);
        }
    }

    private void showFragment(Fragment fragment) {
        for (Fragment frag : fragmentList) {
            if (frag != fragment) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.hide(frag).commit();
            }
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.show(fragment).commit();
    }

    @Override
    public void onClick(View view) {
        choosefragment(view.getId());
    }

    public void choosefragment(int flag){
        switch (flag) {
            case R.id.layout_recommend: {
                if (recommendfragment == null) {
                    recommendfragment = new Recommendfragment();
                }
                addFragment(recommendfragment);
                showFragment(recommendfragment);
                textViewRecommend.setTextColor(Color.RED);
                textViewHotnews.setTextColor(Color.BLACK);
                textViewEntertainment.setTextColor(Color.BLACK);
                textViewMyhome.setTextColor(Color.BLACK);

                imgRecommend.setImageResource(R.drawable.recommend_select);
                imgHotnews.setImageResource(R.drawable.hotnews);
                imgEntertainment.setImageResource(R.drawable.entertainment);
                imgMyhome.setImageResource(R.drawable.myhome);

            }
            break;

            case R.id.layout_hotnews: {
                if (hotnewsfragment == null) {
                    hotnewsfragment = new Hotnewsfragment();
                }

                addFragment(hotnewsfragment);
                showFragment(hotnewsfragment);

                textViewHotnews.setTextColor(Color.RED);
                textViewRecommend.setTextColor(Color.BLACK);
                textViewEntertainment.setTextColor(Color.BLACK);
                textViewMyhome.setTextColor(Color.BLACK);

                imgRecommend.setImageResource(R.drawable.recommend);
                imgHotnews.setImageResource(R.drawable.hotnews_select);
                imgEntertainment.setImageResource(R.drawable.entertainment);
                imgMyhome.setImageResource(R.drawable.myhome);
            }
            break;

            case R.id.layout_entertainment: {
                if (entertainmentfragment == null) {
                    entertainmentfragment = new Entertainmentfragment();
                }
                addFragment(entertainmentfragment);
                showFragment(entertainmentfragment);

                textViewEntertainment.setTextColor(Color.RED);
                textViewRecommend.setTextColor(Color.BLACK);
                textViewHotnews.setTextColor(Color.BLACK);
                textViewMyhome.setTextColor(Color.BLACK);

                imgRecommend.setImageResource(R.drawable.recommend);
                imgHotnews.setImageResource(R.drawable.hotnews);
                imgEntertainment.setImageResource(R.drawable.entertainment_select);
                imgMyhome.setImageResource(R.drawable.myhome);
            }
            break;

            case R.id.layout_myhome: {
                if (myHomeFragment == null) {
                    myHomeFragment = new MyHomeFragment();
                }

                addFragment(myHomeFragment);
                showFragment(myHomeFragment);

                textViewMyhome.setTextColor(Color.RED);
                textViewRecommend.setTextColor(Color.BLACK);
                textViewHotnews.setTextColor(Color.BLACK);
                textViewEntertainment.setTextColor(Color.BLACK);

                imgRecommend.setImageResource(R.drawable.recommend);
                imgHotnews.setImageResource(R.drawable.hotnews);
                imgEntertainment.setImageResource(R.drawable.entertainment);
                imgMyhome.setImageResource(R.drawable.myhome_select);
            }

            default:
                break;
        }
    }
}
