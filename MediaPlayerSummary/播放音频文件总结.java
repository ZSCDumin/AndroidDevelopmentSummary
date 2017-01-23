1、创建 MediaPlay 对象：
   MediaPlayer mediaPlayer = new MediaPlayer();
2、设置音频文件的路径：
   File file = new File(Environment.getExternalStorageDirectory(), "music.mp3");
   mediaPlayer.setDataSource(file.getPath());
3、调用prepare()方法使得 MediaPlay 对象进入准备状态：
   mediaPlayer.prepare();
4、调用start()方法开始播放音频：
   mediaPlayer.start();
5、调用pause()方法暂停播放：
   mediaPlayer.pause();
6、调用reset()方法停止播放：
   mediaPlayer.reset();
7、添加用户权限：
   <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
