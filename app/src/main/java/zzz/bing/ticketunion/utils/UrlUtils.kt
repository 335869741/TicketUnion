package zzz.bing.ticketunion.utils

object UrlUtils {

    fun createCategoryItemUrl(materialId: Int, pager: Int): String {
        return "discovery/${materialId}/${pager}"
    }

    fun dynamicLoadingUrl(width: Int, height: Int, url: String): String {
        return if (width > height) {
            dynamicLoadingUrl(width, url)
        } else {
            dynamicLoadingUrl(height, url)
        }
    }

    fun dynamicLoadingUrl(long: Int, url: String): String {
        var size = (long % 100)+1
        if (size > 5){
            size = 5
        }else if (size < 1){
            size = 1
        }
        return "https:${url}_${size*100}x${size*100}.jpg"
    }
}