package com.ksa.profilecard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ksa.profilecard.ui.theme.LighterGreen
import com.ksa.profilecard.ui.theme.ProfileCardTheme
import com.ksa.profilecard.ui.theme.lightGreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfileCardTheme {
                MainScreen()
            }
        }
    }
}


@Composable
fun MainScreen() {
    Scaffold(topBar = { AppBar() }) {
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            ProfileCard()
        }
    }
}

@Composable
fun AppBar(){
    TopAppBar(navigationIcon = {Icon(Icons.Default.Home,"",
        Modifier.padding(horizontal = 12.dp))},
        title ={ Text(text = "Messaging Application") })
}

@Composable
fun ProfileCard(){
    Card(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight(align = Alignment.Top)
        .padding(16.dp),
        elevation = 16.dp,
        backgroundColor = Color.White
    ) {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start) {
            ProfilePicture()
            ProfileContent()
        }
    }
}

@Composable
fun ProfilePicture(){
    Card(shape = CircleShape,
        border = BorderStroke(width = 2.dp,color = LighterGreen),
        modifier = Modifier.padding(8.dp),
        elevation = 4.dp) {
        Image(painter = painterResource(id = R.drawable.profile2),
            modifier = Modifier.size(72.dp),
            contentDescription = "Profile Picture",
            contentScale = ContentScale.Crop)
    }

}

@Composable
fun ProfileContent(){
    Column(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()) {

        Text(text = "John Doe", style = MaterialTheme.typography.h5)

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(text = "Active Now", style = MaterialTheme.typography.body2)
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ProfileCardTheme {
        MainScreen()
    }
}