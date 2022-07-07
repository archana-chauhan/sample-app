package com.example.mvvmassignment.ui.add_edit_coupon

sealed class AddEditCouponEvent {
    data class OnTitleChange(val title: String): AddEditCouponEvent()
    data class OnTypeChange(val type: String): AddEditCouponEvent()
    data class OnValueChange(val value: String): AddEditCouponEvent()
    data class OnStartDateChange(val start_date: String): AddEditCouponEvent()
    data class OnEndDateChange(val end_date: String): AddEditCouponEvent()
    data class OnUnlimitedTimeChange(val unlimited_time: Boolean): AddEditCouponEvent()
    object OnSaveCouponClick: AddEditCouponEvent()
}
