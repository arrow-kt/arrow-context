@file:Suppress("SUBTYPING_BETWEEN_CONTEXT_RECEIVERS")

package arrow.context.raise

import arrow.context.given
import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.NonEmptySet
import arrow.core.raise.EagerEffect
import arrow.core.raise.Effect
import arrow.core.raise.Raise
import arrow.core.raise.RaiseDSL
import arrow.core.raise.merge
import arrow.core.recover
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.experimental.ExperimentalTypeInference

/**
 * Invoke an [EagerEffect] inside `this` [Raise] context.
 * Any _logical failure_ is raised in `this` [Raise] context,
 * and thus short-circuits the computation.
 *
 * @see [recover] if you want to attempt to recover from any _logical failure_.
 */
context(Raise<Error>)
public operator fun <Error, A> EagerEffect<Error, A>.invoke(): A = invoke(this@Raise)

/**
 * Invoke an [EagerEffect] inside `this` [Raise] context.
 * Any _logical failure_ is raised in `this` [Raise] context,
 * and thus short-circuits the computation.
 *
 * @see [recover] if you want to attempt to recover from any _logical failure_.
 */
context(Raise<Error>)
@RaiseDSL
public fun <Error, A> EagerEffect<Error, A>.bind(): A = invoke(this@Raise)

/**
 * Invoke an [Effect] inside `this` [Raise] context.
 * Any _logical failure_ raised are raised in `this` [Raise] context,
 * and thus short-circuits the computation.
 *
 * @see [recover] if you want to attempt to recover from any _logical failure_.
 */
context(Raise<Error>)
public suspend operator fun <Error, A> Effect<Error, A>.invoke(): A = invoke(this@Raise)

/**
 * Invoke an [Effect] inside `this` [Raise] context.
 * Any _logical failure_ raised are raised in `this` [Raise] context,
 * and thus short-circuits the computation.
 *
 * @see [recover] if you want to attempt to recover from any _logical failure_.
 */
context(Raise<Error>)
@RaiseDSL
public suspend fun <Error, A> Effect<Error, A>.bind(): A = invoke(this@Raise)

/**
 * Extract the [Either.Right] value of an [Either].
 * Any encountered [Either.Left] will be raised as a _logical failure_ in `this` [Raise] context.
 * You can wrap the [bind] call in [recover] if you want to attempt to recover from any _logical failure_.
 *
 * <!--- INCLUDE
 * import arrow.core.Either
 * import arrow.core.right
 * import arrow.core.raise.either
 * import arrow.core.raise.recover
 * import io.kotest.matchers.shouldBe
 * -->
 * ```kotlin
 * fun test() {
 *   val one: Either<Nothing, Int> = 1.right()
 *   val left: Either<String, Int> = Either.Left("failed")
 *
 *   either {
 *     val x = one.bind()
 *     val y = recover({ left.bind() }) { _ : String -> 1 }
 *     x + y
 *   } shouldBe Either.Right(2)
 * }
 * ```
 * <!--- KNIT example-raise-dsl-05.kt -->
 * <!--- TEST lines.isEmpty() -->
 */
context(Raise<Error>)
@RaiseDSL
public fun <Error, A> Either<Error, A>.bind(): A = when (this) {
    is Either.Left -> raise(value)
    is Either.Right -> value
}

/**
 * Extracts all the values in the [Map], raising every [Either.Left]
 * as a _logical failure_. In other words, executed [bind] over every
 * value in this [Map].
 */
context(Raise<Error>)
public fun <K, Error, A> Map<K, Either<Error, A>>.bindAll(): Map<K, A> =
    mapValues { (_, a) -> a.bind() }

context(Raise<Error>)
@RaiseDSL
public fun <Error, A> Iterable<Either<Error, A>>.bindAll(): List<A> =
    map { it.bind() }

/**
 * Extracts all the values in the [NonEmptyList], raising every [Either.Left]
 * as a _logical failure_. In other words, executed [bind] over every
 * value in this [NonEmptyList].
 */
context(Raise<Error>)
@RaiseDSL
public fun <Error, A> NonEmptyList<Either<Error, A>>.bindAll(): NonEmptyList<A> =
    map { it.bind() }

/**
 * Extracts all the values in the [NonEmptySet], raising every [Either.Left]
 * as a _logical failure_. In other words, executed [bind] over every
 * value in this [NonEmptySet].
 */
