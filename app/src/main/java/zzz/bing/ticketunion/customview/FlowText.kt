package zzz.bing.ticketunion.customview

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import zzz.bing.ticketunion.R
import zzz.bing.ticketunion.utils.SizeUtils

class FlowText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {
    companion object{
        const val FLOW_TEXT_MAX_LINE = -1
        const val FLOW_TEXT_MARGIN = 0f
        const val FLOW_TEXT_MAX_LENGTH = 10
        const val FLOW_TEXT_UNDEFINED = -1
        const val FLOW_TEXT_DEFAULT_SIZE = -1
    }
    private var _maxLine: Int
    private var _textMarginTop: Int
    private var _textMarginLeft: Int
    private var _textMarginBottom: Int
    private var _textMarginRight: Int
    private var _textMaxLength: Int
    private var _flowTextSize: Float
    private var _textColor: Int
    private var _textBackground: Int

    private var _textList = ArrayList<String>()
    private val _lines = ArrayList<List<View>>()
    private var _isColorResourceId = true
    private var _onItemClickListener: OnItemClickListener? = null

    init {
        //初始化属性
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlowText)
        val textMargin = typedArray.getDimension(R.styleable.FlowText_textMargin, FLOW_TEXT_MARGIN)
        _maxLine = typedArray.getInt(R.styleable.FlowText_maxLine, FLOW_TEXT_MAX_LINE)
        _textMarginTop  = typedArray.getDimension(R.styleable.FlowText_textMarginTop, textMargin).toInt()
        _textMarginLeft  = typedArray.getDimension(R.styleable.FlowText_textMarginLeft, textMargin).toInt()
        _textMarginBottom  = typedArray.getDimension(R.styleable.FlowText_textMarginBottom, textMargin).toInt()
        _textMarginRight  = typedArray.getDimension(R.styleable.FlowText_textMarginRight, textMargin).toInt()
        _textMaxLength = typedArray.getInt(R.styleable.FlowText_textMaxLength, FLOW_TEXT_MAX_LENGTH)
        _flowTextSize = typedArray.getDimension(R.styleable.FlowText_flowTextSize,FLOW_TEXT_DEFAULT_SIZE.toFloat())

        _textColor = typedArray.getResourceId(R.styleable.FlowText_textColor, FLOW_TEXT_UNDEFINED)
//        LogUtils.d(this,"_textColor == > $_textColor")
        if (_textColor == FLOW_TEXT_UNDEFINED){
            _textColor = typedArray.getColor(R.styleable.FlowText_textColor, resources.getColor(R.color.textColor, null))
            _isColorResourceId = false
//            LogUtils.d(this,"_textColor == > $_textColor")
        }
        _textBackground = typedArray.getResourceId(R.styleable.FlowText_textBackground, FLOW_TEXT_UNDEFINED)

//        LogUtils.d(this, "_maxLine ==> $_maxLine")
//        LogUtils.d(this, "_textMarginTop ==> $_textMarginTop")
//        LogUtils.d(this, "_textMarginLeft ==> $_textMarginLeft")
//        LogUtils.d(this, "_textMarginBottom ==> $_textMarginBottom")
//        LogUtils.d(this, "_textMarginRight ==> $_textMarginRight")
//        LogUtils.d(this, "_textMaxLength ==> $_textMaxLength")
//        LogUtils.d(this, "_textColor ==> $_textColor")
//        LogUtils.d(this, "_textBackground ==> $_textBackground")
        typedArray.recycle()
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val fistChild = getChildAt(0) ?: return
        var pointerLeft = _textMarginLeft
        var pointerTop = _textMarginTop
        var pointerRight = _textMarginRight
        var pointerBottom = fistChild.measuredHeight + _textMarginBottom + _textMarginTop
        for (list in _lines){
            for (view in list){
                pointerRight += view.measuredWidth + _textMarginRight + _textMarginLeft
                view.layout(pointerLeft, pointerTop, pointerRight, pointerBottom)
                pointerLeft = pointerRight + _textMarginLeft
            }
            pointerLeft = _textMarginLeft
            pointerRight = _textMarginRight
            pointerTop += fistChild.measuredHeight + _textMarginTop
            pointerBottom += fistChild.measuredHeight + _textMarginBottom + _textMarginTop
        }
    }

    fun setTextList(list: ArrayList<String>) {
        _textList.clear()
        _textList.addAll(list)
        //根据数据更新View
        setupChildren()
    }

    private fun setupChildren() {
        removeAllViews()

        _textList.forEach { string ->
            val textView = TextView(context)
            textView.text = string
            textView.setPadding(SizeUtils.dip2px(3f))
            textView.isClickable = true
            textView.setTextColor(
                if (_isColorResourceId) ContextCompat.getColor(context, _textColor) else _textColor
            )

            if (_textBackground != FLOW_TEXT_UNDEFINED){
                textView.background = ContextCompat.getDrawable(context, _textBackground)
            }
            textView.setOnClickListener { view ->
                _onItemClickListener?.apply {
                    itemClickListener(view, string)
                }
            }
            textView.maxLines = 1
            if(_flowTextSize != FLOW_TEXT_DEFAULT_SIZE.toFloat() && _flowTextSize > 1){
                textView.textSize = _flowTextSize
            }
            textView.setFadingEdgeLength(_textMaxLength)
            addView(textView)
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val parentWidth = MeasureSpec.getSize(widthMeasureSpec)
        val parentHeight = MeasureSpec.getSize(heightMeasureSpec)

        if (childCount == 0) {
            return
        }
        _lines.clear()
        val childWidth = MeasureSpec.makeMeasureSpec(parentWidth, MeasureSpec.AT_MOST)
        val childHeight = MeasureSpec.makeMeasureSpec(parentHeight, MeasureSpec.AT_MOST)
        var line = ArrayList<View>()
        _lines.add(line)
        for (index: Int in 0 until childCount) {
            val child = getChildAt(index)

            measureChild(child, childWidth, childHeight)

            if (line.size != 0) {
                val isCanAdd = checkChildCanAdd(line, child, parentWidth)
                if (!isCanAdd){
                    if (_maxLine == FLOW_TEXT_MAX_LINE || _lines.size < _maxLine){
                        line = ArrayList()
                        _lines.add(line)
                    }else{
                        break
                    }
                }
            }
            line.add(child)
        }
        val parentHeightTargetSize = (getChildAt(0).measuredHeight + _textMarginTop + _textMarginBottom )* _lines.size
        setMeasuredDimension(parentWidth,parentHeightTargetSize)
    }

    private fun checkChildCanAdd(line: java.util.ArrayList<View>, child: View?, parentWidth: Int): Boolean {
        var totalWidth = 0
        for(view in line){
            totalWidth += view.measuredWidth + _textMarginLeft + _textMarginRight
        }
        totalWidth += child?.measuredWidth!! + _textMarginLeft + _textMarginRight
        return totalWidth < parentWidth
    }
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener){
        _onItemClickListener = onItemClickListener
    }

    interface OnItemClickListener{
        fun itemClickListener(view: View, text: String)
    }
}