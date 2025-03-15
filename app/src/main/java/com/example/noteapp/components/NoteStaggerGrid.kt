import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.noteapp.Screen
import com.example.noteapp.model.Note
import com.example.noteapp.ui.theme.descOfCard
import com.example.noteapp.ui.theme.titleOfCard
import com.example.noteapp.viewmodels.NoteScreenViewModel
import kotlin.math.absoluteValue

@Composable
fun NoteStaggerGrid(
    modifier: Modifier = Modifier, noteViewModel: NoteScreenViewModel = hiltViewModel(),
    navController: NavController?,notes:List<Note>
) {


    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = modifier.fillMaxSize().padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalItemSpacing = 8.dp
    ) {
        items(notes) { note ->
            StaggeredGridItem(note,noteViewModel,navController)
        }
    }
}

@Composable
fun StaggeredGridItem(note: Note, noteViewModel: NoteScreenViewModel, navController: NavController?) {
    val backgroundColors = listOf(
        Color(0xFFFFCDD2), // Light Red
        Color(0xFFC8E6C9), // Light Green
        Color(0xFFBBDEFB), // Light Blue
        Color(0xFFFFF9C4), // Light Yellow
        Color(0xFFD1C4E9)  // Light Purple
    )

    val stableColor = remember(note.id) {
        val hash = (note.id ?: note.noteTitle.hashCode()).absoluteValue
        backgroundColors[hash % backgroundColors.size]
    }

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = stableColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),

        modifier = Modifier
            .wrapContentWidth()
            .padding(4.dp)
            .clickable{

                navController?.navigate(
                    "${Screen.NoteScreen.route}/${note.id}"
                )
            }
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = note.noteTitle,
                style = titleOfCard
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = note.noteDescription,
                style = descOfCard
            )
        }
    }
}
