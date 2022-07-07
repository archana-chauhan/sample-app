package com.example.mvvmassignment.ui.coupon_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mvvmassignment.R
import com.example.mvvmassignment.util.CouponUIEvents
import kotlinx.coroutines.flow.collect

@Composable
fun CouponListScreen(
    onNavigate: (CouponUIEvents.Navigate) -> Unit,
    viewModel: CouponListViewModel = hiltViewModel()
) {
    val coupons = viewModel.coupons.collectAsState(initial = emptyList())
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is CouponUIEvents.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(CouponListEvent.OnAddCouponClick)
                },
                backgroundColor = colorResource(id = R.color.purple_700)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    )
            }
        }
    ) {
        Column() {
            Text(
                text = "Please Add coupons \n(Click on + icon)",
                fontFamily = FontFamily(Font(R.font.medium)),
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = colorResource(
                    id = R.color.black
                ),
                modifier = Modifier
                    .padding(30.dp, 20.dp, 30.dp, 20.dp)
                    .fillMaxWidth()
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(coupons.value) { coupon ->
                    CouponItem(
                        coupon = coupon,
                        onEvent = viewModel::onEvent,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.onEvent(CouponListEvent.OnCouponClick(coupon))
                            }
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}