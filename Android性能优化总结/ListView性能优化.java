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
   （1）
   （2）
   （3）
10、