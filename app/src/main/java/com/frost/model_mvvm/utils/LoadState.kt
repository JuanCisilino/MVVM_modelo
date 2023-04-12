package com.frost.model_mvvm.utils

sealed class LoadState{
    object Loading : LoadState()
    object Success : LoadState()
    object Error : LoadState()
}
