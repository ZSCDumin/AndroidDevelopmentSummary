1、动态添加碎片主要分为五步：
  （1）创建待添加碎片的实例。
       ---例如: new RightFragment()
  （2）获取FragmentManager,活动中可以直接通过调用getSupportFragmentManager()方法得到。
       ---例如: FragmentManager fragmentManager=getSupportFragmentManager();
  （3）开启一个事务，通过调用beginTransaction()方法开启。
       ---例如: FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
  （4）向容器内添加或替换碎片，一般使用replace()方法实现，需要传入容器的Id和待添加的碎片实例。
       ---例如: fragmentTransaction.replace(R.id.right_layout,fragment);
  （5）提交事务，调用commit()方法来完成。
       ---例如: fragmentTransaction.commit();
2、在碎片中模拟返回栈
   fragmentTransaction.addToBackStack(null);//将一个事务添加到返回栈中（接收一个名字用于描述返回栈的状态）
3、碎片与活动之间进行通信
  （1）专门用于从布局文件中获取碎片的实例，进而调用碎片里的方法（活动中调用碎片的方法）
        RightFragment rightFragment = (RightFragment) getFragmentManager().
        findFragmentById(R.id.right_fragment);
  （2）碎片中调用活动中的方法
        MainActivity activity = (MainActivity) getActivity();
    基本思路：首先在一个碎片中找到与之相关联的活动，再通过这个活动去获取另外一个碎片的实例。
4、Fragment的生命周期
  （1）碎片的状态：运行、暂停、停止、销毁状态。
  （2）碎片的回调方法：
        a. onAttach()  //当碎片与活动建立关联的时候调用
        b. onCreateView()  //为碎片创建视图的时候调用
        c. onActivityCreate()  //确保与碎片相关联的活动一定已经创建完毕的时候调用
        d. onDestroyView()  //当与碎片关联的视图被移除时调用
        e. onDetach()   //当碎片和活动结束关联的时候调用
5、动态加载布局的技巧
  （1）使用限定符
       例如：large限定符  ---当屏幕被认为是large的设备就会自动加载layout-large文件夹下的布局,
            而小屏幕的设备则还是会加载layout文件夹下的布局。
      【常见的限定符】：
                       small:   提供给小屏幕设备的资源
                       normal:  提供给中等屏幕设备的资源
                       large:   提供给大屏幕设备的资源
                       xlarge:  提供给超大屏幕设备的资源
  （2）使用最小宽度限定符：用dp为单位
       例如：layout-sw600dp  ---当屏幕宽度大于600dp时，会加载layout-sw600dp文件夹下的布局；
            小于的时候加载另外一个布局（默认布局）。
