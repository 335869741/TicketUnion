package zzz.bing.ticketunion.model.action

import zzz.bing.ticketunion.model.domain.TicketParcelable

interface ITicketActivity {
    fun getTitle(): String
    fun getUrl(): String
    fun getPic(): String
    fun isNoMore():Boolean
}