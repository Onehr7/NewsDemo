package com.example.newsdemo.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.newsdemo.Activity.EditInfActivity;
import com.example.newsdemo.Activity.FeedbackActivity;
import com.example.newsdemo.Activity.ShowCollectionActivity;
import com.example.newsdemo.Activity.loginActivity;
import com.example.newsdemo.R;
import com.example.newsdemo.Utils.DataCleanManager;

public class MyHomeFragment extends Fragment {

    private View view;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private TextView textViewusername;
    private TextView textViewcacheShow;

    private Button buttoncache;
    private Button buttonLogout;
    private Button buttonExit;
    private Button editPassword;
    private Button buttonfeedback;
    private Button buttonCollection;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.myhomefragment, container, false);

        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.view_pager);

        //显示用户名
        SharedPreferences sp = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        textViewusername = view.findViewById(R.id.text_username);
        String username = sp.getString("username", null);
        textViewusername.setText(username);

        //退出登录功能实现
        buttonLogout = view.findViewById(R.id.button_exitlogin);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //清空SharedPreferences
                SharedPreferences sp =  getActivity().getSharedPreferences("login",Context.MODE_PRIVATE);
                sp.edit().clear().commit();

                //界面跳转
                Intent intent = new Intent(getContext(), loginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        //退出系统功能
        buttonExit = view.findViewById(R.id.button_exitsystem);
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        //清除缓存操作
        buttoncache = view.findViewById(R.id.tv_cache_clean);
        textViewcacheShow = view.findViewById(R.id.show_cache_clean);
        try {
            String data = DataCleanManager.getTotalCacheSize(getActivity());
            textViewcacheShow.setText(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        buttoncache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCleanManager.clearAllCache(getActivity());
                textViewcacheShow.setText("0.0M");
                Toast.makeText(getActivity(), "缓存已清理", Toast.LENGTH_SHORT).show();
            }
        });

        //更改密码操作
        editPassword = view.findViewById(R.id.edit_password);
        editPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditInfActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        //意见反馈
        buttonfeedback = view.findViewById(R.id.my_feedback);
        buttonfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FeedbackActivity.class);
                startActivity(intent);
            }
        });

        //收藏功能
        buttonCollection  = view.findViewById(R.id.my_collection);
        buttonCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowCollectionActivity.class);
                startActivity(intent);
            }
        });

//        initView();
        return view;
    }


    private void initView(){

//        tabLayout = view.findViewById(R.id.tab_layout);
//        viewPager = view.findViewById(R.id.view_pager);
//
//        //显示用户名
//        SharedPreferences sp = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
//        textViewusername = view.findViewById(R.id.text_username);
//        textViewusername.setText(sp.getString("username", null));
//
//        //退出功能实现
//        buttonLogout = view.findViewById(R.id.button_exitlogin);
//        buttonLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), loginActivity.class);
//                startActivity(intent);
//                getActivity().finish();
//            }
//        });
//
//        //退出系统功能
//        buttonExit = view.findViewById(R.id.button_exitsystem);
//        buttonExit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().finish();
//            }
//        });
//
//        //清除缓存操作
//        buttoncache = view.findViewById(R.id.tv_cache_clean);
//        buttoncache = view.findViewById(R.id.show_cache_clean);
//        try {
//            String data = DataCleanManager.getTotalCacheSize(getActivity());
//            textViewcacheShow.setText(data);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        buttoncache.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DataCleanManager.clearAllCache(getActivity());
//                textViewcacheShow.setText("0.0M");
//                Toast.makeText(getActivity(), "缓存已清理", Toast.LENGTH_SHORT).show();
//                    }
//        });
//
//        //更改密码操作
//        editPassword = view.findViewById(R.id.edit_password);
//        editPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), EditInfActivity.class);
//                startActivity(intent);
//                getActivity().finish();
//            }
//        });
//
//        //意见反馈
//        buttonfeedback = view.findViewById(R.id.my_feedback);
//        buttonfeedback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), FeedbackActivity.class);
//                startActivity(intent);
//                getActivity().finish();
//            }
//        });
    }

}
