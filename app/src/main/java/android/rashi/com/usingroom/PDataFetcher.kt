package android.rashi.com.usingroom

import android.os.Build.VERSION_CODES.M
import android.util.Log
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.data.DataFetcher
import com.bumptech.glide.load.model.GlideUrl
import timber.log.Timber
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class PDataFetcher(private val model: GlideUrl): DataFetcher<InputStream> {

    lateinit var iS:InputStream
    var bArrL = ArrayList<Byte>()

    lateinit var iS2:InputStream
    var bArrL2 = ArrayList<Byte>()

    var ptr = 0

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
    }

    fun Int.hex(): String = Integer.toHexString(this)

    override fun loadData(priority: Priority, callback: DataFetcher.DataCallback<in InputStream>) {
        val imageUrl = URL("https://cdn.pixabay.com/photo/2018/02/08/22/27/flower-3140492__180.jpg")
        val conn = imageUrl.openConnection() as HttpURLConnection
        conn.connectTimeout = 30000
        conn.readTimeout = 30000
        iS = conn.inputStream
        var c1 = iS.read()
        while (true) {
            bArrL.add(c1.toByte())
            if (c1.hex() == "ffffffff") {
                break
            }
            c1 = iS.read()
        }
        val os1 = ByteArrayInputStream(bArrL.toByteArray())
        callback.onDataReady(os1)

        val imgUrl = model.toURL()
        val con = imgUrl.openConnection() as HttpURLConnection
        con.connectTimeout = 30000
        con.readTimeout = 30000
        iS2 = con.inputStream
        var c = iS2.read()
        while (true) {
            bArrL2.add(c.toByte())
            if (c.hex() == "ffffffff") {
                break
            }
            c = iS2.read()
        }
        val os2 = ByteArrayInputStream(bArrL2.toByteArray())
        callback.onDataReady(os2)

    }

// val imageUrl = model.toURL()
//    val conn = imageUrl.openConnection() as HttpURLConnection
//    conn.connectTimeout = 30000
//    conn.readTimeout = 30000
//    iS = conn.inputStream
//    var c1 = iS.read()
//    while (true) {
////            Log.d("stop this", c1.hex())
//        bArrL.add(c1.toByte())
//        if (c1.hex() == "ffffffff") {
//            break
//        }
//        c1 = iS.read()
//    }
//
//    readTwo() // move on starting pointer
//    var marker: Byte
//    do {
//        marker = nextMarker()
//        if (marker.toInt().hex() == M_SOS) {
//            skipSos()
//            val barr = bArrL.subList(0, ptr)
//            barr.add(0xff.toByte())
//            barr.add(0xd9.toByte())
//            val os = ByteArrayInputStream(barr.toByteArray())
//            callback.onDataReady(os)
//        }
//        else if (marker.toInt().hex()  != M_EOI) {
//            skipMarker()
//        }
//    } while (marker.toInt().hex()  != M_EOI)



