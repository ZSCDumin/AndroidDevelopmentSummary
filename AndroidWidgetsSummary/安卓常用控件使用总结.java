1、TextView 使用总结：
    <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="New TextNew TextNew TextNew TextNew TextNew TextNew TextNew TextNew Text"
            android:textColor="#ff1911"
            android:textSize="24sp"
            android:maxLines="1"
            android:gravity="center"  //显示位置
            android:textStyle="bold"
            android:autoLink="all"  //设置链接
            android:drawableRight="@android:drawable/ic_lock_lock"  //组件中插入图片
            android:id="@+id/textView" />

    <TextView
        android:id="@+id/tv_marquee"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:textColor="@android:color/black"
        android:ellipsize="marquee"   //文字长度超过组件长度时所选择的显示方式 （end-省略号方式，省略号在尾部，start-省略号在头部，middle-省略号在中部，marquee-跑马灯方式显示）
        android:focusable="true"  //控件获得焦点
        android:focusableInTouchMode="true"  //针对触摸屏获得当前焦点
        android:marqueeRepeatLimit="marquee_forever"  //设置跑马灯无限制次数循环
        android:scrollHorizontally="true"  //水平方式显示
        android:singleLine="true"   //使用android:maxLines="1"替换
        android:text="这是跑马灯的效果这是跑马灯的效果这是跑马灯的效果这是跑马灯的效果">
    </TextView>
2、倒计时功能代码：
    new CountDownTimer(30000, 1000) {
        public void onTick(long millisUntilFinished) {
            textView.setText("seconds remaining: " + millisUntilFinished / 1000);
        }
        public void onFinish() {
            textView.setText("done!");
        }
    }.start();
3、TextSwitch 和 ImageSwitch 使用总结：
（1）Android 动画文件：
        <?xml version="1.0" encoding="utf-8"?>
        <set xmlns:android="http://schemas.android.com/apk/res/android">
            <scale
                android:duration="500"   //动画持续时间
                android:fromXScale="1"  //属性为动画起始时 X坐标上的伸缩尺寸
                android:fromYScale="1"  //属性为动画起始时 Y坐标上的伸缩尺寸
                android:interpolator="@android:anim/accelerate_interpolator"  
                /*
                有三种动画插入器:
                    accelerate_decelerate_interpolator  加速-减速 动画插入器 
                    accelerate_interpolator        加速-动画插入器
                    decelerate_interpolator        减速- 动画插入器
                */
                android:pivotX="50%"   //属性为动画相对于物件的X坐标的开始位置
                android:pivotY="50%"   //属性为动画相对于物件的Y坐标的开始位置
                android:toXScale="0"  //属性为动画结束时 X坐标上的伸缩尺寸
                android:toYScale="0" />  //属性为动画结束时 Y坐标上的伸缩尺寸
            <rotate
                android:duration="500"
                android:fromDegrees="0"
                android:pivotX="50%"
                android:pivotY="50%"
                android:toDegrees="360" />
        </set>
（2）

4、SeekBar 控件使用总结：
   （1）布局文件代码：
       <SeekBar
        android:id="@+id/seekBar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
   （2）Java Code ：
        seekBar.setMax(20);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Toast.makeText(SeekBarActivity.this, "正在拖动" + progress, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(SeekBarActivity.this, "开始拖动", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress() <= 5) {
                    imageView.setImageResource(R.mipmap.ic_launcher);
                    seekBar.setProgress(0);
                } else if (seekBar.getProgress() > 5 && seekBar.getProgress() < 15) {
                    seekBar.setProgress(10);
                    imageView.setImageResource(R.mipmap.ic_launcher);
                } else {
                    seekBar.setProgress(20);
                    imageView.setImageResource(R.mipmap.ic_launcher);
                }
                Toast.makeText(SeekBarActivity.this, "停止拖动", Toast.LENGTH_SHORT).show();
            }
        });
