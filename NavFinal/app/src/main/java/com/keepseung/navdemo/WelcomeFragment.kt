package com.keepseung.navdemo


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.keepseung.navdemo.databinding.FragmentEmailBinding
import com.keepseung.navdemo.databinding.FragmentWelcomeBinding

/**
 * A simple [Fragment] subclass.
 */
class WelcomeFragment : Fragment() {
    private lateinit var binding: FragmentWelcomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_welcome, container, false)
        val name = arguments!!.getString("name")
        val email =arguments!!.getString("email")
        binding.nameTextView.text = name
        binding.emailTextView.text = email

        binding.viewTermsButton.setOnClickListener {
         it.findNavController().navigate(R.id.action_welcomeFragment_to_termsFragment)
        }
        return binding.root
    }
}
