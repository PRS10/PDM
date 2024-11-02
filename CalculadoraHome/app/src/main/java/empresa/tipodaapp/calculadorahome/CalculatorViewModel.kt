package empresa.tipodaapp.calculadorahome

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CalculatorViewModel: ViewModel() {
    var state by mutableStateOf(EstadoCalculadora())
        private set

    fun onAction(action: Calculo){
        when(action){
            is Calculo.Number -> enterNumber(action.number)
            is Calculo.Deciaml -> enterDecimal()
            is Calculo.Clear -> state = EstadoCalculadora()
            is Calculo.Operation -> enterOperation(action.operation)
            is Calculo.Calculate -> performCalculation()
            is Calculo.Delete -> performDeletion()
        }

    }

    private fun performDeletion() {
        when{
            state.segundoNumero.isNotBlank() -> state = state.copy(
                segundoNumero = state.segundoNumero.dropLast(1)
            )
            state.operacao != null -> state = state.copy(
                operacao = null
            )
            state.primeiroNumero.isNotBlank() -> state = state.copy(
                primeiroNumero = state.primeiroNumero.dropLast(1)
            )
        }
    }

    private fun performCalculation() {
        val primeiroNumero = state.primeiroNumero.toDoubleOrNull()
        val segundoNumero = state.segundoNumero.toDoubleOrNull()
        if(primeiroNumero != null && segundoNumero != null){

            val resultado = when(state.operacao) {
                is Operacao.Add -> primeiroNumero + segundoNumero
                is Operacao.Sub -> primeiroNumero - segundoNumero
                is Operacao.Mul -> primeiroNumero * segundoNumero
                is Operacao.div -> primeiroNumero / segundoNumero
                null -> return
            }
            state = state.copy(
                primeiroNumero = resultado.toString().take(15),
                segundoNumero = "",
                operacao = null
            )
        }

    }

    private fun enterOperation(operation: Operacao) {
        if(state.primeiroNumero.isNotBlank()) {
            state = state.copy(operacao = operation)
        }
    }

    private fun enterDecimal() {
        if(state.operacao == null && !state.primeiroNumero.contains(".")
            && state.primeiroNumero.isNotBlank()){
            state = state.copy(
                primeiroNumero = state.primeiroNumero + "."
            )
            return
        }

        if(!state.segundoNumero.contains(".") && state.segundoNumero.isNotBlank())
        {
            state = state.copy(
                segundoNumero = state.segundoNumero + "."
            )
        }


    }

    private fun enterNumber(number: Int) {
        if(state.operacao == null) {
            if (state.primeiroNumero.length >= MAX_NUM_LENGTH) {
                return
            }
            state = state.copy(
                primeiroNumero = state.primeiroNumero + number
            )
        }else {

            if (state.segundoNumero.length >= MAX_NUM_LENGTH) {
                return
            }

            state = state.copy(
                segundoNumero = state.segundoNumero + number
            )
        }
    }



    companion object {
        private const val MAX_NUM_LENGTH = 8
    }

}