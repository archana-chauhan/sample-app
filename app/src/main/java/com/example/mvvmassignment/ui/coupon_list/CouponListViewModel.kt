package com.example.mvvmassignment.ui.coupon_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmassignment.data.Coupon
import com.example.mvvmassignment.data.CouponRepository
import com.example.mvvmassignment.util.CouponUIEvents
import com.example.mvvmassignment.util.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CouponListViewModel @Inject constructor(
    private val repository: CouponRepository
): ViewModel() {
    val coupons = repository.getCoupons()
    private val _uiEvent =  Channel<CouponUIEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()
    fun onEvent(event: CouponListEvent) {
        when(event) {
            is CouponListEvent.OnCouponClick -> {
                sendUiEvent(CouponUIEvents.Navigate(Routes.ADD_EDIT_COUPON + "?couponId=${event.coupon.id}"))
            }
            is CouponListEvent.OnAddCouponClick -> {
                sendUiEvent(CouponUIEvents.Navigate(Routes.ADD_EDIT_COUPON))
            }
        }
    }

    private fun sendUiEvent(event: CouponUIEvents) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}