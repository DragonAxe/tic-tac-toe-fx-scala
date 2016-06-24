import java.io.PrintStream
import java.net.{ServerSocket, Socket}
import java.util

import scala.io.BufferedSource

object TTTServer {

  var player = 1

  def main(args: Array[String]) {
    val server = new ServerSocket(9999)
    def collect(opens:List[Socket]): List[Socket] = {
      if (opens.length < 2) {
        collect(opens :+ server.accept())
      } else {
        opens
      }
    }
    val players = collect(Nil)
    def play(grid:Vector[Vector[Int]], iterator: Iterator[Socket]): Unit = {
      players.foreach((player:Socket) => {
        val out = new PrintStream(player.getOutputStream)
        out.println("Display=" + serializedGrid(grid))
      })
      if (!checkGridIsWin(grid)) {
        if (iterator.hasNext) {
          val player = iterator.next()
          val out = new PrintStream(player.getOutputStream)
          out.println("Play")
          val in = new BufferedSource(player.getInputStream)
          in.getLines().next()

          play(grid, iterator)
        } else {
          play(grid, players.iterator)
        }
      }
    }
    play(Vector.fill(3,3)(0), players.iterator)
  }

  /**
    * Serialize any sized grid. Rows are separated by dots.
    *
    * @param grid The grid to serialize
    * @return A string with dot separated rows.
    */
  def serializedGrid(grid:Vector[Vector[Int]]): String = {
    val builder = StringBuilder.newBuilder
    grid.iterator.foreach((v:Vector[Int]) => {
      builder.append('.')
      v.iterator.foreach((z:Int) => {
        builder.append(z.toString)
      })
    })
    builder.toString().substring(1)
  }

  def checkGridIsWin(grid:Vector[Vector[Int]]): Boolean = {
    // TODO: implement grid checking for winner
    false
  }

  def parseCoordinate(text:String):(Int, Int) = {
    val split = text.split(".")
    (split(0), split(1))
  }

  class clientInputConnection(socket: Socket) extends Thread {
    override def run(): Unit = {
      val in = new BufferedSource(socket.getInputStream).getLines()
      val out = new PrintStream(socket.getOutputStream)
      while (in.hasNext) {
        val text = in.next()
        text match {
          case "Start" => println()
        }
      }
    }
  }

  def other(): Unit ={
//
//    //    val player2 = server.accept()
//    val in = new BufferedSource(player1.getInputStream()).getLines()
//    val out = new PrintStream(player1.getOutputStream())
//    var continue = true
//    while (continue) {
//      out.println("Display=010,210,101")
//      out.flush()
//      if (in.hasNext) {
//        println(in.next())
//      } else {
//        continue = false
//      }
//    }
//    player1.close()
//    //    player2.close()
  }

}
