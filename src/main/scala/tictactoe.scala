import javafx.application.Application
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.stage.{Stage, WindowEvent}

class TicTacToe extends Application {
  override def start(primaryStage: Stage) {
    primaryStage.setTitle("Tic Tac Toe")
    primaryStage.setOnCloseRequest(new EventHandler[WindowEvent] {
      override def handle(t: WindowEvent): Unit = System.exit(0)
    })

    val canvas = new Canvas()
    canvas.setHeight(300)
    canvas.setWidth(300)
    val g = canvas.getGraphicsContext2D
    g.setStroke(Color.GREEN)
    g.strokeLine(0, 100, 300, 100)
    g.strokeLine(0, 200, 300, 200)
    g.strokeLine(100, 0, 100, 300)
    g.strokeLine(200, 0, 200, 300)

    val root = new StackPane
    root.getChildren.add(canvas)

    primaryStage.setScene(new Scene(root, 300, 300))
    primaryStage.show()
  }
}

object ticTacToe {
  def main(args: Array[String]) {
    Application.launch(classOf[TicTacToe], args: _*)
  }
}
