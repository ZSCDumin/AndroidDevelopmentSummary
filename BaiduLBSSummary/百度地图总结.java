1、基础配置同百度地图获取位置信息。
2、布局文件代码：
    <?xml version="1.0" encoding="utf-8"?>
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">
        <com.baidu.mapapi.map.MapView
            android:id="@+id/bmapView"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:clickable="true" />
    </LinearLayout>
3、逻辑代码如下：
    /**
    * 百度地图
    */
    public class Main2Activity extends AppCompatActivity {

        public LocationClient mLocationClient;
        List<String> permissionList = new ArrayList<>();//权限列表，记录未允许的权限
        private BaiduMap baiduMap;
        private boolean isFirstLocate = true;//第一次定位时执行
        private MapView mapView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mLocationClient = new LocationClient(getApplicationContext());
            mLocationClient.registerLocationListener(new MyLocationListener());//注册定位监听器，当获取到位置信息时，会回调这个监听器
            SDKInitializer.initialize(getApplicationContext());//一定要先初始化，再加载布局
            setContentView(R.layout.activity_main2);
            mapView = (MapView) findViewById(R.id.bmapView);
            baiduMap = mapView.getMap();
            baiduMap.setMyLocationEnabled(true);//开启此功能
            checkPermission();
        }

        public void checkPermission() {
            /**
            * 判断单个权限是否已经允许
            */
            if (ContextCompat.checkSelfPermission(Main2Activity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (ContextCompat.checkSelfPermission(Main2Activity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (ContextCompat.checkSelfPermission(Main2Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

            /**
            * 判断所有权限是否已经申请完成
            */
            if (!permissionList.isEmpty()) {
                String[] permissions = permissionList.toArray(new String[permissionList.size()]);
                ActivityCompat.requestPermissions(Main2Activity.this, permissions, 1);
            } else {
                requestLocation();//请求位置信息
            }
        }

        private void requestLocation() {
			initLocation();
            mLocationClient.start();//开始定位
        }

        private void initLocation() {
            LocationClientOption option = new LocationClientOption();
            option.setScanSpan(5000);//设置更新间隔，5秒钟
            option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);//传感器模式，只进行GPS定位
            //LocationClientOption.LocationMode.Hight_Accuracy：高精度模式，在GPS信号正常的时候优先选择GPS,否则选择网络。
            //LocationClientOption.LocationMode.Battery_Saving：省电模式，只使用网络进行定位

            option.setIsNeedAddress(true);//获取当地详细的位置信息
            mLocationClient.setLocOption(option);
        }


        private void navigateTo(BDLocation location) {
            if (isFirstLocate) {
                Toast.makeText(this, "定位到：" + location.getAddrStr(), Toast.LENGTH_SHORT).show();
                Log.i("TAG", location.getLatitude() + " " + location.getLongitude());
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());//封装位置信息
                MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
                baiduMap.animateMapStatus(update);
                update = MapStatusUpdateFactory.zoomTo(16f);//设置缩放级别，3-19，值越大越精确
                baiduMap.animateMapStatus(update);//将地图移动到指定的经纬度
                isFirstLocate = false;
            }
            MyLocationData.Builder locationBuilder = new MyLocationData.
                    Builder();
            locationBuilder.latitude(location.getLatitude());
            locationBuilder.longitude(location.getLongitude());
            MyLocationData locationData = locationBuilder.build();
            baiduMap.setMyLocationData(locationData);//显示我的位置
        }

        @Override
        protected void onResume() {
            super.onResume();
            mapView.onResume();
        }

        @Override
        protected void onPause() {
            super.onPause();
            mapView.onPause();
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            mapView.onDestroy();
            baiduMap.setMyLocationEnabled(false);//关闭此功能
        }

        public class MyLocationListener implements BDLocationListener {

            @Override
            public void onReceiveLocation(BDLocation location) {
                if (location.getLocType() == BDLocation.TypeGpsLocation
                        || location.getLocType() == BDLocation.TypeNetWorkLocation) {//网络或者GPS
                    navigateTo(location);
                }
            }
        }
    }
