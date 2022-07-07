package com.example.mvvmassignment.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CouponDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCoupon(coupon: Coupon)

    @Query("SELECT * FROM Coupon WHERE id = :id")
    suspend fun getCouponById(id: Int): Coupon

    @Query("SELECT * FROM Coupon")
    fun getCoupons(): Flow<List<Coupon>>

}