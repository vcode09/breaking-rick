package com.vmoreno.butterfly.rick.adapter

class ItemDataAbstract<T>(
    override val data: T,
    override val type: Int = 0
) : ItemData<T>