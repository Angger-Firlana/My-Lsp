package com.example.mylsp.screen.asesor.apl

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mylsp.component.HeaderForm
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FR02RKM(navController: NavController, modifier: Modifier = Modifier){
    var namaAsesi by remember { mutableStateOf("") }
    var tanggalAsesi by remember { mutableStateOf("") }
    var namaAsesor by remember { mutableStateOf("") }
    var tanggalAsesor by remember { mutableStateOf("") }

    // Date picker states
    var showDatePickerAsesi by remember { mutableStateOf(false) }
    var showDatePickerAsesor by remember { mutableStateOf(false) }

    val datePickerStateAsesi = rememberDatePickerState()
    val datePickerStateAsesor = rememberDatePickerState()

    // Date formatter
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
            .verticalScroll(rememberScrollState())

    ) {
        HeaderForm(
            "FR.APL.02.ASESMEN MANDIRI"
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Nama Asesi Section
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Nama Asesi",
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Text(
                    text = "Tanggal",
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                OutlinedTextField(
                    value = namaAsesi,
                    onValueChange = { namaAsesi = it },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Gray,
                        focusedBorderColor = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.width(16.dp))

                OutlinedTextField(
                    value = tanggalAsesi,
                    onValueChange = { },
                    modifier = Modifier
                        .width(120.dp)
                        .clickable { showDatePickerAsesi = true },
                    singleLine = true,
                    readOnly = true,
                    placeholder = { Text("Pilih tanggal", fontSize = 12.sp) },
                    trailingIcon = {
                        IconButton(onClick = { showDatePickerAsesi = true }) {
                            Icon(
                                Icons.Default.DateRange,
                                contentDescription = "Calendar",
                                tint = Color.Gray,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Gray,
                        focusedBorderColor = Color.Gray
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Tanda Tangan Asesi Section
        Text(
            text = "Tanda Tangan Asesi",
            fontSize = 14.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Signature Box for Asesi
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                .background(Color.White)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Divider line
        HorizontalDivider(
            color = Color(0xFFF39C12),
            thickness = 2.dp,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Ditinjau oleh Asesor Section
        Text(
            text = "Ditinjau oleh Asesor:",
            fontSize = 14.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Nama Asesor Section
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Nama Asesor",
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Text(
                    text = "Tanggal",
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                OutlinedTextField(
                    value = namaAsesor,
                    onValueChange = { namaAsesor = it },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Gray,
                        focusedBorderColor = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.width(16.dp))

                OutlinedTextField(
                    value = tanggalAsesor,
                    onValueChange = { },
                    modifier = Modifier
                        .width(120.dp)
                        .clickable { showDatePickerAsesor = true },
                    singleLine = true,
                    readOnly = true,
                    placeholder = { Text("Pilih tanggal", fontSize = 12.sp) },
                    trailingIcon = {
                        IconButton(onClick = { showDatePickerAsesor = true }) {
                            Icon(
                                Icons.Default.DateRange,
                                contentDescription = "Calendar",
                                tint = Color.Gray,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Gray,
                        focusedBorderColor = Color.Gray
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Tanda Tangan Asesor Section
        Text(
            text = "Tanda Tangan Asesor",
            fontSize = 14.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Signature Box for Asesor
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                .background(Color.White)
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Kirim Jawaban Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = {
                    // Handle submit
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF39C12)
                ),
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text(
                    text = "Kirim Jawaban",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }

    // Date Picker Dialog for Asesi
    if (showDatePickerAsesi) {
        DatePickerDialog(
            onDismissRequest = { showDatePickerAsesi = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerStateAsesi.selectedDateMillis?.let { millis ->
                            tanggalAsesi = dateFormatter.format(Date(millis))
                        }
                        showDatePickerAsesi = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePickerAsesi = false }) {
                    Text("Batal")
                }
            }
        ) {
            DatePicker(
                state = datePickerStateAsesi,
                showModeToggle = true
            )
        }
    }

    // Date Picker Dialog for Asesor
    if (showDatePickerAsesor) {
        DatePickerDialog(
            onDismissRequest = { showDatePickerAsesor = true },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerStateAsesor.selectedDateMillis?.let { millis ->
                            tanggalAsesor = dateFormatter.format(Date(millis))
                        }
                        showDatePickerAsesor = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePickerAsesor = false }) {
                    Text("Batal")
                }
            }
        ) {
            DatePicker(
                state = datePickerStateAsesor,
                showModeToggle = false
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FR02RKMPreview() {
    MaterialTheme {
        Surface {
            FR02RKM(navController = rememberNavController())
        }
    }
}