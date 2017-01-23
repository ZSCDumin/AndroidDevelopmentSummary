1、创建线程的常见方法：
  （1）自定义类继承 Thread：
        class MyThread extends Thread{
            @Override
            public void run() {
                //处理具体逻辑
            }
        }
        new MyThread().start();//启动线程
  （2）实现Runnable接口：
        class MyThread implements Runnable{
            @Override
            public void run() {
                //处理具体逻辑
            }
        }
        new Thread(new MyThread()).start();
  （3）匿名内部类：
        new Thread(new Runnable() {
            @Override
            public void run() {
                //处理具体逻辑
            }
        }).start();

2、Android 异步消息机制：
  （1）Android 中规定更新 UI 必须在主线程中执行。
  （2）采用 Handler 消息机制更新 UI,代码如下：
       public class MainActivity extends AppCompatActivity implements View.OnClickListener {

          public static final int UPDATE_TEXT = 1;
          private TextView text;
          private Handler handler = new Handler() {
              public void handleMessage(Message msg) {
                  switch (msg.what) {
                      case UPDATE_TEXT:
                          // 在这里可以进行UI操作
                          text.setText("Nice to meet you");
                          break;
                      default:
                          break;
                  }
              }
          };

          @Override
          protected void onCreate(Bundle savedInstanceState) {
              super.onCreate(savedInstanceState);
              setContentView(R.layout.activity_main);
              text = (TextView) findViewById(R.id.text);
              Button changeText = (Button) findViewById(R.id.change_text);
              changeText.setOnClickListener(this);
          }

          @Override
          public void onClick(View v) {
              switch (v.getId()) {
                  case R.id.change_text:
                      new Thread(new Runnable() {
                          @Override
                          public void run() {
                              Message message = new Message();
                              message.what = UPDATE_TEXT;
                              handler.sendMessage(message); // 将Message对象发送出去
                          }
                      }).start();
                      break;
                  default:
                      break;
              }
          }
       }
  （3）异步消息主要有四个部分组成：
       Message：属于线程间传递的消息，在线程之间进行少量数据的交换。
       Handler：消息处理者，用于消息的发送的处理，使用sendMessage()方法发送消息，
                使用handleMessage()方法进行消息的处理。
       MessageQueue：消息队列，存放 Handle 发送的消息。一个线程只能拥有一个此对象。
       Looper：MessageQueue 的管家，调用loop()方法会进入一个无限循环，每当发现一条消息时，就会发送到
               handleMessage()方法中，每个线程只有一个 Looper 对象。


3、异步任务（AsyncTask）：
  （1）onPreExecute()方法：
       后台任务初始化之前调用，用于界面上的初始化操作，比如对话框的显示。
  （2）doInBackground(Params ...)方法：
       执行耗时操作，由return语句返回任务结果。
  （3）onProgressUpdate(Progress ...)方法：
       更新UI。
  （4）onPostExecute()方法：
       后台任务结束并通过return语句返回结果时调用，比如提醒任务处理完毕，关闭对话框。
