package android.rashi.com.usingroom

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_mars_landing.*
import kotlinx.android.synthetic.main.content_mars_landing.*

class MarsLanding : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mars_landing)
        setSupportActionBar(toolbar)
        val name = intent.extras!!.getString("name")
        welcome_on_mars.text = "Welcome to Mars, $name!"
    }

}
