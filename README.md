# TXT阅读器

## 一. 实验内容

#### 1.项目简介
随着手机智能化的普及，人们的智能化生活得到了诸多便利，与人们生活嘻嘻相关的各类app应用应运而生，正如大家所说的那样“手机在手，应有尽有”。目前纸质图书阅读不方便，需随身携带书籍，而电子图书只需使用必备的手机即可实现随时随地地阅读，满足人们对于阅读的需求，拓宽人们阅读渠道，也给人们的生活带来的便利性。

**[apk下载](https://github.com/Sugarcandy/ReaderApp/blob/master/apk/app-debug.apk)** <br>

#### 2.目前已实现功能及截图
[1.欢迎页面](#1.欢迎界面)

![Image text](https://raw.githubusercontent.com/Sugarcandy/ReaderApp/master/img/3.1.png)

[2.书架页面](#2.书架页面)

（1）增加图书

![Image text](https://raw.githubusercontent.com/Sugarcandy/ReaderApp/master/img/3.2.1.png)

（2）图书重命名

![Image text](https://raw.githubusercontent.com/Sugarcandy/ReaderApp/master/img/3.2.2.png)

（3）移出单本图书

![Image text](https://raw.githubusercontent.com/Sugarcandy/ReaderApp/master/img/3.2.3.png)

（4）移出所有图书

![Image text](https://raw.githubusercontent.com/Sugarcandy/ReaderApp/master/img/3.2.4.png)

（5）书架图书查询

![Image text](https://raw.githubusercontent.com/Sugarcandy/ReaderApp/master/img/3.2.5.png)

[3.阅读页面](#3.阅读页面)

（1）目录功能

![Image text](https://raw.githubusercontent.com/Sugarcandy/ReaderApp/master/img/3.3.1.png)

（2）字体设置与调节。包括大小、是否加粗。

![Image text](https://raw.githubusercontent.com/Sugarcandy/ReaderApp/master/img/3.3.2.png)

（3）页面风格设置，夜间模式等。

![Image text](https://raw.githubusercontent.com/Sugarcandy/ReaderApp/master/img/3.3.3.png)

（4）进度跳转与当前进度获取。

![Image text](https://raw.githubusercontent.com/Sugarcandy/ReaderApp/master/img/3.3.4.png)

（5）章节获取与章节跳转。

![Image text](https://raw.githubusercontent.com/Sugarcandy/ReaderApp/master/img/3.3.5.png)

（6）可以滑盖翻页与平移翻页切换，支持轻击翻页。

![Image text](https://raw.githubusercontent.com/Sugarcandy/ReaderApp/master/img/3.3.6.png)

（7）可以进行长按滑动选择复制文字。

![Image text](https://raw.githubusercontent.com/Sugarcandy/ReaderApp/master/img/3.3.7.png)

（8）自动跳转到上次阅读进度。

![Image text](https://raw.githubusercontent.com/Sugarcandy/ReaderApp/master/img/3.3.7.png)

[4.关于我们](#4.关于我们)

![Image text](https://raw.githubusercontent.com/Sugarcandy/ReaderApp/master/img/3.4.png)

## 二. 关键代码
###### <a id="1.欢迎界面">1.欢迎页面</a>
  (1)activity_welcome.xml

    <?xml version="1.0" encoding="utf-8"?>
    <com.monke.immerselayout.ImmerseFrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="#30000000"
        app:need_immerse="true">
    
        <ImageView
            android:id="@+id/imageView"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:scaleType="fitXY" />
    
        <com.monke.immerselayout.ImmerseFrameLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="vertical"
            app:need_immerse="false">
    
            <LinearLayout
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_gravity="center"
                android:layout_marginBottom="60dp"
                android:orientation="vertical">
    
                <FrameLayout
                    android:layout_width="180dp"
                    android:layout_height="31dp"
                    android:layout_gravity="center_horizontal">
    
                    <ImageView
                        android:id="@+id/logo"
                        android:layout_width="match_parent"
                        android:layout_height="match_parent"
                        android:scaleType="fitXY"
                        android:src="@drawable/logo"
                        android:transitionName="logo" />
                </FrameLayout>
            </LinearLayout>
        </com.monke.immerselayout.ImmerseFrameLayout>
    
    </com.monke.immerselayout.ImmerseFrameLayout>
  
  （2）WelcomeActivity.java
  
    package hw.txtreader.activity;
    
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.os.Handler;
    import android.os.Message;
    import android.preference.PreferenceManager;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.view.Window;
    import android.view.WindowManager;
    import android.widget.ImageView;
    import com.bumptech.glide.Glide;
    import java.io.IOException;
    import hw.txtreader.R;
    import hw.txtreader.util.HttpUtil;
    import okhttp3.Call;
    import okhttp3.Callback;
    import okhttp3.Response;
    
    public class WelcomeActivity extends AppCompatActivity{
    
        private ImageView imageView;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
    
            //隐藏标题栏以及状态栏
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_welcome);
            imageView=findViewById(R.id.imageView);
            SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
            String bingPic=preferences.getString("bing_Pic",null);
            if(bingPic!=null){
                Glide.with(this).load(bingPic).into(imageView);
            }else {
                loadBingPic();
            }
            handler.sendEmptyMessageDelayed(0,3000);//延迟三秒进入主界面
        }
    
        private void loadBingPic(){//加载Bing每日一图
            String requestBingPic="http://guolin.tech/api/bing_pic";
            HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }
    
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String bingPic=response.body().string();
                    SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(WelcomeActivity.this).edit();
                    editor.putString("bing_pic",bingPic);
                    editor.apply();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(WelcomeActivity.this).load(bingPic).into(imageView);
                        }
                    });
                }
            });
        }
        private Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                getHome();
                super.handleMessage(msg);
            }
        };
    
        public void getHome(){//跳转页面
            Intent intent = new Intent(WelcomeActivity.this, BookShelfActivity.class);
            startActivity(intent);
            finish();
        }
    }

  ###### <a id="2.书架页面">2.书架页面</a>
  
  (1)BookShelfActivity.java(在WelcomeActivity中跳转页面到BookShelfActivity)
  
    package hw.txtreader.activity;
    
    import android.Manifest;
    import android.app.Activity;
    import android.app.AlertDialog;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.content.pm.PackageManager;
    import android.database.Cursor;
    import android.net.Uri;
    import android.provider.MediaStore;
    import android.support.design.widget.FloatingActionButton;
    import android.support.v4.app.ActivityCompat;
    import android.support.v4.content.ContextCompat;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.support.v7.widget.SearchView;
    import android.view.ContextMenu;
    import android.view.LayoutInflater;
    import android.view.MenuItem;
    import android.view.View;
    import android.view.inputmethod.EditorInfo;
    import android.widget.AdapterView;
    import android.widget.EditText;
    import android.widget.ImageButton;
    import android.widget.ListView;
    import android.widget.SimpleAdapter;
    import android.widget.Toast;
    
    import com.bifan.txtreaderlib.main.TxtConfig;
    import com.bifan.txtreaderlib.ui.HwTxtPlayActivity;
    
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;
    
    import hw.txtreader.R;
    import hw.txtreader.bean.BookBean;
    import hw.txtreader.dao.BookBeanDao;
    import hw.txtreader.dao.DbHelper;
    
    /**
     * 书架页面
     */
    
    /**
     * 从文件中读取文件并将他的url存到数据库
     * 然后在列表就通过url加载这个文件
     */
    public class BookShelfActivity extends AppCompatActivity {
    
        private ListView shelf;//书架
        private SimpleAdapter adapter;//列表适配器
        private List<Map<String, String>> bookBeanList = new ArrayList<>();//书籍列表
        private FloatingActionButton addBook;//从本地导入书籍
        private Boolean Permit = false;
        private final BookBeanDao bookBeanDao = DbHelper.getInstance().getmDaoSession().getBookBeanDao();
        private SearchView search;
        private ImageButton setting;//关于我们
    
        private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 0x01;
    
        String[] target = {
                "name",
                "introduce",
                "author"
        };//参数名
    
        int[] xmlpath = {
                R.id.title,
    //      R.id.describe,
    //      R.id.author
        };
    
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_header);
            if (CheckPermission()) {//获取文件操作权限
                Permit = true;
            }
    
            shelf = findViewById(R.id.shelf);
    //        registerForContextMenu(shelf);
            search = findViewById(R.id.search);
            search.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
            search.setIconified(false);
            addBook = findViewById(R.id.ib_add);
            addBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chooseFile(null);//添加书籍
                }
            });
    
            getdata();//获取列表信息
            shelf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String path = bookBeanList.get(position).get("url");
                    TxtConfig.saveIsOnVerticalPageMode(BookShelfActivity.this, false);
                    HwTxtPlayActivity.loadTxtFile(BookShelfActivity.this, path);
                }
            });
            setting = findViewById(R.id.setting);
            setting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(BookShelfActivity.this, AboutActivity.class);
                    startActivity(intent);
                }
            });
            LongClick();//长按item弹出菜单
    
            Search();//搜索
    
        }
    
        public void getdata() {
            List<BookBean> list = bookBeanDao.loadAll();//获取所有书籍
    
            bookBeanList.clear();//先清理掉
            if (list.size() > 0) {
                for (BookBean bookBean : list) {//添加数据库的所有书籍
                    Map<String, String> map = new HashMap<>();
                    map.put("name", bookBean.getName());
    //                map.put("introduce", bookBean.getIntroduce());
    //                map.put("author", bookBean.getAuthor());
                    map.put("url", bookBean.getNoteUrl());
                    bookBeanList.add(map);
                }
                adapter = new SimpleAdapter(BookShelfActivity.this, bookBeanList, R.layout.activity_book_shelf_item, target, xmlpath);
                shelf.setAdapter(adapter);
            } else {
                toast("当前没有书籍！");
            }
        }
    
        public void chooseFile(View view) {//从本机选择文件
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("text/plain");//设置类型
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(intent, 3);
        }
    
        private Boolean CheckPermission() {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
    
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                return false;
            }
            return true;
        }
    
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (resultCode == Activity.RESULT_OK) {//是否选择，没选择就不会继续
                Uri uri = data.getData();//得到uri，后面就是将uri转化成file的过程。
                String[] pros = {MediaStore.Files.FileColumns.DATA};
                try {
                    Cursor cursor = managedQuery(uri, pros, null, null, null);
                    int actual_txt_column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    String path = cursor.getString(actual_txt_column_index);
                    //保存书籍的path
                    //更新数据库
                    BookBean bookBean = new BookBean();
                    bookBean.setNoteUrl(path);
                    bookBean.setName(path.trim().substring(path.lastIndexOf("/") + 1));
                    bookBean.setChapterUrl(path);
    //                bookBean.setAuthor("唐家三少");
    //                bookBean.setIntroduce("这是一片斗气的世界，这里没有绚丽的魔法，有的只有强横的斗气...");
                    long res = bookBeanDao.insertOrReplace(bookBean);//添加到数据库
                    if (res > 0) {
                        getdata();
                        toast("添加成功！");
                    } else
                        toast("添加失败！");
                } catch (Exception e) {
                    toast("选择出错了!");
                }
            }
        }
    
    
        public void LongClick() {//长按菜单操作
            shelf.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                @Override
                public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                    menu.add(0, 0, 0, "重命名");
                    menu.add(0, 1, 0, "移出书架");
                    menu.add(0, 2, 0, "移出所有");
                }
            });
        }
    
        @Override
        public boolean onContextItemSelected(MenuItem item) {
            //获取当前
            final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                    .getMenuInfo();
            String path = null;
            BookBean bookBean = null;
            AlertDialog.Builder alertDialog = null;
            switch (item.getItemId()) {
                case 0:
                    //重命名
                    alertDialog = new AlertDialog.Builder(BookShelfActivity.this);
                    final View layout = LayoutInflater.from(this).inflate(R.layout.activity_alert, shelf, false);
                    alertDialog.setView(layout);
                    alertDialog.setTitle("请输入修改后的书名");
                    alertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText text = (EditText) layout;
                            String name = text.getText().toString();
                            if (name != null && !"".equals(name)) {
                                String path = bookBeanList.get(info.position).get("url");
                                String author = bookBeanList.get(info.position).get("author");
                                String desc = bookBeanList.get(info.position).get("introduce");
                                BookBean bookBean = new BookBean();
                                bookBean.setNoteUrl(path);
                                bookBean.setName(name);
                                bookBean.setAuthor(author);
                                bookBean.setIntroduce(desc);
                                bookBeanDao.update(bookBean);
                                adapter.notifyDataSetChanged();
                                getdata();//刷新页面
                            } else {
                                toast("输入为空");
                            }
                        }
                    }).setNegativeButton("取消", null);
                    alertDialog.show();//显示alertDialog
                    break;
                case 1:
                    //移出书架
                    path = bookBeanList.get(info.position).get("url");
                    bookBean = new BookBean();
                    bookBean.setNoteUrl(path);
                    bookBeanDao.delete(bookBean);
                    toast("删除成功！");
                    shelf.removeAllViewsInLayout();//清空所有view
                    getdata();//重新获取数据
                    break;
                case 2:
                    //移出全部
                    bookBeanDao.deleteAll();
                    toast("删除成功！");
                    shelf.removeAllViewsInLayout();//清空所有view
                    getdata();//刷新
                    break;
            }
            return super.onContextItemSelected(item);
        }
    
        public void Search() {
            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }
    
                @Override
                public boolean onQueryTextChange(String newText) {
                    if (newText != "") {
                        String name = "%" + newText + "%";
                        List<BookBean> list = bookBeanDao.queryBuilder().where(BookBeanDao.Properties.Name.like(name)).list();
                        bookBeanList.clear();//先清理掉
                        if (list.size() > 0) {
                            for (BookBean bookBean : list) {//添加数据库的所有书籍
                                Map<String, String> map = new HashMap<>();
                                map.put("name", bookBean.getName());
    //                            map.put("introduce", bookBean.getIntroduce());
    //                            map.put("author", bookBean.getAuthor());
                                map.put("url", bookBean.getNoteUrl());
                                bookBeanList.add(map);
                            }
                            shelf.removeAllViewsInLayout();//清空所有view
    
                            adapter = new SimpleAdapter(BookShelfActivity.this, bookBeanList, R.layout.activity_book_shelf_item, target, xmlpath);
                            shelf.setAdapter(adapter);
                        } else {
                            toast("没有符合搜索条件的书籍");
                        }
                        return true;
                    }
                    return false;
                }
            });
        }
    
        private Toast t;
    
        private void toast(String msg) {
            if (t != null) {
                t.cancel();
            }
            t = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
            t.show();
        }
    }

