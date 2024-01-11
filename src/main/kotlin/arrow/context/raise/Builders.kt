@file:OptIn(ExperimentalContracts::class)
@file:Suppress("SUBTYPING_BETWEEN_CONTEXT_RECEIVERS")

package arrow.context.raise

import arrow.atomic.Atomic
import arrow.atomic.update
import arrow.atomic.value
import arrow.context.EmptyValue
import arrow.context.given
import arrow.core.*
import arrow.core.raise.*
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@RaiseDSL
public inline fun <Error, A> singleton(
  raise: () -> A,
  block: context(Raise<Unit>, Raise<Error>) () -> A,
): A {
  contract {
    callsInPlace(raise, InvocationKind.AT_MOST_ONCE)
    callsInPlace(block, InvocationKind.AT_MOST_ONCE)
  }
  return recover({ block(this, this) }) { raise() }
}

/**
 * Runs a computation [block] using [Raise], and return its outcome as nullable type,
 * where `null` represents logical failure.
 *
 * This function re-throws any exceptions thrown within the [Raise] block.
 *
 * Read more about running a [Raise] computation in the
 * [Arrow docs](https://arrow-kt.io/learn/typed-errors/working-with-typed-errors/#running-and-inspecting-results).
 *
 * @see ignoreErrors By default, `nullable` only allows raising `null`.
 * Calling [ignoreErrors][ignoreErrors] inside `nullable` allows to raise any error, which will be returned to the caller as if `null` was raised.
 */
public inline fun <A> nullable(block: context(Raise<Unit>, Raise<Nothing?>) () -> A): A? =
  singleton({ null }, block)

/**
 * Runs a computation [block] using [Raise], and return its outcome as [Result].
 *
 *
 * Read more about running a [Raise] computation in the
 * [Arrow docs](https://arrow-kt.io/learn/typed-errors/working-with-typed-errors/#running-and-inspecting-results).
 */
public inline fun <A> result(block: Raise<Throwable>.() -> A): Result<A> =
  fold(block, Result.Companion::failure, Result.Companion::failure, Result.Companion::success)

/**
 * Runs a computation [block] using [Raise], and return its outcome as [Option].
 * - [Some] represents success,
 * - [None] represents logical failure.
 *
 * This function re-throws any exceptions thrown within the [Raise] block.
 *
 * Read more about running a [Raise] computation in the
 * [Arrow docs](https://arrow-kt.io/learn/typed-errors/working-with-typed-errors/#running-and-inspecting-results).
 */
public inline fun <A> option(block: context(Raise<Unit>, Raise<None>) () -> A): Option<A> =
  singleton<None, _>(::none) { block(given<Raise<Unit>>(), given<Raise<None>>()).some() }

/**
 * Runs a computation [block] using [Raise], and return its outcome as [Ior].
 * - [Ior.Right] represents success,
 * - [Ior.Left] represents logical failure which made it impossible to continue,
 * - [Ior.Both] represents that some logical failures were raised,
 *   but it was possible to continue until producing a final value.
 *
 * This function re-throws any exceptions thrown within the [Raise] block.
 *
 * In both [Ior.Left] and [Ior.Both] cases, if more than one logical failure
 * has been raised, they are combined using [combineError].
 *
 * Read more about running a [Raise] computation in the
 * [Arrow docs](https://arrow-kt.io/learn/typed-errors/working-with-typed-errors/#running-and-inspecting-results).
 */
public inline fun <Error, A> ior(
  crossinline combineError: (Error, Error) -> Error,
  block: context(Raise<Error>, Accumulate<Error>) () -> A
): Ior<Error, A> = with(SemigroupAccumulate(combineError)) {
  fold(
    { block(this, given<Accumulate<Error>>()) },
    { accumulateAndGet(it).leftIor() },
    { a -> foldValue({ a.rightIor() }) { e: Error -> Ior.Both(e, a) } }
  )
}

@PublishedApi
internal abstract class SemigroupAccumulate<T> : Accumulate<T> {
  @PublishedApi
  internal val value: Atomic<Any?> = Atomic(EmptyValue)

  abstract fun combine(first: T, second: T): T

  final override fun accumulate(t: T) =
    value.update { EmptyValue.combine(it, t, ::combine) }

  @Suppress("UNCHECKED_CAST")
  fun accumulateAndGet(t: T): T =
    value.updateAndGet { EmptyValue.combine(it, t, ::combine) } as T

