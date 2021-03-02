package zzz.bing.ticketunion.view.fragment

import androidx.lifecycle.ViewModelProvider
import zzz.bing.ticketunion.BaseFragment
import zzz.bing.ticketunion.databinding.FragmentRedpacketBinding
import zzz.bing.ticketunion.viewmodel.HomeViewModel

class RedPacketFragment : BaseFragment<FragmentRedpacketBinding, HomeViewModel>() {
    override fun initViewBinding(): FragmentRedpacketBinding {
        return FragmentRedpacketBinding.inflate(layoutInflater)
    }

    override fun initViewModel(): HomeViewModel {
        return ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
    }
}