package com.cs.autolayout.csautomaticlayout.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.cs.autolayout.csautomaticlayout.R;
import com.cs.autolayout.csautomaticlayout.utils.Utils;

/**
 *  CSLayout实现的功能：像目前主流软件淘宝，京东，搜索功能底下的搜索记录布局样式
 *  当一行文字内容占满宽度的时候自动向下换行布局
 */

public class CSLayout extends ViewGroup {

    private int textColor;
    private int textBackgroundColor;
    private int textBackgroundResource;
    private float textViewSize;
    private int textViewPadding;
    private int textViewPaddingLeft;
    private int textViewPaddingRight;
    private int textViewPaddingTop;
    private int textViewPaddingBottom;
    private float childspacing;

    public int contentSize = 0;
    public int lineNum = 1;


    private class NoImplementationException extends Exception {
        NoImplementationException(String s) {
            super(s);
        }
    }

    public CSLayout(Context context) throws NoImplementationException {
        super(context);
        throw new NoImplementationException("未执行attributeSet,联系开发者QQ:1143241513");
    }

    public CSLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context, attrs);
    }

    public CSLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context, attrs);
    }

    public void initData(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CSLayout);
        this.textColor = ta.getColor(R.styleable.CSLayout_childTextColor, Color.BLACK);
        this.textViewSize = ta.getFloat(R.styleable.CSLayout_childTextSize, 15);
        this.textBackgroundColor = ta.getColor(R.styleable.CSLayout_childTextBackgroundColor, 0);
        this.textBackgroundResource = ta.getResourceId(R.styleable.CSLayout_childTextBackgroundResource,0);
        this.textViewPadding = ta.getInt(R.styleable.CSLayout_childPadding,0);
        this.textViewPaddingLeft = ta.getInt(R.styleable.CSLayout_childLeftPadding,0);
        this.textViewPaddingRight = ta.getInt(R.styleable.CSLayout_childRightPadding,0);
        this.textViewPaddingTop = ta.getInt(R.styleable.CSLayout_childTopPadding,0);
        this.textViewPaddingBottom = ta.getInt(R.styleable.CSLayout_childBottomPadding,0);
        this.childspacing = ta.getFloat(R.styleable.CSLayout_childSpacing,0);
        ta.recycle();
    }

    /**
     * 传入字符串，自动添加子元素
     * @param infos
     */
    public void loadView(String[] infos) {
        if (infos != null && infos.length > 0 && !isXceedParentHeight()) {
            int index = 0;
            for (String info : infos) {
                if (!TextUtils.isEmpty(info)) {
                    TextView textView = new TextView(getContext());
                    textView.setTextColor(this.textColor);
                    textView.setTextSize(this.textViewSize);
                    textView.setText(info);
                    if(this.textBackgroundResource != 0){
                        textView.setBackgroundResource(this.textBackgroundResource);
                    }else if(this.textBackgroundColor != 0){
                        textView.setBackgroundColor(this.textBackgroundColor);
                    }else{
                        textView.setBackgroundResource(R.drawable.select_background);
                    }

                    if(this.textViewPadding != 0){
                        int paddingValue = Utils.dip2px(getContext(),this.textViewPadding);
                        textView.setPadding(paddingValue,paddingValue,paddingValue,paddingValue);
                    }else {
                        int paddingLeftValue = Utils.dip2px(getContext(),this.textViewPaddingLeft);
                        int paddingRightValue = Utils.dip2px(getContext(),this.textViewPaddingRight);
                        int paddingTopValue = Utils.dip2px(getContext(),this.textViewPaddingTop);
                        int paddingBottomValue = Utils.dip2px(getContext(),this.textViewPaddingBottom);
                        textView.setPadding(paddingLeftValue,paddingTopValue,paddingRightValue,paddingBottomValue);
                    }
                    addView(textView, index);
                    index++;
                }
            }
            if(itemListener != null){
                addListener();
            }
        }else if (infos != null && infos.length > 0){
            for (int i = 0 ; i < infos.length ; i++){
                TextView childView = (TextView) getChildAt(i);
                if(childView != null){
                    childView.setText(infos[i]);
                }
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        lineNum = 1;
        int lineWidth = getPaddingLeft();
        int lineHeight = getPaddingTop();
        int margin = Utils.dip2px(getContext(),(childspacing > 0 ? childspacing : 5));
        int childCount = getChildCount();
        //实现布局代码逻辑
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if(childView != null){
                if (lineWidth + childView.getMeasuredWidth() < getWidth() - getPaddingRight()) {
                    childView.layout(lineWidth, lineHeight, childView.getMeasuredWidth() + lineWidth, childView.getMeasuredHeight() + lineHeight);
                    lineWidth = lineWidth + margin + childView.getMeasuredWidth();
                } else if(!isXceedParentHeight()){
                    lineNum ++;
                    lineWidth = getPaddingLeft();
                    lineHeight += margin + childView.getMeasuredHeight();
                    childView.layout(lineWidth, lineHeight, childView.getMeasuredWidth() + lineWidth, childView.getMeasuredHeight() + lineHeight);
                    lineWidth = lineWidth + margin + childView.getMeasuredWidth();
                }else{
                    int count = getChildCount() - i;
                    removeViews(i,count);
                    break;
                }
            }

        }
    }

    public boolean isXceedParentHeight(){
        View childView = getChildAt(0);
        if(childView != null){
            contentSize = (lineNum * childView.getMeasuredHeight()) + (lineNum * Utils.dip2px(getContext(),(childspacing > 0 ? childspacing : 5)));
            if (contentSize + childView.getMeasuredHeight() > (getHeight() - getPaddingTop() - getPaddingBottom())){
                return true;
            }
            return false;
        }
        return false;
    }


    public interface ItemListener{
        void registerListener(int position,TextView textView);
    }

    public ItemListener itemListener;

    public void addItemListener(ItemListener itemListener){
        if(itemListener != null){
            this.itemListener = itemListener;
            addListener();
        }else{
            throw new NullPointerException("ItemListener Must not be empty");
        }
    }

    private void addListener(){
        if(getChildCount() > 0 && itemListener != null){
            for (int i = 0 ; i < getChildCount(); i++){
                View childView = getChildAt(i);
                if(childView != null){
                    final int item = i;
                    childView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            itemListener.registerListener(item, (TextView) v);
                        }
                    });
                }
            }
        }
    }


}
