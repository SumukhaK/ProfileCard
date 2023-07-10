@file:Suppress("NAME_SHADOWING")

package com.ksa.profilecard

import android.os.Bundle
import android.service.autofill.OnClickAction
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.ksa.profilecard.ui.theme.LighterGreen
import com.ksa.profilecard.ui.theme.ProfileCardTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfileCardTheme {
                UserApplication()
            }
        }
    }

    @Composable
    fun UserApplication() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "user_list"){
            composable("user_list"){
                UsersListScreen(navController)
            }
            composable("user_details/{userId}", arguments = listOf(navArgument("userId"){
                type = NavType.IntType
            })){navBackStackEntry ->
                UserProfileDetailsPage(navBackStackEntry.arguments!!.getInt("userId"),navController)
            }

        }
    }
}

@Composable
fun UsersListScreen(navController: NavHostController) {
    Scaffold(topBar = { AppBar(title = "User List",
        icon = Icons.Default.Home){

    } }) {
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            LazyColumn() {
                items(userProfileList){ userProfile ->
                    ProfileCard(userProfile = userProfile){
                        navController.navigate("user_details/${userProfile.id}")
                    }
                }
            }
        }
    }
}

@Composable
fun AppBar(title : String, icon: ImageVector, iconClickAction: () -> Unit){
    TopAppBar(
        navigationIcon = {Icon(icon,"",
            Modifier.padding(horizontal = 12.dp)
                .clickable(onClick =  {iconClickAction.invoke()})
        )},
        title ={ Text(text = title) })
}

@Composable
fun ProfileCard(userProfile: UserProfile, clickAction:()-> Unit){
    Card(modifier = Modifier
        .padding(top = 2.dp, bottom = 2.dp, start = 8.dp, end = 8.dp)
        .fillMaxWidth()
        .wrapContentHeight(align = Alignment.Top)
        .padding(16.dp)
        .clickable(onClick = {clickAction.invoke()}),
        elevation = 16.dp,
        backgroundColor = Color.White
    ) {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start) {
            ProfilePicture(userProfile.pictureUrl,userProfile.status,72.dp)
            ProfileContent(userProfile.name,userProfile.status, Alignment.Start)
        }
    }
}

@Composable
fun ProfilePicture(drawableURL:String,status:Boolean, imageSize : Dp){
    Card(shape = CircleShape,
        border = BorderStroke(width = 2.dp,color = if(status)
            LighterGreen
        else Color.Red
        ),
        modifier = Modifier.padding(8.dp),
        elevation = 4.dp) {
        /*  Image(painter = painterResource(id = drawableId),
              modifier = Modifier.size(72.dp),
              contentDescription = "Profile Picture",
              contentScale = ContentScale.Crop)*/
        AsyncImage(
            model = drawableURL,
            modifier = Modifier.size(imageSize),
            contentScale = ContentScale.FillBounds,
            contentDescription = "Profile Picture",
        )
    }
}

@Composable
fun ProfileContent(userName:String,onlineStatus:Boolean,alignment: Alignment.Horizontal){
    Column(modifier = Modifier.padding(8.dp),
        horizontalAlignment = alignment) {
        if(!onlineStatus){
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(text = userName, style = MaterialTheme.typography.h5)
            }
        }else{
            Text(text = userName, style = MaterialTheme.typography.h5)
        }

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {

            Text(text = if(onlineStatus)"Active Now" else "Offline", style = MaterialTheme.typography.body2)

        }
    }
}


@Composable
fun UserProfileDetailsPage(userId:Int,navController: NavHostController) {
    val userProfile = userProfileList.first{userProfile ->
        userId == userProfile.id
    }
    Scaffold(topBar = { AppBar(title = "User Details",
        icon = Icons.Default.ArrowBack){
        navController.navigateUp()
    } }) {
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {

            Column(modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top) {

                ProfilePicture(drawableURL = userProfile.pictureUrl, status =userProfile.status,240.dp)
                ProfileContent(userName = userProfile.name, onlineStatus = userProfile.status,Alignment.CenterHorizontally)
            }
        }
    }
}