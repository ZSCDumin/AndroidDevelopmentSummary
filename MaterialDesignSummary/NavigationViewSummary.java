1、自定义headerLayout：
    <?xml version="1.0" encoding="utf-8"?>
    <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="180dp"
        android:background="?attr/colorPrimary"
        android:padding="10dp">

        <de.hdodenhof.circleimageview.CircleImageView
            android:id="@+id/icon_image"
            android:layout_width="70dp"
            android:layout_height="70dp"
            android:layout_centerInParent="true"
            android:src="@drawable/nav_icon" />

        <TextView
            android:id="@+id/username"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_alignParentBottom="true"
            android:text="tonygreendev@gmail.com"
            android:textColor="#FFF"
            android:textSize="14sp" />

        <TextView
            android:id="@+id/mail"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_above="@id/username"
            android:text="Tony Green"
            android:textColor="#FFF"
            android:textSize="14sp" />

    </RelativeLayout>
2、自定义Menu:
    <?xml version="1.0" encoding="utf-8"?>
    <menu xmlns:android="http://schemas.android.com/apk/res/android">
        <group android:checkableBehavior="single">
            <item
                android:id="@+id/nav_call"
                android:icon="@drawable/nav_call"
                android:title="Call" />
            <item
                android:id="@+id/nav_friends"
                android:icon="@drawable/nav_friends"
                android:title="Friends" />
            <item
                android:id="@+id/nav_location"
                android:icon="@drawable/nav_location"
                android:title="Location" />
            <item
                android:id="@+id/nav_mail"
                android:icon="@drawable/nav_mail"
                android:title="Mail" />
            <item
                android:id="@+id/nav_task"
                android:icon="@drawable/nav_task"
                android:title="Tasks" />
        </group>
    </menu>
3、自定义NavigationView：
    <?xml version="1.0" encoding="utf-8"?>
    <android.support.v4.widget.DrawerLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/drawer_layout"
        xmlns:app="http://schemas.android.com/apk/res-auto">
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
        <android.support.design.widget.NavigationView
            android:id="@+id/nav_view"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout_gravity="start"
            app:headerLayout="@layout/nav_header"
            app:menu="@menu/nav_menu" />
    </android.support.v4.widget.DrawerLayout>
4、设置默认选中项并添加监听事件：
    navView.setCheckedItem(R.id.nav_call);//设置默认选中项
    navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            mDrawerLayout.closeDrawers();//关闭滑动菜单
            return true;
        }
    });