package com.example.mvvmassignment.ui.add_edit_coupon

import android.app.DatePickerDialog
import android.util.Log
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mvvmassignment.R
import com.example.mvvmassignment.util.CouponUIEvents
import kotlinx.coroutines.flow.collect
import java.util.*

@Composable
fun AddEditCouponScreen(
    onPopBackStack: () -> Unit,
    viewModel: AddEditCouponViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    var expanded by remember { mutableStateOf(false) }
    val couponTypes = listOf("Percentage", "Monetary")
    val activity = LocalContext.current
    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.ArrowDropDown
    // For Starting Date
    val startYear: Int
    val startMonth: Int
    val startDay: Int
    val startCalendar = Calendar.getInstance()
    startYear = startCalendar.get(Calendar.YEAR)
    startMonth = startCalendar.get(Calendar.MONTH)
    startDay = startCalendar.get(Calendar.DAY_OF_MONTH)
    startCalendar.time = Date()

    val startDatePickerDialog = DatePickerDialog(
        activity,
        { _: DatePicker, startYear: Int, startMonth: Int, startDay: Int ->
            viewModel.startDate = "$startDay/${startMonth + 1}/$startYear"
            startCalendar.set(startYear, startMonth, startDay)
            viewModel.startDateMl = startCalendar.timeInMillis
            viewModel.unlimitedTime = false
        }, startYear, startMonth, startDay
    )
    // For End Date
    val endYear: Int
    val endMonth: Int
    val endDay: Int
    val endCalendar = Calendar.getInstance()
//    val endCal = viewModel.startDateMl
    endYear = endCalendar.get(Calendar.YEAR)
    endMonth = endCalendar.get(Calendar.MONTH)
    endDay = endCalendar.get(Calendar.DAY_OF_MONTH)
    endCalendar.time = Date()
    val endDatePickerDialog = DatePickerDialog(
        activity,
        { _: DatePicker, endYear: Int, endMonth: Int, endDay: Int ->
            viewModel.endDate = "$endDay/${endMonth + 1}/$endYear"
            viewModel.unlimitedTime = false
        }, endYear, endMonth, endDay
    )
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is CouponUIEvents.PopBackStack -> onPopBackStack()
                is CouponUIEvents.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                }
                else -> Unit
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "Add New Coupon",
                    fontFamily = FontFamily(Font(R.font.bold)),
                    fontSize = 20.sp,
                    color = colorResource(
                        id = R.color.purple_700
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 30.dp, 0.dp, 20.dp)
                )
            }
            OutlinedTextField(
                value = viewModel.title,
                onValueChange = { viewModel.onEvent(AddEditCouponEvent.OnTitleChange(it)) },
                label = {
                    Text(text = "Title")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = colorResource(
                        id = R.color.purple_700
                    ),
                    unfocusedBorderColor = Color.Gray
                ),
                textStyle = TextStyle(
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.bold)),
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Column(modifier = Modifier.weight(0.5F)) {
                    Box(modifier = Modifier.clickable {
                        expanded = !expanded
                    }) {
                        OutlinedTextField(
                            enabled = false,
                            value = viewModel.type,
                            onValueChange = {
                                viewModel.type = it
                                expanded = false
                                viewModel.onEvent(AddEditCouponEvent.OnTypeChange(it, viewModel.value))
                            },
                            modifier = Modifier
                                .clickable {
                                    expanded = !expanded
                                },
                            label = { Text("Type") },
                            trailingIcon = {
                                Icon(icon, "contentDescription",
                                    Modifier.clickable { expanded = !expanded })
                            }
                        )
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                        ) {
                            couponTypes.forEach { label ->
                                DropdownMenuItem(onClick = {
                                    viewModel.type = label
                                    expanded = !expanded
                                    viewModel.value = ""
                                }) {
                                    Text(text = label)
                                }
                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(0.5F)
                        .padding(10.dp, 0.dp, 0.dp, 0.dp)
                ) {
                    OutlinedTextField(
                        value = viewModel.value,
                        onValueChange = {
                            viewModel.onEvent(AddEditCouponEvent.OnValueChange(it))
                        },
                        label = {
                            Text(text = "Value")
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = colorResource(
                                id = R.color.purple_700
                            ),
                            unfocusedBorderColor = Color.Gray
                        ),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.bold)),
                            fontWeight = FontWeight.Normal,
                            fontSize = 18.sp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    )
                }

            }
            Row() {
                Column(modifier = Modifier.weight(0.5f)) {
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = viewModel.startDate,
                        enabled = false,
                        onValueChange = {
                            viewModel.onEvent(AddEditCouponEvent.OnStartDateChange(it, viewModel.startDateMl)) },
                        label = {
                            Text(text = "Start Date")
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = colorResource(
                                id = R.color.purple_700
                            ),
                            unfocusedBorderColor = Color.Gray
                        ),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.bold)),
                            fontWeight = FontWeight.Normal,
                            fontSize = 18.sp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .clickable {
                                startDatePickerDialog.datePicker.minDate =
                                    startCalendar.timeInMillis
                                startDatePickerDialog.show()
                            }
                    )

                }
                Column(
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(10.dp, 0.dp, 0.dp, 0.dp)
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = viewModel.endDate,
                        enabled = false,
                        onValueChange = { viewModel.onEvent(AddEditCouponEvent.OnEndDateChange(it)) },
                        label = {
                            Text(text = "End Date")
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = colorResource(
                                id = R.color.purple_700
                            ),
                            unfocusedBorderColor = Color.Gray
                        ),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.bold)),
                            fontWeight = FontWeight.Normal,
                            fontSize = 18.sp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .clickable {
                                endDatePickerDialog.datePicker.minDate = viewModel.startDateMl
                                endDatePickerDialog.show()
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                Checkbox(
                    checked = viewModel.unlimitedTime,
                    onCheckedChange = { isChecked ->
                        viewModel.startDate = ""
                        viewModel.endDate = ""
                        viewModel.onEvent(AddEditCouponEvent.OnUnlimitedTimeChange(isChecked))
                    }
                )
                Text(
                    text = "Unlimited Time",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 0.dp, 0.dp, 0.dp)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .padding(0.dp, 10.dp, 0.dp, 10.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.purple_700)),
                    shape = CircleShape, onClick = {
                        viewModel.onEvent(AddEditCouponEvent.OnSaveCouponClick)
                    }) {
                    Text(
                        "Save", fontFamily = FontFamily(Font(R.font.regular)),
                        color = Color.White,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(40.dp, 7.dp, 40.dp, 7.dp)
                    )
                }
            }
        }
    }
}