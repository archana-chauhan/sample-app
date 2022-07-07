package com.example.mvvmassignment.ui.add_edit_coupon

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmassignment.data.Coupon
import com.example.mvvmassignment.data.CouponRepository
import com.example.mvvmassignment.util.CouponUIEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditCouponViewModel @Inject constructor(
    private val repository: CouponRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var coupon by mutableStateOf<Coupon?>(null)
        private set
    var title by mutableStateOf("")
        private set
    var type by mutableStateOf("")
    var value by mutableStateOf("")
    var startDate by mutableStateOf("")
    var startDateMl by mutableStateOf(0L)
    var endDate by mutableStateOf("")
    var unlimitedTime by mutableStateOf(false)
    private val _uiEvent = Channel<CouponUIEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()
    init {
        val couponId = savedStateHandle.get<Int>("couponId")!!
        if (couponId != -1) {
            viewModelScope.launch {
                repository.getCouponById(couponId)?.let { coupon ->
                    title = coupon.title
                    type = coupon.type
                    value = coupon.value
                    startDate = coupon.start_date
                    startDateMl = coupon.start_date_ml
                    endDate = coupon.end_date
                    unlimitedTime = coupon.unlimited_time
                    this@AddEditCouponViewModel.coupon = coupon
                }
            }
        }
    }

    fun onEvent(event: AddEditCouponEvent) {
        when (event) {
            is AddEditCouponEvent.OnTitleChange -> {
                title = event.title
            }
            is AddEditCouponEvent.OnTypeChange -> {
                type = event.type
                value = event.value2
                if (type.equals("Percentage")) {
                    if (!value.isBlank()) {
                        value = ""
                    }
                    else {
                        value = value
                    }
                }
                else if (type.equals("Monetary")) {
                    if (!value.isBlank()) {
                        value = ""
                    }
                    else {
                        value = String.format("%.2f", value.toDouble())
                    }

                }
            }
            is AddEditCouponEvent.OnValueChange -> {
                if (type.equals("Percentage")) {
                    value = event.value
                }
                else if (type.equals("Monetary")) {
                    value = String.format("%.2f", event.value.toDouble())
                }
            }
            is AddEditCouponEvent.OnStartDateChange -> {
                startDate = event.start_date
                startDateMl = event.start_date_ml
            }
            is AddEditCouponEvent.OnEndDateChange -> {
                endDate = event.end_date
            }
            is AddEditCouponEvent.OnUnlimitedTimeChange -> {
                unlimitedTime = event.unlimited_time
            }
            is AddEditCouponEvent.OnSaveCouponClick -> {
                viewModelScope.launch {
                    if (title.isBlank()) {
                        sendUiEvent(
                            CouponUIEvents.ShowSnackbar(
                                message = "Please fill title"
                            )
                        )
                        return@launch
                    }
                    if (type.isBlank()) {
                        sendUiEvent(
                            CouponUIEvents.ShowSnackbar(
                                message = "Please select coupon type"
                            )
                        )
                        return@launch
                    }
                    if (value.isBlank()) {
                        sendUiEvent(
                            CouponUIEvents.ShowSnackbar(
                                message = "Please enter coupon value"
                            )
                        )
                        return@launch
                    }
                    if (unlimitedTime) {
                        startDate = ""
                        endDate = ""
                    }


//                    if (startDate.isBlank()) {
//                        sendUiEvent(
//                            CouponUIEvents.ShowSnackbar(
//                                message = "Please select start date"
//                            )
//                        )
//                        return@launch
//                    }
//                    if (endDate.isBlank()) {
//                        sendUiEvent(
//                            CouponUIEvents.ShowSnackbar(
//                                message = "Please select end date"
//                            )
//                        )
//                        return@launch
//                    }
                    repository.addCoupon(
                        Coupon(
                            id = coupon?.id,
                            title = title,
                            type = type,
                            value = value,
                            start_date = startDate,
                            start_date_ml = startDateMl,
                            end_date = endDate,
                            unlimited_time = unlimitedTime,
                        )
                    )
                    sendUiEvent(CouponUIEvents.PopBackStack)
                }
            }
        }
    }

    private fun sendUiEvent(event: CouponUIEvents) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}