1、AlertDialog使用方法如下：

	(1)示例代码：
	 
		AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
		dialog.setTitle("对话框");
		dialog.setMessage("正在上传图片...");
		dialog.setCancelable(true);
		dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//逻辑代码
			}
		});
		dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//逻辑代码
			}
		});
		dialog.show();

2、ProgressDialog使用方法如下：

	(1)示例代码如下：
	
		ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
		progressDialog.setTitle("进度条对话框！");
		progressDialog.setMessage("正在加载...");
		progressDialog.setCancelable(true);//设置是否允许取消
		progressDialog.show();