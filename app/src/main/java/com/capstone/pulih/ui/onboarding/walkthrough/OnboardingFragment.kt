package com.capstone.pulih.ui.onboarding.walkthrough

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.capstone.pulih.R
import com.capstone.pulih.databinding.FragmentOnboardingBinding
import com.capstone.pulih.databinding.OnboardingDesignBinding
import com.capstone.pulih.ui.onboarding.walkthrough.OnboardingFragment.Companion.MAX_STEP
import com.google.android.material.tabs.TabLayoutMediator

class OnboardingFragment : Fragment() {
    private var _binding: FragmentOnboardingBinding? = null

    private val binding get() = _binding!!

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.adapter = OnBoardingViewPager2Adapter()

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
        }.attach()

        binding.viewPager.setPageTransformer(ZoomOutPageTransformer())


        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                if(position== MAX_STEP -1){
                    binding.nextBtn.text               =   getString(R.string.start)
                    binding.nextBtn.contentDescription  =   getString(R.string.start)//
                }else{
                    binding.nextBtn.text                  = getString(R.string.next)//
                    binding.nextBtn.contentDescription    = getString(R.string.start)//"Next"
                }
            }
        })

        //............................................................
        binding.tvSkip.setOnClickListener {
            Navigation.createNavigateOnClickListener(R.id.onboarding_to_welcome)
            view.findNavController().navigate(R.id.onboarding_to_welcome)
        }

        binding.previousBtn.setOnClickListener {
            val current = (binding.viewPager.currentItem) - 1
            if (current >= 0) {
                binding.viewPager.currentItem = current

                // to update button text
                if (current >= MAX_STEP - 1) {
                    binding.nextBtn.text = getString(R.string.start)
                    binding.nextBtn.contentDescription = getString(R.string.start)
                } else {
                    binding.nextBtn.text = getString(R.string.next)
                    binding.nextBtn.contentDescription = getString(R.string.next)
                }
            }
        }

        binding.nextBtn.setOnClickListener {
            if(binding.nextBtn.text.toString()==getString(R.string.start)){
                Navigation.createNavigateOnClickListener(R.id.onboarding_to_welcome)
                view.findNavController().navigate(R.id.onboarding_to_welcome)
            }
            else{

                val current = (binding.viewPager.currentItem) + 1
                binding.viewPager.currentItem = current

                if(current>= MAX_STEP -1){
                    binding.nextBtn.text                =   getString(R.string.start)//"Get Started"
                    binding.nextBtn.contentDescription  =   getString(R.string.start)//"Get Started"

                }else{
                    binding.nextBtn.text                =   getString(R.string.next)//"Next"
                    binding.nextBtn.contentDescription  =   getString(R.string.next)//"Next"
                }
            }
        }
    }
    companion object {
        const val MAX_STEP = 3
    }
}

class OnBoardingViewPager2Adapter : RecyclerView.Adapter<OnboardingDataPages>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingDataPages {
        return OnboardingDataPages(
            OnboardingDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    //get the size of color array
    override fun getItemCount(): Int = MAX_STEP // Int.MAX_VALUE

    //binding the screen with view
    override fun onBindViewHolder(holder: OnboardingDataPages, position: Int) = holder.itemView.run {

        with(holder) {
            if (position == 0) {
                bindingDesign.title.text = context.getString(R.string.title1)
                bindingDesign.desc.text = context.getString(R.string.desc1)
                bindingDesign.imageView.setImageResource(R.drawable.img)
            }
            if (position == 1) {
                bindingDesign.title.text = context.getString(R.string.title2)
                bindingDesign.desc.text = context.getString(R.string.desc2)
                bindingDesign.imageView.setImageResource(R.drawable.img_1)
            }
            if (position == 2) {
                bindingDesign.title.text = context.getString(R.string.title3)
                bindingDesign.desc.text = context.getString(R.string.desc3)
                bindingDesign.imageView.setImageResource(R.drawable.img_2)
            }
        }
    }
}
class OnboardingDataPages(val bindingDesign: OnboardingDesignBinding) : RecyclerView.ViewHolder(bindingDesign.root)
