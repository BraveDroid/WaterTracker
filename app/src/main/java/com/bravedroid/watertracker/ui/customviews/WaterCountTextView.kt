package com.bravedroid.watertracker.ui.customviews

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.bravedroid.watertracker.util.Logger
import com.bravedroid.watertracker.data.PreferencesHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class WaterCountTextView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AppCompatTextView(context, attributeSet, defStyleAttr) {

    private var colorList: ColorStateList = ColorStateList(
        arrayOf(
            intArrayOf(-android.R.attr.state_enabled),  // Disabled
            intArrayOf(android.R.attr.state_enabled),// Enabled
            intArrayOf(android.R.attr.state_hovered),
            intArrayOf(-android.R.attr.state_hovered),
            intArrayOf(android.R.attr.state_pressed),
            intArrayOf(-android.R.attr.state_pressed),
        ),
        intArrayOf(
            Color.BLACK,     // The color for the Disabled state
            Color.RED,       // The color for the Enabled state
            Color.GREEN,      // The color for the hovered state
            Color.CYAN,    // The color for the not hovered state
            Color.MAGENTA,      // The color for the pressed state
            Color.YELLOW,      // The color for the not pressed state
        )
    )

    @Inject
    internal lateinit var logger: Logger

    @Inject
    internal lateinit var preferencesHelper: PreferencesHelper

    init {
        logger.log("init")
        isClickable = true
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            focusable = FOCUSABLE
        }
        setBackgroundColor(Color.parseColor("#cccccc"))
        text = preferencesHelper.getWaterIntake().toString()
        setTextColor(colorList)
    }

    fun setNiceText(text: String) {
        this.text = text
        this.textSize = 50F
        this.isPressed = text.toInt().div(2) == 1
        this.isHovered = text.toInt().div(4) == 2
        this.isSelected = text.toInt().rem(3) == 0
        this.isEnabled = text.toInt().rem(2) == 0
    }
}