(2)activity_header.xml（书架页面，包含图标和ListView）

    <?xml version="1.0" encoding="utf-8"?>
    <com.monke.immerselayout.ImmerseFrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:tools="http://schemas.android.com/tools"
        android:id="@+id/shelfLayout"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        tools:context=".activity.BookShelfActivity">
    
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:background="@drawable/back3"
            android:orientation="vertical">
    
            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="48dp"
                android:orientation="horizontal">
    
                <ImageView
                    android:id="@+id/iv_logo"
                    android:layout_width="119dp"
                    android:layout_height="40dp"
                    android:layout_gravity="center_vertical"
                    android:layout_marginLeft="15dp"
                    android:src="@drawable/logo" />
    
                <View
                    android:layout_width="0dp"
                    android:layout_height="0dp"
                    android:layout_weight="1" />
    
                <ImageButton
                    android:id="@+id/setting"
                    android:layout_width="40dp"
                    android:layout_height="40dp"
                    android:layout_gravity="center_vertical"
                    android:layout_marginRight="5dp"
                    android:background="@drawable/bg_ib_pre"
                    android:paddingTop="10dp"
                    android:paddingBottom="10dp"
                    android:scaleType="fitCenter"
                    android:src="@drawable/about" />
    
            </LinearLayout>
    
            <android.support.v7.widget.SearchView
                android:id="@+id/search"
                android:layout_width="match_parent"
                android:layout_height="wrap_content" />
    
            <ListView
                android:id="@+id/shelf"
                android:layout_width="match_parent"
                android:layout_height="wrap_content" />
    
        </LinearLayout>
    
        <android.support.design.widget.FloatingActionButton
            android:id="@+id/ib_add"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="bottom|end"
            android:layout_marginRight="16dp"
            android:layout_marginBottom="50dp"
            android:clickable="true"
            android:src="@drawable/selector_iv_add"
            app:backgroundTint="#767676"
            app:fabSize="auto" />
    </com.monke.immerselayout.ImmerseFrameLayout>
    
(3)activity_alert.xml（重命名页面）

    <?xml version="1.0" encoding="utf-8"?>
    <EditText xmlns:android="http://schemas.android.com/apk/res/android"
        android:id="@+id/name"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="请输入新的书名" />

(4)activity_book_shelf_item.xml（每本书的item）

    <?xml version="1.0" encoding="utf-8"?>
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="#fff2ff"
        android:orientation="vertical"
        tools:context=".activity.BookShelfActivity">
    
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="87dp"
            android:layout_marginTop="10dp">
    
            <android.support.v7.widget.AppCompatImageView
                android:layout_width="87dp"
                android:layout_height="87dp"
                android:src="@drawable/reader" />
    
            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="87dp"
                android:layout_marginLeft="20dp"
                android:orientation="vertical">
    
                <TextView
                    android:id="@+id/title"
                    android:layout_width="match_parent"
                    android:layout_height="30dp"
                    android:layout_marginTop="27dp"
                    android:singleLine="true"
                    android:textSize="20dp" />
            </LinearLayout>
        </LinearLayout>
    
        <View
            android:layout_width="match_parent"
            android:layout_height="5dp"
            android:layout_gravity="center" />
    </LinearLayout>
 
###### <a id="3.阅读页面">3.阅读页面</a>

(1)在BookShelfActivity.java设置单击事件

     shelf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String path = bookBeanList.get(position).get("url");
                    TxtConfig.saveIsOnVerticalPageMode(BookShelfActivity.this, false);
                    HwTxtPlayActivity.loadTxtFile(BookShelfActivity.this, path);
                }
            });
