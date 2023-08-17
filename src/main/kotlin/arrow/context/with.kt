@file:Suppress("SUBTYPING_BETWEEN_CONTEXT_RECEIVERS")
@file:OptIn(ExperimentalContracts::class)

package arrow.context

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

public inline fun <A, B, R> with(a: A, b: B, block: context(A, B) () -> R): R {
  contract {
    callsInPlace(block, InvocationKind.EXACTLY_ONCE)
  }
  return block(a, b)
}
public inline fun <A, B, C, R> with(a: A, b: B, c: C, block: context(A, B, C) () -> R): R {
  contract {
    callsInPlace(block, InvocationKind.EXACTLY_ONCE)
  }
  return block(a, b, c)
}
public inline fun <A, B, C, D, R> with(a: A, b: B, c: C, d: D, block: context(A, B, C, D) () -> R): R {
  contract {
    callsInPlace(block, InvocationKind.EXACTLY_ONCE)
  }
  return block(a, b, c, d)
}
public inline fun <A, B, C, D, E, R> with(a: A, b: B, c: C, d: D, e: E, block: context(A, B, C, D, E) () -> R): R {
  contract {
    callsInPlace(block, InvocationKind.EXACTLY_ONCE)
  }
  return block(a, b, c, d, e)
}
public inline fun <A, B, C, D, E, F, R> with(a: A, b: B, c: C, d: D, e: E, f: F, block: context(A, B, C, D, E, F) () -> R): R {
  contract {
    callsInPlace(block, InvocationKind.EXACTLY_ONCE)
  }
  return block(a, b, c, d, e, f)
}
public inline fun <A, B, C, D, E, F, G, R> with(a: A, b: B, c: C, d: D, e: E, f: F, g: G, block: context(A, B, C, D, E, F, G) () -> R): R {
  contract {
    callsInPlace(block, InvocationKind.EXACTLY_ONCE)
  }
  return block(a, b, c, d, e, f, g)
}
public inline fun <A, B, C, D, E, F, G, H, R> with(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, block: context(A, B, C, D, E, F, G, H) () -> R): R {
  contract {
    callsInPlace(block, InvocationKind.EXACTLY_ONCE)
  }
  return block(a, b, c, d, e, f, g, h)
}
public inline fun <A, B, C, D, E, F, G, H, I, R> with(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, block: context(A, B, C, D, E, F, G, H, I) () -> R): R {
  contract {
    callsInPlace(block, InvocationKind.EXACTLY_ONCE)
  }
  return block(a, b, c, d, e, f, g, h, i)
}
public inline fun <A, B, C, D, E, F, G, H, I, J, R> with(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, block: context(A, B, C, D, E, F, G, H, I, J) () -> R): R {
  contract {
    callsInPlace(block, InvocationKind.EXACTLY_ONCE)
  }
  return block(a, b, c, d, e, f, g, h, i, j)
}
public inline fun <A, B, C, D, E, F, G, H, I, J, K, R> with(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, block: context(A, B, C, D, E, F, G, H, I, J, K) () -> R): R {
  contract {
    callsInPlace(block, InvocationKind.EXACTLY_ONCE)
  }
  return block(a, b, c, d, e, f, g, h, i, j, k)
}
public inline fun <A, B, C, D, E, F, G, H, I, J, K, L, R> with(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, block: context(A, B, C, D, E, F, G, H, I, J, K, L) () -> R): R {
  contract {
    callsInPlace(block, InvocationKind.EXACTLY_ONCE)
  }
  return block(a, b, c, d, e, f, g, h, i, j, k, l)
}
public inline fun <A, B, C, D, E, F, G, H, I, J, K, L, M, R> with(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, block: context(A, B, C, D, E, F, G, H, I, J, K, L, M) () -> R): R {
  contract {
    callsInPlace(block, InvocationKind.EXACTLY_ONCE)
  }
  return block(a, b, c, d, e, f, g, h, i, j, k, l, m)
}
public inline fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, R> with(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, block: context(A, B, C, D, E, F, G, H, I, J, K, L, M, N) () -> R): R {
  contract {
    callsInPlace(block, InvocationKind.EXACTLY_ONCE)
  }
  return block(a, b, c, d, e, f, g, h, i, j, k, l, m, n)
}
public inline fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, R> with(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, block: context(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O) () -> R): R {
  contract {
    callsInPlace(block, InvocationKind.EXACTLY_ONCE)
  }
  return block(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o)
}
public inline fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, R> with(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, block: context(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P) () -> R): R {
  contract {
    callsInPlace(block, InvocationKind.EXACTLY_ONCE)
  }
  return block(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p)
}
public inline fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R> with(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, block: context(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q) () -> R): R {
  contract {
    callsInPlace(block, InvocationKind.EXACTLY_ONCE)
  }
  return block(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q)
}
public inline fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, S, R> with(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, s: S, block: context(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, S) () -> R): R {
  contract {
    callsInPlace(block, InvocationKind.EXACTLY_ONCE)
  }
  return block(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, s)
}
public inline fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, S, T, R> with(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, s: S, t: T, block: context(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, S, T) () -> R): R {
  contract {
    callsInPlace(block, InvocationKind.EXACTLY_ONCE)
  }
  return block(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, s, t)
}
public inline fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, S, T, U, R> with(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, s: S, t: T, u: U, block: context(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, S, T, U) () -> R): R {
  contract {
    callsInPlace(block, InvocationKind.EXACTLY_ONCE)
  }
  return block(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, s, t, u)
}
public inline fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, S, T, U, V, R> with(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, s: S, t: T, u: U, v: V, block: context(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, S, T, U, V) () -> R): R {
  contract {
    callsInPlace(block, InvocationKind.EXACTLY_ONCE)
  }
  return block(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, s, t, u, v)
}
public inline fun <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, S, T, U, V, W, R> with(a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I, j: J, k: K, l: L, m: M, n: N, o: O, p: P, q: Q, s: S, t: T, u: U, v: V, w: W, block: context(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, S, T, U, V, W) () -> R): R {
  contract {
    callsInPlace(block, InvocationKind.EXACTLY_ONCE)
  }
  return block(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, s, t, u, v, w)
}

/*
// Use this to re-generate
public fun generateDeclaration(index: Int):  String {
  val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toList().filterNot { it == 'R' }.take(index)
  val types = alphabet.joinToString()
  val parameters = alphabet.joinToString { "${it.lowercase()}: $it" }
  val arguments = alphabet.joinToString { it.lowercase() }
  return """
    public inline fun <$types, R> with($parameters, block: context($types) () -> R): R {
      contract { 
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
      }
      return block($arguments)
    }
  """.trimIndent()
}*/
