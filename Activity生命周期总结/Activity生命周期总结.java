1、Activity 生命周期：见图。
   （1）onCreate：活动正在被创建。
   （2）onReStart：活动正在重新启动。
   （3）onStart：活动正在被启动。
   （4）onResume：活动已经可见。
   （5）onPause：活动正在停止。
   （6）onStop：活动即将停止。
   （7）onDestroy：活动即将被销毁。
   
    打开新的活动时，先执行就活动的onPause()，然后新的活动再启动。
2、不想系统重新创建活动时，可以给活动添加如下的属性：
   android:configChanges="orientation"> //屏幕方向发生改变时。
3、活动的三条回路：
   （1）onPause ---> onResume ---> onPause
   （2）onStop ---> onCreate ---> onStart ---> onResume ---> onPause ---> onStop
   （3）onCreate ---> onStart ---> onResume ---> onPause ---> onStop --->onDestrooy
4、活动的启动模式：
   （1）standard：标准模式。（运行一次创建一个实例）
   （2）singleTop：栈顶复用。（在栈顶时不会创建实例，否则创建新实例）
   （3）singleTask：栈内复用。（有实例时不创建新实例；若实例不在栈顶，则把其调到栈顶【删除其上的活动】，只有一个实例存在）
   （4）singleInstance：单实例模式。（独占一个任务栈，且只有一个实例）
5、Activity 的Flags：
   （1）FLAG_ACTIVITY_NEW_TASK：singleTask
   （2）FLAG_ACTIVITY_SINGLE_TOP：singleTop
   （3）FLAG_ACTIVITY_CLEAR_TOP：singleTask
   （4）FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS：相当于android:excludeFromRecents="true" //禁止通过历史列表回到之前的活动
6、