5、Spinner 控件使用总结：
  （1）编写数据源文件values/arrays.xml：
        <?xml version="1.0" encoding="utf-8"?>
        <resources>
            <string-array name="months">
                <item>一月</item>
                <item>二月</item>
                <item>三月</item>
            </string-array>
        </resources>
  （2）布局文件：
        <?xml version="1.0" encoding="utf-8"?>
        <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
            android:layout_width="match_parent"
            android:layout_height="match_parent">
            <Spinner
                android:id="@+id/spinner"
                android:entries="@array/months"  //数据源
                android:dropDownWidth="20dp"  //显示项的宽度
                android:layout_width="wrap_content"
                android:spinnerMode="dialog"   //下拉控件模式
                android:prompt="@string/month"  //当显示模式为dialog时生效,作用为显示dialog的标题内容
                android:layout_height="wrap_content" />
        </RelativeLayout>
   （3）Java Code ：
        ArrayAdapter<String> adapter_season, adapter_month;
        String months[][] = {{"一月", "二月", "三月"}, {"四月", "五月", "六月"}, {"七月", "八月", "九月"}, {"十月", "十一月", "十二月"}};
        private Spinner spinner_season, spinner_month;
        private List<String> list_season;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.spinner_complex);
            spinner_season = (Spinner) findViewById(R.id.spinner_season);
            spinner_month = (Spinner) findViewById(R.id.spinner_month);
            spinner_season.setPrompt("请选择季节");
            initDatas();
            adapter_season = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list_season);
            spinner_season.setAdapter(adapter_season);
            spinner_season.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    adapter_month = new ArrayAdapter<String>(SpinnerActivity.this, android.R.layout.simple_list_item_1, months[position]);//通过二维数组及position的值动态决定下级菜单的数据源
                    adapter_month.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);//设置下拉显示样式
                    spinner_month.setAdapter(adapter_month);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        private void initDatas() {
            list_season = new ArrayList<String>();
            list_season.add("春季");
            list_season.add("夏季");
            list_season.add("秋季");
            list_season.add("冬季");
        }
6、ViewFlipper 控件使用总结：
  （1）布局文件代码：
        <?xml version="1.0" encoding="utf-8"?>
        <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
            android:layout_width="match_parent"
            android:layout_height="match_parent">
            <ViewFlipper
                android:id="@+id/viewflipper"
                android:layout_width="match_parent"
                android:layout_height="100dp">
                <ImageView
                    android:layout_width="match_parent"
                    android:layout_height="100dp"
                    android:src="@drawable/img1" />
                <ImageView
                    android:layout_width="match_parent"
                    android:layout_height="100dp"
                    android:src="@drawable/img2" />
                <ImageView
                    android:layout_width="match_parent"
                    android:layout_height="100dp"
                    android:src="@drawable/img3" />
            </ViewFlipper>
        </RelativeLayout>
  （2）Java Code：
        viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
        viewFlipper.setAutoStart(true); // 设置自动播放功能（点击事件，前自动播放）
        viewFlipper.setFlipInterval(3000);//间隔3秒
        if (viewFlipper.isAutoStart() && !viewFlipper.isFlipping()) {
            viewFlipper.startFlipping();//自动播放
        }
  （3）动态代码添加：
        public class MainActivity extends Activity  {
            private int[] imgs = { R.drawable.img1, R.drawable.img2, R.drawable.img3 };//图片源
            private ViewFlipper viewFlipper;
            private float startX = 0f, tempX = 0f;
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
                for (int i = 0; i < imgs.length; i++) { // 动态添加图片源
                    ImageView iv = new ImageView(this);
                    iv.setImageResource(imgs[i]);
                    iv.setScaleType(ImageView.ScaleType.CENTER);
                    /*****
                    ImageView调用了setScaleType方法设置了图片在ImageView上的显示样式，这里总结一下方法里参数的含义：
                       -->  SetScaleType(ImageView.ScaleType.CENTER)：不改变图片的size居中显示，当图片的长/宽超过View的长或宽时，截取图片的居中部分显示。
                       -->  setScaleType(ImageView.ScaleType.CENTER_INSIDE)：改变图片的size居中显示，可以显示完成的图片。
                       -->  setScaleType(ImageView.ScaleType.FIT_CENTER)：把图片按比例缩放到View的宽度，居中显示。
                       -->  setScaleType(ImageView.ScaleType.FIT_XY)：不按比例缩放图片，将图片塞满整个View（图片会被拉扯变形）。
                       -->  SetScaleType(ImageView.ScaleType.CENTER_CROP)：按比例扩大图片居中显示，占据整个View，图片会被截取，不会变形。
                       -->  SetScaleType(ImageView.ScaleType.FIT_START/FIT_END)：与FIT_CENTER一样，都是按比例缩放图片，只不过FiT_CENTER是居中显示，而FIT_START是置于顶部，FIT_END是置于底部。
                    *****/
                    viewFlipper.addView(iv, new LayoutParams(LayoutParams.MATCH_PARENT,
                            LayoutParams.MATCH_PARENT));
                }
                viewFlipper.setAutoStart(true); // 设置自动播放功能（点击事件，前自动播放）
                viewFlipper.setFlipInterval(3000);//间隔3秒
                if (viewFlipper.isAutoStart() && !viewFlipper.isFlipping()) {
                    viewFlipper.startFlipping();//自动播放
                }
            }

            @Override
            public boolean onTouchEvent(MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        startX=event.getX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        viewFlipper.stopFlipping();
                        tempX=event.getX();
                        if(tempX-startX>100){//向右滑动看前一页
                            viewFlipper.showPrevious();
                        viewFlipper.setInAnimation(this,R.anim.push_left_in);
                        viewFlipper.setOutAnimation(this,R.anim.push_left_out);
                        }else if(startX-tempX>100){//向左滑动看后一页
                            viewFlipper.showNext();
                    viewFlipper.setInAnimation(this,R.anim.push_right_in);
                    viewFlipper.setOutAnimation(this,R.anim.push_right_out);
                        }
                        break;
                }
                return super.onTouchEvent(event);
            }
        }
