package com.example.mvvmassignment.data

import androidx.room.*

@Database(
    entities = [Coupon::class],
    version = 1
)
abstract class CouponDatabase : RoomDatabase() {
    abstract val couponDao: CouponDao
}