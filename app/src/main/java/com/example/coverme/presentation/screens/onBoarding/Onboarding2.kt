package com.example.coverme.presentation.screens.onBoarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.coverme.R


class Onboarding2 : Fragment(R.layout.fragment_onboarding2) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.onboarding2Btn).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, Onboarding3())
                .addToBackStack(null)
                .commit()
        }
    }
}