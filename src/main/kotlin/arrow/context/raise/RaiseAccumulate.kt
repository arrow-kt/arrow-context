@file:OptIn(ExperimentalContracts::class)
@file:Suppress("SUBTYPING_BETWEEN_CONTEXT_RECEIVERS")

package arrow.context.raise

import arrow.context.EmptyValue
import arrow.context.EmptyValue.combine
import arrow.context.EmptyValue.unbox
import arrow.context.EmptyValue.unboxOrElse
import arrow.context.given
import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.NonEmptySet
import arrow.core.nonEmptyListOf
import arrow.core.raise.Raise
import arrow.core.raise.RaiseDSL
import arrow.core.toNonEmptyListOrNull
import arrow.core.toNonEmptySetOrNull
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind.AT_MOST_ONCE
import kotlin.contracts.contract

/**
 * Accumulate the errors from running both [action1] and [action2] using the given [combine] function.
 *
 * See the Arrow docs for more information over
 * [error accumulation](https://arrow-kt.io/learn/typed-errors/working-with-typed-errors/#accumulating-errors)
 * and how to use it in [validation](https://arrow-kt.io/learn/typed-errors/validation/).
 */
context(Raise<Error>)
@RaiseDSL
public inline fun <Error, A, B, C> zipOrAccumulate(
    combine: (Error, Error) -> Error,
    action1: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> A,
    action2: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> B,
    block: (A, B) -> C,
): C {
    contract { callsInPlace(block, AT_MOST_ONCE) }
    return zipOrAccumulate(
        combine,
        action1,
        action2,
        { },
    ) { a, b, _ ->
        block(a, b)
    }
}

/**
 * Accumulate the errors from running [action1], [action2], and [action3] using the given [combine].
 *
 * See the Arrow docs for more information over
 * [error accumulation](https://arrow-kt.io/learn/typed-errors/working-with-typed-errors/#accumulating-errors)
 * and how to use it in [validation](https://arrow-kt.io/learn/typed-errors/validation/).
 */
context(Raise<Error>)
@RaiseDSL
public inline fun <Error, A, B, C, D> zipOrAccumulate(
    combine: (Error, Error) -> Error,
    action1: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> A,
    action2: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> B,
    action3: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> C,
    block: (A, B, C) -> D,
): D {
    contract { callsInPlace(block, AT_MOST_ONCE) }
    return zipOrAccumulate(
        combine,
        action1,
        action2,
        action3,
        { },
    ) { a, b, c, _ ->
        block(a, b, c)
    }
}

/**
 * Accumulate the errors from running [action1], [action2], [action3], and [action4] using the given [combine].
 *
 * See the Arrow docs for more information over
 * [error accumulation](https://arrow-kt.io/learn/typed-errors/working-with-typed-errors/#accumulating-errors)
 * and how to use it in [validation](https://arrow-kt.io/learn/typed-errors/validation/).
 */
context(Raise<Error>)
@RaiseDSL
public inline fun <Error, A, B, C, D, E> zipOrAccumulate(
    combine: (Error, Error) -> Error,
    action1: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> A,
    action2: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> B,
    action3: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> C,
    action4: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> D,
    block: (A, B, C, D) -> E,
): E {
    contract { callsInPlace(block, AT_MOST_ONCE) }
    return zipOrAccumulate(
        combine,
        action1,
        action2,
        action3,
        action4,
        { },
    ) { a, b, c, d, _ ->
        block(a, b, c, d)
    }
}

/**
 * Accumulate the errors from running [action1], [action2], [action3], [action4], and [action5] using the given [combine].
 *
 * See the Arrow docs for more information over
 * [error accumulation](https://arrow-kt.io/learn/typed-errors/working-with-typed-errors/#accumulating-errors)
 * and how to use it in [validation](https://arrow-kt.io/learn/typed-errors/validation/).
 */
context(Raise<Error>)
@RaiseDSL
public inline fun <Error, A, B, C, D, E, F> zipOrAccumulate(
    combine: (Error, Error) -> Error,
    action1: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> A,
    action2: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> B,
    action3: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> C,
    action4: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> D,
    action5: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> E,
    block: (A, B, C, D, E) -> F,
): F {
    contract { callsInPlace(block, AT_MOST_ONCE) }
    return zipOrAccumulate(
        combine,
        action1,
        action2,
        action3,
        action4,
        action5,
        { },
    ) { a, b, c, d, e, _ ->
        block(a, b, c, d, e)
    }
}

