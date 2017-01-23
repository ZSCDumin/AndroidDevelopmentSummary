1、获取 NotificationManager 实例：
   NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
2、使用 Build 构造器创建 Notification 对象：
   Notification notification = new NotificationCompat.Builder(this)
     .setContentTitle("This is content title")//设置通知标题
     .setContentText("This is content text")//设置标题正文内容
     .setWhen(System.currentTimeMillis())//设置消息时间
     .setSmallIcon(R.mipmap.ic_launcher)//设置消息的小图标
     .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))//设置消息的大图标
     .build();
3、调用 NotificationManager 的notify()方法将通知显示出来：
   manager.notify(1, notification);
4、PendingIntent 的作用相当于延迟执行的 Intent：

   /**********/FLAGS 有几种状态:/***********/

  （1）FLAG_CANCEL_CURRENT：如果 AlarmManager 管理的 PendingIntent 已经存在,那么将会取消当前的 PendingIntent，
       从而创建一个新的 PendingIntent.
  （2）FLAG_UPDATE_CURRENT：如果 AlarmManager 管理的 PendingIntent 已经存在,让新的 Intent 更新之前 Intent 对象数据,
       例如更新 Intent 中的 Extras,另外,我们也可以在 PendingIntent 的原进程中调用
       PendingIntent 的cancel()把其从系统中移除掉。
  （3）FLAG_NO_CREATE：如果 AlarmManager 管理的 PendingIntent 已经存在,那么将不进行任何操作,直接返回已经.
  （4）FLAG_ONE_SHOT：该PendingIntent只作用一次.在该PendingIntent对象通过send()方法触发过后,
       PendingIntent 将自动调用cancel()进行销毁,那么如果你再调用send()方法的话,系统将会返回一个 SendIntentException.
5、取消通知的两种做法：
  （1）NotificationCompat.Builder 中连缀一个 setAutoCancel(true)方法即可。
  （2）显示调用cancel()方法：
       NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
       manager.cancel(1);//显示调用cancel()方法
6、通知使用进阶技巧：
  （1）设置通知的声音：
       示例代码：
       setSound(Uri.fromFile(new File("/system/media/audio/ringtones/Luna.ogg")))
  （2）设置通知的震动：【下标为奇数时表示震动，为偶数时表示静止,使用时添加如下权限：
       <uses-permission android:name="android.permission.VIBRATE" />】
       示例代码：
       setVibrate(new long[]{0, 1000, 1000, 1000})
  （3）设置LED灯，参数分别表示颜色，亮起时长，暗去时长：
       示例代码：
       setLights(Color.GREEN, 1000, 1000)
  （4）使用通知的默认效果：
       setDefaults(NotificationCompat.DEFAULT_ALL)
  （5）封装长文字信息：
       示例代码：
       setStyle(new NotificationCompat.BigTextStyle().bigText("Learn how to build notifications, send and sync data, and use voice actions. Get the official Android IDE and developer tools to build apps for Android."))
  （6）设置大图片：
       示例代码：
       setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.big_image)))
  （7）设置通知的优先顺序。【PRIORITY_DEFAULT 表示默认重要程度，PRIORITY_MAX 表示重要程度最高，
       PRIORITY_MIN 表示重要程度最低，PRIORITY_HIGH 表示重要程度较高，PRIORITY_LOW 表示重要程度较低】
       示例代码：
       setPriority(NotificationCompat.PRIORITY_MAX)
