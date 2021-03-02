package zzz.bing.ticketunion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding, V : ViewModel> : Fragment() {
    protected lateinit var binding: T
    protected lateinit var viewModel: V

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = initViewBinding()
        viewModel = initViewModel()
        initView()
        initData()
        initListener()
        initObserver()
        return binding.root
    }

    protected open fun initObserver() {

    }


    protected open fun initListener() {

    }

    protected open fun initView() {

    }

    protected open fun initData() {

    }

    protected abstract fun initViewModel(): V
    protected abstract fun initViewBinding(): T
}