package com.example.newsdemo.Utils;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.newsdemo.R;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @description:上拉加载
 */
public class LoadListView extends ListView
        implements AbsListView.OnScrollListener {
    private int lastVisibleItem; //最后一个可见项
    private int totalItems; //总的item
    private View footer, header; //底部View+头部View;
    private boolean isLoading = false;//是否正在加载
    private ILoadListener iListener;//自定义的一个加载接口。
    // 暴露给MainActivity让它实现具体加载操作。可以根据需求不同而改写。

    private boolean isRemark = false;//判断是否在当前页的最顶端并下滑
    private int startY; //Y坐标 记录手指开始按下的坐标
    private RLoadListener rLoadListener;//自定义的一个加载接口。
    // 暴露给MainActivity让它实现具体加载操作。可以根据需求不同而改写。

    private int scrollState;//当前滚动的 状态

    private int headerHeight;//顶部布局文件的高度

    final int NONE = 0;//正常状态
    final int PULL = 1;//下拉
    final int RELESE = 2;//释放
    final int REFLASHING = 3; //刷新

    private int state = 0;//判断当前状态,默认为正常状态

    private int firstVisibleItem;//第一个可见项


    public LoadListView(Context context) {
        this(context, null);

    }

    public LoadListView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.listViewStyle);

    }

    public LoadListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initViews(context);


    }

    /**
     * 添加底部+头部提示到ListVIew
     *
     * @param context
     */
    private void initViews(Context context) {
        //获得footer+header布局文件
        LayoutInflater inflater = LayoutInflater.from(context);
        footer = inflater.inflate(R.layout.footer, null);

        footer.findViewById(R.id.ll_footer).setVisibility(GONE);
        //初始化时设置footer不可见

        header = inflater.inflate(R.layout.header, null);
        //测量header的宽和高
        measureView(header);
        //记录下header的高
        headerHeight = header.getMeasuredHeight();
        topPadding(-headerHeight);

        this.addHeaderView(header);
        this.addFooterView(footer);
        this.setOnScrollListener(this);//设置滚动监听

    }


    /**
     * 通知父布局，占用的宽和高
     *
     * @param view
     */
    private void measureView(View view) {

        //得到view的布局宽高
        ViewGroup.LayoutParams vlp = view.getLayoutParams();
        //如果没有就new一个
        if (vlp == null) {
            vlp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        //ViewGroup.getChildMeasureSpec(int spec,int padding,int childDimension)
        // 1.父view的详细尺寸 2.view当前尺寸的下的边距 3.child在当前尺寸下的宽（高）
        int width = ViewGroup.getChildMeasureSpec(0, 0, vlp.width);
        int height;
        int tempHeight = vlp.height;
        if (tempHeight > 0) {
            height = MeasureSpec.makeMeasureSpec(tempHeight, MeasureSpec.EXACTLY);

        } else {
            height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        view.measure(width, height);

    }


    /**
     * 设置header布局的上边距
     *
     * @param topPadding
     */
    private void topPadding(int topPadding) {
        header.setPadding(header.getPaddingLeft(), topPadding, header.getPaddingRight(), header.getPaddingBottom());
        //重绘
        header.invalidate();

    }


    @Override
    public void onScrollStateChanged(AbsListView view,
                                     int scrollState) {
        this.scrollState = scrollState;
        if (lastVisibleItem == totalItems &&
                scrollState == SCROLL_STATE_IDLE) {
            //如果不是在加载
            if (!isLoading) {
                footer.findViewById(R.id.ll_footer)
                        .setVisibility(View.VISIBLE);
                iListener.onLoad();
                isLoading = true;
            }
        }
    }
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        this.lastVisibleItem = firstVisibleItem + visibleItemCount;
        this.totalItems = totalItemCount;
        this.firstVisibleItem = firstVisibleItem;
    }

    /**
     * 加载更多数据的回调接口
     */
    public interface ILoadListener {
        public void onLoad();
    }

    //上拉加载完毕
    public void loadCompleted() {
        isLoading = false;
        footer.findViewById(R.id.ll_footer).setVisibility(GONE);
    }


    public void setInterface(ILoadListener iListener) {

        this.iListener = iListener;
    }

    /**
     * 下拉刷新接口
     */
    public interface RLoadListener {
        public void onRefresh();

    }

    public void setReflashInterface(RLoadListener rLoadListener) {
        this.rLoadListener = rLoadListener;

    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN://如果按下
                if (firstVisibleItem == 0) {
                    isRemark = true;
                    startY = (int) ev.getY();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                onMove(ev);
                break;
            case MotionEvent.ACTION_UP:
                if (state == RELESE) {
                    state = REFLASHING;
                    //加载最新数据
                    reflashViewByState();
                    rLoadListener.onRefresh();
                } else if (state == PULL) {
                    state = NONE;
                    isRemark = false;
                    reflashViewByState();
                }
                break;


        }

        return super.onTouchEvent(ev);
    }

    /**
     * 判断移动的过程
     *
     * @param ev
     */
    private void onMove(MotionEvent ev) {
        if (!isRemark) {
            return;
        }
        int tempY = (int) ev.getY();
        //记录滑动距离
        int space = tempY - startY;
        //比较滑动距离和header的高
        int topPadding = space - headerHeight;
        switch (state) {
            case NONE:
                if (space > 0) {
                    state = PULL;
                    reflashViewByState();
                }
                break;
            case PULL:
                //重绘header
                topPadding(topPadding);
                if (space > headerHeight + 30 && scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                    state = RELESE;
                    reflashViewByState();

                }
                break;
            case RELESE:
                topPadding(topPadding);
                if (space < headerHeight + 30) {
                    state = PULL;
                    reflashViewByState();
                } else if (space <= 0) {//如果space<0说明向上滑。所以firstVisibleItem就不是0了 所以将isRemark设置为false;即listView现在不是最顶端的位置
                    state = NONE;
                    isRemark = false;
                    reflashViewByState();
                }
                break;

        }

    }


    /**
     * 根据当前状态，改变界面显示
     */
    private void reflashViewByState() {
        TextView tip = (TextView) header.findViewById(R.id.tip);
        ImageView arrow = (ImageView) header.findViewById(R.id.arrow);
        ProgressBar header_pb = (ProgressBar) header.findViewById(R.id.header_pb);
        //设置的下拉箭头的动画效果
        RotateAnimation animation = new RotateAnimation(0, 180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(500);
        animation.setFillAfter(true);
        //设置的下拉箭头的动画效果
        RotateAnimation animation2 = new RotateAnimation(180, 0,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        animation2.setDuration(500);
        animation2.setFillAfter(true);

        /**
         * 四种状态动画的改变
         */
        switch (state) {
            case NONE:
                arrow.clearAnimation();
                topPadding(-headerHeight);
                break;
            case PULL:
                arrow.setVisibility(View.VISIBLE);
                header_pb.setVisibility(View.GONE);
                tip.setText("下拉刷新");
                arrow.clearAnimation();
                arrow.setAnimation(animation2);
                break;
            case RELESE:
                arrow.setVisibility(View.VISIBLE);
                header_pb.setVisibility(View.GONE);
                tip.setText("松开刷新");
                arrow.clearAnimation();
                arrow.setAnimation(animation);
                break;
            case REFLASHING:
                topPadding(50);
                arrow.setVisibility(View.GONE);
                header_pb.setVisibility(View.VISIBLE);
                tip.setText("正在刷新...");
                arrow.clearAnimation();
                break;
        }


    }


    /**
     * 获取完整数据
     */
    public void reflashComplete() {
        state = NONE;
        isRemark = false;
        reflashViewByState();
        //设置刷新完成的时间
        TextView lasetupdate_time = (TextView) header.findViewById(R.id.tv_lastupdate_time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String time = simpleDateFormat.format(date);
        lasetupdate_time.setText(time);


    }

}

