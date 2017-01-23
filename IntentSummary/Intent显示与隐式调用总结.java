一、Intent显示调用

   1、范例代码如下
        Intent intent=new Intent(MainActivity.this,Main2Activity.class);  //当前Activity、要跳转的Activity
        startActivity(intent); //启动Activity

二、Intent隐式调用

   1、注意事项
        Intent隐式调用时，它并不明确指出调用哪一个活动，交由系统去分析找出合适的活动去启动。
        一个Intent中只能有一个action，但可以包含多个category。
		<category android:name="android.intent.category.DEFAULT"/> //这行代码不能省略，否则会运行出错

   2、范例代码如下
        Intent intent=new Intent("com.dumin.zsc.intentdemo.ACTION_START");
        startActivity(intent);

   3、AndroidManifest文件代码如下
        <activity
            android:name=".Main2Activity">
            <intent-filter>
                <action android:name="com.dumin.zsc.intentdemo.ACTION_START"/>   //设置要启动的Activity
                <category android:name="android.intent.category.DEFAULT"/>       //设置默认启动方式
				<category android:name="com.dumin.zsc.intentdemo.MY_CATEGORY"/>
            </intent-filter>
        </activity>
        <activity
            android:name=".Main3Activity">
            <intent-filter>
                <action android:name="com.dumin.zsc.intentdemo.ACTION_START"/>   //设置要启动的Activity
                <category android:name="android.intent.category.DEFAULT"/>       //设置默认启动方式
				<category android:name="com.dumin.zsc.intentdemo.MY_CATEGORY"/>
            </intent-filter>
        </activity>

		/************************系统会给出两个活动选择供你选择，由你自己决定启动哪一个活动**********************/

   4、Intent启动其他程序的活动
        (1) 调用系统浏览器打开"百度首页"
		    //指定Intent的Action为：Intent.ACTION_VIEW，这个是安卓系统内置的一个动作
			Intent it=new Intent(Intent.ACTION_VIEW);
			//将字符串解析成Uri对象，并调用setData方法将这个对象传递进去
			it.setData(Uri.parse("https://www.baidu.com"));
			startActivity(it);

		(2) 调用系统拨号界面
		    Intent it = new Intent(Intent.ACTION_DIAL);
            it.setData(Uri.parse("tel:10086"));
            startActivity(it);

		(3) 向下一个活动传递数据
	        MainActivity代码如下：
			String data="Hello SecondActivity!";
			Intent intent = new Intent(MainActivity.this,SecondActivity.class);
			intent.putExtra("extra_data",data);
			startActivity(intent);

		    SecondActivity代码如下：
		    Intent intent=getIntent();
			String data=intent.getStringExtra("extra_data");
			Log.i("data",data);

		(4) 向上一个活动返回数据
		    MainActivity代码如下：

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,SecondActivity.class);
				startActivityForResult(intent,1);
			}

            //在SecondActivity被销毁时会回调上一个活动的onActivityResult()方法
			@Override
			protected void onActivityResult(int requestCode, int resultCode, Intent data) {
				//通过requestCode判断数据来源，通过resultCode判断处理结果是否成功，从data中取出数据
				switch (requestCode){
					case 1:
						if(resultCode==RESULT_OK){
							String returnedData=data.getStringExtra("data_extra");
							Log.d("FirstActivity",returnedData);
						}
						break;
					default:
				}
			}

	        SecondActivity代码如下：

			@Override
			public void onClick(View v) {
				Intent intent=getIntent();
				intent.putExtra("data_extra","Hello FirstActivity!");
				setResult(RESULT_OK,intent);
				finish();
			}
