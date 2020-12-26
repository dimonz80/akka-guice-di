package akkadi

import com.google.inject.Guice
import net.codingwell.scalaguice.InjectorExtensions._

object Main extends App {
  println("starting")
  Guice.createInjector(new StartupModule).instance[MainApp]
}



