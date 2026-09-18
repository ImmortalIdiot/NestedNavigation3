package io.ii.navigation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import io.ii.activity.navigation.ActivityKey
import io.ii.activity.navigation.activityEntries
import io.ii.profile.navigation.ProfileKey
import io.ii.profile.navigation.profileEntries
import io.ii.projects.navigation.ProjectKey
import io.ii.projects.navigation.projectEntries

@Composable
fun Navigation() {

    val navState = rememberNavigationState(startTopLevelKey = ProjectKey)

    val navigator = remember(navState) {
        Navigator(navState)
    }

    val entries = navState.toEntries(appEntryProvider(navigator))

    LaunchedEffect(navState) {
        snapshotFlow { navState.selectedBackStack.toList() }
            .collect {
                Log.d("Backstack", navState.selectedBackStack.joinToString())
            }
    }

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 50.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    onClick = {
                        navigator.selectTopLevel(ProjectKey)
                    }
                ) {
                    Text("Projects")
                }
                Button(
                    onClick = {
                        navigator.selectTopLevel(ActivityKey)
                    }
                ) {
                    Text("Activity")
                }
                Button(
                    onClick = {
                        navigator.selectTopLevel(ProfileKey)
                    }
                ) {
                    Text("Profile")
                }
            }
        }
    ) { paddings ->
        NavDisplay(
            modifier = Modifier.padding(paddings),
            entries = entries,
            onBack = { navigator.onBack() },
        )
    }
}

fun appEntryProvider(
    navigator: Navigator
): (NavKey) -> NavEntry<NavKey> {
    return entryProvider {
        projectEntries { key -> navigator.navigate(key) }
        activityEntries()
        profileEntries { key -> navigator.navigate(key) }
    }
}
