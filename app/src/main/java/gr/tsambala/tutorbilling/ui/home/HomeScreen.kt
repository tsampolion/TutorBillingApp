package gr.tsambala.tutorbilling.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import gr.tsambala.tutorbilling.utils.formatAsCurrency

/**
 * HomeScreen is the main screen of the Tutor Billing app.
 *
 * This is where tutors land when they open the app, giving them an immediate
 * overview of their business. It's designed to answer the most important questions:
 * "How much have I earned this week?" and "Which students do I teach?"
 *
 * The screen follows Material Design 3 principles with a clean, scannable layout
 * that makes financial information easy to digest at a glance.
 *
 * @param onNavigateToStudent Called when user taps on a student
 * @param onNavigateToAddStudent Called when user taps the add button
 * @param viewModel The HomeViewModel that manages this screen's state
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToStudent: (Long) -> Unit,
    onNavigateToAddStudent: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
    onAddStudent: () -> Unit
) {
    // Collect the UI state from the ViewModel
    // collectAsStateWithLifecycle ensures proper lifecycle handling
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            // Large top app bar provides visual hierarchy and brand presence
            LargeTopAppBar(
                title = {
                    Text(
                        "Tutor Billing",
                        style = MaterialTheme.typography.headlineLarge
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            // FAB for adding new students - the primary action
            ExtendedFloatingActionButton(
                onClick = onNavigateToAddStudent,
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Add Student") },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Financial summary cards at the top for immediate visibility
            FinancialSummarySection(
                weekTotal = uiState.totalWeekEarnings,
                monthTotal = uiState.totalMonthEarnings,
                modifier = Modifier.padding(16.dp)
            )

            // Search bar for finding students quickly
            SearchBar(
                query = uiState.searchQuery,
                onQueryChange = viewModel::updateSearchQuery,
                onClear = viewModel::clearSearch,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            // The fancy divider mentioned in requirements
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
            )

            // Main content area
            when {
                uiState.isLoading -> {
                    // Loading state
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                uiState.errorMessage != null -> {
                    // Error state
                    ErrorMessage(
                        message = uiState.errorMessage,
                        onDismiss = viewModel::clearError,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                uiState.studentSummaries.isEmpty() -> {
                    // Empty state - no students yet
                    EmptyState(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp)
                    )
                }

                else -> {
                    // Student list
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 80.dp // Space for FAB
                        ),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(
                            items = uiState.studentSummaries,
                            key = { it.id } // Stable key for better performance
                        ) { student ->
                            StudentCard(
                                student = student,
                                onClick = { onNavigateToStudent(student.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Financial summary section showing week and month totals.
 * These cards provide the most important information at a glance.
 */
@Composable
private fun FinancialSummarySection(
    weekTotal: Double,
    monthTotal: Double,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Week total card
        SummaryCard(
            title = "This Week",
            amount = weekTotal,
            modifier = Modifier.weight(1f),
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )

        // Month total card
        SummaryCard(
            title = "This Month",
            amount = monthTotal,
            modifier = Modifier.weight(1f),
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

/**
 * Individual summary card for financial totals.
 * Uses Material 3 card styling with tonal colors.
 */
@Composable
private fun SummaryCard(
    title: String,
    amount: Double,
    modifier: Modifier = Modifier,
    containerColor: androidx.compose.ui.graphics.Color,
    contentColor: androidx.compose.ui.graphics.Color
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = contentColor.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = amount.formatAsCurrency(),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )
        }
    }
}

/**
 * Search bar for filtering students.
 * Includes clear button for better UX.
 */
@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier,
        placeholder = { Text("Search students...") },
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "Search",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        trailingIcon = {
            AnimatedVisibility(
                visible = query.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                IconButton(onClick = onClear) {
                    Icon(
                        Icons.Default.Clear,
                        contentDescription = "Clear search"
                    )
                }
            }
        },
        singleLine = true,
        shape = MaterialTheme.shapes.large
    )
}

/**
 * Student card showing summary information.
 * Each card is a tappable entry point to student details.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StudentCard(
    student: HomeViewModel.StudentSummary,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Student info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = student.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = student.formattedRate,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (student.lessonCount > 0) {
                    Text(
                        text = "${student.lessonCount} lessons this month",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Financial info
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = student.weekTotal.formatAsCurrency(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Week",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = student.monthTotal.formatAsCurrency(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Month",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

/**
 * Empty state shown when no students exist yet.
 * Encouraging and instructive to guide new users.
 */
@Composable
private fun EmptyState(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "👋",
            style = MaterialTheme.typography.displayLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Welcome to Tutor Billing!",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Add your first student to start tracking lessons and earnings.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/**
 * Error message display with dismiss action.
 * Non-intrusive but noticeable.
 */
@Composable
private fun ErrorMessage(
    message: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
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
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onErrorContainer,
                modifier = Modifier.weight(1f)
            )
            TextButton(onClick = onDismiss) {
                Text("Dismiss")
            }
        }
    }
}