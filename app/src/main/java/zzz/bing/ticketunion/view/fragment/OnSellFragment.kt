package zzz.bing.ticketunion.view.fragment

import androidx.lifecycle.ViewModelProvider
import zzz.bing.ticketunion.BaseFragment
import zzz.bing.ticketunion.databinding.FragmentOnSellBinding
import zzz.bing.ticketunion.viewmodel.MainViewModel

class OnSellFragment : BaseFragment<FragmentOnSellBinding, MainViewModel>() {
    override fun initViewBinding(): FragmentOnSellBinding {
        return FragmentOnSellBinding.inflate(layoutInflater)
    }

    override fun initViewModel(): MainViewModel {
        return ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }
}