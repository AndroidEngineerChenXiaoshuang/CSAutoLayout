# 自动化布局

自动化布局，实现了像目前主流app淘宝，京东等历史搜索记录布局功能，当一行上面不能容下多一个的单元格时候就会自动向下布局，
当内容超过父控件的高度时候，自动replace从第一个单元格开始，到多出的单元格元素大小的位置。

下面看效果图
![image](https://github.com/AndroidEngineerChenXiaoshuang/CSAutoLayout/blob/master/demo.gif) 

如何将自己的项目集成:[Android Sutido如何添加项目为依赖,详细图文](http://www.jianshu.com/p/18f8e2e124d1)，这篇文章有详细的配置方法，
目前项目没有采用gradle集成方式，请大家谅解，集成完毕后就可以轻松调用相关的配置，首先在你需要使用到CSLayout的位置中添加布局代码

```
<com.cs.autolayout.csautomaticlayout.layout.CSLayout
  android:layout_width="match_parent"
  android:layout_height="match_parent"
  android:id="@+id/csLayout"
  android:animateLayoutChanges="true"
  android:padding="10dp"/>
```

然后在相应的布局下获取到csLayout实例，下面就是测试demo下相应activity下的代码:
```
public CSLayout csLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        csLayout = (CSLayout) findViewById(R.id.csLayout);
        csLayout.addItemListener(new CSLayout.ItemListener() {
            @Override
            public void registerListener(int position, TextView textView) {
                Toast.makeText(MainActivity.this, "position:" + position + "->" + textView.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void click(View view) {
        String[] strings = {"第一行代码", "swift深入浅出", "Java面向对象编程", "iPhone X", "一加5t手机壳", "面包机", "装逼神器"};
        csLayout.loadView(strings);
    }

```
首先获取到了csLayout实例，然后通过addItemListener为单个元素添加点击事件，在点击事件回调中参数一是相对应的position，参数二是当前被点击的
textView对象。
我们在点击事件中通过csLayout的loadView方法加载元素，loadView需要传入一个字符串对象，通过字符串对象，csLayout将自动去加载textView，然后
在addView到csLayout中，这样就完成了从集成到调用的全部操作。

最后csLayout也可以配置它的布局属性，下面就列出所有的布局属性
```
 <com.cs.autolayout.csautomaticlayout.layout.CSLayout
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:background="#ff5858"
        android:id="@+id/csLayout"
        android:animateLayoutChanges="true"
        app:childTextColor="#000"
        app:childTextSize="15"
        app:childPadding="10"
        app:childLeftPadding="10"
        app:childRightPadding="10"
        app:childTopPadding="10"
        app:childBottomPadding="10"
        app:childSpacing="10"
        app:childTextBackgroundColor="#999"
        app:childTextBackgroundResource="@drawable/select_background"
        android:padding="10dp"/>
```
这些参数是不需要加单位，文字大小的单位都是sp，边距都是以dp为单位。
