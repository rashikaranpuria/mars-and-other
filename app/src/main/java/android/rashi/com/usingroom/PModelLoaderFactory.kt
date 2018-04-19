package android.rashi.com.usingroom

import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import java.io.InputStream

class PModelLoaderFactory: ModelLoaderFactory<GlideUrl, InputStream> {
    override fun teardown() {
        // do nothing
    }

    override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<GlideUrl, InputStream> = PModelLoader()
}