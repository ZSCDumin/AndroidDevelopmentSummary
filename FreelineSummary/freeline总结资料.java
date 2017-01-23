1、在工程的build.gradle配置如下：

	buildscript {
		repositories {
			jcenter()
		}
		dependencies {
			classpath 'com.antfortune.freeline:gradle:0.8.5'
		}
	}

2、在module的build.gradle配置如下：
	
	apply plugin: 'com.antfortune.freeline'
	android {
		...
		
		freeline {
          hack true
        }
	}
	dependencies {
		debugCompile 'com.antfortune.freeline:runtime:0.8.5'
		releaseCompile 'com.antfortune.freeline:runtime-no-op:0.8.5'
		testCompile 'com.antfortune.freeline:runtime-no-op:0.8.5'
    }
	
3、自定义的Application

	 public class App extends Application {
		 @Override
		 public void onCreate() {
			 super.onCreate();
			 FreelineCore.init(this);
		 }
	 }
	 
4、在AndroidStudio控制面板中输入下列命令：

	gradlew initFreeline -Pmirror
	python freeline.py
	
    **************注意事项：Python版本必须为 2.7 < version < 3.0*****************