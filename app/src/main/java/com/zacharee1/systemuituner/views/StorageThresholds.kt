package com.zacharee1.systemuituner.views

import android.content.Context
import android.provider.Settings
import android.util.AttributeSet
import android.widget.ScrollView
import com.zacharee1.systemuituner.util.SimpleSeekBarListener
import com.zacharee1.systemuituner.util.prefManager
import com.zacharee1.systemuituner.util.writeGlobal
import kotlinx.android.synthetic.main.storage_thresholds.view.*

class StorageThresholds(context: Context, attrs: AttributeSet) : ScrollView(context, attrs) {
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        threshold_percent.apply {
            scaledProgress = Settings.Global.getInt(context.contentResolver, Settings.Global.SYS_STORAGE_THRESHOLD_PERCENTAGE, 5).toFloat()
            listener = object : SimpleSeekBarListener() {
                override fun onProgressChanged(newValue: Int, newScaledValue: Float) {
                    context.prefManager.putInt(Settings.Global.SYS_STORAGE_THRESHOLD_PERCENTAGE, newScaledValue.toInt())
                    context.writeGlobal(Settings.Global.SYS_STORAGE_THRESHOLD_PERCENTAGE, newScaledValue.toInt())
                }
            }
        }

        threshold_bytes.apply {
            scaledProgress = Settings.Global.getInt(context.contentResolver, Settings.Global.SYS_STORAGE_THRESHOLD_MAX_BYTES, 500000000) * scale
            listener = object : SimpleSeekBarListener() {
                override fun onProgressChanged(newValue: Int, newScaledValue: Float) {
                    context.prefManager.putInt(Settings.Global.SYS_STORAGE_THRESHOLD_MAX_BYTES, newValue)
                    context.writeGlobal(Settings.Global.SYS_STORAGE_THRESHOLD_MAX_BYTES, newValue)
                }
            }
        }
    }
}