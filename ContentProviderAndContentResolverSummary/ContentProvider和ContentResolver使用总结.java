1、读取手机联系人：
   (1)添加用户权限：
      <uses-permission android:name="android.permission.READ_CONTACTS" />
   (2)示例代码如下：
      private void readContacts() {
           Cursor cursor = null;
           try {
               // 查询联系人数据
               cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
               if (cursor != null) {
                   while (cursor.moveToNext()) {
                       // 获取联系人姓名
                       String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                       // 获取联系人手机号
                       String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                       contactsList.add(displayName + "\n" + number);
                   }
                   adapter.notifyDataSetChanged();
               }
           } catch (Exception e) {
               e.printStackTrace();
           } finally {
               if (cursor != null) {
                   cursor.close();
               }
           }
       }
2、ContentProvider 的使用
  （1）ContentProvider 一般为存储和获取数据提供统一的接口，可以在不同的应用程序之间共享数据。（相当于服务器）
  （2）自定义 ContentProvider 继承 ContentProvider 实现其6个方法,具体代码如下：
      public class DatabaseProvider extends ContentProvider {

          public static final int BOOK_DIR = 0;
          public static final int BOOK_ITEM = 1;
          public static final int CATEGORY_DIR = 2;
          public static final int CATEGORY_ITEM = 3;
          public static final String AUTHORITY = "com.edu.zscdm.databasetest.provider";
          private static UriMatcher uriMatcher;
          private MyDatabaseHelper dbHelper;
          static {
              uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
              uriMatcher.addURI(AUTHORITY, "book", BOOK_DIR);
              uriMatcher.addURI(AUTHORITY, "book/#", BOOK_ITEM);
              uriMatcher.addURI(AUTHORITY, "category", CATEGORY_DIR);
              uriMatcher.addURI(AUTHORITY, "category/#", CATEGORY_ITEM);
          }

          @Override
          public boolean onCreate() {
              dbHelper = new MyDatabaseHelper(getContext(), "BookStore.db", null, 2);
              return true;
          }

          @Override
          public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
              // 查询数据
              SQLiteDatabase db = dbHelper.getReadableDatabase();
              Cursor cursor = null;
              switch (uriMatcher.match(uri)) {
                  case BOOK_DIR:
                      cursor = db.query("Book", projection, selection, selectionArgs, null, null, sortOrder);
                      break;
                  case BOOK_ITEM:
                      String bookId = uri.getPathSegments().get(1);
                      cursor = db.query("Book", projection, "id = ?", new String[] { bookId }, null, null, sortOrder);
                      break;
                  case CATEGORY_DIR:
                      cursor = db.query("Category", projection, selection, selectionArgs, null, null, sortOrder);
                      break;
                  case CATEGORY_ITEM:
                      String categoryId = uri.getPathSegments().get(1);
                      cursor = db.query("Category", projection, "id = ?", new String[] { categoryId }, null, null, sortOrder);
                      break;
                  default:
                      break;
              }
              return cursor;
          }

          @Override
          public Uri insert(Uri uri, ContentValues values) {
              // 添加数据
              SQLiteDatabase db = dbHelper.getWritableDatabase();
              Uri uriReturn = null;
              switch (uriMatcher.match(uri)) {
                  case BOOK_DIR:
                  case BOOK_ITEM:
                      long newBookId = db.insert("Book", null, values);
                      uriReturn = Uri.parse("content://" + AUTHORITY + "/book/" + newBookId);
                      break;
                  case CATEGORY_DIR:
                  case CATEGORY_ITEM:
                      long newCategoryId = db.insert("Category", null, values);
                      uriReturn = Uri.parse("content://" + AUTHORITY + "/category/" + newCategoryId);
                      break;
                  default:
                      break;
              }
              return uriReturn;
          }

          @Override
          public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
              // 更新数据
              SQLiteDatabase db = dbHelper.getWritableDatabase();
              int updatedRows = 0;
              switch (uriMatcher.match(uri)) {
                  case BOOK_DIR:
                      updatedRows = db.update("Book", values, selection, selectionArgs);
                      break;
                  case BOOK_ITEM:
                      String bookId = uri.getPathSegments().get(1);
                      updatedRows = db.update("Book", values, "id = ?", new String[] { bookId });
                      break;
                  case CATEGORY_DIR:
                      updatedRows = db.update("Category", values, selection, selectionArgs);
                      break;
                  case CATEGORY_ITEM:
                      String categoryId = uri.getPathSegments().get(1);
                      updatedRows = db.update("Category", values, "id = ?", new String[] { categoryId });
                      break;
                  default:
                      break;
              }
              return updatedRows;
          }

          @Override
          public int delete(Uri uri, String selection, String[] selectionArgs) {
              // 删除数据
              SQLiteDatabase db = dbHelper.getWritableDatabase();
              int deletedRows = 0;
              switch (uriMatcher.match(uri)) {
                  case BOOK_DIR:
                      deletedRows = db.delete("Book", selection, selectionArgs);
                      break;
                  case BOOK_ITEM:
                      String bookId = uri.getPathSegments().get(1);
                      deletedRows = db.delete("Book", "id = ?", new String[] { bookId });
                      break;
                  case CATEGORY_DIR:
                      deletedRows = db.delete("Category", selection, selectionArgs);
                      break;
                  case CATEGORY_ITEM:
                      String categoryId = uri.getPathSegments().get(1);
                      deletedRows = db.delete("Category", "id = ?", new String[] { categoryId });
                      break;
                  default:
                      break;
              }
              return deletedRows;
          }

          @Override
          public String getType(Uri uri) {
              switch (uriMatcher.match(uri)) {
                  case BOOK_DIR:
                      return "vnd.android.cursor.dir/vnd.com.edu.zscdm.databasetest.provider.book";
                  case BOOK_ITEM:
                      return "vnd.android.cursor.item/vnd.com.edu.zscdm.databasetest.provider.book";
                  case CATEGORY_DIR:
                      return "vnd.android.cursor.dir/vnd.com.edu.zscdm.databasetest.provider.category";
                  case CATEGORY_ITEM:
                      return "vnd.android.cursor.item/vnd.com.edu.zscdm.databasetest.provider.category";
              }
              return null;
          }
      }
  （3）AndroidManifest.xml文件中添加代码如下：
      /**
       * 可选，添加用户权限，限制随意访问
       */
      <permission
          android:name="zscdumin.provider.READ"
          android:protectionLevel="normal"/>  //保护等级为一般
      <permission
          android:name="zscdumin.provider.WRITEAndREAD"
          android:protectionLevel="normal"/>
      <permission
          android:name="zscdumin.provider.WRITE"
          android:protectionLevel="normal"/>

      /**
       * 必选,
       */
      <provider
            android:name=".DatabaseProvider"
            android:authorities="com.edu.zscdm.databasetest.provider" //一般为当前应用程序的包名 + .provider
            android:readPermission="zscdumin.provider.READ" //该provider的读权限的标识
            android:writePermission="zscdumin.provider.WRITE" //该provider的写权限标识
            android:enabled="true"  //设置此provider是否可以被其他应用使用。
            android:permission="zscdumin.provider.WRITEAndREAD" //provider读写权限标识
            android:grantUriPermissions=""
            /*临时权限标识，true时，意味着该provider下所有数据均可被临时使用；false时，则反之，但可以通过设置<grant-uri-permission>标签来指定哪些路径可以被临时使用。这么说可能还是不容易理解，我们举个例子，
            比如你开发了一个邮箱应用，其中含有附件需要第三方应用打开，但第三方应用又没有向你申请该附件的读权限，
            但如果你设置了此标签，则可以在start第三方应用时，传入FLAG_GRANT_READ_URI_PERMISSION或FLAG_GRANT_WRITE_URI_PERMISSION来让第三方应用临时具有读写该数据的权限。*/
            android:exported="true"/>
  （4）ContentProvider中的URI
      URI标准格式示例："content://com.edu.zscdm.databasetest.provider/path/id"
        a. Authority：授权信息，用以区别不同的ContentProvider。
        b. Path：表名，用以区分ContentProvider中不同的数据表。
        c. Id：Id号，用以区别表中的不同数据。
      通配符：
        * ：表示匹配任意长度的字符
        # ：表示匹配任意长度的数字
  （5）注：ContentResolver 是读取 ContentProvider 中的数据来显示，所以需要先安装 ContentProvider。