(2)HwTxtPlayActivity.java

    package com.bifan.txtreaderlib.ui;
    
    import android.content.ClipboardManager;
    import android.content.Context;
    import android.content.Intent;
    import android.database.Cursor;
    import android.graphics.Color;
    import android.net.Uri;
    import android.os.Bundle;
    import android.os.Handler;
    import android.provider.MediaStore;
    import android.support.annotation.Nullable;
    import android.support.v4.content.ContextCompat;
    import android.support.v7.app.AppCompatActivity;
    import android.text.TextUtils;
    import android.util.DisplayMetrics;
    import android.view.MotionEvent;
    import android.view.View;
    import android.view.WindowManager;
    import android.widget.AdapterView;
    import android.widget.SeekBar;
    import android.widget.TextView;
    import android.widget.Toast;
    
    import com.bifan.txtreaderlib.R;
    import com.bifan.txtreaderlib.bean.TxtChar;
    import com.bifan.txtreaderlib.bean.TxtMsg;
    import com.bifan.txtreaderlib.interfaces.ICenterAreaClickListener;
    import com.bifan.txtreaderlib.interfaces.IChapter;
    import com.bifan.txtreaderlib.interfaces.ILoadListener;
    import com.bifan.txtreaderlib.interfaces.IPageChangeListener;
    import com.bifan.txtreaderlib.interfaces.ISliderListener;
    import com.bifan.txtreaderlib.interfaces.ITextSelectListener;
    import com.bifan.txtreaderlib.main.TxtConfig;
    import com.bifan.txtreaderlib.main.TxtReaderView;
    import com.bifan.txtreaderlib.utils.ELogger;
    
    import java.io.File;
    
    public class HwTxtPlayActivity extends AppCompatActivity {
        protected Handler mHandler;
        protected boolean FileExist = false;
    
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(getContentViewLayout());
            FileExist = getIntentData();
            init();
            loadFile();
            registerListener();
    
        }
    
        protected int getContentViewLayout() {
            return R.layout.activity_hwtxtpaly;
        }
    
        protected boolean getIntentData() {
            // Get the intent that started this activity
            Uri uri = getIntent().getData();
            if (uri != null) {
                ELogger.log("getIntentData", "" + uri);
            } else {
                ELogger.log("getIntentData", "uri is null");
            }
            if (uri != null) {
                try {
                    String path = getRealPathFromUri(uri);
                    if (!TextUtils.isEmpty(path)) {
                        if (path.contains("/storage/")) {
                            path = path.substring(path.indexOf("/storage/"));
                        }
                        ELogger.log("getIntentData", "path:" + path);
                        File file = new File(path);
                        if (file.exists()) {
                            FilePath = path;
                            FileName = file.getName();
                            return true;
                        } else {
                            toast("文件不存在");
                            return false;
                        }
                    }
                    return false;
                } catch (Exception e) {
                    toast("文件出错了");
                }
            }
    
            FilePath = getIntent().getStringExtra("FilePath");
            FileName = getIntent().getStringExtra("FileName");
            ContentStr = getIntent().getStringExtra("ContentStr");
            if (ContentStr == null) {
                return FilePath != null && new File(FilePath).exists();
            } else {
                return true;
            }
    
        }
    
        private String getRealPathFromUri(Uri contentUri) {
            Cursor cursor = null;
            try {
                String[] pro = {MediaStore.Files.FileColumns.DATA};
                cursor = getContentResolver().query(contentUri, pro, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);
                cursor.moveToFirst();
                return cursor.getString(column_index);
            } catch (Exception e) {
                return null;
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
    
        /**
         * @param context  上下文
         * @param FilePath 文本文件路径
         */
        public static void loadTxtFile(Context context, String FilePath) {
            loadTxtFile(context, FilePath, null);
        }
    
        /**
         * @param context 上下文
         * @param str     文本文内容
         */
        public static void loadStr(Context context, String str) {
            loadTxtStr(context, str, null);
        }
    
        /**
         * @param context  上下文
         * @param str      文本显示内容
         * @param FileName 显示的书籍或者文件名称
         */
        public static void loadTxtStr(Context context, String str, String FileName) {
            Intent intent = new Intent();
            intent.putExtra("ContentStr", str);
            intent.putExtra("FileName", FileName);
            intent.setClass(context, HwTxtPlayActivity.class);
            context.startActivity(intent);
        }
    
        /**
         * @param context  上下文
         * @param FilePath 文本文件路径
         * @param FileName 显示的书籍或者文件名称
         */
        public static void loadTxtFile(Context context, String FilePath, String FileName) {
            Intent intent = new Intent();
            intent.putExtra("FilePath", FilePath);
            intent.putExtra("FileName", FileName);
            intent.setClass(context, HwTxtPlayActivity.class);
            context.startActivity(intent);
        }
    
        protected View mTopDecoration, mBottomDecoration;
        protected View mChapterMsgView;
        protected TextView mChapterMsgName;
        protected TextView mChapterMsgProgress;
        protected TextView mChapterNameText;
        protected TextView mChapterMenuText;
        protected TextView mProgressText;
        protected TextView mSettingText;
        protected TextView mSelectedText;
        protected TxtReaderView mTxtReaderView;
        protected View mTopMenu;
        protected View mBottomMenu;
        protected View mCoverView;
        protected View ClipboardView;
        protected String CurrentSelectedText;
    
        protected ChapterList mChapterListPop;
        protected MenuHolder mMenuHolder = new MenuHolder();
    
        protected void init() {
            mHandler = new Handler();
            mChapterMsgView = findViewById(R.id.activity_hwtxtplay_chapter_msg);
            mChapterMsgName = (TextView) findViewById(R.id.chapter_name);
            mChapterMsgProgress = (TextView) findViewById(R.id.charpter_progress);
            mTopDecoration = findViewById(R.id.activity_hwtxtplay_top);
            mBottomDecoration = findViewById(R.id.activity_hwtxtplay_bottom);
            mTxtReaderView = (TxtReaderView) findViewById(R.id.activity_hwtxtplay_readerView);
            mChapterNameText = (TextView) findViewById(R.id.activity_hwtxtplay_chaptername);
            mChapterMenuText = (TextView) findViewById(R.id.activity_hwtxtplay_chapter_menutext);
            mProgressText = (TextView) findViewById(R.id.activity_hwtxtplay_progress_text);
            mSettingText = (TextView) findViewById(R.id.activity_hwtxtplay_setting_text);
            mTopMenu = findViewById(R.id.activity_hwtxtplay_menu_top);
            mBottomMenu = findViewById(R.id.activity_hwtxtplay_menu_bottom);
            mCoverView = findViewById(R.id.activity_hwtxtplay_cover);
            ClipboardView = findViewById(R.id.activity_hwtxtplay_Clipboar);
            mSelectedText = (TextView) findViewById(R.id.activity_hwtxtplay_selected_text);
    
            mMenuHolder.mTitle = (TextView) findViewById(R.id.txtreadr_menu_title);
            mMenuHolder.mPreChapter = (TextView) findViewById(R.id.txtreadr_menu_chapter_pre);
            mMenuHolder.mNextChapter = (TextView) findViewById(R.id.txtreadr_menu_chapter_next);
            mMenuHolder.mSeekBar = (SeekBar) findViewById(R.id.txtreadr_menu_seekbar);
            mMenuHolder.mTextSizeDel = findViewById(R.id.txtreadr_menu_textsize_del);
            mMenuHolder.mTextSize = (TextView) findViewById(R.id.txtreadr_menu_textsize);
            mMenuHolder.mTextSizeAdd = findViewById(R.id.txtreadr_menu_textsize_add);
            mMenuHolder.mBoldSelectedLayout = findViewById(R.id.txtreadr_menu_textsetting1_bold);
            mMenuHolder.mNormalSelectedLayout = findViewById(R.id.txtreadr_menu_textsetting1_normal);
            mMenuHolder.mCoverSelectedLayout = findViewById(R.id.txtreadr_menu_textsetting2_cover);
            mMenuHolder.mTranslateSelectedLayout = findViewById(R.id.txtreadr_menu_textsetting2_translate);
    
            mMenuHolder.mStyle1 = findViewById(R.id.hwtxtreader_menu_style1);
            mMenuHolder.mStyle2 = findViewById(R.id.hwtxtreader_menu_style2);
            mMenuHolder.mStyle3 = findViewById(R.id.hwtxtreader_menu_style3);
            mMenuHolder.mStyle4 = findViewById(R.id.hwtxtreader_menu_style4);
            mMenuHolder.mStyle5 = findViewById(R.id.hwtxtreader_menu_style5);
        }
    
        private final int[] StyleTextColors = new int[]{
                Color.parseColor("#4a453a"),
                Color.parseColor("#505550"),
                Color.parseColor("#453e33"),
                Color.parseColor("#8f8e88"),
                Color.parseColor("#27576c")
        };
    
        protected String ContentStr = null;
        protected String FilePath = null;
        protected String FileName = null;
    
        protected void loadFile() {
            TxtConfig.savePageSwitchDuration(this, 400);
            if (ContentStr == null) {
                if (TextUtils.isEmpty(FilePath) || !(new File(FilePath).exists())) {
                    toast("文件不存在");
                    return;
                }
    
            }
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //延迟加载避免闪一下的情况出现
                    if (ContentStr == null) {
                        loadOurFile();
                    } else {
                        loadStr();
                    }
                }
            }, 300);
    
    
        }
    
        protected void loadOurFile() {
            mTxtReaderView.loadTxtFile(FilePath, new ILoadListener() {
                @Override
                public void onSuccess() {
                    if (!hasExisted) {
                        onLoadDataSuccess();
                    }
                }
    
                @Override
                public void onFail(final TxtMsg txtMsg) {
                    if (!hasExisted) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onLoadDataFail(txtMsg);
                            }
                        });
                    }
    
                }
    
                @Override
                public void onMessage(String message) {
                    //加载过程信息
                }
            });
        }
    
        /**
         * @param txtMsg txtMsg
         */
        protected void onLoadDataFail(TxtMsg txtMsg) {
            //加载失败信息
            toast(txtMsg + "");
        }
    
        /**
         *
         */
        protected void onLoadDataSuccess() {
            if (TextUtils.isEmpty(FileName)) {//没有显示的名称，获取文件名显示
                FileName = mTxtReaderView.getTxtReaderContext().getFileMsg().FileName;
            }
            setBookName(FileName);
            initWhenLoadDone();
        }
    
        private void loadStr() {
            String testText = ContentStr;
            mTxtReaderView.loadText(testText, new ILoadListener() {
                @Override
                public void onSuccess() {
                    setBookName("test with str");
                    initWhenLoadDone();
                }
    
                @Override
                public void onFail(TxtMsg txtMsg) {
                    //加载失败信息
                    toast(txtMsg + "");
                }
    
                @Override
                public void onMessage(String message) {
                    //加载过程信息
                }
            });
        }
    
        protected void initWhenLoadDone() {
            if (mTxtReaderView.getTxtReaderContext().getFileMsg() != null) {
                FileName = mTxtReaderView.getTxtReaderContext().getFileMsg().FileName;
            }
            mMenuHolder.mTextSize.setText(mTxtReaderView.getTextSize() + "");
            mTopDecoration.setBackgroundColor(mTxtReaderView.getBackgroundColor());
            mBottomDecoration.setBackgroundColor(mTxtReaderView.getBackgroundColor());
            //mTxtReaderView.setLeftSlider(new MuiLeftSlider());//修改左滑动条
            //mTxtReaderView.setRightSlider(new MuiRightSlider());//修改右滑动条
            //字体初始化
            onTextSettingUi(mTxtReaderView.getTxtReaderContext().getTxtConfig().Bold);
            //翻页初始化
            onPageSwitchSettingUi(mTxtReaderView.getTxtReaderContext().getTxtConfig().SwitchByTranslate);
            //保存的翻页模式
            if (mTxtReaderView.getTxtReaderContext().getTxtConfig().SwitchByTranslate) {
                mTxtReaderView.setPageSwitchByTranslate();
            } else {
                mTxtReaderView.setPageSwitchByCover();
            }
            //章节初始化
            if (mTxtReaderView.getChapters() != null && mTxtReaderView.getChapters().size() > 0) {
                WindowManager m = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                DisplayMetrics metrics = new DisplayMetrics();
                m.getDefaultDisplay().getMetrics(metrics);
                int ViewHeight = metrics.heightPixels - mTopDecoration.getHeight();
                mChapterListPop = new ChapterList(this, ViewHeight, mTxtReaderView.getChapters(), mTxtReaderView.getTxtReaderContext().getParagraphData().getCharNum());
                mChapterListPop.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        IChapter chapter = (IChapter) mChapterListPop.getAdapter().getItem(i);
                        mChapterListPop.dismiss();
                        mTxtReaderView.loadFromProgress(chapter.getStartParagraphIndex(), 0);
                    }
                });
                mChapterListPop.setBackGroundColor(mTxtReaderView.getBackgroundColor());
            } else {
                Gone(mChapterMenuText);
            }
        }
    
        protected void registerListener() {
            mSettingText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Show(mTopMenu, mBottomMenu, mCoverView);
                }
            });
            setMenuListener();
            setSeekBarListener();
            setCenterClickListener();
            setPageChangeListener();
            setOnTextSelectListener();
            setStyleChangeListener();
            setExtraListener();
        }
    
        private void setExtraListener() {
            mMenuHolder.mPreChapter.setOnClickListener(new ChapterChangeClickListener(true));
            mMenuHolder.mNextChapter.setOnClickListener(new ChapterChangeClickListener(false));
            mMenuHolder.mTextSizeAdd.setOnClickListener(new TextChangeClickListener(true));
            mMenuHolder.mTextSizeDel.setOnClickListener(new TextChangeClickListener(false));
            mMenuHolder.mBoldSelectedLayout.setOnClickListener(new TextSettingClickListener(true));
            mMenuHolder.mNormalSelectedLayout.setOnClickListener(new TextSettingClickListener(false));
            mMenuHolder.mTranslateSelectedLayout.setOnClickListener(new SwitchSettingClickListener(true));
            mMenuHolder.mCoverSelectedLayout.setOnClickListener(new SwitchSettingClickListener(false));
        }
    
        protected void setStyleChangeListener() {
            mMenuHolder.mStyle1.setOnClickListener(new StyleChangeClickListener(ContextCompat.getColor(this, R.color.hwtxtreader_styleclor1), StyleTextColors[0]));
            mMenuHolder.mStyle2.setOnClickListener(new StyleChangeClickListener(ContextCompat.getColor(this, R.color.hwtxtreader_styleclor2), StyleTextColors[1]));
            mMenuHolder.mStyle3.setOnClickListener(new StyleChangeClickListener(ContextCompat.getColor(this, R.color.hwtxtreader_styleclor3), StyleTextColors[2]));
            mMenuHolder.mStyle4.setOnClickListener(new StyleChangeClickListener(ContextCompat.getColor(this, R.color.hwtxtreader_styleclor4), StyleTextColors[3]));
            mMenuHolder.mStyle5.setOnClickListener(new StyleChangeClickListener(ContextCompat.getColor(this, R.color.hwtxtreader_styleclor5), StyleTextColors[4]));
        }
    
        protected void setOnTextSelectListener() {
            mTxtReaderView.setOnTextSelectListener(new ITextSelectListener() {
                @Override
                public void onTextChanging(TxtChar firstSelectedChar, TxtChar lastSelectedChar) {
                    //firstSelectedChar.Top
                    //  firstSelectedChar.Bottom
                    // 这里可以根据 firstSelectedChar与lastSelectedChar的top与bottom的位置
                    //计算显示你要显示的弹窗位置，如果需要的话
                }
    
                @Override
                public void onTextChanging(String selectText) {
                    onCurrentSelectedText(selectText);
                }
    
                @Override
                public void onTextSelected(String selectText) {
                    onCurrentSelectedText(selectText);
                }
            });
    
            mTxtReaderView.setOnSliderListener(new ISliderListener() {
                @Override
                public void onShowSlider(TxtChar txtChar) {
                    //TxtChar 为当前长按选中的字符
                    // 这里可以根据 txtChar的top与bottom的位置
                    //计算显示你要显示的弹窗位置，如果需要的话
                }
    
                @Override
                public void onShowSlider(String currentSelectedText) {
                    onCurrentSelectedText(currentSelectedText);
                    Show(ClipboardView);
                }
    
                @Override
                public void onReleaseSlider() {
                    Gone(ClipboardView);
                }
            });
    
        }
    
        protected void setPageChangeListener() {
            mTxtReaderView.setPageChangeListener(new IPageChangeListener() {
                @Override
                public void onCurrentPage(float progress) {
                    int p = (int) (progress * 1000);
                    mProgressText.setText(((float) p / 10) + "%");
                    mMenuHolder.mSeekBar.setProgress((int) (progress * 100));
                    IChapter currentChapter = mTxtReaderView.getCurrentChapter();
                    if (currentChapter != null) {
                        mChapterNameText.setText((currentChapter.getTitle() + "").trim());
                    } else {
                        mChapterNameText.setText("无章节");
                    }
                }
            });
        }
    
        protected void setCenterClickListener() {
            mTxtReaderView.setOnCenterAreaClickListener(new ICenterAreaClickListener() {
                @Override
                public boolean onCenterClick(float widthPercentInView) {
                    mSettingText.performClick();
                    return true;
                }
    
                @Override
                public boolean onOutSideCenterClick(float widthPercentInView) {
                    if (mBottomMenu.getVisibility() == View.VISIBLE) {
                        mSettingText.performClick();
                        return true;
                    }
                    return false;
                }
            });
        }
    
        protected void setMenuListener() {
            mTopMenu.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;
                }
            });
            mBottomMenu.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;
                }
            });
            mCoverView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    Gone(mTopMenu, mBottomMenu, mCoverView, mChapterMsgView);
                    return true;
                }
            });
            mChapterMenuText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mChapterListPop != null) {
                        if (!mChapterListPop.isShowing()) {
                            mChapterListPop.showAsDropDown(mTopDecoration);
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    IChapter currentChapter = mTxtReaderView.getCurrentChapter();
                                    if (currentChapter != null) {
                                        mChapterListPop.setCurrentIndex(currentChapter.getIndex());
                                        mChapterListPop.notifyDataSetChanged();
                                    }
                                }
                            }, 300);
                        } else {
                            mChapterListPop.dismiss();
                        }
                    }
                }
            });
            mTopMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mChapterListPop.isShowing()) {
                        mChapterListPop.dismiss();
                    }
                }
            });
        }
    
        protected void setSeekBarListener() {
    
            mMenuHolder.mSeekBar.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        mTxtReaderView.loadFromProgress(mMenuHolder.mSeekBar.getProgress());
                        Gone(mChapterMsgView);
                    }
                    return false;
                }
            });
            mMenuHolder.mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, final int progress, boolean fromUser) {
                    if (fromUser) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                onShowChapterMsg(progress);
                            }
                        });
                    }
                }
    
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }
    
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    Gone(mChapterMsgView);
                }
            });
    
        }
    
    
        private void onShowChapterMsg(int progress) {
            if (mTxtReaderView != null && mChapterListPop != null) {
                IChapter chapter = mTxtReaderView.getChapterFromProgress(progress);
                if (chapter != null) {
                    float p = (float) chapter.getStartIndex() / (float) mChapterListPop.getAllCharNum();
                    if (p > 1) {
                        p = 1;
                    }
                    Show(mChapterMsgView);
                    mChapterMsgName.setText(chapter.getTitle());
                    mChapterMsgProgress.setText((int) (p * 100) + "%");
                }
            }
        }
    
        private void onCurrentSelectedText(String SelectedText) {
            mSelectedText.setText("选中" + (SelectedText + "").length() + "个文字");
            CurrentSelectedText = SelectedText;
        }
    
        private void onTextSettingUi(Boolean isBold) {
            if (isBold) {
                mMenuHolder.mBoldSelectedLayout.setBackgroundResource(R.drawable.shape_menu_textsetting_selected);
                mMenuHolder.mNormalSelectedLayout.setBackgroundResource(R.drawable.shape_menu_textsetting_unselected);
            } else {
                mMenuHolder.mBoldSelectedLayout.setBackgroundResource(R.drawable.shape_menu_textsetting_unselected);
                mMenuHolder.mNormalSelectedLayout.setBackgroundResource(R.drawable.shape_menu_textsetting_selected);
            }
        }
    
        private void onPageSwitchSettingUi(Boolean isTranslate) {
            if (isTranslate) {
                mMenuHolder.mTranslateSelectedLayout.setBackgroundResource(R.drawable.shape_menu_textsetting_selected);
                mMenuHolder.mCoverSelectedLayout.setBackgroundResource(R.drawable.shape_menu_textsetting_unselected);
            } else {
                mMenuHolder.mTranslateSelectedLayout.setBackgroundResource(R.drawable.shape_menu_textsetting_unselected);
                mMenuHolder.mCoverSelectedLayout.setBackgroundResource(R.drawable.shape_menu_textsetting_selected);
            }
        }
    
        private class TextSettingClickListener implements View.OnClickListener {
            private Boolean Bold;
    
            public TextSettingClickListener(Boolean bold) {
                Bold = bold;
            }
    
            @Override
            public void onClick(View view) {
                if (FileExist) {
                    mTxtReaderView.setTextBold(Bold);
                    onTextSettingUi(Bold);
                }
            }
        }
    
        private class SwitchSettingClickListener implements View.OnClickListener {
            private Boolean isSwitchTranslate;
    
            public SwitchSettingClickListener(Boolean pre) {
                isSwitchTranslate = pre;
            }
    
            @Override
            public void onClick(View view) {
                if (FileExist) {
                    if (!isSwitchTranslate) {
                        mTxtReaderView.setPageSwitchByCover();
                    } else {
                        mTxtReaderView.setPageSwitchByTranslate();
                    }
                    onPageSwitchSettingUi(isSwitchTranslate);
                }
            }
        }
    
    
        private class ChapterChangeClickListener implements View.OnClickListener {
            private Boolean Pre;
    
            public ChapterChangeClickListener(Boolean pre) {
                Pre = pre;
            }
    
            @Override
            public void onClick(View view) {
                if (Pre) {
                    mTxtReaderView.jumpToPreChapter();
                } else {
                    mTxtReaderView.jumpToNextChapter();
                }
            }
        }
    
        private class TextChangeClickListener implements View.OnClickListener {
            private Boolean Add;
    
            public TextChangeClickListener(Boolean pre) {
                Add = pre;
            }
    
            @Override
            public void onClick(View view) {
                if (FileExist) {
                    int textSize = mTxtReaderView.getTextSize();
                    if (Add) {
                        if (textSize + 2 <= TxtConfig.MAX_TEXT_SIZE) {
                            mTxtReaderView.setTextSize(textSize + 2);
                            mMenuHolder.mTextSize.setText(textSize + 2 + "");
                        }
                    } else {
                        if (textSize - 2 >= TxtConfig.MIN_TEXT_SIZE) {
                            mTxtReaderView.setTextSize(textSize - 2);
                            mMenuHolder.mTextSize.setText(textSize - 2 + "");
                        }
                    }
                }
            }
        }
    
        private class StyleChangeClickListener implements View.OnClickListener {
            private int BgColor;
            private int TextColor;
    
            public StyleChangeClickListener(int bgColor, int textColor) {
                BgColor = bgColor;
                TextColor = textColor;
            }
    
            @Override
            public void onClick(View view) {
                if (FileExist) {
                    mTxtReaderView.setStyle(BgColor, TextColor);
                    mTopDecoration.setBackgroundColor(BgColor);
                    mBottomDecoration.setBackgroundColor(BgColor);
                    if (mChapterListPop != null) {
                        mChapterListPop.setBackGroundColor(BgColor);
                    }
                }
            }
        }
    
        protected void setBookName(String name) {
            mMenuHolder.mTitle.setText(name + "");
        }
    
        protected void Show(View... views) {
            for (View v : views) {
                v.setVisibility(View.VISIBLE);
            }
        }
    
        protected void Gone(View... views) {
            for (View v : views) {
                v.setVisibility(View.GONE);
            }
        }
    
    
        private Toast t;
    
        protected void toast(final String msg) {
            if (t != null) {
                t.cancel();
            }
            t = Toast.makeText(HwTxtPlayActivity.this, msg, Toast.LENGTH_SHORT);
            t.show();
        }
    
        protected class MenuHolder {
            public TextView mTitle;
            public TextView mPreChapter;
            public TextView mNextChapter;
            public SeekBar mSeekBar;
            public View mTextSizeDel;
            public View mTextSizeAdd;
            public TextView mTextSize;
            public View mBoldSelectedLayout;
            public View mNormalSelectedLayout;
            public View mCoverSelectedLayout;
            public View mTranslateSelectedLayout;
            public View mStyle1;
            public View mStyle2;
            public View mStyle3;
            public View mStyle4;
            public View mStyle5;
        }
    
        @Override
        protected void onDestroy() {
            super.onDestroy();
            exist();
        }
    
        public void BackClick(View view) {
            finish();
        }
    
        public void onCopyText(View view) {
            if (!TextUtils.isEmpty(CurrentSelectedText)) {
                toast("已经复制到粘贴板");
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(CurrentSelectedText + "");
            }
            onCurrentSelectedText("");
            mTxtReaderView.releaseSelectedState();
            Gone(ClipboardView);
        }
    
        @Override
        public void onBackPressed() {
            super.onBackPressed();
            finish();
        }
    
        @Override
        public void finish() {
            super.finish();
            exist();
        }
    
        protected boolean hasExisted = false;
    
        protected void exist() {
            if (!hasExisted) {
                ContentStr = null;
                hasExisted = true;
                if (mTxtReaderView != null) {
                    mTxtReaderView.saveCurrentProgress();
                }
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mTxtReaderView != null) {
                            mTxtReaderView.getTxtReaderContext().Clear();
                            mTxtReaderView = null;
                        }
                        if (mHandler != null) {
                            mHandler.removeCallbacksAndMessages(null);
                            mHandler = null;
                        }
                        if (mChapterListPop != null) {
                            if (mChapterListPop.isShowing()) {
                                mChapterListPop.dismiss();
                            }
                            mChapterListPop.onDestroy();
                            mChapterListPop = null;
                        }
                        mMenuHolder = null;
                    }
                }, 300);
    
            }
        }
    }

