package zzz.bing.ticketunion.view.fragment

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import zzz.bing.ticketunion.BaseFragment
import zzz.bing.ticketunion.databinding.FragmentOnSellBinding
import zzz.bing.ticketunion.utils.LogUtils
import zzz.bing.ticketunion.utils.NetLoadState
import zzz.bing.ticketunion.utils.NetLoadStateUtils
import zzz.bing.ticketunion.view.adapter.OnSellAdapter
import zzz.bing.ticketunion.viewmodel.OnSellViewModel

class OnSellFragment : BaseFragment<FragmentOnSellBinding, OnSellViewModel>() {
    private val _onSellAdapter by lazy { OnSellAdapter() }

    override fun initViewBinding(): FragmentOnSellBinding {
        return FragmentOnSellBinding.inflate(layoutInflater)
    }

    override fun initViewModel(): OnSellViewModel {
        val vm by viewModels<OnSellViewModel>()
        return vm
    }

    override fun initObserver() {
        viewModel.onSellMapData.observe(viewLifecycleOwner, { list ->
            LogUtils.d(this, "list ==> $list")
            if (viewModel.onSellPage == 1) {
                _onSellAdapter.submitList(list)
            } else {
                val allList = _onSellAdapter.currentList
                allList.addAll(list)
                _onSellAdapter.submitList(allList)
                binding.refresh.finishLoadmore()
            }
        })
        viewModel.onSellNetState.observe(viewLifecycleOwner, {
            val state = if (it == NetLoadState.Loading && viewModel.onSellPage != 1) {
                NetLoadState.Successful
            } else {
                it
            }
            NetLoadStateUtils.viewStateChange(
                binding.includeLoading.root,
                binding.includeError.root,
                binding.constraint,
                state
            )
        })
    }

    override fun initView() {
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(binding.includeTitle.toolbarTitle)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        activity.supportActionBar?.setHomeButtonEnabled(true)
        activity.supportActionBar?.title = "每日特惠"

        binding.refresh.setEnableLoadmore(true)
        binding.refresh.setEnableRefresh(false)

        binding.recyclerOnSell.adapter = _onSellAdapter
        binding.recyclerOnSell.layoutManager = GridLayoutManager(view?.context, 2)
    }

    override fun initListener() {
        binding.includeError.root.setOnClickListener {
            if (viewModel.onSellNetState.value == NetLoadState.Error) {
                viewModel.loadOnSell()
            }
        }
        binding.refresh.setOnRefreshListener(object : RefreshListenerAdapter() {
            override fun onLoadMore(refreshLayout: TwinklingRefreshLayout?) {
                if (viewModel.onSellNetState.value == NetLoadState.Loading) {
                    return
                }
                viewModel.loadOnSell()
            }
        })
    }
}