package com.example.booksapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.booksapp.R
import com.example.booksapp.presentation.navigation.AppNavigationGraph
import com.example.booksapp.presentation.navigation.BottomBar
import com.example.booksapp.presentation.screens.components.CustomSnackbar
import com.example.booksapp.presentation.screens.search.SearchViewModel
import com.example.booksapp.presentation.ui.theme.BooksAppTheme
import com.example.booksapp.presentation.utils.SnackbarMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BooksAppTheme{
                val navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }

                val screensWithoutBottomBar = listOf("book_details/{bookId}?isFavourite={isFavourite}")

                Scaffold(
                    snackbarHost = { CustomSnackbar(snackbarHostState = snackbarHostState) },
                    bottomBar = {
                        val currentBackStackEntry = navController.currentBackStackEntryAsState().value
                        val currentRoute = currentBackStackEntry?.destination?.route

                        if (currentRoute !in screensWithoutBottomBar) {
                            BottomBar(navController = navController)
                        }
                                },
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    AppNavigationGraph(
                        navController = navController,
                        snackbarHostState = snackbarHostState,
                        modifier = Modifier.padding(paddingValues = innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BooksAppTheme {
        Greeting("Android")
    }
}