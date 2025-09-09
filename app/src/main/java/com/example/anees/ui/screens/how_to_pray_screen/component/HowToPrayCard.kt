package com.example.anees.ui.screens.how_to_pray_screen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anees.R

@Preview(showBackground = true)
@Composable
fun HowToPrayCard(modifier: Modifier = Modifier) {

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    Card(
        modifier = modifier
            .fillMaxSize()
            .border(2.dp, Color(0xEB803F0B), RoundedCornerShape(32.dp)),
        shape = RoundedCornerShape(32.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .height((screenHeight * 0.5f))
                .fillMaxWidth()
        ) {

            Image(
                painter = painterResource(id = R.drawable.p_07),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier.size(50.dp)
                    .align(Alignment.TopStart).padding(start = 8.dp, top = 8.dp)
                    .background(color = Color(0xEBE7D3BB).copy(alpha = 0.4f), shape = CircleShape),
                contentAlignment = Alignment.Center
            ){
                Text("1", color = Color.Black, style = TextStyle(fontSize = 24.sp), fontWeight = FontWeight.Bold)
            }
            Text(
                "السجود الثاني",
                color = Color.Black,
                style = TextStyle(fontSize = 28.sp, fontFamily = FontFamily(Font(R.font.amiri))),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
            IconButton(
                onClick = { },
                modifier = Modifier.align(Alignment.CenterStart).padding(horizontal = 4.dp).padding(top = 16.dp).background(color = Color(0xEBE7D3BB).copy(alpha = 0.4f), shape = RoundedCornerShape(50))
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier.size(48.dp)
                )
            }
            IconButton(
                onClick = { },
                modifier = Modifier.align(Alignment.CenterEnd).padding(horizontal = 4.dp).padding(top = 16.dp).background(color = Color(0xEBE7D3BB).copy(alpha = 0.4f), shape = RoundedCornerShape(50))
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier.size(48.dp)
                )
            }
        }

            Text(
                "ثم كان-صلى الله عليه وسلم- يجلس للتشهد بعد الفراغ من الر كعة الثانية ، فإذا كانت الصلاة ركعتين كالصبح جلس مفترشا كما كان يجلس بين السجدتين وكذلك «يجلس في التشهد الأول»(.) من الثلاثية أو الرباعية  \n" +
                        "فإذا جلست في وسط الصلاة فاطمئن ، وافترش فخذك اليسرى ثم تشهد\n" +
                        "و كان إذا قعد في التشهد؛ وضع كفه اليمنى عل فخذه ( وفي رواية\n" +
                        "ركبته ) اليمنى ، ووضع كفه اليسرى على فخذه ( وفي رواية: ركبته ) اليسرى باسطها عليها \n" +
                        "و كان يضع حد مرفقه الأيمن عل فخذه اليمنى\n" +
                        " ونهى رجلا وهو جالس معتمد عل يده اليسرى في الصلاة فقال( إنها صلاة اليهود)\n" +
                        "وكان يبسط كفه اليسرى عل ركبته اليسرى ، ويقبض أصابع كفه اليمنى كلها ، ويشير إصبعه التي تلي الابهام إلى القبلة، ويرمي ببصره إليها\n" +
                        "و كان إذا أشار بإصبعه وضع إبهامه عل إصبعه الوسطى وتارة كان يحلق بهما حلقة \n" +
                        "و كان يرفع إصبعه يحركها يدعو بها \n" +
                        "\n" +
                        "صيغة التشهد: {التَّحِيَّاتُ الطَّيِّبَاتُ الصَّلَوَاتُ لِلَّهِ السَّلَامُ عَلَيْكَ أيُّها النبيُّ ورَحْمَةُ اللهِ وبَرَكَاتُهُ، السَّلَامُ عَلَيْنَا وعلَى عِبَادِ اللهِ الصَّالِحِينَ، أشْهَدُ أنْ لا إلَهَ إلَّا اللَّهُ وأَشْهَدُ أنَّ مُحَمَّدًا عَبْدُهُ ورَسولُهُ}",

                color = Color.Black,
                modifier= Modifier
                    .background(Color(0xEBE7D3BB))
                    .padding(horizontal = 16.dp)
                    .padding(top = 4.dp, bottom = 12.dp)
                    .verticalScroll(rememberScrollState()),
                textAlign = TextAlign.Right,
                fontWeight = FontWeight.Normal,
                style = TextStyle(fontSize = 20.sp, fontFamily = FontFamily(Font(R.font.othmani)))
            )
        }
    }
}