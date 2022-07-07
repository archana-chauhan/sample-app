package com.example.mvvmassignment.ui.coupon_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mvvmassignment.R
import com.example.mvvmassignment.data.Coupon

@Composable
fun CouponItem(
    coupon: Coupon,
    onEvent: (CouponListEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .padding(16.dp, 10.dp, 16.dp, 10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            backgroundColor = MaterialTheme.colors.surface,
            elevation = 10.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                    ) {
                        Column(modifier = Modifier.weight(0.9f)) {
                            Text(
                                text = coupon.title,
                                fontFamily = FontFamily(Font(R.font.bold)),
                                fontSize = 24.sp,
                                color = colorResource(
                                    id = R.color.purple_700
                                ),
                            )
                        }
                        Column(modifier = Modifier.weight(0.1f)) {
                            IconButton(onClick = {
                                onEvent(CouponListEvent.OnCouponClick(coupon))
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit",
                                    modifier = Modifier.size(24.dp),
                                )
                            }
                        }
                    }
                    Text(
                        text = if (coupon.type.equals("Percentage")) {
                            coupon.value + "%" + " OFF"
                        } else {
                            "$" + coupon.value
                        },
                        fontFamily = FontFamily(Font(R.font.medium)),
                        fontSize = 20.sp,
                        color = colorResource(
                            id = R.color.black
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Text(
                        text = if (!coupon.start_date.isBlank() && !coupon.end_date.isBlank()) {
                            "Grab this offer which starts on ${coupon.start_date} & ends on ${coupon.end_date}"
                        } else {
                            ""
                        },
                        fontFamily = FontFamily(Font(R.font.regular)),
                        fontSize = 16.sp,
                        color = colorResource(
                            id = R.color.black
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }

            }
        }
    }
}
