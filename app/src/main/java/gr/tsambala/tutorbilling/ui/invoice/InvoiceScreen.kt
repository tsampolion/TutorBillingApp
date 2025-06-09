package gr.tsambala.tutorbilling.ui.invoice

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.core.content.FileProvider
import gr.tsambala.tutorbilling.data.database.LessonWithStudent
import kotlinx.coroutines.runBlocking
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvoiceScreen(
    onBack: () -> Unit,
    viewModel: InvoiceViewModel = hiltViewModel()
) {
    val startDate by viewModel.startDate.collectAsStateWithLifecycle()
    val endDate by viewModel.endDate.collectAsStateWithLifecycle()
    val lessons by viewModel.lessons.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Invoice") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(onClick = onBack, modifier = Modifier.weight(1f)) { Text("Cancel") }
                Button(
                    onClick = {
                        val uri = createInvoicePdf(context.cacheDir, lessons)
                        val share = Intent(Intent.ACTION_SEND).apply {
                            type = "application/pdf"
                            putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(context, "${context.packageName}.provider", File(uri.path!!)))
                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        }
                        context.startActivity(Intent.createChooser(share, null))
                    },
                    modifier = Modifier.weight(1f),
                    enabled = lessons.isNotEmpty()
                ) { Text("Share PDF") }
            }
        }
    ) { padding ->
        Column(Modifier.padding(padding).padding(16.dp)) {
            DateField("Start", startDate) { date -> viewModel.updateStartDate(date) }
            DateField("End", endDate) { date -> viewModel.updateEndDate(date) }

            if (lessons.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No unpaid lessons")
                }
            } else {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(lessons) { item ->
                        LessonRow(item)
                        Divider()
                    }
                }
            }
        }
    }
}

@Composable
private fun LessonRow(item: LessonWithStudent) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(item.student.getFullName(), style = MaterialTheme.typography.bodyMedium)
            Text(LocalDate.parse(item.lesson.date).format(DateTimeFormatter.ofPattern("dd MMM")))
        }
        Text("€%.2f".format(item.calculateFee()))
    }
}

@Composable
private fun DateField(label: String, date: LocalDate, onDate: (LocalDate) -> Unit) {
    val context = LocalContext.current
    OutlinedTextField(
        value = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
        onValueChange = {},
        readOnly = true,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                DatePickerDialog(
                    context,
                    { _, y, m, d -> onDate(LocalDate.of(y, m + 1, d)) },
                    date.year,
                    date.monthValue - 1,
                    date.dayOfMonth
                ).show()
            }
    )
}

fun createInvoicePdf(cacheDir: File, lessons: List<LessonWithStudent>): Uri {
    val pdf = android.graphics.pdf.PdfDocument()
    val pageInfo = android.graphics.pdf.PdfDocument.PageInfo.Builder(595, 842, 1).create()
    val page = pdf.startPage(pageInfo)
    val canvas = page.canvas
    var y = 50
    val paint = android.graphics.Paint()
    lessons.forEach {
        canvas.drawText(
            "${it.lesson.date} ${it.student.getFullName()} €%.2f".format(it.calculateFee()),
            40f,
            y.toFloat(),
            paint
        )
        y += 20
    }
    pdf.finishPage(page)
    val file = File(cacheDir, "invoice-${System.currentTimeMillis()}.pdf")
    FileOutputStream(file).use { pdf.writeTo(it) }
    pdf.close()
    return Uri.fromFile(file)
}