/**
 * Accumulate the errors from running [action1], [action2], [action3], [action4], [action5], and [action6] using the given [combine].
 *
 * See the Arrow docs for more information over
 * [error accumulation](https://arrow-kt.io/learn/typed-errors/working-with-typed-errors/#accumulating-errors)
 * and how to use it in [validation](https://arrow-kt.io/learn/typed-errors/validation/).
 */
context(Raise<Error>)
@RaiseDSL
public inline fun <Error, A, B, C, D, E, F, G> zipOrAccumulate(
    combine: (Error, Error) -> Error,
    action1: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> A,
    action2: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> B,
    action3: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> C,
    action4: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> D,
    action5: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> E,
    action6: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> F,
    block: (A, B, C, D, E, F) -> G,
): G {
    contract { callsInPlace(block, AT_MOST_ONCE) }
    return zipOrAccumulate(
        combine,
        action1,
        action2,
        action3,
        action4,
        action5,
        action6,
        { },
    ) { a, b, c, d, e, f, _ ->
        block(a, b, c, d, e, f)
    }
}

/**
 * Accumulate the errors from running [action1], [action2], [action3], [action4], [action5], [action6], and [action7] using the given [combine].
 *
 * See the Arrow docs for more information over
 * [error accumulation](https://arrow-kt.io/learn/typed-errors/working-with-typed-errors/#accumulating-errors)
 * and how to use it in [validation](https://arrow-kt.io/learn/typed-errors/validation/).
 */
context(Raise<Error>)
@RaiseDSL
public inline fun <Error, A, B, C, D, E, F, G, H> zipOrAccumulate(
    combine: (Error, Error) -> Error,
    action1: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> A,
    action2: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> B,
    action3: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> C,
    action4: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> D,
    action5: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> E,
    action6: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> F,
    action7: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> G,
    block: (A, B, C, D, E, F, G) -> H,
): H {
    contract { callsInPlace(block, AT_MOST_ONCE) }
    return zipOrAccumulate(
        combine,
        action1,
        action2,
        action3,
        action4,
        action5,
        action6,
        action7,
        { },
    ) { a, b, c, d, e, f, g, _ ->
        block(a, b, c, d, e, f, g)
    }
}

/**
 * Accumulate the errors from running [action1], [action2], [action3], [action4], [action5], [action6], [action7], and [action8] using the given [combine].
 *
 * See the Arrow docs for more information over
 * [error accumulation](https://arrow-kt.io/learn/typed-errors/working-with-typed-errors/#accumulating-errors)
 * and how to use it in [validation](https://arrow-kt.io/learn/typed-errors/validation/).
 */
context(Raise<Error>)
@RaiseDSL
public inline fun <Error, A, B, C, D, E, F, G, H, I> zipOrAccumulate(
    combine: (Error, Error) -> Error,
    action1: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> A,
    action2: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> B,
    action3: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> C,
    action4: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> D,
    action5: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> E,
    action6: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> F,
    action7: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> G,
    action8: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> H,
    block: (A, B, C, D, E, F, G, H) -> I,
): I {
    contract { callsInPlace(block, AT_MOST_ONCE) }
    return zipOrAccumulate(
        combine,
        action1,
        action2,
        action3,
        action4,
        action5,
        action6,
        action7,
        action8,
        { },
    ) { a, b, c, d, e, f, g, h, _ ->
        block(a, b, c, d, e, f, g, h)
    }
}

/**
 * Accumulate the errors from running [action1], [action2], [action3], [action4], [action5], [action6], [action7], [action8], and [action9] using the given [combine].
 *
 * See the Arrow docs for more information over
 * [error accumulation](https://arrow-kt.io/learn/typed-errors/working-with-typed-errors/#accumulating-errors)
 * and how to use it in [validation](https://arrow-kt.io/learn/typed-errors/validation/).
 */
