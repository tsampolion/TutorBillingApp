package gr.tsambala.tutorbilling.ui.student

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Today
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Warning
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
import gr.tsambala.tutorbilling.data.model.RateType

/**
 * StudentScreen handles both viewing and editing student information.
 *
 * This screen adapts its UI based on the mode:
 * - View mode: Shows student details, lessons, and statistics
 * - Edit mode: Allows modifying student information
 * - Add mode: Shows empty form for creating a new student
 *
 * The screen follows Material Design 3 guidelines with proper elevation,
 * spacing, and interactive elements.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentScreen(
    onNavigateBack: () -> Unit,
    onNavigateToAddLesson: (Long) -> Unit,
    onNavigateToEditLesson: (Long) -> Unit,
    viewModel: StudentViewModel = hiltViewModel(),
    studentId: String?,
    onNavigateToLesson: Function<Unit>,
    onAddLesson: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Remember the delete confirmation dialog state
    if (uiState.showDeleteConfirmation) {
        DeleteConfirmationDialog(
            studentName = uiState.student?.name ?: "",
            onConfirm = {
                viewModel.deleteStudent(onSuccess = onNavigateBack)
            },
            onDismiss = viewModel::hideDeleteConfirmation
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when {
                            uiState.isEditMode && uiState.student == null -> "Add Student"
                            uiState.isEditMode -> "Edit Student"
                            else -> uiState.student?.name ?: "Student Details"
                        }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (!uiState.isEditMode && uiState.student != null) {
                        // View mode actions
                        IconButton(onClick = viewModel::toggleEditMode) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit")
                        }
                        if (uiState.canDelete) {
                            IconButton(onClick = viewModel::showDeleteConfirmation) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete")
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            // Show FAB only in view mode for existing students
            if (!uiState.isEditMode && uiState.student != null) {
                ExtendedFloatingActionButton(
                    onClick = {
                        uiState.student?.let {
                            onNavigateToAddLesson(it.id)
                        }
                    },
                    icon = { Icon(Icons.Default.Add, contentDescription = null) },
                    text = { Text("Add Lesson") }
                )
            }
        }
    ) { paddingValues ->
        when {
            uiState.isLoading -> {
                // Loading state
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
                // Error state
                ErrorDisplay(
                    message = uiState.errorMessage,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                )
            }

            uiState.isEditMode -> {
                // Edit/Add mode
                StudentEditForm(
                    uiState = uiState,
                    viewModel = viewModel,
                    onSave = {
                        viewModel.saveStudent(
                            onSuccess = {
                                if (uiState.student == null) {
                                    onNavigateBack() // Go back after adding
                                } else {
                                    viewModel.toggleEditMode() // Return to view mode
                                }
                            }
                        )
                    },
                    onCancel = {
                        if (uiState.student == null) {
                            onNavigateBack() // Cancel adding
                        } else {
                            viewModel.toggleEditMode() // Cancel editing
                        }
                    },
                    modifier = Modifier.padding(paddingValues)
                )
            }

            else -> {
                // View mode
                StudentDetailView(
                    uiState = uiState,
                    onEditLesson = onNavigateToEditLesson,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}

/**
 * Student detail view showing information and lessons.
 * This is the default view mode for existing students.
 */
@Composable
private fun StudentDetailView(
    uiState: StudentViewModel.StudentUiState,
    onEditLesson: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 80.dp) // Space for FAB
    ) {
        // Student info card
        item {
            StudentInfoCard(
                student = uiState.student,
                modifier = Modifier.padding(16.dp)
            )
        }

        // Statistics cards
        item {
            StatisticsSection(
                totalEarnings = uiState.totalEarnings,
                totalHours = uiState.totalHours,
                totalLessons = uiState.totalLessons,
                currentMonthEarnings = uiState.currentMonthEarnings,
                lastMonthEarnings = uiState.lastMonthEarnings,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        // Lessons section header
        item {
            SectionHeader(
                title = "Lessons",
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 24.dp,
                    bottom = 8.dp
                )
            )
        }

        if (uiState.lessons.isEmpty()) {
            item {
                EmptyLessonsState(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp)
                )
            }
        } else {
            items(
                items = uiState.lessons,
                key = { it.lesson.id }
            ) { lessonDisplay ->
                LessonCard(
                    lessonDisplay = lessonDisplay,
                    onEdit = { onEditLesson(lessonDisplay.lesson.id) },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }
        }
    }
}

/**
 * Student information card showing basic details.
 */
