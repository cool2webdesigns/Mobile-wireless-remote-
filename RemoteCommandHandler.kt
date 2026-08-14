package com.wirelessremote.receiver

import android.content.Context
import android.media.AudioManager
import android.view.KeyEvent

class RemoteCommandHandler(
    private val context: Context
) {

    fun execute(command: String) {

        when (command) {

            "VOLUME_UP" ->
                volumeUp()

            "VOLUME_DOWN" ->
                volumeDown()

            "MUTE" ->
                mute()

            "PLAY_PAUSE" ->
                mediaKey(
                    KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE
                )

            "NEXT" ->
                mediaKey(
                    KeyEvent.KEYCODE_MEDIA_NEXT
                )

            "PREVIOUS" ->
                mediaKey(
                    KeyEvent.KEYCODE_MEDIA_PREVIOUS
                )

            "FAST_FORWARD" ->
                mediaKey(
                    KeyEvent.KEYCODE_MEDIA_FAST_FORWARD
                )

            "REWIND" ->
                mediaKey(
                    KeyEvent.KEYCODE_MEDIA_REWIND
                )

            else -> {

                /*
                 * Navigation commands can be handled
                 * by your own receiver UI.
                 */

                println(
                    "Remote command: $command"
                )
            }
        }
    }

    private fun volumeUp() {

        val audio =
            context.getSystemService(
                Context.AUDIO_SERVICE
            ) as AudioManager

        audio.adjustVolume(
            AudioManager.ADJUST_RAISE,
            AudioManager.FLAG_SHOW_UI
        )
    }

    private fun volumeDown() {

        val audio =
            context.getSystemService(
                Context.AUDIO_SERVICE
            ) as AudioManager

        audio.adjustVolume(
            AudioManager.ADJUST_LOWER,
            AudioManager.FLAG_SHOW_UI
        )
    }

    private fun mute() {

        val audio =
            context.getSystemService(
                Context.AUDIO_SERVICE
            ) as AudioManager

        audio.adjustVolume(
            AudioManager.ADJUST_TOGGLE_MUTE,
            AudioManager.FLAG_SHOW_UI
        )
    }

    private fun mediaKey(key: Int) {

        val audio =
            context.getSystemService(
                Context.AUDIO_SERVICE
            ) as AudioManager

        audio.dispatchMediaKeyEvent(
            KeyEvent(
                KeyEvent.ACTION_DOWN,
                key
            )
        )

        audio.dispatchMediaKeyEvent(
            KeyEvent(
                KeyEvent.ACTION_UP,
                key
            )
        )
    }
}
