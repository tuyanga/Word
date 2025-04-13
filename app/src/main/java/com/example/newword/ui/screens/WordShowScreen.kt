package com.example.newword.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newword.R
import com.example.newword.viewmodel.WordViewModel
import com.example.newword.data.Word
import com.example.newword.viewmodel.AppViewModelProvider
import com.example.newword.viewmodel.SettingsViewModel
import androidx.compose.foundation.combinedClickable

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WordShowScreen(
    modifier: Modifier = Modifier,
    onAddClick: () -> Unit = {},
    onUpdateClick: (Word) -> Unit = {},
    viewModel: WordViewModel = viewModel(factory = AppViewModelProvider.Factory),
    settingsViewModel: SettingsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    var showForeignTemporarily by remember { mutableStateOf(false) }
    var showMongolianTemporarily by remember { mutableStateOf(false) }
    val displayMode by settingsViewModel.displayMode.collectAsState()
    val currentWord by viewModel.currentWord.collectAsState()

    var showDeleteDialog by remember { mutableStateOf(false) }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth()
                    .combinedClickable(
                        onClick = {
                            showForeignTemporarily = !showForeignTemporarily
                            showMongolianTemporarily = false
                        },
                        onLongClick = {
                            currentWord?.let { onUpdateClick(it) }
                        }
                    ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (displayMode == "both" || displayMode == "foreign" || showForeignTemporarily) {
                        Text(
                            text = currentWord?.foreign ?: "No word",
                            fontSize = 20.sp
                        )
                    } else {
                        Text(text = "Click to reveal", fontSize = 16.sp, color = Color.Gray)
                    }
                }
            }
            Card(
                modifier = Modifier.fillMaxWidth()
                    .combinedClickable(
                        onClick = {
                            showMongolianTemporarily = !showMongolianTemporarily
                            showForeignTemporarily = false
                        },
                        onLongClick = {
                            currentWord?.let { onUpdateClick(it) }
                        }
                    ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (displayMode == "both" || displayMode == "mongolian" || showMongolianTemporarily) {
                        Text(
                            text = currentWord?.mongolian ?: "No meaning",
                            fontSize = 20.sp
                        )
                    } else {
                        Text(text = "Click to reveal", fontSize = 16.sp, color = Color.Gray)
                    }
                }
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        onAddClick()
                              },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
                ) {
                    Text(stringResource(R.string.add))
                }
                Button(
                    onClick = {
                        currentWord?.let { // 👈 байгаа үгийг засах
                            onUpdateClick(it)
                        }
                              },
                    enabled = currentWord != null,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
                ) {
                    Text(stringResource(R.string.update))
                }
                Button(
                    modifier = Modifier.weight(1f),
                    enabled = currentWord != null,
                    onClick = { showDeleteDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
                ) {
                    Text(stringResource(R.string.delete))
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    modifier = Modifier.weight(1f),
                    enabled = currentWord != null,
                    onClick = { viewModel.prevWord() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
                ) {
                    Text(stringResource(R.string.previous))
                }
                Button(
                    modifier = Modifier.weight(1f),
                    enabled = currentWord != null,
                    onClick = { viewModel.nextWord() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
                ) {
                    Text(stringResource(R.string.next))
                }
            }
        }
    }
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteCurrentWord()
                        showDeleteDialog = false
                    }
                ) {
                    Text("Тийм", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteDialog = false }
                ) {
                    Text("Үгүй")
                }
            },
            title = { Text("Устгах уу?") },
            text = {
                Text("Энэ үгийг бүрмөсөн устгах уу?")
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WordShowScreenPreview() {
    MaterialTheme {
        WordShowScreen()
    }
}
