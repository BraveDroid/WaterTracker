package com.bravedroid.watertracker.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.bravedroid.watertracker.data.PreferencesHelper
import com.bravedroid.watertracker.util.*
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Singleton

@InstallIn(ActivityComponent::class)
@Module
class WaterTrackingModule {

    @ActivityScoped
    @Provides
    fun providesSharedPreferences(
        @ApplicationContext context: Context,
    ) =
        context.getSharedPreferences("water_tracker_prefs", MODE_PRIVATE)

    @ActivityScoped
    @Provides
    fun providesPreferencesHelper(
        sharedPreferences: SharedPreferences,
        logger: Logger,
    ) = PreferencesHelper(sharedPreferences, logger)

    @ActivityScoped
    @Provides
    fun providesActivityHelper(
        logger: Logger,
    ) = ActivityHelper(logger)

    @ActivityScoped
    @Provides
    fun providesHeavy(
        @TextResources textResource: Resources,
        @ImageResources textImage: Resources,
        logger: Logger,
    ) = Heavy(textResource, textImage, logger)
}

@Module
@InstallIn(ApplicationComponent::class)
class ResourceModule {
    @Singleton
    @Provides
    @TextResources
    fun providesTextResource(
    ): Resources = TextResourcesImpl()

    @Singleton
    @Provides
    @ImageResources
    fun providesImageResource(
    ): Resources = ImageResourcesImpl()
}

@Module
@InstallIn(ApplicationComponent::class)
class GlideModule {
    @Singleton
    @Provides
    fun providesGlide(
        @ApplicationContext context: Context,
    ) = Glide.with(context)

    @Singleton
    @Provides
    fun providesImageLoader(
        glide: RequestManager
    ): ImageLoader = ImageLoaderImpl(glide)
}

@Module
@InstallIn(ActivityComponent::class)
class TrackerModule {

    @Tracker1
    @Provides
    fun providesTracker1(logger: Logger): BaseInfoActivityTracker = InfoActivityTracker1(logger)

    @Tracker2
    @Provides
    fun providesTracker2(
        @ActivityContext context: Context,
        logger: Logger,
    ): BaseInfoActivityTracker = InfoActivityTracker2(context, logger)
}
