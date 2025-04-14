package ca.uqac.tp_mobile.presentation.addEdit.fields.formats

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun FormLocationField(
    modifier: Modifier = Modifier,
    locationName: String,
    onLocationSelected: (locationName: String, locationLat: Double, locationLng: Double) -> Unit
) {
    var showMapModal by remember { mutableStateOf(false) }

    Button(modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD1D4FF)),
        onClick = {
            Log.d("FormLocationField", "Ouverture de la modale localisation")
            showMapModal = true
        }) {
        Text(
            text = locationName.ifEmpty { "Lieu de la routine" },
            modifier = Modifier.fillMaxWidth(),
            color = Color(0xFF000547)
        )
    }

    if (showMapModal) {
        LocationPickerModal(onDismissRequest = { showMapModal = false },
            onLocationSelected = { selectedName, lat, lng ->
                onLocationSelected(selectedName, lat, lng)
                showMapModal = false
            })
    }
}

@Composable
fun LocationPickerModal(
    onDismissRequest: () -> Unit,
    onLocationSelected: (locationName: String, locationLat: Double, locationLng: Double) -> Unit
) {
    var query by remember { mutableStateOf("") }
    // Pour commencer centrer la carte sur l'UQAC par défaut
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(48.4177, -71.0522), 10f)
    }
    var selectedLatLng by remember { mutableStateOf<LatLng?>(null) }

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                TextField(value = query,
                    onValueChange = { query = it },
                    placeholder = { Text("Nommez le lieu") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    GoogleMap(modifier = Modifier.fillMaxSize(),
                        cameraPositionState = cameraPositionState,
                        onMapClick = { latLng ->
                            Log.d("MapClick", "Clique sur la carte en $latLng")
                            query = ""
                            selectedLatLng = latLng
                        },
                        onPOIClick = { poi ->
                            Log.d(
                                "MapClick POI",
                                "Clique sur un point d'intérêt : ${poi.name} en ${poi.latLng}"
                            )
                            selectedLatLng = poi.latLng
                            query = poi.name.replace("\n", " ").takeIf { it.isNotBlank() }
                                ?: "Lieu sans nom"
                        }) {
                        selectedLatLng?.let { latLng ->
                            Marker(
                                state = MarkerState(position = latLng), title = "Sélectionné"
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = { onDismissRequest() }) {
                        Text("Annuler")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        selectedLatLng?.let { latLng ->
                            val locationDisplayName = query.ifBlank { "Point sélectionné" }
                            onLocationSelected(
                                locationDisplayName, latLng.latitude, latLng.longitude
                            )
                        }
                    }) {
                        Text("Confirmer")
                    }
                }
            }
        }
    }
}