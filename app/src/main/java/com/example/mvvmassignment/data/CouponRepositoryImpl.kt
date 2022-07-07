package com.example.mvvmassignment.data

import kotlinx.coroutines.flow.Flow

class CouponRepositoryImpl(
    private val couponDao: CouponDao
) : CouponRepository {
    override suspend fun addCoupon(coupon: Coupon) {
        couponDao.addCoupon(coupon)
    }
    override suspend fun getCouponById(id: Int): Coupon {
        return couponDao.getCouponById(id)
    }
    override fun getCoupons(): Flow<List<Coupon>> {
        return couponDao.getCoupons()
    }
}