package com.example.doctorworkers.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomActionSheetWithContent(
    sheetContent: @Composable (ModalBottomSheetState, CoroutineScope) -> Unit,
    content: @Composable (ModalBottomSheetState, CoroutineScope) -> Unit
) {
    val state = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetBackgroundColor = Color.White,
        sheetElevation = 12.dp,
        sheetShape = RoundedCornerShape(topEnd = 32.dp),
        sheetState = state,
        sheetContent = { sheetContent(state, scope) }
    ) {
        content(state, scope)
    }
}