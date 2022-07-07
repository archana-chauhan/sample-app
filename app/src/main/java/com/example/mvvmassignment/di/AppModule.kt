package com.example.mvvmassignment.di

import android.app.Application
import androidx.room.Room
import com.example.mvvmassignment.data.CouponDatabase
import com.example.mvvmassignment.data.CouponRepository
import com.example.mvvmassignment.data.CouponRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideCouponDatabase(app: Application): CouponDatabase {
        return Room.databaseBuilder(app, CouponDatabase::class.java, "coup_db").build()
    }
    @Provides
    @Singleton
    fun provideCouponRepository(db: CouponDatabase): CouponRepository {
        return CouponRepositoryImpl(db.couponDao)
    }
}