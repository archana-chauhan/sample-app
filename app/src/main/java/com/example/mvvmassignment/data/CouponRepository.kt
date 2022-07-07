package com.example.mvvmassignment.data

import kotlinx.coroutines.flow.Flow

interface CouponRepository {

    suspend fun addCoupon(coupon: Coupon)

    suspend fun getCouponById(id: Int): Coupon

    fun getCoupons(): Flow<List<Coupon>>
}