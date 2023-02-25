package com.example.doctorworkers.ui.features.doctorProfile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import com.example.doctorworkers.R
import com.example.doctorworkers.ui.theme.Blue700
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun Avatar(urlAvatar: String, uploadAvatar: () -> Unit) {
    Card(
        Modifier
            .fillMaxWidth()
            .height(300.dp),
        backgroundColor = Blue700,
        elevation = 16.dp,
        shape = RoundedCornerShape(bottomEnd = 24.dp, bottomStart = 24.dp)
    ) {
        Box(contentAlignment = Alignment.BottomEnd) {
            IconButton(onClick = { /*TODO*/ }, Modifier.padding(8.dp)) {
                Icon(
                    modifier = Modifier
                        .size(100.dp),
                    painter = painterResource(id = R.drawable.ic_upload),
                    contentDescription = "set avatar"
                )
            }

            if (urlAvatar != "") {
                GlideImage(imageModel = urlAvatar, modifier = Modifier.fillMaxSize())
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_doctor),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}