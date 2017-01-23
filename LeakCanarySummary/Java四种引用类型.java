1、强引用

   （1）概念：

        强引用是我们接触最多的引用，只要某个对象有强引用与之关联，JVM 必定不会回收这个对象，
        即使在内存不足的情况下，JVM 宁愿抛出OutOfMemory错误也不会回收这种对象。

   （2）例子如下：

        Object object = new Object();
        String str = "hello";
        
        比如下面这段代码：

        public class Main {
            public static void main(String[] args) {
                new Main().fun1();
            }
              
            public void fun1() {
                Object object = new Object();
                Object[] objArr = new Object[1000];
            }
        }

        当 Fun1 运行完之后, Object 和 ObjArr 都已经不存在了,所以它们指向的对象都会被 JVM 回收。
        如果想中断强引用和某个对象之间的关联，可以显示地将引用赋值为 NULL,这样一来的话,
        JVM 在合适的时间就会回收该对象。

2、软引用

  （1）概念：

        具有软引用的对象，内存空间充足的时候，垃圾回收器不会回收，当内存空间不充足的时候，垃圾回收器回收。
  
  （2）作用：

        软引用可用来实现内存敏感的高速缓存,比如网页缓存、图片缓存等。使用软引用能防止内存泄露,
        增强程序的健壮性。
        软引用的特点是它的一个实例保存对一个 Java 对象的软引用，该软引用的存在不妨碍垃圾收集线程
        对该 Java 对象的回收。也就是说，一旦软引用保存了对一个 Java 对象的软引用后，在垃圾线程对
        这个 Java 对象回收前，软引用类所提供的get()方法返回 Java 对象的强引用。另外，一旦垃圾线程
        回收该Java对象之后, get()方法将返回null。
 
        public class SoftReferenceTest {
            public static void main(String[] args) {
                String string = new String("hello world");
                SoftReference<String> reference = new SoftReference<String>(string);
                string = null;
                System.out.println("gc()前弱引用所指向的对象是: "+reference.get());
                System.gc();//gc()不一定立刻执行垃圾回收
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("gc()后弱引用所指向的对象是: "+reference.get());
            }
        }
        //结果（因为内存充足所以没有被回收）
        gc()前弱引用所指向的对象是: hello world
        gc()后弱引用所指向的对象是: hello world

3、弱引用

  （1）概念：

        在垃圾回收器线程扫描它所管辖的内存区域的过程中，一旦发现了只具有弱引用的对象，
        不管当前内存空间足够与否，都会回收它的内存。
 
  （2）例子如下：

        public class WeakReferenceTest {
            public static void main(String[] args) {
                String string = new String("hello world");
                WeakReference<String> reference = new WeakReference<String>(string);
                string = null;
                System.out.println("gc()前弱引用所指向的对象是: " + reference.get());
                System.gc();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("gc()后弱引用所指向的对象是: " + reference.get());
            }
        }
        //结果
        gc()前弱引用所指向的对象是: hello world
        gc()后弱引用所指向的对象是: null

        String string = "hello world";//常量池不能被回收
        WeakReference<String> reference = new WeakReference<String>(string);
        string = null;
        System.out.println("gc()前弱引用所指向的对象是: " + reference.get());
        System.gc();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("gc()后弱引用所指向的对象是: " + reference.get());

        //结果
        gc()前弱引用所指向的对象是: hello world
        gc()后弱引用所指向的对象是: hello world

