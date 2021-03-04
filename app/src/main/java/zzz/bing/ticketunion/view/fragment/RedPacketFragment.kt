package zzz.bing.ticketunion.view.fragment

import androidx.lifecycle.ViewModelProvider
import zzz.bing.ticketunion.BaseFragment
import zzz.bing.ticketunion.databinding.FragmentRedpacketBinding
import zzz.bing.ticketunion.viewmodel.MainViewModel

class RedPacketFragment : BaseFragment<FragmentRedpacketBinding, MainViewModel>() {
    override fun initViewBinding(): FragmentRedpacketBinding {
        return FragmentRedpacketBinding.inflate(layoutInflater)
    }

    override fun initViewModel(): MainViewModel {
        return ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }
}