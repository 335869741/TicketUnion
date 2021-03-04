package zzz.bing.ticketunion.view.fragment

import androidx.lifecycle.ViewModelProvider
import zzz.bing.ticketunion.BaseFragment
import zzz.bing.ticketunion.databinding.FragmentSearchBinding
import zzz.bing.ticketunion.viewmodel.MainViewModel

class SearchFragment : BaseFragment<FragmentSearchBinding, MainViewModel>() {
    override fun initViewBinding(): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(layoutInflater)
    }

    override fun initViewModel(): MainViewModel {
        return ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }
}