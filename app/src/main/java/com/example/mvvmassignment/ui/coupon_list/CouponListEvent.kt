package com.example.mvvmassignment.ui.coupon_list

import com.example.mvvmassignment.data.Coupon


sealed class CouponListEvent {
    data class OnCouponClick(val coupon: Coupon): CouponListEvent()
    object OnAddCouponClick: CouponListEvent()
}
