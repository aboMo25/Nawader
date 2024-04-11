package com.example.lastmusicapp

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.lastmusicapp.models.SongModel
import com.google.firebase.firestore.FirebaseFirestore
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

object MyExoplayer {
    private var exoplayer: ExoPlayer? = null
    private var currentSong: SongModel? = null

    fun getCurrentSong(): SongModel? {
        return currentSong
    }

    fun getInstance(): ExoPlayer? {
        return exoplayer
    }

    fun startPlaying(context: Context, song: SongModel) {
        if (exoplayer == null)
            exoplayer = ExoPlayer.Builder(context).build()

        if (currentSong != song) {
            currentSong = song
            updateCount()

            currentSong?.url?.apply {
                val mediaItem = MediaItem.fromUri(this)
                exoplayer?.setMediaItem(mediaItem)
                exoplayer?.prepare()
                exoplayer?.play()
            }
        }
    }

    fun updateCount() {
        currentSong?.id?.let { id ->
            FirebaseFirestore.getInstance().collection("songs")
                .document(id)
                .get().addOnSuccessListener {
                    var latestCount = it.getLong("count")
                    if (latestCount == null) {
                        latestCount = 1L
                    } else {
                        latestCount = latestCount + 1
                    }
                    FirebaseFirestore.getInstance().collection("songs")
                        .document(id)
                        .update(mapOf("count" to latestCount))
                }
        }
    }

    fun downloadAndSaveSong(context: Context, song: SongModel, callback: (Uri?) -> Unit) {
        Thread {
            var fileUri: Uri? = null
            try {
                val url = URL(song.url)
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.connect()

                val input: InputStream = BufferedInputStream(url.openStream(), 8192)

                val dir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).absolutePath + File.separator + "YourAppName")
                if (!dir.exists()) {
                    dir.mkdirs()
                }

                val outputFile = File(dir, "${song.title}.mp3")
                val output: OutputStream = FileOutputStream(outputFile)

                val data = ByteArray(1024)
                var count: Int
                while (input.read(data).also { count = it } != -1) {
                    output.write(data, 0, count)
                }

                output.flush()
                output.close()
                input.close()

                // Create a Uri for the saved file
                fileUri = Uri.fromFile(outputFile)
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle any errors that may occur during download
                Log.e("DownloadError", "Failed to download: ${e.message}")
            }
            // Invoke the callback with the fileUri
            callback.invoke(fileUri)
        }.start()
    }


}
