package ipca.examples.calculator

class CalculatorBrain {

    enum class Operation (val op: String) {
        SUM("+"),
        SUB("-"),
        MULT("*"),
        DIV("/"),
        SQRT("√"),
        SIGNAL("±"),
        PERCENT("%"),
        RAND("\uD83D\uDE02");

        companion object {
            fun getOp(value: String): Operation {
                return entries.find { it.op == value } ?: RAND
            }
        }
    }

    var operation : Operation? = null
    var operand : Double = 0.0

    fun doOperation(value: Double) : Double {
        val result = when (operation) {
            Operation.SUM -> operand + value
            Operation.SUB -> operand - value
            Operation.MULT -> operand * value
            Operation.DIV -> operand / value
            Operation.SQRT -> Math.sqrt(operand)
            Operation.SIGNAL -> -operand
            Operation.PERCENT -> operand / 100
            Operation.RAND -> Math.random()
            null -> value
        }
        return result
    }

    fun backspace(currentDisplay: String): String {
        return if (currentDisplay.length > 1) {
            currentDisplay.dropLast(1)
        } else {
            "0"
        }
    }

    fun clear(): Double {
        operand = 0.0
        operation = null
        return 0.0
    }
}