context(Raise<Error>)
@RaiseDSL
public fun <Error, A> NonEmptySet<Either<Error, A>>.bindAll(): NonEmptySet<A> =
    map { it.bind() }.toNonEmptySet()

// here temporarily until it's in Arrow
@RaiseDSL
public inline fun <Error> attempt(
    block: context(Raise<Error>)
    () -> Nothing,
): Error {
    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
    }
    return merge(block)
}

/**
 * Ensures that the [condition] is met;
 * otherwise, [Raise.raise]s a logical failure of type [Error].
 *
 * In summary, this is a type-safe alternative to [require], using the [Raise] API.
 *
 * ### Example:
 * ```
 * @JvmInline
 * value class CountryCode(val code: String)
 *
 * sealed interface CountryCodeError {
 *     data class InvalidLength(val length: Int) : CountryCodeError
 *     object ContainsInvalidChars : CountryCodeError
 * }
 *
 * context(Raise<CountryCodeError>)
 * fun countryCode(rawCode: String): CountryCode {
 *     ensure(rawCode.length == 2) { CountryCodeError.InvalidLength(rawCode.length) }
 *     ensure(rawCode.any { !it.isLetter() }) { CountryCodeError.ContainsInvalidChars }
 *     return CountryCode(rawCode)
 * }
 *
 * fun main() {
 *     recover({
 *         countryCode("US") // valid
 *         countryCode("ABC") // raises CountryCode.InvalidLength error
 *         countryCode("A1") // raises CountryCode.ContainsInvalidChar
 *     }) { error ->
 *         // Handle errors in a type-safe manner
 *         when (error) {
 *             CountryCodeError.ContainsInvalidChars -> {}
 *             is CountryCodeError.InvalidLength -> {}
 *         }
 *     }
 *
 *     // Can call it w/o error handling => prone to runtime errors
 *     countryCodeOrThrow("Will fail")
 *     countryCode("Will fail") // this line won't compile => we're protected
 *
 *     try {
 *         countryCodeOrThrow("US") // valid
 *         countryCodeOrThrow("ABC") // throw IllegalArgumentException
 *         countryCodeOrThrow("A1") // throw IllegalArgumentException
 *     } catch (e: IllegalArgumentException) {
 *         // Not easy to handle
 *     }
 * }
 *
 * // Not type-safe alternative using require
 * @Throws(IllegalArgumentException::class)
 * fun countryCodeOrThrow(rawCode: String): CountryCode {
 *     require(rawCode.length == 2) { CountryCodeError.InvalidLength(rawCode.length) }
 *     require(rawCode.any { !it.isLetter() }) { CountryCodeError.ContainsInvalidChars }
 *     return CountryCode(rawCode)
 * }
 * ```
 *
 * @param condition the condition that must be true.
 * @param raise a lambda that produces an error of type [Error] when the [condition] is false.
 *
 */
context(Raise<Error>)
@OptIn(ExperimentalTypeInference::class)
@RaiseDSL
@OverloadResolutionByLambdaReturnType
public inline fun <Error> ensure(condition: Boolean, raise: () -> Error) {
    contract {
        callsInPlace(raise, InvocationKind.AT_MOST_ONCE)
        returns() implies condition
    }
    return if (condition) Unit else raise(raise())
}

@RaiseDSL
public inline fun ensure(condition: Boolean, raise: () -> Nothing) {
    contract {
        callsInPlace(raise, InvocationKind.AT_MOST_ONCE)
        returns() implies condition
    }
    return if (condition) Unit else raise()
}

/**
 * Ensures that the [value] is not null;
 * otherwise, [Raise.raise]s a logical failure of type [Error].
 *
 * In summary, this is a type-safe alternative to [requireNotNull], using the [Raise] API.
 *
 * ### Example
 * ```
 *@JvmInline
 * value class FullName(val name: String)
 *
 * sealed interface NameError {
 *     object NullValue : NameError
 * }
 *
 * context(Raise<NameError>)
 * fun fullName(name: String?): FullName {
 *     val nonNullName = ensureNotNull(name) { NameError.NullValue }
 *     return FullName(nonNullName)
 * }
 *
 * fun main() {
 *     recover({
 *         fullName("John Doe") // valid
 *         fullName(null) // raises NameError.NullValue error
 *     }) { error ->
 *         // Handle errors in a type-safe manner
 *         when (error) {
 *             NameError.NullValue -> {}
 *         }
 *     }
 * }
 * ```
 *
 * @param value the value that must be non-null.
 * @param raise a lambda that produces an error of type [Error] when the [value] is null.
 */