context(Raise<Error>)
@RaiseDSL
public inline fun <Error, A, B, C, D, E, F, G, H, I, J> zipOrAccumulate(
    combine: (Error, Error) -> Error,
    action1: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> A,
    action2: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> B,
    action3: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> C,
    action4: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> D,
    action5: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> E,
    action6: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> F,
    action7: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> G,
    action8: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> H,
    action9: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> I,
    block: (A, B, C, D, E, F, G, H, I) -> J,
): J {
    contract { callsInPlace(block, AT_MOST_ONCE) }
    var error: Any? = EmptyValue
    return zipOrAccumulate(action1, action2, action3, action4, action5, action6, action7, action8, action9, block, {
        error = combine(error, it.reduce(combine), combine)
    }) { raise(unbox(error)) }
}

@RaiseDSL
public inline fun <Error, A, B, C, D, E, F, G, H, I, J> zipOrAccumulate(
    action1: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> A,
    action2: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> B,
    action3: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> C,
    action4: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> D,
    action5: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> E,
    action6: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> F,
    action7: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> G,
    action8: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> H,
    action9: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> I,
    block: (A, B, C, D, E, F, G, H, I) -> J,
    accumulate: (NonEmptyList<Error>) -> Unit,
    raise: () -> Nothing,
): J {
    contract { callsInPlace(block, AT_MOST_ONCE) }
    val a = valueOrEmpty(action1, accumulate)
    val b = valueOrEmpty(action2, accumulate)
    val c = valueOrEmpty(action3, accumulate)
    val d = valueOrEmpty(action4, accumulate)
    val e = valueOrEmpty(action5, accumulate)
    val f = valueOrEmpty(action6, accumulate)
    val g = valueOrEmpty(action7, accumulate)
    val h = valueOrEmpty(action8, accumulate)
    val i = valueOrEmpty(action9, accumulate)
    return block(
        unboxOrElse(a, raise),
        unboxOrElse(b, raise),
        unboxOrElse(c, raise),
        unboxOrElse(d, raise),
        unboxOrElse(e, raise),
        unboxOrElse(f, raise),
        unboxOrElse(g, raise),
        unboxOrElse(h, raise),
        unboxOrElse(i, raise),
    )
}

/**
 * Accumulate the errors from running both [action1] and [action2].
 *
 * See the Arrow docs for more information over
 * [error accumulation](https://arrow-kt.io/learn/typed-errors/working-with-typed-errors/#accumulating-errors)
 * and how to use it in [validation](https://arrow-kt.io/learn/typed-errors/validation/).
 */
context(Raise<NonEmptyList<Error>>)
@RaiseDSL
public inline fun <Error, A, B, C> zipOrAccumulate(
    action1: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> A,
    action2: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> B,
    block: (A, B) -> C,
): C {
    contract { callsInPlace(block, AT_MOST_ONCE) }
    return zipOrAccumulate(
        action1,
        action2,
        {},
    ) { a, b, _ ->
        block(a, b)
    }
}

/**
 * Accumulate the errors from running [action1], [action2], and [action3].
 *
 * See the Arrow docs for more information over
 * [error accumulation](https://arrow-kt.io/learn/typed-errors/working-with-typed-errors/#accumulating-errors)
 * and how to use it in [validation](https://arrow-kt.io/learn/typed-errors/validation/).
 */
context(Raise<NonEmptyList<Error>>)
@RaiseDSL
public inline fun <Error, A, B, C, D> zipOrAccumulate(
    action1: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> A,
    action2: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> B,
    action3: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> C,
    block: (A, B, C) -> D,
): D {
    contract { callsInPlace(block, AT_MOST_ONCE) }
    return zipOrAccumulate(
        action1,
        action2,
        action3,
        {},
    ) { a, b, c, _ ->
        block(a, b, c)
    }
}

/**
 * Accumulate the errors from running [action1], [action2], [action3], and [action4].
 *
 * See the Arrow docs for more information over
 * [error accumulation](https://arrow-kt.io/learn/typed-errors/working-with-typed-errors/#accumulating-errors)
 * and how to use it in [validation](https://arrow-kt.io/learn/typed-errors/validation/).
 */
context(Raise<NonEmptyList<Error>>)
@RaiseDSL
public inline fun <Error, A, B, C, D, E> zipOrAccumulate(
    action1: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> A,
    action2: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> B,
    action3: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> C,
    action4: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> D,
    block: (A, B, C, D) -> E,
): E {
    contract { callsInPlace(block, AT_MOST_ONCE) }
    return zipOrAccumulate(
        action1,
        action2,
        action3,
        action4,
        {},
    ) { a, b, c, d, _ ->
        block(a, b, c, d)
    }
}