(3)activity_hwtxtplay.xml

    <?xml version="1.0" encoding="utf-8"?>
    <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
    
        <RelativeLayout
            android:id="@+id/activity_hwtxtplay_top"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="#ffffff"
            >
    
            <TextView
                android:id="@+id/activity_hwtxtplay_chaptername"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginLeft="10dp"
                android:layout_marginRight="5dp"
                android:layout_toLeftOf="@+id/activity_hwtxtplay_chpatermenu"
                android:ellipsize="end"
                android:maxLines="1"
                android:paddingBottom="10dp"
                android:paddingTop="10dp"
                android:text="第一章"
                android:textColor="#666666" />
    
            <LinearLayout
                android:id="@+id/activity_hwtxtplay_chpatermenu"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_alignParentRight="true"
                android:layout_centerVertical="true"
                android:clickable="true"
                android:orientation="horizontal">
    
                <View
    
                    android:layout_width="7dp"
                    android:layout_height="7dp"
                    android:layout_gravity="center_vertical"
                    android:layout_marginRight="5dp"
                    android:layout_toLeftOf="@+id/activity_hwtxtplay_chapter_menutext"
                    android:background="@drawable/shape_little_ball" />
    
                <TextView
                    android:id="@+id/activity_hwtxtplay_chapter_menutext"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_gravity="center_vertical"
                    android:paddingRight="10dp"
                    android:paddingBottom="10dp"
                    android:paddingTop="10dp"
                    android:clickable="true"
                    android:text="章节"
                    android:textColor="#858582" />
            </LinearLayout>
    
    
        </RelativeLayout>
    
        <com.bifan.txtreaderlib.main.TxtReaderView
            android:id="@+id/activity_hwtxtplay_readerView"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout_above="@+id/activity_hwtxtplay_bottom"
            android:layout_below="@+id/activity_hwtxtplay_top"
            android:background="#ffffff" />
    
        <RelativeLayout
            android:id="@+id/activity_hwtxtplay_bottom"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_alignParentBottom="true"
            android:background="#ffffff"
            android:paddingTop="5dp">
    
            <TextView
                android:id="@+id/activity_hwtxtplay_progress_text"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginLeft="10dp"
                android:text="0%"
                android:textColor="#666666" />
    
            <TextView
                android:id="@+id/activity_hwtxtplay_setting_text"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_alignParentRight="true"
                android:layout_marginRight="5dp"
                android:clickable="true"
                android:paddingBottom="5dp"
                android:paddingTop="5dp"
                android:text="设置"
                android:textColor="#858582" />
        </RelativeLayout>
    
    
        <RelativeLayout
            android:id="@+id/activity_hwtxtplay_Clipboar"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_alignParentBottom="true"
            android:background="#000000"
            android:visibility="gone">
    
            <View
                android:layout_width="match_parent"
                android:layout_height="1px"
                android:background="#666666" />
    
            <TextView
                android:id="@+id/activity_hwtxtplay_selected_text"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginLeft="10dp"
                android:paddingBottom="10dp"
                android:paddingTop="10dp"
                android:text="选中0个字"
                android:textColor="#aaffffff" />
    
            <TextView
                android:id="@+id/activity_hwtxtplay_Clipboar_click"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_alignParentRight="true"
                android:layout_marginRight="5dp"
                android:clickable="true"
                android:onClick="onCopyText"
                android:paddingBottom="10dp"
                android:paddingTop="10dp"
                android:text="复制"
                android:textColor="#ffffff" />
        </RelativeLayout>
    
    
        <View
            android:id="@+id/activity_hwtxtplay_cover"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout_above="@+id/activity_hwtxtplay_menu_bottom"
            android:layout_below="@+id/activity_hwtxtplay_menu_top"
            android:background="#00000000"
            android:clickable="true"
            android:focusable="true"
            android:visibility="gone" />
    
        <include
            android:id="@+id/activity_hwtxtplay_menu_top"
            layout="@layout/view_menu_top"
            android:clickable="true"
            android:focusable="true"
            android:visibility="gone" />
    
        <include
            android:id="@+id/activity_hwtxtplay_menu_bottom"
            layout="@layout/view_menu_bottom"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_alignParentBottom="true"
            android:clickable="true"
            android:focusable="true"
            android:visibility="gone" />
        <include
            android:id="@+id/activity_hwtxtplay_chapter_msg"
            layout="@layout/view_charpter_msg"
            android:layout_width="250dp"
            android:layout_height="80dp"
            android:layout_centerInParent="true"
            android:visibility="gone" />
    </RelativeLayout>

