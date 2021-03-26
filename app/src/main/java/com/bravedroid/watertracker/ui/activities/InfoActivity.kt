package com.bravedroid.watertracker.ui.activities

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bravedroid.watertracker.R
import com.bravedroid.watertracker.data.ScreenWatcher
import com.bravedroid.watertracker.databinding.ActivityInfoBinding
import com.bravedroid.watertracker.ui.fragments.InfoFragment1
import com.bravedroid.watertracker.ui.fragments.InfoFragment2
import com.bravedroid.watertracker.ui.fragments.InfoFragment3
import com.bravedroid.watertracker.util.ActivityHelper
import com.bravedroid.watertracker.util.Logger
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class InfoActivity : AppCompatActivity() {
    @Inject
    lateinit var screenWatcher: ScreenWatcher

    @Inject
    internal lateinit var logger: Logger

    @Inject
    lateinit var activityHelper: ActivityHelper

    private val observer: (t: Map<Class<out Activity>, Int>) -> Unit = { map ->
        map.entries.forEach {
            logger.log("**** from InfoActivity ${it.key} ${it.value}", "Visits")
        }
    }


    private lateinit var binding: ActivityInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activityHelper.hashCode()

//        supportFragmentManager.beginTransaction()
//            .add(R.id.fragment1, InfoFragment1.instance())
//            .commit()
//
//        supportFragmentManager.beginTransaction()
//            .add(R.id.fragment2, InfoFragment2.instance())
//            .commit()
//
//        supportFragmentManager.beginTransaction()
//            .add(R.id.fragment3, InfoFragment3.instance())
//            .commit()
    }

    override fun onStart() {
        super.onStart()
        screenWatcher.totalCount.observe(this, observer)
    }

    override fun onResume() {
        super.onResume()
        screenWatcher.incrementVisits(this::class.java)
    }

    override fun onPause() {
        super.onPause()
        screenWatcher.totalCount.removeObservers(this)
    }
}
