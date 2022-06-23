package com.vmoreno.butterfly.rick.modules.character.entities

import android.content.Context
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import com.vmoreno.butterfly.rick.R
import com.vmoreno.butterfly.rick.adapter.ItemView
import com.vmoreno.butterfly.rick.databinding.ItemCharacterBinding
import com.vmoreno.butterfly.rick.utils.setSingleClickListener
import com.vmoreno.butterfly.rick.view.properties.itemViewBinding

class CharacterItemView(
    override val context: Context,
    onCardClickListener: ((CharacterBreakingBadUi) -> Unit),
    onFavouriteClickListener: ((CharacterBreakingBadUi) -> Unit),
) : ItemView<CharacterBreakingBadUi> {

    private val binding by itemViewBinding<ItemCharacterBinding>(R.layout.item_character)

    override val view = binding.root

    override lateinit var data: CharacterBreakingBadUi

    init {
        binding.root.apply {
            layoutParams = ViewGroup.MarginLayoutParams(
                MATCH_PARENT,
                WRAP_CONTENT
            )
            binding.cardViewTokenHome.setSingleClickListener {
                onCardClickListener(data)
            }
            binding.favouriteImageView.setSingleClickListener {
                onFavouriteClickListener(data)
            }
        }
    }

    override fun bind(item: CharacterBreakingBadUi) {
        data = item
        with(binding) {
            this.item = item
            executePendingBindings()
        }
    }
}