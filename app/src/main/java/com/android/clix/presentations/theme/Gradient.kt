package com.android.clix.presentations.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val linearGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF161616),
        Color(0xFF212121)
    ),
)

val blackLinearGradient = Brush.verticalGradient(
    colors = listOf(
        Color.Black.copy(alpha = 0.5f),
        Color.Black.copy(alpha = 1f)
    ),
)

val clixMixed = Brush.verticalGradient(
    colors = listOf(
        Color(0xFFF11E22),
        Color(0xFF912A89)
    ),
)

val radialGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFFF11E22),
        Color(0xFF912A89)
    ),
)


val radialGradientDisable = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF525050),
        Color(0xFF525050)
    ),
)

val darkGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF101010),
        Color(0xFF1C1C1C)
    ),
)

val nextBackButton = Brush.radialGradient(
    colors = listOf(
        Color(0xFFFD5F5D),
        Color(0xFFEF221F),
        Color(0xFF8D2A84)
    ),
)

val horizontalGradient = Brush.horizontalGradient(
    colors = listOf(
        Color.Black.copy(alpha = 0.8f),
        Color.Black.copy(alpha = 0.2f),
        Color.White.copy(alpha = 0.3f),
        Color.Black.copy(alpha = 0.2f),
        Color.Black.copy(alpha = 0.8f)
    ),
)

val greenGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF36D44F),
        Color(0xFF187B28)
    ),
)