package gr.tsambala.tutorbilling.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import gr.tsambala.tutorbilling.ui.settings.SettingsViewModel
import gr.tsambala.tutorbilling.utils.formatAsCurrency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeMenuScreen(
    onStudentsClick: () -> Unit,
    onClassesClick: () -> Unit,
    onLessonsClick: () -> Unit,
    onAddStudent: () -> Unit,
    onAddLesson: () -> Unit,
    onSettings: () -> Unit,
    onSearch: () -> Unit,
    viewModel: HomeMenuViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    var showFabMenu by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val settings by settingsViewModel.settings.collectAsStateWithLifecycle()

    val studentsColor = if (uiState.studentCount > 0)
        MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer
    val classesColor = if (uiState.classCount > 0)
        MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.secondaryContainer
    val lessonsColor = if (uiState.lessonCount > 0)
        MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.tertiaryContainer

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tutor Billing") },
                actions = {
                    IconButton(onClick = onSearch) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                    IconButton(onClick = onSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        },
        floatingActionButton = {
            Box {
                FloatingActionButton(onClick = { showFabMenu = !showFabMenu }) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
                DropdownMenu(expanded = showFabMenu, onDismissRequest = { showFabMenu = false }) {
                    DropdownMenuItem(text = { Text("Add Student") }, onClick = {
                        showFabMenu = false
                        onAddStudent()
                    })
                    DropdownMenuItem(text = { Text("Add Lesson") }, onClick = {
                        showFabMenu = false
                        onAddLesson()
                    })
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 32.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("This week", style = MaterialTheme.typography.labelSmall)
                    Text(
                        uiState.weekRevenue.formatAsCurrency(settings.currencySymbol, settings.roundingDecimals),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)) {
                Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("This month", style = MaterialTheme.typography.labelSmall)
                    Text(
                        uiState.monthRevenue.formatAsCurrency(settings.currencySymbol, settings.roundingDecimals),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
            Button(
                onClick = onStudentsClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = studentsColor)
            ) { Text("Students") }
            Button(
                onClick = onClassesClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = classesColor)
            ) { Text("Classes") }
            Button(
                onClick = onLessonsClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = lessonsColor)
            ) { Text("Lessons") }
        }
    }
}
