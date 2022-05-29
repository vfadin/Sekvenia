package com.example.sekvenia.ui.home

import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.AddToEndSingle
import kotlin.reflect.jvm.internal.impl.incremental.components.Position

@StateStrategyType(SingleStateStrategy::class)
interface IHomeView : MvpView {
    fun bindUi()
    fun setData()
    fun setGenreFilter(position: Int)
}
