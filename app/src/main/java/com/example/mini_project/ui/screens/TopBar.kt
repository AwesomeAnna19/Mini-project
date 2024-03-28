package com.example.mini_project.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import com.example.mini_project.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LifeRPGTopBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        title = {
            Text(
             text = "Mini Project",
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium)),
            fontWeight = FontWeight.Bold
            )
        }
    )
}