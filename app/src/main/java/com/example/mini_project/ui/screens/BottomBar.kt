package com.example.mini_project.ui.screens

import androidx.compose.foundation.lazy.layout.LazyLayoutPinnedItemList
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Celebration
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.mini_project.data.Screen
import com.example.mini_project.data.badge.Badge


data class NavItem (
    val screen: Screen,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean
)

val navItemList = listOf(
    NavItem(
        screen = Screen.Tasks,
        title = "Tasks",
        selectedIcon = Icons.Filled.List,
        unselectedIcon = Icons.Outlined.List,
        hasNews = false
    ),
    NavItem(
        screen = Screen.Stats,
        title = "Stats",
        selectedIcon = Icons.Filled.BarChart,
        unselectedIcon = Icons.Outlined.BarChart,
        hasNews = false
    ),
    NavItem(
        screen = Screen.Rewards,
        title = "Rewards",
        selectedIcon = Icons.Filled.Celebration,
        unselectedIcon = Icons.Outlined.Celebration,
        hasNews = false
    )
)


//Funktionalitet mangler
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LifeRPGBottomBar(
    currentTab: Screen,              //Den bottom nav tab man er på lige nu
    onTabPressed: ((Screen)-> Unit), //Når man trykker på en tab
    navItemList: List<NavItem>,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
        for (navItem in navItemList) {
            NavigationBarItem(
                selected = currentTab == navItem.screen, //En navigation bar item er selected hvis den nuværende tab er lig den skærmen på den navitem, som i øjeblikket loopes over
                onClick = {onTabPressed(navItem.screen)},
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
                            imageVector = if(currentTab == navItem.screen) {
                            navItem.selectedIcon } else navItem.unselectedIcon,
                            contentDescription = navItem.title
                        )

                    }
                }
            )
        }

    }
}