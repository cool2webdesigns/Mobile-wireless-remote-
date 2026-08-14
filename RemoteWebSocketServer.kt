package com.wirelessremote.receiver

import android.content.Context
import java.net.Inet4Address
import java.net.NetworkInterface
import java.util.Collections
import kotlin.random.Random

class RemoteWebSocketServer(
    private val context: Context,
    private val onStatus: (String) -> Unit
) {

    private val port = 8080

    private val pairingPin =
        Random.nextInt(100000, 999999).toString()

    private var running = false

    private var thread: Thread? = null

    fun start() {

        if (running) return

        running = true

        onStatus("Receiver running")

        thread = Thread {

            try {

                /*
                 * The production implementation should use
                 * a WebSocket server library such as OkHttp.
                 */

                while (running) {

                    Thread.sleep(1000)

                }

            } catch (_: Exception) {

            }

        }

        thread?.start()
    }

    fun stop() {

        running = false

        thread?.interrupt()

        thread = null

        onStatus("Receiver stopped")
    }

    fun getPairingPin(): String {
        return pairingPin
    }

    fun getLocalIp(): String {

        try {

            val interfaces =
                Collections.list(
                    NetworkInterface.getNetworkInterfaces()
                )

            for (networkInterface in interfaces) {

                val addresses =
                    Collections.list(
                        networkInterface.inetAddresses
                    )

                for (address in addresses) {

                    if (!address.isLoopbackAddress &&
                        address is Inet4Address
                    ) {

                        return address.hostAddress ?: "127.0.0.1"

                    }
                }
            }

        } catch (_: Exception) {

        }

        return "127.0.0.1"
    }
}
