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
    private List<Map<String,String>> bookBeanList=new ArrayList<>();//书籍列表
    private FloatingActionButton addBook;//从本地导入书籍
    private Boolean Permit = false;
    private final BookBeanDao bookBeanDao=DbHelper.getInstance().getmDaoSession().getBookBeanDao();
    private SearchView search;
    private ImageButton setting;//关于我们

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 0x01;

    String []target={
            "name",
            "introduce",
            "author"
    };//参数名

    int [] xmlpath={
      R.id.title,
//      R.id.describe,
//      R.id.author
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header);
        if(CheckPermission()){//获取文件操作权限
            Permit=true;
        }

        shelf=findViewById(R.id.shelf);
//        registerForContextMenu(shelf);
        search=findViewById(R.id.search);
        search.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        search.setIconified(false);
        addBook=findViewById(R.id.ib_add);
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
                String path=bookBeanList.get(position).get("url");
                TxtConfig.saveIsOnVerticalPageMode(BookShelfActivity.this,false);
                HwTxtPlayActivity.loadTxtFile(BookShelfActivity.this,path);
            }
        });
        setting=findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BookShelfActivity.this,AboutActivity.class);
                startActivity(intent);
            }
        });
        LongClick();//长按item弹出菜单

        Search();//搜索

    }

    public void getdata(){
        List<BookBean> list=bookBeanDao.loadAll();//获取所有书籍

        bookBeanList.clear();//先清理掉
        if(list.size()>0) {
            for (BookBean bookBean : list) {//添加数据库的所有书籍
                Map<String, String> map = new HashMap<>();
                map.put("name", bookBean.getName());
//                map.put("introduce", bookBean.getIntroduce());
//                map.put("author", bookBean.getAuthor());
                map.put("url",bookBean.getNoteUrl());
                bookBeanList.add(map);
            }
            adapter=new SimpleAdapter(BookShelfActivity.this,bookBeanList,R.layout.activity_book_shelf_item,target,xmlpath);
            shelf.setAdapter(adapter);
        }else {
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
                BookBean bookBean=new BookBean();
                bookBean.setNoteUrl(path);
                bookBean.setName(path.trim().substring(path.lastIndexOf("/")+1));
                bookBean.setChapterUrl(path);
//                bookBean.setAuthor("唐家三少");
//                bookBean.setIntroduce("这是一片斗气的世界，这里没有绚丽的魔法，有的只有强横的斗气...");
                long res=bookBeanDao.insertOrReplace(bookBean);//添加到数据库
                if(res>0) {
                    getdata();
                    toast("添加成功！");
                }
                else
                    toast("添加失败！");
            } catch (Exception e) {
                toast("选择出错了!");
            }
        }
    }



    public void LongClick(){//长按菜单操作
        shelf.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(0,0,0,"重命名");
                menu.add(0,1,0,"移出书架");
                menu.add(0,2,0,"移出所有");
            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //获取当前
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        String path=null;
        BookBean bookBean=null;
        AlertDialog.Builder alertDialog=null;
        switch (item.getItemId()){
            case 0:
                //重命名
                alertDialog=new AlertDialog.Builder(BookShelfActivity.this);
                final View layout= LayoutInflater.from(this).inflate(R.layout.activity_alert,shelf,false);
                alertDialog.setView(layout);
                alertDialog.setTitle("请输入修改后的书名");
                alertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText text=(EditText) layout;
                        String name=text.getText().toString();
                        if(name!=null&&!"".equals(name)) {
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
                        }else {
                            toast("输入为空");
                        }
                    }
                }).setNegativeButton("取消",null);
                alertDialog.show();//显示alertDialog
                break;
            case 1:
                //移出书架
                path=bookBeanList.get(info.position).get("url");
                bookBean=new BookBean();
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

    public void Search(){
            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText!=""){
                    String name="%"+newText+"%";
                    List<BookBean> list=bookBeanDao.queryBuilder().where(BookBeanDao.Properties.Name.like(name)).list();
                    bookBeanList.clear();//先清理掉
                    if(list.size()>0) {
                        for (BookBean bookBean : list) {//添加数据库的所有书籍
                            Map<String, String> map = new HashMap<>();
                            map.put("name", bookBean.getName());
//                            map.put("introduce", bookBean.getIntroduce());
//                            map.put("author", bookBean.getAuthor());
                            map.put("url",bookBean.getNoteUrl());
                            bookBeanList.add(map);
                        }
                        shelf.removeAllViewsInLayout();//清空所有view

                        adapter=new SimpleAdapter(BookShelfActivity.this,bookBeanList,R.layout.activity_book_shelf_item,target,xmlpath);
                        shelf.setAdapter(adapter);
                    }else {
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
