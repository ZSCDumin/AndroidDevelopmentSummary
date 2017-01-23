1、Android 6.0 之前的系统没有采用运行时权限,所以打电话的代码如下：
  （1）添加用户权限如下：
      <uses-permission android:name="android.permission.CALL_PHONE" />
  （2）采用隐式 Intent 打开拨号服务代码如下：
      try {
          Intent intent = new Intent(Intent.ACTION_CALL);
          intent.setData(Uri.parse("tel:10086"));
          startActivity(intent);
      } catch (SecurityException e) {
          e.printStackTrace();
      }
2、Android 6.0 之后的系统采用运行时权限,所以打电话的代码如下：
  （1）添加用户权限如下：
       <uses-permission android:name="android.permission.CALL_PHONE" />
  （2）检查是否申请了权限：
       //未申请权限
       if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) !=
               PackageManager
                       .PERMISSION_GRANTED) {
           //申请权限
           ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission
                   .CALL_PHONE}, 1);//最后一个参数表示请求码
       }
       //已申请权限
       else {
           //权限已经申请,直接拨打电话
           call();
       }
  （3）请求结果的回调函数：
       @Override
       public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
               grantResults) {
           switch (requestCode) {
               case 1:
                   if (grantResults.length > 0 && grantResults[0] == PackageManager
                           .PERMISSION_GRANTED) {//用户同意拨打电话的权限
                       call();
                   } else {
                       Toast.makeText(this, "你拒绝了打电话的权限！", Toast.LENGTH_SHORT).show();
                   }
                   break;
               default:
           }
        }
