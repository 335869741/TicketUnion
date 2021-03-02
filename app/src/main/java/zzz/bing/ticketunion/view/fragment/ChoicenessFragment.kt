package zzz.bing.ticketunion.view.fragment

import androidx.lifecycle.ViewModelProvider
import zzz.bing.ticketunion.BaseFragment
import zzz.bing.ticketunion.databinding.FragmentChoicenessBinding
import zzz.bing.ticketunion.viewmodel.HomeViewModel

class ChoicenessFragment : BaseFragment<FragmentChoicenessBinding, HomeViewModel>() {

    override fun initViewBinding(): FragmentChoicenessBinding {
        return FragmentChoicenessBinding.inflate(layoutInflater)
    }

    override fun initViewModel(): HomeViewModel {
        return ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
    }

}