context(Raise<Error>)
@OptIn(ExperimentalTypeInference::class)
@RaiseDSL
@OverloadResolutionByLambdaReturnType
public inline fun <Error, B : Any> ensureNotNull(value: B?, raise: () -> Error): B {
    contract {
        callsInPlace(raise, InvocationKind.AT_MOST_ONCE)
        returns() implies (value != null)
    }
    return value ?: raise(raise())
}

@RaiseDSL
public inline fun <B : Any> ensureNotNull(value: B?, raise: () -> Nothing): B {
    contract {
        callsInPlace(raise, InvocationKind.AT_MOST_ONCE)
        returns() implies (value != null)
    }
    return value ?: raise()
}

/**
 * Execute the [Raise] context function resulting in [A] or any _logical error_ of type [OtherError],
 * and transform any raised [OtherError] into [Error], which is raised to the outer [Raise].
 *
 * <!--- INCLUDE
 * import arrow.core.Either
 * import arrow.core.raise.either
 * import arrow.core.raise.withError
 * import io.kotest.matchers.shouldBe
 * -->
 * ```kotlin
 * fun test() {
 *   either<Int, String> {
 *     withError(String::length) {
 *       raise("failed")
 *     }
 *   } shouldBe Either.Left(6)
 * }
 * ```
 * <!--- KNIT example-raise-dsl-11.kt -->
 * <!--- TEST lines.isEmpty() -->
 */
context(Raise<Error>)
@RaiseDSL
public inline fun <Error, OtherError, A> withError(
    transform: (OtherError) -> Error,
    block: context(Raise<OtherError>)
    () -> A,
): A {
    contract {
        callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
    }
    val error = attempt { return block(given<Raise<OtherError>>()) }
    raise(transform(error))
}

context(Raise<OriginalError>)
@RaiseDSL
public inline fun <OriginalError, Error1, Error2, R> withError(
    transform1: (Error1) -> OriginalError,
    transform2: (Error2) -> OriginalError,
    block: context(Raise<Error1>, Raise<Error2>)
    () -> R,
): R {
    contract {
        callsInPlace(transform1, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform2, InvocationKind.AT_MOST_ONCE)
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
    }
    return withError(transform1) {
        withError<OriginalError, Error2, R>(transform2) {
            block(given<Raise<Error1>>(), given<Raise<Error2>>())
        }
    }
}

context(Raise<OriginalError>)
@RaiseDSL
public inline fun <OriginalError, Error1, Error2, Error3, R> withError(
    transform1: (Error1) -> OriginalError,
    transform2: (Error2) -> OriginalError,
    transform3: (Error3) -> OriginalError,
    block: context(Raise<Error1>, Raise<Error2>, Raise<Error3>)
    () -> R,
): R {
    contract {
        callsInPlace(transform1, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform2, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform3, InvocationKind.AT_MOST_ONCE)
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
    }
    return withError(transform1, transform2) {
        withError<OriginalError, Error3, R>(transform3) {
            block(given<Raise<Error1>>(), given<Raise<Error2>>(), given<Raise<Error3>>())
        }
    }
}

context(Raise<OriginalError>)
@RaiseDSL
public inline fun <OriginalError, Error1, Error2, Error3, Error4, R> withError(
    transform1: (Error1) -> OriginalError,
    transform2: (Error2) -> OriginalError,
    transform3: (Error3) -> OriginalError,
    transform4: (Error4) -> OriginalError,
    block: context(Raise<Error1>, Raise<Error2>, Raise<Error3>, Raise<Error4>)
    () -> R,
): R {
    contract {
        callsInPlace(transform1, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform2, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform3, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform4, InvocationKind.AT_MOST_ONCE)
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
    }
    return withError(transform1, transform2, transform3) {
        withError<OriginalError, Error4, R>(transform4) {
            block(given<Raise<Error1>>(), given<Raise<Error2>>(), given<Raise<Error3>>(), given<Raise<Error4>>())
        }
    }
}

