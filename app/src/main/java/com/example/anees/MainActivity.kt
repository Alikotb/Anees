package com.example.anees

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffset
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.anees.ui.navigation.ScreenRoute
import com.example.anees.ui.navigation.SetUpNavHost
import com.example.anees.utils.extensions.setAllAlarms
import com.example.anees.utils.location.LocationProvider
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController
    private var askedForOverlayPermission = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!Settings.canDrawOverlays(this)) {
            askedForOverlayPermission = true
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            startActivity(intent)
        }
        else{
            setAllAlarms()
        }
        enableEdgeToEdge()
        setContent {
            val drawerState = remember { mutableStateOf(false) }
            val scope = rememberCoroutineScope()
            val systemUiController = rememberSystemUiController()
            navController = rememberNavController()

            SideEffect {
                systemUiController.setStatusBarColor(
                    color = Color.Transparent,
                    darkIcons = true
                )
            }
            Box(modifier = Modifier.fillMaxSize().background(Color(0xFF064789))) {
                CustomDrawer(
                    isOpen = drawerState.value,
                    onClose = { drawerState.value = false },
                    onNavigate = { destination ->
                        scope.launch {
                            drawerState.value = false
                            navController.navigate(destination)
                        }
                    }
                )
                MainContent(
                    navController = navController,
                    drawerOpen = drawerState.value,
                    onMenuClick = { drawerState.value = !drawerState.value }
                )


            }
        }
    }

    override fun onStart() {
        super.onStart()
        val locationProvider = LocationProvider(this)
        locationProvider.fetchLatLong(this) { location ->
        }
    }

    override fun onResume() {
        super.onResume()
        if (askedForOverlayPermission && Settings.canDrawOverlays(this)) {
            askedForOverlayPermission = false
            setAllAlarms()
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val locationProvider = LocationProvider(this)
        locationProvider.handlePermissionResult(requestCode, grantResults, this) {
        }
    }
}

enum class MenuState {
    EXPANDED, COLLAPSED
}

@Composable
fun CustomDrawer(
    isOpen: Boolean,
    onClose: () -> Unit,
    onNavigate: (ScreenRoute) -> Unit
) {
    val offsetX by animateDpAsState(
        targetValue = if (isOpen) 0.dp else (-240).dp,
        animationSpec = tween(300)
    )

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(240.dp)
            .offset(x = offsetX)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Close Drawer",
                color = Color.White,
                modifier = Modifier
                    .clickable { onClose() }
                    .padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Home",
                color = Color.White,
                modifier = Modifier
                    .clickable { onNavigate(ScreenRoute.HomeScreen) }
                    .padding(vertical = 8.dp)
            )

            Text(
                text = "Qibla",
                color = Color.White,
                modifier = Modifier
                    .clickable { onNavigate(ScreenRoute.QiblaScreen) }
                    .padding(vertical = 8.dp)
            )

            Text(
                text = "Adhkar",
                color = Color.White,
                modifier = Modifier
                    .clickable { onNavigate(ScreenRoute.AdhkarScreen) }
                    .padding(vertical = 8.dp)
            )

            Text(
                text = "Prayer Times",
                color = Color.White,
                modifier = Modifier
                    .clickable { onNavigate(ScreenRoute.PrayerTimesScreen) }
                    .padding(vertical = 8.dp)
            )

            Text(
                text = "Names of Allah",
                color = Color.White,
                modifier = Modifier
                    .clickable { onNavigate(ScreenRoute.NamesOfAllahScreen) }
                    .padding(vertical = 8.dp)
            )

            Text(
                text = "Radio",
                color = Color.White,
                modifier = Modifier
                    .clickable { onNavigate(ScreenRoute.RadioScreen) }
                    .padding(vertical = 8.dp)
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
    navController: NavHostController,
    drawerOpen: Boolean,
    onMenuClick: () -> Unit
) {
    // Define the animation states
    val currentState = if (drawerOpen) MenuState.EXPANDED else MenuState.COLLAPSED
    val transition = updateTransition(currentState, label = "menuTransition")

    // Animation values
    val scale by transition.animateFloat(
        transitionSpec = { tween(300, easing = LinearOutSlowInEasing) },
        label = "scale"
    ) { state ->
        when (state) {
            MenuState.EXPANDED -> 0.7f
            MenuState.COLLAPSED -> 1f
        }
    }

    val offset by transition.animateOffset(
        transitionSpec = { tween(300, easing = LinearOutSlowInEasing) },
        label = "offset"
    ) { state ->
        when (state) {
            MenuState.EXPANDED -> Offset(450f, 60f)
            MenuState.COLLAPSED -> Offset(0f, 0f)
        }
    }

    val roundness by transition.animateDp(
        transitionSpec = { tween(300) },
        label = "roundness"
    ) { state ->
        when (state) {
            MenuState.EXPANDED -> 20.dp
            MenuState.COLLAPSED -> 0.dp
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                translationX = offset.x
            }
            .clip(RoundedCornerShape(roundness))
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = { Text("Anees App", fontSize = 20.sp) },
            navigationIcon = {
                IconButton(onClick = onMenuClick) { // Fixed: using the passed onMenuClick
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu"
                    )
                }
            }
        )

        SetUpNavHost(navController)
    }

}




