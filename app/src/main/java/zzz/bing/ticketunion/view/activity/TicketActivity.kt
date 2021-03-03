package zzz.bing.ticketunion.view.activity

import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import zzz.bing.ticketunion.BaseActivity
import zzz.bing.ticketunion.R
import zzz.bing.ticketunion.databinding.ActivityTicketBinding
import zzz.bing.ticketunion.model.domain.TicketParcelable
import zzz.bing.ticketunion.utils.Constant
import zzz.bing.ticketunion.utils.LogUtils
import zzz.bing.ticketunion.utils.UrlUtils
import zzz.bing.ticketunion.viewmodel.TicketViewModel
import java.lang.Exception

class TicketActivity : BaseActivity<ActivityTicketBinding, TicketViewModel>() {
    private var _isTaobaoInstall = false

    override fun getViewBinding(): ActivityTicketBinding {
        return ActivityTicketBinding.inflate(layoutInflater)
    }

    override fun initViewModel(): TicketViewModel {
        val model by viewModels<TicketViewModel>()
        return model
    }

    override fun initView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = "淘口令"
    }

    override fun initData() {
        val bundle = intent.extras!!
        val ticketParcelable =
            bundle.getParcelable<TicketParcelable>(Constant.KEY_TICKET_PARCELABLE)
        viewModel.load(ticketParcelable!!)
        val url = UrlUtils.urlJoinHttp(ticketParcelable.pic)
        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.ic_photo_placeholder)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progressBar2.visibility = View.GONE
                    return false
                }
            })
            .into(binding.imageTaoCode)

        try {
            val packageInfo =
                packageManager.getPackageInfo("", PackageManager.MATCH_UNINSTALLED_PACKAGES)
            _isTaobaoInstall = packageInfo != null
            LogUtils.d(this,"packageInfo == > $packageInfo")
        }catch (e :PackageManager.NameNotFoundException){
            e.printStackTrace()
            _isTaobaoInstall = false
        }
        LogUtils.d(this,"_isTaobaoInstall == > $_isTaobaoInstall")
        binding.buttonTaoCode.text = if (_isTaobaoInstall) "打开淘宝领券" else "复制淘口令"
    }

    @Suppress("RedundantSamConstructor")
    override fun initObserver() {
        viewModel.taoCode.observe(this, Observer { string ->
            binding.editTaoCode.setText(string)
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}