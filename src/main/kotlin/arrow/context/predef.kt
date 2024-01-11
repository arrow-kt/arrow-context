package arrow.context

import arrow.context.raise.raise
import arrow.core.identity
import arrow.core.raise.Raise

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
  inline fun <T, R> fold(value: Any?, ifEmpty: () -> R, ifNotEmpty: (T) -> R): R =
    if (value === EmptyValue) ifEmpty() else ifNotEmpty(value as T)

  context(Raise<Unit>)
  fun <T> ensureNotEmpty(value: Any?): T = fold(value, { raise() }, ::identity)
}
