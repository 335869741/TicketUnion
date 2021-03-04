package zzz.bing.ticketunion.view.fragment

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import zzz.bing.ticketunion.BaseFragment
import zzz.bing.ticketunion.databinding.FragmentChoicenessBinding
import zzz.bing.ticketunion.model.domain.ChoicenessCategory
import zzz.bing.ticketunion.model.domain.UatmTbkItem
import zzz.bing.ticketunion.view.adapter.ChoicenessCategoryAdapter
import zzz.bing.ticketunion.view.adapter.ChoicenessContentAdapter
import zzz.bing.ticketunion.viewmodel.HomeViewModel

class ChoicenessFragment : BaseFragment<FragmentChoicenessBinding, HomeViewModel>() {
    private val _categoryAdapter by lazy { ChoicenessCategoryAdapter() }
    private val _contentAdapter by lazy { ChoicenessContentAdapter() }
    override fun initViewBinding(): FragmentChoicenessBinding {
        return FragmentChoicenessBinding.inflate(layoutInflater)
    }

    override fun initViewModel(): HomeViewModel {
        return ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
    }

    override fun initView() {
        binding.recyclerCategory.adapter = _categoryAdapter
        binding.recyclerCategory.layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerContent.adapter = _contentAdapter
        binding.recyclerContent.layoutManager = LinearLayoutManager(requireActivity())
    }

    override fun initData() {
        val category = listOf(
            ChoicenessCategory(1,"111",1),
            ChoicenessCategory(2,"222",1),
            ChoicenessCategory(3,"333",1),
            ChoicenessCategory(4,"444",1),
            ChoicenessCategory(5,"555",1)
        )
        _categoryAdapter.submitList(category)
        val content = listOf(
            UatmTbkItem(),
            UatmTbkItem(),
            UatmTbkItem(),
            UatmTbkItem(),
            UatmTbkItem()
        )
        _contentAdapter.submitList(content)
    }
}