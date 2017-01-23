1、在buil.gradle文件中配置如下:

    compile 'com.google.zxing:core:3.1.0'
    compile 'cn.bingoogolapple:bga-qrcodecore:1.0.7@aar'
    compile 'cn.bingoogolapple:bga-zxing:1.0.7@aar'

2、二位码生成代码示例:

   (1)生成普通的二维码
		/**
		 * 创建普通二维码
		 */
		private void createQRCode() {
			//生成二维码，第一个参数为要生成的文本，第二个参数为生成尺寸，第三个参数为生成回调
			QRCodeEncoder.encodeQRCode(etInput.getText().toString().trim(), DisplayUtils.dp2px(GenerateActivity.this, 160), new QRCodeEncoder.Delegate() {
				/**
				 * 生成成功
				 * @param bitmap
				 */
				@Override
				public void onEncodeQRCodeSuccess(Bitmap bitmap) {
					ivQr.setImageBitmap(bitmap);
				}

				/**
				 * 生成失败
				 */
				@Override
				public void onEncodeQRCodeFailure() {
					Toast.makeText(GenerateActivity.this, "生成中文二维码失败", Toast.LENGTH_SHORT).show();
				}
			});
		}

   (2)带有头像的二维码
		/**
		 * 创建带Logo的二维码
		 */
		private void createQRCodeWithLogo() {

			//生成二维码，第一个参数为要生成的文本，第二个参数为生成尺寸，第三个参数为生成二维码颜色，第四个参数为logo资源，第五个参数为生成回调
			QRCodeEncoder.encodeQRCode(etInput.getText().toString().trim(), DisplayUtils.dp2px(GenerateActivity.this, 160), Color.parseColor("#000000"), ((BitmapDrawable) ivLogo.getDrawable()).getBitmap(), new QRCodeEncoder.Delegate() {
				@Override
				public void onEncodeQRCodeSuccess(Bitmap bitmap) {
					ivQr.setImageBitmap(bitmap);
				}

				@Override
				public void onEncodeQRCodeFailure() {
					Toast.makeText(GenerateActivity.this, "生成带logo的中文二维码失败", Toast.LENGTH_SHORT).show();
				}
			});
		}
		
3、二维码解析代码示例:

	   /**
		 * 解析
		 */
		public void decodeQRCode() {
			Bitmap bitmap = ((BitmapDrawable) ivQr.getDrawable()).getBitmap();
			decode(bitmap, "解析二维码失败");
		}

		/**
		 * 解析二维码,可以解析二维码、带logo二维码、条形码
		 *
		 * @param bitmap
		 * @param err
		 */
		private void decode(Bitmap bitmap, final String err) {
			QRCodeDecoder.decodeQRCode(bitmap, new QRCodeDecoder.Delegate() {
				@Override
				public void onDecodeQRCodeSuccess(String result) {
					Toast.makeText(GenerateActivity.this, result, Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onDecodeQRCodeFailure() {
					Toast.makeText(GenerateActivity.this, err, Toast.LENGTH_SHORT).show();
				}
			});
		}

4、二维码扫描代码如下:

        package com.edu.zscdm.qrcodedemo;
		
		import android.app.Activity;
		import android.os.Bundle;
		import android.os.Vibrator;
		import android.widget.Toast;
		import butterknife.BindView;
		import butterknife.ButterKnife;
		import cn.bingoogolapple.qrcode.core.QRCodeView;
		import cn.bingoogolapple.qrcode.zxing.ZXingView;

		public class ScanActivity extends Activity implements QRCodeView.Delegate {

			@BindView(R.id.zx_view)
			ZXingView mQR;

			@Override
			protected void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_scan);
				ButterKnife.bind(this);

				//设置结果处理
				mQR.setResultHandler(this);
				//开始读取二维码
				mQR.startSpot();
			}

			/**
			 * 扫描二维码方法大全（已知）
			 * mQR.startCamera();               开启预览，但是并未开始识别
			 * mQR.stopCamera();                停止预览，并且隐藏扫描框
			 * mQR.startSpot();                 开始识别二维码
			 * mQR.stopSpot();                  停止识别
			 * mQR.startSpotAndShowRect();      开始识别并显示扫描框
			 * mQR.stopSpotAndHiddenRect();     停止识别并隐藏扫描框
			 * mQR.showScanRect();              显示扫描框
			 * mQR.hiddenScanRect();            隐藏扫描框
			 * mQR.openFlashlight();            开启闪光灯
			 * mQR.closeFlashlight();           关闭闪光灯
			 * mQR.startSpotDelay(ms)           延迟ms毫秒后开始识别
			 */

			/**
			 * 扫描二维码成功
			 *
			 * @param result
			 */
			@Override
			public void onScanQRCodeSuccess(String result) {

				Toast.makeText(ScanActivity.this, result, Toast.LENGTH_SHORT).show();
				//震动
				vibrate();
				//停止预览
				mQR.stopCamera();

			}

			/**
			 * 打开相机出错
			 */
			@Override
			public void onScanQRCodeOpenCameraError() {
				Toast.makeText(ScanActivity.this, "打开相机出错！请检查是否开启权限！", Toast.LENGTH_SHORT).show();
			}

			/**
			 * 震动
			 */
			private void vibrate() {
				Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
				vibrator.vibrate(200);
			}

			@Override
			protected void onStart() {
				super.onStart();

				//启动相机
				mQR.startCamera();
			}

			@Override
			protected void onStop() {
				mQR.stopCamera();
				super.onStop();
			}
		}

5、完整项目请参考此网址"https://github.com/ZSCDumin/ZXingDemo.git"