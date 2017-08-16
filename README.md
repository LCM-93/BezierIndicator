# BezierIndicator
一个神奇的导航栏

[晨鸣的博客--神奇的水滴效果导航栏-BezierIndicator](http://lichenming.com/%E7%A5%9E%E5%A5%87%E7%9A%84%E6%B0%B4%E6%BB%B4%E6%95%88%E6%9E%9C%E5%AF%BC%E8%88%AA%E6%A0%8F-BezierIndicator.html)

## 效果

![一个神奇的导航栏](https://raw.githubusercontent.com/lichenming0516/BezierIndicator/master/img/bezierIndicator_gif.gif)


## 使用

### 根目录build.gradle中配置

```
allprojects {
    repositories {
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}
```

### 项目build.gradle中配置

```
compile 'com.github.lichenming0516:BezierIndicator:1.0.0'
```

### 布局

```
    <com.lcm.bezierbottomIndicator.BezierBottomIndicator
        android:id="@+id/bezierBottomIndicator"
        android:layout_width="match_parent"
        android:layout_height="60dp"
        android:layout_alignParentBottom="true"
        android:paddingBottom="10dp"
        android:paddingTop="10dp"
        app:childPadding="12dp"
        app:leftRightGap="20dp">

        <ImageView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:scaleType="centerInside"
            android:src="@mipmap/iv_camera" />

        <ImageView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:scaleType="fitXY"
            android:src="@mipmap/iv_jiaojuan" />

        <ImageView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:scaleType="centerCrop"
            android:src="@mipmap/iv_mine" />

        <ImageView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:scaleType="fitCenter"
            android:src="@mipmap/iv_message" />

    </com.lcm.bezierbottomIndicator.BezierBottomIndicator>
```


### 代码

```
        //设置每个按钮选中后的颜色
        bezierBottomIndicator.setCircularColors(Color.parseColor("#FCC04D"), Color.parseColor("#00C3E2"), Color.parseColor("#FE626D"), Color.parseColor("#966ACF"));

        //绑定ViewPager
        bezierBottomIndicator.setViewPager(viewPager);
```

