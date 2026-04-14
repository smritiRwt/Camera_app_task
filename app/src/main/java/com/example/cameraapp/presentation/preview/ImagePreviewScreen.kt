package com.example.cameraapp.presentation.preview

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.ui.res.stringResource
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PhotoLibrary
import com.example.cameraapp.R
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cameraapp.presentation.ThemeViewModel

import coil.compose.rememberAsyncImagePainter
import com.example.cameraapp.presentation.preview.viewmodel.PreviewViewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImagePreviewScreen(
    navController: NavController,
    viewModel: PreviewViewModel = hiltViewModel(),
    themeViewModel: ThemeViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadSavedImages(context)
    }

    val savedStateHandle =
        navController.currentBackStackEntry?.savedStateHandle

    val imagesFromCamera =
        savedStateHandle?.getStateFlow<List<String>?>("images", null)
            ?.collectAsState()

    LaunchedEffect(imagesFromCamera?.value) {
        imagesFromCamera?.value?.let { newImages ->
            viewModel.addNewImages(context, newImages)
            savedStateHandle.remove<List<String>>("images")
        }
    }

    val images = viewModel.images

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(R.string.app_name)) },
            )
        },
        bottomBar = {
            Button(
                onClick = { navController.navigate("camera") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = stringResource(R.string.capture_image))
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {


            if (images.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.PhotoLibrary,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = Color.LightGray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text =stringResource(R.string.no_captured_image),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                }

            } else {

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp)
                ) {

                    items(images) { image ->

                        Card(
                            modifier = Modifier
                                .padding(6.dp)
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {

                            Box {

                                // 📸 Image
                                Image(
                                    painter = rememberAsyncImagePainter(File(image.path)),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(110.dp),
                                    contentScale = ContentScale.Crop
                                )

                                Text(
                                    text = image.zoomLabel,
                                    color = Color.White,
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .background(Color.Black.copy(0.6f))
                                        .padding(horizontal = 4.dp, vertical = 2.dp),
                                    style = MaterialTheme.typography.labelSmall
                                )

                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(6.dp)
                                        .size(22.dp)
                                        .background(Color.White, shape = CircleShape)
                                        .clickable { viewModel.deleteImage(image) },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = null,
                                        tint = Color.Black,
                                        modifier = Modifier.size(12.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
