package com.wirelessremote.receiver

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var statusText: TextView
    private lateinit var addressText: TextView
    private lateinit var pinText: TextView

    private var server: RemoteWebSocketServer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        statusText = findViewById(R.id.statusText)
        addressText = findViewById(R.id.addressText)
        pinText = findViewById(R.id.pinText)

        startReceiver()

        findViewById<Button>(R.id.stopButton).setOnClickListener {
            stopReceiver()
        }

        findViewById<Button>(R.id.startButton).setOnClickListener {
            startReceiver()
        }
    }

    private fun startReceiver() {

        if (server != null) return

        server = RemoteWebSocketServer(
            context = this,
            onStatus = { status ->
                runOnUiThread {
                    statusText.text = status
                }
            }
        )

        server?.start()

        addressText.text =
            "ws://${server?.getLocalIp()}:8080"

        pinText.text =
            "PIN: ${server?.getPairingPin()}"
    }

    private fun stopReceiver() {

        server?.stop()

        server = null

        statusText.text = "Receiver stopped"
        addressText.text = "Not running"
    }

    override fun onDestroy() {
        server?.stop()
        super.onDestroy()
    }
}
