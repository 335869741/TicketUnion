package zzz.bing.ticketunion.view.fragment

import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import zzz.bing.ticketunion.BaseFragment
import zzz.bing.ticketunion.databinding.FragmentChoicenessBinding
import zzz.bing.ticketunion.utils.LogUtils
import zzz.bing.ticketunion.utils.NetLoadState
import zzz.bing.ticketunion.view.adapter.ChoicenessCategoryAdapter
import zzz.bing.ticketunion.view.adapter.ChoicenessContentAdapter
import zzz.bing.ticketunion.viewmodel.MainViewModel

class ChoicenessFragment : BaseFragment<FragmentChoicenessBinding, MainViewModel>() {
    private val _categoryAdapter by lazy { ChoicenessCategoryAdapter(viewModel) }
    private val _contentAdapter by lazy { ChoicenessContentAdapter(requireActivity()) }
    private var _index = 0

    override fun initViewBinding(): FragmentChoicenessBinding {
        return FragmentChoicenessBinding.inflate(layoutInflater)
    }

    override fun initViewModel(): MainViewModel {
        val vm by viewModels<MainViewModel>()
        return vm
    }

    override fun initObserver() {
        viewModel.choicenessCategoryList.observe(viewLifecycleOwner, {list ->
            LogUtils.d(this,"list ==> $list")
            _categoryAdapter.submitList(list)
            viewModel.netLoadChoicenessContent(list[0].favoritesId)
        })
        viewModel.choicenessContentList.observe(viewLifecycleOwner, {
            _contentAdapter.submitList(it)
        })
        viewModel.choicenessPositionItem.observe(viewLifecycleOwner,{ item ->
            val favoritesId = viewModel.choicenessCategoryList.value?.get(item)?.favoritesId!!
            viewModel.netLoadChoicenessContent(favoritesId)
        })
        viewModel.choicenessContentNetState.observe(viewLifecycleOwner, {state ->
            binding.includeLoading.root.visibility =
                if (state == NetLoadState.Loading) View.VISIBLE else View.GONE
            binding.includeError.root.visibility =
                if (state == NetLoadState.Error) View.VISIBLE else View.GONE
            binding.recyclerContent.visibility =
                if (state == NetLoadState.Successful) View.VISIBLE else View.GONE
        })
    }

    override fun initListener() {
        binding.includeError.root.setOnClickListener {
            val favoritesId = viewModel.choicenessCategoryList.value?.get(_index)?.favoritesId!!
            viewModel.netLoadChoicenessContent(favoritesId)
        }
    }

    override fun initView() {
        binding.recyclerCategory.adapter = _categoryAdapter
        binding.recyclerCategory.layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerContent.adapter = _contentAdapter
        binding.recyclerContent.layoutManager = LinearLayoutManager(requireActivity())
    }

    override fun initData() {
        viewModel.netLoadChoicenessCategory()
    }
}