7、ViewPager+FragmentStatePagerAdapter 实现仿微信Tab：
  （1） 主布局文件（main.xml）：
        <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="vertical" >
            <android.support.v4.view.ViewPager
                android:id="@+id/viewpager"
                android:layout_width="match_parent"
                android:layout_height="0dp"
                android:layout_weight="1" >
            </android.support.v4.view.ViewPager>
            <include layout="@layout/bottom" />
        </LinearLayout>

   （2）底部栏文件（bottom.xml）
        <?xml version="1.0" encoding="utf-8"?>
        <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
            android:layout_width="match_parent"
            android:layout_height="70dp"
            android:background="#ffffffff"
            android:orientation="horizontal" >
            <LinearLayout
                android:id="@+id/ll_chat"
                android:layout_width="0dp"
                android:layout_height="fill_parent"
                android:layout_weight="1"
                android:gravity="center"
                android:orientation="vertical" >
                <ImageView
                    android:id="@+id/img_chat"
                    android:layout_width="40dp"
                    android:layout_height="40dp"
                    android:background="#0000"
                    android:src="@drawable/chat_yes" />
                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="微信"
                    android:textColor="#b6b3b3" />
            </LinearLayout>
            <LinearLayout
                android:id="@+id/ll_frd"
                android:layout_width="0dp"
                android:layout_height="fill_parent"
                android:layout_weight="1"
                android:gravity="center"
                android:orientation="vertical" >
                <ImageView
                    android:id="@+id/img_frd"
                    android:layout_width="40dp"
                    android:layout_height="40dp"
                    android:background="#0000"
                    android:src="@drawable/frd_no" />
                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="通讯录"
                    android:textColor="#b6b3b3" />
            </LinearLayout>
            <LinearLayout
                android:id="@+id/ll_find"
                android:layout_width="0dp"
                android:layout_height="fill_parent"
                android:layout_weight="1"
                android:gravity="center"
                android:orientation="vertical" >
                <ImageView
                    android:id="@+id/img_find"
                    android:layout_width="40dp"
                    android:layout_height="40dp"
                    android:background="#0000"
                    android:src="@drawable/find_no" />
                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="发现"
                    android:textColor="#b6b3b3" />
            </LinearLayout>
            <LinearLayout
                android:id="@+id/ll_me"
                android:layout_width="0dp"
                android:layout_height="fill_parent"
                android:layout_weight="1"
                android:gravity="center"
                android:orientation="vertical" >
                <ImageView
                    android:id="@+id/img_me"
                    android:layout_width="40dp"
                    android:layout_height="40dp"
                    android:background="#0000"
                    android:src="@drawable/me_no" />
                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="我"
                    android:textColor="#b6b3b3" />
            </LinearLayout>
        </LinearLayout>
   （3）四个子 View 布局文件（tab01.xml）：
        <?xml version="1.0" encoding="utf-8"?>
        <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:gravity="center"
            android:orientation="vertical" >
            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_gravity="center"
                android:text="聊天页"
                android:textSize="30sp" >
            </TextView>
        </LinearLayout>
   （4）四个 Fragment 的代码（MyFragment1.java）：
        public class MyFragment1 extends Fragment {
            @Nullable
            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                return inflater.inflate(R.layout.tab01,null);
            }
        }
   （5） 适配器类代码（ViewPagerFragmentAdapter.java）：
            public class ViewPagerFragmentAdapter extends FragmentStatePagerAdapter {
                private List<Fragment> datas;
                public ViewPagerFragmentAdapter(FragmentManager fm,List<Fragment> datas) {
                    super(fm);
                    this.datas=datas;
                }
                @Override
                public Fragment getItem(int position) {//返回子View对象
                    return datas.get(position);
                }
                @Override
                public int getCount() {//返回子View的个数
                    return datas.size();
                }
                @Override
                public Object instantiateItem(ViewGroup container, int position) {//初始子View方法
                    return super.instantiateItem(container, position);
                }
                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {//销毁子View
                    super.destroyItem(container, position, object);
                }
            }
   （6）MainActivity代码（MainActivity.java）：
        public class MainActivity extends FragmentActivity implements OnClickListener {
            //主要要继承自FragmentActivity,这样才能在初始适配器类是使用getSupportFragmentManager方法获取FragmentManager对象
            private ViewPager mViewPager;
            private List<Fragment> datas;
            private ViewPagerFragmentAdapter viewPagerFragmentAdapter;
            private LinearLayout mLLChat,mLLFrd,mLLFind,mLLMe;
            private ImageView mImageViewChat,mImageViewFrd,mImageViewFind,mImageViewMe;
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                requestWindowFeature(Window.FEATURE_NO_TITLE);
                setContentView(R.layout.activity_main);
                initDatas();//初始化数据集
                initView();// 初始化控件
                initEvent();// 注册单击监听
                viewPagerFragmentAdapter=new ViewPagerFragmentAdapter(getSupportFragmentManager(),datas);//初始化适配器类
                mViewPager.setAdapter(viewPagerFragmentAdapter);
            }
            private void initDatas() {
                datas=new ArrayList<Fragment>();
                datas.add(new MyFragment1());
                datas.add(new MyFragment2());
                datas.add(new MyFragment3());
                datas.add(new MyFragment4());

            }
            private void initEvent() {
                mLLChat.setOnClickListener(this);
                mLLFrd.setOnClickListener(this);
                mLLFind.setOnClickListener(this);
                mLLMe.setOnClickListener(this);
                mViewPager.setOnPageChangeListener(new OnPageChangeListener() {//ViewPager滑动切换监听
                    @Override
                    public void onPageSelected(int arg0) {
                        int currentItem=mViewPager.getCurrentItem();
                        resetImag();
                        switch (currentItem) {
                        case 0:
                        mImageViewChat.setImageResource(R.drawable.chat_yes);
                        break;
                        case 1:
                        mImageViewFrd.setImageResource(R.drawable.frd_yes);
                        break;
                        case 2:
                        mImageViewFind.setImageResource(R.drawable.find_yes);
                        break;
                        case 3:
                        mImageViewMe.setImageResource(R.drawable.me_yes);
                        break;
                        default:
                        break;
                        }
                    }
                    @Override
                    public void onPageScrolled(int arg0, float arg1, int arg2) {

                    }
                    @Override
                    public void onPageScrollStateChanged(int arg0) {

                    }
                });
            }
            private void initView() {
                mViewPager = (ViewPager) findViewById(R.id.viewpager);
                mLLChat = (LinearLayout) findViewById(R.id.ll_chat);
                mLLFrd = (LinearLayout) findViewById(R.id.ll_frd);
                mLLFind = (LinearLayout) findViewById(R.id.ll_find);
                mLLMe = (LinearLayout) findViewById(R.id.ll_me);
                mImageViewChat = (ImageView) findViewById(R.id.img_chat);
                mImageViewFrd = (ImageView) findViewById(R.id.img_frd);
                mImageViewFind = (ImageView) findViewById(R.id.img_find);
                mImageViewMe = (ImageView) findViewById(R.id.img_me);
            }
            @Override
            public void onClick(View v) {
                resetImag();
                switch (v.getId()) {
                case R.id.ll_chat:
                    mViewPager.setCurrentItem(0);
                    mImageViewChat.setImageResource(R.drawable.chat_yes);
                    break;
                case R.id.ll_frd:
                    mViewPager.setCurrentItem(1);
                    mImageViewFrd.setImageResource(R.drawable.frd_yes);
                    break;
                case R.id.ll_find:
                    mViewPager.setCurrentItem(2);
                    mImageViewFind.setImageResource(R.drawable.find_yes);
                    break;
                case R.id.ll_me:
                    mViewPager.setCurrentItem(3);
                    mImageViewMe.setImageResource(R.drawable.me_yes);
                    break;
                default:
                    break;
                }
            }
            private void resetImag() {//重置图片
                mImageViewChat.setImageResource(R.drawable.chat_no);
                mImageViewFrd.setImageResource(R.drawable.frd_no);
                mImageViewFind.setImageResource(R.drawable.find_no);
                mImageViewMe.setImageResource(R.drawable.me_no);
            }
        }

8、FragmentPagerAdapter 和 FragmentStatePagerAdapter 的区别：
        由继承结构可以看出FragmentPagerAdapter继承自PagerAdapter，子页面由Fragment组成，该适配器没有实现页面销毁的方法，所有的页面都保存在内存当中，
    当页面比较大时要考虑使用FragmentStatePagerAdapter适配器类。 实现FragmentPagerAdapter时必须要覆写的方法是getItem和getCount方法。

