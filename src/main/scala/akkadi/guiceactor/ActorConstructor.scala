package akkadi.guiceactor

import akka.actor.{Actor, ActorRef, ActorRefFactory, Props}
import javax.inject.Singleton

import scala.reflect.ClassTag


/**
 * Actor constructor for variant amount of arguments
 *
 * @tparam T actor type
 */
@Singleton
class ActorConstructor[T <: Actor] {
  def construct(context: ActorRefFactory)(implicit classTag: ClassTag[T]): TypedActorRef[T] =
    new TypedActorRef[T] {
      override val ref: ActorRef = context.actorOf(Props(classTag.runtimeClass))
    }

  def construct[P1](p1: P1)(context: ActorRefFactory)(implicit classTag: ClassTag[T]): TypedActorRef[T] =
    new TypedActorRef[T] {
      override val ref: ActorRef = context.actorOf(Props(classTag.runtimeClass, p1))
    }

  def construct[P1, P2](p1: P1, p2: P1)(context: ActorRefFactory)(implicit classTag: ClassTag[T]): TypedActorRef[T] =
    new TypedActorRef[T] {
      override val ref: ActorRef = context.actorOf(Props(classTag.runtimeClass, p1, p2))
    }

  def construct[P1, P2, P3]( p1: P1, p2: P1, p3: P3)(context: ActorRefFactory)(implicit classTag: ClassTag[T]): TypedActorRef[T] =
    new TypedActorRef[T] {
      override val ref: ActorRef = context.actorOf(Props(classTag.runtimeClass, p1, p2, p3))
    }

  // TODO propagate the construct function with a larger amount of arguments
  // TODO propagate the construct function with a named actors
}