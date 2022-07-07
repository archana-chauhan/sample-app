package com.example.mvvmassignment.data

import androidx.room.*

@Entity
data class Coupon(
    val title: String,
    val type: String,
    val value: String,
    val start_date: String,
    val end_date: String,
    val unlimited_time: Boolean,
    @PrimaryKey val id: Int? = null
)