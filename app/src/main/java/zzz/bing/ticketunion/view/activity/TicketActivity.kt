package zzz.bing.ticketunion.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import zzz.bing.ticketunion.BaseActivity
import zzz.bing.ticketunion.R
import zzz.bing.ticketunion.databinding.ActivityTicketBinding
import zzz.bing.ticketunion.viewmodel.TicketViewModel

class TicketActivity : BaseActivity<ActivityTicketBinding,TicketViewModel>() {

    override fun getViewBinding(): ActivityTicketBinding {
        return ActivityTicketBinding.inflate(layoutInflater)
    }

    override fun initViewModel(): TicketViewModel {
        val model by viewModels<TicketViewModel>()
        return model
    }
}