/**
 * Accumulate the errors from running [action1], [action2], [action3], [action4], and [action5].
 *
 * See the Arrow docs for more information over
 * [error accumulation](https://arrow-kt.io/learn/typed-errors/working-with-typed-errors/#accumulating-errors)
 * and how to use it in [validation](https://arrow-kt.io/learn/typed-errors/validation/).
 */
context(Raise<NonEmptyList<Error>>)
@RaiseDSL
public inline fun <Error, A, B, C, D, E, F> zipOrAccumulate(
    action1: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> A,
    action2: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> B,
    action3: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> C,
    action4: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> D,
    action5: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> E,
    block: (A, B, C, D, E) -> F,
): F {
    contract { callsInPlace(block, AT_MOST_ONCE) }
    return zipOrAccumulate(
        action1,
        action2,
        action3,
        action4,
        action5,
        {},
    ) { a, b, c, d, e, _ ->
        block(a, b, c, d, e)
    }
}

/**
 * Accumulate the errors from running [action1], [action2], [action3], [action4], [action5], and [action6].
 *
 * See the Arrow docs for more information over
 * [error accumulation](https://arrow-kt.io/learn/typed-errors/working-with-typed-errors/#accumulating-errors)
 * and how to use it in [validation](https://arrow-kt.io/learn/typed-errors/validation/).
 */
context(Raise<NonEmptyList<Error>>)
@RaiseDSL
public inline fun <Error, A, B, C, D, E, F, G> zipOrAccumulate(
    action1: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> A,
    action2: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> B,
    action3: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> C,
    action4: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> D,
    action5: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> E,
    action6: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> F,
    block: (A, B, C, D, E, F) -> G,
): G {
    contract { callsInPlace(block, AT_MOST_ONCE) }
    return zipOrAccumulate(
        action1,
        action2,
        action3,
        action4,
        action5,
        action6,
        {},
    ) { a, b, c, d, e, f, _ ->
        block(a, b, c, d, e, f)
    }
}

/**
 * Accumulate the errors from running [action1], [action2], [action3], [action4], [action5], [action6], and [action7].
 *
 * See the Arrow docs for more information over
 * [error accumulation](https://arrow-kt.io/learn/typed-errors/working-with-typed-errors/#accumulating-errors)
 * and how to use it in [validation](https://arrow-kt.io/learn/typed-errors/validation/).
 */
context(Raise<NonEmptyList<Error>>)
@RaiseDSL
public inline fun <Error, A, B, C, D, E, F, G, H> zipOrAccumulate(
    action1: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> A,
    action2: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> B,
    action3: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> C,
    action4: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> D,
    action5: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> E,
    action6: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> F,
    action7: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> G,
    block: (A, B, C, D, E, F, G) -> H,
): H {
    contract { callsInPlace(block, AT_MOST_ONCE) }
    return zipOrAccumulate(
        action1,
        action2,
        action3,
        action4,
        action5,
        action6,
        action7,
        {},
    ) { a, b, c, d, e, f, g, _ ->
        block(a, b, c, d, e, f, g)
    }
}

/**
 * Accumulate the errors from running [action1], [action2], [action3], [action4], [action5], [action6], [action7], and [action8].
 *
 * See the Arrow docs for more information over
 * [error accumulation](https://arrow-kt.io/learn/typed-errors/working-with-typed-errors/#accumulating-errors)
 * and how to use it in [validation](https://arrow-kt.io/learn/typed-errors/validation/).
 */
context(Raise<NonEmptyList<Error>>)
@RaiseDSL
public inline fun <Error, A, B, C, D, E, F, G, H, I> zipOrAccumulate(
    action1: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> A,
    action2: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> B,
    action3: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> C,
    action4: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> D,
    action5: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> E,
    action6: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> F,
    action7: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> G,
    action8: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> H,
    block: (A, B, C, D, E, F, G, H) -> I,
): I {
    contract { callsInPlace(block, AT_MOST_ONCE) }
    return zipOrAccumulate(
        action1,
        action2,
        action3,
        action4,
        action5,
        action6,
        action7,
        action8,
        {},
    ) { a, b, c, d, e, f, g, h, _ ->
        block(a, b, c, d, e, f, g, h)
    }
}

