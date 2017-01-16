1、广播分类：
  （1）标准广播：
          完全异步的广播，所有广播接收者同时接收到广播。
  （2）有序广播：
          同步执行的广播，同一时刻只有一个广播接收者能够接收到广播。
       广播有先后之分，优先级高的先接收到广播，并且前面的广播可以截断后面的广播。

2、动态注册监听网络变化
   示例代码如下：
   public class MainActivity extends AppCompatActivity {

      private IntentFilter intentFilter;
      private NetWorkChangeReceiver netWorkChangeReceiver;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);
          //动态注册
          intentFilter = new IntentFilter();//intent过滤器
          intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");//添加action
          netWorkChangeReceiver = new NetWorkChangeReceiver();//广播接收器
          registerReceiver(netWorkChangeReceiver, intentFilter);//注册广播接收器
      }

      @Override
      protected void onDestroy() {
          super.onDestroy();
          unregisterReceiver(netWorkChangeReceiver);//取消注册广播接收器
      }

      class NetWorkChangeReceiver extends BroadcastReceiver {

          /**
           * 当有广播来时，此方法执行
           * @param context
           * @param intent
           */
          @Override
          public void onReceive(Context context, Intent intent) {
              ConnectivityManager connectivityManager = (ConnectivityManager)
                      getSystemService(Context.CONNECTIVITY_SERVICE);
              NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
              if (networkInfo != null && networkInfo.isAvailable()) {
                  Toast.makeText(context, "当前网络可用！", Toast.LENGTH_SHORT).show();
              }
              Toast.makeText(context, "当前网络不可用！", Toast.LENGTH_SHORT).show();
          }
      }
   }

   记得添加用户权限：
   <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

3、静态注册监听网络变化
    在 AndroidManifest 文件中添加代码如下：
    <receiver android:name=".NetWorkChangeReceiver">
         <intent-filter>
             <action android:name="android.net.conn.CONNECTIVITY_CHANGE">
             </action>
         </intent-filter>
     </receiver>
     记得添加用户权限：
     <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
4、静态注册实现开机启动
  （1） AndroidStudio 快捷方式创建一个广播接收器，代码如下：

       public class BootCompleteReceiver extends BroadcastReceiver {
           @Override
           public void onReceive(Context context, Intent intent) {
               Toast.makeText(context, "Boot Complete", Toast.LENGTH_SHORT).show();
           }
       }
  （2）在 AndroidManifest 文件中自动生成代码如下：
       <receiver
            android:name=".BootCompleteReceiver"
            android:enabled="true"  //表示是否启用这个广播接收器
            android:exported="true">  //表示是否允许这个广播接收器接受本应用程序之外的广播
            <intent-filter>
                <action android:name="android.intent.action.BOOT_COMPLETED" />
            </intent-filter>
       </receiver>
  （3）添加用户权限
      <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
      <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
5、发送自定义广播
  （1）发送标准广播
       a. AndroidStudio 快捷方式创建一个广播接收器，代码如下：
           public class MyBroadcastReceiver extends BroadcastReceiver {
               @Override
               public void onReceive(Context context, Intent intent) {
                   Toast.makeText(context,"recevied in MyBroadcastReceiver",Toast.LENGTH_SHORT).show();
               }
           }
       b. 在 AndroidManifest 文件中自动生成代码如下：
          <receiver
             android:name=".AnotherBroadcastReceiver"
             android:enabled="true"
             android:exported="true">
             <intent-filter>
                 <action android:name="com.edu.zscdm.broadcastreceiver.MY_BROADCAST" />
             </intent-filter>
          </receiver>
       c. 在 MainActivity 中添加代码如下：
          Intent intent = new Intent("com.edu.zscdm.broadcastreceiver.MY_BROADCAST");
          sendBroadcast(intent);//发送广播
  （2）发送有序广播
        a. AndroidStudio 快捷方式创建一个广播接收器，代码如下：
            public class AnotherBroadcastReceiver extends BroadcastReceiver {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Toast.makeText(context,"recevied in AnotherBroadcastReceiver",Toast.LENGTH_SHORT).show();
                    abortBroadcast();//截断广播，优先级比其低的无法收到广播消息
                }
            }
        b. 在 AndroidManifest 文件中自动生成代码如下：
           <receiver
              android:name=".AnotherBroadcastReceiver"
              android:enabled="true"
              android:exported="true">
              <!-- android:priority="100" 表示优先级最高，优先级较高的广播接收器
                   就可以先接收到广播。-->
              <intent-filter android:priority="100">
                  <action android:name="com.edu.zscdm.broadcastreceiver.MY_BROADCAST" />
              </intent-filter>
           </receiver>
       c. 在 MainActivity 中添加代码如下：
          Intent intent = new Intent("com.edu.zscdm.broadcastreceiver.MY_BROADCAST");
          sendOrderedBroadcast(intent, null);//发送有序广播
6、使用本地广播
    示例代码如下：
    public class MainActivity extends AppCompatActivity implements View.OnClickListener {
        private Button btn;//按钮
        private IntentFilter intentFilter;//intent过滤器
        private LocalReceiver localReceiver;//广播接收器
        private LocalBroadcastManager localBroadcastManager;//广播管理器

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            btn = (Button) findViewById(R.id.btn);
            btn.setOnClickListener(this);
            localBroadcastManager=LocalBroadcastManager.getInstance(this);//实例化LocalBroadcastManager
            intentFilter=new IntentFilter();//实例化intent过滤器
            intentFilter.addAction("com.edu.zscdm.broadcastdemo2.LOCAL_BROADCAST");//添加action
            localReceiver=new LocalReceiver();//实例化广播接收器
            localBroadcastManager.registerReceiver(localReceiver,intentFilter);//注册广播接收器
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            localBroadcastManager.unregisterReceiver(localReceiver);//取消注册广播接收器
        }

        @Override
        public void onClick(View v) {
            Intent intent=new Intent("com.edu.zscdm.broadcastdemo2.LOCAL_BROADCAST");//隐式Intent
            localBroadcastManager.sendBroadcast(intent);//发送广播
        }
        class LocalReceiver extends BroadcastReceiver{
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(context,"recevied localbroadcast",Toast.LENGTH_SHORT).show();//弹出提示框
            }
        }
    }
