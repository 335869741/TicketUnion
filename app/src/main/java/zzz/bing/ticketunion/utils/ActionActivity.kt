package zzz.bing.ticketunion.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import zzz.bing.ticketunion.model.action.ITicketActivity
import zzz.bing.ticketunion.model.domain.TicketParcelable
import zzz.bing.ticketunion.view.activity.TicketActivity

object ActionActivity {
    fun actionTicketActivity(context: Context,item: ITicketActivity){
        if (item.isNoMore()){
            TsUtils.ts(context,Constant.TOAST_PROMPT)
        }
        val bundle = Bundle()
        bundle.putParcelable(Constant.KEY_TICKET_PARCELABLE,
            TicketParcelable(item.getUrl(), item.getTitle(), item.getPic())
        )
        val intent = Intent(context, TicketActivity::class.java)
        intent.putExtras(bundle)
        context.startActivity(intent)
    }
}