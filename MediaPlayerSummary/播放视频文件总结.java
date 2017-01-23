1、绑定 VideoView 控件：
   videoView = (VideoView) findViewById(R.id.video_view);
2、指定视频文件的路径：
   File file = new File(Environment.getExternalStorageDirectory(), "movie.mp4");
   videoView.setVideoPath(file.getPath());
3、开始播放视频：
   videoView.start();
4、暂停播放视频：
   videoView.pause();
5、重新播放视频：
   videoView.resume();
6、添加用户权限：
   <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
