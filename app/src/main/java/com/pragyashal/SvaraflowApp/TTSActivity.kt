
package com.pragyashal.SvaraflowApp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.pragyashal.SvaraflowApp.theme.SvaraFlowApplicationTheme
import kotlinx.coroutines.launch

// Data class for sample audio and sealed class for navigation items
data class SampleAudio(val title: String, val language: String)
sealed class Screen(val title: String, val icon: @Composable () -> Unit) {
    object HdVoiceGeneration : Screen("HD Voice Generation", { Icon(Icons.Default.GraphicEq, null) })
    object CustomVoiceGeneration : Screen("Custom Voice Generation", { Icon(Icons.Default.RecordVoiceOver, null) })
    object MultiSpeakerPodcast : Screen("Multi-speaker Podcast", { Icon(Icons.Default.Podcasts, null) })
    object UpcomingFeatures : Screen("Upcoming Features", { Icon(Icons.Default.NewReleases, null) })
    object YourFiles : Screen("Your Files", { Icon(Icons.Default.Folder, null) })
    object SampleMedia : Screen("Sample Media", { Icon(Icons.Default.Audiotrack, null) })
}

@OptIn(ExperimentalMaterial3Api::class)
class TTSActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // This state variable will control the theme
            var isDarkTheme by remember { mutableStateOf(false) }

            SvaraFlowApplicationTheme(darkTheme = isDarkTheme) {
                MainAppShell(
                    isDarkTheme = isDarkTheme,
                    onThemeToggle = { isDarkTheme = !isDarkTheme }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppShell(isDarkTheme: Boolean, onThemeToggle: () -> Unit) {
    val context = LocalContext.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navigationItems = listOf(
        Screen.HdVoiceGeneration,
        Screen.CustomVoiceGeneration,
        Screen.MultiSpeakerPodcast,
        Screen.UpcomingFeatures,
        Screen.YourFiles,
        Screen.SampleMedia
    )
    var selectedScreen: Screen by remember { mutableStateOf(Screen.HdVoiceGeneration) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))
                navigationItems.forEach { screen ->
                    NavigationDrawerItem(
                        icon = screen.icon,
                        label = { Text(screen.title) },
                        selected = screen == selectedScreen,
                        onClick = {
                            scope.launch { drawerState.close() }
                            selectedScreen = screen
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(selectedScreen.title) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    actions = {
                        // Theme Toggle Button
                        IconButton(onClick = onThemeToggle) {
                            Icon(
                                imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                                contentDescription = "Toggle Theme"
                            )
                        }
                        IconButton(onClick = { context.startActivity(Intent(context, SubscriptionActivity::class.java)) }) {
                            Icon(Icons.Filled.CardMembership, contentDescription = "Subscription")
                        }
                        IconButton(onClick = { context.startActivity(Intent(context, ProfileActivity::class.java)) }) {
                            Icon(Icons.Filled.AccountCircle, contentDescription = "Profile")
                        }
                    }
                )
            }
        ) { innerPadding ->
            // Show the correct screen based on the user's selection
            when (selectedScreen) {
                is Screen.HdVoiceGeneration -> HdVoiceGenerationScreen(innerPadding)
                is Screen.CustomVoiceGeneration -> CustomVoiceGenerationScreen(innerPadding)
                is Screen.MultiSpeakerPodcast -> MultiSpeakerPodcastScreen(innerPadding)
                is Screen.UpcomingFeatures -> UpcomingFeaturesScreen(innerPadding)
                is Screen.YourFiles -> YourFilesScreen(innerPadding)
                is Screen.SampleMedia -> SampleMediaScreen(innerPadding)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HdVoiceGenerationScreen(paddingValues: PaddingValues) {
    var textToSpeak by remember { mutableStateOf(TextFieldValue("")) }
    var speechRate by remember { mutableStateOf(1.0f) }
    val voiceOptions = listOf("Aoede", "Zeus", "Hera", "Apollo")
    var selectedVoice by remember { mutableStateOf(voiceOptions[0]) }
    var isVoiceMenuExpanded by remember { mutableStateOf(false) }
    val languageOptions = listOf("English (US)", "English (UK)", "Spanish", "French")
    var selectedLanguage by remember { mutableStateOf(languageOptions[0]) }
    var isLanguageMenuExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(4.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Text to Speak", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = textToSpeak, onValueChange = { textToSpeak = it }, label = { Text("Enter text here...") }, modifier = Modifier.fillMaxWidth().height(150.dp))
                Text(text = "${10000 - textToSpeak.text.length} characters remaining", style = MaterialTheme.typography.bodySmall, modifier = Modifier.align(Alignment.End))
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(4.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Settings", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                ExposedDropdownMenuBox(expanded = isVoiceMenuExpanded, onExpandedChange = { isVoiceMenuExpanded = !isVoiceMenuExpanded }) {
                    OutlinedTextField(value = selectedVoice, onValueChange = {}, readOnly = true, label = { Text("Select Voice") }, trailingIcon = { Icon(Icons.Filled.ArrowDropDown, contentDescription = null) }, modifier = Modifier.menuAnchor().fillMaxWidth())
                    ExposedDropdownMenu(expanded = isVoiceMenuExpanded, onDismissRequest = { isVoiceMenuExpanded = false }) {
                        voiceOptions.forEach { voice -> DropdownMenuItem(text = { Text(voice) }, onClick = { selectedVoice = voice; isVoiceMenuExpanded = false }) }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                ExposedDropdownMenuBox(expanded = isLanguageMenuExpanded, onExpandedChange = { isLanguageMenuExpanded = !isLanguageMenuExpanded }) {
                    OutlinedTextField(value = selectedLanguage, onValueChange = {}, readOnly = true, label = { Text("Select Language") }, trailingIcon = { Icon(Icons.Filled.ArrowDropDown, contentDescription = null) }, modifier = Modifier.menuAnchor().fillMaxWidth())
                    ExposedDropdownMenu(expanded = isLanguageMenuExpanded, onDismissRequest = { isLanguageMenuExpanded = false }) {
                        languageOptions.forEach { language -> DropdownMenuItem(text = { Text(language) }, onClick = { selectedLanguage = language; isLanguageMenuExpanded = false }) }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text("Speech Rate: ${String.format("%.2f", speechRate)}x")
                Slider(value = speechRate, onValueChange = { speechRate = it }, valueRange = 0.25f..2.0f, steps = 6)
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { /* TODO: Add Synthesize Speech logic */ }, modifier = Modifier.fillMaxWidth()) { Text("Synthesize Speech") }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { /* TODO: Add Generate Images logic */ }, modifier = Modifier.fillMaxWidth()) {
            Icon(painterResource(id = R.drawable.ic_generate_image), contentDescription = null, modifier = Modifier.size(20.dp)); Spacer(modifier = Modifier.width(8.dp)); Text("Generate Images")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { /* TODO: Add Upload File logic */ }, modifier = Modifier.weight(1f)) {
                Icon(painterResource(id = R.drawable.ic_upload_file), contentDescription = null, modifier = Modifier.size(20.dp)); Spacer(modifier = Modifier.width(8.dp)); Text("Upload File")
            }
            Button(onClick = { /* TODO: Add Download logic */ }, modifier = Modifier.weight(1f)) {
                Icon(painterResource(id = R.drawable.ic_download), contentDescription = null, modifier = Modifier.size(20.dp)); Spacer(modifier = Modifier.width(8.dp)); Text("Download")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun YourFilesScreen(paddingValues: PaddingValues) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    Column(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp)) {
        Text("Your Files", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(value = searchQuery, onValueChange = { searchQuery = it }, label = { Text("Search your files by content...") }, modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { /* TODO: Search logic */ }) { Text("Search") }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "No files uploaded yet.", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
    }
}

@Composable
fun SampleMediaScreen(paddingValues: PaddingValues) {
    val sampleMediaList = listOf(
        SampleAudio("Zephyr", "Bengali"), SampleAudio("Ainilam", "English"), SampleAudio("Gacrux", "Gujarati"),
        SampleAudio("Puck", "Hindi"), SampleAudio("Despina", "Kannada"), SampleAudio("Achernar", "Malayalam"),
        SampleAudio("Umbriel", "Marathi"), SampleAudio("Sulafat", "Tamil"), SampleAudio("Aoede", "Telugu")
    )

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 160.dp),
        modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(sampleMediaList) { sample ->
            SampleAudioCard(sample = sample)
        }
    }
}

@Composable
fun CustomVoiceGenerationScreen(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Custom Voice Generation (Coming Soon)", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text("This section will allow you to train and use your own custom voice models.", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun MultiSpeakerPodcastScreen(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Multi-speaker Podcast (Coming Soon)", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Effortlessly create podcasts with multiple distinct voices.", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun UpcomingFeaturesScreen(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Upcoming Features (Coming Soon)", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Stay tuned for exciting new features like real-time audio editing, advanced voice effects, and more!", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun SampleAudioCard(sample: SampleAudio) {
    Card(elevation = CardDefaults.cardElevation(2.dp)) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(sample.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(sample.language, style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { /* TODO: Play/Pause logic */ }) { Icon(Icons.Filled.PlayCircle, contentDescription = "Play") }
                Text("0:00", style = MaterialTheme.typography.bodySmall)
                Slider(value = 0f, onValueChange = {}, modifier = Modifier.weight(1f).padding(horizontal = 8.dp))
                IconButton(onClick = { /* TODO: More options */ }) { Icon(Icons.Filled.MoreVert, contentDescription = "More") }
            }
        }
    }
}
