package zzz.bing.ticketunion.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create
import zzz.bing.ticketunion.model.Api
import zzz.bing.ticketunion.model.domain.TicketParcelable
import zzz.bing.ticketunion.model.domain.TicketTaoCode
import zzz.bing.ticketunion.model.domain.TicketTaoCodeParams
import zzz.bing.ticketunion.utils.LogUtils
import zzz.bing.ticketunion.utils.RetrofitManager
import zzz.bing.ticketunion.utils.UrlUtils
import java.net.HttpURLConnection

class TicketViewModel(application: Application) : AndroidViewModel(application) {
    private val _api = RetrofitManager.get().retrofit.create<Api>()
    private var _taoCode = MutableLiveData<String>()

    val taoCode: LiveData<String> get() = _taoCode

    //    private var
    fun load(ticketParcelable: TicketParcelable) {
        val url = UrlUtils.urlJoinHttp(ticketParcelable.url)
        val title = ticketParcelable.title
        val postTicketTaoCode = _api.postTicketTaoCode(TicketTaoCodeParams(url, title))
        postTicketTaoCode.enqueue(object : Callback<TicketTaoCode> {
            override fun onResponse(call: Call<TicketTaoCode>, response: Response<TicketTaoCode>) {
                val code = response.code()
                LogUtils.d(this@TicketViewModel, "code ==> $code")
                if (code == HttpURLConnection.HTTP_OK) {
                    val tao = response.body()?.TaoCodeCreate?.taoCodeCreateResponse?.taoCodeBody?.code
                    tao?.apply {
                        val index = this.indexOf("￥")
                        val last = this.lastIndexOf("￥")
                        val string = this.substring(index,last+1)
                        LogUtils.d(this@TicketViewModel, "code ==> $string")
                        _taoCode.postValue(string)
                    }
                } else {
                    TODO("Not yet implemented")
                }
            }

            override fun onFailure(call: Call<TicketTaoCode>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

}