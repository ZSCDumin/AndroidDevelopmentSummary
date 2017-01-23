1、使用Handler消息传递机制

   （1）主线程中定义Handler：

        private Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        textView.setText("handleMessage方法！");//更新UI
                        break;
                }
            }
        };
    
   （2）子线程发消息，通知Handler完成UI更新

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);//在子线程有一段耗时操作,比如请求网络
                    mHandler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
   
    
2、使用AsyncTask异步任务

        class MyAsync extends AsyncTask<String, String, String> {
            @Override
            protected String doInBackground(String... params) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                tv.setText("更新后的TextView");
            }
        }

3、使用runOnUiThread(action)方法

        new Thread() {
            public void run() {
                //这儿是耗时操作，完成之后更新UI；
                runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        //更新UI
                        imageView.setImageBitmap(bitmap);
                    }
                });
            }
        }.start();

        如果在非上下文类中（Activity），可以通过传递上下文实现调用；
        Activity activity = (Activity) imageView.getContext();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(bitmap);
            }
        });

        这种方法使用比较灵活，但如果 Thread 定义在其他地方，需要传递 Activity 对象；

4、使用Handler的post(Runnabel r)方法

        public void post()
        {
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(5000);//在子线程有一段耗时操作,比如请求网络
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText("post 方法！");//更新UI
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }