package gr.tsambala.tutorbilling.ui.lesson

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.ui.platform.LocalContext
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
    val context = LocalContext.current

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
            // Student info or picker
            if (uiState.availableStudents.isEmpty()) {
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
            } else {
                var expanded by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = uiState.selectedStudentId?.let { id ->
                            uiState.availableStudents.firstOrNull { it.id == id }?.getFullName()
                        } ?: "",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Student*") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        uiState.availableStudents.forEach { student ->
                            DropdownMenuItem(
                                text = { Text(student.getFullName()) },
                                onClick = {
                                    viewModel.updateSelectedStudent(student.id)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            // Date input
            OutlinedTextField(
                value = uiState.date,
                onValueChange = {},
                readOnly = true,
                label = { Text("Date") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val current = try {
                            LocalDate.parse(uiState.date, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                        } catch (_: Exception) {
                            LocalDate.now()
                        }
                        DatePickerDialog(
                            context,
                            { _, year, month, day ->
                                val date = LocalDate.of(year, month + 1, day)
                                viewModel.updateDate(date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                            },
                            current.year,
                            current.monthValue - 1,
                            current.dayOfMonth
                        ).show()
                    },
                singleLine = true
            )

            // Time input
            OutlinedTextField(
                value = uiState.startTime,
                onValueChange = {},
                readOnly = true,
                label = { Text("Start Time") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val (h, m) = uiState.startTime.split(":").mapNotNull { it.toIntOrNull() }
                            .let { if (it.size == 2) it[0] to it[1] else LocalTime.now().hour to LocalTime.now().minute }
                        TimePickerDialog(
                            context,
                            { _, hour, minute ->
                                viewModel.updateStartTime("%02d:%02d".format(hour, minute))
                            },
                            h,
                            m,
                            true
                        ).show()
                    },
                singleLine = true
            )

            if (uiState.studentRateType == RateTypes.HOURLY) {
                OutlinedTextField(
                    value = uiState.durationMinutes,
                    onValueChange = viewModel::updateDuration,
                    label = { Text("Duration (minutes)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }

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

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onNavigateBack,
                    modifier = Modifier.weight(1f)
                ) { Text("Cancel") }
                Button(
                    onClick = {
                        viewModel.saveLesson()
                        onNavigateBack()
                    },
                    modifier = Modifier.weight(1f),
                    enabled = viewModel.isFormValid()
                ) { Text("Save") }
            }
        }
    }
}