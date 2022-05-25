package com.example.sekvenia.ui.home

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface IHomeView : MvpView {
    @AddToEndSingle
    fun show()
}
