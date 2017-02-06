1、CardView 的使用方法：
  （1）本质上是 FrameLayout, 只不过多了圆角和阴影等效果。
  （2）布局代码如下： 
       <?xml version="1.0" encoding="utf-8"?>
       <android.support.v7.widget.CardView xmlns:android="http://schemas.android.com/apk/res/android"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            xmlns:app="http://schemas.android.com/apk/res-auto"
            android:id="@+id/cardview"
            app:elevation="5dp"
            app:cardCornerRadius="4dp">

            <TextView
                android:id="@+id/info_text"
                android:text="dumin"
                android:layout_width="match_parent"
                android:layout_height="wrap_content" />
       </android.support.v7.widget.CardView>
2、AppBarLayout 的使用方法：
  （1）本质上是垂直的 LinearLayout,内部做了很多事件的封装。
  （2）布局代码如下：
       <android.support.design.widget.AppBarLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content">

            <android.support.v7.widget.Toolbar
                android:id="@+id/toolbar"
                android:layout_width="match_parent"
                android:layout_height="?attr/actionBarSize"
                android:background="?attr/colorPrimary"
                android:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar"
                app:popupTheme="@style/ThemeOverlay.AppCompat.Light"
                app:layout_scrollFlags="scroll|enterAlways|snap" /> 
       </android.support.design.widget.AppBarLayout>
  （3）代码解析：
       app:layout_scrollFlags="scroll|enterAlways|snap" /> 
        -> scroll： RecycleView 向上滚动时，Toolbar 会跟着向上滚动并实现隐藏。
        -> enterAlways：RecycleView 向下滚动时，Toolbar 会跟着向下滚动并重新显示。
        -> snap：当 Toolbar 还没有完全显示或者隐藏时，会根据滚动距离自动选择隐藏还是显示。
3、SwipeRefreshLayout 的使用方法：
  （1）布局代码如下：
        <android.support.v4.widget.SwipeRefreshLayout
            android:id="@+id/swipe_refresh"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:layout_behavior="@string/appbar_scrolling_view_behavior">

            <android.support.v7.widget.RecyclerView
                android:id="@+id/recycler_view"
                android:layout_width="match_parent"
                android:layout_height="match_parent" />
        </android.support.v4.widget.SwipeRefreshLayout>
  （2）刷新的监听事件代码如下：
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh); 
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);//设置刷新进度条的颜色
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits();
            }
        });
4、CollapsingToolbarLayout 的使用方法：
  （1）布局代码如下：
        <?xml version="1.0" encoding="utf-8"?>
        <android.support.design.widget.CoordinatorLayout
            xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:app="http://schemas.android.com/apk/res-auto"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:fitsSystemWindows="true">

            <android.support.design.widget.AppBarLayout
                android:id="@+id/appBar"
                android:layout_width="match_parent"
                android:layout_height="250dp"
                android:fitsSystemWindows="true">

                <android.support.design.widget.CollapsingToolbarLayout
                    android:id="@+id/collapsing_toolbar"
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    android:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar"
                    android:fitsSystemWindows="true"
                    app:contentScrim="?attr/colorPrimary"   //趋于折叠状态以及折叠之后的背景色
                    app:layout_scrollFlags="scroll|exitUntilCollapsed">

                    <ImageView
                        android:id="@+id/fruit_image_view"
                        android:layout_width="match_parent"
                        android:layout_height="match_parent"
                        android:scaleType="centerCrop"
                        android:fitsSystemWindows="true"
                        app:layout_collapseMode="parallax" />  //折叠模式：parallax表示在折叠过程中会产生一定的位移。

                    <android.support.v7.widget.Toolbar
                        android:id="@+id/toolbar"
                        android:layout_width="match_parent"
                        android:layout_height="?attr/actionBarSize"
                        app:layout_collapseMode="pin" />  //折叠模式：pin表示在折叠过程中位置不发生改变。
                </android.support.design.widget.CollapsingToolbarLayout>
            </android.support.design.widget.AppBarLayout>

            <android.support.v4.widget.NestedScrollView
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                app:layout_behavior="@string/appbar_scrolling_view_behavior">

                <LinearLayout
                    android:orientation="vertical"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content">

                    <android.support.v7.widget.CardView
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:layout_marginBottom="15dp"
                        android:layout_marginLeft="15dp"
                        android:layout_marginRight="15dp"
                        android:layout_marginTop="35dp"
                        app:cardCornerRadius="4dp">

                        <TextView
                            android:id="@+id/fruit_content_text"
                            android:layout_width="match_parent"
                            android:layout_height="wrap_content"
                            android:layout_margin="10dp" />
                    </android.support.v7.widget.CardView>
                </LinearLayout>
            </android.support.v4.widget.NestedScrollView>

            <android.support.design.widget.FloatingActionButton
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_margin="16dp"
                android:src="@drawable/ic_comment"
                app:layout_anchor="@id/appBar"
                app:layout_anchorGravity="bottom|end" />
        </android.support.design.widget.CoordinatorLayout>
  （2）刷新的监听事件代码如下：
5、NestedScrollView 的使用方法：
  （1）布局代码如下：
        <android.support.v4.widget.NestedScrollView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:layout_behavior="@string/appbar_scrolling_view_behavior">

            <LinearLayout
                android:orientation="vertical"
                android:layout_width="match_parent"
                android:layout_height="wrap_content">

                <android.support.v7.widget.CardView
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_marginBottom="15dp"
                    android:layout_marginLeft="15dp"
                    android:layout_marginRight="15dp"
                    android:layout_marginTop="35dp"
                    app:cardCornerRadius="4dp">

                    <TextView
                        android:id="@+id/fruit_content_text"
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:layout_margin="10dp" />
                </android.support.v7.widget.CardView>
            </LinearLayout>
        </android.support.v4.widget.NestedScrollView>
  （2）刷新的监听事件代码如下：