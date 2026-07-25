package com.example.todoapp.receiver

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Looper
import android.os.Handler


object AlarmSoundPlayer {

    private var mediaPlayer: MediaPlayer? = null

    private val handler =
        Handler(Looper.getMainLooper())

    private val stopRunnable = Runnable {
        stop()
    }

    fun play(
        songUrl: String,
        durationMillis: Long = 30_000L
    ) {
        stop()

        runCatching {
            val player = MediaPlayer()

            mediaPlayer = player

            player.setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(
                        AudioAttributes.USAGE_ALARM
                    )
                    .setContentType(
                        AudioAttributes.CONTENT_TYPE_MUSIC
                    )
                    .build()
            )

            player.setDataSource(songUrl)

            player.setOnPreparedListener {
                it.start()

                handler.postDelayed(
                    stopRunnable,
                    durationMillis
                )
            }

            player.setOnCompletionListener {
                stop()
            }

            player.setOnErrorListener { _, _, _ ->
                stop()
                true
            }

            player.prepareAsync()
        }.onFailure {
            stop()
        }
    }

    fun stop() {
        handler.removeCallbacks(stopRunnable)

        mediaPlayer?.let { player ->
            runCatching {
                if (player.isPlaying) {
                    player.stop()
                }
            }

            runCatching {
                player.release()
            }
        }

        mediaPlayer = null
    }

    fun isPlaying(): Boolean {
        return runCatching {
            mediaPlayer?.isPlaying == true
        }.getOrDefault(false)
    }
}