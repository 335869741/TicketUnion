package zzz.bing.ticketunion

import android.app.Application
import android.content.Context

class BaseApplication :Application() {

    companion object{
        private var appContext:Context? = null
        fun getContext() = appContext!!
    }

    override fun onCreate() {
        super.onCreate()
        appContext = baseContext

//        RxTool.init(this)
    }
}