3、ContentResolver 的使用
  （1）ContentProvider 来统一管理与不同ContentProvider间的操作。（相当于客户端）
  （2）AndroidManifest.xml文件中添加用户权限代码如下：
     /**
      * 可选，若ContentProvider没有设置权限的话可以不用添加
      */
      <uses-permission android:name="zscdumin.provider.READ"/>
      <uses-permission android:name="zscdumin.provider.WRITE"/>
  （3）MainActivity 中示例代码如下：
      public class MainActivity extends AppCompatActivity {
          private String newId;
          @Override
          protected void onCreate(Bundle savedInstanceState) {
              super.onCreate(savedInstanceState);
              setContentView(R.layout.activity_main);
              Button addData = (Button) findViewById(R.id.add_data);
              addData.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      // 添加数据
                      Uri uri = Uri.parse("content://com.edu.zscdm.databasetest.provider/book");
                      ContentValues values = new ContentValues();
                      values.put("name", "A Clash of Kings");
                      values.put("author", "George Martin");
                      values.put("pages", 1040);
                      values.put("price", 55.55);
                      Uri newUri = getContentResolver().insert(uri, values);
                      newId = newUri.getPathSegments().get(1);
                  }
              });
              Button queryData = (Button) findViewById(R.id.query_data);
              queryData.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      // 查询数据
                      Uri uri = Uri.parse("content://com.edu.zscdm.databasetest.provider/book");
                      Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                      if (cursor != null) {
                          while (cursor.moveToNext()) {
                              String name = cursor.getString(cursor. getColumnIndex("name"));
                              String author = cursor.getString(cursor. getColumnIndex("author"));
                              int pages = cursor.getInt(cursor.getColumnIndex ("pages"));
                              double price = cursor.getDouble(cursor. getColumnIndex("price"));
                              Log.d("MainActivity", "book name is " + name);
                              Log.d("MainActivity", "book author is " + author);
                              Log.d("MainActivity", "book pages is " + pages);
                              Log.d("MainActivity", "book price is " + price);
                          }
                          cursor.close();
                      }
                  }
              });
              Button updateData = (Button) findViewById(R.id.update_data);
              updateData.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      // 更新数据
                      Uri uri = Uri.parse("content://com.edu.zscdm.databasetest.provider/book/" + newId);
                      ContentValues values = new ContentValues();
                      values.put("name", "A Storm of Swords");
                      values.put("pages", 1216);
                      values.put("price", 24.05);
                      getContentResolver().update(uri, values, null, null);
                  }
              });
              Button deleteData = (Button) findViewById(R.id.delete_data);
              deleteData.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      // 删除数据
                      Uri uri = Uri.parse("content://com.edu.zscdm.databasetest.provider/book/" + newId);
                      getContentResolver().delete(uri, null, null);
                  }
              });
          }
      }
