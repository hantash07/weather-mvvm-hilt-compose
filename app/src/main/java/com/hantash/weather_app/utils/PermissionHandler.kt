package com.hantash.weather_app.utils

import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun RequestPermission(
    permission: String,
    rationaleMessage: String = "This permission is required for the app to function properly.",
    onPermissionResult: (Boolean) -> Unit
) {
    val permissionGrantedState = rememberUpdatedState(onPermissionResult)
    val showRationale = remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        permissionGrantedState.value(isGranted)
    }

    if (showRationale.value) {
        // Display rationale (if needed)
        PermissionRationaleDialog(
            message = rationaleMessage,
            onDismiss = { showRationale.value = false },
            onConfirm = {
                showRationale.value = false
                launcher.launch(permission)
            }
        )
    }

    PermissionHandler(
        permission = permission,
        launcher = launcher,
        showRationale = { showRationale.value = true },
    )
}

private fun isPermissionGranted(activity: ComponentActivity, permission: String): Boolean {
    return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED
}

@Composable
private fun PermissionHandler(
    permission: String,
    launcher: androidx.activity.result.ActivityResultLauncher<String>,
    showRationale: () -> Unit
) {
    val activity = LocalContext.current as ComponentActivity

    when {
        isPermissionGranted(activity, permission) -> {
            launcher.launch(permission)
        }
        activity.shouldShowRequestPermissionRationale(permission) -> {
            // Show rationale if needed
            showRationale()
        }
        else -> {
            // Request permission
            launcher.launch(permission)
        }
    }
}

@Composable
private fun PermissionRationaleDialog(
    message: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Permission Required") },
        text = { Text(message) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Grant")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}