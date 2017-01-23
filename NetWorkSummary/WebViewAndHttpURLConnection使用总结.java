1、WebView 使用方法：
  （1）添加网络权限：
       <uses-permission android:name="android.permission.INTERNET" />
  （2）逻辑代码如下：
       示例代码一：
        public class MainActivity extends AppCompatActivity {
            private WebView webView;
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                webView= (WebView) findViewById(R.id.web_view);

                //设置网页
                WebSettings webSettings = webView.getSettings();
                webSettings.setDisplayZoomControls(true);//支持显示缩放
                webSettings.setJavaScriptEnabled(true);//支持JavaScript脚本
                webSettings.setSupportZoom(true);//支持缩放
                webView.loadUrl("https://www.baidu.com");

                //设置默认浏览器，不调用系统的浏览器
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        webView.loadUrl(url);
                        return super.shouldOverrideUrlLoading(view, url);
                    }

                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                        return super.shouldOverrideUrlLoading(view, request);
                    }
                });
            }
            /**
             * 处理按键按下时的操作
             *
             * @param keyCode
             * @param event
             * @return boolean
             */
            @Override
            public boolean onKeyDown(int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {//如果按下的是返回键
                    if (webView.canGoBack()) {//如果能够返回
                        webView.goBack();
                    } else {
                        System.exit(0);//退出程序
                    }
                    return true;
                }
                return super.onKeyDown(keyCode, event);
            }
        }
       示例代码二：
        public class Main2Activity extends AppCompatActivity {

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                /*********方式一*******/
                setContentView(R.layout.activity_main2);
                Uri uri = Uri.parse("https://www.baidu.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

                /*********方式二*******/
                WebView webview = new WebView(this);
                setContentView(webview);
                //1、加载Url资源
                webview.loadUrl("https://www.baidu.com");
                //2、加载html资源
                String summary = "<html><body>You scored <b>192</b> points.</body></html>";
                webview.loadData(summary, "text/html", null);

            }
        }
2、HttpURLConnection 获取数据的方法
  （1）获取 HttpURLConnection 实例：
        URL url = new URL("https://www.baidu.com");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
  （2）设置Http请求所使用的方法：
        connection.setRequestMethod("GET");
  （3）自由定制：
        connection.setConnectTimeout(8000);//设置连接超时（毫秒）
        connection.setReadTimeout(8000);//设置读取超时（毫秒）
  （4）调用getInputStream()方法获取到服务器返回的输入流：
        InputStream in = connection.getInputStream();
  （5）下面对获取到的输入流进行读取：
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        showResponse(response.toString());
  （5）调用disconnect()方法将这个Http连接关闭：
        connection.disconnect();
3、HttpURLConnection 提交数据给服务器的方法
  （1）设置Http请求所使用的方法：
       connection.setRequestMethod("POST");
  （2）向服务器提交用户名和密码：
       DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
       outputStream.writeBytes("username = admin & password = 123456");
