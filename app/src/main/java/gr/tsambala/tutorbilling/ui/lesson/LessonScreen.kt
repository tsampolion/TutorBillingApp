package gr.tsambala.tutorbilling.ui.lesson

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

/**
 * LessonScreen handles creating and editing lessons.
 *
 * This screen provides an intuitive interface for:
 * - Selecting a student (if not preselected)
 * - Choosing date and time with native pickers
 * - Setting duration with quick-select buttons or custom input
 * - Adding optional notes
 * - Seeing real-time fee calculation
 *
 * The UI adapts based on whether we're creating a new lesson or editing an existing one.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonScreen(
    onNavigateBack: () -> Unit,
    viewModel: LessonViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Date picker dialog
    if (uiState.showDatePicker) {
        DatePickerDialog(
            selectedDate = uiState.date,
            onDateSelected = viewModel::updateDate,
            onDismiss = viewModel::toggleDatePicker
        )
    }

    // Time picker dialog
    if (uiState.showTimePicker) {
        TimePickerDialog(
            selectedTime = uiState.startTime,
            onTimeSelected = viewModel::updateStartTime,
            onDismiss = viewModel::toggleTimePicker
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (uiState.isEditMode) "Edit Lesson" else "Add Lesson"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.Close, contentDescription = "Cancel")
                    }
                },
                actions = {
                    TextButton(
                        onClick = {
                            viewModel.saveLesson(onSuccess = onNavigateBack)
                        },
                        enabled = !uiState.isSaving,
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        if (uiState.isSaving) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            Text("Save")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            uiState.errorMessage != null -> {
                ErrorDisplay(
                    message = uiState.errorMessage,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                )
            }

            else -> {
                LessonForm(
                    uiState = uiState,
                    viewModel = viewModel,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }
        }
    }
}

/**
 * Main lesson form containing all input fields.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LessonForm(
    uiState: LessonViewModel.LessonUiState,
    viewModel: LessonViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Student selection dropdown
        if (uiState.selectedStudent == null || uiState.students.size > 1) {
            StudentSelectionSection(
                students = uiState.students,
                selectedStudent = uiState.selectedStudent,
                onStudentSelected = viewModel::selectStudent,
                error = uiState.validationErrors.studentError
            )
        } else {
            // Show selected student as read-only when preselected
            SelectedStudentCard(
                student = uiState.selectedStudent
            )
        }

        // Date and time section
        DateTimeSection(
            date = uiState.date,
            startTime = uiState.startTime,
            formattedDate = viewModel.getFormattedDate(),
            formattedTimeRange = viewModel.getFormattedTimeRange(),
            onDateClick = viewModel::toggleDatePicker,
            onTimeClick = viewModel::toggleTimePicker,
            dateError = uiState.validationErrors.dateError,
            timeError = uiState.validationErrors.timeError
        )

        // Duration section with quick select buttons
        DurationSection(
            duration = uiState.durationMinutes,
            quickOptions = uiState.quickDurationOptions,
            onDurationChange = viewModel::updateDuration,
            onQuickSelect = viewModel::setQuickDuration,
            error = uiState.validationErrors.durationError
        )

        // Fee calculation display
        FeeCalculationCard(
            fee = uiState.calculatedFee,
            student = uiState.selectedStudent
        )

        // Optional notes
        OutlinedTextField(
            value = uiState.notes,
            onValueChange = viewModel::updateNotes,
            label = { Text("Notes (optional)") },
            placeholder = { Text("Topics covered, homework assigned, etc.") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            maxLines = 5
        )
    }
}

/**
 * Student selection dropdown section.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StudentSelectionSection(
    students: List<gr.tsambala.tutorbilling.data.model.Student>,
    selectedStudent: gr.tsambala.tutorbilling.data.model.Student?,
    onStudentSelected: (gr.tsambala.tutorbilling.data.model.Student) -> Unit,
    error: String?
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(
            text = "Student",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(4.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            OutlinedTextField(
                value = selectedStudent?.name ?: "",
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("Select a student") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                isError = error != null,
                supportingText = error?.let { { Text(it) } },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                students.forEach { student ->
                    DropdownMenuItem(
                        text = {
                            Column {
                                Text(student.name)
                                Text(
                                    text = student.getFormattedRate(),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        },
                        onClick = {
                            onStudentSelected(student)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

/**
 * Selected student display card (read-only).
 */
@Composable
private fun SelectedStudentCard(
    student: gr.tsambala.tutorbilling.data.model.Student
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = student.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = student.getFormattedRate(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                )
            }
        }
    }
}

/**
 * Date and time selection section.
 */
@Composable
private fun DateTimeSection(
    date: LocalDate,
    startTime: LocalTime,
    formattedDate: String,
    formattedTimeRange: String,
    onDateClick: () -> Unit,
    onTimeClick: () -> Unit,
    dateError: String?,
    timeError: String?
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // Date selector
        Column {
            Text(
                text = "Date",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedCard(
                onClick = onDateClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.CalendarMonth,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = formattedDate,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Change date",
                        modifier = Modifier.size(18.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            dateError?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }
        }

        // Time selector
        Column {
            Text(
                text = "Time",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedCard(
                onClick = onTimeClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Schedule,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = formattedTimeRange,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Change time",
                        modifier = Modifier.size(18.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            timeError?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }
        }
    }
}

/**
 * Duration input section with quick select buttons.
 */
@Composable
private fun DurationSection(
    duration: String,
    quickOptions: List<Int>,
    onDurationChange: (String) -> Unit,
    onQuickSelect: (Int) -> Unit,
    error: String?
) {
    Column {
        Text(
            text = "Duration",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(4.dp))

        // Quick select chips
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            quickOptions.forEach { minutes ->
                FilterChip(
                    selected = duration == minutes.toString(),
                    onClick = { onQuickSelect(minutes) },
                    label = {
                        Text(
                            text = if (minutes < 60) "${minutes}m" else "${minutes / 60}h",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Custom duration input
        OutlinedTextField(
            value = duration,
            onValueChange = onDurationChange,
            label = { Text("Minutes") },
            placeholder = { Text("Custom duration") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            isError = error != null,
            supportingText = error?.let { { Text(it) } },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            suffix = { Text("minutes") }
        )
    }
}

/**
 * Fee calculation display card.
 */
@Composable
private fun FeeCalculationCard(
    fee: Double,
    student: gr.tsambala.tutorbilling.data.model.Student?
) {
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
            Column {
                Text(
                    text = "Lesson Fee",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                if (student != null) {
                    Text(
                        text = "Based on ${student.getFormattedRate()}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
                    )
                }
            }
            Text(
                text = "€%.2f".format(fee),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

/**
 * Date picker dialog using Material 3 DatePicker.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePickerDialog(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate.toEpochDay() * 86400000
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val date = LocalDate.ofEpochDay(millis / 86400000)
                        onDateSelected(date)
                    }
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

/**
 * Time picker dialog using Material 3 TimePicker.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimePickerDialog(
    selectedTime: LocalTime,
    onTimeSelected: (LocalTime) -> Unit,
    onDismiss: () -> Unit
) {
    val timePickerState = rememberTimePickerState(
        initialHour = selectedTime.hour,
        initialMinute = selectedTime.minute,
        is24Hour = true
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select time") },
        text = {
            TimePicker(state = timePickerState)
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val time = LocalTime.of(timePickerState.hour, timePickerState.minute)
                    onTimeSelected(time)
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

/**
 * Error display component.
 */
@Composable
private fun ErrorDisplay(
    message: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.errorContainer
            )
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Error,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }
    }
}