package arrow.context

import arrow.core.identity
import arrow.core.raise.Raise
import arrow.core.raise.recover
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * This is a work-around for having nested nulls in generic code.
 * This allows for writing faster generic code instead of using `Option`.
 * This is only used as an optimisation technique in low-level code,
 * always prefer to use `Option` in actual business code when needed in generic code.
 */
@PublishedApi
internal object EmptyValue {
  @Suppress("UNCHECKED_CAST")
  fun <A> unbox(value: Any?): A =
    fold(value, { null as A }, ::identity)

  inline fun <T> combine(first: Any?, second: T, combine: (T, T) -> T): T =
    fold(first, { second }, { t: T -> combine(t, second) })

  @Suppress("UNCHECKED_CAST")
  inline fun <T, R> fold(value: Any?, ifEmpty: () -> R, ifNotEmpty: (T) -> R): R {
    contract {
      callsInPlace(ifEmpty, InvocationKind.AT_MOST_ONCE)
      callsInPlace(ifNotEmpty, InvocationKind.AT_MOST_ONCE)
    }
    return if (value === EmptyValue) ifEmpty() else ifNotEmpty(value as T)
  }

  inline fun <T> unboxOrElse(value: Any?, ifEmpty: () -> T): T {
    contract {
      callsInPlace(ifEmpty, InvocationKind.AT_MOST_ONCE)
    }
    return fold(value, ifEmpty, ::identity)
  }
}

@PublishedApi
internal class FailureValue(val error: Any?) {
  companion object {
    @Suppress("UNCHECKED_CAST")
    inline fun <E, T, R> fold(value: Any?, ifEmpty: (E) -> R, ifNotEmpty: (T) -> R): R {
      contract {
        callsInPlace(ifEmpty, InvocationKind.AT_MOST_ONCE)
        callsInPlace(ifNotEmpty, InvocationKind.AT_MOST_ONCE)
      }
      return if (value is FailureValue) ifEmpty(value.error as E) else ifNotEmpty(value as T)
    }

    inline fun <E, T> unboxOrElse(value: Any?, ifEmpty: (E) -> T): T {
      contract {
        callsInPlace(ifEmpty, InvocationKind.AT_MOST_ONCE)
      }
      return fold(value, ifEmpty, ::identity)
    }

    context(Raise<E>)
    fun <E, T> bind(value: Any?): T =
      unboxOrElse<E, T>(value) { raise(it) }

    inline fun <E, T> maybeFailure(block: context(Raise<E>) () -> T): Any? {
      contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
      }
      return recover(block, ::FailureValue)
    }
  }
}