package gr.tsambala.tutorbilling.ui.lesson

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import gr.tsambala.tutorbilling.data.model.RateTypes
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonScreen(
    studentId: String?,
    lessonId: String?,
    onNavigateBack: () -> Unit,
    viewModel: LessonViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (lessonId == "new") "Add Lesson" else "Edit Lesson"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    var showDelete by remember { mutableStateOf(false) }
                    if (lessonId != "new") {
                        IconButton(onClick = { showDelete = true }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                    TextButton(
                        onClick = {
                            viewModel.saveLesson()
                            onNavigateBack()
                        },
                        enabled = viewModel.isFormValid()
                    ) {
                        Text("Save")
                    }

                    if (showDelete) {
                        AlertDialog(
                            onDismissRequest = { showDelete = false },
                            title = { Text("Delete Lesson") },
                            text = { Text("Are you sure you want to delete this lesson?") },
                            confirmButton = {
                                TextButton(onClick = {
                                    viewModel.deleteLesson(onNavigateBack)
                                    showDelete = false
                                }) {
                                    Text("Delete", color = MaterialTheme.colorScheme.error)
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { showDelete = false }) { Text("Cancel") }
                            }
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Student info (read-only)
            if (uiState.studentName.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = uiState.studentName,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (uiState.studentRateType == RateTypes.HOURLY) {
                                "€${uiState.studentRate}/hour"
                            } else {
                                "€${uiState.studentRate}/lesson"
                            },
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            // Date input
            var showDatePicker by remember { mutableStateOf(false) }
            val datePickerState = rememberDatePickerState()
            if (showDatePicker) {
                DatePickerDialog(
                    onDismissRequest = { showDatePicker = false },
                    confirmButton = {
                        TextButton(onClick = {
                            showDatePicker = false
                            datePickerState.selectedDateMillis?.let { millis ->
                                val date = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate()
                                viewModel.updateDate(date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                            }
                        }) { Text("OK") }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDatePicker = false }) { Text("Cancel") }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }
            OutlinedTextField(
                value = uiState.date,
                onValueChange = {},
                readOnly = true,
                label = { Text("Date") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDatePicker = true },
                singleLine = true
            )

            // Time input
            var showTimePicker by remember { mutableStateOf(false) }
            val timeState = rememberTimePickerState()
            if (showTimePicker) {
                TimePickerDialog(
                    onDismissRequest = { showTimePicker = false },
                    confirmButton = {
                        TextButton(onClick = {
                            showTimePicker = false
                            viewModel.updateStartTime("%02d:%02d".format(timeState.hour, timeState.minute))
                        }) { Text("OK") }
                    },
                    dismissButton = {
                        TextButton(onClick = { showTimePicker = false }) { Text("Cancel") }
                    }
                ) { TimePicker(state = timeState) }
            }
            OutlinedTextField(
                value = uiState.startTime,
                onValueChange = {},
                readOnly = true,
                label = { Text("Start Time") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showTimePicker = true },
                singleLine = true
            )

            // Duration input
            OutlinedTextField(
                value = uiState.durationMinutes,
                onValueChange = viewModel::updateDuration,
                label = { Text("Duration (minutes)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Fee calculation
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Lesson Fee",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "€%.2f".format(viewModel.calculateFee()),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Notes
            OutlinedTextField(
                value = uiState.notes,
                onValueChange = viewModel::updateNotes,
                label = { Text("Notes (optional)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5
            )
        }
    }
}