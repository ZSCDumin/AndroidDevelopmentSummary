1、在模块的 build.gradle 里面配置如下:

      dependencies {

        debugCompile 'com.squareup.leakcanary:leakcanary-android:1.5'
        releaseCompile 'com.squareup.leakcanary:leakcanary-android-no-op:1.5'
        testCompile 'com.squareup.leakcanary:leakcanary-android-no-op:1.5'

      }

2、在项目的 Application 类中配置如下:

      public class ExampleApplication extends Application {

        @Override public void onCreate() {
          super.onCreate();
          LeakCanary.install(this);//初始化LeakCanary
        }
      }

3、在 AndroidManifest 文件中配置如下：
      <application
        android:name=".MyApplication">
        ......
      />
