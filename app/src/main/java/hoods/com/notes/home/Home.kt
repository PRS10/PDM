//package hoods.com.notes.home
//
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.Timestamp
import hoods.com.notes.Utils
import hoods.com.notes.home.HomeUiState
import hoods.com.notes.home.HomeViewModel
import hoods.com.notes.models.Notes
import hoods.com.notes.repository.Resources
import hoods.com.notes.ui.theme.NotesTheme
import java.text.SimpleDateFormat
import java.util.*

//@Composable
//fun Home(
//    homeViewModel: HomeViewModel?,
//    onNoteClick: (id: String) -> Unit,
//    navToDetailPage: () -> Unit,
//    navToLoginPage: () -> Unit,
//) {
//    val homeUiState = homeViewModel?.homeUiState ?: HomeUiState()
//
//    var openDialog by remember {
//        mutableStateOf(false)
//    }
//    var selectedNote: Notes? by remember {
//        mutableStateOf(null)
//    }
//
//    val scaffoldState = rememberScaffoldState()
//
//    LaunchedEffect(key1 = Unit){
//        homeViewModel?.loadNotes()
//    }
//
//    Scaffold(
//        scaffoldState = scaffoldState,
//        floatingActionButton = {
//            FloatingActionButton(
//                onClick = {
//                    navToDetailPage.invoke()
//                }) {
//                Icon(
//                    imageVector = Icons.Default.Add,
//                    contentDescription = null,
//                )
//            }
//        },
//        topBar = {
//            TopAppBar(
//                navigationIcon = {},
//                actions = {
//                    IconButton(onClick = {
//                        homeViewModel?.signOut()
//                        navToLoginPage.invoke()
//                    }) {
//                        Icon(
//                            imageVector = Icons.Default.ExitToApp,
//                            contentDescription = null,
//                        )
//                    }
//                },
//                title = {
//                    Text(text = "Home")
//                }
//            )
//        }
//    ) { padding ->
//        Column(modifier = Modifier.padding(padding)) {
//            when (homeUiState.notesList) {
//                is Resources.Loading -> {
//                    CircularProgressIndicator(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .wrapContentSize(align = Alignment.Center)
//                    )
//                }
//                is Resources.Success -> {
//                    LazyVerticalGrid(
//                        columns = GridCells.Fixed(2),
//                        contentPadding = PaddingValues(16.dp),
//                    ) {
//                        items(
//                            homeUiState.notesList.data ?: emptyList()
//                        ) { note ->
//                            NoteItem(
//                                notes = note,
//                                onLongClick = {
//                                    openDialog = true
//                                    selectedNote = note
//                                },
//                            )
//                            {
//                                onNoteClick.invoke(note.documentId)
//                            }
//
//                        }
//
//
//                    }
//                    AnimatedVisibility(visible = openDialog) {
//                        AlertDialog(
//                            onDismissRequest = {
//                                openDialog = false
//                            },
//                            title = { Text(text = "Delete Note?") },
//                            confirmButton = {
//                                Button(
//                                    onClick = {
//                                        selectedNote?.documentId?.let {
//                                            homeViewModel?.deleteNote(it)
//                                        }
//                                        openDialog = false
//                                    },
//                                    colors = ButtonDefaults.buttonColors(
//                                        backgroundColor = Color.Red
//                                    ),
//                                ) {
//                                    Text(text = "Delete")
//                                }
//                                Button(onClick = {
//
//                                    openDialog = false
//                                }) {
//                                    Text(text = "Share note?")
//                                }
//
//                            },
//                            dismissButton = {
//                                Button(onClick = { openDialog = false }) {
//                                    Text(text = "Cancel")
//                                }
//                            }
//                        )
//
//
//                    }
//                }
//                else -> {
//                    Text(
//                        text = homeUiState
//                            .notesList.throwable?.localizedMessage ?: "Unknown Error",
//                        color = Color.Red
//                    )
//                    Log.e(
//                        "Home",
//                        homeUiState.notesList.throwable?.localizedMessage ?: "Unknown Error"
//                    )
//                }
//
//
//            }
//
//
//        }
//
//    }
//    LaunchedEffect(key1 = homeViewModel?.hasUser){
//        if (homeViewModel?.hasUser == false){
//            navToLoginPage.invoke()
//        }
//    }
//
//
//}
//
//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//fun NoteItem(
//    notes: Notes,
//    onLongClick: () -> Unit,
//    onClick: () -> Unit,
//) {
//    Card(
//        modifier = Modifier
//            .combinedClickable(
//                onLongClick = { onLongClick.invoke() },
//                onClick = { onClick.invoke() }
//            )
//            .padding(8.dp)
//            .fillMaxWidth(),
//        backgroundColor = Utils.colors[notes.color]
//    ) {
//
//        Column {
//            Text(
//                text = notes.title,
//                style = MaterialTheme.typography.h6,
//                fontWeight = FontWeight.Bold,
//                maxLines = 1,
//                overflow = TextOverflow.Clip,
//                modifier = Modifier.padding(4.dp)
//            )
//            Spacer(modifier = Modifier.size(4.dp))
//            CompositionLocalProvider(
//                LocalContentAlpha provides ContentAlpha.disabled
//            ) {
//                Text(
//                    text = notes.description,
//                    style = MaterialTheme.typography.body1,
//                    overflow = TextOverflow.Ellipsis,
//                    modifier = Modifier.padding(4.dp),
//                    maxLines = 4
//                )
//
//            }
//            Spacer(modifier = Modifier.size(4.dp))
//            CompositionLocalProvider(
//                LocalContentAlpha provides ContentAlpha.disabled
//            ) {
//                Text(
//                    text = formatDate(notes.timestamp),
//                    style = MaterialTheme.typography.body1,
//                    overflow = TextOverflow.Ellipsis,
//                    modifier = Modifier
//                        .padding(4.dp)
//                        .align(Alignment.End),
//                    maxLines = 4
//                )
//
//            }
//
//
//        }
//
//
//    }
//
//
//}
//
//
//private fun formatDate(timestamp: Timestamp): String {
//    val sdf = SimpleDateFormat("MM-dd-yy hh:mm", Locale.getDefault())
//    return sdf.format(timestamp.toDate())
//}
//
//
//@Preview
//@Composable
//fun PrevHomeScreen() {
//    NotesTheme {
//        Home(
//            homeViewModel = null,
//            onNoteClick = {},
////            navToLoginPage = { /*TODO*/ },
//            navToDetailPage = { /*TODO*/ }
//        ) {
//
//        }
//    }
//}
//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun PrevNoteItem(){
//
//    val note = Notes(
//            userId = "1",
//            title = "Nota",
//            description = "bla bla bla",
//            color = 2,
//            timestamp = Timestamp.now(),
//            documentId = "dsa"
//    )
//
//    NoteItem(
//            notes = note,
//            onLongClick = {},
//            onClick = {},
//    )
//
//
//}
//
//
//
//
//
//
//