/**
 * Accumulate the errors from running [action1], [action2], [action3], [action4], [action5], [action6], [action7], [action8], and [action9].
 *
 * See the Arrow docs for more information over
 * [error accumulation](https://arrow-kt.io/learn/typed-errors/working-with-typed-errors/#accumulating-errors)
 * and how to use it in [validation](https://arrow-kt.io/learn/typed-errors/validation/).
 */
context(Raise<NonEmptyList<Error>>)
@RaiseDSL
public inline fun <Error, A, B, C, D, E, F, G, H, I, J> zipOrAccumulate(
    action1: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> A,
    action2: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> B,
    action3: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> C,
    action4: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> D,
    action5: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> E,
    action6: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> F,
    action7: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> G,
    action8: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> H,
    action9: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> I,
    block: (A, B, C, D, E, F, G, H, I) -> J,
): J {
    contract { callsInPlace(block, AT_MOST_ONCE) }
    val error: MutableList<Error> = mutableListOf()
    return zipOrAccumulate(
        action1,
        action2,
        action3,
        action4,
        action5,
        action6,
        action7,
        action8,
        action9,
        block,
        error::addAll,
    ) { raise(error.toNonEmptyListOrNull()!!) }
}

context(Raise<Error>)
@RaiseDSL
@PublishedApi
internal inline fun <Error, A> Iterable<A>.forEachAccumulating(
    combine: (Error, Error) -> Error,
    operation: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    (A, Boolean) -> Unit,
) {
    val iterator = iterator()
    val firstError: Error = attempt {
        for (item in iterator) {
            operation(RaiseAccumulate(given()), given(), item, false)
        }
        return
    }.reduce(combine)
    iterator.fold(firstError) { error, item ->
        val newError = attempt {
            operation(RaiseAccumulate(given()), given(), item, true)
            return@fold error
        }.reduce(combine)
        combine(error, newError)
    }.let(::raise)
}

context(Raise<NonEmptyList<Error>>)
@RaiseDSL
@PublishedApi
internal inline fun <Error, A> Iterable<A>.forEachAccumulating(
    operation: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    (A, hasError: Boolean) -> Unit,
) {
    val iterator = iterator()
    val firstError = attempt {
        for (item in iterator) {
            operation(RaiseAccumulate(given()), given(), item, false)
        }
        return
    }
    buildList {
        addAll(firstError)
        iterator.forEach { item ->
            attempt {
                operation(RaiseAccumulate(given()), given(), item, true)
                return@forEach
            }.let(::addAll)
        }
    }.toNonEmptyListOrNull()!!.let(::raise)
}

@PublishedApi
internal inline fun <T, R> Iterator<T>.fold(initial: R, operation: (acc: R, T) -> R): R {
    var accumulator = initial
    for (element in this) accumulator = operation(accumulator, element)
    return accumulator
}

/**
 * Transform every element of [this] using the given [transform], or accumulate all the occurred errors using [combine].
 *
 * See the Arrow docs for more information over
 * [error accumulation](https://arrow-kt.io/learn/typed-errors/working-with-typed-errors/#accumulating-errors)
 * and how to use it in [validation](https://arrow-kt.io/learn/typed-errors/validation/).
 */
context(Raise<Error>)
@RaiseDSL
public inline fun <Error, A, B> Iterable<A>.mapOrAccumulate(
    combine: (Error, Error) -> Error,
    transform: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    (A) -> B,
): List<B> = buildList(collectionSizeOrDefault(10)) {
    this@mapOrAccumulate.forEachAccumulating(combine) { item, hasError ->
        val transformed = transform(given<Raise<Error>>(), given<Raise<NonEmptyList<Error>>>(), item)
        if (!hasError) add(transformed)
    }
}

/**
 * Accumulate the errors obtained by executing the [transform] over every element of [this].
 *
 * See the Arrow docs for more information over
 * [error accumulation](https://arrow-kt.io/learn/typed-errors/working-with-typed-errors/#accumulating-errors)
 * and how to use it in [validation](https://arrow-kt.io/learn/typed-errors/validation/).
 */
