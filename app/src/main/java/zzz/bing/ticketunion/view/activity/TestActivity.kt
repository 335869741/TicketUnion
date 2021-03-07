package zzz.bing.ticketunion.view.activity

import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import zzz.bing.ticketunion.BaseActivity
import zzz.bing.ticketunion.customview.FlowText
import zzz.bing.ticketunion.databinding.ActivityTestBinding
import zzz.bing.ticketunion.utils.LogUtils
import zzz.bing.ticketunion.viewmodel.TestViewModel

class TestActivity :BaseActivity<ActivityTestBinding,TestViewModel>() {
    override fun getViewBinding(): ActivityTestBinding {
        return ActivityTestBinding.inflate(layoutInflater)
    }

    override fun initViewModel(): TestViewModel {
        return ViewModelProvider(this).get(TestViewModel::class.java)
    }

    override fun initData() {
        val texts = ArrayList<String>()
        texts.add("ActivityTestBinding")
        texts.add("_maxLine")
        texts.add("_textMarginTop")
        texts.add("_textMarginLeft")
        texts.add("_textMarginBottom")
        texts.add("_textMarginRight")
        texts.add("_textMaxLength")
        texts.add("_textColor")
        texts.add("_textBackground")
        texts.add("_textList")
        binding.flow.setTextList(texts)

        binding.flow.setOnItemClickListener(object : FlowText.OnItemClickListener {
            override fun itemClickListener(view: View, text: String) {
                LogUtils.d(this@TestActivity, "viewString == > ${(view as TextView).text} <> String ==> $text")
            }
        })
    }
}