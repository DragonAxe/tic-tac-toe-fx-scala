import javafx.application.Application
import javafx.event.{ActionEvent, EventHandler}
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.control.{Button, TextField}
import javafx.scene.input.MouseEvent
import javafx.scene.layout.BorderPane
import javafx.stage.{Stage, WindowEvent}

/**
  * JavaFX gui initialization. Server and client threads are also started here via event handlers.
  */
class TicTacToe extends Application {

  override def start(primaryStage: Stage) {
    val canvas = new Canvas()
    val prom = new RePromise[String]

    primaryStage.setTitle("Tic Tac Toe")
    primaryStage.setOnCloseRequest(new EventHandler[WindowEvent] {
      override def handle(t: WindowEvent): Unit = System.exit(0)
    })

    val serverButton = new Button("Start Local Server")
    serverButton.setOnAction(new EventHandler[ActionEvent] {
      override def handle(t: ActionEvent): Unit = {
        TTTServer.start()
        serverButton.setDisable(true)
      }
    })

    val ipAddressField = new TextField("localhost")

    val clientButton = new Button("Connect to Server:")
    clientButton.setOnAction(new EventHandler[ActionEvent] {
      override def handle(t: ActionEvent): Unit = {
        val ip = ipAddressField.getCharacters.toString
        val g = canvas.getGraphicsContext2D
        val logic = new TTTClient(g, prom, ip)
        logic.start()
        clientButton.setDisable(true)
      }
    })


    canvas.setHeight(300)
    canvas.setWidth(300)
    canvas.setOnMouseClicked(new EventHandler[MouseEvent] {
      override def handle(click: MouseEvent): Unit = {
        val pos = (click.getX / 100).floor.toInt + "." + (click.getY / 100).floor.toInt
        println("Click: ")
        prom.succeed(pos)
      }
    })

    val subSubRoot = new BorderPane()
    subSubRoot.setLeft(clientButton)
    subSubRoot.setCenter(ipAddressField)

    val subRoot = new BorderPane()
    subRoot.setLeft(serverButton)
    subRoot.setCenter(subSubRoot)

    val root = new BorderPane()
    root.setCenter(canvas)
    root.setTop(subRoot)

    primaryStage.setScene(new Scene(root, 300, 300))
    primaryStage.sizeToScene()
    primaryStage.show()

  }

}