  inline fun <R> foldValue(ifEmpty: () -> R, ifNotEmpty: (T) -> R): R =
    EmptyValue.fold(value.value, ifEmpty, ifNotEmpty)
}

@PublishedApi
internal inline fun <T> SemigroupAccumulate(crossinline combine: (T, T) -> T): SemigroupAccumulate<T> =
  object : SemigroupAccumulate<T>() {
    override fun combine(first: T, second: T): T = combine(first, second)
  }

/**
 * Run a computation [block] using [Raise]. and return its outcome as [IorNel].
 * - [Ior.Right] represents success,
 * - [Ior.Left] represents logical failure which made it impossible to continue,
 * - [Ior.Both] represents that some logical failures were raised,
 *   but it was possible to continue until producing a final value.
 *
 * This function re-throws any exceptions thrown within the [Raise] block.
 *
 * In both [Ior.Left] and [Ior.Both] cases, if more than one logical failure
 * has been raised, they are combined using [combineError]. This defaults to
 * combining [NonEmptyList]s by concatenating them.
 *
 * Read more about running a [Raise] computation in the
 * [Arrow docs](https://arrow-kt.io/learn/typed-errors/working-with-typed-errors/#running-and-inspecting-results).
 */
public inline fun <Error, A> iorNel(
  noinline combineError: (NonEmptyList<Error>, NonEmptyList<Error>) -> NonEmptyList<Error> = { a, b -> a + b },
  block: context(Raise<NonEmptyList<Error>>, Accumulate<NonEmptyList<Error>>) () -> A
): IorNel<Error, A> =
  ior(combineError, block)

/**
 * Runs a computation [block] using [Raise], and ignore its outcome.
 *
 * This function re-throws any exceptions thrown within the [Raise] block.
 *
 * Read more about running a [Raise] computation in the
 * [Arrow docs](https://arrow-kt.io/learn/typed-errors/working-with-typed-errors/#running-and-inspecting-results).
 */
public inline fun impure(block: context(Raise<Unit>) () -> Unit) {
  contract { callsInPlace(block, InvocationKind.AT_MOST_ONCE) }
  return singleton<Nothing, _>({ }) { block(given<Raise<Unit>>()) }
}

context(Raise<Unit>)
@RaiseDSL
public fun Raise<Unit>.raise(): Nothing = raise(Unit)

context(Raise<Unit>)
@RaiseDSL
public fun ensure(condition: Boolean) {
  contract { returns() implies condition }
  return if (condition) Unit else raise()
}

context(Raise<Unit>)
@RaiseDSL
public fun <A> Option<A>.bind(): A {
  contract { returns() implies (this@bind is Some<A>) }
  return getOrElse { raise() }
}

context(Raise<Unit>)
@RaiseDSL
@kotlin.internal.LowPriorityInOverloadResolution
public fun <A> A?.bind(): A {
  contract { returns() implies (this@bind != null) }
  return this ?: raise()
}

context(Raise<Unit>)
@RaiseDSL
public fun <A> ensureNotNull(value: A?): A {
  contract { returns() implies (value != null) }
  return value ?: raise()
}

context(Raise<Unit>)
@RaiseDSL
@JvmName("bindAllNullable")
public fun <K, V> Map<K, V?>.bindAll(): Map<K, V> =
  mapValues { (_, v) -> v.bind() }

context(Raise<Unit>)
@JvmName("bindAllOption")
public fun <K, V> Map<K, Option<V>>.bindAll(): Map<K, V> =
  mapValues { (_, v) -> v.bind() }

context(Raise<Unit>)
@RaiseDSL
@JvmName("bindAllNullable")
public fun <A> Iterable<A?>.bindAll(): List<A> =
  map { it.bind() }

context(Raise<Unit>)
@RaiseDSL
@JvmName("bindAllOption")
public fun <A> Iterable<Option<A>>.bindAll(): List<A> =
  map { it.bind() }

context(Raise<Unit>)
@RaiseDSL
@JvmName("bindAllNullable")
public fun <A> NonEmptyList<A?>.bindAll(): NonEmptyList<A> =
  map { it.bind() }

context(Raise<Unit>)
@RaiseDSL
@JvmName("bindAllOption")
public fun <A> NonEmptyList<Option<A>>.bindAll(): NonEmptyList<A> =
  map { it.bind() }

