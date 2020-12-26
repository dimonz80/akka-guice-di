package akkadi

import com.google.inject.{Inject, Injector}
import javax.inject.Singleton

/**
 * Test module for using in actors
 *
 * @param in DI injector
 */
@Singleton
class SomeService @Inject()(in: Injector) {
  def someFunction(): Unit = {
    println(s"some service func works fine")
  }
}
