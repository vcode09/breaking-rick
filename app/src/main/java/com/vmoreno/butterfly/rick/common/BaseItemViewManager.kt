package com.vmoreno.butterfly.rick.common

import com.vmoreno.butterfly.rick.utils.lifecycle.NavigationEvents
import com.vmoreno.butterfly.rick.utils.asLiveData

abstract class BaseItemViewManager {

    protected val _navigationEvent = SingleLiveEvent<NavigationEvents>()
    val navigationEvent get() = _navigationEvent.asLiveData()
}