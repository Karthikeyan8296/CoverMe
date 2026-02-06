package com.example.coverme.presentation.screens.onBoarding

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.example.coverme.R
import com.example.coverme.data.local.dataStore.OnBoardingDataStore
import com.example.coverme.presentation.screens.Home.HomeFragment
import kotlinx.coroutines.launch

class Onboarding3 : Fragment(R.layout.fragment_onboarding3) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val onBoardingDataStore = OnBoardingDataStore(requireContext())

        view.findViewById<Button>(R.id.onboarding3Btn).setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {

                onBoardingDataStore.setOnBoarding()

                //Im deleting the all "null" History stack
                parentFragmentManager.popBackStack(
                    null,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )

                parentFragmentManager.beginTransaction().replace(R.id.main, HomeFragment())
                    .commit()
            }
        }
    }
}