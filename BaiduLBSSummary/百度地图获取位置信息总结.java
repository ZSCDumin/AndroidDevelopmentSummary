1、下载基础地图和定位地图SDK。
2、添加SDK的jar包到项目的libs目录下，
3、新建一个目录jniLibs与Java目录并列，把so文件全部拷贝过来。
4、按下Sync按钮进行同步。
5、AndroidManifest 文件配置如下：
    <?xml version="1.0" encoding="utf-8"?>
    <manifest xmlns:android="http://schemas.android.com/apk/res/android"
        package="com.edu.zscdm.lbsdemo">

        <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" /> //粗糙定位
        <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" /> //精确定位
        <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
        <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
        <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
        <uses-permission android:name="android.permission.READ_PHONE_STATE" />
        <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
        <uses-permission android:name="android.permission.INTERNET" />
        <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS" />//允许在SD卡中创建和删除文件
        <uses-permission android:name="android.permission.WAKE_LOCK" /> //锁屏开关

        <application
            android:allowBackup="true"
            android:icon="@mipmap/ic_launcher"
            android:label="@string/app_name"
            android:roundIcon="@mipmap/ic_launcher_round"
            android:supportsRtl="true"
            android:theme="@style/AppTheme">
            <meta-data
                android:name="com.baidu.lbsapi.API_KEY"
                android:value="hpsOsyygaYNWKUazSIwAdDW31oqyt9HG" />  //YOUR API KEY,只修改这个地方，其他地方不变
            <activity android:name=".MainActivity">
                <intent-filter>
                    <action android:name="android.intent.action.MAIN" />

                    <category android:name="android.intent.category.LAUNCHER" />
                </intent-filter>
            </activity>
            <service
                android:name="com.baidu.location.f"
                android:enabled="true"
                android:process=":remote"/>
        </application>

    </manifest>
6、逻辑代码如下：
    public class MainActivity extends AppCompatActivity {

        public LocationClient mLocationClient;
        private TextView positionText;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mLocationClient = new LocationClient(getApplicationContext());
            mLocationClient.registerLocationListener(new MyLocationListener());//注册定位监听器，当获取到位置信息时，会回调这个监听器
            setContentView(R.layout.activity_main);

            positionText = (TextView) findViewById(R.id.position_text_view);
            List<String> permissionList = new ArrayList<>();//权限列表，记录未允许的权限

            /**
            * 判断是否单个权限是否已经允许
            */
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

            /**
            * 判断所有权限是否已经申请完成
            */
            if (!permissionList.isEmpty()) {
                String[] permissions = permissionList.toArray(new String[permissionList.size()]);
                ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
            } else {
                requestLocation();//请求位置信息
            }
        }

        private void requestLocation() {
            //initLocation();
            mLocationClient.start();//开始定位
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
            switch (requestCode) {
                case 1:
                    if (grantResults.length > 0) {
                        for (int result : grantResults) {
                            if (result != PackageManager.PERMISSION_GRANTED) {
                                Toast.makeText(this, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                                finish();
                                return;
                            }
                        }
                        requestLocation();
                    } else {
                        Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    break;
                default:
            }
        }

        public class MyLocationListener implements BDLocationListener {

            @Override
            public void onReceiveLocation(BDLocation location) {
                StringBuilder currentPosition = new StringBuilder();
                currentPosition.append("纬度：").append(location.getLatitude()).append("\n");//获取纬度信息
                currentPosition.append("经线：").append(location.getLongitude()).append("\n");//获取经度信息

                currentPosition.append("定位方式：");
                if (location.getLocType() == BDLocation.TypeGpsLocation) {
                    currentPosition.append("GPS");
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                    currentPosition.append("网络");
                }
                positionText.setText(currentPosition);
            }
        }
    }
7、更新当前位置信息代码：
    private void requestLocation() {
		initLocation();
        mLocationClient.start();//开始定位
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);//设置更新间隔，5秒钟。
        option.setIsNeedAddress(true);//获取当地详细的位置信息。
        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);//传感器模式，只使用GPS定位。
        //LocationClientOption.LocationMode.Hight_Accuracy：高精度模式，在GPS信号正常的时候优先选择GPS,否则选择网络。
        //LocationClientOption.LocationMode.Battery_Saving：省电模式，只使用网络进行定位。
        mLocationClient.setLocOption(option);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();//停止定位
    }