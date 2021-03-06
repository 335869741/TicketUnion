package zzz.bing.ticketunion.view.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import zzz.bing.ticketunion.BaseFragment
import zzz.bing.ticketunion.databinding.FragmentSearchBinding
import zzz.bing.ticketunion.viewmodel.HomeViewModel
import zzz.bing.ticketunion.viewmodel.SearchViewModel

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
    }
}