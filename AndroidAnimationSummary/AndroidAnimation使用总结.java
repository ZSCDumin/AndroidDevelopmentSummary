1、如何在XML文件中定义动
   (1)打开AS，新建Android工程
  （2）在res目录中新建anim文件夹
  （3）在anim目录中新建一个myanim.xml(注意文件名小写)
  （4）加入XML的动画代码
        <?xml version="1.0" encoding="utf-8"?>
        <set xmlns:android="http://schemas.android.com/apk/res/android">
            <alpha/>
            <scale/>
            <translate/>
            <rotate/>
        </set>

            /****渐变透明度动画效果****/
            <?xml version="1.0" encoding="utf-8"?>
            <set xmlns:android="http://schemas.android.com/apk/res/android" >
            <alpha
                android:fromAlpha="0.1"
                android:toAlpha="1.0"
                android:duration="3000"/> 
                <!-- 透明度控制动画效果 alpha
                        浮点型值：
                            fromAlpha 属性为动画起始时透明度
                            toAlpha   属性为动画结束时透明度
                            说明: 
                                0.0表示完全透明
                                1.0表示完全不透明
                            以上值取0.0-1.0之间的float数据类型的数字
                        
                        长整型值：
                            duration  属性为动画持续时间
                            说明:     
                                时间以毫秒为单位
                -->
            </set>

             /****	渐变尺寸伸缩动画效果****/
            <?xml version="1.0" encoding="utf-8"?>
            <set xmlns:android="http://schemas.android.com/apk/res/android">
                <scale  
                        android:interpolator="@android:anim/accelerate_decelerate_interpolator"
                        android:fromXScale="0.0"
                        android:toXScale="1.4"
                        android:fromYScale="0.0"
                        android:toYScale="1.4"
                        android:pivotX="50%"
                        android:pivotY="50%"
                        android:fillAfter="false"
                        android:duration="700" />
            </set>
            <!-- 尺寸伸缩动画效果 scale
                属性：interpolator 指定一个动画的插入器
                    在我试验过程中，使用android.res.anim中的资源时候发现
                    有三种动画插入器:
                        accelerate_decelerate_interpolator  加速-减速 动画插入器
                        accelerate_interpolator        加速-动画插入器
                        decelerate_interpolator        减速- 动画插入器
                    其他的属于特定的动画效果
                浮点型值：
                    
                        fromXScale 属性为动画起始时 X坐标上的伸缩尺寸    
                        toXScale   属性为动画结束时 X坐标上的伸缩尺寸     
                    
                        fromYScale 属性为动画起始时Y坐标上的伸缩尺寸    
                        toYScale   属性为动画结束时Y坐标上的伸缩尺寸    
                    
                        说明:
                            以上四种属性值    
                
                                0.0表示收缩到没有 
                                1.0表示正常无伸缩     
                                值小于1.0表示收缩  
                                值大于1.0表示放大
                    
                        pivotX     属性为动画相对于物件的X坐标的开始位置
                        pivotY     属性为动画相对于物件的Y坐标的开始位置
                    
                        说明:
                                以上两个属性值 从0%-100%中取值
                                50%为物件的X或Y方向坐标上的中点位置
                    
                    长整型值：
                        duration  属性为动画持续时间
                        说明:   时间以毫秒为单位
            
                    布尔型值:
                        fillAfter 属性 当设置为true ，该动画转化在动画结束后被应用
            -->

            /****画面转换位置移动动画效果****/
            <?xml version="1.0" encoding="utf-8"?>
            <set xmlns:android="http://schemas.android.com/apk/res/android">
                <translate
                android:fromXDelta="30"
                android:toXDelta="-80"
                android:fromYDelta="30"
                android:toYDelta="300"
                android:duration="2000"
                />
                <!-- translate 位置转移动画效果
                        整型值:
                            fromXDelta 属性为动画起始时 X坐标上的位置    
                            toXDelta   属性为动画结束时 X坐标上的位置
                            fromYDelta 属性为动画起始时 Y坐标上的位置
                            toYDelta   属性为动画结束时 Y坐标上的位置
                            注意:
                                    没有指定fromXType toXType fromYType toYType 时候，
                                    默认是以自己为相对参照物             
                        长整型值：
                            duration  属性为动画持续时间
                            说明:   时间以毫秒为单位
                -->
            </set>

            /****画面转移旋转动画效果****/
            <?xml version="1.0" encoding="utf-8"?>
            <set xmlns:android="http://schemas.android.com/apk/res/android">
            <rotate 
                    android:interpolator="@android:anim/accelerate_decelerate_interpolator"
                    android:fromDegrees="0"   //属性为动画起始时物件的角度
                    android:toDegrees="+350"  //属性为动画结束时物件旋转的角度(可以大于360度)
                    android:pivotX="50%"   
                    android:pivotY="50%"    
                    android:duration="3000" />  
                    <!-- rotate 旋转动画效果
                        属性：interpolator 指定一个动画的插入器
                                在我试验过程中，使用android.res.anim中的资源时候发现
                                有三种动画插入器:
                                    accelerate_decelerate_interpolator   加速-减速 动画插入器
                                    accelerate_interpolator               加速-动画插入器
                                    decelerate_interpolator               减速- 动画插入器
                                其他的属于特定的动画效果
                                                
                        浮点数型值:
                                fromDegrees    属性为动画起始时物件的角度
                                toDegrees   属性为动画结束时物件旋转的角度 可以大于360度   
                    
                            
                                说明:
                                        当角度为负数——表示逆时针旋转
                                        当角度为正数——表示顺时针旋转              
                                        (负数from——to正数:顺时针旋转)   
                                        (负数from——to负数:逆时针旋转) 
                                        (正数from——to正数:顺时针旋转) 
                                        (正数from——to负数:逆时针旋转)       
                    
                                pivotX     属性为动画相对于物件的X坐标的开始位置
                                pivotY     属性为动画相对于物件的Y坐标的开始位置
                                    
                                说明:        以上两个属性值 从0%-100%中取值
                                            50%为物件的X或Y方向坐标上的中点位置
                    
                            长整型值：
                                duration  属性为动画持续时间
                                说明:       时间以毫秒为单位
                    -->
            </set>
