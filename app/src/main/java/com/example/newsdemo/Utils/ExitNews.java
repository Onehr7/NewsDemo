package com.example.newsdemo.Utils;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

/**
 * @description:实现退出系统功能
 */
public class ExitNews extends Application {
    private List<Activity> mList = new LinkedList<Activity>();
    private static ExitNews instance;

    private ExitNews() {
    }

    public synchronized static ExitNews getInstance() {
        if (instance == null) {
            instance = new ExitNews();
        }
        return instance;
    }

    // 添加Activity到列表中维持
    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null) {
                    activity.finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
}
