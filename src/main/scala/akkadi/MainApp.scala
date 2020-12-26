package akkadi

import akka.actor.{ActorRef, ActorSystem}
import akkadi.guiceactor.ActorFactory
import javax.inject.{Inject, Singleton}

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.io.StdIn.readLine


/**
 * Main application module
 *
 * @param factory
 * @param system
 */
@Singleton
class MainApp @Inject()(factory: ActorFactory, system: ActorSystem) {

  val mainActor: ActorRef = factory.typedRef[PrimaryActor](factory.system, "main-actor").ref

  Iterator.continually(readLine(">>>")).takeWhile(_ != "end").foreach {
    case null =>
    case msg if msg != "" => mainActor ! msg
    case _ =>
  }

  Await.result(system.terminate, Duration.Inf)
}
