package com.capstone.pulih.ui.onboarding.walkthrough

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.capstone.pulih.R
import com.capstone.pulih.ui.auth.response.Pages

class OnboardingFragment(private val pages: Pages) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_onboarding, container, false)
        val titleText = view.findViewById<TextView>(R.id.title)
        val descText = view.findViewById<TextView>(R.id.desc)
        val imageView = view.findViewById<ImageView>(R.id.imageView)

        titleText.text = pages.title
        descText.text = pages.desc
        imageView.setImageResource(pages.image)
        return view
    }


}