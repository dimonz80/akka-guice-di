package akkadi.guiceactor

import akka.actor.{Actor, ActorRef, ActorRefFactory, Props}
import com.google.inject.Inject
import javax.inject.Singleton

/**
 * Factory for constructing TypedActorRef with DI container
 *
 * @param p  actor provider
 * @tparam T - actor type
 */
@Singleton
class TypedActorRefFactory[T <: Actor] @Inject()(p: ActorProducer[T]) {

  import scala.reflect.runtime.universe.TypeTag

  def create(context: ActorRefFactory)(implicit typeTag: TypeTag[T]): TypedActorRef[T] = new TypedActorRef[T] {
    override val ref: ActorRef = context.actorOf(Props.apply(p.produce()))
  }

  def create(context: ActorRefFactory, name: String)(implicit typeTag: TypeTag[T]): TypedActorRef[T] = new TypedActorRef[T] {
    override val ref: ActorRef = context.actorOf(Props.apply(p.produce()), name)
  }
}