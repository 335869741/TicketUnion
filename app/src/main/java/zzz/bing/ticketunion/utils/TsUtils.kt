package zzz.bing.ticketunion.utils

import android.content.Context
import android.widget.Toast

object TsUtils {
    fun ts(context:Context,string: String){
        Toast.makeText(context,string, Toast.LENGTH_SHORT).show()
    }
    fun ts(context:Context,string: String,int: Int){
        Toast.makeText(context,string, int).show()
    }
}