(4)view_menu_buttom.xml(设置框,单击屏幕中央或设置按钮时显示)

    <?xml version="1.0" encoding="utf-8"?>
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:background="#000000"
        android:orientation="vertical"
        android:padding="10dp">
    
        <RelativeLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content">
    
            <TextView
                android:id="@+id/txtreadr_menu_chapter_pre"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_centerVertical="true"
                android:text="上一章"
                android:textColor="@color/hwtxtreader_bottom_menu_textclor"
                android:textSize="@dimen/hwtxtreader_menu_textsize_normal" />
    
            <SeekBar
                android:id="@+id/txtreadr_menu_seekbar"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_centerVertical="true"
                android:layout_gravity="center_vertical"
                android:layout_toLeftOf="@+id/txtreadr_menu_chapter_next"
                android:layout_toRightOf="@+id/txtreadr_menu_chapter_pre"
                android:layout_weight="1"
                android:maxHeight="3dip"
                android:minHeight="3dip"
                android:progress="30"
                android:progressDrawable="@drawable/txtview_po_seekbar"
                android:thumb="@drawable/shape_seekbar_thumb"
                android:thumbOffset="0dip" />
    
            <TextView
                android:id="@+id/txtreadr_menu_chapter_next"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_alignParentRight="true"
                android:layout_centerVertical="true"
                android:text="下一章"
                android:textColor="@color/hwtxtreader_bottom_menu_textclor"
                android:textSize="@dimen/hwtxtreader_menu_textsize_normal" />
        </RelativeLayout>
    
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="15dp"
            android:orientation="horizontal"
            android:weightSum="6">
    
            <LinearLayout
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_gravity="center_vertical"
                android:layout_marginLeft="50px"
                android:layout_weight="2"
                android:orientation="horizontal">
    
                <RelativeLayout
                    android:id="@+id/txtreadr_menu_textsize_del"
                    android:layout_width="0dp"
                    android:layout_height="wrap_content"
                    android:layout_centerVertical="true"
                    android:layout_weight="1">
    
                    <ImageView
                        android:layout_width="20dp"
                        android:layout_height="20dp"
                        android:layout_centerInParent="true"
                        android:scaleType="centerInside"
                        android:src="@drawable/ic_key_del" />
                </RelativeLayout>
    
                <TextView
                    android:id="@+id/txtreadr_menu_textsize"
                    android:layout_width="0dp"
                    android:layout_height="wrap_content"
                    android:layout_centerVertical="true"
                    android:layout_weight="1"
                    android:gravity="center"
                    android:text="25"
                    android:textColor="@color/hwtxtreader_bottom_menu_textclor"
                    android:textSize="@dimen/hwtxtreader_menu_textsize_max" />
    
                <RelativeLayout
                    android:id="@+id/txtreadr_menu_textsize_add"
                    android:layout_width="0dp"
                    android:layout_height="wrap_content"
                    android:layout_centerVertical="true"
                    android:layout_weight="1">
    
                    <ImageView
                        android:layout_width="20dp"
                        android:layout_height="20dp"
                        android:layout_centerInParent="true"
                        android:scaleType="centerInside"
                        android:src="@drawable/ic_key_add" />
                </RelativeLayout>
            </LinearLayout>
    
            <RelativeLayout
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_weight="4">
    
                <LinearLayout
                    android:id="@+id/txtreadr_menu_textsetting1"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_alignParentRight="true"
                    android:layout_marginRight="20dp"
                    android:orientation="horizontal">
    
                    <LinearLayout
                        android:id="@+id/txtreadr_menu_textsetting1_bold"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:orientation="horizontal"
                        android:paddingLeft="@dimen/hwtxtreader_menu_textsetting_padding_leftright"
                        android:paddingTop="@dimen/hwtxtreader_menu_textsetting_padding_topbottom"
                        android:paddingRight="@dimen/hwtxtreader_menu_textsetting_padding_leftright"
                        android:paddingBottom="@dimen/hwtxtreader_menu_textsetting_padding_topbottom">
    
                        <TextView
                            android:layout_width="0dp"
                            android:layout_height="wrap_content"
                            android:layout_gravity="center_vertical"
                            android:layout_weight="1"
                            android:gravity="center|top"
                            android:text="加粗"
                            android:textColor="@color/hwtxtreader_bottom_menu_textclor"
                            android:textSize="@dimen/hwtxtreader_menu_textsize_max" />
    
                    </LinearLayout>
    
                    <LinearLayout
                        android:id="@+id/txtreadr_menu_textsetting1_normal"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:layout_marginLeft="30dp"
                        android:orientation="horizontal"
                        android:paddingLeft="@dimen/hwtxtreader_menu_textsetting_padding_leftright"
                        android:paddingTop="@dimen/hwtxtreader_menu_textsetting_padding_topbottom"
                        android:paddingRight="@dimen/hwtxtreader_menu_textsetting_padding_leftright"
                        android:paddingBottom="@dimen/hwtxtreader_menu_textsetting_padding_topbottom">
    
                        <TextView
                            android:layout_width="0dp"
                            android:layout_height="wrap_content"
                            android:layout_gravity="center_vertical"
                            android:layout_weight="1"
                            android:gravity="center|top"
                            android:text="普通"
                            android:textColor="@color/hwtxtreader_bottom_menu_textclor"
                            android:textSize="@dimen/hwtxtreader_menu_textsize_max" />
    
                    </LinearLayout>
    
                </LinearLayout>
    
                <LinearLayout
                    android:id="@+id/txtreadr_menu_textsetting2"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_below="@+id/txtreadr_menu_textsetting1"
                    android:layout_alignParentRight="true"
                    android:layout_marginTop="10dp"
                    android:layout_marginRight="20dp"
                    android:orientation="horizontal">
    
                    <LinearLayout
                        android:id="@+id/txtreadr_menu_textsetting2_cover"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:orientation="horizontal"
                        android:paddingLeft="@dimen/hwtxtreader_menu_textsetting_padding_leftright"
                        android:paddingTop="@dimen/hwtxtreader_menu_textsetting_padding_topbottom"
                        android:paddingRight="@dimen/hwtxtreader_menu_textsetting_padding_leftright"
                        android:paddingBottom="@dimen/hwtxtreader_menu_textsetting_padding_topbottom">
    
                        <TextView
                            android:layout_width="0dp"
                            android:layout_height="wrap_content"
                            android:layout_gravity="center_vertical"
                            android:layout_weight="1"
                            android:gravity="center|top"
                            android:text="滑盖"
                            android:textColor="@color/hwtxtreader_bottom_menu_textclor"
                            android:textSize="@dimen/hwtxtreader_menu_textsize_max" />
    
                    </LinearLayout>
    
                    <LinearLayout
                        android:id="@+id/txtreadr_menu_textsetting2_translate"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:layout_marginLeft="30dp"
                        android:orientation="horizontal"
                        android:paddingLeft="@dimen/hwtxtreader_menu_textsetting_padding_leftright"
                        android:paddingTop="@dimen/hwtxtreader_menu_textsetting_padding_topbottom"
                        android:paddingRight="@dimen/hwtxtreader_menu_textsetting_padding_leftright"
                        android:paddingBottom="@dimen/hwtxtreader_menu_textsetting_padding_topbottom">
    
                        <TextView
                            android:layout_width="0dp"
                            android:layout_height="wrap_content"
                            android:layout_gravity="center_vertical"
                            android:layout_weight="1"
                            android:gravity="center|top"
                            android:text="平移"
                            android:textColor="@color/hwtxtreader_bottom_menu_textclor"
                            android:textSize="@dimen/hwtxtreader_menu_textsize_max" />
    
                    </LinearLayout>
    
                </LinearLayout>
    
            </RelativeLayout>
        </LinearLayout>
    
        <RelativeLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content">
    
            <TextView
                android:id="@+id/tag_1"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_alignParentLeft="true"
                android:layout_centerVertical="true"
                android:text="上一"
                android:textSize="@dimen/hwtxtreader_menu_textsize_normal"
                android:visibility="invisible" />
    
            <TextView
                android:id="@+id/tag_2"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_alignParentRight="true"
                android:layout_centerVertical="true"
                android:text="下一"
                android:textSize="@dimen/hwtxtreader_menu_textsize_normal"
                android:visibility="invisible" />
    
            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginTop="20dp"
                android:layout_marginBottom="0dp"
                android:layout_toLeftOf="@+id/tag_2"
                android:layout_toRightOf="@+id/tag_1"
                android:orientation="horizontal"
                android:weightSum="5">
    
                <RelativeLayout
                    android:layout_width="0dp"
                    android:layout_height="wrap_content"
                    android:layout_weight="1">
    
                    <View
                        android:id="@+id/hwtxtreader_menu_style1"
                        android:layout_width="25dp"
                        android:layout_height="25dp"
                        android:layout_centerInParent="true"
                        android:background="@color/hwtxtreader_styleclor1" />
                </RelativeLayout>
    
                <RelativeLayout
                    android:layout_width="0dp"
                    android:layout_height="wrap_content"
                    android:layout_weight="1">
    
                    <View
                        android:id="@+id/hwtxtreader_menu_style2"
                        android:layout_width="25dp"
                        android:layout_height="25dp"
                        android:layout_centerInParent="true"
                        android:background="@color/hwtxtreader_styleclor2" />
                </RelativeLayout>
    
                <RelativeLayout
                    android:layout_width="0dp"
                    android:layout_height="wrap_content"
                    android:layout_weight="1">
    
                    <View
                        android:id="@+id/hwtxtreader_menu_style3"
                        android:layout_width="25dp"
                        android:layout_height="25dp"
                        android:layout_centerInParent="true"
                        android:background="@color/hwtxtreader_styleclor3" />
                </RelativeLayout>
    
                <RelativeLayout
                    android:layout_width="0dp"
                    android:layout_height="wrap_content"
                    android:layout_weight="1">
    
                    <View
                        android:id="@+id/hwtxtreader_menu_style4"
                        android:layout_width="25dp"
                        android:layout_height="25dp"
                        android:layout_centerInParent="true"
                        android:background="@color/hwtxtreader_styleclor4" />
                </RelativeLayout>
    
                <RelativeLayout
                    android:layout_width="0dp"
                    android:layout_height="wrap_content"
                    android:layout_weight="1">
    
                    <View
                        android:id="@+id/hwtxtreader_menu_style5"
                        android:layout_width="25dp"
                        android:layout_height="25dp"
                        android:layout_centerInParent="true"
                        android:background="@color/hwtxtreader_styleclor5" />
                </RelativeLayout>
            </LinearLayout>
        </RelativeLayout>
    </LinearLayout>

