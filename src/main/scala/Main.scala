import javafx.application.Application
import javafx.scene.control.{Alert, ButtonType}

object Main {
  def main(args: Array[String]) {
    Application.launch(classOf[TicTacToe], args: _*)
  }
}
