package empresa.tipodaapp.calculadorahome

sealed  class Calculo{
    data class Number(val number: Int): Calculo()
    object Clear: Calculo()
    object Delete: Calculo()
    object Deciaml: Calculo()
    object Calculate: Calculo()
    data class Operation(val operation: Operacao): Calculo()

}