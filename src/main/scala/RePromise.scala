import scala.concurrent.duration._
import scala.concurrent.{Await, Promise}

class RePromise[T] {

  var isWaiting: Boolean = false
  var prom: Promise[T] = Promise[T]

  def getValue: T = {
    isWaiting = true
    val result = Await.result(prom.future, Duration.Inf)
    prom = Promise[T]
    result
  }

  def succeed(value: T): Unit = {
    if (isWaiting) {
      if (!prom.isCompleted) {
        isWaiting = false
        prom.success(value)
      }
    }
  }
}
