package zzz.bing.ticketunion.model.action

/**
 * 跳转TicketActivity需要的数据
 */
interface ITicketActivity {
    fun getTitle(): String
    fun getUrl(): String
    fun getPic(): String
    fun isNoMore():Boolean
}