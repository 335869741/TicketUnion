package zzz.bing.ticketunion.view.fragment

import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import zzz.bing.ticketunion.BaseFragment
import zzz.bing.ticketunion.databinding.FragmentHomePagerBinding
import zzz.bing.ticketunion.model.domain.ItemContent
import zzz.bing.ticketunion.utils.Constant
import zzz.bing.ticketunion.utils.LogUtils
import zzz.bing.ticketunion.view.adapter.HomePagerItemAdapter
import zzz.bing.ticketunion.viewmodel.MainViewModel
import java.util.ArrayList

class HomePagerFragment : BaseFragment<FragmentHomePagerBinding, MainViewModel>() {

    private var _title: String? = null
    private var _materialId: Int? = null
    private var _page: Int = 1
    private var _isLoading = false
    private lateinit var _homePagerItemAdapter: HomePagerItemAdapter
//    private lateinit var _homePagerItemLooperAdapter: HomePagerItemLooperAdapter

    override fun initViewBinding(): FragmentHomePagerBinding {
        return FragmentHomePagerBinding.inflate(layoutInflater)
    }

    override fun initViewModel(): MainViewModel {
        return ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun initView() {
        _homePagerItemAdapter = HomePagerItemAdapter(requireActivity())
        binding.pagerRecycler.adapter = _homePagerItemAdapter
        binding.pagerRecycler.layoutManager = LinearLayoutManager(requireActivity())
//        _homePagerItemLooperAdapter = HomePagerItemLooperAdapter()
//        binding.Pager2Looper.adapter = _homePagerItemLooperAdapter
        binding.refresh.setEnableLoadmore(true)
        binding.refresh.setEnableRefresh(false)
    }

    override fun initListener() {
        //网络异常，用户主动重新加载
        binding.loadErrorLayout.root.setOnClickListener {
            if (binding.loadErrorLayout.root.visibility == View.VISIBLE) {
                viewModel.netLoadCategoryItem(_materialId!!, _page)
                loadState(MainViewModel.NetLoadState.Loading)
            }
        }
        //用户主动加载更多
        binding.refresh.setOnRefreshListener(object : RefreshListenerAdapter(){
            override fun onLoadMore(refreshLayout: TwinklingRefreshLayout?) {
                LogUtils.d(this@HomePagerFragment,"loadMore")
                loadData()
            }
        })
    }

    override fun initObserver() {
        _materialId?.apply {
//        if (_materialId != null) {
            //网络数据更新
            viewModel.categoryItemLiveData.observe(viewLifecycleOwner, {
//                if (it[_materialId] != null) {
                it[_materialId]?.apply {
                    if (_page <= 2){
                        _homePagerItemAdapter.submitList(this)
                    }else{
                        val list = _homePagerItemAdapter.currentList
                        val item = ArrayList<ItemContent>()
                        item.addAll(list)
                        item.addAll(this)
                        _homePagerItemAdapter.submitList(item)
                        binding.refresh.finishLoadmore()
                        Toast.makeText(requireActivity(),"加载了${this.size}数据",Toast.LENGTH_SHORT).show()
                    }
                    _isLoading = false
                }
            })

            //网络加载事件改变
            viewModel.categoryItemResponse.observe(viewLifecycleOwner, {
                it[_materialId]?.apply {
                    loadState(this)
                }
//                if (it[_materialId] != null) {
//                    loadState(it[_materialId]!!)
//                }
            })
        }
    }

    override fun initData() {
        _title = arguments?.getString(Constant.KEY_TITLE_NAME)!!
        _materialId = arguments?.getInt(Constant.KEY_TITLE_MATERIAL_ID)!!
        LogUtils.d(this, "getTitle == > $_title <> getId == > $_materialId")
        loadData()
    }

    /**
     * 根据数据加载情况改变视图
     */
    private fun loadState(netState: MainViewModel.NetLoadState) {
        LogUtils.d(this,"page ==> $_page")
        if(_page <= 2){
            binding.loadingLayout.root.visibility =
            if (netState == MainViewModel.NetLoadState.Loading) View.VISIBLE else View.GONE
            binding.loadErrorLayout.root.visibility =
                if (netState == MainViewModel.NetLoadState.Error) View.VISIBLE else View.GONE
            binding.pagerRecycler.visibility =
                if (netState == MainViewModel.NetLoadState.Successful) View.VISIBLE else View.GONE
        }
        if(netState == MainViewModel.NetLoadState.Error){
            Toast.makeText(requireActivity(),"加载失败",Toast.LENGTH_SHORT).show()
            binding.refresh.finishLoadmore()
            if (_page > 1){
                _page--
            }
            return
        }
    }

    /**
     * 加载数据的方法
     */
    fun loadData(){
        if (_materialId != null && !_isLoading) {
            if (_materialId == 1){
                loadState(MainViewModel.NetLoadState.Loading)
            }
            viewModel.netLoadCategoryItem(_materialId!!, _page)
            _page++
            _isLoading = true
        }
    }

}