1、选项菜单（OptionsMenu）：
  （1）选项菜单共有五个常用的方法，下面对这些方法进行一一介绍：
        public boolean onCreateOptionsMenu(Menu menu)：使用此方法创建选项菜单，通常有两种方式，一种称为静态方式（xml布局文件创建菜单），另一种通过Menu的add方法动态添加选项菜单；
        public boolean onOptionsItemSelected(MenuItem item)：选中菜单项被选择后触发，可以根据item对象的getItemId方法判断点击了哪一个菜单；
        public void onOptionsMenuClosed(Menu menu):菜单关闭时触发；
        public boolean onPrepareOptionsMenu(Menu menu)：选项菜单显示之前触发；
        public boolean onMenuOpened(int featureId, Menu menu)：选项菜单打开时触发。
  （2）下面通过静态和动态的方式构建选项菜单：
      1）静态方法：通过定义xml文件的形式确定子项菜单 菜单文件（menu.xml）
        <?xml version="1.0" encoding="utf-8"?>
        <menu xmlns:android="http://schemas.android.com/apk/res/android">
            <item
                android:id="@+id/meun1"
                android:icon="@mipmap/ic_launcher"
                android:orderInCategory="3"  //组内顺序
                android:title="菜单一" />
            <item
                android:id="@+id/menu2"
                android:orderInCategory="2"
                android:title="菜单二" />
            <item
                android:id="@+id/menu3"
                android:enabled="false"
                android:orderInCategory="4"
                android:title="不可用菜单" />
        </menu>

         /**********  MainActivity.java 代码如下： ***********/

            public class MainActivity extends Activity {
                @Override
                protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    setContentView(R.layout.activity_main);
                }
                @Override
                public boolean onCreateOptionsMenu(Menu menu) {
                    getMenuInflater().inflate(R.menu.menu,menu);
                    return true;
                }
                @Override
                public boolean onOptionsItemSelected(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.meun1:
                            Toast.makeText(MainActivity.this, "菜单一被选择了", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.menu2:
                            Toast.makeText(MainActivity.this, "菜单二被选择了", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.menu3:
                            Toast.makeText(MainActivity.this, "菜单三被选择了", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    return super.onOptionsItemSelected(item);
                }
                @Override
                public boolean onMenuOpened(int featureId, Menu menu) {
                    Toast.makeText(MainActivity.this, "选项菜单开启", Toast.LENGTH_SHORT).show();
                    return super.onMenuOpened(featureId, menu);
                }
                @Override
                public void onOptionsMenuClosed(Menu menu) {
                    super.onOptionsMenuClosed(menu);
                    Toast.makeText(MainActivity.this, "选项菜单关闭", Toast.LENGTH_SHORT).show();
                }
            }
      2）动态方法：

            /**********  MainActivity.java 代码如下： ***********/

            public class MainActivity extends Activity {
                @Override
                protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    setContentView(R.layout.activity_main);
                }
                @Override
                public boolean onCreateOptionsMenu(Menu menu) {//创建选项菜单
                    menu.add(menu.NONE, 1, 1, "菜单1");
                    menu.add(menu.NONE, 2, 2, "菜单2");
                    menu.add(menu.NONE, 3, 3, "菜单3");
                    menu.add(menu.NONE, 4, 4, "菜单4");
                    return super.onCreateOptionsMenu(menu);
                }
                @Override
                public boolean onOptionsItemSelected(MenuItem item) {//菜单选择监听
                    switch (item.getItemId()) {
                        case 1:
                            Toast.makeText(MainActivity.this, "菜单一被选择了", Toast.LENGTH_SHORT).show();
                            break;
                        case 2:
                            Toast.makeText(MainActivity.this, "菜单二被选择了", Toast.LENGTH_SHORT).show();
                            break;
                        case 3:
                            Toast.makeText(MainActivity.this, "菜单三被选择了", Toast.LENGTH_SHORT).show();
                            break;
                        case 4:
                            Toast.makeText(MainActivity.this, "菜单四被选择了", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    return super.onOptionsItemSelected(item);
                }
                @Override
                public boolean onMenuOpened(int featureId, Menu menu) {//菜单打开事件监听
                    Toast.makeText(MainActivity.this, "选项菜单开启", Toast.LENGTH_SHORT).show();
                    return super.onMenuOpened(featureId, menu);
                }
                @Override
                public void onOptionsMenuClosed(Menu menu) {//菜单关闭事件监听
                    super.onOptionsMenuClosed(menu);
                    Toast.makeText(MainActivity.this, "选项菜单关闭", Toast.LENGTH_SHORT).show();
                }
            }

            /******* 代码解析*********/
            public MenuItem add(int groupId, int itemId, int order, CharSequence title); 
              add方法中需要传入四个参数：
                -> groupId：组别，不分组可以写Menu.NONE
                -> itemId：子菜单项Id，作为子菜单的唯一标识
                -> order：菜单显示顺序
                -> title：菜单文本显示
  （3）解决 Android4.0 Icon 无法显示的问题：
       原因在于：mOptionalIconsVisible成员初始值默认为false
       解决办法：这时候就需要考虑用反射了，在代码运行创建菜单的时候通过反射调用setOptionalIconsVisible方法设置mOptionalIconsVisible为true，
                然后在给菜单添加Icon，这样就可以在菜单中显示添加的图标了。
       详细代码如下：
        //enable为true时，菜单添加图标有效，enable为false时无效。4.0系统默认无效  
        private void setIconEnable(Menu menu, boolean enable) {
            try {
                Class<?> clazz = Class.forName("com.android.internal.view.menu.MenuBuilder");
                Method m = clazz.getDeclaredMethod("setOptionalIconsVisible", boolean.class);
                m.setAccessible(true);

                //MenuBuilder实现Menu接口，创建菜单时，传进来的menu其实就是MenuBuilder对象(java的多态特征)  
                m.invoke(menu, enable);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
2、弹出式对话框：
  （1）弹出框布局文件（view.xml） 弹出框布局采用GridView控件进行布局，代码如下：
        <?xml version="1.0" encoding="utf-8"?>
        <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
            android:layout_width="fill_parent"
            android:layout_height="fill_parent"
            android:orientation="vertical">
            <GridView
                android:id="@+id/gridview"
                android:layout_width="300dp"
                android:layout_height="150dp"
                android:gravity="center"
                android:numColumns="3" />
        </LinearLayout>
   （2）GridView子布局文件（item.xml）
        <?xml version="1.0" encoding="utf-8"?>
        <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
            android:id="@+id/RelativeLayout_Item"
            android:layout_width="fill_parent"
            android:layout_height="wrap_content"
            android:padding="10dp">
            <ImageView
                android:id="@+id/iv_icon"
                android:layout_width="40dp"
                android:layout_height="40dp"
                android:layout_centerHorizontal="true"></ImageView>
            <TextView
                android:id="@+id/tv_title"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_below="@id/iv_icon"
                android:layout_centerHorizontal="true"
                android:text="选项"></TextView>
        </RelativeLayout>
   （3）MainActivity 代码（MainActivity.java）
        public class MainActivity extends Activity {
            private AlertDialog alertDialog;
            private GridView gridView;
            private View view;
            private int[] icons = {R.drawable.exit, R.drawable.fold, R.drawable.set, R.drawable.newpic, R.drawable.more};
            private String[] titles = {"退出", "文件", "设置", "新建", "更多"};
            private SimpleAdapter simpleAdapter;
            private List<Map<String, Object>> datas;
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                view = LayoutInflater.from(this).inflate(R.layout.view, null);//获得弹出框布局文件对象
                initDatas();//初始化数据集
                //初始化SimpleAdapter
                simpleAdapter = new SimpleAdapter(this, datas, R.layout.item, new String[]{"icon", "title"}, new int[]{R.id.iv_icon, R.id.tv_title});
                alertDialog=new AlertDialog.Builder(this).create();//创建弹出框
                alertDialog.setView(view);//设置弹出框布局
                gridView=(GridView)view.findViewById(R.id.gridview);
                gridView.setAdapter(simpleAdapter);//设置适配器
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//GridView子项单击事件监听
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(MainActivity.this,"您点击了"+titles[position]+"按钮",Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    }
                });
            }
            private void initDatas() {
                datas = new ArrayList<>();
                for (int i = 0; i < titles.length; i++) {
                    Map map = new HashMap();
                    map.put("icon", icons[i]);
                    map.put("title", titles[i]);
                    datas.add(map);
                }
            }
            @Override
            public boolean onKeyDown(int keyCode, KeyEvent event) {//覆写了onKeyDown方法
                if(keyCode==KeyEvent.KEYCODE_MENU){//当按下菜单键时
                    if (alertDialog == null) {
                        alertDialog = new AlertDialog.Builder(this).setView(view).show();
                    } else
                        alertDialog.show();
                }
                return super.onKeyDown(keyCode, event);
            }
        }
3、上下文菜单：
  （1）概念：
         上下文菜单可以理解成PC端上的右键，当需要进行复制或粘贴、删除或重命名时就可以选定想要操作的对象点击右键，
         在弹出菜单中选择所需的操作。Android提供了长按被操作对象，弹出浮动的操作菜单的交互方式，这个弹出菜单就被称为上下文菜单，
         任何控件都可以注册上下文菜单，采用的有EditText弹出上下文菜单，进行清空或粘贴的操作、ListView 子 Item 的删除和添加等。
  （2）设置一个上下文菜单一般分为三个步骤：
        => 创建上下文菜单：覆写onCreateContenxtMenu方法，由其参数ContextMenu类的menu对象结合其add方法，添加子菜单。
        => 添加单项选择监听：覆写onContextItemSelected方法，由其参数MenuItem类的item对象结合其getItemId方法，判断选择了哪一个子菜单。
        => 注册上下文菜单：将想要注册上下文菜单的控件对象作为registerForContextMenu方法的参数传入，即可为其添加上下文菜单。
  （3）具体代码如下：
        public class Main3Activity extends AppCompatActivity {
            private EditText editText;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                LinearLayout linearLayout = new LinearLayout(this);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                editText = new EditText(this);
                editText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                editText.setHint("上下文菜单测试");
                registerForContextMenu(editText);//为EditText控件添加上下文菜单
                linearLayout.addView(editText);
                setContentView(linearLayout);
            }

            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {//创建上下文菜单
                super.onCreateContextMenu(menu, v, menuInfo);
                menu.setHeaderTitle("上下文菜单");//上下文菜单的标题
                menu.setHeaderIcon(android.R.drawable.ic_btn_speak_now); //上下文菜单图标
                menu.add(Menu.NONE, 1, 1, "粘贴");
                menu.add(Menu.NONE, 2, 2, "清空");
            }

            @Override
            public boolean onContextItemSelected(MenuItem item) {//子菜单选择事件监听
                switch (item.getItemId()) {//根据子菜单ID进行菜单选择判断
                    case 1:
                        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        clipboardManager.setPrimaryClip(ClipData.newPlainText(null, "内容"));
                        if (clipboardManager.hasPrimaryClip()) {
                            editText.setText(clipboardManager.getPrimaryClip().getItemAt(0).getText());
                        }
                        break;
                    case 2:
                        editText.setText("");
                        break;
                }
                return super.onContextItemSelected(item);
            }
        }

4、子菜单：
  （1）子菜单和选项菜单比较类似，它的特点是可以将菜单进行分组，更方面用户进行选择和操作。创建子菜单的步骤也大致可以分为三步：
        => 覆写Activity的onCreateOptionsMenu()方法，调用Menu类的addSubMenu()方法来添加子菜单。
        => 调用SubMenu的add()方法，添加子菜单子项，add方法有四个参数，同样也是分组ID、子项ID、子项顺序和子项文本信息。
        => 覆写onContextItemSelected()方法，为子菜单添加选择事件监听。
  （2）布局文件方式（静态方式）：
        <?xml version="1.0" encoding="utf-8"?>
        <menu xmlns:android="http://schemas.android.com/apk/res/android">
            <item
                android:id="@+id/file"
                android:icon="@mipmap/ic_launcher"
                android:orderInCategory="100"
                android:showAsAction="never"
                android:title="文件操作">
                <menu>
                    <item
                        android:id="@+id/new_file"
                        android:showAsAction="never"
                        android:title="新建" />
                    <item
                        android:id="@+id/open_file"
                        android:showAsAction="never"
                        android:title="打开" />
                    <item
                        android:id="@+id/edit_file"
                        android:showAsAction="never"
                        android:title="编辑" />
                </menu>
            </item>
            <item
                android:id="@+id/set"
                android:icon="@mipmap/ic_launcher"
                android:orderInCategory="100"
                android:showAsAction="never"
                android:title="设置">
                <menu>
                    <item
                        android:id="@+id/delete_set"
                        android:showAsAction="never"
                        android:title="删除" />
                    <item
                        android:id="@+id/exit_set"
                        android:showAsAction="never"
                        android:title="退出" />
                </menu>
            </item>
        </menu>
  
  （3）MainActivity 代码如下：
        public class MainActivity extends Activity {
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
            }
            @Override
            public boolean onCreateOptionsMenu(Menu menu) {//构建菜单
                MenuInflater menuInflater=getMenuInflater();
                menuInflater.inflate(R.menu.menu,menu);
                return super.onCreateOptionsMenu(menu);
            }
            @Override
            public boolean onOptionsItemSelected(MenuItem item) {//菜单选择事件监听
                switch (item.getItemId()){
                    case R.id.new_file:
                        Toast.makeText(this,"您选择了新建按钮",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.open_file:
                        Toast.makeText(this,"您选择了打开按钮",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.edit_file:
                        Toast.makeText(this,"您选择了编辑按钮",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.delete_set:
                        Toast.makeText(this,"您选择了删除按钮",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.exit_set:
                        Toast.makeText(this,"您选择了退出按钮",Toast.LENGTH_SHORT).show();
                        break;
                }
                return super.onOptionsItemSelected(item);
            }
        }
  （4）代码动态添加（动态方式）：
        public class MainActivity extends Activity {
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
            }
            @Override
            public boolean onCreateOptionsMenu(Menu menu) {//构建菜单
                SubMenu file =menu.addSubMenu("文件");//获得子菜单对象
                file.setHeaderIcon(android.R.drawable.ic_btn_speak_now);
                file.setHeaderTitle("文件操作");
                file.add(1,1,1,"新建");//调用add方法添加子菜单子项
                file.add(1,2,2,"打开");
                file.add(1,3,3,"编辑");
                SubMenu set =menu.addSubMenu("设置");
                set.add(1,4,4,"删除");
                set.add(1,5,5,"退出");
                return super.onCreateOptionsMenu(menu);
            }
            @Override
            public boolean onOptionsItemSelected(MenuItem item) {//菜单选择事件监听
                switch (item.getItemId()){
                    case 1:
                        Toast.makeText(this,"您选择了新建按钮",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(this,"您选择了打开按钮",Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(this,"您选择了编辑按钮",Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(this,"您选择了删除按钮",Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        Toast.makeText(this,"您选择了退出按钮",Toast.LENGTH_SHORT).show();
                        break;
                }
                return super.onOptionsItemSelected(item);
            }
        }
5、悬浮菜单：
（1）布局文件代码如下：
    <?xml version="1.0" encoding="utf-8"?>
    <LinearLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="#ffffff"
        android:orientation="vertical"
        android:paddingBottom="1dp">
        <View
            android:layout_width="match_parent"
            android:layout_height="2dp"
            android:background="#10a324"
            android:layout_alignParentTop="true"/>
        <TextView
            android:id="@+id/tv_exit"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            style="@style/text_style"
            android:text="退出"/>
        <View
            android:layout_width="match_parent"
            android:layout_height="1dp"
            android:background="#d4d2d2"/>
        <TextView
            android:id="@+id/tv_set"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            style="@style/text_style"
            android:text="设置"/>
        <View
            android:layout_width="match_parent"
            android:layout_height="1dp"
            android:background="#d4d2d2"/>
        <TextView
            android:id="@+id/tv_cancel"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            style="@style/text_style"
            android:text="取消"/>
    </LinearLayout>
（2）PopMenuActivity.java 代码如下：
    public class PopMenuActivity extends Activity implements View.OnClickListener {
        private PopupWindow popupWindow;
        private TextView tvExit, tvSet, tvCancel;
        private View rootView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
        }

        private void showPopup() {
            View view = LayoutInflater.from(this).inflate(R.layout.popupwindow_layout, null);//PopupWindow对象
            popupWindow = new PopupWindow(this);//初始化PopupWindow对象
            popupWindow.setContentView(view);//设置PopupWindow布局文件
            popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);//设置PopupWindow宽
            popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);//设置PopupWindow高
            rootView = LayoutInflater.from(this).inflate(R.layout.activity_main, null);//父布局
            popupWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
            popupWindow.setOutsideTouchable(true);
            tvSet = (TextView) view.findViewById(R.id.tv_set);
            tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
            tvExit = (TextView) view.findViewById(R.id.tv_exit);//在view对象中通过findViewById找到TextView控件
            tvSet.setOnClickListener(this);//注册点击监听
            tvCancel.setOnClickListener(this);//注册点击监听
            tvExit.setOnClickListener(this);//注册点击监听
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    Toast.makeText(PopMenuActivity.this, "PupWindow消失了！", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_cancel:
                    popupWindow.dismiss();//关闭PopupWindow
                    break;
                case R.id.tv_exit:
                    finish();//调用Activity的finish方法退出应用程序
                    break;
                case R.id.tv_set:
                    Toast.makeText(this, "设置", Toast.LENGTH_SHORT).show();
                    popupWindow.dismiss();
                    break;
            }
        }

        public void test(View view) {
            if (popupWindow == null) {
                showPopup();
            } else {
                popupWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);//设置PopupWindow的弹出位置。
            }
        }
    }