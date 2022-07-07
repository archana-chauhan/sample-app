package com.example.mvvmassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mvvmassignment.ui.add_edit_coupon.AddEditCouponScreen
import com.example.mvvmassignment.ui.coupon_list.CouponListScreen
import com.example.mvvmassignment.util.Routes
import com.plcoding.mvvmtodoapp.ui.theme.MVVMTodoAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVVMTodoAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Routes.COUPON_LIST
                ) {
                    composable(Routes.COUPON_LIST) {
                        CouponListScreen(
                            onNavigate = {
                                navController.navigate(it.routes)
                            }
                        )
                    }
                    composable(
                        route = Routes.ADD_EDIT_COUPON + "?couponId={couponId}",
                        arguments = listOf(
                            navArgument(name = "couponId") {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ) {
                        AddEditCouponScreen(onPopBackStack = {
                            navController.popBackStack()
                        })
                    }
                }
            }
        }
    }
}