1、将数据存储到文件中
   （1）示例代码：
         public void save(String inputText) {//将内容保存在文件中的函数
              FileOutputStream out = null;
              BufferedWriter writer = null;
              try {
                  //Context.MODE_PRIVATE 文件覆盖的方式, Context.MODE_APPEND 文件追加的方式
                  out = openFileOutput("data", Context.MODE_PRIVATE);
                  writer = new BufferedWriter(new OutputStreamWriter(out));
                  writer.write(inputText);
              } catch (IOException e) {
                  e.printStackTrace();
              } finally {
                  try {
                      if (writer != null) {
                          writer.close();
                      }
                  } catch (IOException e) {
                      e.printStackTrace();
                  }
              }
          }
2、从文件中读取数据
   （1）示例代码：
         public String load() {//读取文件的内容的函数
              FileInputStream in = null;
              BufferedReader reader = null;
              StringBuilder content = new StringBuilder();
              try {
                  in = openFileInput("data");
                  reader = new BufferedReader(new InputStreamReader(in));
                  String line = "";
                  while ((line = reader.readLine()) != null) {
                      content.append(line);
                  }
              } catch (IOException e) {
                  e.printStackTrace();
              } finally {
                  if (reader != null) {
                      try {
                          reader.close();
                      } catch (IOException e) {
                          e.printStackTrace();
                      }
                  }
              }
              return content.toString();
          }
3、将数据存储到 SharedPreferences 中
  （1）获取 SharedPreferences 对象的3种方法
       1). Context 类中的 getSharedPreferences() 方法
       2). Activity 类中的 getSharedPreferences()方法
       3). PreferenceManager 类中的getDefaultSharedPreferences()方法
           a. 调用SharedPreferences对象的edit()方法来获取一个SharedPreferences.Editor()对象。
           b. 向SharedPreferences.Editor对象中添加数据，比如一个布尔类型的数据就用putBoolean()方法。
           c. 调用apply()方法将添加的数据提交,从而完成数据存储操作。
  （2）示例代码：
         /**
          * 存储数据
          */
          public void write() {
             SharedPreferences.Editor editor = getSharedPreferences("DuminData", MODE_PRIVATE).edit();
             editor.putString("Name", "Tom");
             editor.putInt("Age", 22);
             editor.putBoolean("IsMarried", true);
             editor.apply();
          }
4、从 SharedPreferences 中读取数据
  （1）示例代码：
       /**
        * 读取数据
        */
        public void read() {
           SharedPreferences sharedPreferences = getSharedPreferences("DuminData", MODE_PRIVATE);
           String name = sharedPreferences.getString("Name", "");
           int age = sharedPreferences.getInt("Age", 0);
           Boolean married = sharedPreferences.getBoolean("ISMarried", false);
           Toast.makeText(this, "Name：" + name + " Age：" + age + " ISMarried：" + married, Toast.LENGTH_SHORT).show();
        }
5、SQLite 数据库存储
  （1）利用 SQLiteOpenHelper 帮助类完成数据库的创建和升级
       a. 覆写onCreate()、onUpdate()两个方法实现数据库的创建、升级。
       b. getWritableDatabase() 和 getReadableDatabase() 这两个方法都可以表示创建
          或者打开一个数据库(不存在时创建新的数据库);不同的是数据库不可写入时,getReadableDatabase()方法返回的对象将以只读的方式打开数据库,getWritableDatabase()将会出现异常。
       c. blob 表示二进制类型数据
  （2）创建数据库
       dbHelper = new MyDatabaseHelper(this, "BookStore.db", null, 2);
       dbHelper.getWritableDatabase();
  （3）创建数据表
       SQLiteDatabase db = dbHelper.getWritableDatabase();
       ContentValues values = new ContentValues();
       // 开始组装第一条数据
       values.put("name", "The Da Vinci Code");
       values.put("author", "Dan Brown");
       values.put("pages", 454);
       values.put("price", 16.96);
       db.insert("Book", null, values); // 插入第一条数据
       values.clear();
       // 开始组装第二条数据
       values.put("name", "The Lost Symbol");
       values.put("author", "Dan Brown");
       values.put("pages", 510);
       values.put("price", 19.95);
       db.insert("Book", null, values); // 插入第二条数据
  （3）修改数据表
       SQLiteDatabase db = dbHelper.getWritableDatabase();
       ContentValues values = new ContentValues();
       values.put("price", 10.99);
       db.update("Book", values, "name = ?", new String[] { "The Da Vinci Code" });
  （4）删除数据表
       SQLiteDatabase db = dbHelper.getWritableDatabase();
       db.delete("Book", "pages > ?", new String[] { "500" });
  （5）查询数据表
       SQLiteDatabase db = dbHelper.getWritableDatabase();
       // 查询Book表中所有的数据
       Cursor cursor = db.query("Book", null, null, null, null, null, null);
       if (cursor.moveToFirst()) {
            do {
                // 遍历Cursor对象，取出数据并打印
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String author = cursor.getString(cursor.getColumnIndex("author"));
                int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));
                Log.d("MainActivity", "book name is " + name);
                Log.d("MainActivity", "book author is " + author);
                Log.d("MainActivity", "book pages is " + pages);
                Log.d("MainActivity", "book price is " + price);
            } while (cursor.moveToNext());
       }
       cursor.close();
