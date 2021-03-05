package zzz.bing.ticketunion.view.fragment

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import zzz.bing.ticketunion.BaseFragment
import zzz.bing.ticketunion.databinding.FragmentOnSellBinding
import zzz.bing.ticketunion.utils.LogUtils
import zzz.bing.ticketunion.utils.NetLoadState
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
            LogUtils.d(this ,"list ==> $list")
            if (viewModel.onSellPage == 1){
                _onSellAdapter.submitList(list)
            }else{
                val allList = _onSellAdapter.currentList
                allList.addAll(list)
                _onSellAdapter.submitList(allList)
            }
        })
        viewModel.onSellNetState.observe(viewLifecycleOwner, {state ->
            binding.includeLoading.root.visibility = if (state == NetLoadState.Loading) View.VISIBLE else View.GONE
            binding.includeError.root.visibility = if (state == NetLoadState.Error) View.VISIBLE else View.GONE
            binding.constraint.visibility = if (state == NetLoadState.Successful) View.VISIBLE else View.GONE
        })
    }

    override fun initView() {
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(binding.includeTitle.toolbarTitle)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        activity.supportActionBar?.setHomeButtonEnabled(true)
        activity.supportActionBar?.title = "每日特惠"

        binding.recyclerOnSell.adapter = _onSellAdapter
        binding.recyclerOnSell.layoutManager = GridLayoutManager(view?.context,2)
    }

    override fun initListener() {
        binding.includeError.root.setOnClickListener {
            if (viewModel.onSellNetState.value == NetLoadState.Error){
                viewModel.loadOnSell()
            }
        }
    }
}