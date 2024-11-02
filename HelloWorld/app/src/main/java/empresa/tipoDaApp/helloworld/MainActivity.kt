package empresa.tipoDaApp.helloworld

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import empresa.tipoDaApp.helloworld.ui.theme.HelloWorldTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HelloWorldTheme {
                var name by remember {mutableStateOf("")}
                var nomes by remember {
                    mutableStateOf(listOf<String>())
                }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp)
                            .padding(top = 10.dp),
                    ) {
                        Row (
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .weight(1f),
//                            horizontalArrangement = Arrangement.Center,
//                            verticalAlignment = Alignment.CenterVertically
                        ){
                            OutlinedTextField(
                                value = name,
                                onValueChange = {text -> name = text},
                                label = { Text("") },
                                modifier = Modifier.weight(1f)
                            )

                            Spacer(modifier = Modifier.width(16.dp))
                            Button(onClick = {
                                if (name.isNotBlank()) {
                                    nomes = nomes + name
                                }
//                                name = ""
                            },
                                modifier = Modifier.padding(start = 10.dp)
                            ) {
                                Text("Add")
                            }
                        }
                        LazyColumn {
                            items(nomes){
                                nome ->
                                Text(
                                    text = nome,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(16.dp),
                                )
                                HorizontalDivider()
                            }
                        }
                    }

                }
            }
        }
    }


