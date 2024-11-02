package empresa.tipodaapp.calculadorahome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Calculadora(
    state: EstadoCalculadora,
    espacamentoBotao: Dp = 8.dp,
    onAction: (Calculo) -> Unit,
    modifier: Modifier = Modifier

){
    Box(modifier = modifier){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.spacedBy(espacamentoBotao)
        ) {

            Text(
                text = state.primeiroNumero + (state.operacao?.symbol ?: "") + state.segundoNumero,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                    fontWeight = FontWeight.Light,
                    fontSize = 80.sp,
                    color = Color.White,
                    lineHeight = 100.sp,
                    maxLines = 2
            )
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(espacamentoBotao)
            ){
                BotaoCalculadora(
                    symbol = "AC",
                    modifier = Modifier
                        .background(LightGray)
                        .aspectRatio(2f)
                        .weight(2f),
                    onClick = {
                        onAction(Calculo.Clear)
                    }
                )
                BotaoCalculadora(
                    symbol = "Del",
                    modifier = Modifier
                        .background(LightGray)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(Calculo.Delete)
                    }
                )

                BotaoCalculadora(
                    symbol = "/",
                    modifier = Modifier
                        .background(Orange)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(Calculo.Operation(Operacao.div))
                    }
                )


            }

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(espacamentoBotao)
            ){
                BotaoCalculadora(
                    symbol = "7",
                    modifier = Modifier
                        .background(LightGray)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(Calculo.Number(7))
                    }
                )
                BotaoCalculadora(
                    symbol = "8",
                    modifier = Modifier
                        .background(LightGray)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(Calculo.Number(8))
                    }
                )

                BotaoCalculadora(
                    symbol = "9",
                    modifier = Modifier
                        .background(LightGray)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(Calculo.Number(9))
                    }
                )
                BotaoCalculadora(
                    symbol = "*",
                    modifier = Modifier
                        .background(Orange)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(Calculo.Operation(operation = Operacao.Mul))
                    }
                )


            }

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(espacamentoBotao)
            ){
                BotaoCalculadora(
                    symbol = "4",
                    modifier = Modifier
                        .background(LightGray)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(Calculo.Number(4))
                    }
                )
                BotaoCalculadora(
                    symbol = "5",
                    modifier = Modifier
                        .background(LightGray)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(Calculo.Number(5))
                    }
                )

                BotaoCalculadora(
                    symbol = "6",
                    modifier = Modifier
                        .background(LightGray)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(Calculo.Number(6))
                    }
                )
                BotaoCalculadora(
                    symbol = "-",
                    modifier = Modifier
                        .background(Orange)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(Calculo.Operation(operation = Operacao.Sub))
                    }
                )


            }

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(espacamentoBotao)
            ){
                BotaoCalculadora(
                    symbol = "1",
                    modifier = Modifier
                        .background(LightGray)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(Calculo.Number(1))
                    }
                )
                BotaoCalculadora(
                    symbol = "2",
                    modifier = Modifier
                        .background(LightGray)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(Calculo.Number(2))
                    }
                )

                BotaoCalculadora(
                    symbol = "3",
                    modifier = Modifier
                        .background(LightGray)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(Calculo.Number(3))
                    }
                )
                BotaoCalculadora(
                    symbol = "+",
                    modifier = Modifier
                        .background(Orange)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(Calculo.Operation(operation = Operacao.Add))
                    }
                )


            }
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(espacamentoBotao)
            ){
                BotaoCalculadora(
                    symbol = "0",
                    modifier = Modifier
                        .background(LightGray)
                        .aspectRatio(2f)
                        .weight(2f),
                    onClick = {
                        onAction(Calculo.Number(0))
                    }
                )
                BotaoCalculadora(
                    symbol = ".",
                    modifier = Modifier
                        .background(LightGray)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(Calculo.Deciaml)
                    }
                )

                BotaoCalculadora(
                    symbol = "=",
                    modifier = Modifier
                        .background(Orange)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(Calculo.Calculate)
                    }
                )



            }

        }
    }
}