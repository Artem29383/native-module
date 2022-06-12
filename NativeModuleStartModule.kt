package io.tezis.mobile.modules.nativeModuleStart

import android.view.MotionEvent
import android.view.View
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.modules.core.DeviceEventManagerModule
import io.tezis.mobile.AppManager
import java.util.*
import kotlin.properties.Delegates

class NativeModuleStart(reactContext: ReactApplicationContext) :
    ReactContextBaseJavaModule(reactContext), View.OnTouchListener {

    override fun getName() = "NativeModuleStart"

    lateinit var elementView: View
    var dx by Delegates.notNull<Float>()
    var dy by Delegates.notNull<Float>()

    @ReactMethod
    fun incrementValue(value: Int, promise: Promise) {
        promise.resolve((value + 11).toString())
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun incrementValueSync(value: Int): String {

        (Timer(true)).schedule(object : TimerTask() {
            override fun run() {
                AppManager.getInstance().currentReactContext
                    ?.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
                    ?.emit("nativeModuleStartEvent", "data from native")
            }
        }, 5000)

        return ((value + 11).toString())
    }

    @ReactMethod
    fun nativeModuleView(elementRef: Int) {
        elementView = AppManager.getInstance().activityContext?.findViewById<View>(elementRef)!!
        elementView.setOnTouchListener(this)
    }

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                dx = view.x - event.rawX
                dy = view.y - event.rawY
            }
            MotionEvent.ACTION_MOVE -> {
                elementView.animate()
                    .x(event.rawX + dx)
                    .y(event.rawY + dy)
                    .setDuration(0)
                    .start()
            }
            else -> {
                return false
            }
        }
        return true
    }
}

