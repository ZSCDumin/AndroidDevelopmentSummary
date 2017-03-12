1、采用ViewHolder优化ListView
   (1)在自定义的Adapter中自定义内部类ViewHolder,并将布局的控价作为成员变量。
   (2)核心代码：
      判断是否缓存，若没有缓存，则通过ConvertView.setTag(holder)方法进行缓存，否则通过Tag找到缓存布局。
2、设置分隔线：通过divider和dividerHight两个属性实现。
   代码如下：
   android:divider="@android:color/draker_gray"
   android:dividerHight="10dp"
   /****若divider设为null，则表示分割线为透明的。********/

3、隐藏滚动条：android:scrollBar="none"
4、取消Item点击效果：android:listSelector="@android:color/transparent"  或  android:listSelector="#00000000"
5、设置ListView需要显示第几项：listView.setSelection(N);
6、实现平滑移动：
   mListView.smoothScrollBy(distance,duration);
   mListView.smoothScrollByOffset(offset);
   mListView.smoothScrollToPosition(index);
7、动态修改ListView:
   mData.add("New");
   mAdapter.notifyDataSetChanged();

8、处理空的ListView:
   listView.setEmptyView(findViewById(R.id.empty_view));
   //表示当listView为空数据时，显示一张默认的图片，用力提示用户，有数据时，则不会显示。
9、ListView 的滑动监听：
   （1）onTouchListener：
         onTouch回调方法：
         ACTION_DOMN,ACTION_UP,ACTION_MOVE 三个事件。
   （2）onScrollListener：onScrollStateChanged()和onScroll()两个回调方法。
        onScrollStateChanged()参数有三种形式：
        --> onScrollListener.SCROLL_STATE_IDLE：滚动停止时
        --> onScrollListener.SCROLL_STATE_TOUCH_SCROLL：正在滚动时
        --> onScrollListener.SCROLL_STATE_FLING：用手指用力滑动时，在离开时由于惯性继续滑动的状态。
10、onScroll方法：
   （1）参数如下：
        firstVisibleItem：当前能看到的第一个Item的ID
        visibleItemCount：当前能看见的Item总数
        totalItemCount：整个ListView的Item总数
   （2）即使显示一小半的Item也包括在内了。
   （3）判断滚动是否到最后一行，就可以使用如下的代码了：
        if(firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0){
             //滚动到最后一行了
        }

   （4）判断滚动方向：
        if(firstVisibleItem > lastVisibleItem){
              //上滑
         }
         else if(firstVisibleItem < lastVisibleItem){
              //下滑
         }
         lastVisibleItem = firstVisibleItem;
11、ScrollTo 与 ScrollBy：
     SCrollTo(int x,int y) //表示移动到一个具体的坐标点
     SCrollBy(int dx,int dy) //表示移动的增量为 dx,dy

     注意：移动的只是View的content，所以应该View所在的ViewGroup中来使用ScrollBy()方法，移动它的子View,代码如下：
          ((view).getParent).scrollBy(offsetX,offsetY);
12、SCroller 实现平滑滚动原理：
    （1）原理：
         将滑动距离分成多分，每份采用瞬间移动，利用人眼视觉暂留特性。
    （2）大致步骤：
         a. 创建Scroll对象。
         b. 重写computeScroll()方法，实现模拟滑动（记得调用invalidate()方法实现重绘方法，不断调用computeScroll()方法）。
         c. 开启模拟过程。
13、具有弹性的ListView：
    （1）重写overScrollBy()方法。 
    （2）将maxOverScrollY改为设置的值 -mMaxOverDistance
         private void initeView()  {
               DisPlayMetrics metrics = mContext.getResource().getDisplayMetrics();
               float density = metrics.density;
               mMaxOverDistance = (int)(density * mMaxOverDistance);
         }
14、向下滚动时，标题栏和悬浮按钮消失效果实现：
    （1）添加一个HeadView,避免第一个Item被ToolBar遮挡。
    （2）定义一个变量用来获取系统认为的最低滑动距离。
    （3）监听滑动事件了。
15、聊天界面：
     getItemViewType(int position)  //返回第position个Item是何种类型
     getViewTypeCount()  //返回不同布局的总数
16、侧边滑动效果：DrawerLayout 和 SlidingPanelLayout 布局背后的实现类：ViewDragHelper
    （1）初始化ViewDragHelper,viewDragHelper.create(this,callBack);
    （2）重写事件拦截方法，onInterceptTouchEvent,onTouchEvent。
    （3）处理computeScroll()方法。
    （4）处理回调callBack。
   