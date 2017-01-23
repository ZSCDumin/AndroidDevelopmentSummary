一、Android 内存泄漏总结：

1、原因分析

   （1）一般内存泄漏(traditional memory leak)的原因是： 由忘记释放分配的内存导致的。（如 Cursor 忘记关闭）

   （2）逻辑内存泄漏(logical memory leak)的原因是： 当应用不再需要这个对象，当仍未释放该对象的所有引用。

   （3）如果持有对象的强引用，垃圾回收器是无法在内存中回收这个对象。

2、在Android中，导致潜在内存泄漏的陷阱不外乎两种：

   （1）全局进程(process­global)的 static 变量。这个无视应用的状态，持有 Activity 的强引用的怪物。
   （2）活在 Activity 生命周期之外的线程。没有清空对 Activity 的强引用。

3、常见的内存泄漏的例子：

   （1）静态 Activity

   （2）静态 View

   （3）内部类

   （4）匿名类

   （5）Handlers

   （6）Threads

   （7）TimerTask

   （8）Sensor Manager

二、要避免内存泄露，主要要遵循以下几点：

	（1）不要为Context长期保存引用（要引用Context就要使得引用对象和它本身的生命周期保持一致）。
	（2）如果要使用到Context，尽量使用 ApplicationContext去代替Context，因为 ApplicationContext 的生命周期较长，引用情况下不会造成内存泄露问题.
	（3）在你不控制对象的生命周期的情况下避免在你的 Activity 中使用static变量。尽量使用 WeakReference 去代替一个static.
	（4）垃圾回收器并不保证能准确回收内存，这样在使用自己需要的内容时，主要生命周期和及时释放掉不需要的对象.
       尽量在Activity的生命周期结束时，在onDestroy中把我们做引用的其他对象做释放，比如：cursor.close()

       其实我们可以在很多方面使用更少的代码去完成程序。比如：我们可以多的使用 9 Patch 图片等。
       有很多细节地方都可以值得我们去发现、挖掘更多的内存问题。我们要是能做到 C/C++对于程序的“谁创建，
       谁释放”原则，那我们对于内存的把握，并不比 Java 或 Android 本身的 GC 机制差，而且更好的控制内存，
       能使我们的手机运行得更流畅。
