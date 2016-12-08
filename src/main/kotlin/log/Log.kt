@file:JvmName("Log")
package log

/**
 * @author kitttn
 */

class Log {
    companion object {
        @JvmStatic
        fun i(tag: String, data: Any) {
            println("$tag: $data")
        }

        @JvmStatic @JvmOverloads
        fun e(tag: String, data: Any, throwable: Throwable? = null) {
            println("$tag: $data")
            throwable?.printStackTrace()
        }
    }
}