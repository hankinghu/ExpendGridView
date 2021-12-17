### 可以折叠Gridview
![ezgif](https://raw.githubusercontent.com/hankinghu/pic_libary/master/pic/ezgif.com-gif-maker.gif)

### 方法

```java
public void addExpendControlView(View view)
```
用于添加底部控制的view


```java
setFoldNum(int foldNm)
```
用于设置折叠时显示的行数

### 使用方法

```kotlin
//折叠控制view
gridView.addFooterView(LoadMoreView(this))

//设置折叠行数
gridView.setFoldNum(2)

gridView.adapter = object : BaseAdapter() {...}
```

上面的loadMoreView就是底部的折叠控制view，添加自己的控制view时，继承下面接口
```kotlin
interface IExpendControl {
    /**
     * 展开
     */
    fun expend()

    /**
     * 折叠
     */
    fun fold()
}
```
重写expend和fold方法，也就是展开和折叠，完成控制。

```kotlin
class LoadMoreView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs), IExpendControl {
    private var viewBinding: LoadMoreViewBinding =
        LoadMoreViewBinding.inflate(LayoutInflater.from(context), this)

    override fun expend() {
        //展开时操作
        viewBinding.loadMore.text = "收起"
        viewBinding.arrow.setImageResource(R.drawable.up_arrow)
    }

    override fun fold() {
        //折叠时操作
        viewBinding.loadMore.text = "展开全部"
        viewBinding.arrow.setImageResource(R.drawable.down_arrow)

    }
}
```
### //todo
1、展开和收起添加动画