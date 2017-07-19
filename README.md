# RepairPrecisionViewPager
[![](https://jitpack.io/v/HsXuTao/RepairViewPager.svg)](https://jitpack.io/#HsXuTao/RepairViewPager)

在做广告轮播图的时候经常会用到假无限循环（最大值为Integer.MAX_VALUE），然后设置默认值到中间来实现。这时候，系统自带的ViewPager会出现滑动不灵敏的问题。
该库就是修复了系统的ViewPager在大数据量的Itme时候滑动异常的问题。

如何使用：
1：在你根目录里的build.gradle添加如下代码
```java
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```


2：在你的工程里，加入
```java
dependencies {
    compile 'com.github.HsXuTao:RepairViewPager:V1.0.1'
}
```

3：将您的ViewPager用RepairPrecisionViewPager替换：
```java
<ViewPager xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_height="match_parent" android:layout_width="match_parent"
     android:id="@+id/viewpager">
    <!-- scroll view child goes here -->
</ViewPager>
```

替换成
```java
<RepairPrecisionViewPager xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_height="match_parent" android:layout_width="match_parent"
    android:id="@+id/viewpager">
    <!-- scroll view child goes here -->
</RepairPrecisionViewPager>
```

4:代码中,参考正常使用系统ViewPager的方法，只不过将系统的PagerAdapter和xml中的ViewPager替换成项目中的RepairPrecisionPagerAdapter和RepairPrecisionViewPager即可
```java
public class ViewPagerAdapter extends RepairPrecisionPagerAdapter {
    ...
}

viewPager = (RepairPrecisionViewPager) findViewById(R.id.viewpager);
final ViewPagerAdapter adapter = new ViewPagerAdapter(mActivity);
viewPager.setAdapter(adapter);
```


