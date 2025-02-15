package ipca.examples.calculator.ui.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ipca.examples.calculator.ui.theme.CalculatorTheme
import ipca.examples.calculator.ui.theme.Orange
import ipca.examples.calculator.ui.theme.Pink40

@Composable
fun CalcButton(modifier: Modifier = Modifier,
               label : String = "",
               isOperation : Boolean = false,
               onButtonPress : (String) -> Unit ) {
    Button(
        modifier = modifier
            .aspectRatio(if(label == "C" || label == "⌫") 2f else 1f)
            .padding(2.dp),
        colors = ButtonDefaults.run { buttonColors( if (isOperation) Orange else  Pink40) },
        onClick = { onButtonPress(label) }
    ) {
        Text(text = label,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CalcButtonPreview(){
    CalculatorTheme {
        CalcButton(
            label = "0"
        ){ }
    }
}