package hw.txtreader.test;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bifan.txtreaderlib.main.TxtConfig;
import com.bifan.txtreaderlib.ui.HwTxtPlayActivity;

import java.util.List;

import hw.txtreader.R;
import hw.txtreader.bean.BookBean;

public class testDbActivity extends AppCompatActivity {

    HwTxtPlayActivity txtreader;
    private Boolean Permit = false;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 0x01;
    private String FilePath = Environment.getExternalStorageDirectory() + "/1.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_db);
        if(CheckPermission()){
            Permit=true;
        }
        Button insert=findViewById(R.id.insert);
        Button query=findViewById(R.id.query);
        final TextView records=findViewById(R.id.records);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    testDb.insert();
                    records.setText("添加成功！");
                }catch (Exception e){
                    e.printStackTrace();
                    records.setText("添加失败！");
                }

            }
        });

        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                records.setText("");
                try {
                    List<BookBean> bookBeanList = testDb.query();
                    StringBuilder stringBuilder=new StringBuilder();
                    for(BookBean bookBean:bookBeanList){
                        stringBuilder.append(bookBean.toString()+"\n");
                    }
                    records.setText(stringBuilder);
                }catch (Exception e){
                    e.printStackTrace();
                   records.setText("查询出错！");
                }
                chooseFile(null);
            }

        });
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
                TxtConfig.saveIsOnVerticalPageMode(this,false);
                HwTxtPlayActivity.loadTxtFile(testDbActivity.this,path);
            } catch (Exception e) {
                toast("选择出错了");
            }
        }
    }

    public void chooseFile(View view) {
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

    private Toast t;

    private void toast(String msg) {
        if (t != null) {
            t.cancel();
        }
        t = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        t.show();
    }

}
