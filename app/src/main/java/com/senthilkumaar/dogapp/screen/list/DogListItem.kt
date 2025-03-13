package com.senthilkumaar.dogapp.screen.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.senthilkumaar.dogapp.model.Dog
import com.senthilkumaar.dogapp.theme.spacing_m
import com.senthilkumaar.dogapp.theme.spacing_s

@Composable
fun DogListItem(
    dog: Dog,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing_s)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(spacing_m)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(dog.imageUrl),
                contentDescription = "Dog Image",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(spacing_m))

            Column {
                Text(
                    text = dog.name,
                    style = MaterialTheme.typography.h6
                )
                Text(
                    text = dog.temperament ?: "Unknown",
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDogListItem() {
    DogListItem(
        dog = Dog(
            id = 1,
            name = "Labrador Retriever",
            temperament = "Friendly, Outgoing",
            life_span = "10-12 years",
            origin = "Canada",
            reference_image_id = "B1uW7l5VX"
        ),
        onClick = {}
    )
}