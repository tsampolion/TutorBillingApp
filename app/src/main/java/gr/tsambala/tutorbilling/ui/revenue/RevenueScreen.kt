package gr.tsambala.tutorbilling.ui.revenue

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import gr.tsambala.tutorbilling.ui.settings.SettingsViewModel
import gr.tsambala.tutorbilling.utils.formatAsCurrency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RevenueScreen(
    onBack: () -> Unit,
    onInvoice: () -> Unit,
    onPastInvoices: () -> Unit,
    viewModel: RevenueViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val settings by settingsViewModel.settings.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Revenue") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MetricTile(
                    label = "Daily",
                    value = uiState.dailyRevenue.formatAsCurrency(
                        settings.currencySymbol,
                        settings.roundingDecimals
                    ),
                    modifier = Modifier.weight(1f),
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
                MetricTile(
                    label = "Weekly",
                    value = uiState.weeklyRevenue.formatAsCurrency(
                        settings.currencySymbol,
                        settings.roundingDecimals
                    ),
                    modifier = Modifier.weight(1f),
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
                MetricTile(
                    label = "Monthly",
                    value = uiState.monthlyRevenue.formatAsCurrency(
                        settings.currencySymbol,
                        settings.roundingDecimals
                    ),
                    modifier = Modifier.weight(1f),
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MetricTile(
                    label = "Unpaid",
                    value = uiState.monthlyUnpaid.formatAsCurrency(
                        settings.currencySymbol,
                        settings.roundingDecimals
                    ),
                    modifier = Modifier.weight(1f),
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
                MetricTile(
                    label = "Paid",
                    value = uiState.monthlyPaid.formatAsCurrency(
                        settings.currencySymbol,
                        settings.roundingDecimals
                    ),
                    modifier = Modifier.weight(1f),
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onInvoice,
                    modifier = Modifier.weight(1f)
                ) { Text("New Invoice") }
                OutlinedButton(
                    onClick = onPastInvoices,
                    modifier = Modifier.weight(1f)
                ) { Text("Past Invoices") }
            }
        }
    }
}

@Composable
private fun MetricTile(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    containerColor: androidx.compose.ui.graphics.Color
) {
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = containerColor)) {
        Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(label, style = MaterialTheme.typography.labelSmall)
            Text(value, style = MaterialTheme.typography.titleMedium)
        }
    }
}
