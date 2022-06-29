package tech.codeabsolute.presentation.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.java.KoinJavaComponent
import tech.codeabsolute.ui.theme.LightThemePrimary
import tech.codeabsolute.util.empty
import java.io.File
import java.util.prefs.Preferences
import javax.swing.JFileChooser
import javax.swing.UIManager

@Composable
fun SettingsSection(
    preferences: Preferences = KoinJavaComponent.get(Preferences::class.java)
) {
    Row(
        modifier = Modifier
            .shadow(elevation = 6.dp, shape = RoundedCornerShape(4), clip = true)
            .border(border = BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(4))
            .clip(RoundedCornerShape(4))
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Column {
            Text("Language", fontWeight = FontWeight.Bold, fontSize = 28.sp)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = true,
                    onCheckedChange = {},
                    colors = CheckboxDefaults.colors(checkedColor = LightThemePrimary),
                    enabled = false
                )
                Text("English")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = false,
                    onCheckedChange = {},
                    colors = CheckboxDefaults.colors(checkedColor = LightThemePrimary),
                    enabled = false
                )
                Text("Français")
            }
        }
    }
    Row(
        modifier = Modifier
            .shadow(elevation = 6.dp, shape = RoundedCornerShape(4), clip = true)
            .border(border = BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(4))
            .clip(RoundedCornerShape(4))
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {

        var isOpen by remember { mutableStateOf(false) }
        var selectedFolder by remember { mutableStateOf(preferences.get("BACKUP_FOLDER", "Not selected")) }

        Column {
            Text("Backup", fontWeight = FontWeight.Bold, fontSize = 28.sp)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(selectedFolder, modifier = Modifier.padding(end = 8.dp))
                Button(onClick = {
                    isOpen = true
                }) {
                    Text("Select backup folder")
                }
            }
            Row {
                Button(onClick = {
                    doBackup(selectedFolder)
                }) {
                    Text("Do Backup")
                }
            }

            FolderChooser(isOpen) {
                selectedFolder = it ?: String.empty()
                preferences.put("BACKUP_FOLDER", it)
                isOpen = false
            }
        }
    }
}

fun doBackup(selectedFolder: String) {
    val userHomeDirectory = System.getProperty("user.home")
    val directory = "$userHomeDirectory/Sang-T MP/"

    File(directory).copyRecursively(File("$selectedFolder/Sang-T MP backup/"), true)
}

@Composable
fun FolderChooser(
    isOpen: Boolean,
    onFolderSelected: (String?) -> Unit
) {
    if (isOpen) {
        onFolderSelected(FileChooser.chooseDirectorySwing())
    }
}

object FileChooser {

    fun chooseDirectorySwing(): String? {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())

        val chooser = JFileChooser().apply {
            fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
            isVisible = true
        }

        return when (val code = chooser.showOpenDialog(null)) {
            JFileChooser.APPROVE_OPTION -> chooser.selectedFile.absolutePath
            JFileChooser.CANCEL_OPTION -> null
            JFileChooser.ERROR_OPTION -> "An error occurred while executing JFileChooser::showOpenDialog"
            else -> "Unknown return code '${code}' from JFileChooser::showOpenDialog"
        }
    }
}