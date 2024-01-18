package arrow.context.raise

import arrow.context.RaiseResolver
import arrow.context.TestingRaise
import arrow.core.NonEmptyList
import arrow.core.nel
import arrow.core.nonEmptyListOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.Test

@ExtendWith(RaiseResolver::class)
class RaiseAccumulateSpec {
  context(TestingRaise<NonEmptyList<String>>)
  @Test fun raiseAccumulateTakesPrecedenceOverExtensionFunction() = runTest {
    shouldRaise(nonEmptyListOf("false", "1: IsFalse", "2: IsFalse")) {
      zipOrAccumulate(
        { ensure<String>(false) { "false" } },
        { (1..2).mapOrAccumulate { ensure<NonEmptyList<String>>(false) { "$it: IsFalse".nel() } } }
      ) { _, _ -> 1 }
    }
  }

  context(TestingRaise<NonEmptyList<String>>)
  @Test fun raiseAccumulateTakesPrecedenceOverExtensionFunction2() = runTest {
    shouldRaise(nonEmptyListOf("false", "1: IsFalse", "2: IsFalse")) {
      zipOrAccumulate(
        { ensure(false) { raise("false") } },
        { (1..2).mapOrAccumulate { ensure(false) { raise("$it: IsFalse".nel()) } } }
      ) { _, _ -> 1 }
    }
  }
}
