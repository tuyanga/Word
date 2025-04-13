package com.example.newword.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newword.R
import com.example.newword.data.Word
import com.example.newword.viewmodel.AppViewModelProvider
import com.example.newword.viewmodel.WordViewModel

@Composable
fun EditWordScreen(
    wordId: Int,
    onNoInsertButtonClicked: () -> Unit = {},
    onInsertButtonClicked: () -> Unit = {},
    viewModel: WordViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {
    val wordList by viewModel.wordList.collectAsState()
    val word = wordList.find { it.id == wordId }

    var gadaadug by rememberSaveable { mutableStateOf("") }
    var mongolug by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(word) {
        word?.let {
            gadaadug = it.foreign
            mongolug = it.mongolian
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
            TextField(
                value = gadaadug,
                onValueChange = { gadaadug = it },
                label = { Text(stringResource(R.string.gadaadug)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = mongolug,
                onValueChange = { mongolug = it },
                label = { Text(stringResource(R.string.mongolug)) },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
            verticalAlignment = Alignment.Bottom
        ) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = onNoInsertButtonClicked,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = colorResource(id = R.color.purple_500)
                ),
                shape = MaterialTheme.shapes.small
            ) {
                Text(stringResource(R.string.noinsert))
            }

            Button(
                modifier = Modifier.weight(1f),
                enabled = gadaadug.isNotBlank() && mongolug.isNotBlank(),
                onClick = {
                    val newWord = Word(
                        id = word?.id ?: 0,
                        foreign = gadaadug,
                        mongolian = mongolug
                    )
                    if (word != null) {
                        viewModel.updateWord(newWord)
                    } else {
                        viewModel.insertWord(newWord)
                    }
                    onInsertButtonClicked()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.purple_500),
                    contentColor = Color.White
                ),
                shape = MaterialTheme.shapes.small
            ) {
                Text(stringResource(R.string.insert))
            }
        }
    }
}
