package ipca.examples.calculator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ipca.examples.calculator.ui.components.CalcButton
import ipca.examples.calculator.ui.theme.CalculatorTheme
import ipca.examples.calculator.ui.theme.Orange
import ipca.examples.calculator.ui.theme.Pink40

@Composable
fun CalculatorView(modifier: Modifier = Modifier ){

    var display by remember { mutableStateOf("0") }

    var calculatorBrain = remember { CalculatorBrain() }

    fun getDisplay() : Double {
        return display.toDouble()
    }

    fun setDisplay(value: Double) {
        if ( value % 1 == 0.0) {
            display = value.toInt().toString()
        } else {
            display = value.toString()
        }
    }

    var userIsInTheMiddleOfTyping by remember {
        mutableStateOf(false)
    }

    val onNumPress : (String) -> Unit = { num ->
        if(userIsInTheMiddleOfTyping) {
            if (display == "0") {
                if (num == ".") {
                    display = "0."
                } else {
                    display = num
                }
            } else {
                if (num == ".") {
                    if (!display.contains(".")) {
                        display += num
                    }
                } else {
                    display += num
                }
            }
        }else{
            display = num
        }

        userIsInTheMiddleOfTyping = true
    }

    val onOperationPressed : (String) -> Unit = { op ->
        userIsInTheMiddleOfTyping = false
        setDisplay( calculatorBrain.doOperation(getDisplay()))
        calculatorBrain.operand = getDisplay()
        calculatorBrain.operation = CalculatorBrain.Operation.getOp(op)
    }

    val onBackspacePress: () -> Unit = {
        display = calculatorBrain.backspace(display)
        userIsInTheMiddleOfTyping = display != "0"
    }

    val onClearPress: () -> Unit = {
        display = calculatorBrain.clear().toString()
        userIsInTheMiddleOfTyping = false
    }

    Column (modifier = modifier){
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = display,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.displayLarge)
        Row {
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "C",
                isOperation = true,
                onButtonPress = { onClearPress() }
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "⌫",
                isOperation = true,
                onButtonPress = { onBackspacePress() }
            )
        }
        Row {
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "√",
                isOperation = true,
                onButtonPress = onOperationPressed
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "±",
                isOperation = true,
                onButtonPress = onOperationPressed
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "%",
                isOperation = true,
                onButtonPress = onOperationPressed
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "Rand",
                isOperation = true,
                onButtonPress = onOperationPressed
            )
        }
        Row{
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "7",
                onButtonPress = onNumPress
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "8",
                onButtonPress = onNumPress
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "9",
                onButtonPress = onNumPress
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "+",
                isOperation = true,
                onButtonPress = onOperationPressed
            )

        }
        Row{
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "4",
                onButtonPress = onNumPress
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "5",
                onButtonPress = onNumPress
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "6",
                onButtonPress = onNumPress
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "-",
                isOperation = true,
                onButtonPress = onOperationPressed
            )

        }
        Row{
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "1",
                onButtonPress = onNumPress
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "2",
                onButtonPress = onNumPress
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "3",
                onButtonPress = onNumPress
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "*",
                isOperation = true,
                onButtonPress = onOperationPressed
            )

        }
        Row{
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "0",
                onButtonPress = onNumPress
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = ".",
                onButtonPress = onNumPress
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "=",
                isOperation = true,
                onButtonPress = onOperationPressed
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "/",
                isOperation = true,
                onButtonPress = onOperationPressed
            )

        }
    }

}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview(){
    CalculatorTheme {
        CalculatorView()
    }

}