context(Raise<OriginalError>)
@RaiseDSL
public inline fun <OriginalError, Error1, Error2, Error3, Error4, Error5, R> withError(
    transform1: (Error1) -> OriginalError,
    transform2: (Error2) -> OriginalError,
    transform3: (Error3) -> OriginalError,
    transform4: (Error4) -> OriginalError,
    transform5: (Error5) -> OriginalError,
    block: context(Raise<Error1>, Raise<Error2>, Raise<Error3>, Raise<Error4>, Raise<Error5>)
    () -> R,
): R {
    contract {
        callsInPlace(transform1, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform2, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform3, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform4, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform5, InvocationKind.AT_MOST_ONCE)
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
    }
    return withError(transform1, transform2, transform3, transform4) {
        withError<OriginalError, Error5, R>(transform5) {
            block(given<Raise<Error1>>(), given<Raise<Error2>>(), given<Raise<Error3>>(), given<Raise<Error4>>(), given<Raise<Error5>>())
        }
    }
}

context(Raise<OriginalError>)
@RaiseDSL
public inline fun <OriginalError, Error1, Error2, Error3, Error4, Error5, Error6, R> withError(
    transform1: (Error1) -> OriginalError,
    transform2: (Error2) -> OriginalError,
    transform3: (Error3) -> OriginalError,
    transform4: (Error4) -> OriginalError,
    transform5: (Error5) -> OriginalError,
    transform6: (Error6) -> OriginalError,
    block: context(Raise<Error1>, Raise<Error2>, Raise<Error3>, Raise<Error4>, Raise<Error5>, Raise<Error6>)
    () -> R,
): R {
    contract {
        callsInPlace(transform1, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform2, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform3, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform4, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform5, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform6, InvocationKind.AT_MOST_ONCE)
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
    }
    return withError(transform1, transform2, transform3, transform4, transform5) {
        withError<OriginalError, Error6, R>(transform6) {
            block(given<Raise<Error1>>(), given<Raise<Error2>>(), given<Raise<Error3>>(), given<Raise<Error4>>(), given<Raise<Error5>>(), given<Raise<Error6>>())
        }
    }
}

context(Raise<OriginalError>)
@RaiseDSL
public inline fun <OriginalError, Error1, Error2, Error3, Error4, Error5, Error6, Error7, R> withError(
    transform1: (Error1) -> OriginalError,
    transform2: (Error2) -> OriginalError,
    transform3: (Error3) -> OriginalError,
    transform4: (Error4) -> OriginalError,
    transform5: (Error5) -> OriginalError,
    transform6: (Error6) -> OriginalError,
    transform7: (Error7) -> OriginalError,
    block: context(Raise<Error1>, Raise<Error2>, Raise<Error3>, Raise<Error4>, Raise<Error5>, Raise<Error6>, Raise<Error7>)
    () -> R,
): R {
    contract {
        callsInPlace(transform1, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform2, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform3, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform4, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform5, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform6, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform7, InvocationKind.AT_MOST_ONCE)
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
    }
    return withError(transform1, transform2, transform3, transform4, transform5, transform6) {
        withError<OriginalError, Error7, R>(transform7) {
            block(given<Raise<Error1>>(), given<Raise<Error2>>(), given<Raise<Error3>>(), given<Raise<Error4>>(), given<Raise<Error5>>(), given<Raise<Error6>>(), given<Raise<Error7>>())
        }
    }
}

context(Raise<OriginalError>)
@RaiseDSL
public inline fun <OriginalError, Error1, Error2, Error3, Error4, Error5, Error6, Error7, Error8, R> withError(
    transform1: (Error1) -> OriginalError,
    transform2: (Error2) -> OriginalError,
    transform3: (Error3) -> OriginalError,
    transform4: (Error4) -> OriginalError,
    transform5: (Error5) -> OriginalError,
    transform6: (Error6) -> OriginalError,
    transform7: (Error7) -> OriginalError,
    transform8: (Error8) -> OriginalError,
    block: context(Raise<Error1>, Raise<Error2>, Raise<Error3>, Raise<Error4>, Raise<Error5>, Raise<Error6>, Raise<Error7>, Raise<Error8>)
    () -> R,
): R {
    contract {
        callsInPlace(transform1, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform2, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform3, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform4, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform5, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform6, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform7, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform8, InvocationKind.AT_MOST_ONCE)
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
    }
    return withError(transform1, transform2, transform3, transform4, transform5, transform6, transform7) {
        withError<OriginalError, Error8, R>(transform8) {
            block(given<Raise<Error1>>(), given<Raise<Error2>>(), given<Raise<Error3>>(), given<Raise<Error4>>(), given<Raise<Error5>>(), given<Raise<Error6>>(), given<Raise<Error7>>(), given<Raise<Error8>>())
        }
    }
}

