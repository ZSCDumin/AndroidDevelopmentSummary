1、RelativeLayout 相对布局
2、LinearLayout 线性布局
3、FramLayout 帧布局
4、AbsoluteLayout 绝对布局
5、TableLayout 表格布局
6、PercentFrameLayout 与 PercentRelativeLayout  百分比布局
   (1)使用前，在模块的build.gradle文件中配置如下：
      compile 'com.android.support:percent:25.1.0'
   (2)布局示例代码如下：
      <?xml version="1.0" encoding="utf-8"?>
      <android.support.percent.PercentFrameLayout
           xmlns:android="http://schemas.android.com/apk/res/android"
           xmlns:app="http://schemas.android.com/apk/res-auto"
           android:layout_width="match_parent"
           android:layout_height="match_parent">
           <Button
               android:id="@+id/button1"
               android:text="Button 1"
               android:layout_gravity="left|top"
               app:layout_widthPercent="50%"
               app:layout_heightPercent="50%"
               />
           <Button
               android:id="@+id/button2"
               android:text="Button 2"
               android:layout_gravity="right|top"
               app:layout_widthPercent="50%"
               app:layout_heightPercent="50%"
               />

           <Button
               android:id="@+id/button3"
               android:text="Button 3"
               android:layout_gravity="left|bottom"
               app:layout_widthPercent="50%"
               app:layout_heightPercent="50%"
               />

           <Button
               android:id="@+id/button4"
               android:text="Button 4"
               android:layout_gravity="right|bottom"
               app:layout_widthPercent="50%"
               app:layout_heightPercent="50%"
               />
      </android.support.percent.PercentFrameLayout>
7、自定义布局
   (1)XML布局代码如下：
      <?xml version="1.0" encoding="utf-8"?>
      <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
           android:layout_width="match_parent"
           android:layout_height="wrap_content"
           android:background="@drawable/title_bg">
           <Button
               android:id="@+id/title_back"
               android:layout_width="wrap_content"
               android:layout_height="wrap_content"
               android:layout_gravity="center"
               android:layout_margin="5dp"
               android:background="@drawable/back_bg"
               android:text="Back"
               android:textColor="#fff" />
           <TextView
               android:id="@+id/title_text"
               android:layout_width="0dp"
               android:layout_height="wrap_content"
               android:layout_gravity="center"
               android:layout_weight="1"
               android:gravity="center"
               android:text="Title Text"
               android:textColor="#fff"
               android:textSize="24sp" />
           <Button
               android:id="@+id/title_edit"
               android:layout_width="wrap_content"
               android:layout_height="wrap_content"
               android:layout_gravity="center"
               android:layout_margin="5dp"
               android:background="@drawable/edit_bg"
               android:text="Edit"
               android:textColor="#fff" />
      </LinearLayout>
   (2)引入布局代码如下：
      <include layout="@layout/title"/>
   (3)TitleLayout代码如下：
      public class TitleLayout extends LinearLayout {
          public TitleLayout(Context context, AttributeSet attrs) {
               super(context, attrs);
               LayoutInflater.from(context).inflate(R.layout.title, this);//布局加载器
               Button titleBack = (Button) findViewById(R.id.title_back);
               Button titleEdit = (Button) findViewById(R.id.title_edit);
               titleBack.setOnClickListener(new OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       ((Activity) getContext()).finish();//结束Activity
                   }
               });
               titleEdit.setOnClickListener(new OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Toast.makeText(getContext(), "You clicked Edit button",
                               Toast.LENGTH_SHORT).show();
                   }
               });
           }
      }
   (4)MainActivity中的代码如下：
      ActionBar actionbar = getSupportActionBar();
      if (actionbar != null) {
          actionbar.hide();//隐藏标题栏
      }
