@file:Suppress("SUBTYPING_BETWEEN_CONTEXT_RECEIVERS")
package arrow.context.raise

import arrow.context.given
import arrow.core.raise.Raise
import arrow.core.raise.RaiseDSL
import arrow.core.raise.merge
import arrow.core.raise.withError
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

context(Raise<OriginalError>)
@RaiseDSL
public inline fun <OriginalError, Error1, Error2, R> withError(
  transform1: (Error1) -> OriginalError,
  transform2: (Error2) -> OriginalError,
  block: context(Raise<Error1>, Raise<Error2>) () -> R
): R {
  contract {
    callsInPlace(transform1, InvocationKind.AT_MOST_ONCE)
    callsInPlace(transform2, InvocationKind.AT_MOST_ONCE)
  }
  return withError(transform1) {
    given<Raise<OriginalError>>().withError(transform2) {
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
  block: context(Raise<Error1>, Raise<Error2>, Raise<Error3>) () -> R
): R {
  contract {
    callsInPlace(transform1, InvocationKind.AT_MOST_ONCE)
    callsInPlace(transform2, InvocationKind.AT_MOST_ONCE)
    callsInPlace(transform3, InvocationKind.AT_MOST_ONCE)
  }
  return withError(transform1, transform2) {
    given<Raise<OriginalError>>().withError(transform3) {
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
  block: context(Raise<Error1>, Raise<Error2>, Raise<Error3>, Raise<Error4>) () -> R
): R {
  contract {
    callsInPlace(transform1, InvocationKind.AT_MOST_ONCE)
    callsInPlace(transform2, InvocationKind.AT_MOST_ONCE)
    callsInPlace(transform3, InvocationKind.AT_MOST_ONCE)
    callsInPlace(transform4, InvocationKind.AT_MOST_ONCE)
  }
  return withError(transform1, transform2, transform3) {
    given<Raise<OriginalError>>().withError(transform4) {
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
  block: context(Raise<Error1>, Raise<Error2>, Raise<Error3>, Raise<Error4>, Raise<Error5>) () -> R
): R {
  contract {
    callsInPlace(transform1, InvocationKind.AT_MOST_ONCE)
    callsInPlace(transform2, InvocationKind.AT_MOST_ONCE)
    callsInPlace(transform3, InvocationKind.AT_MOST_ONCE)
    callsInPlace(transform4, InvocationKind.AT_MOST_ONCE)
    callsInPlace(transform5, InvocationKind.AT_MOST_ONCE)
  }
  return withError(transform1, transform2, transform3, transform4) {
    given<Raise<OriginalError>>().withError(transform5) {
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
  block: context(Raise<Error1>, Raise<Error2>, Raise<Error3>, Raise<Error4>, Raise<Error5>, Raise<Error6>) () -> R
): R {
  contract {
    callsInPlace(transform1, InvocationKind.AT_MOST_ONCE)
    callsInPlace(transform2, InvocationKind.AT_MOST_ONCE)
    callsInPlace(transform3, InvocationKind.AT_MOST_ONCE)
    callsInPlace(transform4, InvocationKind.AT_MOST_ONCE)
    callsInPlace(transform5, InvocationKind.AT_MOST_ONCE)
    callsInPlace(transform6, InvocationKind.AT_MOST_ONCE)
  }
  return withError(transform1, transform2, transform3, transform4, transform5) {
    given<Raise<OriginalError>>().withError(transform6) {
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
  block: context(Raise<Error1>, Raise<Error2>, Raise<Error3>, Raise<Error4>, Raise<Error5>, Raise<Error6>, Raise<Error7>) () -> R
): R {
  contract {
    callsInPlace(transform1, InvocationKind.AT_MOST_ONCE)
    callsInPlace(transform2, InvocationKind.AT_MOST_ONCE)
    callsInPlace(transform3, InvocationKind.AT_MOST_ONCE)
    callsInPlace(transform4, InvocationKind.AT_MOST_ONCE)
    callsInPlace(transform5, InvocationKind.AT_MOST_ONCE)
    callsInPlace(transform6, InvocationKind.AT_MOST_ONCE)
    callsInPlace(transform7, InvocationKind.AT_MOST_ONCE)
  }
  return withError(transform1, transform2, transform3, transform4, transform5, transform6) {
    given<Raise<OriginalError>>().withError(transform7) {
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
  block: context(Raise<Error1>, Raise<Error2>, Raise<Error3>, Raise<Error4>, Raise<Error5>, Raise<Error6>, Raise<Error7>, Raise<Error8>) () -> R
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
  }
  return withError(transform1, transform2, transform3, transform4, transform5, transform6, transform7) {
    given<Raise<OriginalError>>().withError(transform8) {
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
  block: context(Raise<Error1>, Raise<Error2>, Raise<Error3>, Raise<Error4>, Raise<Error5>, Raise<Error6>, Raise<Error7>, Raise<Error8>, Raise<Error9>) () -> R
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
  }
  return withError(transform1, transform2, transform3, transform4, transform5, transform6, transform7, transform8) {
    given<Raise<OriginalError>>().withError(transform9) {
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
  block: context(Raise<Error1>, Raise<Error2>, Raise<Error3>, Raise<Error4>, Raise<Error5>, Raise<Error6>, Raise<Error7>, Raise<Error8>, Raise<Error9>, Raise<Error10>) () -> R
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
  }
  return withError(transform1, transform2, transform3, transform4, transform5, transform6, transform7, transform8, transform9) {
    given<Raise<OriginalError>>().withError(transform10) {
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
      }
      return withError($transformArguments) {
        given<Raise<OriginalError>>().withError($lastTransformArgument) {
          block($givens)
        }
      }
    }
  """.trimIndent()
}*/

// here temporarily until it's in Arrow
@RaiseDSL
public inline fun <Error> attempt(block: Raise<Error>.() -> Nothing): Error {
  contract {
    callsInPlace(block, InvocationKind.AT_MOST_ONCE)
  }
  return merge(block)
}

context(Raise<Error>)
@RaiseDSL
public fun <Error> Error.raise(): Nothing = raise(this)