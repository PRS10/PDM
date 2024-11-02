package empresa.tipodaapp.calculadorahome

sealed class Operacao (val symbol: String) {
    object Add: Operacao("+")
    object Sub: Operacao("-")
    object div: Operacao("/")
    object Mul: Operacao("x")

}