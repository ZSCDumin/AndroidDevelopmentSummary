1、自定义的带图片的TextView类的代码如下：
    package com.edu.zscdm.widgetdemo;

    import android.content.Context;
    import android.graphics.Bitmap;
    import android.graphics.BitmapFactory;
    import android.graphics.Canvas;
    import android.graphics.Rect;
    import android.support.v7.widget.AppCompatTextView;
    import android.util.AttributeSet;
    import android.util.Log;

    /**
    * Created by ZSCDM on 2017/2/8.
    * 作者邮箱：2712220318@qq.com
    */

    public class IconTextView extends AppCompatTextView {
        //  命名空间的值
        private final String namespace = "http://net.blogjava.mobile";
        //  图像资源ID
        private int resourceId = 0;
        private Bitmap bitmap;

        public IconTextView(Context context, AttributeSet attrs) {
            super(context, attrs);
            resourceId = attrs.getAttributeResourceValue(namespace, "iconSrc", 0);//通过mobile:iconSrc属性指定图片的ID
            if (resourceId > 0)
                bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            if (bitmap != null) {

                //  从原图上截取图像的区域，在本例中为整个图像
                Rect src = new Rect();
                //  将截取的图像复制到bitmap上的目标区域，在本例中与复制区域相同
                Rect target = new Rect();
                src.left = 0;
                src.top = 0;
                src.right = bitmap.getWidth();
                src.bottom = bitmap.getHeight();
                int textHeight = (int) getTextSize();
                target.left = 0;
                //  计算图像复制到目录区域的纵坐标。由于TextView中文本内容并不是从最顶端开始绘制的，因此，需要重新计算绘制图像的纵坐标
                target.top = (int) ((getMeasuredHeight() - getTextSize()) / 2) + 1;//getMeasuredHeight()表示实际View的大小，与屏幕无关。
                // getMeasuredHeight ： 表示的是view的实际大小。
                // getHeight： 表示的是view在屏幕上显示的大小。

                target.bottom = target.top + textHeight;
                //  为了保证图像不变形，需要根据图像高度重新计算图像的宽度
                target.right = (int) (textHeight * (bitmap.getWidth() / (float) bitmap
                        .getHeight()));
                //  开始绘制图像
                canvas.drawBitmap(bitmap, src, target, getPaint());
                //  将TextView中的文本向右移动一定的距离（在本例中移动了图像宽度加5个象素点的位置）
                canvas.translate(target.right + 5, 0);//文字与图片的间距为5个像素
            }
            super.onDraw(canvas);
        }
    }


2、在布局中使用的代码如下：
    <?xml version="1.0" encoding="utf-8"?>
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:mobile="http://net.blogjava.mobile"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">

        <com.edu.zscdm.widgetdemo.IconTextView
            android:layout_width="fill_parent"
            android:layout_height="wrap_content"
            android:textSize="24dp"
            android:text="第二个笑脸"
            mobile:iconSrc="@drawable/small" />
    </LinearLayout>

3、自定义的带文本标签的EditText类的代码如下：
    package com.edu.zscdm.widgetdemo;

    import android.content.Context;
    import android.util.AttributeSet;
    import android.view.LayoutInflater;
    import android.widget.LinearLayout;
    import android.widget.TextView;

    public class LabelEditText extends LinearLayout {
        private TextView textView;
        private String labelText;
        private int labelFontSize;
        private String labelPosition;

        public LabelEditText(Context context, AttributeSet attrs) {
            super(context, attrs);
            //  读取labelText属性的资源ID
            int resourceId = attrs.getAttributeResourceValue(null, "labelText", 0);
            //  未获得资源ID，继续读取属性值
            if (resourceId == 0)
                labelText = attrs.getAttributeValue(null, "labelText");
                //  从资源文件中获得labelText属性的值
            else
                labelText = getResources().getString(resourceId);
            //  如果按两种方式都未获得labelTex属性的值，表示未设置该属性，抛出异常
            if (labelText == null) {
                throw new RuntimeException("必须设置labelText属性.");
            }
            //  获得labelFontSize属性的资源ID
            resourceId = attrs.getAttributeResourceValue(null, "labelFontSize", 0);
            //  继续读取labelFontSize属性的值，如果未设置该属性，将属性值设为14
            if (resourceId == 0)
                labelFontSize = attrs.getAttributeIntValue(null, "labelFontSize",
                        14);
                //  从资源文件中获得labelFontSize属性的值
            else
                labelFontSize = getResources().getInteger(resourceId);
            //  获得labelPosition属性的资源ID
            resourceId = attrs.getAttributeResourceValue(null, "labelPosition", 0);
            //  继续读取labelPosition属性的值
            if (resourceId == 0)
                labelPosition = attrs.getAttributeValue(null, "labelPosition");
                //  从资源文件中获得labelPosition属性的值
            else
                labelPosition = getResources().getString(resourceId);
            //  如果未设置labelPosition属性值，将该属性值设为left
            if (labelPosition == null)
                labelPosition = "left";

            String infService = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li;
            //  获得LAYOUT_INFLATER_SERVICE服务
            li = (LayoutInflater) context.getSystemService(infService);
            LinearLayout linearLayout = null;
            //  根据labelPosition属性的值装载不同的布局文件
            if ("left".equals(labelPosition))
                linearLayout = (LinearLayout) li.inflate(R.layout.labeledittext_horizontal, this);
            else if ("top".equals(labelPosition))
                linearLayout = (LinearLayout) li.inflate(R.layout.labeledittext_vertical, this);
            else
                throw new RuntimeException("labelPosition属性的值只能是left或top.");

            //  下面的代码从相应的布局文件中获得了TextView对象，并根据LabelTextView的属性值设置TextView的属性
            textView = (TextView) findViewById(R.id.textview);
            textView.setTextSize((float) labelFontSize);
            textView.setTextSize(labelFontSize);
            textView.setText(labelText);
        }
    }
4、在布局中使用的代码如下：
    <?xml version="1.0" encoding="utf-8"?>
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="fill_parent"
        android:layout_height="fill_parent"
        android:orientation="horizontal">  //水平方向上的

        <TextView
            android:id="@+id/textview"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content" />

        <EditText
            android:id="@+id/edittext"
            android:layout_width="fill_parent"
            android:layout_height="wrap_content" />
    </LinearLayout>