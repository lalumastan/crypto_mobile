package icsdiscover.crypto_mobile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import icsdiscover.crypto_mobile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
    }

    override fun onBackPressed() {
        finishAndRemoveTask()
        super.onBackPressed()
    }
}