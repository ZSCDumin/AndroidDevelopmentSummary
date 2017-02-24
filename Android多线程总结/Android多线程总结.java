1、wait：线程进入等待池，失去对象的机锁，其他线程可以访问；使用notify/notifyAll或指定睡眠的时间来唤醒它。
2、sleep：线程睡眠，但不能改变对象的机锁，其他线程无法访问。当在一个synchronized块中调用sleep时，线程对象的机锁并没有释放。其他线程无法访问。
3、join：等待目标线程执行完成之后再继续执行。
4、yield：线程礼让，有运行态转为就绪态，让出执行权限，让其他线程得以优先执行（能否优先执行情况不知）。
5、多线程相关的方法：Runnable,Callable,Future,FutureTask。
6、创建固定数量的线程池的方法：
  （1）最常用的就是使用Executors.nextFixedThreadPool(int size)函数。
  （2）代码如下：
        ExecutorService executorService= Executors.newFixedThreadPool(3);
        for (int i = 0; i < max; i++) {
            Future<Integer> task=executorService.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    System.out.print("*******");
                    return null;
                }
            });
        }
7、当线程池中没有空闲的线程可用时，且必须创建一个新的线程来执行任务时，可通过Executors的newCachedThreadPool()方法来实现。
  （1）代码如下：
        ExecutorService executorService= Executors.newCachedThreadPool();
        for (int i = 0; i < max; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.print("*********");
                }
            });
        }
8、定时执行一些任务：
  （1）代码如下：
        ScheduledExecutorService executorService=Executors.newScheduledThreadPool(3);
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.print("********");
            }
        },1,2, TimeUnit.SECONDS);//第二个参数表示第一个延迟的时间，第三个参数表示执行的周期。