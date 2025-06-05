package gr.tsambala.tutorbilling.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeMenuScreen(
    onStudentsClick: () -> Unit,
    onClassesClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tutor Billing") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onStudentsClick,
                modifier = Modifier.fillMaxWidth()
            ) { Text("Students") }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onClassesClick,
                modifier = Modifier.fillMaxWidth()
            ) { Text("Classes") }
        }
    }
}
