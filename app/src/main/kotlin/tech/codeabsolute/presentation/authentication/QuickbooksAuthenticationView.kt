package tech.codeabsolute.presentation.authentication

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.round
import javafx.application.Platform
import javafx.concurrent.Worker
import javafx.embed.swing.JFXPanel
import javafx.scene.Scene
import javafx.scene.web.WebView
import netscape.javascript.JSObject
import tech.codeabsolute.model.Url
import java.awt.BorderLayout
import java.awt.Container
import java.awt.Desktop
import java.net.URI
import javax.swing.JPanel

@Composable
fun QuickbooksAuthenticationView(composeWindow: ComposeWindow, url: Url, response: (URI) -> Unit) {

    val jfxpanel = remember { JFXPanel() }

    Box(modifier = Modifier.fillMaxSize()) {
        JavaFXPanel(
            root = composeWindow,
            panel = jfxpanel,
            // function to initialize JFXPanel, Group, Scene
            onCreate = {
//                Platform.runLater {
//                    val root = WebView()
//                    val engine = root.engine
//                    val scene = Scene(root)
//                    engine.loadWorker.stateProperty().addListener { _, _, newState ->
//                        if (newState === Worker.State.SUCCEEDED) {
//                            val jsObject = root.engine.executeScript("window") as JSObject
//                        }
//                    }
//                    engine.loadWorker.exceptionProperty().addListener { _, _, newError ->
//                        println("page load error : $newError")
//                    }
//                    engine.locationProperty().addListener { _, _, newLocation ->
//                        println("location : $newLocation")
//                        val uri = URI(newLocation)
//                        val host = uri.host
//
//                        if (host == "codeabsolute.tech") {
//                            response(uri)
//                        }
//                    }
//                    jfxpanel.scene = scene
//                    engine.load(url.value)
//                    engine.setOnError { error -> println("onError : $error") }
//                }
            }
        )
    }
}

@Composable
private fun JavaFXPanel(
    root: Container,
    panel: JFXPanel,
    onCreate: () -> Unit
) {
//    val container = remember { JPanel() }
//    val density = LocalDensity.current.density
//
//    Layout(
//        content = {},
//        modifier = Modifier.onGloballyPositioned { childCoordinates ->
//            val coordinates = childCoordinates.parentCoordinates
//            val location = coordinates?.localToWindow(Offset.Zero)?.round() ?: IntOffset.Zero
//            val size = coordinates?.size ?: IntSize.Zero
//            container.setBounds(
//                (location.x / density).toInt(),
//                (location.y / density).toInt(),
//                (size.width / density).toInt(),
//                (size.height / density).toInt()
//            )
//            container.validate()
//            container.repaint()
//        },
//        measurePolicy = { _, _ ->
//            layout(0, 0) {}
//        }
//    )
//
//    DisposableEffect(Unit) {
//        container.apply {
//            layout = BorderLayout(0, 0)
//            add(panel)
//        }
//        root.add(container)
//        onCreate.invoke()
//        onDispose {
//            root.remove(container)
//        }
//    }
}