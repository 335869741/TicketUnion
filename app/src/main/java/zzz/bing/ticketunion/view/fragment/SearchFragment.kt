package zzz.bing.ticketunion.view.fragment

import android.app.AlertDialog
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import zzz.bing.ticketunion.BaseFragment
import zzz.bing.ticketunion.customview.FlowText
import zzz.bing.ticketunion.databinding.FragmentSearchBinding
import zzz.bing.ticketunion.model.domain.SearchData
import zzz.bing.ticketunion.utils.LogUtils
import zzz.bing.ticketunion.utils.NetLoadState
import zzz.bing.ticketunion.utils.NetLoadStateUtils
import zzz.bing.ticketunion.view.adapter.SearchContentAdapter
import zzz.bing.ticketunion.viewmodel.SearchViewModel
import java.lang.StringBuilder
import java.util.ArrayList

class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>() {
    private var _queryState = false
    private val _editString = StringBuilder()
    private val _adapter by lazy { SearchContentAdapter() }

    override fun initViewBinding(): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(layoutInflater)
    }

    override fun initViewModel(): SearchViewModel {
        val vm by viewModels<SearchViewModel>()
        return vm
    }

    override fun initData() {
        viewModel.loadSearchRecommend()
    }

    override fun initView() {
        binding.recycler.adapter = _adapter
        binding.recycler.layoutManager = LinearLayoutManager(view?.context)
        binding.refresh.setEnableLoadmore(true)
        binding.refresh.setEnableRefresh(false)
    }

    override fun initObserver() {
        /**
         * 热搜推荐关键词
         */
        viewModel.searchRecommend.observe(viewLifecycleOwner, {searchList ->
            val list = ArrayList<String>()
            searchList.forEach {
                list.add(it.keyword)
            }
            LogUtils.d(this,"list ==> $list")
            if (list.isEmpty()) return@observe
            binding.flowTextRecommended.setTextList(list)
        })
        /**
         * 搜索历史
         */
        viewModel.searchHistory.observe(viewLifecycleOwner, { historyList ->
            val list = ArrayList<String>()
            historyList.forEach {
                list.add(it.searchText)
            }
            LogUtils.d(this, "list ==> $list")
            if (list.isEmpty()) {
                binding.constraintSearchHistory.visibility = View.GONE
                return@observe
            }
            binding.constraintSearchHistory.visibility = View.VISIBLE
            binding.flowTextSearchHistory.setTextList(list)
        })
        /**
         * 搜索结果
         */
        viewModel.searchContent.observe(viewLifecycleOwner, {list ->
            list?.apply {
                if (isNotEmpty()){
                    if (viewModel.isLoadMore){
                        val array = ArrayList<SearchData>()
                        array.addAll(_adapter.currentList)
                        array.addAll(list)
                        _adapter.submitList(array)
                        binding.refresh.finishLoadmore()
                    }else{
                        _adapter.submitList(this)
                    }
                }
            }
        })
        /**
         * 加载状态
         */
        viewModel.searchLoadState.observe(viewLifecycleOwner, {state ->
            if (!viewModel.isLoadMore){
                LogUtils.d(this,"state ==> $state || isLoadMore ==> ${viewModel.isLoadMore}")
                NetLoadStateUtils.viewStateChange(
                    binding.includeLoading.root,
                    binding.includeLoadError.root,
                    binding.refresh,
                    state
                )
            }
            binding.key.visibility = View.GONE
        })
    }

    override fun initListener() {
        /**
         * 历史记录item
         */
        binding.flowTextSearchHistory.setOnItemClickListener(object : FlowText.OnItemClickListener{
            override fun itemClickListener(view: View, text: String) {
                LogUtils.d(this@SearchFragment,"text ==> $text")
                search(text, true)
            }
        })
        /**
         * 精选推荐item
         */
        binding.flowTextRecommended.setOnItemClickListener(object :FlowText.OnItemClickListener{
            override fun itemClickListener(view: View, text: String) {
                LogUtils.d(this@SearchFragment,"text ==> $text")
                search(text, true)
            }
        })
        /**
         * 搜索按钮
         */
        binding.textSearchButton.setOnClickListener {
            LogUtils.d(this,"editString ==> $_editString")
            search(_editString.toString(), _queryState)
            it.requestFocus()
        }
        /**
         * 检测输入
         */
        binding.editTextSearch.addTextChangedListener {
            it?.apply {
                if (trim().isNotEmpty()){
                    binding.textSearchButton.text = "搜索"
                    _editString.setLength(0)
                    _editString.append(this)
                    _queryState = true
                }else{
                    binding.textSearchButton.text = "取消"
                    _editString.setLength(0)
                    _queryState = false

                }
            }
        }
        /**
         * 输入框获取焦点
         */
        binding.editTextSearch.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus){
                binding.key.visibility = View.VISIBLE
                binding.includeLoadError.root.visibility = View.GONE
                binding.includeLoading.root.visibility = View.GONE
                binding.refresh.visibility = View.GONE
            }
        }
        /**
         * 输入框
         */
        binding.editTextSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND){
                binding.textSearchButton.performClick()
            }
            return@setOnEditorActionListener false
        }
        /**
         * 清空历史
         */
        binding.imageSearchHistory.setOnClickListener {
            viewModel.searchHistory.value?.apply {
                if (!isNullOrEmpty()){
                    AlertDialog.Builder(requireActivity())
                        .setTitle("清空历史记录")
                        .setPositiveButton("确定") { _, _ ->
                            viewModel.deleteSearchHistory()
                        }.setNegativeButton("取消"){ _, _ ->
                        }.create()
                        .show()
                    LogUtils.d(this, "searchHistory ==> ${viewModel.searchHistory}")
                }
            }
        }
        //重新加载
        binding.includeLoadError.root.setOnClickListener {
            if (viewModel.searchLoadState.value == NetLoadState.Error){
                viewModel.reLoad()
            }
        }
        //加载更多
        binding.refresh.setOnRefreshListener(object : RefreshListenerAdapter(){
            override fun onLoadMore(refreshLayout: TwinklingRefreshLayout?) {
                viewModel.loadMore()
            }
        })
    }

    /**
     * 搜索
     */
    @Suppress("UselessCallOnNotNull")
    fun search(text: String, isQueryState: Boolean){
        val string = text.trim()
        if (isQueryState && !string.isNullOrEmpty()){
            viewModel.addSearchHistory(text)
            viewModel.load(1,string)
        }
    }
}