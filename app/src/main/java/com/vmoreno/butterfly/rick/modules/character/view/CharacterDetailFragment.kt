package com.vmoreno.butterfly.rick.modules.character.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.vmoreno.butterfly.rick.domain.messagesexception.MessageExceptionInfo
import com.vmoreno.butterfly.rick.R
import com.vmoreno.butterfly.rick.common.BaseFragment
import com.vmoreno.butterfly.rick.databinding.FragmentDetailBinding
import com.vmoreno.butterfly.rick.modules.character.viewmodel.CharacterViewModel
import com.vmoreno.butterfly.rick.modules.main.view.MainActivity
import com.vmoreno.butterfly.rick.utils.setSingleClickListener
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.android.viewmodel.ext.android.sharedViewModel

class CharacterDetailFragment : BaseFragment<CharacterViewModel>(R.layout.fragment_detail) {
    private val binding by viewBinding(FragmentDetailBinding::bind)
    override val viewModel: CharacterViewModel by sharedViewModel()
    private lateinit var navController: NavController
    private val args: CharacterDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        binding.apply {
            viewModel = this@CharacterDetailFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        viewModel.setCharacter(args.character)
        initToolbar()
        addListeners()
    }

    private fun initToolbar() {
        binding.toolbar.setSingleClickListener {
            navController.popBackStack()
        }
    }

    override fun showError(messageExceptionInfo: MessageExceptionInfo) {
        (activity as? MainActivity)?.showDialogBreakingBad(
            messageExceptionInfo.title,
            messageExceptionInfo.message
        )
    }

    private fun addListeners() {
        viewModel.navigationEvent.observe(viewLifecycleOwner, Observer {
            navigateFragment(it)
        })
    }
}