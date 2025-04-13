package com.example.newword.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newword.R
import com.example.newword.ui.theme.NewWordTheme
import com.example.newword.viewmodel.AppViewModelProvider
import com.example.newword.viewmodel.SettingsViewModel

@Composable
fun SelectOptionScreen(
    viewModel: SettingsViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onCancelButtonClicked: () -> Unit = {},
    onNextButtonClicked: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val displayMode by viewModel.displayMode.collectAsState()
    var selectedValue by rememberSaveable {mutableStateOf<String?>(null) }
    LaunchedEffect(displayMode) {
        selectedValue = displayMode
    }
    val options = listOf("foreign", "mongolian","both")
    val optionLabels = listOf(
        stringResource(R.string.set1),
        stringResource(R.string.set2),
        stringResource(R.string.set3)
    )
    val effectiveSelected = selectedValue ?: displayMode
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
            options.forEachIndexed { index, value ->
                Row(
                    modifier = Modifier.selectable(
                        selected = effectiveSelected == value,
                        onClick = { selectedValue = value }
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = effectiveSelected == value,
                        onClick = { selectedValue = value }
                    )
                    Text(optionLabels[index])
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
            verticalAlignment = Alignment.Bottom
        ) {
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = onCancelButtonClicked,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = colorResource(id = R.color.purple_500)
                ),
                shape = MaterialTheme.shapes.small
            ) {
                Text(stringResource(R.string.cancel))
            }
            Button(
                modifier = Modifier.weight(1f),
                enabled = effectiveSelected != null,
                onClick = {
                    selectedValue?.let { viewModel.saveDisplayMode(it) }
                    onNextButtonClicked()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.purple_500),
                    contentColor = Color.White
                ),
                shape = MaterialTheme.shapes.small
            ) {
                Text(stringResource(R.string.save))
            }
        }
    }
}

@Preview
@Composable
fun SelectOptionPreview() {
    NewWordTheme {
        SelectOptionScreen(
            modifier = Modifier.fillMaxHeight()
        )
    }
}