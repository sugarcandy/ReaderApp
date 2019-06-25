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
