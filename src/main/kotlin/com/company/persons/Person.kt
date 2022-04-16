package com.company.persons

import javafx.scene.image.Image
import javafx.scene.image.ImageView
import kotlin.coroutines.*

abstract class Person (
    private val point: Point, override val timeOfBorn: Long,
    override val timeOfLive: Long, private val path: String, private val destinationPoint: Point
) : BaseAI(), IBehaviour {

    private var imageView: ImageView

    init {
        val image = Image(path)
        imageView = ImageView(image)
    }

    fun getImageView(): ImageView {
        imageView.apply {
            x = point.x
            y = point.y
            fitWidth = 50.0
            fitHeight = 50.0
        }
        return imageView
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Person

        if (point.x != other.point.x) return false
        if (point.y != other.point.y) return false
        if (timeOfBorn != other.timeOfBorn) return false
        if (timeOfLive != other.timeOfLive) return false
        if (path != other.path) return false
        if (imageView != other.imageView) return false

        return true
    }

    override fun hashCode(): Int {
        var result = point.hashCode()
        result = 31 * result + timeOfBorn.hashCode()
        result = 31 * result + timeOfLive.hashCode()
        result = 31 * result + path.hashCode()
        result = 31 * result + imageView.hashCode()
        return result
    }

}