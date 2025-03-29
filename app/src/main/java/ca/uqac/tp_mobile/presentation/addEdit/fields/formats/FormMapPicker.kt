package ca.uqac.tp_mobile.presentation.addEdit.fields.formats

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun FormMapPicker() {
    var selectedLocation by remember { mutableStateOf<LatLng?>(null) }
    var userLocation by remember { mutableStateOf<LatLng?>(null) }
    val context = LocalContext.current
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    // Demander la permission de localisation
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Obtenir la position de l'utilisateur
            if (ContextCompat.checkSelfPermission(
                    context, Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                location?.let {
                    userLocation = LatLng(it.latitude, it.longitude)
                }
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Carte Google Maps
        GoogleMap(modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
            cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(userLocation ?: LatLng(0.0, 0.0), 10f)
            },
            onMapClick = { latLng ->
                selectedLocation = latLng
            }) {
            userLocation?.let { location ->
                Marker(
                    state = MarkerState(position = location), title = "Votre position"
                )
            }
            selectedLocation?.let { location ->
                Marker(
                    state = MarkerState(position = location), title = "Emplacement sélectionné"
                )
            }
        }

        // Afficher les coordonnées sélectionnées
        selectedLocation?.let { location ->
            Text("Latitude: ${location.latitude}, Longitude: ${location.longitude}")
        }

        // Bouton de validation
        Button(onClick = {
            // Traiter la sélection de l'emplacement
        }) {
            Text("Valider la sélection")
        }
    }
}
