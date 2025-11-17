package com.example.roomdatabasedemo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

// Opt-in to use experimental Material 3 API features, like the new Scaffold.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(viewModel: NoteViewModel) {
    // Collect the list of notes from the ViewModel as a State, so UI recomposes on changes.
    val notes by viewModel.notes.collectAsState()
    // State for the title input field.
    var title by remember { mutableStateOf("") }
    // State for the content input field.
    var content by remember { mutableStateOf("") }
    // State to hold the note currently being edited, if any.
    var editingNote by remember { mutableStateOf<Note?>(null) }
    // State to control the visibility of the delete confirmation dialog.
    var showDeleteDialog by remember { mutableStateOf<Note?>(null) }
    // Formatter for displaying dates in a consistent format.
    val dateFormat = remember { SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()) }
    // State for managing SnackBar messages.
    val snackbarHostState = remember { SnackbarHostState() }
    // Coroutine scope for launching suspending functions, like showing snackbars.
    val scope = rememberCoroutineScope()

    val DarkColors = darkColorScheme(
        primary = (Color(0xFFBB86FC)),
        onPrimary = Black,
        primaryContainer = (Color(0xFF3700B3)),
        onPrimaryContainer = White,
        secondary = (Color(0xFF03DAC6)),
        onSecondary = Black,
        background = (Color(0xFF121212)),
        onBackground = White,
        surface = (Color(0xFF1E1E1E)),
        onSurface = White,
        error = (Color(0xFFCF6679)),
        onError = Black,
    )

    val LightColors = lightColorScheme(
        primary = (Color(0xFF6200EE)),
        onPrimary = White,
        primaryContainer = (Color(0xFFBB86FC)),
        onPrimaryContainer = Black,
        secondary = (Color(0xFF03DAC6)),
        onSecondary = Black,
        background = (Color(0xFFF0F0F0)),
        onBackground = Black,
        surface = White,
        onSurface = Black,
        error = (Color(0xFFB00020)),
        onError = White
    )

    // Scaffold provides a basic screen layout with a SnackBarHost.
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        // Main column for arranging input fields, buttons, and the note list.
        Column(modifier = Modifier
            .padding(16.dp)
            .padding(top = 50.dp)) {
            // Title for the note input section.
            Text("Add a Note", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            // Outlined text field for note title input.
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Outlined text field for note content input.
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Content") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Row for action buttons (Add/Update Note, Cancel Edit).
            Row {
                // Button to add a new note or update an existing one.
                Button(onClick = {
                    if (title.isNotBlank() && content.isNotBlank()) {
                        if (editingNote == null) {
                            // If not editing, add a new note.
                            viewModel.addNote(title, content, dateFormat.format(Date()))
                            scope.launch { snackbarHostState.showSnackbar("Note added!") }
                        } else {
                            // If editing,
                            viewModel.deleteNote(editingNote!!)
                            viewModel.addNote(title, content, dateFormat.format(Date()))
                            editingNote = null
                            scope.launch { snackbarHostState.showSnackbar("Note updated!") }
                        }
                        // Clear input fields after action.
                        title = ""
                        content = ""
                    }
                }) {
                    // Button text changes based on whether a note is being edited.
                    Text(if (editingNote == null) "Add Note" else "Update Note",
                        color = MaterialTheme.colorScheme.onSurface)
                }
                // Show "Cancel Edit" button only when a note is being edited.
                if (editingNote != null) {
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedButton(onClick = {
                        // Clear editing state and input fields on cancel.
                        editingNote = null
                        title = ""
                        content = ""
                    }) {
                        Text("Cancel Edit")
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            // Title for the list of notes.
            Text("Your Notes", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onBackground)
            Spacer(modifier = Modifier.height(8.dp))
            // LazyColumn to efficiently display a scrollable list of notes.
            LazyColumn(modifier = Modifier.fillMaxHeight()) {
                items(notes.size) { idx ->
                    val note = notes[idx]
                    // Surface for individual note display, with a slight elevation.
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        tonalElevation = 2.dp
                    ) {
                        // Row to arrange note details and action buttons horizontally.
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Column for displaying note title, content, and date.
                            Column(modifier = Modifier.weight(1f)) {
                                Text(note.title, style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurface)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(note.content, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(note.date, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                            // Column for Edit and Delete buttons.
                            Column {
                                // Button to initiate editing of a note.
                                Button(onClick = {
                                    editingNote = note
                                    title = note.title
                                    content = note.content
                                }) {
                                    Text("Edit", color = MaterialTheme.colorScheme.onSurface)
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                // Button to show delete confirmation dialog.
                                Button(onClick = { showDeleteDialog = note }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)) {
                                    Text("Delete", color = MaterialTheme.colorScheme.error)
                                }
                            }
                        }
                    }
                }
            }
        }
        // Delete confirmation AlertDialog.
        if (showDeleteDialog != null) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = null },
                title = { Text("Delete Note") },
                text = { Text("Are you sure you want to delete this note?") },
                confirmButton = {
                    TextButton(onClick = {
                        // Delete the note, show snackbar, and reset states.
                        viewModel.deleteNote(showDeleteDialog!!)
                        scope.launch { snackbarHostState.showSnackbar("Note deleted!") }
                        showDeleteDialog = null
                        // If the deleted note was being edited, clear editing state.
                        if (editingNote == showDeleteDialog) {
                            editingNote = null
                            title = ""
                            content = ""
                        }
                    }) {
                        Text("Delete")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteDialog = null }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}