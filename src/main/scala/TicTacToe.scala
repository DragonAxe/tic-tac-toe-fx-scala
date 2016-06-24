import java.io.PrintStream
import java.net.{InetAddress, Socket}
import javafx.application.Application
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.canvas.{Canvas, GraphicsContext}
import javafx.scene.input.MouseEvent
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.stage.{Stage, WindowEvent}

import scala.io.BufferedSource


class TicTacToe extends Application {

  override def start(primaryStage: Stage) {
    val canvas = new Canvas()
    val logic = new TTTClient(canvas.getGraphicsContext2D)

    primaryStage.setTitle("Tic Tac Toe")
    primaryStage.setOnCloseRequest(new EventHandler[WindowEvent] {
      override def handle(t: WindowEvent): Unit = System.exit(0)
    })


    canvas.setHeight(300)
    canvas.setWidth(300)
    canvas.setOnMouseClicked(new EventHandler[MouseEvent] {
      val maxPlayer = 2
      var player = 1
      var grid = Array.fill(3, 3)(0)
      override def handle(click: MouseEvent): Unit = {

      }
    })

    val root = new StackPane
    root.getChildren.add(canvas)

    primaryStage.setScene(new Scene(root, 300, 300))
    primaryStage.sizeToScene()
    primaryStage.show()

    //////////

    logic.start()

  }




}


