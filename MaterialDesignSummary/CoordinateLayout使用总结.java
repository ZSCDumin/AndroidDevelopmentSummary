1、布局代码如下：
    <?xml version="1.0" encoding="utf-8"?>
    <android.support.v4.widget.DrawerLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:id="@+id/drawer_layout">
        <android.support.design.widget.CoordinatorLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent">
            <android.support.v7.widget.Toolbar
                android:id="@+id/toolbar"
                android:layout_width="match_parent"
                android:layout_height="?attr/actionBarSize"
                android:background="?attr/colorPrimary"
                android:popupTheme="@style/ThemeOverlay.AppCompat.Light"
                app:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar" />

            <android.support.design.widget.FloatingActionButton
                android:id="@+id/fab"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_gravity="bottom|end"
                android:layout_margin="16dp"
                android:src="@drawable/ic_done"
                app:elevation="8dp" />
        </android.support.design.widget.CoordinatorLayout>

    </android.support.v4.widget.DrawerLayout>
2、CoordinatorLayout 加强版的 FrameLayout,可以监听所有子控件的各种事件，自动帮我们做出最为合理的响应。