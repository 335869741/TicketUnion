package zzz.bing.ticketunion.view.activity

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.setupWithNavController
import zzz.bing.ticketunion.BaseActivity
import zzz.bing.ticketunion.R
import zzz.bing.ticketunion.databinding.ActivityMainBinding
import zzz.bing.ticketunion.utils.LogUtils
import zzz.bing.ticketunion.viewmodel.MainViewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView() {
        //androidx.fragment.app.FragmentContainerView获取navController的方式
//        (supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment).findNavController()
        val navController = findNavigationById(R.id.navHostFragment)

//        //menu item id和 navigation fragment id必须一致
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    override fun initListener() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            LogUtils.d(this, "initListener: $item")
            findNavigationById(R.id.navHostFragment).popBackStack()
            findNavigationById(R.id.navHostFragment).navigate(item.itemId)
            return@setOnNavigationItemSelectedListener true
        }
    }

    override fun initViewModel(): MainViewModel {
        return ViewModelProvider(this).get(MainViewModel::class.java)
    }

}