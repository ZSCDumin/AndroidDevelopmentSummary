1、添加用户权限：
   <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
2、清单文件中添加provider信息如下：
    <provider
        android:name="android.support.v4.content.FileProvider"
        android:authorities="com.edu.zscdm.camerademo.fileprovider"
        android:exported="true"
        android:grantUriPermissions="true">
        <meta-data
            android:name="android.support.FILE_PROVIDER_PATHS"
            android:resource="@xml/file_paths" />
    </provider>
3、示例代码如下：
    public class MainActivity extends AppCompatActivity {
        public static final int TAKE_PHOTO = 1;
        public static final int CHOOSE_PHOTO = 2;
        private ImageView picture;
        private Uri imageUri;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            Button takePhoto = (Button) findViewById(R.id.take_photo);
            Button chooseFromAlbum = (Button) findViewById(R.id.choose_from_album);
            picture = (ImageView) findViewById(R.id.picture);
            takePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 创建File对象，用于存储拍照后的图片
                    //getExternalCacheDir()获取应用关联缓存目录：/sdcard/Android/data/<package name>/cache
                    File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
                    try {
                        if (outputImage.exists()) {
                            outputImage.delete();
                        }
                        outputImage.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (Build.VERSION.SDK_INT < 24) {//判断Android系统是否小于7.0,小于表示Android7.0以下系统
                        imageUri = Uri.fromFile(outputImage);
                    } else {
                        //从Android7.0开始直接使用真实路径是不安全的，会抛出FileUriExposedException异常，
                        //FileProvider是一种特殊的Uri，它可以选择性的将封装过的Uri共享给外部，提高应用的安全性。
                        imageUri = FileProvider.getUriForFile(MainActivity.this, "com.edu.zscdm.camerademo.fileprovider", outputImage);
                    }
                    // 启动相机程序
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, TAKE_PHOTO);
                }
            });
            chooseFromAlbum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE }, 1);
                    } else {
                        openAlbum();
                    }
                }
            });
        }

        private void openAlbum() {
            Intent intent = new Intent("android.intent.action.GET_CONTENT");
            intent.setType("image/*");
            startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
            switch (requestCode) {
                case 1:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        openAlbum();
                    } else {
                        Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
            }
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            switch (requestCode) {
                case TAKE_PHOTO:
                    if (resultCode == RESULT_OK) {
                        try {
                            // 将拍摄的照片显示出来
                            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                            picture.setImageBitmap(bitmap);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case CHOOSE_PHOTO:
                    if (resultCode == RESULT_OK) {
                        // 判断手机系统版本号
                        if (Build.VERSION.SDK_INT >= 19) {
                            // 4.4及以上系统使用这个方法处理图片
                            handleImageOnKitKat(data);
                        } else {
                            // 4.4以下系统使用这个方法处理图片
                            handleImageBeforeKitKat(data);
                        }
                    }
                    break;
                default:
                    break;
            }
        }

        /**
         * 对Uri进行封装
         * @param data
         */
        @TargetApi(19)
        private void handleImageOnKitKat(Intent data) {
            String imagePath = null;
            Uri uri = data.getData();
            Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
            if (DocumentsContract.isDocumentUri(this, uri)) {
                // 如果是document类型的Uri，则通过document id处理
                String docId = DocumentsContract.getDocumentId(uri);
                if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                    String id = docId.split(":")[1]; // 解析出数字格式的id
                    String selection = MediaStore.Images.Media._ID + "=" + id;
                    imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
                } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                    imagePath = getImagePath(contentUri, null);
                }
            } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                // 如果是content类型的Uri，则使用普通方式处理
                imagePath = getImagePath(uri, null);
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                // 如果是file类型的Uri，直接获取图片路径即可
                imagePath = uri.getPath();
            }
            displayImage(imagePath); // 根据图片路径显示图片
        }

        private void handleImageBeforeKitKat(Intent data) {
            Uri uri = data.getData();
            String imagePath = getImagePath(uri, null);
            displayImage(imagePath);
        }

        private String getImagePath(Uri uri, String selection) {
            String path = null;
            // 通过Uri和selection来获取真实的图片路径
            Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                }
                cursor.close();
            }
            return path;
        }

        private void displayImage(String imagePath) {
            if (imagePath != null) {
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                picture.setImageBitmap(bitmap);
            } else {
                Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
            }
        }
    }
