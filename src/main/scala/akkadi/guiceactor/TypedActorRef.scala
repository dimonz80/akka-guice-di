package akkadi.guiceactor

import akka.actor.{Actor, ActorRef}

/**
 * Типизированная обертка над ActorRef
 *
 * @tparam T  actor type
 */
trait TypedActorRef[T <: Actor] {
  val ref: ActorRef
}
