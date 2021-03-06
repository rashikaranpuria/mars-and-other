package android.rashi.com.usingroom

import android.util.Log
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.data.DataFetcher
import com.bumptech.glide.load.model.GlideUrl
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.net.HttpURLConnection


class PDataFetcher(private val model: GlideUrl): DataFetcher<InputStream> {

    lateinit var iS:InputStream
    var bArrL = ArrayList<Byte>()

    var inputStreamFinished: Boolean = false

    val M_PAD = "0"   /* Padding byte used when 0xFF is found in compressed data */
    val M_RST0 = "d0"   /* Restart Markers 0-7, related to DRI */
    val M_RST1 = "d1"
    val M_RST2 = "d2"
    val M_RST3 = "d3"
    val M_RST4 = "d4"
    val M_RST5 = "d5"
    val M_RST6 = "d6"
    val M_RST7 = "d7"
    val M_SOI = "d8"   /* Start of Image */
    val M_EOI = "d9"   /* End of Image */
    val M_SOS = "da"   /* Start of Scan (size is not known) */
    val M_DRI = "dd"   /* Define Restart Interval */
    val M_FF = "ff"
    
    override fun getDataClass(): Class<InputStream> = InputStream::class.java

    override fun cleanup() {
        // Clean up input stream -
    }

    override fun getDataSource(): DataSource = DataSource.REMOTE

    override fun cancel() {
        // Cancel ongoing load -
        iS.close()
    }

    fun Int.hex(): String = Integer.toHexString(this)

    override fun loadData(priority: Priority, callback: DataFetcher.DataCallback<in InputStream>) {

        Log.d("In PDataFetcher", "maybe...")
        // open image stream
            Log.d("In PDataFetcher", "starting...")
            val imageUrl = model.toURL()
            val conn = imageUrl.openConnection() as HttpURLConnection
            conn.connectTimeout = 30000
            conn.readTimeout = 30000
            iS = conn.inputStream
            // check if valid image
            if (readOne().hex() + readOne().hex() != M_FF + M_SOI) {
                // Deal with image not valid
            }
            var marker: Int
            loop@ do {
                Log.d("In PDataFetcher", "doing...")
                marker = nextMarker()
                if(marker == -1) break@loop
                if (marker.hex() == M_SOS) {
                    // add the skip SOS code here
                    val sm = skipSos()
                    if (sm != 0) {
                        val skip2 = bArrL.removeLast()
                        val skip1 = bArrL.removeLast()
                        bArrL.add(255.toByte())
                        bArrL.add(217.toByte())
                        val os = ByteArrayInputStream(bArrL.toByteArray())
                        bArrL.removeLastTwo()
                        bArrL.add(skip1)
                        bArrL.add(skip2)
                        Log.d("In PDataFetcher", "skipLastTwo...")
                        try {
                            callback.onDataReady(os)
                        }
                        catch (e: Exception) {
                            Log.d("In PDataFetcher", "error...")
                            e.printStackTrace()
                        }
                    } else if (sm == -1){
                        // handling condition that input stream is over
                        break@loop
                    } else {
                        bArrL.add(255.toByte())
                        bArrL.add(217.toByte())
                        val os = ByteArrayInputStream(bArrL.toByteArray())
                        bArrL.removeLastTwo()
                        Log.d("In PDataFetcher", "no skiplastTwo...")
                        try {
                            callback.onDataReady(os)
                        }
                        catch (e: Exception) {
                            Log.d("In PDataFetcher", "error...")
                            e.printStackTrace()
                        }
                    }
                } else if (marker.hex() != M_EOI){
                    skipMarker()
                }
            } while (marker.hex() != M_EOI)

            bArrL.add(255.toByte())
            bArrL.add(217.toByte())
            val os = ByteArrayInputStream(bArrL.toByteArray())
            bArrL.removeLastTwo()
            Log.d("In PDataFetcher", "no skiplastTwo...")
            try {
                callback.onDataReady(os)
            }
            catch (e: Exception) {
                Log.d("In PDataFetcher", "error...")
                e.printStackTrace()
            }
    }

    fun ArrayList<Byte>.removeLast(): Byte {
        Log.d("In PDataFetcher", "remove last...")
        val ret = this.last()
        this.removeAt(this.size - 1)
        return ret
    }

    fun ArrayList<Byte>.removeLastTwo() {
        Log.d("In PDataFetcher", "remove last two...")
        this.removeAt(this.size - 1)
        this.removeAt(this.size - 1)
    }

    fun readTwo(): Int {
        val c1 = readOne()
        val c2 = readOne()

        if (c1 == -1 || c2 == -1)
            return -1

        val c3 = c1 shl 8
        val cur = c3 + c2
        Log.d("In PDataFetcher", "read two..." + c1.hex() + " " +c2.hex() + " c3: " + c3)
        return cur
    }

    fun readOne(): Int {
        val c = iS.read()
        if (c != -1)
            bArrL.add(c.toByte())

        Log.d("In PDataFetcher", "read one..." + c.hex())
        return c
    }

    fun nextMarker(): Int {

        var marker = readOne()
        while (marker.hex() != M_FF && marker != -1) {
            marker = readOne()
        }

        if (marker != -1) {
            do{
                marker = readOne()
            }
            while (marker.hex() == M_FF && marker != -1)
        }


        Log.d("In PDataFetcher", "next marker..." + marker)
        return marker
    }

    fun skipMarker() {
        Log.d("In PDataFetcher", "skip marker...")
        var len = readTwo() - 2
        var cur = readOne()
        Log.d("In PDataFetcher", "skip marker len: " + len + "cur: " + cur)
        while (len > 0 && cur != -1) {
            len--
            // store cur somewhere
            cur = readOne()
        }
        Log.d("In PDataFetcher", "last read..." + bArrL.last())
    }

    fun skipSos(): Int {
        Log.d("In PDataFetcher", "skip sos...")
        var cur: Int
        while (true) {
            cur = readOne()
            while (cur.hex() != M_FF && cur != -1) {
                cur = readOne()
            }

            if (cur != -1) {
                do{
                    cur = readOne()
                }
                while (cur.hex() == M_FF && cur != -1)
            }

            when (cur.hex()) {
                M_RST0-> cur = 0
                M_RST1-> cur = 0
                M_RST2-> cur = 0
                M_RST3-> cur = 0
                M_RST4-> cur = 0
                M_RST5-> cur = 0
                M_RST6-> cur = 0
                M_RST7-> cur = 0
            }
            if (cur != 0) {
                break
            }
        }
        return cur
    }
}