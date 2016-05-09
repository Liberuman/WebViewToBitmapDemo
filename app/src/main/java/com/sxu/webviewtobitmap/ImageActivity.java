package com.sxu.webviewtobitmap;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class ImageActivity extends Activity {

    private WebView webView;
    private TextView generateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.webView);
        generateText = (TextView) findViewById(R.id.generate_text);
        generateText.setVisibility(View.VISIBLE);
        ClipData data = getIntent().getParcelableExtra("data");
        if (data != null) {
            webView.loadData(data.getItemAt(0).getHtmlText(), "text/html; charset=UTF-8", null);
        }

        generateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBitmap(convertWebViewToBitmap());
            }
        });
    }

    /**
     * 将WebView中的内容转换为Bitmap
     * @return
     */
    private Bitmap convertWebViewToBitmap(){
        Picture picture = webView.capturePicture();
        Bitmap bitmap = Bitmap.createBitmap(picture.getWidth(),picture.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        picture.draw(canvas);

        return bitmap;
    }

    /**
     * 保存图片
     * @param bitmap
     */
    public void saveBitmap(Bitmap bitmap) {
        if (bitmap != null && Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File sdDir = Environment.getExternalStorageDirectory();
            File file = new File(sdDir, "test.png");
            try {
                FileOutputStream out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }
    }
}
