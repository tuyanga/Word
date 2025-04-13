package com.example.newword.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.newword.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewWordTopAppBar(onSettingsClick: () -> Unit) {
    TopAppBar(
        title = { Text(stringResource(R.string.title)) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        actions = {
            IconButton(onClick = onSettingsClick) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Settings",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    )
}