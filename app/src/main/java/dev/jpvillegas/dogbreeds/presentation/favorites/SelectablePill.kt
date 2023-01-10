package dev.jpvillegas.dogbreeds.presentation.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.jpvillegas.dogbreeds.presentation.ui.theme.DogBreedsTheme

@Composable
fun SelectablePill(
    content: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    val backgroundColor = if (isSelected) {
        MaterialTheme.colors.secondary.copy(alpha = .5f)
    } else {
        MaterialTheme.colors.surface.copy(alpha = .8f)
    }

    Box(
        modifier = modifier
            .clickable { onClick?.invoke() }
            .background(backgroundColor, shape = RoundedCornerShape(20.dp))
            .border(1.dp, MaterialTheme.colors.primary, RoundedCornerShape(20.dp))
            .padding(vertical = 4.dp, horizontal = 12.dp)
    ) {
        Row(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(
                text = content,
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "",
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .size(18.dp)
                        .padding(start = 4.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun SelectablePillPreview() {
    DogBreedsTheme {
        SelectablePill(content = "Akita", isSelected = true, modifier = Modifier)
    }
}

@Preview
@Composable
fun SelectablePillPreview2() {
    DogBreedsTheme {
        SelectablePill(content = "Akita", isSelected = false, modifier = Modifier)
    }
}