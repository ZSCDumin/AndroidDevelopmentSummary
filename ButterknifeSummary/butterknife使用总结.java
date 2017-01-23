1、配置如下：

	 (1)module配置如下
	 
		dependencies {
		  compile 'com.jakewharton:butterknife:8.4.0'
		  annotationProcessor 'com.jakewharton:butterknife-compiler:8.4.0'
		}

     (2)整个工程配置如下

		buildscript {
		  repositories {
			mavenCentral()
		   }
		  dependencies {
			classpath 'com.jakewharton:butterknife-gradle-plugin:8.4.0'
		  }
		}

		apply plugin: 'com.android.library'
		apply plugin: 'com.jakewharton.butterknife'

		dependencies {
		  compile 'com.jakewharton:butterknife:8.4.0'
		  annotationProcessor 'com.jakewharton:butterknife-compiler:8.4.0'
		}
   
2、使用示例
    (1)单个module中使用的代码如下：
	
		class ExampleActivity extends Activity {
		  @BindView(R.id.user) EditText username;
		  @BindView(R.id.pass) EditText password;
		  @BindString(R.string.login_error) String loginErrorMessage;
		  @OnClick(R.id.submit) void submit() {
			// TODO call server...
		  }

		  @Override public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.simple_activity);
			ButterKnife.bind(this);
			// TODO Use fields...
		  }
		  
		  @UiThread
		  public void updateUI(){
			//更新UI操作
		  }
		}
		
	(2)整个工程使用的代码如下：
	
	    class ExampleActivity extends Activity {
		  @BindView(R2.id.user) EditText username;
		  @BindView(R2.id.pass) EditText password;
		  ...
		}
		
3、ButterKnife优势：

	(1)强大的View绑定和Click事件处理功能，简化代码，提升开发效率
	(2)方便的处理Adapter里的ViewHolder绑定问题
	(3)运行时不会影响APP效率，使用配置方便
	(4)代码清晰，可读性强

4、使用心得：

	(1)Activity ButterKnife.bind(this);必须在setContentView();
	   之后,且父类bind绑定后,子类不需要再bind().
	(2)Fragment ButterKnife.bind(this, mRootView);
	(3)属性布局不能用private or static 修饰，否则会报错
	(4)setContentView()不能通过注解实现。（其他的有些注解框架可以）