context(Raise<OriginalError>)
@RaiseDSL
public inline fun <OriginalError, Error1, Error2, Error3, Error4, Error5, Error6, Error7, Error8, Error9, R> withError(
    transform1: (Error1) -> OriginalError,
    transform2: (Error2) -> OriginalError,
    transform3: (Error3) -> OriginalError,
    transform4: (Error4) -> OriginalError,
    transform5: (Error5) -> OriginalError,
    transform6: (Error6) -> OriginalError,
    transform7: (Error7) -> OriginalError,
    transform8: (Error8) -> OriginalError,
    transform9: (Error9) -> OriginalError,
    block: context(Raise<Error1>, Raise<Error2>, Raise<Error3>, Raise<Error4>, Raise<Error5>, Raise<Error6>, Raise<Error7>, Raise<Error8>, Raise<Error9>)
    () -> R,
): R {
    contract {
        callsInPlace(transform1, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform2, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform3, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform4, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform5, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform6, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform7, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform8, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform9, InvocationKind.AT_MOST_ONCE)
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
    }
    return withError(transform1, transform2, transform3, transform4, transform5, transform6, transform7, transform8) {
        withError<OriginalError, Error9, R>(transform9) {
            block(given<Raise<Error1>>(), given<Raise<Error2>>(), given<Raise<Error3>>(), given<Raise<Error4>>(), given<Raise<Error5>>(), given<Raise<Error6>>(), given<Raise<Error7>>(), given<Raise<Error8>>(), given<Raise<Error9>>())
        }
    }
}

context(Raise<OriginalError>)
@RaiseDSL
public inline fun <OriginalError, Error1, Error2, Error3, Error4, Error5, Error6, Error7, Error8, Error9, Error10, R> withError(
    transform1: (Error1) -> OriginalError,
    transform2: (Error2) -> OriginalError,
    transform3: (Error3) -> OriginalError,
    transform4: (Error4) -> OriginalError,
    transform5: (Error5) -> OriginalError,
    transform6: (Error6) -> OriginalError,
    transform7: (Error7) -> OriginalError,
    transform8: (Error8) -> OriginalError,
    transform9: (Error9) -> OriginalError,
    transform10: (Error10) -> OriginalError,
    block: context(Raise<Error1>, Raise<Error2>, Raise<Error3>, Raise<Error4>, Raise<Error5>, Raise<Error6>, Raise<Error7>, Raise<Error8>, Raise<Error9>, Raise<Error10>)
    () -> R,
): R {
    contract {
        callsInPlace(transform1, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform2, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform3, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform4, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform5, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform6, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform7, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform8, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform9, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform10, InvocationKind.AT_MOST_ONCE)
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
    }
    return withError(transform1, transform2, transform3, transform4, transform5, transform6, transform7, transform8, transform9) {
        withError<OriginalError, Error10, R>(transform10) {
            block(given<Raise<Error1>>(), given<Raise<Error2>>(), given<Raise<Error3>>(), given<Raise<Error4>>(), given<Raise<Error5>>(), given<Raise<Error6>>(), given<Raise<Error7>>(), given<Raise<Error8>>(), given<Raise<Error9>>(), given<Raise<Error10>>())
        }
    }
}
/*

private fun generateWithError(index: Int): String {
  val numbers = 1..index
  val errorTypes = numbers.map { "Error$it" }
  val types = errorTypes.joinToString()
  val transforms = numbers.map { "transform$it" }
  val transformParameters = transforms.zip(errorTypes) { transform, errorType ->
    "$transform: ($errorType) -> OriginalError"
  }.joinToString(",\n    ")
  val raises = errorTypes.joinToString { "Raise<$it>" }
  val givens = errorTypes.joinToString { "given<Raise<$it>>()" }
  val callsInPlaces = transforms.joinToString("\n      ") {
    "callsInPlace($it, InvocationKind.AT_MOST_ONCE)"
  }
  val transformArguments = transforms.dropLast(1).joinToString()
  val lastTransformArgument = transforms.last()
  return """
    context(Raise<OriginalError>)
    @RaiseDSL
    public inline fun <OriginalError, $types, R> withError(
      $transformParameters,
      block: context($raises) () -> R
    ): R {
      contract {
        $callsInPlaces
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
      }
      return withError($transformArguments) {
        withError<OriginalError, ${errorTypes.last()}, R>($lastTransformArgument) {
          block($givens)
        }
      }
    }
  """.trimIndent()
} */
