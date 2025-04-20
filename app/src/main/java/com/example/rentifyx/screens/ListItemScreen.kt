package com.example.rentifyx.screens

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.rentifyx.reusablecomposable.CustomInputField
import com.example.rentifyx.reusablecomposable.PrimaryButton
import com.example.rentifyx.viewmodel.ListItemViewModel
import java.io.File

@Composable
fun ListItemScreen(bottomNavController: NavHostController) {

    val context = LocalContext.current
    val viewModel = ListItemViewModel(LocalContext.current.applicationContext as Application)

    var productName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var pinCode by remember { mutableStateOf("") }
    var contactNumber by remember { mutableStateOf("") }
    var pricePerDay by remember { mutableStateOf("") }
    var loadingImages by remember { mutableStateOf(false) }
    var compressedImageFileList by remember { mutableStateOf<List<File>>(listOf()) }

    var currentImageIndex by remember { mutableIntStateOf(0) }

    val hasPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ContextCompat.checkSelfPermission(
            context, Manifest.permission.READ_MEDIA_IMAGES
        ) == PackageManager.PERMISSION_GRANTED
    } else {
        ContextCompat.checkSelfPermission(
            context, Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    val multipleImagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(), onResult = { listOfUris ->
            if (listOfUris.size > 10) {
                Toast.makeText(
                    context,
                    "Images can not be selected more than 10",
                    Toast.LENGTH_LONG
                ).show()
            } else if (listOfUris.isNotEmpty()) {
                loadingImages = true
                viewModel.compressImages(context, listOfUris) { listOfFile ->
                    loadingImages = false
                    compressedImageFileList = listOfFile
                    currentImageIndex = 0
                }
            }
        })

    val singleImagePickupLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(), onResult = { uri ->
            uri?.let {
                viewModel.compressImages(context, listOf(uri)) { listOfFile ->
                    if (listOfFile.isNotEmpty()) {
                        compressedImageFileList =
                            compressedImageFileList.toMutableList()
                                .also { list -> list[currentImageIndex] = listOfFile[0] }
                    }
                }
            }
        })

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(), onResult = { isGranted ->
            if (isGranted) {
                multipleImagePickerLauncher.launch("image/*")
            } else {
                Toast.makeText(
                    context,
                    "You need to grant media permission to select images",
                    Toast.LENGTH_LONG
                ).show()
            }
        })

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .imePadding(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(20))
                .background(Color.White)
                .clickable {
                    if (hasPermission) {
                        multipleImagePickerLauncher.launch("image/*")
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                        } else {
                            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                        }
                    }
                }, contentAlignment = Alignment.Center
        ) {
            if (compressedImageFileList.isEmpty() && loadingImages == false) {
                Text(
                    "Select product images",
                    color = Color.Gray,
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center,
                )
            } else if (loadingImages) {
                CircularProgressIndicator(
                    modifier = Modifier.size(25.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            } else {
                Log.d("compress", "Loading Images")
                Image(
                    painter = rememberAsyncImagePainter(model = compressedImageFileList[currentImageIndex]),
                    contentDescription = "product_image",
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            singleImagePickupLauncher.launch("image/*")
                        },
                    contentScale = ContentScale.Crop
                )
            }


            if (currentImageIndex > 0) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(50.dp)
                        .align(Alignment.CenterStart)
                        .clickable {
                            currentImageIndex = currentImageIndex - 1
                        }, contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "arrow_back",
                        tint = Color.White
                    )
                }
            }

            if (currentImageIndex < (compressedImageFileList.size - 1)) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(50.dp)
                        .align(Alignment.CenterEnd)
                        .clickable {
                            currentImageIndex = currentImageIndex + 1
                        }, contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "arrow_forward",
                        tint = Color.White
                    )
                }
            }

            //Dot Indicator
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 10.dp)
            ) {
                compressedImageFileList.forEachIndexed { index, _ ->
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 3.dp)
                            .size(if (index == currentImageIndex) 10.dp else 6.dp)
                            .clip(CircleShape)
                            .background(
                                if (index == currentImageIndex) Color.White else Color.White.copy(
                                    alpha = 0.4f
                                )
                            )
                    )
                }
            }
        }

        CustomInputField(
            value = productName, onValueChange = {
                productName = it
            }, modifier = Modifier.padding(start = 20.dp, end = 20.dp), label = "Product Name"
        )

        CustomInputField(
            value = address, onValueChange = {
                address = it
            }, modifier = Modifier.padding(start = 20.dp, end = 20.dp), label = "Address"
        )

        CustomInputField(
            value = pinCode, onValueChange = {
                pinCode = it
            }, modifier = Modifier.padding(start = 20.dp, end = 20.dp), label = "PinCode"
        )

        CustomInputField(
            value = contactNumber, onValueChange = {
                contactNumber = it
            }, modifier = Modifier.padding(start = 20.dp, end = 20.dp), label = "Contact Number"
        )

        CustomInputField(
            value = pricePerDay, onValueChange = {
                pricePerDay = it
            }, modifier = Modifier.padding(start = 20.dp, end = 20.dp), label = "Price Per Day"
        )

        PrimaryButton(
            onClick = { /* Handle listing */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(50.dp)
                .align(Alignment.CenterHorizontally),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 6.dp,
                pressedElevation = 8.dp
            ),
        ) {
            Text(
                text = "List The Product",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(50.dp))
    }
}
