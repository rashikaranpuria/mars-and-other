package android.rashi.com.usingroom

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import java.io.InputStream

@GlideModule
class MyAppGlideModule : AppGlideModule() {

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry.prepend(GlideUrl::class.java, InputStream::class.java, PModelLoaderFactory())
//                super.registerComponents(context, glide, registry);
    }
}
