1、布局文件中添加Toolbar控件：
    <android.support.v7.widget.Toolbar
        android:id="@+id/toolbar"
        android:layout_width="match_parent"
        android:layout_height="?attr/actionBarSize"
        android:background="?attr/colorPrimary"
        android:popupTheme="@style/ThemeOverlay.AppCompat.Light"
        app:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar">
    </android.support.v7.widget.Toolbar>

2、自定义toolbar.xml文件：
    <?xml version="1.0" encoding="utf-8"?>
    <menu xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto">
        <item
            android:id="@+id/backup"
            android:icon="@drawable/ic_backup"
            android:title="Backup"
            app:showAsAction="always"/>
        <item
            android:id="@+id/delete"
            android:icon="@drawable/ic_delete"
            android:title="Delete"
            app:showAsAction="ifRoom"/>
        <item
            android:id="@+id/settings"
            android:icon="@drawable/ic_settings"
            android:title="Settings"
            app:showAsAction="never"/>
    </menu>
3、将Toolbar的实例传入：
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
4、重写onCreateOptions()方法：
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }
5、菜单栏图标按钮点击事件：
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.backup:
                Toast.makeText(this, "You clicked Backup", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this, "You clicked Delete", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(this, "You clicked Settings", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }