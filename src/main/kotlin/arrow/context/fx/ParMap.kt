@file:Suppress("SUBTYPING_BETWEEN_CONTEXT_RECEIVERS")

package arrow.context.fx

import arrow.context.given
import arrow.context.raise.RaiseAccumulate
import arrow.context.raise.attempt
import arrow.context.raise.mapOrAccumulate
import arrow.core.NonEmptyList
import arrow.core.left
import arrow.core.raise.Raise
import arrow.core.raise.either
import arrow.core.right
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

context(Raise<Error>)
public suspend fun <Error, A, B> Iterable<A>.parMapOrAccumulate(
  context: CoroutineContext = EmptyCoroutineContext,
  concurrency: Int,
  combine: (Error, Error) -> Error,
  transform: suspend context(CoroutineScope, Raise<Error>, Raise<NonEmptyList<Error>>) (A) -> B
): List<B> =
  coroutineScope {
    val semaphore = Semaphore(concurrency)
    map {
      async(context) {
        attempt {
          return@async semaphore.withPermit {
            transform(this@coroutineScope, RaiseAccumulate(given()), given(), it).right()
          }
        }.reduce(combine).left()
      }
    }.awaitAll().mapOrAccumulate(combine) { it.bind() }
  }

context(Raise<Error>)
public suspend fun <Error, A, B> Iterable<A>.parMapOrAccumulate(
  context: CoroutineContext = EmptyCoroutineContext,
  combine: (Error, Error) -> Error,
  transform: suspend context(CoroutineScope, Raise<Error>, Raise<NonEmptyList<Error>>) (A) -> B
): List<B> =
  coroutineScope {
    map {
      async(context) {
        attempt {
          return@async transform(this@coroutineScope, RaiseAccumulate(given()), given(), it).right()
        }.reduce(combine).left()
      }
    }.awaitAll().mapOrAccumulate(combine) { it.bind() }
  }

context(Raise<NonEmptyList<Error>>)
public suspend fun <Error, A, B> Iterable<A>.parMapOrAccumulate(
  context: CoroutineContext = EmptyCoroutineContext,
  concurrency: Int,
  transform: suspend context(CoroutineScope, Raise<Error>, Raise<NonEmptyList<Error>>) (A) -> B
): List<B> =
  coroutineScope {
    val semaphore = Semaphore(concurrency)
    map {
      async(context) {
        either {
          semaphore.withPermit {
            transform(this@coroutineScope, RaiseAccumulate(this), this, it)
          }
        }
      }
    }.awaitAll().mapOrAccumulate { it.bind() }
  }

context(Raise<NonEmptyList<Error>>)
public suspend fun <Error, A, B> Iterable<A>.parMapOrAccumulate(
  context: CoroutineContext = EmptyCoroutineContext,
  transform: suspend context(CoroutineScope, Raise<Error>, Raise<NonEmptyList<Error>>) (A) -> B
): List<B> =
  coroutineScope {
    map {
      async(context) {
        either {
          transform(this@coroutineScope, RaiseAccumulate(this), this, it)
        }
      }
    }.awaitAll().mapOrAccumulate { it.bind() }
  }
