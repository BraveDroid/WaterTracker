package com.bravedroid.watertracker

import android.content.Context
import androidx.core.app.ActivityCompat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@InstallIn(ActivityComponent::class)
@Module
class WaterTrackingModule {

    @Provides
    fun providesPreferencesHelper(
        @ApplicationContext context: Context
    ) = PreferencesHelper(context)
}
