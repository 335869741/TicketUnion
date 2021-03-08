package zzz.bing.ticketunion.utils

import android.view.View

enum class NetLoadState {
    Loading,
    Error,
    Successful
}

object NetLoadStateUtils {

    @Synchronized
    fun viewStateChange(loading: View, error: View, success: View, state: NetLoadState) {
        loading.visibility = if (state == NetLoadState.Loading) View.VISIBLE else View.GONE
        error.visibility = if (state == NetLoadState.Error) View.VISIBLE else View.GONE
        success.visibility = if (state == NetLoadState.Successful) View.VISIBLE else View.GONE
    }
}