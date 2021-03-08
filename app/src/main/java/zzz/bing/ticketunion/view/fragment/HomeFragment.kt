package zzz.bing.ticketunion.view.fragment

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import zzz.bing.ticketunion.BaseFragment
import zzz.bing.ticketunion.databinding.FragmentHomeBinding
import zzz.bing.ticketunion.utils.LogUtils
import zzz.bing.ticketunion.utils.NetLoadState
import zzz.bing.ticketunion.utils.NetLoadStateUtils
import zzz.bing.ticketunion.view.adapter.HomePagerAdapter
import zzz.bing.ticketunion.viewmodel.HomeViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    private val _homePagerAdapter by lazy {
        HomePagerAdapter(requireActivity())
    }

    override fun initViewBinding(): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun initViewModel(): HomeViewModel {
        return ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
    }

    override fun initView() {
    }

    override fun initListener() {
        binding.loadErrorLayout.root.setOnClickListener {
            viewModel.reLoadCategoryTitles()
        }
    }

    override fun initObserver() {
        viewModel.categoryTitleResponse.observe(viewLifecycleOwner, Observer { netState ->
            LogUtils.d(this, "Title加载状态改变 $netState")
            NetLoadStateUtils.viewStateChange(
                binding.loadingLayout.root,
                binding.loadErrorLayout.root,
                binding.homePager,
                netState
            )
        })

        viewModel.titles.observe(viewLifecycleOwner, Observer {
            LogUtils.d(this, "观察结果 titles == > $it")
            _homePagerAdapter.titles = ArrayList(it)
            binding.pager.adapter = _homePagerAdapter
            pagerContactTab()
        })
    }

    private fun pagerContactTab() {
        TabLayoutMediator(
            binding.tabLayout,
            binding.pager
        ) { tab, position ->
            _homePagerAdapter.titles[position].apply {
                tab.text = this.titleName
            }
        }.attach()
    }

}