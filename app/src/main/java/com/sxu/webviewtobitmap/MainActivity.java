package com.sxu.webviewtobitmap;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.TextView;

public class MainActivity extends Activity {

    private WebView webView;
    private TextView generateImageText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.webView);
        generateImageText = (TextView) findViewById(R.id.generate_text);
        webView.loadUrl("http://www.jianshu.com/p/edadb47f3f18");
        registerForContextMenu(generateImageText);
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                generateImageText.setVisibility(View.VISIBLE);
                return false;
            }
        });


        generateImageText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ImageActivity.class);
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                intent.putExtra("data", cm.getPrimaryClip());
                startActivity(intent);
            }
        });
    }

    //菜单键
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "刷新");
        menu.add(0, 0, 1, "后退");
        menu.add(0, 0, 2, "前进");
        return super.onCreateOptionsMenu(menu);
    }
    //菜单点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getOrder()) {
            case 0:
                webView.reload();
                break;
            case 1:
                if(webView.canGoBack()){
                    webView.goBack();
                }
                break;
            case 2:
                if(webView.canGoForward()){
                    webView.goForward();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
