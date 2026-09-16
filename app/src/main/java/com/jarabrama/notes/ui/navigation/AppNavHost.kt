package com.jarabrama.notes.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jarabrama.notes.ui.detail.NoteDetailViewModel
import com.jarabrama.notes.ui.detail.NoteEditScreen
import com.jarabrama.notes.ui.notes.NoteListScreen
import com.jarabrama.notes.ui.notes.NotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost() {
  val navController = rememberNavController()
  val navBackStackEntry by navController.currentBackStackEntryAsState()
  val currentRoute = navBackStackEntry?.destination?.route

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Mis Notas") },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = MaterialTheme.colorScheme.primaryContainer,
          titleContentColor = MaterialTheme.colorScheme.primary,
        )
      )
    }
  ) { innerPadding ->
    NavHost(
      navController = navController,
      startDestination = Screen.NoteList.route,
      modifier = Modifier.padding(innerPadding)
    ) {
      composable(route = Screen.NoteList.route) {
        val viewModel: NotesViewModel = hiltViewModel()
        NoteListScreen(
          viewModel = viewModel,
          onAddNoteClick = {
            navController.navigate(Screen.NoteEdit.passNoteId())
          },
          onNoteClick = { noteId ->
            navController.navigate(Screen.NoteEdit.passNoteId(noteId))
          }
        )
      }
      composable(
        route = Screen.NoteEdit.route,
        arguments = listOf(
          navArgument("noteId") {
            type = NavType.LongType
            defaultValue = -1L
          }
        )
      ) {
        val viewModel: NoteDetailViewModel = hiltViewModel()
        NoteEditScreen(
          viewModel = viewModel,
          onNavigateBack = {
            navController.popBackStack()
          }
        )
      }
    }
  }
}
