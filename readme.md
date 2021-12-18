### 可以折叠Gridview
![ezgif](https://raw.githubusercontent.com/hankinghu/pic_libary/master/pic/ezgif.com-gif-maker.gif)

### 实现原理

#### 1、折叠实现
重写gridview的setAdapter方法

```java
    @Override
public void setAdapter(ListAdapter adapter) {
        if (foldNm > 0) {
        //进行折叠
        adapter = new FoldViewGridAdapter(adapter, foldNm);
        }
        super.setAdapter(adapter);
        }
```
FoldViewGridAdapter是一个包装类，用来实现对当前的adapter包装，主要是重写getCount方法如下:

```java
private class FoldViewGridAdapter implements WrapperListAdapter {
        ...
    @Override
    public int getCount() {
        //fn如果小于1，不进行折叠,根据是否折叠来判断,默认是折叠状态
        if (fn < 0) {
            return adapter.getCount();
        } else {
            //如果fn大于0，进行折叠处理
            if (expend) {
                return adapter.getCount();
            } else {
                return Math.min(adapter.getCount(), fn * getNumColumns());
            }
        }
    }
    @Override
    public ListAdapter getWrappedAdapter() {
        return this;
    }
        ...
}
```
先判断fn,也就是折叠后显示的行数，fn初始值为-1，也就是不折叠，当设置了fn值大于0，那么就需要进行折叠判断，如果需要折叠，则返回
```java
 return Math.min(adapter.getCount(), fn * getNumColumns());
```
两者中的最小值。fn * getNumColumns()是折叠时显示的行数和显示的列数的积，也就是显示的个数。

#### 2、如何添加ExpendControlView

同样在重写setAdapter方法，对adapter进行再次封装。

```java
HeaderViewGridAdapter headerViewGridAdapter = new HeaderViewGridAdapter(mHeaderViewInfos, mFooterViewInfos, adapter);

```
添加ExpendControlView的逻辑主要在HeaderViewGridAdapter中，HeaderViewGrideAdapter要实现一下几点：
#### 1、怎么使添加的ExpendControlView，独占一行。


```java
    public void addExpendControlView(View v, Object data, boolean isSelectable, boolean isExpendControlView) {
 
        ViewGroup.LayoutParams lyp = v.getLayoutParams();

        FixedViewInfo info = new FixedViewInfo();
        FrameLayout fl = new FullWidthFixedViewLayout(getContext());

        if (lyp != null) {
            v.setLayoutParams(new FrameLayout.LayoutParams(lyp.width, lyp.height));
            fl.setLayoutParams(new LayoutParams(lyp.width, lyp.height));
        }
        fl.addView(v);
        info.view = v;
        info.viewContainer = fl;
        info.data = data;
        info.isSelectable = isSelectable;
        //控制是否可以折叠
        info.isExpendControlView = isExpendControlView;
        mFooterViewInfos.add(info);

        if (mAdapter != null) {
            ((HeaderViewGridAdapter) mAdapter).notifyDataSetChanged();
        }
    }
```
在addExpendControlView方法中，会先创建一个fl的viewGroup,然后再把controlview添加到fl中，FullWidthFixedViewLayout的代码如下：

```java
    private class FullWidthFixedViewLayout extends FrameLayout {

        public FullWidthFixedViewLayout(Context context) {
            super(context);
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            int realLeft = GridViewEnableFooter.this.getPaddingLeft() + getPaddingLeft();
            // Try to make where it should be, from left, full width
            if (realLeft != left) {
                offsetLeftAndRight(realLeft - left);
            }
            super.onLayout(changed, left, top, right, bottom);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int targetWidth = GridViewEnableFooter.this.getMeasuredWidth()
                    - GridViewEnableFooter.this.getPaddingLeft()
                    - GridViewEnableFooter.this.getPaddingRight();
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(targetWidth,
                    MeasureSpec.getMode(widthMeasureSpec));
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
```
在onLayout和onMeasure的时候设置宽度和gridview的宽度一致，来设置fl的宽度和gridview的宽度一致，这样可以独占一行。

#### 2、添加ExpendControlView后count的计算。

添加ExpendControlView需要重新计算adapter的getcount返回值，getCount方法如下：

```java
        public int getCount() {
                return getExpendControlViewsCount() * mNumColumns + getAdapterAndPlaceHolderCount();
        }
```
因为一个ExpendControlView会占一行，所以需要乘以列数，getAdapterAndPlaceHolderCount方法实现如下：

```java
        private int getAdapterAndPlaceHolderCount() {
            return (int) (Math.ceil(1f * mAdapter.getCount() / mNumColumns) * mNumColumns);
        }
```

getAdapterAndPlaceHolderCount用来补充数量，使得count个数为整数列的倍数。如果不是整数列，那么添加的ExpendControlView不会占用单独的行，显示有问题。

#### 3、重写getView方法。
添加的ExpendControlView需要在getView中进行返回，所以还需要重写getView方法。

```java
public View getView(int position, View convertView, ViewGroup parent) {

            // Adapter
            final int adjPosition = position - numHeadersAndPlaceholders;
            int adapterCount = 0;
            if (mAdapter != null) {
                adapterCount = getAdapterAndPlaceHolderCount();
                if (adjPosition < adapterCount) {
                    if (adjPosition < mAdapter.getCount()) {
                        return mAdapter.getView(adjPosition, convertView, parent);
                    } else {
                        if (convertView == null) {
                            convertView = new View(parent.getContext());
                        }
                        convertView.setVisibility(View.INVISIBLE);
                        convertView.setMinimumHeight(mRowHeight);
                        return convertView;
                    }
                }
            }
            // ExpendControlView
            final int footerPosition = adjPosition - adapterCount;
            if (footerPosition < getCount()) {
                View footViewContainer = mFooterViewInfos
                        .get(footerPosition / mNumColumns).viewContainer;
                if (position % mNumColumns == 0) {
                    return footViewContainer;
                } else {
                    if (convertView == null) {
                        convertView = new View(parent.getContext());
                    }
                    // We need to do this because GridView uses the height of the last item
                    // in a row to determine the height for the entire row.
                    convertView.setVisibility(View.INVISIBLE);
                    convertView.setMinimumHeight(footViewContainer.getHeight());
                    return convertView;
                }
            }
            throw new ArrayIndexOutOfBoundsException(position);
        }
```



### 方法
1、添加底部控制view

```java
public void addExpendControlView(View view)
```

2、设置折叠时展示的行数

```java
setFoldNum(int foldNm)
```
用于设置折叠时显示的行数

### 使用方法

```java
//折叠控制view
gridView.addFooterView(LoadMoreView(this))

//设置折叠行数
gridView.setFoldNum(2)

gridView.adapter = object : BaseAdapter() {...}
```

上面的loadMoreView就是底部的折叠控制view，添加自己的控制view时，继承下面接口
```java
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

```java
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
### 源码地址
https://github.com/hankinghu/ExpendGridView
### 参考
https://github.com/liaohuqiu/android-GridViewWithHeaderAndFooter