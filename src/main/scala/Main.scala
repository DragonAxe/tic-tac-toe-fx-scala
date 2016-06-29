import javafx.application.Application

/**
  * Application entry point.
  */
object Main {
  def main(args: Array[String]) {
    Application.launch(classOf[TicTacToe], args: _*)
  }
}
