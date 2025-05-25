package gr.tsambala.tutorbilling.ui.home;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import androidx.lifecycle.compose.collectAsStateWithLifecycle;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000J\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u0012\u0010\u0000\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u0003\u001a(\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u0003\u001a\"\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u0003\u001a4\u0010\r\u001a\u00020\u00012\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\b\b\u0002\u0010\u0012\u001a\u00020\u0013H\u0007\u001a<\u0010\u0014\u001a\u00020\u00012\u0006\u0010\u0015\u001a\u00020\u00062\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u0003\u001a\u001e\u0010\u0018\u001a\u00020\u00012\u0006\u0010\u0019\u001a\u00020\u001a2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0003\u001a<\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u00062\u0006\u0010\u001e\u001a\u00020\u000b2\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020 H\u0003\u00f8\u0001\u0000\u00a2\u0006\u0004\b\"\u0010#\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006$"}, d2 = {"EmptyState", "", "modifier", "Landroidx/compose/ui/Modifier;", "ErrorMessage", "message", "", "onDismiss", "Lkotlin/Function0;", "FinancialSummarySection", "weekTotal", "", "monthTotal", "HomeScreen", "onNavigateToStudent", "Lkotlin/Function1;", "", "onNavigateToAddStudent", "viewModel", "Lgr/tsambala/tutorbilling/ui/home/HomeViewModel;", "SearchBar", "query", "onQueryChange", "onClear", "StudentCard", "student", "Lgr/tsambala/tutorbilling/ui/home/HomeViewModel$StudentSummary;", "onClick", "SummaryCard", "title", "amount", "containerColor", "Landroidx/compose/ui/graphics/Color;", "contentColor", "SummaryCard-jZ3TX3s", "(Ljava/lang/String;DLandroidx/compose/ui/Modifier;JJ)V", "app_debug"})
public final class HomeScreenKt {
    
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
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void HomeScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Long, kotlin.Unit> onNavigateToStudent, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToAddStudent, @org.jetbrains.annotations.NotNull()
    gr.tsambala.tutorbilling.ui.home.HomeViewModel viewModel) {
    }
    
    /**
     * Financial summary section showing week and month totals.
     * These cards provide the most important information at a glance.
     */
    @androidx.compose.runtime.Composable()
    private static final void FinancialSummarySection(double weekTotal, double monthTotal, androidx.compose.ui.Modifier modifier) {
    }
    
    /**
     * Search bar for filtering students.
     * Includes clear button for better UX.
     */
    @androidx.compose.runtime.Composable()
    private static final void SearchBar(java.lang.String query, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onQueryChange, kotlin.jvm.functions.Function0<kotlin.Unit> onClear, androidx.compose.ui.Modifier modifier) {
    }
    
    /**
     * Student card showing summary information.
     * Each card is a tappable entry point to student details.
     */
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    private static final void StudentCard(gr.tsambala.tutorbilling.ui.home.HomeViewModel.StudentSummary student, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    /**
     * Empty state shown when no students exist yet.
     * Encouraging and instructive to guide new users.
     */
    @androidx.compose.runtime.Composable()
    private static final void EmptyState(androidx.compose.ui.Modifier modifier) {
    }
    
    /**
     * Error message display with dismiss action.
     * Non-intrusive but noticeable.
     */
    @androidx.compose.runtime.Composable()
    private static final void ErrorMessage(java.lang.String message, kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, androidx.compose.ui.Modifier modifier) {
    }
}