(5)view_menu_top.xml(返回框,单击屏幕中央或设置按钮时显示)

    <?xml version="1.0" encoding="utf-8"?>
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="#000000"
        android:orientation="horizontal"
        android:paddingLeft="10dp"
        android:paddingTop="13dp"
        android:paddingBottom="13dp">
    
        <ImageView
            android:layout_width="30dp"
            android:layout_height="18dp"
            android:layout_gravity="center_vertical"
            android:onClick="BackClick"
            android:src="@drawable/ic_key_back" />
    
        <TextView
            android:id="@+id/txtreadr_menu_title"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_gravity="center_vertical"
            android:layout_marginLeft="25dp"
            android:ellipsize="end"
            android:maxLines="1"
            android:text=""
            android:textColor="#ffffff"
            android:textSize="17sp" />
    </LinearLayout>

###### <a id="4.关于我们">4.关于我们</a>

(1)AboutActivity.java(在书架页面点击右上角图标可以进入该页面)

    package hw.txtreader.activity;
    
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    
    import hw.txtreader.R;
    
    public class AboutActivity extends AppCompatActivity {
    
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_about);
        }
    }

(2)activity_about.xml

	<?xml version="1.0" encoding="utf-8"?>
	<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
	    xmlns:app="http://schemas.android.com/apk/res-auto"
	    xmlns:tools="http://schemas.android.com/tools"
	    android:layout_width="fill_parent"
	    android:layout_height="fill_parent"
	    android:background="@drawable/background"
	    android:orientation="vertical">
	
	
	    <android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
	        xmlns:app="http://schemas.android.com/apk/res-auto"
	        xmlns:tools="http://schemas.android.com/tools"
	        android:layout_width="match_parent"
	        android:layout_height="match_parent"
	        android:background="@drawable/background"
	        tools:context=".activity.AboutActivity">
	
	        <ImageView
	            android:id="@+id/imageView2"
	            android:layout_width="wrap_content"
	            android:layout_height="wrap_content"
	            android:layout_marginTop="128dp"
	            android:src="@drawable/icon"
	            app:layout_constraintEnd_toEndOf="parent"
	            app:layout_constraintHorizontal_bias="0.5"
	            app:layout_constraintStart_toStartOf="parent"
	            app:layout_constraintTop_toTopOf="parent" />
	
	        <TextView
	            android:id="@+id/textView"
	            android:layout_width="wrap_content"
	            android:layout_height="wrap_content"
	            android:layout_marginTop="36dp"
	            android:text="@string/app_name"
	            android:textSize="30dp"
	            app:layout_constraintEnd_toEndOf="parent"
	            app:layout_constraintStart_toStartOf="parent"
	            app:layout_constraintTop_toBottomOf="@+id/imageView2" />
	
	        <TextView
	            android:id="@+id/edition"
	            android:layout_width="300dp"
	            android:layout_height="54dp"
	            android:layout_marginStart="8dp"
	            android:layout_marginTop="8dp"
	            android:layout_marginEnd="8dp"
	            android:layout_marginBottom="8dp"
	            android:drawableStart="@drawable/banbe"
	            android:gravity="center"
	            android:text="版本: v1.0"
	            android:textSize="20dp"
	            app:layout_constraintBottom_toBottomOf="parent"
	            app:layout_constraintEnd_toEndOf="parent"
	            app:layout_constraintStart_toStartOf="parent"
	            app:layout_constraintTop_toTopOf="parent"
	            app:layout_constraintVertical_bias="0.617" />
	
	        <TextView
	            android:id="@+id/team"
	            android:layout_width="300dp"
	            android:layout_height="40dp"
	            android:layout_marginStart="8dp"
	            android:layout_marginEnd="8dp"
	            android:drawableStart="@drawable/tuandui"
	            android:gravity="center"
	            android:text="开发团队：安卓小分队"
	            android:textSize="20dp"
	            app:layout_constraintEnd_toEndOf="parent"
	            app:layout_constraintStart_toStartOf="parent"
	            app:layout_constraintTop_toBottomOf="@+id/edition" />
	
	        <TextView
	            android:id="@+id/call"
	            android:layout_width="300dp"
	            android:layout_height="50dp"
	            android:layout_marginBottom="8dp"
	            android:drawableStart="@drawable/lianxi"
	            android:gravity="center"
	            android:text="有问题联系qq:1593457"
	            android:textSize="20dp"
	            app:layout_constraintBottom_toBottomOf="parent"
	            app:layout_constraintEnd_toEndOf="parent"
	            app:layout_constraintStart_toStartOf="parent"
	            app:layout_constraintTop_toBottomOf="@+id/team"
	            app:layout_constraintVertical_bias="0.0" />
	
	
	        <TextView
	            android:id="@+id/code"
	            android:layout_width="wrap_content"
	            android:layout_height="wrap_content"
	            android:layout_marginBottom="8dp"
	            android:text="维护地址：https://github.com/Sugarcandy/ReaderApp"
	            android:textAlignment="center"
	            android:textSize="20dp"
	            app:layout_constraintBottom_toBottomOf="parent"
	            app:layout_constraintStart_toStartOf="parent" />
	
	    </android.support.constraint.ConstraintLayout>
	
	</LinearLayout>

三.其他关键代码

