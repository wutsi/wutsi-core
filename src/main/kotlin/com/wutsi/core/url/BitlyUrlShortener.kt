package com.wutsi.core.url

import net.swisstech.bitly.BitlyClient
import net.swisstech.bitly.model.Response
import net.swisstech.bitly.model.v3.ShortenResponse

class BitlyUrlShortener(private val accessToken: String) : UrlShortener {
    override fun shorten(url: String): String {
        val client = BitlyClient(accessToken)
        val resp: Response<ShortenResponse> = client.shorten()
            .setLongUrl(url)
            .call()

        if (resp.status_code != 200)
            throw UrlShortenerException(resp.status_txt)

        return resp.data.url
    }
}
