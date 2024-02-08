package arrow.context.raise

public fun interface Accumulate<in T> {
    public fun accumulate(t: T)
}
