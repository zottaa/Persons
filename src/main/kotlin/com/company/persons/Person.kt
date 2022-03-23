package com.company.persons

import javafx.scene.image.Image
import javafx.scene.image.ImageView

abstract class Person (private val _x : Double, private val _y : Double, private val path: String) : IBehaviour {

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

}