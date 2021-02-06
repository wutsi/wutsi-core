package com.wutsi.core.logging

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.Collections
import java.util.Optional
import java.util.StringJoiner


open class KVLoggerImpl (
        private val logger: Logger = LOGGER,
        private val encoder: LoggerEncoder = LoggerEncoder()
) : KVLogger {
    companion object {
        private val LOGGER = LoggerFactory.getLogger(KVLoggerImpl::class.java)
        val EXCEPTION = "Exception"
        val EXCEPTION_MESSAGE = "ExceptionMessage"
        val KVPAIR_SEPARATOR = "="
        val ITEM_SEPARATOR = " "

        val MAX_LENGTH = 10000
    }

    val data: MutableMap<String, MutableList<String>> = Collections.synchronizedMap(mutableMapOf())

    override fun log() {
        if (data.isEmpty())
            return

        logger.info(toString())
        data.clear()
    }

    override fun log(ex: Throwable) {
        add(EXCEPTION, ex.javaClass.name)
        add(EXCEPTION_MESSAGE, ex.message)
        logger.error(toString(), ex)
        data.clear()
    }

    override fun add(key: String, value: String?) {
        if (value == null){
            return
        }

        if (!data.containsKey(key)) {
            data[key] = mutableListOf()
        }
        data[key]?.add(value)
    }

    override fun add(key: String, value: Long?) {
        add(key, value?.toString())
    }

    override fun add(key: String, value: Double?) {
        add(key, value?.toString())
    }

    override fun add(key: String, value: Optional<Any>) {
        if (value.isPresent) {
            add(key, value.get())
        }
    }

    override fun add(key: String, values: Collection<Any>?) {
        if (values == null) {
            return
        }

        for (value in values) {
            add(key, value)
        }
    }

    override fun add(key: String, value: Any?) {
        add(key, value?.toString())
    }

    override fun toString(): String {
        /* iterate over keys and define the order in which it should get logged */
        val keys = data.keys.sorted()

        /* convert to string */
        val buffer = StringBuilder()
        for (key in keys) {
            val value: List<String> = data[key] ?: continue

            val encodedValue = encoder.encode(toString(value))
            if (encodedValue != null) {
                if (buffer.isNotEmpty()) {
                    buffer.append(ITEM_SEPARATOR)
                }

                buffer.append(key).append(KVPAIR_SEPARATOR).append(encodedValue)
            }
        }

        // Put the stacktrace at the end!
        val str = buffer.toString()
        return if (str.length > MAX_LENGTH) str.substring(0, MAX_LENGTH) else str
    }

    private fun toString(values: List<String>): String {
        if (values.size == 1) {
            return values[0]
        } else {
            val joiner = StringJoiner(ITEM_SEPARATOR)
            for (value in values) {
                joiner.add(value)
            }
            return joiner.toString()
        }
    }
}
