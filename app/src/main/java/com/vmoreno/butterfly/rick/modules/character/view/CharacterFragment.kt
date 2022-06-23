package com.vmoreno.butterfly.rick.modules.character.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vmoreno.butterfly.rick.domain.messagesexception.MessageExceptionInfo
import com.vmoreno.butterfly.rick.R
import com.vmoreno.butterfly.rick.adapter.GenericAdapter
import com.vmoreno.butterfly.rick.adapter.ItemDataAbstract
import com.vmoreno.butterfly.rick.common.BaseFragment
import com.vmoreno.butterfly.rick.common.ScopedViewModel
import com.vmoreno.butterfly.rick.components.CustomDialogFragment
import com.vmoreno.butterfly.rick.databinding.FragmentCharacterBinding
import com.vmoreno.butterfly.rick.modules.character.entities.CharacterBreakingBadUi
import com.vmoreno.butterfly.rick.modules.character.entities.CharacterItemView
import com.vmoreno.butterfly.rick.modules.character.viewmodel.CharacterViewModel
import com.vmoreno.butterfly.rick.modules.main.view.MainActivity
import com.vmoreno.butterfly.rick.network.NetworkStatusHelper
import com.vmoreno.butterfly.rick.utils.OnSingleClickListener
import com.google.android.material.snackbar.Snackbar
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.android.viewmodel.ext.android.sharedViewModel

const val TAG_CHARACTER = "CharacterFragment"

class CharacterFragment : BaseFragment<CharacterViewModel>(R.layout.fragment_character) {
    private val binding by viewBinding(FragmentCharacterBinding::bind)
    override val viewModel: CharacterViewModel by sharedViewModel()
    private var visibleItemCount = 0
    private var totalItemCount = 0
    private var pastVisibleItems = 0
    private lateinit var navController: NavController
    private val characterListAdapter by lazy {
        GenericAdapter { parent, _ ->
            CharacterItemView(parent.context, viewModel::onItemSelected, ::validateFavourite)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        binding.apply {
            viewModel = this@CharacterFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        if (viewModel.dataList.isNullOrEmpty()) {
            println("onViewCreated")
            initViews()
            viewModel.init()
        }
        addListeners()
        binding.rvList.adapter = characterListAdapter
    }

    override fun showError(messageExceptionInfo: MessageExceptionInfo) {
        (activity as? MainActivity)?.showDialogBreakingBad(
            messageExceptionInfo.title,
            messageExceptionInfo.message
        )
    }

    private fun validateFavourite(item: CharacterBreakingBadUi) {
        if (item.isFavourite) showConfirmationDialog(item)
        else viewModel.saveFavourite(item)
    }

    private fun showConfirmationDialog(item: CharacterBreakingBadUi) {
        activity?.let {
            var dialog: CustomDialogFragment? = null
            dialog = CustomDialogFragment.Builder(requireContext())
                .setIcon(R.drawable.ic_favourite_fill, R.color.white)
                .setTitle(R.string.title_favourite_label)
                .setMessage(R.string.delete_favourite_body)
                .setPositiveButton(R.string.accept_label, OnSingleClickListener {
                    viewModel.deleteFavourite(item)
                    dialog?.dismiss()
                })
                .setNegativeButton(R.string.cancel_label, OnSingleClickListener {
                    dialog?.dismiss()
                })
                .setCancelable(false)
                .create()
            dialog.show(it.supportFragmentManager, TAG_CHARACTER)
        }
    }

    private fun initViews() {
        context?.let { context ->
            NetworkStatusHelper(context).observe(this, {
                viewModel.showHideInternetConnection(it)
            })
        }
    }

    private fun notifyFavourite() {
        val snack =
            Snackbar.make(binding.mainViewSearch, R.string.notify_success, Snackbar.LENGTH_LONG)
        snack.show()
    }

    private fun addListeners() {
        viewModel.navigationEvent.observe(viewLifecycleOwner, Observer {
            navigateFragment(it)
        })
        viewModel.characterList.observe(viewLifecycleOwner, Observer {
            characterListAdapter.submitList(
                it.map { characterUiModel -> ItemDataAbstract(characterUiModel) }
            )
        })
        viewModel.notifyFavourite.observe(viewLifecycleOwner, {
            if (it) notifyFavourite()
        })


        binding.rvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    binding.rvList.layoutManager?.let {
                        visibleItemCount = it.childCount
                        totalItemCount = it.itemCount
                        pastVisibleItems =
                            (it as LinearLayoutManager).findFirstVisibleItemPosition()
                    }
                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        if (viewModel.model.value != ScopedViewModel.UiModel.Loading) {
                            this@CharacterFragment.viewModel.getCharacterList()
                        }
                    }
                }
            }
        })
    }
}