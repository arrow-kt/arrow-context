package arrow.context

import arrow.context.raise.attempt
import arrow.core.NonEmptyList
import arrow.core.raise.Raise
import arrow.core.toNonEmptyListOrNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.property.Arb
import io.kotest.property.arbitrary.list
import io.kotest.property.arbitrary.map
import io.kotest.property.arbitrary.of
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver
import kotlin.math.max
import kotlin.test.fail

// copied from kotest-extensions-arrow

fun <A> Arb.Companion.nonEmptyList(arb: Arb<A>, range: IntRange = 0..100): Arb<NonEmptyList<A>> =
    Arb.list(arb, max(range.first, 1)..range.last).map { it.toNonEmptyListOrNull()!! }

fun Arb.Companion.throwable(): Arb<Throwable> =
    Arb.of(listOf(RuntimeException(), NoSuchElementException(), IllegalArgumentException()))

class RaiseResolver : ParameterResolver {
    override fun supportsParameter(
        parameterContext: ParameterContext,
        extensionContext: ExtensionContext,
    ) = parameterContext.parameter.type == TestingRaise::class.java

    override fun resolveParameter(
        parameterContext: ParameterContext,
        extensionContext: ExtensionContext,
    ) = TestingRaise<Any?>()
}

@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE)
@DslMarker
annotation class TestingRaiseDsl

@TestingRaiseDsl
class TestingRaise<Error> : Raise<Error> {
    override fun raise(r: Error): Nothing =
        fail("Expected to succeed, but raised $r")

    inline fun shouldRaiseAny(
        block: context((@TestingRaiseDsl Raise<Error>))
        () -> Any?,
    ): Error =
        attempt {
            val result = block(given())
            fail("Expected to raise an error, but instead succeeded with $result")
        }

    inline fun <reified ExpectedError : Any> shouldRaise(
        unit: Unit = Unit,
        block: context((@TestingRaiseDsl Raise<Error>))
        () -> Any?,
    ): ExpectedError =
        shouldRaiseAny(block).shouldBeInstanceOf<ExpectedError>()

    inline fun shouldRaise(
        expected: Error,
        block: context((@TestingRaiseDsl Raise<Error>))
        () -> Any?,
    ): Error =
        shouldRaiseAny(block) shouldBe expected
}