1.bean
(1)BookBean.java

    package hw.txtreader.bean;
    
    import com.bifan.txtreaderlib.bean.Chapter;
    
    import org.greenrobot.greendao.annotation.Entity;
    import org.greenrobot.greendao.annotation.Id;
    import org.greenrobot.greendao.annotation.Transient;
    
    import java.util.ArrayList;
    import java.util.List;
    
    import org.greenrobot.greendao.annotation.Generated;
    
    /**
     * 读取进来的文本信息
     */
    @Entity
    public class BookBean {
        private String name; //小说名
    
        private String tag;
    
        @Id
        private String noteUrl; //小说本地MD5
    
        private String chapterUrl;  //章节目录地址
    
        @Transient
        private List<Chapter> chapterlist = new ArrayList<>();    //章节列表
    
        private long finalRefreshData;  //章节最后更新时间
    
        private String coverUrl; //小说封面
    
        private String author;//作者
    
        private String introduce; //简介
    
        private String origin; //来源
    
        @Generated(hash = 169255905)
        public BookBean(String name, String tag, String noteUrl, String chapterUrl,
                        long finalRefreshData, String coverUrl, String author, String introduce,
                        String origin) {
            this.name = name;
            this.tag = tag;
            this.noteUrl = noteUrl;
            this.chapterUrl = chapterUrl;
            this.finalRefreshData = finalRefreshData;
            this.coverUrl = coverUrl;
            this.author = author;
            this.introduce = introduce;
            this.origin = origin;
        }
    
        @Generated(hash = 269018259)
        public BookBean() {
        }
    
        public String getName() {
            return this.name;
        }
    
        public void setName(String name) {
            this.name = name;
        }
    
        public String getTag() {
            return this.tag;
        }
    
        public void setTag(String tag) {
            this.tag = tag;
        }
    
        public String getNoteUrl() {
            return this.noteUrl;
        }
    
        public void setNoteUrl(String noteUrl) {
            this.noteUrl = noteUrl;
        }
    
        public String getChapterUrl() {
            return this.chapterUrl;
        }
    
        public void setChapterUrl(String chapterUrl) {
            this.chapterUrl = chapterUrl;
        }
    
        public long getFinalRefreshData() {
            return this.finalRefreshData;
        }
    
        public void setFinalRefreshData(long finalRefreshData) {
            this.finalRefreshData = finalRefreshData;
        }
    
        public String getCoverUrl() {
            return this.coverUrl;
        }
    
        public void setCoverUrl(String coverUrl) {
            this.coverUrl = coverUrl;
        }
    
        public String getAuthor() {
            return this.author;
        }
    
        public void setAuthor(String author) {
            this.author = author;
        }
    
        public String getIntroduce() {
            return this.introduce;
        }
    
        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }
    
        public String getOrigin() {
            return this.origin;
        }
    
        public void setOrigin(String origin) {
            this.origin = origin;
        }
    
        @Override
        public String toString() {
            return "BookBean{" +
                    "name='" + name + '\'' +
                    ", tag='" + tag + '\'' +
                    ", noteUrl='" + noteUrl + '\'' +
                    ", chapterUrl='" + chapterUrl + '\'' +
                    ", chapterlist=" + chapterlist +
                    ", finalRefreshData=" + finalRefreshData +
                    ", coverUrl='" + coverUrl + '\'' +
                    ", author='" + author + '\'' +
                    ", introduce='" + introduce + '\'' +
                    ", origin='" + origin + '\'' +
                    '}';
        }
    }

(2)BookShelfBean.java

    package hw.txtreader.bean;
    
    import org.greenrobot.greendao.annotation.Entity;
    import org.greenrobot.greendao.annotation.Id;
    import org.greenrobot.greendao.annotation.Transient;
    import org.greenrobot.greendao.annotation.Generated;
    
    /**
     * 书架
     */
    @Entity
    public class BookShelfBean {
        @Id
        private String noteUrl; //对应BookInfoBean noteUrl;
    
        private int durChapter;   //当前章节 （包括番外）
    
        //private int durChapterPage = BookContentView.DURPAGEINDEXBEGIN;  // 当前章节位置   用页码
    
        private long finalDate;  //最后阅读时间
    
        private String tag;
    
        @Transient
        private BookBean bookBean = new BookBean();
    
        @Generated(hash = 1754467827)
        public BookShelfBean(String noteUrl, int durChapter, long finalDate, String tag) {
            this.noteUrl = noteUrl;
            this.durChapter = durChapter;
            this.finalDate = finalDate;
            this.tag = tag;
        }
    
        @Generated(hash = 1462228839)
        public BookShelfBean() {
        }
    
        public String getNoteUrl() {
            return this.noteUrl;
        }
    
        public void setNoteUrl(String noteUrl) {
            this.noteUrl = noteUrl;
        }
    
        public int getDurChapter() {
            return this.durChapter;
        }
    
        public void setDurChapter(int durChapter) {
            this.durChapter = durChapter;
        }
    
        public long getFinalDate() {
            return this.finalDate;
        }
    
        public void setFinalDate(long finalDate) {
            this.finalDate = finalDate;
        }
    
        public String getTag() {
            return this.tag;
        }
    
        public void setTag(String tag) {
            this.tag = tag;
        }
    
        @Override
        public String toString() {
            return "BookShelfBean{" +
                    "noteUrl='" + noteUrl + '\'' +
                    ", durChapter=" + durChapter +
                    ", finalDate=" + finalDate +
                    ", tag='" + tag + '\'' +
                    ", bookBean=" + bookBean +
                    '}';
        }
    }

2.Dao

