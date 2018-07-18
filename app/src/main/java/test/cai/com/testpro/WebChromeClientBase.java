package test.cai.com.testpro;

import android.content.Context;
import android.webkit.WebChromeClient;

public class WebChromeClientBase extends WebChromeClient {
    Context context;

    public WebChromeClientBase(Context context) {
        this.context = context;
    }

}
