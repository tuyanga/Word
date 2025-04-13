package com.example.newword.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.newword.ui.components.NewWordTopAppBar
import com.example.newword.ui.screens.EditWordScreen
import com.example.newword.ui.screens.SelectOptionScreen
import com.example.newword.ui.screens.WordShowScreen
import com.example.newword.ui.theme.NewWordTheme

enum class NewWordRoute {
    WordShow,
    EditWord,
    Settings
}

@Composable
fun NewWordScreen(windowSize: WindowWidthSizeClass) {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            NewWordTopAppBar(onSettingsClick = {
                navController.navigate(NewWordRoute.Settings.name)
            })
        }
    ) { innerPadding ->
        if (windowSize == WindowWidthSizeClass.Expanded) {

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                NavHost(
                    navController = navController,
                    startDestination = NewWordRoute.WordShow.name,
                    modifier = Modifier.fillMaxSize()
                ) {
                    composable(route = NewWordRoute.WordShow.name) {
                        Row(Modifier.fillMaxSize()) {
                            Box(modifier = Modifier.weight(1f)) {
                                WordShowScreen(
                                    onAddClick = {
                                        navController.navigate("${NewWordRoute.EditWord.name}/-1")
                                    },
                                    onUpdateClick = { word ->
                                        navController.navigate("${NewWordRoute.EditWord.name}/${word.id}")
                                    }
                                )
                            }
                            Box(modifier = Modifier.weight(1f)) {
                                SelectOptionScreen(
                                    onNextButtonClicked = { /* handle */ },
                                    onCancelButtonClicked = { /* handle */ }
                                )
                            }
                        }
                    }

                    composable(
                        route = "${NewWordRoute.EditWord.name}/{wordId}",
                        arguments = listOf(navArgument("wordId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val wordId = backStackEntry.arguments?.getInt("wordId") ?: -1
                        EditWordScreen(
                            wordId = wordId,
                            onInsertButtonClicked = {
                                navController.navigate(NewWordRoute.WordShow.name)
                            },
                            onNoInsertButtonClicked = {
                                navController.navigate(NewWordRoute.WordShow.name)
                            }
                        )
                    }

                    composable(route = NewWordRoute.Settings.name) {
                        SelectOptionScreen(
                            onNextButtonClicked = {
                                navController.navigate(NewWordRoute.WordShow.name)
                            },
                            onCancelButtonClicked = {
                                navController.navigate(NewWordRoute.WordShow.name)
                            }
                        )
                    }
                }
            }
        } else {

            NavHost(
                navController = navController,
                startDestination = NewWordRoute.WordShow.name,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                composable(route = NewWordRoute.WordShow.name) {
                    WordShowScreen(
                        onAddClick = {
                            navController.navigate("${NewWordRoute.EditWord.name}/-1")
                        },
                        onUpdateClick = { word ->
                            navController.navigate("${NewWordRoute.EditWord.name}/${word.id}")
                        }
                    )
                }

                composable(
                    route = "${NewWordRoute.EditWord.name}/{wordId}",
                    arguments = listOf(navArgument("wordId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val wordId = backStackEntry.arguments?.getInt("wordId") ?: -1
                    EditWordScreen(
                        wordId = wordId,
                        onInsertButtonClicked = {
                            navController.navigate(NewWordRoute.WordShow.name)
                        },
                        onNoInsertButtonClicked = {
                            navController.navigate(NewWordRoute.WordShow.name)
                        }
                    )
                }

                composable(route = NewWordRoute.Settings.name) {
                    SelectOptionScreen(
                        onNextButtonClicked = {
                            navController.navigate(NewWordRoute.WordShow.name)
                        },
                        onCancelButtonClicked = {
                            navController.navigate(NewWordRoute.WordShow.name)
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNewWordApp() {
    NewWordTheme {
        NewWordScreen(windowSize = WindowWidthSizeClass.Compact)
    }
}