(1)BookBeanDao.java

    package hw.txtreader.dao;
    
    import android.database.Cursor;
    import android.database.sqlite.SQLiteStatement;
    
    import org.greenrobot.greendao.AbstractDao;
    import org.greenrobot.greendao.Property;
    import org.greenrobot.greendao.internal.DaoConfig;
    import org.greenrobot.greendao.database.Database;
    import org.greenrobot.greendao.database.DatabaseStatement;
    
    import hw.txtreader.bean.BookBean;
    
    // THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
    
    /**
     * DAO for table "BOOK_BEAN".
     */
    public class BookBeanDao extends AbstractDao<BookBean, String> {
    
        public static final String TABLENAME = "BOOK_BEAN";
    
        /**
         * Properties of entity BookBean.<br/>
         * Can be used for QueryBuilder and for referencing column names.
         */
        public static class Properties {
            public final static Property Name = new Property(0, String.class, "name", false, "NAME");
            public final static Property Tag = new Property(1, String.class, "tag", false, "TAG");
            public final static Property NoteUrl = new Property(2, String.class, "noteUrl", true, "NOTE_URL");
            public final static Property ChapterUrl = new Property(3, String.class, "chapterUrl", false, "CHAPTER_URL");
            public final static Property FinalRefreshData = new Property(4, long.class, "finalRefreshData", false, "FINAL_REFRESH_DATA");
            public final static Property CoverUrl = new Property(5, String.class, "coverUrl", false, "COVER_URL");
            public final static Property Author = new Property(6, String.class, "author", false, "AUTHOR");
            public final static Property Introduce = new Property(7, String.class, "introduce", false, "INTRODUCE");
            public final static Property Origin = new Property(8, String.class, "origin", false, "ORIGIN");
        }
    
    
        public BookBeanDao(DaoConfig config) {
            super(config);
        }
    
        public BookBeanDao(DaoConfig config, DaoSession daoSession) {
            super(config, daoSession);
        }
    
        /**
         * Creates the underlying database table.
         */
        public static void createTable(Database db, boolean ifNotExists) {
            String constraint = ifNotExists ? "IF NOT EXISTS " : "";
            db.execSQL("CREATE TABLE " + constraint + "\"BOOK_BEAN\" (" + //
                    "\"NAME\" TEXT," + // 0: name
                    "\"TAG\" TEXT," + // 1: tag
                    "\"NOTE_URL\" TEXT PRIMARY KEY NOT NULL ," + // 2: noteUrl
                    "\"CHAPTER_URL\" TEXT," + // 3: chapterUrl
                    "\"FINAL_REFRESH_DATA\" INTEGER NOT NULL ," + // 4: finalRefreshData
                    "\"COVER_URL\" TEXT," + // 5: coverUrl
                    "\"AUTHOR\" TEXT," + // 6: author
                    "\"INTRODUCE\" TEXT," + // 7: introduce
                    "\"ORIGIN\" TEXT);"); // 8: origin
        }
    
        /**
         * Drops the underlying database table.
         */
        public static void dropTable(Database db, boolean ifExists) {
            String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BOOK_BEAN\"";
            db.execSQL(sql);
        }
    
        @Override
        protected final void bindValues(DatabaseStatement stmt, BookBean entity) {
            stmt.clearBindings();
    
            String name = entity.getName();
            if (name != null) {
                stmt.bindString(1, name);
            }
    
            String tag = entity.getTag();
            if (tag != null) {
                stmt.bindString(2, tag);
            }
    
            String noteUrl = entity.getNoteUrl();
            if (noteUrl != null) {
                stmt.bindString(3, noteUrl);
            }
    
            String chapterUrl = entity.getChapterUrl();
            if (chapterUrl != null) {
                stmt.bindString(4, chapterUrl);
            }
            stmt.bindLong(5, entity.getFinalRefreshData());
    
            String coverUrl = entity.getCoverUrl();
            if (coverUrl != null) {
                stmt.bindString(6, coverUrl);
            }
    
            String author = entity.getAuthor();
            if (author != null) {
                stmt.bindString(7, author);
            }
    
            String introduce = entity.getIntroduce();
            if (introduce != null) {
                stmt.bindString(8, introduce);
            }
    
            String origin = entity.getOrigin();
            if (origin != null) {
                stmt.bindString(9, origin);
            }
        }
    
        @Override
        protected final void bindValues(SQLiteStatement stmt, BookBean entity) {
            stmt.clearBindings();
    
            String name = entity.getName();
            if (name != null) {
                stmt.bindString(1, name);
            }
    
            String tag = entity.getTag();
            if (tag != null) {
                stmt.bindString(2, tag);
            }
    
            String noteUrl = entity.getNoteUrl();
            if (noteUrl != null) {
                stmt.bindString(3, noteUrl);
            }
    
            String chapterUrl = entity.getChapterUrl();
            if (chapterUrl != null) {
                stmt.bindString(4, chapterUrl);
            }
            stmt.bindLong(5, entity.getFinalRefreshData());
    
            String coverUrl = entity.getCoverUrl();
            if (coverUrl != null) {
                stmt.bindString(6, coverUrl);
            }
    
            String author = entity.getAuthor();
            if (author != null) {
                stmt.bindString(7, author);
            }
    
            String introduce = entity.getIntroduce();
            if (introduce != null) {
                stmt.bindString(8, introduce);
            }
    
            String origin = entity.getOrigin();
            if (origin != null) {
                stmt.bindString(9, origin);
            }
        }
    
        @Override
        public String readKey(Cursor cursor, int offset) {
            return cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2);
        }
    
        @Override
        public BookBean readEntity(Cursor cursor, int offset) {
            BookBean entity = new BookBean( //
                    cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // name
                    cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // tag
                    cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // noteUrl
                    cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // chapterUrl
                    cursor.getLong(offset + 4), // finalRefreshData
                    cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // coverUrl
                    cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // author
                    cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // introduce
                    cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8) // origin
            );
            return entity;
        }
    
        @Override
        public void readEntity(Cursor cursor, BookBean entity, int offset) {
            entity.setName(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
            entity.setTag(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
            entity.setNoteUrl(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
            entity.setChapterUrl(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
            entity.setFinalRefreshData(cursor.getLong(offset + 4));
            entity.setCoverUrl(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
            entity.setAuthor(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
            entity.setIntroduce(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
            entity.setOrigin(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        }
    
        @Override
        protected final String updateKeyAfterInsert(BookBean entity, long rowId) {
            return entity.getNoteUrl();
        }
    
        @Override
        public String getKey(BookBean entity) {
            if (entity != null) {
                return entity.getNoteUrl();
            } else {
                return null;
            }
        }
    
        @Override
        public boolean hasKey(BookBean entity) {
            return entity.getNoteUrl() != null;
        }
    
        @Override
        protected final boolean isEntityUpdateable() {
            return true;
        }
    
    }

(2)BookShelfBeanDao.java

    package hw.txtreader.dao;
    
    import android.database.Cursor;
    import android.database.sqlite.SQLiteStatement;
    
    import org.greenrobot.greendao.AbstractDao;
    import org.greenrobot.greendao.Property;
    import org.greenrobot.greendao.internal.DaoConfig;
    import org.greenrobot.greendao.database.Database;
    import org.greenrobot.greendao.database.DatabaseStatement;
    
    import hw.txtreader.bean.BookShelfBean;
    
    // THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
    /** 
     * DAO for table "BOOK_SHELF_BEAN".
    */
    public class BookShelfBeanDao extends AbstractDao<BookShelfBean, String> {
    
        public static final String TABLENAME = "BOOK_SHELF_BEAN";
    
        /**
         * Properties of entity BookShelfBean.<br/>
         * Can be used for QueryBuilder and for referencing column names.
         */
        public static class Properties {
            public final static Property NoteUrl = new Property(0, String.class, "noteUrl", true, "NOTE_URL");
            public final static Property DurChapter = new Property(1, int.class, "durChapter", false, "DUR_CHAPTER");
            public final static Property FinalDate = new Property(2, long.class, "finalDate", false, "FINAL_DATE");
            public final static Property Tag = new Property(3, String.class, "tag", false, "TAG");
        }
    
    
        public BookShelfBeanDao(DaoConfig config) {
            super(config);
        }
        
        public BookShelfBeanDao(DaoConfig config, DaoSession daoSession) {
            super(config, daoSession);
        }
    
        /** Creates the underlying database table. */
        public static void createTable(Database db, boolean ifNotExists) {
            String constraint = ifNotExists? "IF NOT EXISTS ": "";
            db.execSQL("CREATE TABLE " + constraint + "\"BOOK_SHELF_BEAN\" (" + //
                    "\"NOTE_URL\" TEXT PRIMARY KEY NOT NULL ," + // 0: noteUrl
                    "\"DUR_CHAPTER\" INTEGER NOT NULL ," + // 1: durChapter
                    "\"FINAL_DATE\" INTEGER NOT NULL ," + // 2: finalDate
                    "\"TAG\" TEXT);"); // 3: tag
        }
    
        /** Drops the underlying database table. */
        public static void dropTable(Database db, boolean ifExists) {
            String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BOOK_SHELF_BEAN\"";
            db.execSQL(sql);
        }
    
        @Override
        protected final void bindValues(DatabaseStatement stmt, BookShelfBean entity) {
            stmt.clearBindings();
     
            String noteUrl = entity.getNoteUrl();
            if (noteUrl != null) {
                stmt.bindString(1, noteUrl);
            }
            stmt.bindLong(2, entity.getDurChapter());
            stmt.bindLong(3, entity.getFinalDate());
     
            String tag = entity.getTag();
            if (tag != null) {
                stmt.bindString(4, tag);
            }
        }
    
        @Override
        protected final void bindValues(SQLiteStatement stmt, BookShelfBean entity) {
            stmt.clearBindings();
     
            String noteUrl = entity.getNoteUrl();
            if (noteUrl != null) {
                stmt.bindString(1, noteUrl);
            }
            stmt.bindLong(2, entity.getDurChapter());
            stmt.bindLong(3, entity.getFinalDate());
     
            String tag = entity.getTag();
            if (tag != null) {
                stmt.bindString(4, tag);
            }
        }
    
        @Override
        public String readKey(Cursor cursor, int offset) {
            return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
        }    
    
        @Override
        public BookShelfBean readEntity(Cursor cursor, int offset) {
            BookShelfBean entity = new BookShelfBean( //
                cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // noteUrl
                cursor.getInt(offset + 1), // durChapter
                cursor.getLong(offset + 2), // finalDate
                cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // tag
            );
            return entity;
        }
         
        @Override
        public void readEntity(Cursor cursor, BookShelfBean entity, int offset) {
            entity.setNoteUrl(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
            entity.setDurChapter(cursor.getInt(offset + 1));
            entity.setFinalDate(cursor.getLong(offset + 2));
            entity.setTag(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
         }
        
        @Override
        protected final String updateKeyAfterInsert(BookShelfBean entity, long rowId) {
            return entity.getNoteUrl();
        }
        
        @Override
        public String getKey(BookShelfBean entity) {
            if(entity != null) {
                return entity.getNoteUrl();
            } else {
                return null;
            }
        }
    
        @Override
        public boolean hasKey(BookShelfBean entity) {
            return entity.getNoteUrl() != null;
        }
    
        @Override
        protected final boolean isEntityUpdateable() {
            return true;
        }
        
    }

(3)DaoMaster.java

    package hw.txtreader.dao;
    
    import android.content.Context;
    import android.database.sqlite.SQLiteDatabase;
    import android.database.sqlite.SQLiteDatabase.CursorFactory;
    import android.util.Log;
    
    import org.greenrobot.greendao.AbstractDaoMaster;
    import org.greenrobot.greendao.database.StandardDatabase;
    import org.greenrobot.greendao.database.Database;
    import org.greenrobot.greendao.database.DatabaseOpenHelper;
    import org.greenrobot.greendao.identityscope.IdentityScopeType;
    
    
    // THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
    
    /**
     * Master of DAO (schema version 1): knows all DAOs.
     */
    public class DaoMaster extends AbstractDaoMaster {
        public static final int SCHEMA_VERSION = 1;
    
        /**
         * Creates underlying database table using DAOs.
         */
        public static void createAllTables(Database db, boolean ifNotExists) {
            BookBeanDao.createTable(db, ifNotExists);
            BookShelfBeanDao.createTable(db, ifNotExists);
        }
    
        /**
         * Drops underlying database table using DAOs.
         */
        public static void dropAllTables(Database db, boolean ifExists) {
            BookBeanDao.dropTable(db, ifExists);
            BookShelfBeanDao.dropTable(db, ifExists);
        }
    
        /**
         * WARNING: Drops all table on Upgrade! Use only during development.
         * Convenience method using a {@link DevOpenHelper}.
         */
        public static DaoSession newDevSession(Context context, String name) {
            Database db = new DevOpenHelper(context, name).getWritableDb();
            DaoMaster daoMaster = new DaoMaster(db);
            return daoMaster.newSession();
        }
    
        public DaoMaster(SQLiteDatabase db) {
            this(new StandardDatabase(db));
        }
    
        public DaoMaster(Database db) {
            super(db, SCHEMA_VERSION);
            registerDaoClass(BookBeanDao.class);
            registerDaoClass(BookShelfBeanDao.class);
        }
    
        public DaoSession newSession() {
            return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
        }
    
        public DaoSession newSession(IdentityScopeType type) {
            return new DaoSession(db, type, daoConfigMap);
        }
    
        /**
         * Calls {@link #createAllTables(Database, boolean)} in {@link #onCreate(Database)} -
         */
        public static abstract class OpenHelper extends DatabaseOpenHelper {
            public OpenHelper(Context context, String name) {
                super(context, name, SCHEMA_VERSION);
            }
    
            public OpenHelper(Context context, String name, CursorFactory factory) {
                super(context, name, factory, SCHEMA_VERSION);
            }
    
            @Override
            public void onCreate(Database db) {
                Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
                createAllTables(db, false);
            }
        }
    
        /**
         * WARNING: Drops all table on Upgrade! Use only during development.
         */
        public static class DevOpenHelper extends OpenHelper {
            public DevOpenHelper(Context context, String name) {
                super(context, name);
            }
    
            public DevOpenHelper(Context context, String name, CursorFactory factory) {
                super(context, name, factory);
            }
    
            @Override
            public void onUpgrade(Database db, int oldVersion, int newVersion) {
                Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
                dropAllTables(db, true);
                onCreate(db);
            }
        }
    
    }

(4)DaoSession.java

    package hw.txtreader.dao;
    
    import java.util.Map;
    
    import org.greenrobot.greendao.AbstractDao;
    import org.greenrobot.greendao.AbstractDaoSession;
    import org.greenrobot.greendao.database.Database;
    import org.greenrobot.greendao.identityscope.IdentityScopeType;
    import org.greenrobot.greendao.internal.DaoConfig;
    
    import hw.txtreader.bean.BookBean;
    import hw.txtreader.bean.BookShelfBean;
    
    import hw.txtreader.dao.BookBeanDao;
    import hw.txtreader.dao.BookShelfBeanDao;
    
    // THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
    
    /**
     * {@inheritDoc}
     *
     * @see org.greenrobot.greendao.AbstractDaoSession
     */
    public class DaoSession extends AbstractDaoSession {
    
        private final DaoConfig bookBeanDaoConfig;
        private final DaoConfig bookShelfBeanDaoConfig;
    
        private final BookBeanDao bookBeanDao;
        private final BookShelfBeanDao bookShelfBeanDao;
    
        public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
                daoConfigMap) {
            super(db);
    
            bookBeanDaoConfig = daoConfigMap.get(BookBeanDao.class).clone();
            bookBeanDaoConfig.initIdentityScope(type);
    
            bookShelfBeanDaoConfig = daoConfigMap.get(BookShelfBeanDao.class).clone();
            bookShelfBeanDaoConfig.initIdentityScope(type);
    
            bookBeanDao = new BookBeanDao(bookBeanDaoConfig, this);
            bookShelfBeanDao = new BookShelfBeanDao(bookShelfBeanDaoConfig, this);
    
            registerDao(BookBean.class, bookBeanDao);
            registerDao(BookShelfBean.class, bookShelfBeanDao);
        }
    
        public void clear() {
            bookBeanDaoConfig.clearIdentityScope();
            bookShelfBeanDaoConfig.clearIdentityScope();
        }
    
        public BookBeanDao getBookBeanDao() {
            return bookBeanDao;
        }
    
        public BookShelfBeanDao getBookShelfBeanDao() {
            return bookShelfBeanDao;
        }
    
    }

(5)DbHelper.java

    package hw.txtreader.dao;
    
    import org.greenrobot.greendao.database.Database;
    
    import hw.txtreader.MApplication;
    
    public class DbHelper {
        private DaoMaster.DevOpenHelper mHelper;
        private Database db;
        private DaoMaster mDaoMaster;
        private DaoSession mDaoSession;
    
        private DbHelper() {
            mHelper = new DaoMaster.DevOpenHelper(MApplication.getInstance(), "reader.db", null);
            db = mHelper.getWritableDb();
            // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
            mDaoMaster = new DaoMaster(db);
            mDaoSession = mDaoMaster.newSession();
        }
    
        private static DbHelper instance;
    
        public static DbHelper getInstance() {
            if (null == instance) {
                synchronized (DbHelper.class) {
                    if (null == instance) instance = new DbHelper();
                }
            }
            return instance;
        }
    
        public DaoSession getmDaoSession() {
            return mDaoSession;
        }
    
        public Database getDb() {
            return db;
        }
    }

3.util
HttpUtil.java

    package hw.txtreader.util;
    
    import okhttp3.OkHttpClient;
    import okhttp3.Request;
    
    public class HttpUtil {
        public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(address).build();
            client.newCall(request).enqueue(callback);
        }
    }
