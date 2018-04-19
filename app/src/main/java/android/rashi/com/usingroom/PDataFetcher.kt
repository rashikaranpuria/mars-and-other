package android.rashi.com.usingroom

import android.util.Log
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.data.DataFetcher
import com.bumptech.glide.load.model.GlideUrl
import java.io.InputStream
import java.net.HttpURLConnection


class PDataFetcher(val model: GlideUrl): DataFetcher<InputStream> {
    override fun getDataClass(): Class<InputStream> = InputStream::class.java

    override fun cleanup() {
        // Clean up input stream -
    }

    override fun getDataSource(): DataSource = DataSource.REMOTE

    override fun cancel() {
        // Cancel ongoing load -
    }

    override fun loadData(priority: Priority, callback: DataFetcher.DataCallback<in InputStream>) {
        try {
            val imageUrl = model.toURL()
            val conn = imageUrl.openConnection() as HttpURLConnection
            conn.setConnectTimeout(30000)
            conn.setReadTimeout(30000)
            val iS = conn.getInputStream()
            var current = iS.read()
            var str = ""
            Log.d("In PDataFetcher", "starting...")
            while (current != -1) {
//                Log.d("reading...", Integer.toHexString(current))
                str +=Integer.toHexString(current)
                current = iS.read()

            }
            Log.d("In PDataFetcher,FINAL:", str)
            callback.onDataReady(null)
            Log.d("Works", "dhsddbhsd")
        } catch (ex: Exception) {
            ex.printStackTrace()
        }


        Log.d("In PDataFetcher", model.toStringUrl())
//        callback.onDataReady(null)
    }
}