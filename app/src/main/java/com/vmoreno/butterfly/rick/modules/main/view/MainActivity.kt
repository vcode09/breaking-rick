package com.vmoreno.butterfly.rick.modules.main.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.vmoreno.butterfly.rick.R
import com.vmoreno.butterfly.rick.components.CustomDialogFragment
import com.vmoreno.butterfly.rick.utils.OnSingleClickListener

class MainActivity :
    AppCompatActivity(R.layout.activity_main),
    NavController.OnDestinationChangedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController().addOnDestinationChangedListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        navController().removeOnDestinationChangedListener(this)
    }

    private fun navController(): NavController =
        findNavController(R.id.fragment_host)

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
    }

    fun showDialogBreakingBad(title: String, body: String) {

        var dialog: CustomDialogFragment? = null
        dialog = CustomDialogFragment.Builder(this)
            .setIcon(R.drawable.ic_error, R.color.white)
            .setTitle(title)
            .setMessage(body)
            .setPositiveButton(R.string.accept_label, OnSingleClickListener {
                dialog?.dismiss()
            })
            .setCancelable(false)
            .create()
        dialog.show(this.supportFragmentManager, "TAG")
    }
}