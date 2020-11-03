package com.wutsi.core.translate

interface TranslateService {
    fun translate(fromLanguage: String?, toLanguage: String, text: String): String
}
