package com.example.bookexamplesapp

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.bookexamplesapp.Contact

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ContactListApp()
                }
            }
        }
    }
}





@Composable
fun ContactListApp() {
    val contacts = listOf(
        Contact("Mary Ann", "maryann@gmail.com", "753-346-2246"),
        Contact("Jerry Marise", "jmarise@gmail.com", "684-245-2113"),
        Contact("Todd Kong", "Toddkong@gmail.com", "754-345-8765"),
        Contact("Jason Markus", "jmark@gmail.com", "643-226-4564"),
        Contact("Octavian Link", "Octolink@gmail.com", "754-345-7643"),
        Contact("Quinton Henry", "Henryquin@gmail.com", "643-235-6549"),
        Contact("Larking Meyer", "lmeyer@gmail.com", "754-334-9872"),
        Contact("Frogger Kamaboko", "Froggerbigboko@gmail.com", "342-654-8753"),
        Contact("Klay Hamlin", "KlayHam@gmail.com", "644-234-7890"),
        Contact("Terrion Saracen", "TerrySars@gmail.com", "446-687-1245"),
        Contact("Cam Jugg", "CJugg@gmail.com", "436-568-2345"),
        Contact("Henry Jackal", "HenryJack@gmail.com", "234-655-1235"),
        Contact("Cooper Clarity", "CoopClar@gmail.com", "234-123-6574"),
        Contact("Claire White", "CWhite@gmail.com", "354-576-9876"),
        Contact("Alex Tsunami", "bigbadTsnuami@gmail.com", "435-576-3249"),
        Contact("Nick Charger", "ChargeNick@gmail.com", "423-456-1238"),
        Contact("Rob Archer", "Rawrcher@gmail.com", "143-465-8976"),
        Contact("Donald Paul", "Dpaul@gmail.com", "534-765-3248"),
        Contact("Jake Freddie", "Jfred@gmail.com", "234-756-9809"),
        Contact("Spencer Justice", "JusticeTime@gmail.com", "342-564-8677"),
        Contact("Deezy Jordan", "DeezyJs@gmail.com", "798-324-7657"),
        Contact("Clark Patty", "CPatty@gmail.com", "987-423-8765"),
        Contact("Pharia Valerie", "Pharval@gmail.com", "765-432-7564"),
        Contact("Kim Shores", "ShoresKim@gmail.com", "345-567-3451"),
        Contact("Slapped Deppals", "GetSlapped@gmail.com", "756-465-6479"),
        Contact("Nick Jungles", "Junglebird@gmail.com", "534-134-7654"),
    )

    ContactList(contacts)

}

@Composable
fun ContactList(contacts: List<Contact>) {
    var selectedContact by remember { mutableStateOf("") }
    val selectedContactName by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 50.dp)
    ) {
        Text(
            "Contact List",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp),) {
            items(contacts) { contact ->
                ContactItem(
                    contact = contact,
                    isSelected = true,
                    onClick = { selectedContact = "contact" }
                )
            }
        }
    }
}

    @Composable
    fun ContactItem(
        contact: Contact,
        isSelected: Boolean,
        onClick: () -> Unit
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick),
            elevation = CardDefaults.cardElevation(
                defaultElevation = if (isSelected) 8.dp else 4.dp
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (isSelected) {
                            MaterialTheme.colorScheme.primaryContainer
                        } else {
                            MaterialTheme.colorScheme.surface
                        }
                    )
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(color = Color.Red),
                    contentAlignment = Alignment.Center
                ) {
                    Text(contact.name,
                            style = TextStyle(fontSize = 10.sp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        contact.name,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                        )
                    )
                    Text (contact.email)
                    Text (contact.phone)
                }
            }
        }
    }

    // Data class for contact information
    data class Contact(
        val name: String,
        val email: String,
        val phone: String
    )

    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun ContactListAppPreview() {
        ContactListApp()
    }

