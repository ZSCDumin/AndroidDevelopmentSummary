1、全局获取 Context 的技巧：
  （1）getApplicationContext()方法获取
  （2）通过自定义的Application类来获取，代码如下：
        public class MyApplication extends Application {
            public static Context context;
            @Override
            public void onCreate() {
                super.onCreate();
                LitepalApplication.initalize(context);  //初始化Litepal
            }

            public static Context getContext() {
                return context;
            }
        }
2、Serializable 接口的使用：
  （1）自定义Person类实现Serializable接口：
        public class Person implements Serializable {
            public String name;
            public String age;

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }
            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
  （2）逻辑代码如下：
        /*******利用Intent传送对象数据*******/
        Person person=new Person();
        person.setAge("20");
        person.setName("dumin");
        Intent intent=new Intent(this,MainActivity.class);
        intent.putExtra("person_data",person);
        startActivity(intent);

        /********获取对象数据*********/
        Person person= (Person) getIntent().getSerializableExtra("person_data");

3、Parcelable 接口的使用：
  （1）自定义Person类实现Serializable接口：
        public class Person1 implements Parcelable {
            public String name;
            public String age;

            protected Person1(Parcel in) {
                name = in.readString();
                age = in.readString();
            }

            public String getName() {
                return name;
            }

            public Person1(String name, String age) {
                this.name = name;
                this.age = age;
            }

            public String getAge() {
                return age;
            }

            public static final Creator<Person1> CREATOR = new Creator<Person1>() {
                @Override
                public Person1 createFromParcel(Parcel in) {
                    return new Person1(in);
                }

                @Override
                public Person1[] newArray(int size) {
                    return new Person1[size];
                }
            };

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(name);
                dest.writeString(age);
            }
        }

  （2）逻辑代码如下：
        /*******利用Intent传送对象数据*******/
        Person person=new Person();
        person.setAge("20");
        person.setName("dumin");
        Intent intent=new Intent(this,MainActivity.class);
        intent.putExtra("person_data",person);
        startActivity(intent);

        /********获取对象数据*********/
        Person1 person1 = (Person1) getIntent().getParcelableExtra("person_data");
4、定制自己的日志消息类：
    public class LogUtils {
        public static final int VERBOSE = 1;
        public static final int DEBUG = 2;
        public static final int INFO = 3;
        public static final int WARN = 4;
        public static final int ERROR = 5;
        public static final int NOTHING = 6;//level等于nothing时，屏蔽所有的日志消息
        public static int level = VERBOSE;

        public static void v(String tag, String msg) {
            if (level <= VERBOSE)
                Log.v(tag, msg);
        }

        public static void i(String tag, String msg) {
            if (level <= VERBOSE)
                Log.v(tag, msg);
        }

        public static void w(String tag, String msg) {
            if (level <= VERBOSE)
                Log.v(tag, msg);
        }

        public static void e(String tag, String msg) {
            if (level <= VERBOSE)
                Log.v(tag, msg);
        }

        public static void d(String tag, String msg) {
            if (level <= VERBOSE)
                Log.v(tag, msg);
        }
    }

    /************具体使用代码如下：*************/
    LogUtils.i("TAG","Messages");
5、Alarm 机制使用方法：
    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);  
    long time = SystemClock.elapsedRealtime() + 10 * 1000;
    //在非Doze模式下适用
    alarmManager.set(AlarmManager.ELAPSED_REALTIME, time, pendingIntent);

    //在Doze模式下也适用
    alarmManager.setAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME, time, pendingIntent);
    alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME, time, pendingIntent);

    //代码解析：
    AlarmManager.ELAPSED_REALTIME：从系统开机开始算起，但不会唤醒CPU。
    AlarmManager.ELAPSED_REALTIME_WAKEUP：：从系统开机开始算起，但会唤醒CPU。
    AlarmManager.RTC：从1970年1月1日1点开始算起，不会唤醒CPU。
    AlarmManager.RTC_WAKEUP：从1970年1月1日开始算起，会唤醒CPU。
    System.currentTimeMillis()：从1970年1月1日开始算起到现在的毫秒数。
    SystemClock.elapsedRealtime()：从系统开机开始算起到现在的毫秒数。
6、Lambda 表达式使用：
  （1）build.gradle添加配置如下：
        defaultConfig {
            jackOptions.enabled = true
        }
        compileOptions {
            sourceCompatibility JavaVersion.VERSION_1_8
            targetCompatibility JavaVersion.VERSION_1_8
        }
  （2）使用范例如下：
        new Thread(()->{
            //逻辑代码
        }).start();
        
        Button button=new Button(this);
        button.setOnClickListener((v)->{
            //逻辑代码
        });