2、View Animation 动画（补间动画）：
    Java Code 如下:
        private Animation myAnimation;
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
            case R.id.btnAlpha:
                /**
                * 使用XML中的动画效果 第一个参数Context为程序的上下文 第二个参数id为动画XML文件的引用
                */
                myAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_anim);
                imgPic.startAnimation(myAnimation);
                break;

            case R.id.btnScale:
                myAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_anim);
                imgPic.startAnimation(myAnimation);
                break;

            case R.id.btnTranslate:
                myAnimation = AnimationUtils.loadAnimation(this,
                        R.anim.translate_anim);
                imgPic.startAnimation(myAnimation);
                break;

            case R.id.btnRotate:
                myAnimation = AnimationUtils
                        .loadAnimation(this, R.anim.rotate_anim);
                imgPic.startAnimation(myAnimation);
                break;

            }
        }

3、Drawable Animation（逐帧动画）：
    步骤如下：
    （1）在res/drawable目录添加动画系列图片素材。
    （2）在drawable文件夹中添加动画Animation-list帧布局文件。
        <?xml version="1.0" encoding="utf-8"?>
        <!--
            根标签为animation-list，其中oneshot代表着是否只展示一遍，设置为false会不停的循环播放动画  
            根标签下，通过item标签对动画中的每一个图片进行声明  
            android:duration 表示展示所用的该图片的时间长度  
        -->
        <animation-list xmlns:android="http://schemas.android.com/apk/res/android"
            android:oneshot="false" >
            <item
                android:drawable="@drawable/cmmusic_progress_1"
                android:duration="150">
            </item>
            <item
                android:drawable="@drawable/cmmusic_progress_2"
                android:duration="150">
            </item>
            <item
                android:drawable="@drawable/cmmusic_progress_3"
                android:duration="150">
            </item>
            <item
                android:drawable="@drawable/cmmusic_progress_4"
                android:duration="150">
            </item>
            <item
                android:drawable="@drawable/cmmusic_progress_5"
                android:duration="150">
            </item>
            <item
                android:drawable="@drawable/cmmusic_progress_6"
                android:duration="150">
            </item>
            <item
                android:drawable="@drawable/cmmusic_progress_7"
                android:duration="150">
            </item>
            <item
                android:drawable="@drawable/cmmusic_progress_8"
                android:duration="150">
            </item>
        </animation-list>
    （3）Java 代码如下：
        public class AnimationActivity extends Activity implements OnClickListener {
            private ImageView imgPic;
            private Button btnStart, btnStop;
            private AnimationDrawable animationDrawable;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_animation);

                intiView();

                initData();
            }

            /**
            * 初始化组件
            */
            private void intiView() {
                imgPic = (ImageView) findViewById(R.id.imgPic);
                btnStart = (Button) findViewById(R.id.btnStart);
                btnStop = (Button) findViewById(R.id.btnStop);
            }

            /**
            * 初始化数据
            */
            private void initData() {
                btnStart.setOnClickListener(this);
                btnStop.setOnClickListener(this);
                //Sets a drawable as the content of this ImageView.
                imgPic.setImageResource(R.drawable.loading_anim);
                //给动画资源赋值
                animationDrawable = (AnimationDrawable) imgPic.getDrawable();
            }

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                case R.id.btnStart:
                    animationDrawable.start();//开始
                    break;

                case R.id.btnStop: 
                    animationDrawable.stop(); //停止
                    break;

                }
            }
        }

4、Android 加载Gif动画的控件：
   https://github.com/arvin505/gifloader

动画详解参考网址入下：http://blog.csdn.net/yanbober/article/details/46481171  