//    Log.d("In PDataFetcher", "maybe...")
//    // open image stream
//    Log.d("In PDataFetcher", "starting...")
//    val imageUrl = model.toURL()
//    val conn = imageUrl.openConnection() as HttpURLConnection
//    conn.connectTimeout = 30000
//    conn.readTimeout = 30000
//    iS = conn.inputStream
//    // check if valid image
//    if (readOne().hex() + readOne().hex() != M_FF + M_SOI) {
//        // Deal with image not valid
//    }
//    var marker: Int
//    loop@ do {
//        Log.d("In PDataFetcher", "doing...")
//        marker = nextMarker()
//        if(marker == -1) break@loop
//        if (marker.hex() == M_SOS) {
//            // add the skip SOS code here
//            val sm = skipSos()
//            if (sm != 0) {
//                val skip2 = bArrL.removeLast()
//                val skip1 = bArrL.removeLast()
//                bArrL.add(255.toByte())
//                bArrL.add(217.toByte())
//                val os = ByteArrayInputStream(bArrL.toByteArray())
//                bArrL.removeLastTwo()
//                bArrL.add(skip1)
//                bArrL.add(skip2)
//                Log.d("In PDataFetcher", "skipLastTwo...")
//                try {
//                    callback.onDataReady(os)
//                }
//                catch (e: Exception) {
//                    Log.d("In PDataFetcher", "error...")
//                    e.printStackTrace()
//                }
//            } else if (sm == -1){
//                // handling condition that input stream is over
//                break@loop
//            } else {
//                bArrL.add(255.toByte())
//                bArrL.add(217.toByte())
//                val os = ByteArrayInputStream(bArrL.toByteArray())
//                bArrL.removeLastTwo()
//                Log.d("In PDataFetcher", "no skiplastTwo...")
//                try {
//                    callback.onDataReady(os)
//                }
//                catch (e: Exception) {
//                    Log.d("In PDataFetcher", "error...")
//                    e.printStackTrace()
//                }
//            }
//        } else if (marker.hex() != M_EOI){
//            skipMarker()
//        }
//    } while (marker.hex() != M_EOI)
//
//    bArrL.add(255.toByte())
//    bArrL.add(217.toByte())
//    val os = ByteArrayInputStream(bArrL.toByteArray())
//    bArrL.removeLastTwo()
//    Log.d("In PDataFetcher", "no skiplastTwo...")
//    try {
//        callback.onDataReady(os)
//    }
//    catch (e: Exception) {
//        Log.d("In PDataFetcher", "error...")
//        e.printStackTrace()
//    }

    fun ArrayList<Byte>.removeLast() {
//        Log.d("In PDataFetcher", "remove last...")
//        val ret = this.last()
//        this.removeAt(this.size - 1)
//        return ret
        ptr--
    }

    fun ArrayList<Byte>.removeLastTwo() {
//        Log.d("In PDataFetcher", "remove last two...")
//        this.removeAt(this.size - 1)
//        this.removeAt(this.size - 1)
        ptr-=2
    }

    fun readTwo(): Byte {

//        val c1 = readOne()
//        val c2 = readOne()
//
//        if (c1 == -1 || c2 == -1)
//            return -1
//
//        val c3 = c1 shl 8
//        val cur = c3 + c2
//        Log.d("In PDataFetcher", "read two..." + c1.hex() + " " +c2.hex() + " c3: " + c3)
//        return cur

        if (ptr+1 < bArrL.size){
            val c1 = bArrL[ptr++]
            val c2 = bArrL[ptr++]

            return ((c1.toInt() shl 8).toByte() + c2).toByte()
        }
        else
            throw(Error("EOF in read two"))


    }

    fun readOne(): Byte {
        Timber.d("read one")
//        val c = iS.read()
//        if (c != -1)
//            bArrL.add(c.toByte())
//
//        Log.d("In PDataFetcher", "read one..." + c.hex())
//        return c
        if (ptr < bArrL.size)
            return bArrL[ptr++]
        else
            throw(Error("EOF in read one"))
    }

    fun nextMarker(): Byte {

//        var marker = readOne()
//        while (marker.hex() != M_FF && marker != -1) {
//            marker = readOne()
//        }
//
//        if (marker != -1) {
//            do{
//                marker = readOne()
//            }
//            while (marker.hex() == M_FF && marker != -1)
//        }
//
//
//        Log.d("In PDataFetcher", "next marker..." + marker)
//        return marker
        var marker = readOne()
        while (marker.toInt().hex() != M_FF) {
            marker = readOne()
        }
        do {
            marker = readOne()
        }while (marker.toInt().hex() == M_FF)

        return marker
    }

    fun skipMarker() {
//        Log.d("In PDataFetcher", "skip marker...")
//        var len = readTwo() - 2
//        var cur = readOne()
//        Log.d("In PDataFetcher", "skip marker len: " + len + "cur: " + cur)
//        while (len > 0 && cur != -1) {
//            len--
//            // store cur somewhere
//            cur = readOne()
//        }
//        Log.d("In PDataFetcher", "last read..." + bArrL.last())
        var len = readTwo()
        Log.d("length: ", len.toInt().hex())
        Timber.d("length: %s", len)
        if (len.toInt() < 2)
            throw(Error("wrong marker length in skip marker"))
        len = (len.toInt() - 2).toByte()
        if (ptr + len < bArrL.size)
            ptr += len.toInt()
        else
            throw(Error("wrong marker length in skip marker"))
    }

    fun skipSos() {
//        Log.d("In PDataFetcher", "skip sos...")
//        var cur: Int
//        while (true) {
//            cur = readOne()
//            while (cur.hex() != M_FF && cur != -1) {
//                cur = readOne()
//            }
//
//            if (cur != -1) {
//                do{
//                    cur = readOne()
//                }
//                while (cur.hex() == M_FF && cur != -1)
//            }
//
//            when (cur.hex()) {
//                M_RST0-> cur = 0
//                M_RST1-> cur = 0
//                M_RST2-> cur = 0
//                M_RST3-> cur = 0
//                M_RST4-> cur = 0
//                M_RST5-> cur = 0
//                M_RST6-> cur = 0
//                M_RST7-> cur = 0
//            }
//            if (cur != 0) {
//                break
//            }
//        }
//        return cur

        while (true){
            var c = readOne()
            while(c.toInt().hex() != M_FF){
                c = readOne()
            }

            do {
                c = readOne()
            }while (c.toInt().hex() == M_FF)

            when(c.toInt().hex()){
                M_RST0-> c = 0
                M_RST1-> c = 0
                M_RST2-> c = 0
                M_RST3-> c = 0
                M_RST4-> c = 0
                M_RST5-> c = 0
                M_RST6-> c = 0
                M_RST7-> c = 0
            }
            if (c.toInt() != 0) {
                ptr-=2
                break
            }
        }
    }
}