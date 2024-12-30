
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.foundation.lazy.grid.*
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ContentAlpha
import androidx.compose.material.icons.filled.Favorite
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.firebase.Timestamp
import hoods.com.notes.Utils
import hoods.com.notes.home.HomeViewModel
import hoods.com.notes.models.Notes
import hoods.com.notes.repository.Resources
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    homeViewModel: HomeViewModel,
    onNoteClick: (String) -> Unit,
    navToDetailPage: () -> Unit,
    navToLoginPage: () -> Unit,
    navToSearchUserPage: (String) -> Unit
) {
    val homeUiState = homeViewModel.homeUiState

    var openDialog by remember {
        mutableStateOf(false)
    }
    var selectedNote: Notes? by remember {
        mutableStateOf(null)
    }

    LaunchedEffect(key1 = Unit) {
        homeViewModel.loadNotes()
    }

    Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                        onClick = { navToDetailPage.invoke() }
                ) {
                    Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Note"
                    )
                }
            },
            topBar = {
                TopAppBar(
                        title = { Text(text = "Notes") },
                        actions = {
                            IconButton(
                                    onClick = {
                                        homeViewModel.signOut()
                                        navToLoginPage.invoke()
                                    }
                            ) {
                                Icon(
                                        imageVector = Icons.Default.ExitToApp,
                                        contentDescription = "Sign Out"
                                )
                            }
                        }
                )
            }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            when (homeUiState.notesList) {
                is Resources.Loading -> {
                    Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is Resources.Success -> {
                    LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(16.dp),
                    ) {

                        if (homeUiState.favoriteNotes.isNotEmpty()) {
                            item(span = { GridItemSpan(2) }) {
                                Text(
                                        text = "Favorites",
                                        style = MaterialTheme.typography.headlineMedium,
                                        modifier = Modifier.padding(vertical = 8.dp)
                                )
                            }

                            items(homeUiState.favoriteNotes) { note ->
                                NoteItem(
                                        notes = note,
                                        onLongClick = {
                                            selectedNote = note
                                            openDialog = true
                                        },
                                        onClick = { onNoteClick(note.documentId) },
                                        isFavorite = true  // Novo parâmetro para mostrar ícone de favorito
                                )
                            }
                        }




                        if (homeUiState.ownNotes.isNotEmpty()) {
                            item(span = { GridItemSpan(2) }) {
                                Column(
                                        modifier = Modifier.padding(vertical = 8.dp)
                                ) {
                                    Text(
                                            text = "My Notes",
                                            style = MaterialTheme.typography.headlineMedium
                                    )
                                    homeViewModel.currentUser?.email?.let { email ->
                                        Text(
                                                text = email,
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                        )
                                    }
                                }
                            }

                            items(homeUiState.ownNotes) { note ->
                                NoteItem(
                                        notes = note,
                                        onLongClick = {
                                            selectedNote = note
                                            openDialog = true
                                        },
                                        onClick = { onNoteClick(note.documentId) }
                                )
                            }
                        }

                        if (homeUiState.sharedNotes.isNotEmpty()) {
                            item(span = { GridItemSpan(2) }) {
                                Text(
                                        text = "Shared with Me",
                                        style = MaterialTheme.typography.headlineMedium,
                                        modifier = Modifier.padding(vertical = 8.dp)
                                )
                            }

                            items(homeUiState.sharedNotes) { note ->
                                NoteItem(
                                        notes = note,
                                        onLongClick = {
                                            selectedNote = note
                                            openDialog = true
                                        },
                                        onClick = { onNoteClick(note.documentId) },
                                        isShared = true
                                )
                            }
                        }
                    }
                }

                else -> {
                    Text(
                            text = homeUiState.notesList.throwable?.localizedMessage ?: "Unknown Error",
                            color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }

    // Dialog
    if (openDialog) {
        AlertDialog(
                onDismissRequest = { openDialog = false },
                title = { Text("Note Options") },
                text = {
                    if (selectedNote?.userId == homeViewModel.currentUser?.uid) {
                        Text("What would you like to do with this note?")
                    } else {
                        Text("This is a shared note")
                    }
                },
                confirmButton = {
                    if (selectedNote?.userId == homeViewModel.currentUser?.uid) {
                        Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth()
                        ) {
                            Button(
                                    onClick = {
                                        selectedNote?.documentId?.let { noteId ->
                                            navToSearchUserPage(noteId)
                                        }
                                        openDialog = false
                                    },
                                    modifier = Modifier.weight(1f)
                            ) {
                                Text("Share")
                            }

                            Button(
                                    onClick = {
                                        selectedNote?.documentId?.let {
                                            homeViewModel.deleteNote(it)
                                        }
                                        openDialog = false
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.error
                                    ),
                                    modifier = Modifier.weight(1f)
                            ) {
                                Text("Delete")
                            }
                        }
                    }
                },
                dismissButton = {
                    TextButton(onClick = { openDialog = false }) {
                        Text("Cancel")
                    }
                }
        )
    }

    LaunchedEffect(key1 = homeViewModel.hasUser) {
        if (!homeViewModel.hasUser) {
            navToLoginPage.invoke()
        }
    }
}



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteItem(
    notes: Notes,
    onLongClick: () -> Unit,
    onClick: () -> Unit,
    isShared: Boolean = false,
    isFavorite: Boolean = false
) {
    Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .combinedClickable(
                        onClick = onClick,
                        onLongClick = onLongClick
                ),
            colors = CardDefaults.cardColors(
                    containerColor = Utils.colors[notes.color]
            )
    ) {
        Column(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
        ) {
            Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                        text = notes.title,
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                )

                Row {
                    if (isFavorite) {
                        Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Favorite note",
                                modifier = Modifier.size(20.dp),
                                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                    if (isShared) {
                        Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Shared note",
                                modifier = Modifier.size(20.dp),
                                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                    text = notes.description,
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 4,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                    text = formatDate(notes.timestamp),
                    style = MaterialTheme.typography.bodySmall,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.align(Alignment.End),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }
    }
}

private fun formatDate(timestamp: Timestamp): String {
    val sdf = SimpleDateFormat("MM-dd-yy HH:mm", Locale.getDefault())
    return sdf.format(timestamp.toDate())
}