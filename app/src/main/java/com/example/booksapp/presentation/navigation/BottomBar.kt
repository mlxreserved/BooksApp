package com.example.booksapp.presentation.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.booksapp.R
import com.example.booksapp.presentation.screens.search.SearchScreen

@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val screens = listOf(
        Route.SearchScreen,
        Route.FavouriteScreen
    )

    Column {
        HorizontalDivider(
            color = colorResource(R.color.light_grey),
            thickness = 2.dp,
            modifier = Modifier.fillMaxWidth()
        )

        NavigationBar(
            containerColor = MaterialTheme.colorScheme.onPrimary,
            modifier = modifier
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            screens.forEach { screen ->
                val isSelected = currentRoute == screen.route

                NavigationBarItem(
                    icon = {
                        Image(
                            imageVector = ImageVector.vectorResource(screen.iconId),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(
                                if (isSelected) colorResource(R.color.light_blue) else colorResource(
                                    R.color.light_grey
                                )
                            )
                        )
                    },
                    label = {
                        Text(text = screen.title)
                    },
                    selected = isSelected,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = colorResource(R.color.light_blue),
                        selectedTextColor = colorResource(R.color.light_blue),
                        unselectedIconColor = colorResource(R.color.light_grey),
                        unselectedTextColor = colorResource(R.color.light_grey),
                        indicatorColor = Color.Transparent
                    ),
                )
            }
        }
    }
}