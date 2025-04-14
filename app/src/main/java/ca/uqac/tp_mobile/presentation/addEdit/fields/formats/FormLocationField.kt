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
import androidx.compose.material3.TextFieldDefaults
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
    initialLatLng: LatLng? = null,
    initialLocationName: String,
    onOpenMap: () -> Unit,
    onLocationSelected: (locationName: String, locationLat: Double, locationLng: Double) -> Unit,
    onNoSelection: () -> Unit
) {
    var showMapModal by remember { mutableStateOf(false) }

    Button(modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD1D4FF)),
        onClick = {
            onOpenMap()
            showMapModal = true
        }) {
        Text(
            text = locationName.ifEmpty { "Lieu de la routine" },
            modifier = Modifier.fillMaxWidth(),
            color = Color(0xFF000547)
        )
    }

    if (showMapModal) {
        LocationPickerModal(initialLatLng = initialLatLng,
            initialLocationName = initialLocationName,
            onDismissRequest = { showMapModal = false },
            onLocationSelected = { selectedName, lat, lng ->
                onLocationSelected(selectedName, lat, lng)
                showMapModal = false
            },
            onNoSelection = {
                onNoSelection()
            })
    }
}

@Composable
fun LocationPickerModal(
    onDismissRequest: () -> Unit,
    initialLatLng: LatLng? = null,
    initialLocationName: String,
    onLocationSelected: (locationName: String, locationLat: Double, locationLng: Double) -> Unit,
    onNoSelection: () -> Unit
) {
    var query by remember { mutableStateOf(initialLocationName) }
    // Pour commencer centrer la carte sur le lieu défini ou l'UQAC par défaut
    val defaultLocation = initialLatLng ?: LatLng(48.4177, -71.0522)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultLocation, 10f)
    }
    var selectedLatLng by remember { mutableStateOf(initialLatLng) }

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                TextField(value = query,
                    onValueChange = { query = it },
                    placeholder = { Text("Nommez le lieu") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF4F4FB),
                        unfocusedContainerColor = Color(0xFFF4F4FB),
                        focusedIndicatorColor = Color(0xFFD1D4FF),
                        unfocusedIndicatorColor = Color(0xFFD1D4FF)
                    )
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
                        Text("Annuler", color = Color(0xFF000547))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        if (selectedLatLng == null) {
                            onNoSelection()
                        } else {
                            selectedLatLng!!.let { latLng ->
                                val locationDisplayName = query.ifBlank { "Point sélectionné" }
                                onLocationSelected(
                                    locationDisplayName, latLng.latitude, latLng.longitude
                                )
                            }
                        }

                    }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00141F))) {
                        Text("Confirmer", color = Color(0xFFF4F4FB))
                    }
                }
            }
        }
    }
}