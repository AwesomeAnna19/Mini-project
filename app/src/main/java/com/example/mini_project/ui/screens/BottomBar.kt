package com.example.mini_project.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import com.example.mini_project.ui.screens.GraphScreen.StatsRoute
import com.example.mini_project.ui.screens.home.HomeRoute


data class NavItem (
    val routeString: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean
)

val navItemList = listOf(
    NavItem(
        routeString = HomeRoute.routeString,
        title = "Tasks",
        selectedIcon = Icons.AutoMirrored.Filled.List,
        unselectedIcon = Icons.AutoMirrored.Outlined.List,
        hasNews = false
    ),
    NavItem(
        routeString = StatsRoute.routeString,
        title = "Stats",
        selectedIcon = Icons.Filled.BarChart,
        unselectedIcon = Icons.Outlined.BarChart,
        hasNews = false
    ),
    /*
    NavItem(
        screen = Screen.Rewards,
        title = "Rewards",
        selectedIcon = Icons.Filled.Celebration,
        unselectedIcon = Icons.Outlined.Celebration,
        hasNews = false
    )
     */
)

@Composable
fun HabitizeBottomBar(
    navItemList: List<NavItem>,
    navController: NavHostController, //Aner ikke om det her er okay ,
    defaultTab: String,
    modifier: Modifier = Modifier
) {

    //Den her skal over i en viewModel
    var currentTab by rememberSaveable {
        mutableStateOf(defaultTab)
    }


    NavigationBar(modifier = modifier) {
        for (navItem in navItemList) {
            NavigationBarItem(
                selected = currentTab == navItem.routeString, //En navigation bar item er selected hvis den nuværende tab er lig den skærmen på den navitem, som i øjeblikket loopes over
                onClick = {
                    currentTab = navItem.routeString //Hvis man klikker på den navItem som der loopes over, så update currenttab
                    navController.navigate(navItem.routeString) //Og naviger til den du lige har klikket på
                },
                label = {
                    Text(text = navItem.title)
                },
                icon = {
                    BadgedBox(
                        badge ={
                            if(navItem.hasNews){
                                Badge {
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if(navItem.routeString == currentTab) {
                            navItem.selectedIcon } else navItem.unselectedIcon,
                            contentDescription = navItem.title
                        )
                    }
                }
            )
        }
    }
}