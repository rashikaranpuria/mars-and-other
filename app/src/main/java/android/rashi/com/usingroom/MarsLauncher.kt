package android.rashi.com.usingroom

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_mars_launcher.*
import kotlinx.android.synthetic.main.content_mars_launcher.*


class MarsLauncher : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mars_launcher)
        setSupportActionBar(toolbar)

        btn_launch.setOnClickListener {
            if(et_name.getText().toString() == "") {
                Snackbar.make(parentView, resources.getString(R.string.no_name_error), Snackbar.LENGTH_SHORT).show()
            }
            else {
                val intent = Intent(this, MarsLanding::class.java)
                intent.putExtra("name", et_name.getText().toString())
                startActivity(intent)
            }
        }
    }
}

