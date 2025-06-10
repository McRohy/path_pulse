package com.example.pathpulse.helpers

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.pathpulse.R

//https://www.youtube.com/watch?v=4Etw10WTJxM
class PhotoPickerHelper(
    private val activity: ComponentActivity
) {
    var selectedImageUri by mutableStateOf<Uri?>(null)

    @Composable
    fun PhotoUploader(modifier: Modifier = Modifier, onPicked: (Uri) -> Unit) {
        val launcher = rememberLauncherForActivityResult(
            ActivityResultContracts.PickVisualMedia(),
            onResult = { uri ->
                uri?.let {
                    selectedImageUri = it
                    onPicked(it)
                }
            }
        )
        Button(
            onClick = { launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
            modifier = modifier,
            shape  = RoundedCornerShape(dimensionResource(R.dimen.rounded_shape_corner)),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            Text(stringResource(R.string.pick_one_img), style = MaterialTheme.typography.headlineMedium)
        }
    }

    companion object {
        // Saver rozloží helper na String? a zase ho zloží naspäť
        fun saver(activity: ComponentActivity) = Saver<PhotoPickerHelper, String>(
            save = { it.selectedImageUri?.toString() },
            restore = { uriString ->
                PhotoPickerHelper(activity).apply {
                    selectedImageUri = uriString.let(Uri::parse)
                }
            }
        )
    }
}



