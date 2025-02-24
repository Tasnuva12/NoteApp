package com.example.noteapp.screen

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.noteapp.R
import com.example.noteapp.Screen
import com.example.noteapp.ui.theme.noteTextStyle
import com.example.noteapp.ui.theme.titleTextStyle
import com.example.noteapp.viewmodels.NoteScreenViewModel

@Composable

fun NoteScreen(
    navController: NavController? = null, modifier: Modifier = Modifier,
    noteViewModel: NoteScreenViewModel = hiltViewModel(),
    noteId: String? = "",
    noteTitle: String? = "",
    noteDescription: String? = ""
) {

    val context=LocalContext.current



    // Observe LiveData for title and description, and get the value from State
    val title = noteViewModel.title.collectAsState().value
    val desc = noteViewModel.desc.collectAsState().value

    LaunchedEffect(noteId) {
        if (noteId != null && noteTitle != null && noteDescription != null) {
            noteViewModel.setTitle(noteTitle?:"")
            noteViewModel.setDescription(noteDescription?:"")
        }
    }




    Column(modifier = modifier.padding(start = 16.dp, top = 50.dp)){
        Row(modifier=Modifier.height(40.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.check),
                contentDescription = "Check Icon which saves the note and navigates to the homescreen",
                modifier=Modifier.clickable{

                     noteViewModel.insertAndupdateToRoomDatabase(navController)
                    navController?.navigate(Screen.HomeScreen.route)
                }

                )
            Spacer(modifier = Modifier.padding(75.dp))
            Icon(
                painter = painterResource(id = R.drawable.clipboardtext),
                contentDescription = "Your Image Description",

            )
//            Spacer(modifier = Modifier.padding(10.dp))
//            Icon(
//                painter = painterResource(id = R.drawable.heart),
//                contentDescription = "Your Image Description",
//
//            )
            Spacer(modifier = Modifier.padding(10.dp))
            Icon(
                painter = painterResource(id = R.drawable.directboxsend),
                contentDescription = "Your Image Description",
                modifier=Modifier.clickable{
                    val shareIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, desc)
                    }

                    context.startActivity(Intent.createChooser(shareIntent, "Share via"))


                }

            )



        }


        Spacer(modifier = Modifier.padding(10.dp))
        Column(modifier=Modifier.verticalScroll(rememberScrollState()).padding(start=16.dp,end=16.dp)) {

       BasicTextField(
           value=title ,
           onValueChange = {noteViewModel.setTitle(it)},
           textStyle = titleTextStyle,

           modifier = Modifier.fillMaxWidth(),
           decorationBox = {innerTextField->
               Box( modifier = Modifier.fillMaxWidth()){
                 if (title.isEmpty()){
                     Text(
                         text="Title",
                         fontSize = 40.sp,

                         color = colorResource(id = R.color.gray)
                     )
                 }
                   innerTextField()
               }

           }



       )
            Spacer(modifier = Modifier.padding(10.dp))
            BasicTextField(
                value=desc ,
                onValueChange = {noteViewModel.setDescription(it)},
                textStyle = noteTextStyle,

                modifier = Modifier.fillMaxWidth(),
                decorationBox = {innerTextField->
                    Box( modifier = Modifier.fillMaxWidth()){
                        if (desc.isEmpty()){
                            Text(
                                text="Write something....",
                                fontSize = 14.sp,

                                color = colorResource(id = R.color.gray)
                            )
                        }
                        innerTextField()
                    }

                }



            )


        }

    }



}