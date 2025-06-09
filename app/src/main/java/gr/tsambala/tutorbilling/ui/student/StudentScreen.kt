package gr.tsambala.tutorbilling.ui.student

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.material3.MenuAnchorType
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import gr.tsambala.tutorbilling.data.model.Lesson
import gr.tsambala.tutorbilling.data.model.RateTypes
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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
    var showDeleteDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when {
                            uiState.isEditMode && studentId == "new" -> "Add Student"
                            uiState.isEditMode -> "Edit Student"
                            else -> "${uiState.name} ${uiState.surname}".trim()
                        }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (!uiState.isEditMode && studentId != "new") {
                        IconButton(onClick = { viewModel.toggleEditMode() }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit")
                        }
                        IconButton(onClick = { showDeleteDialog = true }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
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
        if (uiState.isEditMode) {
            StudentEditForm(
                uiState = uiState,
                viewModel = viewModel,
                onSave = {
                    viewModel.saveStudent()
                    if (studentId == "new") {
                        onNavigateBack()
                    }
                },
                onCancel = {
                    if (studentId == "new") {
                        onNavigateBack()
                    } else {
                        viewModel.toggleEditMode()
                    }
                },
                modifier = Modifier.padding(paddingValues)
            )
        } else {
            StudentDetailView(
                uiState = uiState,
                viewModel = viewModel,
                onLessonClick = onNavigateToLesson,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Student") },
            text = { Text("Are you sure you want to delete ${uiState.name} ${uiState.surname}? This will also delete all lessons.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteStudent(onNavigateBack)
                        showDeleteDialog = false
                    }
                ) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
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

@Composable
private fun StudentDetailView(
    uiState: StudentUiState,
    viewModel: StudentViewModel,
    onLessonClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "${uiState.name} ${uiState.surname}".trim(),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Class: ${uiState.className}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (uiState.rateType == RateTypes.HOURLY) {
                            "€${uiState.rate}/hour"
                        } else {
                            "€${uiState.rate}/lesson"
                        },
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "€%.2f".format(uiState.weekEarnings),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "This week",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "€%.2f".format(uiState.monthEarnings),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "This month",
                            style = MaterialTheme.typography.bodySmall
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
                            text = "€%.2f".format(uiState.totalEarnings),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Total",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Lessons",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            HorizontalDivider()
        }

        if (uiState.lessons.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No lessons yet.\nTap + to add a lesson.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            items(
                items = uiState.lessons,
                key = { it.id }
            ) { lesson ->
                val rate = uiState.rate.toDoubleOrNull() ?: 0.0
                val fee = if (uiState.rateType == RateTypes.HOURLY)
                    (lesson.durationMinutes / 60.0) * rate else rate
                LessonCard(
                    lesson = lesson,
                    fee = fee,
                    onLessonClick = { onLessonClick(lesson.id) },
                    onDeleteClick = { viewModel.deleteLesson(lesson.id) }
                )
                HorizontalDivider()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LessonCard(
    lesson: Lesson,
    fee: Double,
    onLessonClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    Card(
        onClick = onLessonClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
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
                    text = LocalDate.parse(lesson.date)
                        .format(DateTimeFormatter.ofPattern("MMM d, yyyy")),
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = "${lesson.startTime} • ${lesson.durationMinutes} min",
                    style = MaterialTheme.typography.bodyMedium
                )
                lesson.notes?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "€%.2f".format(fee),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = { showDeleteDialog = true }) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete",
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.error
                    )
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
                    Text("Delete", color = MaterialTheme.colorScheme.error)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StudentEditForm(
    uiState: StudentUiState,
    viewModel: StudentViewModel,
    onSave: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    val nameError = uiState.name.isBlank()
    val surnameError = uiState.surname.isBlank()
    val phoneError = !viewModel.isPhoneValid(uiState.parentMobile)
    val emailError = uiState.parentEmail.isNotBlank() && !viewModel.isEmailValid(uiState.parentEmail)
    val rateError = uiState.rate.toDoubleOrNull() == null

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .imePadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = uiState.name,
            onValueChange = viewModel::updateName,
            label = { Text("First Name*") },
            isError = nameError,
            supportingText = { if (nameError) Text("Required") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = uiState.surname,
            onValueChange = viewModel::updateSurname,
            label = { Text("Last Name*") },
            isError = surnameError,
            supportingText = { if (surnameError) Text("Required") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = uiState.parentMobile,
            onValueChange = viewModel::updateParentMobile,
            label = { Text("Parent Mobile*") },
            isError = phoneError,
            supportingText = { if (phoneError) Text("Enter 10 digits") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = uiState.parentEmail,
            onValueChange = viewModel::updateParentEmail,
            label = { Text("Parent Email (optional)") },
            isError = emailError,
            supportingText = { if (emailError) Text("Invalid email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Text(
            text = "Rate Type",
            style = MaterialTheme.typography.titleSmall
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = uiState.rateType == RateTypes.HOURLY,
                onClick = { viewModel.updateRateType(RateTypes.HOURLY) },
                label = { Text("Hourly") },
                modifier = Modifier.weight(1f)
            )
            FilterChip(
                selected = uiState.rateType == RateTypes.PER_LESSON,
                onClick = { viewModel.updateRateType(RateTypes.PER_LESSON) },
                label = { Text("Per Lesson") },
                modifier = Modifier.weight(1f)
            )
        }

        OutlinedTextField(
            value = uiState.rate,
            onValueChange = viewModel::updateRate,
            label = {
                Text(
                    if (uiState.rateType == RateTypes.HOURLY) "Hourly Rate (€)*" else "Rate per Lesson (€)*"
                )
            },
            isError = rateError,
            supportingText = { if (rateError) Text("Required") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            prefix = { Text("€") }
        )

        val classes = listOf(
            "Junior A", "Junior B", "Junior A&B (One year Course)",
            "Senior A", "Senior B", "Senior C", "Senior D/B1",
            "Pre-Lower/B1+", "B2", "C1", "Proficiency/C2", "Custom", "Unassigned"
        )
        var expanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = uiState.className,
                onValueChange = {},
                readOnly = true,
                label = { Text("Class*") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                modifier = Modifier
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable, enabled = true)
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                classes.forEach { cls ->
                    DropdownMenuItem(
                        text = { Text(cls) },
                        onClick = {
                            viewModel.updateClassName(cls)
                            expanded = false
                        }
                    )
                }
            }
        }

        if (uiState.className == "Custom") {
            OutlinedTextField(
                value = uiState.customClass,
                onValueChange = viewModel::updateCustomClass,
                label = { Text("Class Description*") },
                isError = uiState.classError,
                supportingText = { if (uiState.classError) Text("Class already exists") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.weight(1f))

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
                enabled = viewModel.isFormValid()
            ) {
                Text("Save")
            }
        }
    }
}
