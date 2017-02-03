1、自定义布局文件DrawerLayout.xml：
    <?xml version="1.0" encoding="utf-8"?>
    <android.support.v4.widget.DrawerLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:id="@+id/drawer_layout"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <FrameLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent">

            <android.support.v7.widget.Toolbar
                android:id="@+id/toolbar"
                android:layout_width="match_parent"
                android:layout_height="?attr/actionBarSize"
                android:background="?attr/colorPrimary"
                android:popupTheme="@style/ThemeOverlay.AppCompat.Light"
                app:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar">

            </android.support.v7.widget.Toolbar>
        </FrameLayout>

        <TextView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:background="#FFF"
            android:layout_gravity="start"  //根据系统语言确定滑动菜单的位置，汉语、英语滑动菜单在左边。
            android:text="This is Menu"
            android:textSize="30sp" />
    </android.support.v4.widget.DrawerLayout>
2、设置导航按钮图标：
    if (actionBar != null) {
        actionBar.setDisplayHomeAsUpEnabled(true);//让导航按钮显示出来
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);//设置导航按钮图标
    }
3、显示滑动菜单：
   mDrawerLayout.openDrawer(GravityCompat.START);
