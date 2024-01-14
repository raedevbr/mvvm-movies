package com.raedevbr.movies.ui.base

import androidx.lifecycle.ViewModel
import com.raedevbr.movies.usecase.errors.ErrorManager
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {
    //Inject Singleton ErrorManager to get the Errors
    @Inject
    lateinit var errorManager: ErrorManager
}