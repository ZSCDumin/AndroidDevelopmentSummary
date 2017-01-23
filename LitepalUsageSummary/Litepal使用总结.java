1、添加闭包
	dependencies {
	    compile 'org.litepal.android:core:1.4.1'
	}
2、在assets文件夹中添加文件litepal.xml（与res文件夹并列）
    <?xml version="1.0" encoding="utf-8" ?>
    <litepal>
        <dbname value="Book"/>
        <version value="1"/>
        <list>
           <mapping class="com.dumin.zsc.litepaldemo.Book"/>
        </list>
    </litepal>
3、在AndroidManifest文件中配置应用程序名称
	<application
		android:name="org.litepal.LitePalApplication"
		....
		....
	</application>
4、数据库
  （1）创建数据库语句
        Connector.getDatabase();
  （2）创建数据表
        Book book=new Book();
        book.setName("Android");
        book.setAuthor("Dumin");
        book.setPrice(30);
        book.setPages(250);
        book.save();
  （3）修改数据表
        Book book2=new Book();
        book2.setName("Android1");
        book2.setAuthor("Dumin1");
        book2.setPrice(30);
        book2.setPages(250);
        book2.update(142);
  （4）删除数据表
        DataSupport.deleteAll(Book.class);
        DataSupport.delete(Book.class,1);//根据Id来删除
  （5）查询数据表
        List<Book> books = DataSupport.findAll(Book.class);
        for(Book book1 : books){
            Log.d("MainActivity",book1.getId()+"");
            Log.d("MainActivity",book1.getName()+"");
            Log.d("MainActivity",book1.getAuthor()+"");
            Log.d("MainActivity",book1.getPrice()+"");
            Log.d("MainActivity",book1.getPages()+"");
        }
