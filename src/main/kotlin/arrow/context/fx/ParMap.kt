@file:Suppress("SUBTYPING_BETWEEN_CONTEXT_RECEIVERS")

package arrow.context.fx

import arrow.context.FailureValue
import arrow.context.FailureValue.Companion.bind
import arrow.context.FailureValue.Companion.maybeFailure
import arrow.context.given
import arrow.context.raise.RaiseAccumulate
import arrow.context.raise.mapOrAccumulate
import arrow.core.NonEmptyList
import arrow.core.raise.Raise
import arrow.core.raise.recover
import arrow.fx.coroutines.parMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

context(Raise<Error>)
public suspend fun <Error, A, B> Iterable<A>.parMapOrAccumulate(
    context: CoroutineContext = EmptyCoroutineContext,
    concurrency: Int,
    combine: (Error, Error) -> Error,
    transform: suspend context(CoroutineScope, Raise<Error>, Raise<NonEmptyList<Error>>)
    (A) -> B,
): List<B> =
    coroutineScope {
        parMap(context, concurrency) {
            recover({
                transform(this@coroutineScope, RaiseAccumulate(given()), given(), it)
            }) {
                FailureValue(it.reduce(combine))
            }
        }.mapOrAccumulate(combine) { maybeFailure ->
            FailureValue.unboxOrElse<Error, B>(maybeFailure) { raise(it) }
        }
    }

context(Raise<Error>)
public suspend fun <Error, A, B> Iterable<A>.parMapOrAccumulate(
    context: CoroutineContext = EmptyCoroutineContext,
    combine: (Error, Error) -> Error,
    transform: suspend context(CoroutineScope, Raise<Error>, Raise<NonEmptyList<Error>>)
    (A) -> B,
): List<B> =
    coroutineScope {
        parMap(context) {
            recover({
                transform(this@coroutineScope, RaiseAccumulate(given()), given(), it)
            }) {
                FailureValue(it.reduce(combine))
            }
        }.mapOrAccumulate(combine) { maybeFailure ->
            FailureValue.unboxOrElse<Error, B>(maybeFailure) { raise(it) }
        }
    }

context(Raise<NonEmptyList<Error>>)
public suspend fun <Error, A, B> Iterable<A>.parMapOrAccumulate(
    context: CoroutineContext = EmptyCoroutineContext,
    concurrency: Int,
    transform: suspend context(CoroutineScope, Raise<Error>, Raise<NonEmptyList<Error>>)
    (A) -> B,
): List<B> =
    coroutineScope {
        parMap(context, concurrency) {
            maybeFailure {
                transform(this@coroutineScope, RaiseAccumulate(given()), given(), it)
            }
        }.mapOrAccumulate { maybeFailure ->
            bind<NonEmptyList<Error>, B>(maybeFailure)
        }
    }

context(Raise<NonEmptyList<Error>>)
public suspend fun <Error, A, B> Iterable<A>.parMapOrAccumulate(
    context: CoroutineContext = EmptyCoroutineContext,
    transform: suspend context(CoroutineScope, Raise<Error>, Raise<NonEmptyList<Error>>)
    (A) -> B,
): List<B> =
    coroutineScope {
        parMap(context) {
            maybeFailure {
                transform(this@coroutineScope, RaiseAccumulate(given()), given(), it)
            }
        }.mapOrAccumulate { maybeFailure ->
            bind<NonEmptyList<Error>, B>(maybeFailure)
        }
    }