@Composable
fun Home(
    homeViewModel: HomeViewModel?,
    onNoteClick: (id: String) -> Unit,
    navToDetailPage: () -> Unit,
    navToLoginPage: () -> Unit,
    navToSearchUserPage: () -> Unit,  // Nova função para navegação
) {
    val homeUiState = homeViewModel?.homeUiState ?: HomeUiState()

    var openDialog by remember {
        mutableStateOf(false)
    }
    var selectedNote: Notes? by remember {
        mutableStateOf(null)
    }

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = Unit) {
        homeViewModel?.loadNotes()
    }

    Scaffold(
            scaffoldState = scaffoldState,
            floatingActionButton = {
                FloatingActionButton(
                        onClick = {
                            navToDetailPage.invoke()
                        }) {
                    Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                    )
                }
            },
            topBar = {
                TopAppBar(
                        navigationIcon = {},
                        actions = {
                            IconButton(onClick = {
                                homeViewModel?.signOut()
                                navToLoginPage.invoke()
                            }) {
                                Icon(
                                        imageVector = Icons.Default.ExitToApp,
                                        contentDescription = null,
                                )
                            }
                        },
                        title = {
                            Text(text = "Home")
                        }
                )
            }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            when (homeUiState.notesList) {
                is Resources.Loading -> {
                    CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxSize()
                                .wrapContentSize(align = Alignment.Center)
                    )
                }
                is Resources.Success -> {
                    LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(16.dp),
                    ) {
                        items(
                                homeUiState.notesList.data ?: emptyList()
                        ) { note ->
                            NoteItem(
                                    notes = note,
                                    onLongClick = {
                                        openDialog = true
                                        selectedNote = note
                                    },
                            )
                            {
                                onNoteClick.invoke(note.documentId)
                            }
                        }
                    }

                    AnimatedVisibility(visible = openDialog) {
                        AlertDialog(
                                onDismissRequest = {
                                    openDialog = false
                                },
                                title = { Text(text = "Delete Note?") },
                                confirmButton = {
                                    Button(
                                            onClick = {
                                                selectedNote?.documentId?.let {
                                                    homeViewModel?.deleteNote(it)
                                                }
                                                openDialog = false
                                            },
                                            colors = ButtonDefaults.buttonColors(
                                                    backgroundColor = Color.Red
                                            ),
                                    ) {
                                        Text(text = "Delete")
                                    }
                                    Button(onClick = {
                                        navToSearchUserPage.invoke() // Navegar para a página de procura de utilizador
                                        openDialog = false
                                    }) {
                                        Text(text = "Share note?")
                                    }
                                },
                                dismissButton = {
                                    Button(onClick = { openDialog = false }) {
                                        Text(text = "Cancel")
                                    }
                                }
                        )
                    }
                }
                else -> {
                    Text(
                            text = homeUiState
                                .notesList.throwable?.localizedMessage ?: "Unknown Error",
                            color = Color.Red
                    )
                    Log.e(
                            "Home",
                            homeUiState.notesList.throwable?.localizedMessage ?: "Unknown Error"
                    )
                }
            }
        }
    }

    LaunchedEffect(key1 = homeViewModel?.hasUser) {
        if (homeViewModel?.hasUser == false) {
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
) {
    Card(
        modifier = Modifier
            .combinedClickable(
                onLongClick = { onLongClick.invoke() },
                onClick = { onClick.invoke() }
            )
            .padding(8.dp)
            .fillMaxWidth(),
        backgroundColor = Utils.colors[notes.color]
    ) {

        Column {
            Text(
                text = notes.title,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Clip,
                modifier = Modifier.padding(4.dp)
            )
            Spacer(modifier = Modifier.size(4.dp))
            CompositionLocalProvider(
                LocalContentAlpha provides ContentAlpha.disabled
            ) {
                Text(
                    text = notes.description,
                    style = MaterialTheme.typography.body1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(4.dp),
                    maxLines = 4
                )

            }
            Spacer(modifier = Modifier.size(4.dp))
            CompositionLocalProvider(
                LocalContentAlpha provides ContentAlpha.disabled
            ) {
                Text(
                    text = formatDate(notes.timestamp),
                    style = MaterialTheme.typography.body1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(4.dp)
                        .align(Alignment.End),
                    maxLines = 4
                )

            }


        }


    }


}


private fun formatDate(timestamp: Timestamp): String {
    val sdf = SimpleDateFormat("MM-dd-yy hh:mm", Locale.getDefault())
    return sdf.format(timestamp.toDate())
}