1、进程与线程之间的关系：一对多的关系。
2、线程是CPU调度的最小单元，进程一般指一个执行单元。
3、Android 中开启多线程模式：
   常见的方式就是给四大组件在清单文件中指定android:process的属性,另一种特殊的方式就是通过JNI在native层去fork一个新进程。
4、示例代码：
   <activity 
       android:process=":remote"
       或者
       android:process="com.zsc.dumin.remote">
    />

    代码解释：
        ":"的含义表示要在当前的进程名前面附加上当前的包名，这是一种简写的方式，
    且以":"开头的进程属于当前进程的私有进程，其他应用组件不可以和它跑在同一个进程中，
    而进程名不以":"开头的进程属于全局进程，其他应用通过shareUID方式可以和它跑在同一个进程中。
5、一般来说多进程造成如下的问题：
   （1）静态成员和单例模式完全失效。
   （2）线程同步机制完全失效。
   （3）SharePreferences 的可能性下降。
   （4）Application 会多次创建。
6、Serializable 序列化和反序列化：
   （1）实现Serializable接口。
   （2）采用ObjectOutputStream和ObjectInputStream使实现序列化和反序列化。
   （3）指定SerialVersionUID用于判断当前类的序列化的类是否发生变化。
   （4）静态成员变量和用transition关键字标记的成员变量不参与序列化过程。
7、AIDL 原理解析：
   （1）系统根据AIDL文件自动产生的类文件中的方法分析：
       ==> asInterface(android.os.IBinder obj)：
            用于将服务端的Binder对象转换成客户端的AIDL接口类型的对象，如果客户端和服务端位于同一个进程，
            则返回的就是服务端的Stub对象，否则返回系统封装后的Stub.Proxy对象。
       ==> asBinder：
            返回当前的Binder对象。
       ==> onTransact(int code,Parcel data,Parcel reply,int flags)：
            服务端通过code可以确定客户端所请求的目标方法，接着从data中取出目标方法所需要的参数，然后执行目标方法，执行完后就向reply中写入返回值，
            如果此方法返回false，那么客户端请求失败，可以利用这个来做权限访问验证。