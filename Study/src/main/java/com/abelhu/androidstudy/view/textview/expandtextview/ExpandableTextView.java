package com.abelhu.androidstudy.view.textview.expandtextview;

import static androidx.core.util.PatternsCompat.AUTOLINK_WEB_URL;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.DynamicLayout;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.method.Touch;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.abelhu.androidstudy.R;
import com.abelhu.androidstudy.utils.L;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 支持展开收起的自定义TextView。
 * <p>
 * ep_max_line, 收起状态下最大的展示行数, 默认: 4
 * ep_need_expand, 是否展示"展开"按钮, 默认: true
 * ep_need_contract, 是否展示"收起"按钮, 默认: false
 * ep_need_always_showright, 控制展开收起按钮是否固定在右下角，否则会跟随在最后一行内容尾部, 默认: false
 * ep_need_topic, 是否支持把 "#XXX" 转换为话题
 * ep_need_circle, 是否支持把 "%XXX" 转换为圈子
 */
@SuppressWarnings("unused")
public class ExpandableTextView extends AppCompatTextView {
    private static final String TAG = "ExpandableTextView";
    private static final int DEF_MAX_LINE = 4;
    private static final int LINK_DRAWABLE_WH = 30;
    private static final int CIRCLE_DRAWABLE_WH = 30;
    private static final String TEXT_CONTRACT = "收起";
    private static final String TEXT_EXPEND = "展开";
    private static final String SPACE = " ";
    private static final String TEXT_TARGET = "网页链接";
    private static final String IMAGE_TARGET = "图";
    private static final String TARGET = IMAGE_TARGET + TEXT_TARGET;
    private static final String REGEXP_TOPIC = "#([^#])* ";
    private static final String REGEXP_CIRCLE = "%([^%])* ";
    private static final String REGEXP_TRANSPARENT = "--(.)*--";
    //匹配自定义链接的正则表达式
    private static final String SELF_REGEX = "\\[([^\\[]*)\\]\\(([^\\(]*)\\)";

    private TextPaint mPaint;

    private boolean mLinkHit;

    private Context mContext;

    /**
     * 计算的layout
     */
    private DynamicLayout mDynamicLayout;

    //hide状态下，展示多少行开始省略
    private int mLimitLines;

    // expand状态下，展示多少行
    private int mExpandLimitLines;

    private int mCurrentLines;

    private int mWidth;
    private Runnable mPendingAction;

    private Drawable mLinkDrawable = null;

    /**
     * 链接和@用户的事件点击
     */
    private OnLinkClickListener mLinkClickListener;

    /**
     * 展开或者收回事件监听
     */
    private OnExpandOrContractClickListener mExpandOrContractClickListener;

    /**
     * 是否需要收起
     */
    private boolean mNeedContract = true;

    private FormatData mFormatData;

    /**
     * 是否需要展开功能
     */
    private boolean mNeedExpend = true;

    /**
     * 是否需要转换url成网页链接四个字
     */
    private boolean mNeedConvertUrl = false;

    /**
     * 是否需要#话题的功能
     */
    private boolean mNeedTopic = false;

    /**
     * 是否需要%圈子的功能
     */
    private boolean mNeedCircle = false;

    /**
     * 圈子的drawable
     */
    private Drawable mCircleDrawable = null;

    /**
     * 是否需要对链接进行处理
     */
    private boolean mNeedLink = false;

    /**
     * 是否需要对自定义情况进行处理
     */
    private boolean mNeedSelf = false;

    /**
     * 是否需要永远将展开或收回显示在最右边
     */
    private boolean mNeedAlwaysShowRight = false;

    /**
     * 是否需要动画 默认开启动画
     */
    private boolean mNeedAnimation = false;

    private int mLineCount;

    /**
     * 原始内容, 不包括"展开, 收起"
     */
    private CharSequence mContent;

    /**
     * 展开文字的颜色
     */
    private int mExpandTextColor;
    /**
     * 展开文字的颜色
     */
    private int mMentionTextColor;

    /**
     * 链接的字体颜色
     */
    private int mLinkTextColor;

    /**
     * 自定义规则的字体颜色
     */
    private int mSelfTextColor;

    /**
     * 收起的文字的颜色
     */
    private int mContractTextColor;

    /**
     * 展开的文案
     */
    private String mExpandString;
    /**
     * 收起的文案
     */
    private String mContractString;

    /**
     * 在收回和展开前面添加的内容
     */
    private String mEndExpandContent;

