package gr.tsambala.tutorbilling.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeMenuScreen(
    onStudentsClick: () -> Unit,
    onClassesClick: () -> Unit,
    onLessonsClick: () -> Unit,
    onAddStudent: () -> Unit,
    onAddLesson: () -> Unit,
    onRevenue: () -> Unit,
    onSettings: () -> Unit,
    viewModel: HomeMenuViewModel = hiltViewModel()
) {
    var showFabMenu by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val studentsColor = if (uiState.studentCount > 0)
        MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer
    val classesColor = if (uiState.classCount > 0)
        MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.secondaryContainer
    val lessonsColor = if (uiState.lessonCount > 0)
        MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.tertiaryContainer

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FloatingActionButton(
                    onClick = onRevenue,
                    containerColor = MaterialTheme.colorScheme.secondary
                ) { Icon(Icons.Default.BarChart, contentDescription = "Revenue") }
                FloatingActionButton(
                    onClick = { showFabMenu = !showFabMenu },
                    containerColor = MaterialTheme.colorScheme.primary
                ) { Icon(Icons.Default.Add, contentDescription = "Add") }

                FloatingActionButton(
                    onClick = onSettings,
                    containerColor = MaterialTheme.colorScheme.tertiary
                ) { Icon(Icons.Default.Settings, contentDescription = "Settings") }
            }
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 32.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {
            Spacer(Modifier.height(32.dp))
            Text("Tutor Billing", style = MaterialTheme.typography.headlineMedium)
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
