package arrow.context.raise

import arrow.context.shouldRaise
import arrow.core.NonEmptyList
import arrow.core.nel
import arrow.core.nonEmptyListOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class RaiseAccumulateSpec {
  @Test fun raiseAccumulateTakesPrecedenceOverExtensionFunction() = runTest {
    shouldRaise(nonEmptyListOf("false", "1: IsFalse", "2: IsFalse")) {
      zipOrAccumulate(
        { ensure<String>(false) { "false" } },
        { (1..2).mapOrAccumulate { ensure<NonEmptyList<String>>(false) { "$it: IsFalse".nel() } } }
      ) { _, _ -> 1 }
    }
  }

  @Test fun raiseAccumulateTakesPrecedenceOverExtensionFunction2() = runTest {
    shouldRaise(nonEmptyListOf("false", "1: IsFalse", "2: IsFalse")) {
      zipOrAccumulate(
        { ensure(false) { raise("false") } },
        { (1..2).mapOrAccumulate { ensure(false) { raise("$it: IsFalse".nel()) } } }
      ) { _, _ -> 1 }
    }
  }
}
