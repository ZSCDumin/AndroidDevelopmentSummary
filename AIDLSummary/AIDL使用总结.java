1、AIDL 概念介绍：
（1）为使应用程序之间能够彼此通信，Android 提供了IPC(Inter Process Communication, 进程间通信)的一种独特实现：
     AIDL （Android Interface Definition Language， Android 接口定义语言）。
（2）我们知道 Android 中要实现 IPC，有好多种方式：
      a、在 Intent中附加 extras 来传递信息。
      b、共享文件。
      c、SharedPreferences（不建议在进程间通信中使用，因为在多进程模式下，系统对 SharedPreferences 的读/写会变得不可靠，面对高并发的读/写访问，有很大几率会丢失数据）。
      d、基于 Binder的 AIDL。
      e、基于 Binder的 Messenger（翻译为"信使"，其实 Messenger 本质上也是 AIDL，只不过系统做了封装以方便上层调用）。
      f、Socket。
      g、天生支持跨进程访问的 ContentProvider。
2、服务端代码如下：
  （1）AIDL 文件代码如下：
      // IMyAidlInterface.aidl
      package com.edu.zscdm.server;
      // Declare any non-default types here with import statements
      interface IMyAidlInterface {
          int add(in int a, in int b);
      }
  （2）创建 Service 服务代码：
        public class MyService extends Service {
            private IBinder iBinder = new IMyAidlInterface.Stub() {
                @Override
                public int add(int a, int b) throws RemoteException {
                    return a + b;
                }
            };

            public MyService() {
            }

            @Override
            public IBinder onBind(Intent intent) {
                return iBinder;
            }
        }

3、客户端代码如下：
  （1）复制同样的一份AIDL文件，并且包名保持一致。
  （2）绑定服务代码如下：
        public class MainActivity extends AppCompatActivity {
            private TextView textView;
            private IMyAidlInterface iMyAidlInterface;
            private ServiceConnection mServiceConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    Toast.makeText(getApplicationContext(), "连接成功", Toast.LENGTH_SHORT).show();
                    iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    Toast.makeText(getApplicationContext(), "连接失败", Toast.LENGTH_SHORT).show();
                    iMyAidlInterface = null;
                }
            };

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                textView = (TextView) findViewById(R.id.tv);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            textView.setText(iMyAidlInterface.add(2, 3) + "");
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            protected void onStart() {
                super.onStart();
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.edu.zscdm.server", "com.edu.zscdm.server.MyService"));
                bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
            }

            @Override
            protected void onDestroy() {
                super.onDestroy();
                unbindService(mServiceConnection);
            }
        }

4、常见注意事项：
  （1）使用 AndroidStudio 创建的 AIDL 编译时找不到自定义类的解决办法：
        sourceSets {
            main {
                  manifest.srcFile 'src/main/AndroidManifest.xml'
                  java.srcDirs = ['src/main/java', 'src/main/aidl']
                  resources.srcDirs = ['src/main/java', 'src/main/aidl']
                  aidl.srcDirs = ['src/main/aidl']
                  res.srcDirs = ['src/main/res']
                  assets.srcDirs = ['src/main/assets']
            }
        }
  （2）客户端要有相同的一份 AIDL 文件，并且包名只能一致。否则会产生错误。
