import javafx.application.Application
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.input.MouseEvent
import javafx.scene.layout.StackPane
import javafx.stage.{Stage, WindowEvent}


class TicTacToe extends Application {

  override def start(primaryStage: Stage) {
    val canvas = new Canvas()
    val prom = new RePromise[String]
    val logic = new TTTClient(canvas.getGraphicsContext2D, prom)


    primaryStage.setTitle("Tic Tac Toe")
    primaryStage.setOnCloseRequest(new EventHandler[WindowEvent] {
      override def handle(t: WindowEvent): Unit = System.exit(0)
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

    val root = new StackPane
    root.getChildren.add(canvas)

    primaryStage.setScene(new Scene(root, 300, 300))
    primaryStage.sizeToScene()
    primaryStage.show()

    logic.start()

  }


}


