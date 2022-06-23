package com.vmoreno.butterfly.rick.utils.lifecycle

import android.app.Activity.RESULT_OK
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavDirections

sealed class NavigationEvents

data class StartActivityEvent(
    val activity: Class<*>
) : NavigationEvents() {
    var bundle: Bundle? = null
    var code: Int = 0
    var flag: Int = 0
}

data class FinishResultEvent(
    val resultCode: Int = RESULT_OK,
    val bundle: Bundle? = null
) : NavigationEvents()

data class OnBackPressedEvent(
    val resultCode: Int = RESULT_OK
) : NavigationEvents()

data class NavigationToDirectionEvent(
    val navDirections: NavDirections
) : NavigationEvents()

data class NavigationToActionIdEvent(
    @IdRes val actionId: Int,
    val arguments: Bundle? = null
) : NavigationEvents()

data class NavigationUpEvent(
    val navDirections: NavDirections
) : NavigationEvents()

data class PopBackStackEvent(
    val navDirections: NavDirections
) : NavigationEvents()