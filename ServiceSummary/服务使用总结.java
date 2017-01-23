1、自定义服务类继承 Service：
    public class MyService extends Service {

        public MyService() {
        }

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }
2、重写 Service 类的方法：
  （1）onCreate() //只在第一次创建服务时调用
  （2）onStartCommand(Intent intent, int flags, int startId) //服务启动时调用
  （3）onDestroy() //服务结束时调用
  （4）onBind(Intent intent) //在活动中随时控制服务
3、在AndroidManifest.xml文件中注册：
    <service
         android:name=".MyService"
         android:enabled="true"
         android:exported="true" />
4、服务类完整范例如下：
    public class MyService extends Service {

        private DownloadBinder mBinder = new DownloadBinder();

        public MyService() {
        }

        @Override
        public IBinder onBind(Intent intent) {
            return mBinder;
        }

        /**
         * 第一次创建服务时调用
         */
        @Override
        public void onCreate() {
            super.onCreate();
            Log.d("MyService", "onCreate executed");
            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
            Notification notification = new NotificationCompat.Builder(this)
                    .setContentTitle("This is content title")
                    .setContentText("This is content text")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setContentIntent(pi)
                    .build();
            startForeground(1, notification);
        }

        /**
         * 服务启动时调用
         *
         * @param intent
         * @param flags
         * @param startId
         * @return
         */
        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            Log.d("MyService", "onStartCommand executed");
            return super.onStartCommand(intent, flags, startId);
        }

        /**
         * 服务结束时调用
         */
        @Override
        public void onDestroy() {
            super.onDestroy();
            Log.d("MyService", "onDestroy executed");
        }

        class DownloadBinder extends Binder {

            public void startDownload() {
                Log.d("MyService", "startDownload executed");
            }

            public int getProgress() {
                Log.d("MyService", "getProgress executed");
                return 0;
            }

        }
    }
5、服务基本操作：
  （1）启动服务：
        Intent startIntent = new Intent(this, MyService.class);
        startService(startIntent); // 启动服务
  （2）停止服务：
        Intent stopIntent = new Intent(this, MyService.class);
        stopService(stopIntent); // 停止服务

        /*******/
          或者在服务类的任意一个地方调用stopService()方法也行。
        /*******/
  （3）当同时调用了onCreate()方法和bindService()方法时要停止服务需调用以下两个方法：
       stopService(stopIntent); // 停止服务
       unbindService(connection);//解绑服务

6、绑定、解绑服务：
   （1）创建 ServiceConnection 匿名类：
        private ServiceConnection connection = new ServiceConnection() {//匿名类

             @Override
             public void onServiceDisconnected(ComponentName name) {
             }

             @Override
             public void onServiceConnected(ComponentName name, IBinder service) {
                 downloadBinder = (MyService.DownloadBinder) service;
                 downloadBinder.startDownload();//开始下载
                 downloadBinder.getProgress();//获取下载进度
             }
        };
   （2）绑定服务：
        Intent bindIntent = new Intent(this, MyService.class);
        bindService(bindIntent, connection, BIND_AUTO_CREATE); // 绑定服务
   （3）解绑服务：
        unbindService(connection); // 解绑服务
7、前台服务：
  （1）概念：类似于通知在状态栏显示正在运行的图标，不会因为系统内存不足而回收掉。
  （2）创建前台服务的代码：
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("This is content title")
                .setContentText("This is content text")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentIntent(pi)
                .build();
        startForeground(1, notification);
8、IntentService 使用方法：
  （1）作用：异步的，会自动停止的服务。
  （2）自定义 IntentService：
        public class MyIntentService extends IntentService {
            public MyIntentService() {
                super("MyIntentService"); // 调用父类的有参构造函数
            }
            @Override
            protected void onHandleIntent(Intent intent) {
                // 打印当前线程的id
                Log.d("MyIntentService", "Thread id is " + Thread.currentThread().getId());
            }
            @Override
            public void onDestroy() {
                super.onDestroy();
                Log.d("MyIntentService", "onDestroy executed");
            }
        }
  （3）与其等价的做法，在onStartCommand()方法中创建子线程，并调用stopSelf()方法：
        new Thread(new Runnable() {
            @Override
            public void run() {
                //处理具体的逻辑
                stopSelf();
            }
        }).start();
