1、布局优化：
  （1）使用<include/>标签，将标题栏引入Activity中。
       <include
        android:id="@+id/top_title"
        layout="@layout/comment_title" />
  （2）使用<merge/>标签，适用条件为：出现了使用统一布局类的多层嵌套。
       例如：
                    -----ImageVIEW
           Fragment              ------Button
                    ----Fragment    
                                ------ImageView

           ====>转变后
                                       ----ImageView
                    ----RelativeLayout
                                       ----Button
           Fragment 
                    ----ImageView
   （3）ViewStub 视图：一个不可见的，并且在运行期间延迟加载目标的视图，其宽和高都为0。
        <ViewStub
             android:layout="@layout/layout_gv"/>

         gvStub.inflate(); //加载目标视图
         gvStub.setVisibility(View.VISIBLE);//设置视图可见
    
2、AndroidUI 布局中要遵守的原则包括如下几点：
  （1）尽量多使用 RelativeLayout,不要使用局对布局。
  （2）在 ListView 等列表组件中尽量避免使用 LinearLayout 的layout_weight属性。
  （3）将可复用的组件分离出来并通过<include/>标签使用。
  （4）使用<ViewStub/>标签来加载一些不常用的布局。
  （5）使用<merge/>标签减少布局中的嵌套层次。
3、Android 内存泄漏：
  （1）限制 Service 最好的办法就是使用 IntentService。
  （2）使用 Glide、Picasso、Fresco等框架来加载图片。
  （3）使用优化的数据容器，如 SparseArray,SparseBooleanArray,LongSparseArray。
  （4）Android 中的Enums枚举类型的内存消耗通常是静态常量的两倍，应避免使用。
  （5）如果你的抽象类没有显著的提高效率，应该尽量避免使用。
  （6）为序列化的数据使用nano protobufs。
  （7）避免使用依赖注入框架，如RoboGuice2。
  （8）谨慎使用外部库。
  （9）使用ProGuard来剔除不需要的代码。
  （10）对最终的APK使用zipalign。
  （11）使用多进程。
  （12）使用Memory Monitor 或者 Android Device Monitor 的DDMMS工具下的Heap。
  （13）内存检测利器LeakCanary。
  （14）检测过度绘制情况，开发者选项-->调试GPU过度绘制-->显示过度绘制区域。
4、注释总结：
  （1）类的注释格式如下：
       /*
        * 一句话功能简述
        * 功能详细描述
        * @author 作者
        * @see 相关类/方法
        * @since 产品/模块版本
        * @deprecated(可选) 是否弃用
        */
    （2）函数的注释：
        /*
        * 一句话功能简述
        * 功能详细描述
        * @param 参数1 参数说明
        * @param 参数2 参数说明
        * @return 返回类型说明
        * @exception/throws 异常类型/异常说明
        * @see 相关类/方法/成员
        * @since 起始版本
        * @deprecated(可选) 是否弃用
        */
    （3）异常的注释：
        /*
        *关闭当前的数据流
        *@throws IOException 当关闭流失败时抛出异常
        */
        public void name() throws IOException{
        
        }
5、命名规范总结：
（1）包的命名规范：
        包的命名格式建议为：[域名].[公司英文名].[项目名].[模块名]
        如：com.xiaomi.duokan.network
（2）类与接口的命名：
        首字母大写，其余部分遵循驼峰命名法。
（3）函数命名：
        首字母小写，其余部分遵循驼峰命名法。
        
        常见的几种命名方式：
        => 动作方法采用动词和动宾结构。
        => get + 非布尔属性名()。 
        => is + 布尔属性名()。 
        => set + 属性名()。 
        => 动词()。 
        => 动词 + 宾语()。 
（4）字段名：
        第一个单词的字母使用小写的m,剩下的部分采用驼峰命名法，如果字段为public，那么字段名将去掉m前缀。
（5）常量名：使用全大写的英文，单词之间用下划线分隔开，并且使用static final 修饰，
        如果只是static 而非final，那么变量以s开头，后面以驼峰命名法命名。
（6）指定集合中的元素类型。
（7）显示指出操作符的优先级。
 