context(Raise<Unit>)
@RaiseDSL
@JvmName("bindAllNullable")
public fun <A> NonEmptySet<A?>.bindAll(): NonEmptySet<A> =
  map { it.bind() }.toNonEmptySet()

context(Raise<Unit>)
@RaiseDSL
@JvmName("bindAllOption")
public fun <A> NonEmptySet<Option<A>>.bindAll(): NonEmptySet<A> =
  map { it.bind() }.toNonEmptySet()

/**
 * Introduces a scope where you can [bind] errors of any type,
 * but no information is saved in the [raise] case.
 */
context(Raise<Unit>)
@RaiseDSL
public inline fun <A> ignoreErrors(
  block: Raise<Any?>.() -> A,
): A {
  contract { callsInPlace(block, InvocationKind.AT_MOST_ONCE) }
  return block(IgnoreErrorsRaise(given()))
}

@PublishedApi
internal class IgnoreErrorsRaise(raise: Raise<Unit>) : TransformingRaise<Unit, Any?>(raise) {
  override fun transform(error: Any?): Unit = Unit
}

context(Raise<Throwable>)
@RaiseDSL
@JvmName("bindResult")
public fun <A> Result<A>.bind(): A = getOrElse(::raise)

context(Raise<Throwable>)
@RaiseDSL
@JvmName("bindAllResult")
public fun <K, V> Map<K, Result<V>>.bindAll(): Map<K, V> =
  mapValues { (_, v) -> v.bind() }

context(Raise<Throwable>)
@RaiseDSL
@JvmName("bindAllResult")
public fun <A> Iterable<Result<A>>.bindAll(): List<A> =
  map { it.bind() }

context(Raise<Throwable>)
@RaiseDSL
@JvmName("bindAllResult")
public fun <A> NonEmptyList<Result<A>>.bindAll(): NonEmptyList<A> =
  map { it.bind() }

context(Raise<Throwable>)
@RaiseDSL
@JvmName("bindAllResult")
public fun <A> NonEmptySet<Result<A>>.bindAll(): NonEmptySet<A> =
  map { it.bind() }.toNonEmptySet()

context(Raise<NonEmptyList<Throwable>>)
@RaiseDSL
@JvmName("bindAllAccumulateResult")
public fun <K, V> Map<K, Result<V>>.bindAll(): Map<K, V> =
  mapOrAccumulate { (_, v) -> v.bind() }

context(Raise<NonEmptyList<Throwable>>)
@RaiseDSL
@JvmName("bindAllAccumulateResult")
public fun <A> Iterable<Result<A>>.bindAll(): List<A> =
  mapOrAccumulate { it.bind() }

context(Raise<NonEmptyList<Throwable>>)
@RaiseDSL
@JvmName("bindAllAccumulateResult")
public fun <A> NonEmptyList<Result<A>>.bindAll(): NonEmptyList<A> =
  mapOrAccumulate { it.bind() }

context(Raise<NonEmptyList<Throwable>>)
@RaiseDSL
@JvmName("bindAllAccumulateResult")
public fun <A> NonEmptySet<Result<A>>.bindAll(): NonEmptySet<A> =
  mapOrAccumulate { it.bind() }

context(Raise<Error>, Accumulate<Error>)
@RaiseDSL
@JvmName("bindAllIor")
public fun <Error, A> Iterable<Ior<Error, A>>.bindAll(): List<A> =
  map { it.bind() }

context(Raise<Error>, Accumulate<Error>)
@RaiseDSL
@JvmName("bindAllIor")
public fun <Error, A> NonEmptyList<Ior<Error, A>>.bindAll(): NonEmptyList<A> =
  map { it.bind() }

context(Raise<Error>, Accumulate<Error>)
@RaiseDSL
@JvmName("bindAllIor")
public fun <Error, A> NonEmptySet<Ior<Error, A>>.bindAll(): NonEmptySet<A> =
  map { it.bind() }.toNonEmptySet()

context(Raise<Error>, Accumulate<Error>)
@JvmName("bindAllIor")
public fun <Error, K, V> Map<K, Ior<Error, V>>.bindAll(): Map<K, V> =
  mapValues { (_, v) -> v.bind() }

context(Raise<Error>, Accumulate<Error>)
@RaiseDSL
public fun <Error, A> Ior<Error, A>.bind(): A =
  when (this) {
    is Ior.Left -> raise(value)
    is Ior.Right -> value
    is Ior.Both -> {
      accumulate(leftValue)
      rightValue
    }
  }
