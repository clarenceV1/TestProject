package test.cai.com.testpro;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.MalformedJsonException;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class MainActivity extends AppCompatActivity {
    WebView webView;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Logger.addLogAdapter(new AndroidLogAdapter());
        tvResult = findViewById(R.id.tvResult);
        initWebView();
    }

    private void initWebView() {
        webView = new WebView(this);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_MR1) {
            webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        }
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            webSettings.setAllowFileAccess(true); //设置可以访问文件
        }
        webSettings.setJavaScriptCanOpenWindowsAutomatically(false); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        // 特别注意：5.1以上默认禁止了https和http混用，以下方式是开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        WebChromeClientBase mWebChromeClientBase = new WebChromeClientBase(this);
        WebViewClientBase mWebViewClientBase = new WebViewClientBase();
        webView.setWebViewClient(mWebViewClientBase);
        webView.setWebChromeClient(mWebChromeClientBase);
        webView.loadUrl("file:///android_asset/demoeos.html");
    }

    public void loadJsEOS(View view) {
        EosConfig eosConfig = new EosConfig();
        eosConfig.setVerbose(false);
        eosConfig.setHttpEndpoint("http://dev.cryptolions.io:38888");
        eosConfig.setChainId("038f4b0fc8ff18a4f0842a8f0564611f6e96e8535901dd45e43ac8691a1c4dca");

        String json = JSON.toJSONString(eosConfig);
        javascriptCallBack("Eos", json);
    }

    public void loadJsGetinfo(View view) {
        javascriptCallBack("get_info", "");
    }

    /**
     * 安卓调用javascript方法
     *
     * @param methdName javascript的方法名
     * @param param     返回给javascript 的值
     */
    private void javascriptCallBack(String methdName, String param) {
        // Android版本变量
        // 因为该方法在 Android 4.4 版本才可使用，所以使用时需进行版本判断
        StringBuilder builder = new StringBuilder();
        builder.append("javascript:");
        builder.append(methdName);
        builder.append("('");
        builder.append(param);
        builder.append("')");
        String jsUrl = builder.toString();
        if (Build.VERSION.SDK_INT < 19) {
            webView.loadUrl(jsUrl);//xxxxxjavascript方法名
        } else {
            webView.evaluateJavascript(jsUrl, new ValueCallback<String>() {//xxxxxjavascript方法名
                @Override
                public void onReceiveValue(String value) { //javascript 返回的结果
                    tvResult.setText(value);
                    System.out.print(value);
                }
            });
        }
    }


}
