package com.niroshan.smarthome.ui.details

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.niroshan.smarthome.R
import com.niroshan.smarthome.databinding.FragmentDetailsBinding
import com.niroshan.smarthome.internal.setAvatarImage

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        _binding = FragmentDetailsBinding.bind(view)

        binding.apply {
            title.text = args.device.title
            price.text = args.device.price.toString()
            currency.text = args.device.currency

            avatar.apply {
                transitionName = args.device.imageUrl
                setAvatarImage(view, args.device.imageUrl!!)
            }
        }

        ViewCompat.setTransitionName(binding.avatar, "avatar_${args.device.title}")

        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                view?.let { Navigation.findNavController(it).navigateUp() }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}