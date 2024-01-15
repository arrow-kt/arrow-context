package arrow.context.raise

import arrow.context.shouldRaise
import arrow.core.nonEmptyListOf
import arrow.core.raise.ensure
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class RaiseAccumulateSpec {
  @Test fun raiseAccumulateTakesPrecedenceOverExtensionFunction() = runTest {
    shouldRaise(nonEmptyListOf("false", "1: IsFalse", "2: IsFalse")) {
      zipOrAccumulate(
        { ensure<String>(false) { "false" } },
        { (1..2).mapOrAccumulate { ensure<String>(false) { "$it: IsFalse" } } }
      ) { _, _ -> 1 }
    }
  }
}
