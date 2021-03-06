package zzz.bing.ticketunion.view.activity

import android.content.*
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
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
import zzz.bing.ticketunion.utils.*
import zzz.bing.ticketunion.viewmodel.TicketViewModel
class TicketActivity : BaseActivity<ActivityTicketBinding, TicketViewModel>() {
    private var _isTaobaoInstall = false
    private var ticketParcelable:TicketParcelable? = null

    override fun getViewBinding(): ActivityTicketBinding {
        return ActivityTicketBinding.inflate(layoutInflater)
    }

    override fun initViewModel(): TicketViewModel {
        val model by viewModels<TicketViewModel>()
        return model
    }

    override fun initView() {
        setSupportActionBar(binding.includeToolbar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = "淘口令"
//        binding.includeToolbar.toolbar.title = "淘口令"
    }

    override fun initData() {
        val bundle = intent.extras!!
        ticketParcelable = bundle.getParcelable(Constant.KEY_TICKET_PARCELABLE)
        loadData()

        try {
            val packageInfo =
                packageManager.getPackageInfo(Constant.TAOBAO_PAGE_NAME, PackageManager.MATCH_UNINSTALLED_PACKAGES)
            _isTaobaoInstall = packageInfo != null
            LogUtils.d(this,"packageInfo == > $packageInfo")
        }catch (e :PackageManager.NameNotFoundException){
            e.printStackTrace()
            _isTaobaoInstall = false
        }
        LogUtils.d(this,"_isTaobaoInstall == > $_isTaobaoInstall")
        binding.buttonTaoCode.text = if (_isTaobaoInstall) "打开淘宝领券" else "复制淘口令"
    }

    override fun initListener() {
        binding.buttonTaoCode.setOnClickListener {
            val taoCode = binding.editTaoCode.text.trim()
            LogUtils.d(this,"taoCode ==> $taoCode")
            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText(Constant.LABEL_COPY_TAO_CODE,taoCode)
            clipboardManager.setPrimaryClip(clipData)
            if (_isTaobaoInstall){
                val intent = Intent()
                val componentName = ComponentName(Constant.TAOBAO_PAGE_NAME,Constant.TAOBAO_WELCOME_PKG_NAME)
                intent.component = componentName
                startActivity(intent)
            }else{
                TsUtils.ts(this,"复制成功，用打开淘宝使用")
            }
        }
        binding.includeError.root.setOnClickListener {
            if (viewModel.netState.value == NetLoadState.Error){
                loadData()
            }
        }
    }

    @Suppress("RedundantSamConstructor")
    override fun initObserver() {
        viewModel.taoCode.observe(this, Observer { string ->
            binding.editTaoCode.setText(string)
        })
        viewModel.netState.observe(this,{state ->
            NetLoadStateUtils.viewStateChange(
                binding.includeLoading.root,
                binding.includeError.root,
                binding.constraint,
                state
            )
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun loadData(){
        viewModel.load(ticketParcelable!!)
        val url = UrlUtils.urlJoinHttp(ticketParcelable!!.pic)
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
    }
}