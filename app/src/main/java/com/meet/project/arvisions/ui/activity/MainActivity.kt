package com.meet.project.arvisions.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.meet.project.arvisions.R
import com.meet.project.arvisions.databinding.ActivityComposeBinding
import com.meet.project.arvisions.models.Shapes

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    lateinit var binding: ActivityComposeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComposeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProviders.of(this)[MainViewModel::class.java]
        initData()
        binding.composeView.setContent {
            MainScreen(
                viewModel = viewModel,
                onBackClick = ::onBackClick,
                onNextClick = ::onNextClick,
            )
        }
    }

    private fun initData() {
        viewModel.items.add(Shapes("triangle", R.drawable.triangle))
        viewModel.items.add(Shapes("scifi_cube", R.drawable.scifi_cube))
        viewModel.items.add(Shapes("sphere_glass", R.drawable.sphere_glass))
        viewModel.items.add(Shapes("flower_pot", R.drawable.flower_pot))

        viewModel.selectedIndex = 0
    }

    private fun onNextClick() {
        viewModel.selectedIndex = (viewModel.selectedIndex + 1 + viewModel.items.size) % viewModel.items.size
    }

    private fun onBackClick() {
        viewModel.selectedIndex = (viewModel.selectedIndex - 1 + viewModel.items.size) % viewModel.items.size
    }
}