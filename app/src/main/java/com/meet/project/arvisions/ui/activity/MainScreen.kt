package com.meet.project.arvisions.ui.activity

import ThemeColor
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.google.ar.core.Config
import io.github.sceneview.ar.ARScene
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.PlacementMode

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        ArScreen(viewModel = viewModel)
        BottomView(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .zIndex(2f)
                .padding(horizontal = 16.dp, vertical = 16.dp),
            viewModel = viewModel,
            onBackClick = onBackClick,
            onNextClick = onNextClick
        )
    }
}

@Composable
fun ArScreen(
    viewModel: MainViewModel
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .zIndex(1f)) {
        ARScene(
            modifier = Modifier
                .fillMaxSize(),
            nodes = viewModel.nodes,
            planeRenderer = true,
            onCreate = {
                it.lightEstimationMode = Config.LightEstimationMode.DISABLED
                it.planeRenderer.isShadowReceiver = true
                viewModel.modelNode.value = ArModelNode(it.engine, PlacementMode.INSTANT).apply {
                    loadModelGlbAsync(
                        glbFileLocation = "models/${viewModel.items[viewModel.selectedIndex].name}.glb",
                        scaleToUnits = 0.8f
                    )
                    onAnchorChanged = {
                        viewModel.placeModelButton.value = !isAnchored
                    }
                    onHitResult = { node, hitResult ->
                        viewModel.placeModelButton.value = node.isTracking
                    }
                }
                viewModel.nodes.add(viewModel.modelNode.value!!)
            }
        )
        if (viewModel.placeModelButton.value) {
            Button(
                onClick = { viewModel.modelNode.value?.anchor() },
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(text = "Place It")
            }
        }
    }

    LaunchedEffect(key1 = viewModel.items[viewModel.selectedIndex].name) {
        viewModel.modelNode.value?.loadModelGlbAsync(
            glbFileLocation = "models/${viewModel.items[viewModel.selectedIndex].name}.glb"
        )
        Log.e("errorLoading", "Error loading models")
    }
}

@Composable
fun BottomView(
    modifier: Modifier,
    viewModel: MainViewModel,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Surface(
            modifier = Modifier.padding(4.dp),
            color = Color.White.copy(0.2f),
            shape = RoundedCornerShape(50)
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = com.google.android.material.R.drawable.material_ic_keyboard_arrow_left_black_24dp),
                    contentDescription = "LeftArrow",
                    modifier = Modifier.size(32.dp),
                )
            }
        }


        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .border(2.dp, ThemeColor.Grey50, CircleShape)
        ) {
            Image(
                painter = painterResource(id = viewModel.items[viewModel.selectedIndex].imageId),
                contentDescription = "",
                contentScale = ContentScale.FillBounds
            )
        }
        Surface(
            modifier = Modifier.padding(4.dp),
            color = Color.White.copy(0.2f),
            shape = RoundedCornerShape(50)
        ) {
            IconButton(onClick = onNextClick) {
                Icon(
                    painter = painterResource(id = com.google.android.material.R.drawable.material_ic_keyboard_arrow_right_black_24dp),
                    contentDescription = "RightArrow",
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}