    /**
     * 在收回和展开前面添加的内容的字体颜色
     */
    private int mEndExpandTextColor;

    //是否AttachedToWindow
    private boolean isAttached;

    private final boolean mDontConsumeNonUrlClicks = true;

    public ExpandableTextView(Context context) {
        this(context, null);
    }

    public ExpandableTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ExpandableTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
        setMovementMethod(LocalLinkMovementMethod.getInstance());
        addOnAttachStateChangeListener(new OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                if (!isAttached) {
                    doSetContent();
                }
                isAttached = true;
            }

            @Override
            public void onViewDetachedFromWindow(View v) {

            }
        });
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        if (attrs != null) {
            TypedArray a =
                getContext().obtainStyledAttributes(attrs, R.styleable.ExpandableTextView,
                    defStyleAttr, 0);

            mLimitLines = a.getInt(R.styleable.ExpandableTextView_ep_max_line, DEF_MAX_LINE);
            mNeedExpend = a.getBoolean(R.styleable.ExpandableTextView_ep_need_expand, true);
            mNeedContract = a.getBoolean(R.styleable.ExpandableTextView_ep_need_contract, false);
            mNeedAnimation = a.getBoolean(R.styleable.ExpandableTextView_ep_need_animation, false);
            mNeedSelf = a.getBoolean(R.styleable.ExpandableTextView_ep_need_self, false);
            mNeedTopic = a.getBoolean(R.styleable.ExpandableTextView_ep_need_topic, false);
            mNeedCircle = a.getBoolean(R.styleable.ExpandableTextView_ep_need_circle, false);
            mNeedLink = a.getBoolean(R.styleable.ExpandableTextView_ep_need_link, false);
            mNeedAlwaysShowRight =
                a.getBoolean(R.styleable.ExpandableTextView_ep_need_always_showright, false);
            mNeedConvertUrl =
                a.getBoolean(R.styleable.ExpandableTextView_ep_need_convert_url, false);
            mContractString = a.getString(R.styleable.ExpandableTextView_ep_contract_text);
            mExpandString = a.getString(R.styleable.ExpandableTextView_ep_expand_text);
            if (TextUtils.isEmpty(mExpandString)) {
                mExpandString = TEXT_EXPEND;
            }
            if (TextUtils.isEmpty(mContractString)) {
                mContractString = TEXT_CONTRACT;
            }
            mExpandTextColor = a.getColor(R.styleable.ExpandableTextView_ep_expand_color,
                    Color.parseColor("#FFFFFF"));
            mEndExpandTextColor = a.getColor(R.styleable.ExpandableTextView_ep_expand_color,
                    Color.parseColor("#FFFFFF"));
            mContractTextColor = a.getColor(R.styleable.ExpandableTextView_ep_contract_color,
                    Color.parseColor("#FFFFFF"));
            mLinkTextColor = a.getColor(R.styleable.ExpandableTextView_ep_link_color,
                    Color.parseColor("#FF6200"));
            mSelfTextColor = a.getColor(R.styleable.ExpandableTextView_ep_self_color,
                    Color.parseColor("#FF6200"));
            mMentionTextColor = a.getColor(R.styleable.ExpandableTextView_ep_mention_color,
                    Color.parseColor("#C8D2FF"));
            mLinkDrawable = getResources().getDrawable(a.getResourceId(
                    R.styleable.ExpandableTextView_ep_link_res, R.mipmap.link));
            mCircleDrawable = getResources().getDrawable(a.getResourceId(
                    R.styleable.ExpandableTextView_ep_circle_res, R.mipmap.feed_circle_icon));
            mCurrentLines = mLimitLines;
            a.recycle();
        } else {
            mLinkDrawable = context.getResources().getDrawable(R.mipmap.link);
            mCircleDrawable = context.getResources().getDrawable(R.mipmap.feed_circle_icon);
        }

        mContext = context;

        mPaint = getPaint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        //初始化link的图片,必须设置图片大小，否则不显示
        mLinkDrawable.setBounds(0, 0, LINK_DRAWABLE_WH, LINK_DRAWABLE_WH);
        mCircleDrawable.setBounds(0, 0, CIRCLE_DRAWABLE_WH, CIRCLE_DRAWABLE_WH);
        mCircleDrawable.setColorFilter(mMentionTextColor, PorterDuff.Mode.SRC_IN);
    }

    private void setRealContent(CharSequence content) {
        Log.d(TAG, "setRealContent: " + content);
        //处理给定的数据
        mFormatData = formatData(content);
        //用来计算内容的大小
        updateLineCount();
        // 处理文案
        setTextView();
    }

    private void setTextView(){
        // 处理文案
        boolean needFold = mNeedExpend || mLineCount <= mLimitLines;
        SpannableStringBuilder ssb = dealContent(mFormatData, needFold);
        //将内容设置到控件中
        setText(ssb);
    }

    private void updateLineCount(){
        CharSequence content = mFormatData.getFormatedContent();
        //用来计算内容的大小
        Layout.Alignment align = Layout.Alignment.ALIGN_NORMAL;
        float multiplier = getLineSpacingMultiplier();
        float extra = getLineSpacingExtra();
        mDynamicLayout = new DynamicLayout(content, mPaint, mWidth, align, multiplier, extra, true);
        //获取行数
        mLineCount = mDynamicLayout.getLineCount();
        if (onGetLineCountListener != null) {
            onGetLineCountListener.onGetLineCount(mLineCount, mLineCount > mLimitLines);
        }
        Log.d(TAG, "updateLineCount: " + mLineCount + "(" + content + ")");
    }

    /**
     * 设置追加的内容
     */
    public void setEndExpendContent(String endExpendContent) {
        this.mEndExpandContent = endExpendContent;
    }

    /**
     * 设置内容
     */
    public void setContent(final CharSequence content) {
        mContent = content;
        if (isAttached) {
            doSetContent();
        }
    }

    /**
     * 更新内容
     */
    public void updateContent(CharSequence content) {
        Log.d(TAG, "updateContent: " + content);
        mContent = content;
        //处理给定的数据
        mFormatData = formatData(content);
        //用来计算内容的大小
        updateLineCount();
    }

    /**
     * 实际设置内容的
     */
    private void doSetContent() {
        if (mContent == null) {
            return;
        }
        mCurrentLines = mLimitLines;

        if (mWidth <= 0 && getWidth() > 0) {
            mWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        }

        if (mWidth <= 0) {
            mPendingAction = this::doSetContent;
        } else {
            setRealContent(mContent);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST || widthMode == MeasureSpec.EXACTLY) {
            mWidth = width - getPaddingLeft() - getPaddingRight();
        } else {
            mWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        }
        if (mPendingAction != null) {
            mPendingAction.run();
            mPendingAction = null;
        }
    }

    /**
     * 设置最后的收起文案
     */
    private String getExpandEndContent() {
        if (TextUtils.isEmpty(mEndExpandContent)) {
            return String.format(Locale.getDefault(), " %s",
                mContractString);
        } else {
            return String.format(Locale.getDefault(), "  %s  %s",
                mEndExpandContent, mContractString);
        }
    }

    /**
     * 设置展开的文案
     */
    private String getHideEndContent() {
        if (TextUtils.isEmpty(mEndExpandContent)) {
            return String.format(Locale.getDefault(), "...  %s",
                mExpandString);
        } else {
            return String
                .format(Locale.getDefault(), "...  %s  %s",
                    mEndExpandContent, mExpandString);
        }
    }

    /**
     * 处理文字中的链接问题
     */
    private SpannableStringBuilder dealContent(FormatData formatData, boolean needFold) {
        SpannableStringBuilder ssb = new SpannableStringBuilder();

        //处理折叠操作
        if (needFold) {
            if (mCurrentLines < mLineCount) {
                doExpand(formatData, ssb);
            } else {
                ssb.append(formatData.getFormatedContent());
                if (mNeedContract) {
                    doExtract(ssb);
                } else {
                    appendEndExpand(ssb);
                }
            }
        } else {
            ssb.append(formatData.getFormatedContent());
            appendEndExpand(ssb);
        }
        //处理特殊文案:链接, #话题, %圈子, @用户
        processSpecial(formatData, needFold, ssb);
        //清除链接点击时背景效果
        setHighlightColor(Color.TRANSPARENT);

        return ssb;
    }

    /**
     * 内容折叠,底部按钮设置为"展开"
     */
    private void doExpand(FormatData formatData, SpannableStringBuilder ssb) {
        L.Companion.d(TAG, "doExpand: currentLine:" + mCurrentLines + ", lineCount: " + mLineCount);

        int index = mCurrentLines - 1;
        int end = mDynamicLayout.getLineEnd(index);
        int start = mDynamicLayout.getLineStart(index);
        float lineWidth = mDynamicLayout.getLineWidth(index);

        String endString = getHideEndContent();
        float endStringWidth = mPaint.measureText(endString);

        //计算原内容被截取的位置下标
        int fitPosition = getFitPosition(endString, end, start, lineWidth, endStringWidth, 0);
        String substring = formatData.getFormatedContent().toString().substring(0, fitPosition);
        if (substring.endsWith("\n")) {
            substring = substring.substring(0, substring.length() - "\n".length());
        }
        ssb.append(substring);

        //在被截断的文字后面添加 展开 文字
        ssb.append(endString);

        int expendLength = TextUtils.isEmpty(mEndExpandContent) ? 0 : 2 + mEndExpandContent.length();
        int clickStart = ssb.length() - mExpandString.length() - expendLength;
        SwitchSpan clickSpan = new SwitchSpan(StatusType.STATUS_EXPAND, mExpandTextColor);
        ssb.setSpan(clickSpan, clickStart, ssb.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
    }

    private class SwitchSpan extends ClickableSpan {
        private final StatusType type;
        private final int color;

        SwitchSpan(StatusType type, int color) {
            this.type = type;
            this.color = color;
        }

        @Override
        public void onClick(@NonNull View widget) {
            if (mExpandOrContractClickListener != null) {
                mExpandOrContractClickListener.onClick(type);
            }
            switchExpandExtract();
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(color);
            ds.setUnderlineText(false);
        }
    }

    private void processSpecial(FormatData formatData, boolean ignoreMore,
                                SpannableStringBuilder ssb) {
        List<FormatData.PositionData> positionDatas = formatData.getPositionDatas();
        for (FormatData.PositionData data : positionDatas) {
            switch (data.getType()) {
                case LINK_TYPE:
                    processLink(data, ignoreMore, ssb);
                    break;
                case TOPIC_TYPE:
                    processTopic(data, ignoreMore, ssb);
                    break;
                case CIRCLE_TYPE:
                    processCircle(data, ignoreMore, ssb);
                    break;
                case SELF:
                    processSelf(data, ignoreMore, ssb);
                    break;
                case TRANSPARENT:
                    processTransparent(data, ignoreMore, ssb);
                    break;
            }
        }
    }

    private void processLink(FormatData.PositionData data, boolean ignoreMore,
                             SpannableStringBuilder ssb) {
        int start = data.getStart();
        int end = data.getEnd();
        String content = data.getUrl();
        if (mNeedExpend && ignoreMore) {
            int fitPosition = ssb.length() - getHideEndContent().length();
            if (mNeedConvertUrl && data.getStart() < fitPosition) {
                setImageSpan(ssb, data.getStart(), mLinkDrawable);
                //设置链接文字样式
                if (mCurrentLines < mLineCount) {
                    if (fitPosition > data.getStart() + 1 && fitPosition < data.getEnd()) {
                        end = fitPosition;
                    }
                }
                if (data.getStart() + 1 < fitPosition) {
                    addSpan(LinkType.LINK_TYPE, ssb, start, end, content, null, mLinkTextColor);
                }
            }
        } else {
            if (mNeedConvertUrl) {
                setImageSpan(ssb, data.getStart(), mLinkDrawable);
            }
            addSpan(LinkType.LINK_TYPE, ssb, start, end, content, null, mLinkTextColor);
        }
    }

    private void processCircle(FormatData.PositionData data, boolean ignoreMore,
                               SpannableStringBuilder ssb) {
        int start = data.getStart();
        int end = data.getEnd();
        String content = data.getUrl();
        //如果需要展开
        if (mNeedExpend && ignoreMore) {
            int fitPosition = ssb.length() - getHideEndContent().length();
            if (data.getStart() < fitPosition) {
                setImageSpan(ssb, data.getStart(), mCircleDrawable);
                if (mCurrentLines < mLineCount) {
                    if (fitPosition > data.getStart() + 1 && fitPosition < data.getEnd()) {
                        end = fitPosition;
                    }
                }
                if (data.getStart() + 1 < fitPosition) {
                    addSpan(LinkType.CIRCLE_TYPE, ssb, start, end, content, null, mMentionTextColor);
                }
            }
        } else {
            setImageSpan(ssb, data.getStart(), mCircleDrawable);
            addSpan(LinkType.CIRCLE_TYPE, ssb, start, end, content, null, mMentionTextColor);
        }
    }

    private void setImageSpan(SpannableStringBuilder ssb, int start, Drawable drawable) {
        SelfImageSpan imageSpan = new SelfImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
        ssb.setSpan(imageSpan, start, start + 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
    }

    private void processTopic(FormatData.PositionData data, boolean ignoreMore,
                              SpannableStringBuilder ssb) {
        int start = data.getStart();
        int end = data.getEnd();
        String content = data.getUrl();
        if (mNeedExpend && ignoreMore) {
            int fitPosition = ssb.length() - getHideEndContent().length();
            if (start < fitPosition) {
                if (mCurrentLines < mLineCount) {
                    if (fitPosition < data.getEnd()) {
                        end = fitPosition;
                    }
                }
                addSpan(LinkType.TOPIC_TYPE, ssb, start, end, content, null, mMentionTextColor);
            }
        } else {
            addSpan(LinkType.TOPIC_TYPE, ssb, start, end, content, null, mMentionTextColor);
        }
    }

    private void processTransparent(FormatData.PositionData data, boolean ignoreMore,
                              SpannableStringBuilder ssb) {
        int start = data.getStart();
        int end = data.getEnd();
        String content = data.getUrl();
        int fitPosition = ssb.length() - getHideEndContent().length();
        if (fitPosition < data.getEnd()) {
            end = fitPosition;
        }
        addSpan(LinkType.TRANSPARENT, ssb, start, end, content, null, android.R.color.transparent);
    }

    private void processSelf(FormatData.PositionData data, boolean ignoreMore,
                             SpannableStringBuilder ssb) {

        int start = data.getStart();
        int end = data.getEnd();
        String content = data.getSelfAim();
        String self = data.getSelfContent();
        if (mNeedExpend && ignoreMore) {
            int fitPosition = ssb.length() - getHideEndContent().length();
            if (start < fitPosition) {
                if (mCurrentLines < mLineCount) {
                    if (fitPosition < data.getEnd()) {
                        end = fitPosition;
                    }
                }
                addSpan(LinkType.SELF, ssb, start, end, content, self, mSelfTextColor);
            }
        } else {
            addSpan(LinkType.SELF, ssb, start, end, content, self, mSelfTextColor);
        }
    }

    private void appendEndExpand(SpannableStringBuilder ssb) {
        if (!TextUtils.isEmpty(mEndExpandContent)) {
            ssb.append(mEndExpandContent);
            ssb.setSpan(new ForegroundColorSpan(mEndExpandTextColor),
                    ssb.length() - mEndExpandContent.length(), ssb.length(),
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
    }

    /**
     * 内容展开,底部按钮设置为"收起"
     */
    private void doExtract(SpannableStringBuilder ssb) {
        L.Companion.d(TAG, "doExtract: currentLine:" + mCurrentLines + ", lineCount: " + mLineCount);

        String endString = getExpandEndContent();

        //先在尾部添加换行
        if (mNeedAlwaysShowRight) {
            if (!ssb.toString().endsWith("\n")) {
                ssb.append("\n");
            }
            StringBuilder lastLine = new StringBuilder();
            while (mPaint.measureText(lastLine + endString) < mWidth) {
                lastLine.append(SPACE);
            }
            lastLine.replace(0, 1, "");

            ssb.append(lastLine);
        }
        ssb.append(endString);

        int expendLength = TextUtils.isEmpty(mEndExpandContent) ? 0 : 2 + mEndExpandContent.length();
        int clickStart = ssb.length() - mContractString.length() - expendLength;
        SwitchSpan clickSpan = new SwitchSpan(StatusType.STATUS_CONTRACT, mContractTextColor);
        ssb.setSpan(clickSpan, clickStart, ssb.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
    }

    /**
     * 获取需要插入的空格
     */
    private int getFitSpaceCount(float emptyWidth, float endStringWidth) {
        float measureText = mPaint.measureText(SPACE);
        int count = 0;
        while (endStringWidth + measureText * count < emptyWidth) {
            count++;
        }
        return --count;
    }

    private void addSpan(LinkType type, SpannableStringBuilder ssb, int start, int end,
                         String content, String self, int color) {
        ssb.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                if (mLinkClickListener != null) {
                     mLinkClickListener.onLinkClickListener(type, content, self);
                } else {
                    //如果没有设置监听 则调用默认的打开浏览器显示连接
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Uri url = Uri.parse(content);
                    intent.setData(url);
                    mContext.startActivity(intent);
                }
            }

            @Override
            public void updateDrawState(TextPaint paint) {
                paint.setColor(color);
                paint.setUnderlineText(false);
            }
        }, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
    }

    /**
     * 切换展开和收回的状态
     */
    private void switchExpandExtract() {
        boolean isFold = mCurrentLines < mLineCount;

        if (mNeedAnimation) {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
            final boolean finalIsHide = isFold;
            valueAnimator.addUpdateListener(animation -> {
                Float value = (Float) animation.getAnimatedValue();
                if (finalIsHide) {
                    mCurrentLines = mLimitLines + (int) ((mLineCount - mLimitLines) * value);
                } else {
                    if (mNeedContract) {
                        mCurrentLines =
                            mLimitLines + (int) ((mLineCount - mLimitLines) * (1 - value));
                    }
                }
                setTextView();
            });
            valueAnimator.setDuration(100);
            valueAnimator.start();
        } else {
            if (isFold) {
                mCurrentLines = mLineCount;
            } else {
                if (mNeedContract) {
                    mCurrentLines = mLimitLines;
                }
            }
            setTextView();
        }
    }

    /**
     * 计算原内容被裁剪的长度
     */
    private int getFitPosition(String endString, int endPosition, int startPosition,
        float lineWidth,
        float endStringWith, float offset) {
        //最后一行需要添加的文字的字数
        int position = (int) ((lineWidth - (endStringWith + offset)) * (endPosition - startPosition)
            / lineWidth);

        if (position <= endString.length()) {
            return endPosition;
        }
        String lastLineStr = mFormatData.getFormatedContent().toString().substring(startPosition,
            startPosition + position) + endString;
        DynamicLayout layout = new DynamicLayout(lastLineStr, mPaint, mWidth,
            Layout.Alignment.ALIGN_NORMAL, getLineSpacingMultiplier(), getLineSpacingExtra(),
            true);
        if (layout.getLineCount() < 2) {
            return startPosition + position;
        } else {
            return getFitPosition(endString, endPosition, startPosition, lineWidth, endStringWith,
                offset + mPaint.measureText(SPACE));
        }
    }

    /**
     * 对传入的数据进行正则匹配并处理
     */
    private FormatData formatData(CharSequence content) {
        FormatData formatData = new FormatData();
        List<FormatData.PositionData> datas = new ArrayList<>();
        StringBuffer newResult = new StringBuffer();
        //对自定义的进行正则匹配
        if (mNeedSelf) {
            processCustomRules(content, datas, newResult);
            //重置状态
            content = newResult.toString();
            newResult = new StringBuffer();
        }

        if (mNeedLink) {
            processLinkPattern(content, datas, newResult);
            //重置状态
            content = newResult.toString();
        }

        //对#话题 进行正则匹配
        if (mNeedTopic) {
            processPattern(REGEXP_TOPIC, datas, content, LinkType.TOPIC_TYPE);
        }

        //对%圈子 进行正则匹配
        if (mNeedCircle) {
            processPattern(REGEXP_CIRCLE, datas, content, LinkType.CIRCLE_TYPE);
        }

        // 队隐藏内容 进行正则匹配
        processPattern(REGEXP_TRANSPARENT, datas, content, LinkType.TRANSPARENT);

        formatData.setFormatedContent(content.toString());
        formatData.setPositionDatas(datas);
        return formatData;
    }

    private void processPattern(
            String regexp,
            List<FormatData.PositionData> dataList,
            CharSequence content,
            LinkType type) {
        Pattern pattern = Pattern.compile(regexp, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            //将匹配到的内容进行统计处理
            int start = matcher.start();
            int end = matcher.end();
            String url = matcher.group();
            FormatData.PositionData data = new FormatData.PositionData(start, end, url, type);
            dataList.add(data);
        }
    }

    @SuppressLint("RestrictedApi")
    private void processLinkPattern(CharSequence content, List<FormatData.PositionData> datas,
        StringBuffer newResult) {
        Pattern pattern = AUTOLINK_WEB_URL;
        Matcher matcher = pattern.matcher(content);
        int start;
        int end = 0;
        int temp = 0;

        while (matcher.find()) {
            start = matcher.start();
            end = matcher.end();
            newResult.append(content.toString().substring(temp, start));
            if (mNeedConvertUrl) {
                //将匹配到的内容进行统计处理
                datas.add(new FormatData.PositionData(newResult.length() + 1,
                    newResult.length() + 2 + TARGET.length(), matcher.group(),
                    LinkType.LINK_TYPE));
                newResult.append(" ").append(TARGET).append(" ");
            } else {
                String result = matcher.group();
                datas.add(new FormatData.PositionData(newResult.length(),
                    newResult.length() + 2 + result.length(), result, LinkType.LINK_TYPE));
                newResult.append(" ").append(result).append(" ");
            }
            temp = end;
        }
        newResult.append(content.toString().substring(end));
    }

    private void processCustomRules(CharSequence content, List<FormatData.PositionData> datas,
        StringBuffer newResult) {
        int start = 0;
        int end = 0;
        int temp = 0;
        Pattern pattern = Pattern.compile(SELF_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(content);
        List<FormatData.PositionData> datasMention = new ArrayList<>();
        while (matcher.find()) {
            start = matcher.start();
            end = matcher.end();
            newResult.append(content.toString().substring(temp, start));
            //将匹配到的内容进行统计处理
            String result = matcher.group();
            if (!TextUtils.isEmpty(result)) {
                //解析数据
                String aimSrt = result.substring(result.indexOf("[") + 1, result.indexOf("]"));
                String contentSrt =
                    result.substring(result.indexOf("(") + 1, result.indexOf(")"));
                datasMention.add(new FormatData.PositionData(newResult.length() + 1,
                    newResult.length() + 2 + aimSrt.length(), aimSrt, contentSrt,
                    LinkType.SELF));
                newResult.append(" ").append(aimSrt).append(" ");
                temp = end;
            }
        }
        datas.addAll(datasMention);
        newResult.append(content.toString().substring(end));
    }

    /**
     * 自定义ImageSpan 让Image 在行内居中显示
     */
    static class SelfImageSpan extends ImageSpan {
        private final Drawable drawable;

        public SelfImageSpan(Drawable d, int verticalAlignment) {
            super(d, verticalAlignment);
            this.drawable = d;
        }

        @Override
        public Drawable getDrawable() {
            return drawable;
        }

        @Override
        public void draw(@NonNull Canvas canvas, CharSequence text,
            int start, int end, float x,
            int top, int y, int bottom, @NonNull Paint paint) {
            // image to draw
            Drawable b = getDrawable();
            // font metrics of text to be replaced
            Paint.FontMetricsInt fm = paint.getFontMetricsInt();
            int transY = (y + fm.descent + y + fm.ascent) / 2
                - b.getBounds().bottom / 2;
            canvas.save();
            canvas.translate(x, transY);
            b.draw(canvas);
            canvas.restore();
        }
    }

    public static class LocalLinkMovementMethod extends LinkMovementMethod {
        static LocalLinkMovementMethod sInstance;


        public static LocalLinkMovementMethod getInstance() {
            if (sInstance == null)
                sInstance = new LocalLinkMovementMethod();

            return sInstance;
        }

        @Override
        public boolean onTouchEvent(TextView widget,
            Spannable buffer, MotionEvent event) {

            Log.d(TAG, "LocalLinkMovementMethod onTouchEvent: " + event);
            int action = event.getAction();

            if (action == MotionEvent.ACTION_UP ||
                action == MotionEvent.ACTION_DOWN) {
                int x = (int) event.getX();
                int y = (int) event.getY();

                x -= widget.getTotalPaddingLeft();
                y -= widget.getTotalPaddingTop();

                x += widget.getScrollX();
                y += widget.getScrollY();

                Layout layout = widget.getLayout();
                int line = layout.getLineForVertical(y);
                int off = layout.getOffsetForHorizontal(line, x);

                ClickableSpan[] link = buffer.getSpans(
                    off, off, ClickableSpan.class);

                if (link.length != 0) {
                    if (action == MotionEvent.ACTION_UP) {
                        link[0].onClick(widget);
                    } else if (action == MotionEvent.ACTION_DOWN) {
                        Selection.setSelection(buffer,
                            buffer.getSpanStart(link[0]),
                            buffer.getSpanEnd(link[0]));
                    }

                    if (widget instanceof ExpandableTextView) {
                        ((ExpandableTextView) widget).mLinkHit = true;
                    }
                    return true;
                } else {
                    Selection.removeSelection(buffer);
                    Touch.onTouchEvent(widget, buffer, event);
                    return super.onTouchEvent(widget, buffer, event);
                }
            }
            return super.onTouchEvent(widget, buffer, event);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent: " + event);
        int action = event.getAction();
        mLinkHit = false;
        boolean res = super.onTouchEvent(event);

        // 问题就出在这里
//        if (mDontConsumeNonUrlClicks)
//            return mLinkHit;

        //防止选择复制的状态不消失
        if (action == MotionEvent.ACTION_UP) {
            this.setTextIsSelectable(false);
        }

        return res;
    }

    public interface OnLinkClickListener {
        void onLinkClickListener(LinkType type, String content, String selfContent);
    }

    public interface OnGetLineCountListener {
        /**
         * lineCount 预估可能占有的行数
         * canExpand 是否达到可以展开的条件
         */
        void onGetLineCount(int lineCount, boolean canExpand);
    }

    private OnGetLineCountListener onGetLineCountListener;

    public OnGetLineCountListener getOnGetLineCountListener() {
        return onGetLineCountListener;
    }

    public void setOnGetLineCountListener(OnGetLineCountListener onGetLineCountListener) {
        this.onGetLineCountListener = onGetLineCountListener;
    }

    public interface OnExpandOrContractClickListener {
        void onClick(StatusType type);
    }

    public OnLinkClickListener getLinkClickListener() {
        return mLinkClickListener;
    }

    public void setLinkClickListener(OnLinkClickListener linkClickListener) {
        this.mLinkClickListener = linkClickListener;
    }

    public boolean isNeedTopic() {
        return mNeedTopic;
    }

    public void setNeedTopic(boolean needTopic) {
        this.mNeedTopic = needTopic;
    }

    public boolean isNeedCircle() {
        return mNeedCircle;
    }

    public void setNeedCircle(boolean needCircle) {
        this.mNeedTopic = needCircle;
    }

    public Drawable getLinkDrawable() {
        return mLinkDrawable;
    }

    public void setLinkDrawable(Drawable mLinkDrawable) {
        this.mLinkDrawable = mLinkDrawable;
    }

    public Drawable getCircleDrawable() {
        return mCircleDrawable;
    }

    public void setCircleDrawable(Drawable circleDrawable) {
        mCircleDrawable = circleDrawable;
    }

    public boolean isNeedContract() {
        return mNeedContract;
    }

    public void setNeedContract(boolean mNeedContract) {
        this.mNeedContract = mNeedContract;
    }

    public boolean isNeedExpend() {
        return mNeedExpend;
    }

    public void setNeedExpend(boolean mNeedExpend) {
        this.mNeedExpend = mNeedExpend;
    }

    public boolean isNeedAnimation() {
        return mNeedAnimation;
    }

    public void setNeedAnimation(boolean mNeedAnimation) {
        this.mNeedAnimation = mNeedAnimation;
    }

    public int getExpandableLineCount() {
        return mLineCount;
    }

    public void setExpandableLineCount(int mLineCount) {
        this.mLineCount = mLineCount;
    }

    public int getExpandTextColor() {
        return mExpandTextColor;
    }

    public void setExpandTextColor(int mExpandTextColor) {
        this.mExpandTextColor = mExpandTextColor;
    }

    public int getExpandableLinkTextColor() {
        return mLinkTextColor;
    }

    public void setExpandableLinkTextColor(int mLinkTextColor) {
        this.mLinkTextColor = mLinkTextColor;
    }

    public int getContractTextColor() {
        return mContractTextColor;
    }

    public void setContractTextColor(int mContractTextColor) {
        this.mContractTextColor = mContractTextColor;
    }

    public String getExpandString() {
        return mExpandString;
    }

    public void setExpandString(String mExpandString) {
        this.mExpandString = mExpandString;
    }

    public String getContractString() {
        return mContractString;
    }

    public void setContractString(String mContractString) {
        this.mContractString = mContractString;
    }

    public int getEndExpandTextColor() {
        return mEndExpandTextColor;
    }

    public void setEndExpandTextColor(int mEndExpandTextColor) {
        this.mEndExpandTextColor = mEndExpandTextColor;
    }

    public boolean isNeedLink() {
        return mNeedLink;
    }

    public void setNeedLink(boolean mNeedLink) {
        this.mNeedLink = mNeedLink;
    }

    public int getSelfTextColor() {
        return mSelfTextColor;
    }

    public void setSelfTextColor(int mSelfTextColor) {
        this.mSelfTextColor = mSelfTextColor;
    }

    public boolean isNeedSelf() {
        return mNeedSelf;
    }

    public void setNeedSelf(boolean mNeedSelf) {
        this.mNeedSelf = mNeedSelf;
    }

    public boolean isNeedAlwaysShowRight() {
        return mNeedAlwaysShowRight;
    }

    public void setNeedAlwaysShowRight(boolean mNeedAlwaysShowRight) {
        this.mNeedAlwaysShowRight = mNeedAlwaysShowRight;
    }

    public OnExpandOrContractClickListener getExpandOrContractClickListener() {
        return mExpandOrContractClickListener;
    }

    public void setExpandOrContractClickListener(
        OnExpandOrContractClickListener expandOrContractClickListener) {
        this.mExpandOrContractClickListener = expandOrContractClickListener;
    }
}
