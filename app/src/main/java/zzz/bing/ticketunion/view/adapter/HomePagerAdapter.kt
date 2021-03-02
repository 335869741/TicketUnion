package zzz.bing.ticketunion.view.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import zzz.bing.ticketunion.model.domain.Title
import zzz.bing.ticketunion.utils.Constant
import zzz.bing.ticketunion.utils.LogUtils
import zzz.bing.ticketunion.view.fragment.HomePagerFragment

class HomePagerAdapter(private val activity: FragmentActivity) : FragmentStateAdapter(activity) {
    var titles = ArrayList<Title>()

    override fun getItemCount(): Int {
        return titles.size
    }

    override fun createFragment(position: Int): Fragment {
        return newInstance(titles[position])
    }

    private fun newInstance(title: Title): HomePagerFragment {
        val homePagerFragment = HomePagerFragment()
        val bundle = Bundle()
        bundle.putString(Constant.KEY_TITLE_NAME, title.titleName)
        bundle.putInt(Constant.KEY_TITLE_MATERIAL_ID, title.materialId)
        LogUtils.d(this,"$title")
        homePagerFragment.arguments = bundle
        return homePagerFragment
    }


}