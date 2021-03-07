package zzz.bing.ticketunion.view.fragment

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import zzz.bing.ticketunion.BaseFragment
import zzz.bing.ticketunion.customview.FlowText
import zzz.bing.ticketunion.databinding.FragmentSearchBinding
import zzz.bing.ticketunion.utils.LogUtils
import zzz.bing.ticketunion.viewmodel.HomeViewModel
import zzz.bing.ticketunion.viewmodel.SearchViewModel
import java.util.ArrayList

class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>() {
    override fun initViewBinding(): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(layoutInflater)
    }

    override fun initViewModel(): SearchViewModel {
        val vm by viewModels<SearchViewModel>()
        return vm
    }

    override fun initData() {
        viewModel.loadSearchRecommend()
        binding.flowTextSearchHistory.visibility = View.GONE
        binding.constraintSearchHistory.visibility = View.GONE
    }

    override fun initObserver() {
        viewModel.searchRecommend.observe(viewLifecycleOwner, {searchList ->
            val list = ArrayList<String>()
            searchList.forEach {
                list.add(it.keyword)
            }
            LogUtils.d(this,"list ==> $list")
            if (list.isEmpty()) return@observe
            binding.flowTextRecommended.setTextList(list)
        })

        viewModel.searchHistory.observe(viewLifecycleOwner, { historyList ->
            val list = ArrayList<String>()
            historyList.forEach {
                list.add(it.searchText)
            }
            LogUtils.d(this, "list ==> $list")
            if (list.isEmpty()) {
                binding.flowTextSearchHistory.visibility = View.GONE
                binding.constraintSearchHistory.visibility = View.GONE
                return@observe
            }
            binding.flowTextSearchHistory.visibility = View.VISIBLE
            binding.constraintSearchHistory.visibility = View.VISIBLE
            binding.flowTextSearchHistory.setTextList(list)
        })

        viewModel.searchContent.observe(viewLifecycleOwner, {
            // TODO: 2021/3/8  适配器加载数据
        })
    }

    override fun initListener() {
        binding.flowTextSearchHistory.setOnItemClickListener(object : FlowText.OnItemClickListener{
            @Suppress("UselessCallOnNotNull")
            override fun itemClickListener(view: View, text: String) {
                val string = text.trim()
                if (!string.isNullOrEmpty()){
                    viewModel.addSearchHistory(text)
                    viewModel.loadSearchContent(1,string)
                }
            }
        })
        binding.textSearchButton.setOnClickListener {
            // TODO: 2021/3/8
        }
        binding.editTextSearch.addTextChangedListener {
            it?.apply {
                if (!trim().isEmpty()){
                    binding.textSearchButton.text = "搜索"
                }else{
                    binding.textSearchButton.text = "取消"
                }
            }
        }
    }
}