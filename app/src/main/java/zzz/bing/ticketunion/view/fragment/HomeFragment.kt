package zzz.bing.ticketunion.view.fragment

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import zzz.bing.ticketunion.BaseFragment
import zzz.bing.ticketunion.databinding.FragmentHomeBinding
import zzz.bing.ticketunion.utils.LogUtils
import zzz.bing.ticketunion.view.adapter.HomePagerAdapter
import zzz.bing.ticketunion.viewmodel.MainViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding, MainViewModel>() {

    private val _homePagerAdapter by lazy {
        HomePagerAdapter(requireActivity())
    }

    override fun initViewBinding(): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun initViewModel(): MainViewModel {
        return ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
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
            binding.loadingLayout.root.visibility =
                if (netState == MainViewModel.NetLoadState.Loading) View.VISIBLE else View.GONE
            binding.loadErrorLayout.root.visibility =
                if (netState == MainViewModel.NetLoadState.Error) View.VISIBLE else View.GONE
            binding.homePager.visibility =
                if (netState == MainViewModel.NetLoadState.Successful) View.VISIBLE else View.GONE
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