package gr.tsambala.tutorbilling.ui.student

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import gr.tsambala.tutorbilling.data.model.Lesson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentScreen(
    studentId: String?,
    onNavigateBack: () -> Unit,
    onNavigateToLesson: (Long) -> Unit,
    onAddLesson: () -> Unit,
    viewModel: StudentViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (uiState.isEditMode) {
                            if (studentId == "new") "Add Student" else "Edit Student"
                        } else {
                            uiState.name
                        }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (!uiState.isEditMode && studentId != "new") {
                        IconButton(onClick = { viewModel.toggleEditMode() }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit")
                        }
                        IconButton(onClick = { viewModel.deleteStudent(onNavigateBack) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            if (!uiState.isEditMode && studentId != "new") {
                FloatingActionButton(onClick = onAddLesson) {
                    Icon(Icons.Default.Add, contentDescription = "Add Lesson")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (uiState.isEditMode) {
                // Edit mode
                EditStudentForm(
                    uiState = uiState,
                    viewModel = viewModel,
                    onSave = {
                        viewModel.saveStudent()
                        if (studentId == "new") onNavigateBack()
                    }
                )
            } else {
                // View mode
                StudentDetails(
                    uiState = uiState,
                    onLessonClick = onNavigateToLesson,
                    onDeleteLesson = { viewModel.deleteLesson(it) }
                )
            }
        }
    }
}

@Composable
fun EditStudentForm(
    uiState: StudentUiState,
    viewModel: StudentViewModel,
    onSave: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = uiState.name,
            onValueChange = viewModel::updateName,
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = uiState.rateType == "hourly",
                onClick = { viewModel.updateRateType("hourly") },
                label = { Text("Hourly") }
            )
            FilterChip(
                selected = uiState.rateType == "per_lesson",
                onClick = { viewModel.updateRateType("per_lesson") },
                label = { Text("Per Lesson") }
            )
        }

        OutlinedTextField(
            value = uiState.rate,
            onValueChange = viewModel::updateRate,
            label = { Text("Rate (€)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = onSave,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save")
        }
    }
}

@Composable
fun StudentDetails(
    uiState: StudentUiState,
    onLessonClick: (Long) -> Unit,
    onDeleteLesson: (Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Student Info Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Rate",
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        text = if (uiState.rateType == "hourly") {
                            "€${uiState.rate}/hour"
                        } else {
                            "€${uiState.rate}/lesson"
                        },
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
        }

        // Earnings Summary
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Card(
                    modifier = Modifier.weight(1f)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Week",
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            text = "€%.2f".format(uiState.weekEarnings),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Card(
                    modifier = Modifier.weight(1f)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Month",
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            text = "€%.2f".format(uiState.monthEarnings),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Card(
                    modifier = Modifier.weight(1f)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Total",
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            text = "€%.2f".format(uiState.totalEarnings),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Lessons Section
        item {
            Text(
                text = "Lessons",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        }

        items(
            items = uiState.lessons,
            key = { it.id }
        ) { lesson ->
            LessonCard(
                lesson = lesson,
                onLessonClick = { onLessonClick(lesson.id) },
                onDeleteClick = { onDeleteLesson(lesson.id) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonCard(
    lesson: Lesson,
    onLessonClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onLessonClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = lesson.date,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${lesson.startTime} - ${lesson.durationMinutes} min",
                    style = MaterialTheme.typography.bodyMedium
                )
                lesson.notes?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "€%.2f".format(lesson.calculateFee()),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = { showDeleteDialog = true }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Lesson") },
            text = { Text("Are you sure you want to delete this lesson?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteClick()
                        showDeleteDialog = false
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}