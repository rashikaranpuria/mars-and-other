package android.rashi.com.usingroom

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.signature.ObjectKey
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        GlideApp
                .with(this)
//                .load("https://cdn.pixabay.com/photo/2018/02/08/22/27/flower-3140492__180.jpg")
//                .load("http://pooyak.com/p/progjpeg/jpegload.cgi?o=1")
                .load("http://frescolib.org/static/sample-images/animal_d_s.jpg")
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .signature(ObjectKey(System.currentTimeMillis()))
//                .apply(RequestOptions()
//                        .signature(ObjectKey(System.currentTimeMillis())))
                .into(imageView)

//        val mSimpleDraweeView = imageView as SimpleDraweeView
//
//        val uri = Uri.parse("http://pooyak.com/p/progjpeg/jpegload.cgi?o=1")
//        val request = ImageRequestBuilder.newBuilderWithSource(uri)
//                .setProgressiveRenderingEnabled(true)
//                .build()
//        val controller = Fresco.newDraweeControllerBuilder()
//                .setImageRequest(request)
//                .setOldController(mSimpleDraweeView.getController())
//                .build()
//        mSimpleDraweeView.setController(controller)
    }

}
