package com.company.persons

import javafx.scene.image.Image
import javafx.scene.image.ImageView

abstract class Person (private val _x : Double, private val _y : Double, override val timeOfBorn: Long,
                       override val timeOfLive: Long, private val path: String) : IBehaviour {

    private var imageView : ImageView

    init {
        val image = Image(path)
        imageView = ImageView(image)
    }

    fun getImageView(): ImageView {
        imageView.apply {
            this.x = _x
            this.y = _y
            this.fitWidth = 50.0
            this.fitHeight = 50.0
        }
        return imageView
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Person

        if (_x != other._x) return false
        if (_y != other._y) return false
        if (timeOfBorn != other.timeOfBorn) return false
        if (timeOfLive != other.timeOfLive) return false
        if (path != other.path) return false
        if (imageView != other.imageView) return false

        return true
    }

    override fun hashCode(): Int {
        var result = _x.hashCode()
        result = 31 * result + _y.hashCode()
        result = 31 * result + timeOfBorn.hashCode()
        result = 31 * result + timeOfLive.hashCode()
        result = 31 * result + path.hashCode()
        result = 31 * result + imageView.hashCode()
        return result
    }


}