4、虚引用

  （1）概念：

        虚引用主要用来跟踪对象被垃圾回收器回收的活动。
        虚引用与软引用和弱引用的一个区别在于：虚引用必须和引用队列 （ReferenceQueue）联合使用。
        当垃圾回收器准备回收一个对象时，如果发现它还有虚引用，就会在回收对象的内存之前，
        把这个虚引用加入到与之关联的引用队列中。
 
  （2）例子如下：

            public class PhantomReferenceTest {
                public static void main(String[] args) {
                    String string = new String("hello world");
                    //必须和引用队列一起使用
                    PhantomReference<String> reference = new PhantomReference<String>(string,new ReferenceQueue<String>());
                    string = null;
                    System.out.println("gc()前虚引用所指向的对象是: " + reference.get());
                    System.gc();//gc()不一定立刻执行垃圾回收
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("gc()后虚引用所指向的对象是: " + reference.get());
                }
            }
            //结果
            gc()前虚引用所指向的对象是: null
            gc()后虚引用所指向的对象是: null

            一个永远返回null的reference要来何用,请注意构造 PhantomReference 时的第二个参数
            ReferenceQueue(事实上 WeakReference & SoftReference 也可以有这个参数)，
            PhantomReference 唯一的用处就是跟踪referent何时被 enqueue到 ReferenceQueue 中。
            
            引用队列:

                软引用、弱引用可以和一个引用队列（ReferenceQueue）联合使用，如果软引用所引用的
                对象被垃圾回收器回收，Java 虚拟机就会把这个软引用加入到与之关联的引用队列中。
             

            //软引用和引用队列
            public class ReferenceQueueTest {
                public static void main(String[] args) throws InterruptedException {
                    Object referent = new Object();
                    ReferenceQueue<Object> referenceQueue = new ReferenceQueue<Object>();
                    SoftReference<Object> weakReference = new SoftReference<Object>(referent, referenceQueue);

                    System.out.println("垃圾回收器或程序是否添加该引用到引用队列:   " + weakReference.isEnqueued());
                    Reference<? extends Object> polled = referenceQueue.poll();
                    System.out.println("返回队列可用的对象: " + polled);
                    referent = null;
                    System.gc();
                    Thread.sleep(1000);
                    //weakReference.enqueue();//取消注释则运行结果和弱引用一样
                    System.out.println("垃圾回收器及程序是否添加该引用到引用队列:   " + weakReference.isEnqueued());

                    Reference<? extends Object> removed = referenceQueue.remove();
                    System.out.println("阻塞移除队列的中的引用对象:   " + removed);
                }
            }
            //结果(阻塞)
            垃圾回收器或程序是否添加该引用到引用队列:   false
            返回队列可用的对象: null
            垃圾回收器及程序是否添加该引用到引用队列:   false

            //弱引用和引用队列
            public class WeakReferenceTest {
                public static void main(String[] args) throws InterruptedException {
                    Object referent = new Object();
                    ReferenceQueue<Object> referenceQueue = new ReferenceQueue<Object>();
                    WeakReference<Object> weakReference = new WeakReference<Object>(referent, referenceQueue);

                    System.out.println("垃圾回收器或程序是否添加该引用到引用队列    :   " + weakReference.isEnqueued());
                    Reference<? extends Object> polled = referenceQueue.poll();
                    System.out.println("返回队列可用的对象   : " + polled);
                    referent = null;
                    System.gc();
                    Thread.sleep(1000);
                    System.out.println("垃圾回收器及程序是否添加该引用到引用队列    :   " + weakReference.isEnqueued());

                    Reference<? extends Object> removed = referenceQueue.remove();
                    System.out.println("阻塞移除队列的中的引用对象   :   " + removed);
                }
            }
            //结果
            垃圾回收器或程序是否添加该引用到引用队列    :   false
            返回队列可用的对象   : null
            垃圾回收器及程序是否添加该引用到引用队列    :   true
            阻塞移除队列的中的引用对象   :   java.lang.ref.WeakReference@511d50c0
            再谈虚引用

      （1）用来实现比较精细的内存使用控制

            private byte[] data = new byte[0];
            private ReferenceQueue<byte[]> queue = new ReferenceQueue<byte[]>();
            private PhantomReference<byte[]> ref = new PhantomReference<byte[]>(data, queue);
            public byte[] get(int size) {
                if (size <= 0) {
                    throw new IllegalArgumentException("Wrong buffer size");
                }
                if (data.length < size) {
                    data = null;
                    System.gc(); //强制运行垃圾回收器
                    try {
                        queue.remove(); //该方法会阻塞直到队列非空
                        ref.clear(); //幽灵引用不会自动清空，要手动运行
                        ref = null;
                        data = new byte[size];
                        ref = new PhantomReference<byte[]>(data, queue);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return data;
            }

5、再谈弱引用

        WeakHashMap 使用 WeakReference 作为 key， 一旦没有指向 key 的强引用, 
        WeakHashMap 在 GC 后将自动删除相关的 entry。（ThreadLocal中有WeakHashMap的使用，
        大家可以看看ThreadLocal的源码）

        Map<Object, Object> weakHashMap = new WeakHashMap<Object, Object>();
        Object key = new Object();
        Object value = new Object();
        weakHashMap.put(key, value);
        System.out.println(weakHashMap.containsValue(value));

        key = null;
        System.gc();
        Thread.sleep(1000);

        //一旦没有指向 key 的强引用, WeakHashMap 在 GC 后将自动删除相关的 entry

        System.out.println(weakHashMap.containsValue(value));

        //结果
        true
        false


6、进一步理解软引用和弱引用
   
        这2种既有相似之处又有区别。它们都是用来描述非必需对象的，但是被软引用关联的对象只有在内存不
        足时才会被回收，而被弱引用关联的对象在JVM进行垃圾回收时总会被回收。针对上面的特性，软引用适
        合用来进行缓存，当内存不够时能让JVM回收内存，弱引用能用来在回调函数中防止内存泄露。因为回调
        函数往往是匿名内部类，隐式保存有对外部类的引用，所以如果回调函数是在另一个线程里面被回调,而
        这时如果需要回收外部类，那么就会内存泄露，因为匿名内部类保存有对外部类的强引用。