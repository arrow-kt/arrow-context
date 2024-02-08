package arrow.context

context(T)
public fun <T> given(): T = this@T

@JvmInline
public value class Context<T>(private val value: T) {
    public fun given(): T = value
}
