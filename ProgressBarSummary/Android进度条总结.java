1、布局文件代码如下：

   <ProgressBar
  	android:id="@+id/progress_bar"
  	style="@android:style/Widget.ProgressBar.Horizontal"  <!-- 进度条样式 -->
  	android:layout_width="match_parent"
  	android:layout_height="wrap_content"
  	android:layout_below="@+id/textView"
  	android:max="100" />  <!-- 最大值为100 -->

2、MainActivity中代码：

    private int progress;//当前进度
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    progressBar.setProgress(progress);
                    textView.setText(progress+"%");//显示当前进度，以百分号的形式
                    break;
            }
        }
    };
    public void updateProgress() {//更新进度条函数
        progress = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progress < 100) {
                    try {
                        Thread.sleep(1000);
                        mHandler.sendEmptyMessage(0);
                        progress += 1;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
