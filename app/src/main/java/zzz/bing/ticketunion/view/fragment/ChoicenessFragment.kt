package zzz.bing.ticketunion.view.fragment

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import zzz.bing.ticketunion.BaseFragment
import zzz.bing.ticketunion.databinding.FragmentChoicenessBinding
import zzz.bing.ticketunion.utils.LogUtils
import zzz.bing.ticketunion.utils.NetLoadState
import zzz.bing.ticketunion.view.adapter.ChoicenessCategoryAdapter
import zzz.bing.ticketunion.view.adapter.ChoicenessContentAdapter
import zzz.bing.ticketunion.viewmodel.ChoicenessViewModel

class ChoicenessFragment : BaseFragment<FragmentChoicenessBinding, ChoicenessViewModel>() {
    private val _categoryAdapter by lazy { ChoicenessCategoryAdapter(viewModel) }
    private val _contentAdapter by lazy { ChoicenessContentAdapter(requireActivity()) }
    private var _index = 0

    override fun initViewBinding(): FragmentChoicenessBinding {
        return FragmentChoicenessBinding.inflate(layoutInflater)
    }

    override fun initViewModel(): ChoicenessViewModel {
        val vm by viewModels<ChoicenessViewModel>()
        return vm
    }

    override fun initObserver() {
        viewModel.choicenessCategoryList.observe(viewLifecycleOwner, {list ->
            LogUtils.d(this,"list ==> $list")
            _categoryAdapter.submitList(list)
            viewModel.netLoadChoicenessContent(list[0].favoritesId)
        })
        viewModel.choicenessContentList.observe(viewLifecycleOwner, {list ->
            LogUtils.d(this,"list ==> $list")
            _contentAdapter.submitList(list)
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
//        val activity = requireActivity() as AppCompatActivity
//        activity.setSupportActionBar(binding.includeTitle.toolbarTitle)
//        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        activity.supportActionBar?.setHomeButtonEnabled(true)
//        activity.supportActionBar?.title = "每日特惠"

        binding.recyclerCategory.adapter = _categoryAdapter
        binding.recyclerCategory.layoutManager = LinearLayoutManager(view?.context)
        binding.recyclerContent.adapter = _contentAdapter
        binding.recyclerContent.layoutManager = LinearLayoutManager(view?.context)
    }

    override fun initData() {
        viewModel.netLoadChoicenessCategory()
    }
}