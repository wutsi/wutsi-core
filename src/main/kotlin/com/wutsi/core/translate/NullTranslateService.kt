package com.wutsi.core.translate

class NullTranslateService: TranslateService {
    override fun translate(fromLanguage: String?, toLanguage: String, text: String): String =
            text
}
