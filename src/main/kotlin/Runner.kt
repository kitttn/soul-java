import java.util.*

/**
 * @author kitttn
 */

val menu = arrayOf("Authorize", "Turn search on", "Turn search off", "Like user")

fun main(args: Array<String>) {
    val sdk = SdkImpl()
    var running = true
    val sc = Scanner(System.`in`)

    while (running) {
        printMenu()
        val num = sc.nextInt()
        when (num) {
            1 -> sdk.authorize()
            2 -> sdk.turnSearchOn()
            3 -> sdk.turnSearchOff()
            4 -> {
                sc.nextLine()
                val id = sc.nextLine()
                sdk.likeUser(id)
            }
            0 -> running = false
        }
    }
}

fun printMenu() {
    for (i in 1..menu.size)
        println("$i: ${menu[i - 1]}")
    println("0: Exit")
}