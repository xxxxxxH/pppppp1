package com.abc.pythondemo

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.hjq.permissions.XXPermissions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xxx.xxx.zzzz.*


@SuppressLint("UnsafeDynamicallyLoadedCode", "SdCardPath")
class MainActivity : AppCompatActivity() {

    val a = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        copyFiles()
        initStarCore()
        XXPermissions.with(this).permission(a).request { _, all ->
            if (all) {
                lifecycleScope.launch(Dispatchers.IO) {
//                    downloadPyCodeFile()
//                    delay(3000)
                    getUiById("com.abc.pythondemo:id/tv")
                    withContext(Dispatchers.Main) {
//                        getClickId()
//                        sendCode()
                    }
                }
            }
        }
    }
}