package arrow.context.fx

import arrow.atomic.AtomicInt
import arrow.atomic.update
import arrow.context.raise.attempt
import arrow.context.throwable
import arrow.context.unreachable
import arrow.core.NonEmptyList
import arrow.core.nonFatalOrThrow
import arrow.core.raise.merge
import arrow.fx.coroutines.ResourceScope
import arrow.fx.coroutines.resourceScope
import arrow.fx.coroutines.singleThreadContext
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldStartWith
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.string
import io.kotest.property.checkAll
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.test.Test
import kotlin.time.Duration.Companion.milliseconds

class ParMapOrAccumulateTests {
  @Test
  fun parMapOrAccumulateIsStackSafe() = runTestUsingDefaultDispatcher {
    val count = stackSafeIteration()
    val ref = AtomicInt(0)
    merge {
      (0..<count).parMapOrAccumulate(combine = emptyError) { _: Int ->
        ref.update { it + 1 }
      }
    }.size shouldBe count
    ref.get() shouldBe count
  }

  @Test
  fun parMapOrAccumulateRunsInParallel() = runTest {
    val promiseA = CompletableDeferred<Unit>()
    val promiseB = CompletableDeferred<Unit>()
    val promiseC = CompletableDeferred<Unit>()

    merge {
      listOf(suspend {
        promiseA.await()
        promiseC.complete(Unit)
      }, suspend {
        promiseB.await()
        promiseA.complete(Unit)
      }, suspend {
        promiseB.complete(Unit)
        promiseC.await()
      }).parMapOrAccumulate(combine = emptyError) { it.invoke() }
    }
  }

  @Test
  fun parMapOrAccumulateResultsInTheCorrectError() = runTestUsingDefaultDispatcher {
    checkAll(
      Arb.int(min = 10, max = 20), Arb.int(min = 1, max = 9), Arb.throwable()
    ) { n, killOn, e ->
      shouldThrow<Throwable> {
        merge {
          (0..<n).parMapOrAccumulate(combine = emptyError) { i ->
            if (i == killOn) throw e else Unit
          }
        }
      }.nonFatalOrThrow() shouldBe e
    }
  }

  @Test
  fun parMapOrAccumulateConcurrency1OnlyRunsOneTaskAtATime() = runTest {
    val promiseA = CompletableDeferred<Unit>()

    withTimeoutOrNull(100.milliseconds) {
      merge {
        listOf(suspend { promiseA.await() }, suspend { promiseA.complete(Unit) }).parMapOrAccumulate(
          concurrency = 1,
          combine = emptyError
        ) { it.invoke() }
      }
    } shouldBe null
  }

  @Test
  fun parMapOrAccumulateAccumulatesShifts() = runTestUsingDefaultDispatcher {
    checkAll(Arb.string()) { e ->
      attempt<NonEmptyList<String>> {
        (0..<10).parMapOrAccumulate {
          raise(e)
        }
        unreachable()
      } shouldBe NonEmptyList(e, (1..<10).map { e })
    }
  }


  @Test
  fun parMapOrAccumulateCombineEmptyErrorRunsOnProvidedContext() =
    runTestUsingDefaultDispatcher { // 100 is same default length as Arb.list
      checkAll(Arb.int(min = Int.MIN_VALUE, max = 100)) { i ->
        val res = resourceScope {
          merge {
            (0..<i).parMapOrAccumulate(
              single(), combine = emptyError
            ) { Thread.currentThread().name }
          }
        }
        res.forEach { it shouldStartWith "single" }
      }
    }

  @Test
  fun parMapOrAccumulateCombineEmptyErrorConcurrency3RunsOnProvidedContext() =
    runTestUsingDefaultDispatcher { // 100 is same default length as Arb.list
      checkAll(Arb.int(min = Int.MIN_VALUE, max = 100)) { i ->
        val res = resourceScope {
          merge {
            (0..<i).parMapOrAccumulate(
              single(), combine = emptyError, concurrency = 3
            ) { Thread.currentThread().name }
          }
        }
        res.forEach { it shouldStartWith "single" }
      }
    }

  @Test
  fun parMapOrAccumulateRunsOnProvidedContext() =
    runTestUsingDefaultDispatcher { // 100 is same default length as Arb.list
      checkAll(Arb.int(min = Int.MIN_VALUE, max = 100)) { i ->
        val res = resourceScope {
          merge {
            (0..<i).parMapOrAccumulate<Nothing, _, _>(
              single()
            ) { Thread.currentThread().name }
          }
        }
        res.forEach { it shouldStartWith "single" }
      }
    }

  @Test
  fun parMapOrAccumulateConcurrency3RunsOnProvidedContext() =
    runTestUsingDefaultDispatcher { // 100 is same default length as Arb.list
      checkAll(Arb.int(min = Int.MIN_VALUE, max = 100)) { i ->
        val res = resourceScope {
          merge {
            (0..<i).parMapOrAccumulate<Nothing, _, _>(single(), concurrency = 3) {
              Thread.currentThread().name
            }
          }
        }
        res.forEach { it shouldStartWith "single" }
      }
    }
}

private val emptyError: (Nothing, Nothing) -> Nothing = { _, _ -> throw AssertionError("Should not be called") }

suspend fun ResourceScope.single() = singleThreadContext("single")