package com.example.ipanda1.test;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.ipanda1.R;
import com.example.ipanda1.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding mActivityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);

        mActivityMainBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);

        mActivityMainBinding.content.tv.setText("哈哈哈哈");

        mActivityMainBinding.setButtonContent("我是一个不能点击的按钮");
        mActivityMainBinding.setButtonEnabled(false);
        mActivityMainBinding.mainTv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "我被点击了", Toast.LENGTH_SHORT).show();
            }
        });

        User user = new User("小米", 12);
        mActivityMainBinding.setUser(user); // 或者 mActivityMainBinding.setVariable(BR.user,user);

        mActivityMainBinding.setTitle1("事件绑定1");
        mActivityMainBinding.setTitle2("事件绑定2");
        mActivityMainBinding.setTitle3("事件绑定3");
        mActivityMainBinding.setTitle4("change OK");

        mActivityMainBinding.setEvent(new EventListener() {
            @Override
            public void click1(View v) {
                mActivityMainBinding.setTitle1("事件1方法调用");
            }

            @Override
            public void click2(View v) {
                mActivityMainBinding.setTitle2("事件2方法调用");
            }

            @Override
            public void cilck3(String s) {
                mActivityMainBinding.setTitle3(s);
            }
        });

        final Obs obs=new Obs("标题1","子标题1");
        mActivityMainBinding.setObs(obs);

        mActivityMainBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obs.setTitle("标题2");
                obs.setSubTitle("子标题2");
                Snackbar.make(mActivityMainBinding.fab, "改变toolbar的标题", Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
