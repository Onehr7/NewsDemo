package com.example.newsdemo.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.newsdemo.Adapter.NewsAdapter;
import com.example.newsdemo.Beans.News;
import com.example.newsdemo.R;
import com.example.newsdemo.Utils.HttpUtils;
import com.example.newsdemo.login.DBOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 显示收藏夹
 */
public class ShowCollectionActivity extends AppCompatActivity implements NewsAdapter.CallBack {


    private DBOpenHelper dbOpenHelper;
    private ListView collection;    //绑定表格
    private NewsAdapter newsAdapter;
    private List<News> newsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        dbOpenHelper = new DBOpenHelper(this);
        initView();
        initNews();

        collection.setAdapter(newsAdapter);
        collection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String url = newsList.get(position).getNews_url();
                Intent intent = new Intent(ShowCollectionActivity.this,ShowNewsActivity.class);
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });
    }


    private void initNews(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
                Cursor cursor = db.rawQuery("select * from Collection_News",null);
                if(cursor.getCount() != 0) {
                    if(cursor.moveToFirst()){
                        do{
                            String news_url = cursor.getString(cursor.getColumnIndex("news_url"));
                            String news_title = cursor.getString(cursor.getColumnIndex("news_title"));
                            String news_date = cursor.getString(cursor.getColumnIndex("news_date"));
                            String news_author = cursor.getString(cursor.getColumnIndex("news_author"));
                            String news_picurl = cursor.getString(cursor.getColumnIndex("news_picurl"));
                            Bitmap bitmap = HttpUtils.decodeUriAsBitmapFromNet(news_picurl);
                            News news = new News(bitmap,news_title,news_url,news_picurl,news_date,news_author);
                            newsList.add(news);

                        }while (cursor.moveToNext());
                    }
                }
                else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"收藏夹为空！",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       newsAdapter.notifyDataSetChanged();
                   }
               });

                cursor.close();
                db.close();
            }
        }).start();
    }

    private void initView(){
        collection = findViewById(R.id.list_item_collection);
        newsAdapter = new NewsAdapter(this,R.layout.news_item,newsList,this);
    }

    @Override
    public void click(View view) {
        int position = Integer.parseInt(view.getTag().toString());
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        db.execSQL("delete from Collection_News where news_title=?",
                new String[]{newsList.get(position).getNews_title()});
        db.close();
        newsList.remove(position);
        newsAdapter.notifyDataSetChanged();
        Toast.makeText(this, "该新闻已被移除收藏夹！", Toast.LENGTH_SHORT).show();
    }
}