@Composable
private fun StudentInfoCard(
    student: gr.tsambala.tutorbilling.data.model.Student?,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = student?.name ?: "",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    Icons.Default.Paid,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = student?.getFormattedRate() ?: "",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

/**
 * Statistics section showing earnings and lesson counts.
 */
@Composable
private fun StatisticsSection(
    totalEarnings: Double,
    totalHours: Double,
    totalLessons: Int,
    currentMonthEarnings: Double,
    lastMonthEarnings: Double,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Total statistics
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard(
                label = "Total Earnings",
                value = formatCurrency(totalEarnings),
                icon = Icons.Default.Paid,
                modifier = Modifier.weight(1f)
            )
            StatCard(
                label = "Total Hours",
                value = "%.1f".format(totalHours),
                icon = Icons.Default.Timer,
                modifier = Modifier.weight(1f)
            )
            StatCard(
                label = "Lessons",
                value = totalLessons.toString(),
                icon = Icons.Default.Book,
                modifier = Modifier.weight(1f)
            )
        }

        // Monthly comparison
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard(
                label = "This Month",
                value = formatCurrency(currentMonthEarnings),
                icon = Icons.Default.Today,
                modifier = Modifier.weight(1f),
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
            StatCard(
                label = "Last Month",
                value = formatCurrency(lastMonthEarnings),
                icon = Icons.Default.DateRange,
                modifier = Modifier.weight(1f),
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
            )
        }
    }
}

/**
 * Individual statistics card.
 */
@Composable
private fun StatCard(
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier,
    containerColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.surfaceVariant,
    contentColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = contentColor.copy(alpha = 0.6f),
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = contentColor.copy(alpha = 0.7f)
            )
        }
    }
}

/**
 * Section header for organizing content.
 */
@Composable
private fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier
    )
}

/**
 * Individual lesson card showing lesson details.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LessonCard(
    lessonDisplay: StudentViewModel.LessonDisplay,
    onEdit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onEdit,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = lessonDisplay.formattedDate,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "${lessonDisplay.formattedTime} • ${lessonDisplay.formattedDuration}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (!lessonDisplay.lesson.notes.isNullOrBlank()) {
                    Text(
                        text = lessonDisplay.lesson.notes,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1
                    )
                }
            }
            Text(
                text = formatCurrency(lessonDisplay.fee),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

/**
 * Empty state for when no lessons exist.
 */
@Composable
private fun EmptyLessonsState(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "📚",
            style = MaterialTheme.typography.displayMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "No lessons yet",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Tap the button below to add the first lesson",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/**
 * Student edit form for adding or editing student information.
 */
@Composable
private fun StudentEditForm(
    uiState: StudentViewModel.StudentUiState,
    viewModel: StudentViewModel,
    onSave: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Name field
        OutlinedTextField(
            value = uiState.studentName,
            onValueChange = viewModel::updateStudentName,
            label = { Text("Student Name") },
            isError = uiState.validationErrors.nameError != null,
            supportingText = uiState.validationErrors.nameError?.let {
                { Text(it) }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Rate type selection
        Text(
            text = "Rate Type",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = uiState.rateType == RateType.HOURLY,
                onClick = { viewModel.updateRateType(RateType.HOURLY) },
                label = { Text("Hourly Rate") },
                modifier = Modifier.weight(1f)
            )
            FilterChip(
                selected = uiState.rateType == RateType.PER_LESSON,
                onClick = { viewModel.updateRateType(RateType.PER_LESSON) },
                label = { Text("Per Lesson") },
                modifier = Modifier.weight(1f)
            )
        }

        // Rate input based on type
        AnimatedVisibility(
            visible = uiState.rateType == RateType.HOURLY,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            OutlinedTextField(
                value = uiState.hourlyRate,
                onValueChange = viewModel::updateHourlyRate,
                label = { Text("Hourly Rate (€)") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                ),
                isError = uiState.rateType == RateType.HOURLY &&
                        uiState.validationErrors.rateError != null,
                supportingText = if (uiState.rateType == RateType.HOURLY) {
                    uiState.validationErrors.rateError?.let { { Text(it) } }
                } else null,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                prefix = { Text("€") }
            )
        }

        AnimatedVisibility(
            visible = uiState.rateType == RateType.PER_LESSON,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            OutlinedTextField(
                value = uiState.perLessonRate,
                onValueChange = viewModel::updatePerLessonRate,
                label = { Text("Rate Per Lesson (€)") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                ),
                isError = uiState.rateType == RateType.PER_LESSON &&
                        uiState.validationErrors.rateError != null,
                supportingText = if (uiState.rateType == RateType.PER_LESSON) {
                    uiState.validationErrors.rateError?.let { { Text(it) } }
                } else null,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                prefix = { Text("€") }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Action buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier.weight(1f)
            ) {
                Text("Cancel")
            }
            Button(
                onClick = onSave,
                modifier = Modifier.weight(1f),
                enabled = !uiState.isSaving
            ) {
                if (uiState.isSaving) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Save")
                }
            }
        }
    }
}

/**
 * Delete confirmation dialog.
 */
@Composable
private fun DeleteConfirmationDialog(
    studentName: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                Icons.Default.Warning,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error
            )
        },
        title = { Text("Delete Student?") },
        text = {
            Text("Are you sure you want to delete $studentName? This action cannot be undone.")
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Delete")
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
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Default.Error,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

/**
 * Helper function to format currency consistently.
 */
private fun formatCurrency(amount: Double): String {
    return "€%.2f".format(amount)
}