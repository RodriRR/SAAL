package com.saal.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.saal.databinding.FragmentSplashBinding
import com.saal.ui.viewModel.MainViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel
import java.util.*

/**
 * This fragment is responsible for displaying the loading screen.
 */
class SplashFragment : Fragment() {

    private val viewModel by sharedViewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentSplashBinding.inflate(inflater)
        binding.viewmodel = viewModel
        val runSplash = Timer()
        val showSplash = object : TimerTask() {
            override fun run() {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToTaskFragment())
            }
        }
        runSplash.schedule(showSplash, 5000)
        return binding.root
    }


}
