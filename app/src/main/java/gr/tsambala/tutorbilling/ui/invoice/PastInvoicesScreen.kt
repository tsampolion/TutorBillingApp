package gr.tsambala.tutorbilling.ui.invoice

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PastInvoicesScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val invoicesDir = remember { File(context.filesDir, "invoices") }
    val invoices = remember { invoicesDir.listFiles()?.sortedByDescending { it.lastModified() } ?: emptyList() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Past Invoices") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (invoices.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("No invoices found")
            }
        } else {
            LazyColumn(Modifier.fillMaxSize().padding(padding)) {
                items(invoices) { file ->
                    ListItem(
                        headlineContent = { Text(file.name) },
                        modifier = Modifier.clickable {
                            val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
                            val intent = Intent(Intent.ACTION_VIEW).apply {
                                setDataAndType(uri, "application/pdf")
                                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            }
                            context.startActivity(intent)
                        }
                    )
                    Divider()
                }
            }
        }
    }
}
