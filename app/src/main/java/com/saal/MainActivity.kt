package com.saal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.saal.ui.viewModel.MainViewModel
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Main Activity to inflate Fragments
 */
class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {

    }
}
