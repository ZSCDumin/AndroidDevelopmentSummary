1、FloatingActionButton 使用方法：
  （1）布局文件代码：
       <android.support.design.widget.FloatingActionButton
            android:id="@+id/fab"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="bottom|end"
            android:layout_margin="16dp"
            android:src="@drawable/ic_done"
            app:elevation="8dp" />
  （2）按钮点击事件代码如下：
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "FloatingActionButton Clicked", Toast.LENGTH_SHORT).show();
            }
        });
2、Snackbar 使用方法：
    Snackbar.make(view, "Data deleted", Snackbar.LENGTH_SHORT) //第三个参数表示显示时长
            .setAction("Undo", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "Data restored", Toast.LENGTH_SHORT).show();
                }
            })
            .show();