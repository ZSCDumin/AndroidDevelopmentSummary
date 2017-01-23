1、在build.gradle文件中dependencies闭包添加内容如下：
   compile 'com.squareup.okhttp3:okhttp:3.5.0'
2、OkHttp 的具体用法：

   /*********GET请求*********/
  （1）创建一个 OkHttpClient 的实例：
       OkHttpClient client = new OkHttpClient();
  （2）创建一个 Request 对象：
       Request request = new Request.Builder()
       .url("https://www.baidu.com")
       .build();
  （3）调用 OkHttpClient 的newCall()方法创建一个Call对象，并调用它的execute()方法发送请求并获取服务器返回的数据：
       Response response = client.newCall(request).execute();
  （4）获得具体的内容：
       String responseData = response.body().string();

   /***********POST请求***********/
   （1）创建一个 OkHttpClient 的实例：
        OkHttpClient client = new OkHttpClient();
   （2）先构建一个RequestBody对象来存放待提交的参数：
        RequestBody requestBody = new FormBody.Builder()
        .add("username","admin")
        .add("password","123456")
        .build();
   （3）在 Request.Builder 中调用一下post()方法，并将 RequestBody 对象传入：
        Request request = new Request.Builder().
        url("https://www.baidu.com")
        .post(requestBody)
        .build();
   （4）调用execute()方法发送请求并获取服务器返回的数据即可：
        Response response = client.newCall(request).execute();
   （5）获得具体的内容：
        String responseData = response.body().string();
