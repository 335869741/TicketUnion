package zzz.bing.ticketunion

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<T : ViewBinding, V : ViewModel> : AppCompatActivity() {

    protected lateinit var binding: T
    protected lateinit var viewModel: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        viewModel = initViewModel()
        initView()
        initListener()
        initObserver()
        initData()
        setContentView(binding.root)
    }

    protected open fun initObserver(){

    }

    protected open fun initListener() {

    }

    protected open fun initView() {

    }

    protected open fun initData() {

    }

    protected abstract fun getViewBinding(): T
    protected abstract fun initViewModel(): V

    protected fun findNavigationById(@IdRes viewId: Int): NavController {
        val navHostFragment = supportFragmentManager.findFragmentById(viewId) as NavHostFragment
        return navHostFragment.navController
    }
}