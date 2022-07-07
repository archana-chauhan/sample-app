package com.example.mvvmassignment.util

sealed class CouponUIEvents {
    object PopBackStack: CouponUIEvents()

    data class ShowSnackbar(
        val message: String,
        val action: String? = null
    ) : CouponUIEvents()

    data class Navigate(
        val routes: String
    ) : CouponUIEvents()


}
