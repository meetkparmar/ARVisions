package com.meet.project.arvisions.ui.activity

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.meet.project.arvisions.models.Shapes
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.ArNode

class MainViewModel(context: Application): AndroidViewModel(context) {

    var items = mutableListOf<Shapes>()
    var selectedIndex by mutableStateOf(value = 0)
    var nodes = mutableListOf<ArNode>()
    var modelNode = mutableStateOf<ArModelNode?>(value = null)
    var placeModelButton = mutableStateOf(value = false)
}