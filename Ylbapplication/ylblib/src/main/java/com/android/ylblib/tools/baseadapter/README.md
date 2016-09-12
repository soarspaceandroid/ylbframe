## Adapter
A quick adapter library for ListView, GridView, ViewPager, and ExpandableListView on Android. Multiple view types is supported for ListView and GrideView

method

### BaseAdapterHelper
* ```setText()``` calls ```setText(String)``` on any TextView.
* ```setAlpha()``` calls ```setAlpha(float)``` on any View.
* ```setVisible()``` calls ```setVisibility(int)``` on any View.
* ```linkify()``` calls ```Linkify.addLinks(view, ALL)``` on any TextView.
* ```setTypeface()``` calls ```setTypeface(Typeface)``` on any TextView.
* ```setProgress()``` calls ```setProgress(int)``` on any ProgressBar.
* ```setMax()``` calls ```setMax(int)``` on any ProgressBar.
* ```setRating()``` calls ```setRating(int)``` on any RatingBar.
* ```setImageResource()``` calls ```setImageResource(int)``` on any ImageView.
* ```setImageDrawable()``` calls ```setImageDrawable(Drawable)``` on any ImageView.
* ```setImageBitmap()``` calls ```setImageBitmap(Bitmap)``` on any ImageView.
* ```setImageUrl()``` uses to download the image and put it in an ImageView.
* ```setOnClickListener()```
* ```setOnTouchListener()```
* ```setOnLongClickListener()```
* ```setOnCheckedChangeListener()```
* ```setTag()```
* ```setChecked()```
* ```setAdapter()```


  
#### Single layout
* Just override convert()
```java
adapter = new Adapter<ExploreBean>(context, R.layout.item) {
            @Override
            protected void convert(final AdapterHelper helper, ExploreBean exploreBean) {
                final int position = helper.getPosition();
                helper
                        .setText(R.id.tv_explore_name, "__Index: " + String.valueOf(position))
                        .setText(R.id.tv_explore_desc, exploreBean.getDescription())
                        .getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickSnack(position);
                    }
                });
            }
        };      
```

#### Multiple view types layout
* Need to override convert(), getItemViewType(), and getLayoutResId()
```java
adapter = new Adapter<ExploreBean>(context, R.layout.item, R.layout.item0, R.layout.item1) {
            @Override
            protected void convert(final AdapterHelper helper, ExploreBean exploreBean) {
                final int position = helper.getPosition();
                if (position % 3 == 0) {
                    helper.setText(R.id.tv_explore_name, "__Index: " + String.valueOf(position));
                } else if (position % 3 == 1) {
                    helper.setImageResource(R.id.img_explore_icon, exploreBean.getIconResId());
                } else {
                    helper.getItemView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clickSnack(position);
                        }
                    });
                }
            }

            /**
             * Must be overridden, when you have more than one item layout.
             * No need to be overridden, when you only have one item layout.
             */
            @Override
            public int getItemViewType(int position) {
                if (position % 3 == 0) {
                    return 0;
                } else if (position % 3 == 1) {
                    return 1;
                } else {
                    return 2;
                }
            }

            /**
             * Get layoutResId from view type  @see #getItemViewType(int position) return value.
             * Must be overridden, when you have more than one item layout.
             * No need to be overridden, when you only have one item layout.
             */
            @Override
            public int getLayoutResId(int viewType) {
                if (viewType == 0) {
                    return R.layout.item;
                } else if (viewType == 1) {
                    return R.layout.item0;
                } else {
                    return R.layout.item1;
                }
            }
        };
```
For more features, you can extend their Base Adapter

## ExpandableListView
* ExpandableAdapterHelper and ExpandableAdapter for ExpandableListView
* Need to override getChildren(),convertGroupView() and convertChildView()
```java
adapter = new ExpandableAdapter<MenuBean, ExploreBean>(context, R.layout.item_group, R.layout.item_child) {
            @Override
            protected List<ExploreBean> getChildren(int groupPosition) {
                return get(groupPosition).getExploreBeanList();
            }

            @Override
            protected void convertGroupView(final boolean isExpanded, final ExpandableAdapterHelper helper, MenuBean item) {
                helper.setImageResource(R.id.img_explore_icon, item.getIconResId())
                        .setText(R.id.tv_explore_name, item.getDescription())
                        .getItemView().setTag("Example");
            }

            @Override
            protected void convertChildView(boolean isLastChild, final ExpandableAdapterHelper helper, ExploreBean item) {
                helper.getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickSnack(helper.getGroupPosition(), helper.getChildPosition());
                    }
                });
                helper.getItemView().setTag("hello world");
            }
        };
```
For more features, you can extend its Base Adapter

## ViewPager
* ViewPagerAdapter, FragmentPagerAdapterAdvanced, and FragmentStatePagerAdapterAdvanced for ViewPager


```java
adapter = new ViewPagerAdapter<String>(context,R.layout.pager_view) {
            @Override
            protected void convert(PagerAdapterHelper helper, String item) {
                helper.setBackgroundRes(R.id.img_view, R.drawable.exa);
            }
        };


