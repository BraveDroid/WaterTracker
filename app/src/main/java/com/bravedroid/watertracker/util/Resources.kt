package com.bravedroid.watertracker.util

import javax.inject.Qualifier


fun getTextResources(): Map<String, String> = mapOf(
    "title" to "titre",
    "userName" to "Prénom",
    "name" to "nom",
    "age" to "age",
    "gender" to "sexe",
)

fun getImageResources(): Map<String, String> = mapOf(
    "homeBackground" to "https://i.pinimg.com/564x/05/b4/6a/05b46a0466eee73f3fd3132522d7310d.jpg",
    "fargment1Background" to "https://images6.fanpop.com/image/photos/41400000/blue-eyed-kitties-kittens-41495274-301-313.jpg",
    "fargment2Background" to "https://www.desktopbackground.org/download/1600x900/2015/07/01/972824_cats-adorable-kitten-kitty-white-cat-blue-eyes-cute-desktop_1600x1200_h.jpg",
    "fargment3Background" to "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSFoGbURnyBcrM8fPZL-FKkq0uq81SsNIwUHQ&usqp=CAU",

    )

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ImageResources

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TextResources

interface Resources {
    fun get(): Map<String, String>
}

class TextResourcesImpl : Resources {
    override fun get(): Map<String, String> = mapOf(
        "title" to "titre",
        "userName" to "Prénom",
        "name" to "nom",
        "age" to "age",
        "gender" to "sexe",
    )
}

class ImageResourcesImpl : Resources {
    override fun get(): Map<String, String> = mapOf(
        "home" to "https://i.pinimg.com/564x/05/b4/6a/05b46a0466eee73f3fd3132522d7310d.jpg",
        "fargment1" to "https://images6.fanpop.com/image/photos/41400000/blue-eyed-kitties-kittens-41495274-301-313.jpg",
        "fargment2" to "https://www.desktopbackground.org/download/1600x900/2015/07/01/972824_cats-adorable-kitten-kitty-white-cat-blue-eyes-cute-desktop_1600x1200_h.jpg",
        "fargment3" to "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSFoGbURnyBcrM8fPZL-FKkq0uq81SsNIwUHQ&usqp=CAU",
    )
}
