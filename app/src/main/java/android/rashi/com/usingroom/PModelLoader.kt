package android.rashi.com.usingroom

import android.util.Log
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.stream.HttpGlideUrlLoader
import com.bumptech.glide.signature.ObjectKey
import java.io.InputStream

class PModelLoader: HttpGlideUrlLoader() {

    override fun buildLoadData(model: GlideUrl, width: Int, height: Int, options: Options): ModelLoader.LoadData<InputStream>? {
        return ModelLoader.LoadData<InputStream>(ObjectKey(model), PDataFetcher(model))
    }

    override fun handles(model: GlideUrl): Boolean {
        Log.d("In PModelLoader", model.toStringUrl())
        return true
    }
}