context(Raise<NonEmptyList<Error>>)
@RaiseDSL
public inline fun <Error, A, B> Iterable<A>.mapOrAccumulate(
    transform: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    (A) -> B,
): List<B> = buildList(collectionSizeOrDefault(10)) {
    this@mapOrAccumulate.forEachAccumulating { item, hasError ->
        val transformed = transform(given<Raise<Error>>(), given<Raise<NonEmptyList<Error>>>(), item)
        if (!hasError) add(transformed)
    }
}

/**
 * Accumulate the errors obtained by executing the [transform] over every element of [NonEmptyList].
 *
 * See the Arrow docs for more information over
 * [error accumulation](https://arrow-kt.io/learn/typed-errors/working-with-typed-errors/#accumulating-errors)
 * and how to use it in [validation](https://arrow-kt.io/learn/typed-errors/validation/).
 */
context(Raise<NonEmptyList<Error>>)
@RaiseDSL
public inline fun <Error, A, B> NonEmptyList<A>.mapOrAccumulate(
    transform: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    (A) -> B,
): NonEmptyList<B> = requireNotNull(all.mapOrAccumulate(transform).toNonEmptyListOrNull())

/**
 * Accumulate the errors obtained by executing the [transform] over every element of [NonEmptySet].
 *
 * See the Arrow docs for more information over
 * [error accumulation](https://arrow-kt.io/learn/typed-errors/working-with-typed-errors/#accumulating-errors)
 * and how to use it in [validation](https://arrow-kt.io/learn/typed-errors/validation/).
 */
context(Raise<NonEmptyList<Error>>)
@RaiseDSL
public inline fun <Error, A, B> NonEmptySet<A>.mapOrAccumulate(
    transform: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    (A) -> B,
): NonEmptySet<B> = buildSet(size) {
    this@mapOrAccumulate.forEachAccumulating { item, hasError ->
        val transformed = transform(given<Raise<Error>>(), given<Raise<NonEmptyList<Error>>>(), item)
        if (!hasError) add(transformed)
    }
}.toNonEmptySetOrNull()!!

context(Raise<NonEmptyList<Error>>)
@RaiseDSL
public inline fun <K, Error, A, B> Map<K, A>.mapOrAccumulate(
    transform: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    (Map.Entry<K, A>) -> B,
): Map<K, B> = buildMap(size) {
    this@mapOrAccumulate.entries.forEachAccumulating { item, hasError ->
        val transformed = transform(given<Raise<Error>>(), given<Raise<NonEmptyList<Error>>>(), item)
        if (!hasError) put(item.key, transformed)
    }
}

context(Raise<NonEmptyList<Error>>)
@RaiseDSL
public fun <Error, K, A> Map<K, Either<Error, A>>.bindAll(): Map<K, A> =
    mapOrAccumulate { (_, a) -> a.bind() }

context(Raise<NonEmptyList<Error>>)
@RaiseDSL
public fun <Error, A> Iterable<Either<Error, A>>.bindAll(): List<A> =
    mapOrAccumulate { it.bind() }

context(Raise<NonEmptyList<Error>>)
@RaiseDSL
public fun <Error, A> NonEmptyList<Either<Error, A>>.bindAll(): NonEmptyList<A> =
    mapOrAccumulate { it.bind() }

context(Raise<NonEmptyList<Error>>)
@RaiseDSL
public fun <Error, A> NonEmptySet<Either<Error, A>>.bindAll(): NonEmptySet<A> =
    mapOrAccumulate { it.bind() }

@PublishedApi
internal class RaiseAccumulate<Error>(raise: Raise<NonEmptyList<Error>>) :
    TransformingRaise<NonEmptyList<Error>, Error>(raise) {
    override fun transform(error: Error): NonEmptyList<Error> = nonEmptyListOf(error)
}

@PublishedApi
internal inline fun <Error, A> valueOrEmpty(
    block: context(Raise<Error>, Raise<NonEmptyList<Error>>)
    () -> A,
    accumulate: (NonEmptyList<Error>) -> Unit,
): Any? {
    contract {
        callsInPlace(block, AT_MOST_ONCE)
    }
    accumulate(attempt { return block(RaiseAccumulate(given()), given()) })
    return EmptyValue
}

@PublishedApi
internal fun <T> Iterable<T>.collectionSizeOrDefault(default: Int): Int =
    if (this is Collection<*>) this.size else default
