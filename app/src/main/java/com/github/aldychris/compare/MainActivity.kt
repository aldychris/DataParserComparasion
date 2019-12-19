package com.github.aldychris.compare

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.daveanthonythomas.moshipack.MoshiPack
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.github.aldychris.compare.model.Media
import com.google.gson.Gson
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.android.synthetic.main.activity_main.*
import okio.ByteString
import org.msgpack.jackson.dataformat.MessagePackFactory


class MainActivity : AppCompatActivity() {

    var gson = Gson()
    var moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    var jackson = ObjectMapper().registerKotlinModule()

    val moshiPack = MoshiPack()
    var jacksonPack = ObjectMapper(MessagePackFactory()).registerKotlinModule()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        Log.d("MainActivity", Media.SAMPLE_STRING)
    }

    fun btnGsonObj2StrClicked(view: View){
        measureTimeMillis({ time -> tvGsonResult.text = String.format(getString(R.string.restemplate), time) }) {
            val x = gson.toJson(Media.SAMPLE_OBJ)
            Log.d("MainActivity", x)
        }
    }

    fun btnGsonStr2ObjClicked(view: View) {
        measureTimeMillis({ time -> tvGsonResult.text = String.format(getString(R.string.restemplate), time) }) {
            gson.fromJson(Media.SAMPLE_STRING, Media::class.java)
        }
    }

    fun btnMoshiObj2StrClicked(view: View) {
        measureTimeMillis({ time -> tvMoshiResult.text = String.format(getString(R.string.restemplate), time) }) {
            moshi.adapter(Media::class.java).toJson(Media.SAMPLE_OBJ)
        }
    }

    fun btnMoshiStr2ObjClicked(view: View) {
        measureTimeMillis({ time -> tvMoshiResult.text = String.format(getString(R.string.restemplate), time) }) {
            moshi.adapter(Media::class.java).fromJson(Media.SAMPLE_STRING)
        }
    }

    fun btnMoshiMsgPackObj2StrClicked(view: View) {
        measureTimeMillis({ time -> tvMoshiMsgPackResult.text = String.format(getString(R.string.restemplate), time) }) {
            moshiPack.pack(Media.SAMPLE_OBJ).toString()
        }
    }

    fun btnMoshiMsgPackStr2ObjClicked(view: View) {
        measureTimeMillis({ time -> tvMoshiMsgPackResult.text = String.format(getString(R.string.restemplate), time) }) {
            val obj: Media = moshiPack.unpack(ByteString.decodeHex(Media.SAMPLE_MSGPACK).toByteArray())
//            Log.d("MainActivity", obj.param2)
        }
    }

    fun btnJacksonObj2StrClicked(view: View) {
        measureTimeMillis({ time -> tvJacksonResult.text = String.format(getString(R.string.restemplate), time) }) {
            jackson.writeValueAsString(Media.SAMPLE_OBJ)
        }
    }

    fun btnJacksonStr2ObjClicked(view: View) {
        measureTimeMillis({ time -> tvJacksonResult.text = String.format(getString(R.string.restemplate), time) }) {
            jackson.readValue(Media.SAMPLE_STRING, Media::class.java)
        }
    }

    fun btnJacksonMsgPackObj2StrClicked(view: View) {
        measureTimeMillis({ time -> tvJacksonMsgPackResult.text = String.format(getString(R.string.restemplate), time) }) {
            jacksonPack.writeValueAsBytes(Media.SAMPLE_OBJ)
        }
    }

    fun btnJacksonMsgPackStr2ObjClicked(view: View) {
        measureTimeMillis({ time -> tvJacksonMsgPackResult.text = String.format(getString(R.string.restemplate), time) }) {
            jacksonPack.readValue(ByteString.decodeHex(Media.SAMPLE_MSGPACK).toByteArray(), Media::class.java)
        }
    }

    fun btnKotlinExtObj2StrClicked(view: View) {
        measureTimeMillis({ time -> tvKotlinExtResult.text = String.format(getString(R.string.restemplate), time) }) {
            Log.d("DFS",Media.SAMPLE_OBJ.asMap().toString())
        }
    }

    fun btnKotlinExtStr2ObjClicked(view: View) {
        measureTimeMillis({ time -> tvKotlinExtResult.text = String.format(getString(R.string.restemplate), time) }) {
            Media.SAMPLE_STRING.toMap()
        }
    }


    private inline fun <T> measureTimeMillis(logFunc: (Long) -> Unit, function: () -> T): T {
        val startTime = System.currentTimeMillis()
        val result: T = function.invoke()
        logFunc.invoke(System.currentTimeMillis() - startTime)
        return result
    }

}
