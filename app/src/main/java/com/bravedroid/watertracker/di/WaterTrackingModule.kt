package com.bravedroid.watertracker.di

import android.content.Context
import com.bravedroid.watertracker.data.PreferencesHelper
import com.bravedroid.watertracker.util.*
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Singleton

@InstallIn(ActivityComponent::class)
@Module
class WaterTrackingModule {
    @ActivityScoped
    @Provides
    fun providesPreferencesHelper(
        @ApplicationContext context: Context,
        logger: Logger,
    ) = PreferencesHelper(context, logger)

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
