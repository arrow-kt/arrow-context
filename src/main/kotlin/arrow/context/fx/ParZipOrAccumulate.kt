@file:Suppress("SUBTYPING_BETWEEN_CONTEXT_RECEIVERS")

package arrow.context.fx

import arrow.context.FailureValue.Companion.bind
import arrow.context.FailureValue.Companion.maybeFailure
import arrow.context.given
import arrow.context.raise.RaiseAccumulate
import arrow.context.raise.zipOrAccumulate
import arrow.core.NonEmptyList
import arrow.core.raise.Raise
import arrow.fx.coroutines.parZip
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

//region 2-arity
context(Raise<E>)
public suspend inline fun <E, A, B, C> parZipOrAccumulate(
  crossinline combine: (E, E) -> E,
  crossinline fa: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> A,
  crossinline fb: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> B,
  crossinline transform: suspend CoroutineScope.(A, B) -> C
): C =
  parZipOrAccumulate(EmptyCoroutineContext, combine, fa, fb, transform)

context(Raise<E>)
public suspend inline fun <E, A, B, C> parZipOrAccumulate(
  context: CoroutineContext,
  crossinline combine: (E, E) -> E,
  crossinline fa: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> A,
  crossinline fb: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> B,
  crossinline transform: suspend CoroutineScope.(A, B) -> C
): C =
  parZip(
    context,
    { maybeFailure { fa(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fb(this@parZip, RaiseAccumulate(given()), given()) } }
  ) { a, b ->
    zipOrAccumulate(combine, { bind<E, A>(a) }, { bind<E, B>(b) }) { aa, bb -> transform(aa, bb) }
  }

context(Raise<NonEmptyList<E>>)
public suspend inline fun <E, A, B, C> parZipOrAccumulate(
  crossinline fa: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> A,
  crossinline fb: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> B,
  crossinline transform: suspend CoroutineScope.(A, B) -> C
): C =
  parZipOrAccumulate(EmptyCoroutineContext, fa, fb, transform)

context(Raise<NonEmptyList<E>>)
public suspend inline fun <E, A, B, C> parZipOrAccumulate(
  context: CoroutineContext,
  crossinline fa: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> A,
  crossinline fb: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> B,
  crossinline transform: suspend CoroutineScope.(A, B) -> C
): C =
  parZip(
    context,
    { maybeFailure { fa(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fb(this@parZip, RaiseAccumulate(given()), given()) } }
  ) { a, b ->
    zipOrAccumulate({ bind<E, A>(a) }, { bind<E, B>(b) }) { aa, bb -> transform(aa, bb) }
  }
//endregion

//region 3-arity
context(Raise<E>)
public suspend inline fun <E, A, B, C, D> parZipOrAccumulate(
  crossinline combine: (E, E) -> E,
  crossinline fa: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> A,
  crossinline fb: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> B,
  crossinline fc: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> C,
  crossinline transform: suspend CoroutineScope.(A, B, C) -> D
): D =
  parZipOrAccumulate(EmptyCoroutineContext, combine, fa, fb, fc, transform)

context(Raise<E>)
public suspend inline fun <E, A, B, C, D> parZipOrAccumulate(
  context: CoroutineContext,
  crossinline combine: (E, E) -> E,
  crossinline fa: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> A,
  crossinline fb: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> B,
  crossinline fc: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> C,
  crossinline transform: suspend CoroutineScope.(A, B, C) -> D
): D =
  parZip(
    context,
    { maybeFailure { fa(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fb(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fc(this@parZip, RaiseAccumulate(given()), given()) } }
  ) { a, b, c ->
    zipOrAccumulate(combine, { bind<E, A>(a) }, { bind<E, B>(b) }, { bind<E, C>(c) }) { aa, bb, cc -> transform(aa, bb, cc) }
  }

context(Raise<NonEmptyList<E>>)
public suspend inline fun <E, A, B, C, D> parZipOrAccumulate(
  crossinline fa: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> A,
  crossinline fb: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> B,
  crossinline fc: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> C,
  crossinline transform: suspend CoroutineScope.(A, B, C) -> D
): D =
  parZipOrAccumulate(EmptyCoroutineContext, fa, fb, fc, transform)

context(Raise<NonEmptyList<E>>)
public suspend inline fun <E, A, B, C, D> parZipOrAccumulate(
  context: CoroutineContext,
  crossinline fa: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> A,
  crossinline fb: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> B,
  crossinline fc: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> C,
  crossinline transform: suspend CoroutineScope.(A, B, C) -> D
): D =
  parZip(
    context,
    { maybeFailure { fa(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fb(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fc(this@parZip, RaiseAccumulate(given()), given()) } }
  ) { a, b, c ->
    zipOrAccumulate({ bind<E, A>(a) }, { bind<E, B>(b) }, { bind<E, C>(c) }) { aa, bb, cc -> transform(aa, bb, cc) }
  }
//endregion

//region 4-arity
context(Raise<E>)
public suspend inline fun <E, A, B, C, D, F> parZipOrAccumulate(
  crossinline combine: (E, E) -> E,
  crossinline fa: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> A,
  crossinline fb: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> B,
  crossinline fc: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> C,
  crossinline fd: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> D,
  crossinline transform: suspend CoroutineScope.(A, B, C, D) -> F
): F =
  parZipOrAccumulate(EmptyCoroutineContext, combine, fa, fb, fc, fd, transform)

context(Raise<E>)
public suspend inline fun <E, A, B, C, D, F> parZipOrAccumulate(
  context: CoroutineContext,
  crossinline combine: (E, E) -> E,
  crossinline fa: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> A,
  crossinline fb: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> B,
  crossinline fc: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> C,
  crossinline fd: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> D,
  crossinline transform: suspend CoroutineScope.(A, B, C, D) -> F
): F =
  parZip(
    context,
    { maybeFailure { fa(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fb(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fc(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fd(this@parZip, RaiseAccumulate(given()), given()) } }
  ) { a, b, c, d ->
    zipOrAccumulate(combine, { bind<E, A>(a) }, { bind<E, B>(b) }, { bind<E, C>(c) }, { bind<E, D>(d) }) { aa, bb, cc, dd ->
      transform(aa, bb, cc, dd)
    }
  }

context(Raise<NonEmptyList<E>>)
public suspend inline fun <E, A, B, C, D, F> parZipOrAccumulate(
  crossinline fa: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> A,
  crossinline fb: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> B,
  crossinline fc: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> C,
  crossinline fd: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> D,
  crossinline transform: suspend CoroutineScope.(A, B, C, D) -> F
): F =
  parZipOrAccumulate(EmptyCoroutineContext, fa, fb, fc, fd, transform)

context(Raise<NonEmptyList<E>>)
public suspend inline fun <E, A, B, C, D, F> parZipOrAccumulate(
  context: CoroutineContext,
  crossinline fa: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> A,
  crossinline fb: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> B,
  crossinline fc: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> C,
  crossinline fd: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> D,
  crossinline transform: suspend CoroutineScope.(A, B, C, D) -> F
): F =
  parZip(
    context,
    { maybeFailure { fa(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fb(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fc(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fd(this@parZip, RaiseAccumulate(given()), given()) } }
  ) { a, b, c, d ->
    zipOrAccumulate({ bind<E, A>(a) }, { bind<E, B>(b) }, { bind<E, C>(c) }, { bind<E, D>(d) }) { aa, bb, cc, dd ->
      transform(aa, bb, cc, dd)
    }
  }
//endregion

//region 5-arity
context(Raise<E>)
public suspend inline fun <E, A, B, C, D, F, G> parZipOrAccumulate(
  crossinline combine: (E, E) -> E,
  crossinline fa: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> A,
  crossinline fb: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> B,
  crossinline fc: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> C,
  crossinline fd: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> D,
  crossinline ff: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> F,
  crossinline transform: suspend CoroutineScope.(A, B, C, D, F) -> G
): G =
  parZipOrAccumulate(EmptyCoroutineContext, combine, fa, fb, fc, fd, ff, transform)

context(Raise<E>)
public suspend inline fun <E, A, B, C, D, F, G> parZipOrAccumulate(
  context: CoroutineContext,
  crossinline combine: (E, E) -> E,
  crossinline fa: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> A,
  crossinline fb: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> B,
  crossinline fc: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> C,
  crossinline fd: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> D,
  crossinline ff: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> F,
  crossinline transform: suspend CoroutineScope.(A, B, C, D, F) -> G
): G =
  parZip(
    context,
    { maybeFailure { fa(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fb(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fc(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fd(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { ff(this@parZip, RaiseAccumulate(given()), given()) } }
  ) { a, b, c, d, f ->
    zipOrAccumulate(
      combine,
      { bind<E, A>(a) },
      { bind<E, B>(b) },
      { bind<E, C>(c) },
      { bind<E, D>(d) },
      { bind<E, F>(f) }) { aa, bb, cc, dd, ff ->
      transform(aa, bb, cc, dd, ff)
    }
  }

context(Raise<NonEmptyList<E>>)
public suspend inline fun <E, A, B, C, D, F, G> parZipOrAccumulate(
  crossinline fa: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> A,
  crossinline fb: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> B,
  crossinline fc: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> C,
  crossinline fd: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> D,
  crossinline ff: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> F,
  crossinline transform: suspend CoroutineScope.(A, B, C, D, F) -> G
): G =
  parZipOrAccumulate(EmptyCoroutineContext, fa, fb, fc, fd, ff, transform)

context(Raise<NonEmptyList<E>>)
public suspend inline fun <E, A, B, C, D, F, G> parZipOrAccumulate(
  context: CoroutineContext,
  crossinline fa: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> A,
  crossinline fb: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> B,
  crossinline fc: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> C,
  crossinline fd: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> D,
  crossinline ff: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> F,
  crossinline transform: suspend CoroutineScope.(A, B, C, D, F) -> G
): G =
  parZip(
    context,
    { maybeFailure { fa(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fb(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fc(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fd(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { ff(this@parZip, RaiseAccumulate(given()), given()) } }
  ) { a, b, c, d, f ->
    zipOrAccumulate(
      { bind<E, A>(a) },
      { bind<E, B>(b) },
      { bind<E, C>(c) },
      { bind<E, D>(d) },
      { bind<E, F>(f) }) { aa, bb, cc, dd, ff ->
      transform(aa, bb, cc, dd, ff)
    }
  }
//endregion

//region 6-arity
context(Raise<E>)
public suspend inline fun <E, A, B, C, D, F, G, H> parZipOrAccumulate(
  crossinline combine: (E, E) -> E,
  crossinline fa: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> A,
  crossinline fb: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> B,
  crossinline fc: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> C,
  crossinline fd: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> D,
  crossinline ff: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> F,
  crossinline fg: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> G,
  crossinline transform: suspend CoroutineScope.(A, B, C, D, F, G) -> H
): H =
  parZipOrAccumulate(EmptyCoroutineContext, combine, fa, fb, fc, fd, ff, fg, transform)

context(Raise<E>)
public suspend inline fun <E, A, B, C, D, F, G, H> parZipOrAccumulate(
  context: CoroutineContext,
  crossinline combine: (E, E) -> E,
  crossinline fa: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> A,
  crossinline fb: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> B,
  crossinline fc: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> C,
  crossinline fd: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> D,
  crossinline ff: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> F,
  crossinline fg: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> G,
  crossinline transform: suspend CoroutineScope.(A, B, C, D, F, G) -> H
): H =
  parZip(
    context,
    { maybeFailure { fa(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fb(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fc(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fd(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { ff(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fg(this@parZip, RaiseAccumulate(given()), given()) } }
  ) { a, b, c, d, f, g ->
    zipOrAccumulate(
      combine,
      { bind<E, A>(a) },
      { bind<E, B>(b) },
      { bind<E, C>(c) },
      { bind<E, D>(d) },
      { bind<E, F>(f) },
      { bind<E, G>(g) }) { aa, bb, cc, dd, ff, gg ->
      transform(aa, bb, cc, dd, ff, gg)
    }
  }

context(Raise<NonEmptyList<E>>)
public suspend inline fun <E, A, B, C, D, F, G, H> parZipOrAccumulate(
  crossinline fa: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> A,
  crossinline fb: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> B,
  crossinline fc: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> C,
  crossinline fd: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> D,
  crossinline ff: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> F,
  crossinline fg: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> G,
  crossinline transform: suspend CoroutineScope.(A, B, C, D, F, G) -> H
): H =
  parZipOrAccumulate(EmptyCoroutineContext, fa, fb, fc, fd, ff, fg, transform)

context(Raise<NonEmptyList<E>>)
public suspend inline fun <E, A, B, C, D, F, G, H> parZipOrAccumulate(
  context: CoroutineContext,
  crossinline fa: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> A,
  crossinline fb: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> B,
  crossinline fc: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> C,
  crossinline fd: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> D,
  crossinline ff: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> F,
  crossinline fg: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> G,
  crossinline transform: suspend CoroutineScope.(A, B, C, D, F, G) -> H
): H =
  parZip(
    context,
    { maybeFailure { fa(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fb(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fc(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fd(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { ff(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fg(this@parZip, RaiseAccumulate(given()), given()) } }
  ) { a, b, c, d, f, g ->
    zipOrAccumulate(
      { bind<E, A>(a) },
      { bind<E, B>(b) },
      { bind<E, C>(c) },
      { bind<E, D>(d) },
      { bind<E, F>(f) },
      { bind<E, G>(g) }) { aa, bb, cc, dd, ff, gg ->
      transform(aa, bb, cc, dd, ff, gg)
    }
  }
//endregion

//region 7-arity
context(Raise<E>)
public suspend inline fun <E, A, B, C, D, F, G, H, I> parZipOrAccumulate(
  crossinline combine: (E, E) -> E,
  crossinline fa: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> A,
  crossinline fb: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> B,
  crossinline fc: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> C,
  crossinline fd: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> D,
  crossinline ff: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> F,
  crossinline fg: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> G,
  crossinline fh: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> H,
  crossinline transform: suspend CoroutineScope.(A, B, C, D, F, G, H) -> I
): I =
  parZipOrAccumulate(EmptyCoroutineContext, combine, fa, fb, fc, fd, ff, fg, fh, transform)

context(Raise<E>)
public suspend inline fun <E, A, B, C, D, F, G, H, I> parZipOrAccumulate(
  context: CoroutineContext,
  crossinline combine: (E, E) -> E,
  crossinline fa: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> A,
  crossinline fb: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> B,
  crossinline fc: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> C,
  crossinline fd: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> D,
  crossinline ff: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> F,
  crossinline fg: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> G,
  crossinline fh: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> H,
  crossinline transform: suspend CoroutineScope.(A, B, C, D, F, G, H) -> I
): I =
  parZip(
    context,
    { maybeFailure { fa(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fb(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fc(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fd(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { ff(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fg(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fh(this@parZip, RaiseAccumulate(given()), given()) } }
  ) { a, b, c, d, f, g, h ->
    zipOrAccumulate(
      combine,
      { bind<E, A>(a) },
      { bind<E, B>(b) },
      { bind<E, C>(c) },
      { bind<E, D>(d) },
      { bind<E, F>(f) },
      { bind<E, G>(g) },
      { bind<E, H>(h) }) { aa, bb, cc, dd, ff, gg, hh ->
      transform(aa, bb, cc, dd, ff, gg, hh)
    }
  }

context(Raise<NonEmptyList<E>>)
public suspend inline fun <E, A, B, C, D, F, G, H, I> parZipOrAccumulate(
  crossinline fa: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> A,
  crossinline fb: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> B,
  crossinline fc: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> C,
  crossinline fd: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> D,
  crossinline ff: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> F,
  crossinline fg: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> G,
  crossinline fh: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> H,
  crossinline transform: suspend CoroutineScope.(A, B, C, D, F, G, H) -> I
): I =
  parZipOrAccumulate(EmptyCoroutineContext, fa, fb, fc, fd, ff, fg, fh, transform)

context(Raise<NonEmptyList<E>>)
public suspend inline fun <E, A, B, C, D, F, G, H, I> parZipOrAccumulate(
  context: CoroutineContext,
  crossinline fa: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> A,
  crossinline fb: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> B,
  crossinline fc: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> C,
  crossinline fd: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> D,
  crossinline ff: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> F,
  crossinline fg: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> G,
  crossinline fh: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> H,
  crossinline transform: suspend CoroutineScope.(A, B, C, D, F, G, H) -> I
): I =
  parZip(
    context,
    { maybeFailure { fa(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fb(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fc(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fd(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { ff(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fg(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fh(this@parZip, RaiseAccumulate(given()), given()) } }
  ) { a, b, c, d, f, g, h ->
    zipOrAccumulate(
      { bind<E, A>(a) },
      { bind<E, B>(b) },
      { bind<E, C>(c) },
      { bind<E, D>(d) },
      { bind<E, F>(f) },
      { bind<E, G>(g) },
      { bind<E, H>(h) }) { aa, bb, cc, dd, ff, gg, hh ->
      transform(aa, bb, cc, dd, ff, gg, hh)
    }
  }
//endregion

//region 8-arity
context(Raise<E>)
public suspend inline fun <E, A, B, C, D, F, G, H, I, J> parZipOrAccumulate(
  crossinline combine: (E, E) -> E,
  crossinline fa: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> A,
  crossinline fb: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> B,
  crossinline fc: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> C,
  crossinline fd: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> D,
  crossinline ff: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> F,
  crossinline fg: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> G,
  crossinline fh: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> H,
  crossinline fi: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> I,
  crossinline transform: suspend CoroutineScope.(A, B, C, D, F, G, H, I) -> J
): J =
  parZipOrAccumulate(EmptyCoroutineContext, combine, fa, fb, fc, fd, ff, fg, fh, fi, transform)

context(Raise<E>)
public suspend inline fun <E, A, B, C, D, F, G, H, I, J> parZipOrAccumulate(
  context: CoroutineContext,
  crossinline combine: (E, E) -> E,
  crossinline fa: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> A,
  crossinline fb: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> B,
  crossinline fc: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> C,
  crossinline fd: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> D,
  crossinline ff: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> F,
  crossinline fg: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> G,
  crossinline fh: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> H,
  crossinline fi: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> I,
  crossinline transform: suspend CoroutineScope.(A, B, C, D, F, G, H, I) -> J
): J =
  parZip(
    context,
    { maybeFailure { fa(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fb(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fc(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fd(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { ff(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fg(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fh(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fi(this@parZip, RaiseAccumulate(given()), given()) } }
  ) { a, b, c, d, f, g, h, i ->
    zipOrAccumulate(
      combine,
      { bind<E, A>(a) },
      { bind<E, B>(b) },
      { bind<E, C>(c) },
      { bind<E, D>(d) },
      { bind<E, F>(f) },
      { bind<E, G>(g) },
      { bind<E, H>(h) },
      { bind<E, I>(i) }) { aa, bb, cc, dd, ff, gg, hh, ii ->
      transform(aa, bb, cc, dd, ff, gg, hh, ii)
    }
  }

context(Raise<NonEmptyList<E>>)
public suspend inline fun <E, A, B, C, D, F, G, H, I, J> parZipOrAccumulate(
  crossinline fa: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> A,
  crossinline fb: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> B,
  crossinline fc: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> C,
  crossinline fd: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> D,
  crossinline ff: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> F,
  crossinline fg: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> G,
  crossinline fh: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> H,
  crossinline fi: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> I,
  crossinline transform: suspend CoroutineScope.(A, B, C, D, F, G, H, I) -> J
): J =
  parZipOrAccumulate(EmptyCoroutineContext, fa, fb, fc, fd, ff, fg, fh, fi, transform)

context(Raise<NonEmptyList<E>>)
public suspend inline fun <E, A, B, C, D, F, G, H, I, J> parZipOrAccumulate(
  context: CoroutineContext,
  crossinline fa: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> A,
  crossinline fb: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> B,
  crossinline fc: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> C,
  crossinline fd: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> D,
  crossinline ff: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> F,
  crossinline fg: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> G,
  crossinline fh: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> H,
  crossinline fi: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> I,
  crossinline transform: suspend CoroutineScope.(A, B, C, D, F, G, H, I) -> J
): J =
  parZip(
    context,
    { maybeFailure { fa(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fb(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fc(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fd(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { ff(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fg(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fh(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fi(this@parZip, RaiseAccumulate(given()), given()) } }
  ) { a, b, c, d, f, g, h, i ->
    zipOrAccumulate(
      { bind<E, A>(a) },
      { bind<E, B>(b) },
      { bind<E, C>(c) },
      { bind<E, D>(d) },
      { bind<E, F>(f) },
      { bind<E, G>(g) },
      { bind<E, H>(h) },
      { bind<E, I>(i) }) { aa, bb, cc, dd, ff, gg, hh, ii ->
      transform(aa, bb, cc, dd, ff, gg, hh, ii)
    }
  }
//endregion

//region 9-arity
context(Raise<E>)
public suspend inline fun <E, A, B, C, D, F, G, H, I, J, K> parZipOrAccumulate(
  crossinline combine: (E, E) -> E,
  crossinline fa: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> A,
  crossinline fb: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> B,
  crossinline fc: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> C,
  crossinline fd: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> D,
  crossinline ff: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> F,
  crossinline fg: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> G,
  crossinline fh: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> H,
  crossinline fi: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> I,
  crossinline fj: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> J,
  crossinline transform: suspend CoroutineScope.(A, B, C, D, F, G, H, I, J) -> K
): K =
  parZipOrAccumulate(EmptyCoroutineContext, combine, fa, fb, fc, fd, ff, fg, fh, fi, fj, transform)

context(Raise<E>)
public suspend inline fun <E, A, B, C, D, F, G, H, I, J, K> parZipOrAccumulate(
  context: CoroutineContext,
  crossinline combine: (E, E) -> E,
  crossinline fa: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> A,
  crossinline fb: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> B,
  crossinline fc: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> C,
  crossinline fd: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> D,
  crossinline ff: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> F,
  crossinline fg: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> G,
  crossinline fh: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> H,
  crossinline fi: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> I,
  crossinline fj: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> J,
  crossinline transform: suspend CoroutineScope.(A, B, C, D, F, G, H, I, J) -> K
): K =
  parZip(
    context,
    { maybeFailure { fa(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fb(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fc(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fd(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { ff(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fg(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fh(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fi(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fj(this@parZip, RaiseAccumulate(given()), given()) } }
  ) { a, b, c, d, f, g, h, i, j ->
    zipOrAccumulate(
      combine,
      { bind<E, A>(a) },
      { bind<E, B>(b) },
      { bind<E, C>(c) },
      { bind<E, D>(d) },
      { bind<E, F>(f) },
      { bind<E, G>(g) },
      { bind<E, H>(h) },
      { bind<E, I>(i) },
      { bind<E, J>(j) }) { aa, bb, cc, dd, ff, gg, hh, ii, jj ->
      transform(aa, bb, cc, dd, ff, gg, hh, ii, jj)
    }
  }

context(Raise<NonEmptyList<E>>)
public suspend inline fun <E, A, B, C, D, F, G, H, I, J, K> parZipOrAccumulate(
  crossinline fa: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> A,
  crossinline fb: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> B,
  crossinline fc: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> C,
  crossinline fd: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> D,
  crossinline ff: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> F,
  crossinline fg: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> G,
  crossinline fh: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> H,
  crossinline fi: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> I,
  crossinline fj: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> J,
  crossinline transform: suspend CoroutineScope.(A, B, C, D, F, G, H, I, J) -> K
): K =
  parZipOrAccumulate(EmptyCoroutineContext, fa, fb, fc, fd, ff, fg, fh, fi, fj, transform)

context(Raise<NonEmptyList<E>>)
public suspend inline fun <E, A, B, C, D, F, G, H, I, J, K> parZipOrAccumulate(
  context: CoroutineContext,
  crossinline fa: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> A,
  crossinline fb: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> B,
  crossinline fc: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> C,
  crossinline fd: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> D,
  crossinline ff: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> F,
  crossinline fg: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> G,
  crossinline fh: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> H,
  crossinline fi: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> I,
  crossinline fj: suspend context(CoroutineScope, Raise<E>, Raise<NonEmptyList<E>>) () -> J,
  crossinline transform: suspend CoroutineScope.(A, B, C, D, F, G, H, I, J) -> K
): K =
  parZip(
    context,
    { maybeFailure { fa(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fb(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fc(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fd(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { ff(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fg(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fh(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fi(this@parZip, RaiseAccumulate(given()), given()) } },
    { maybeFailure { fj(this@parZip, RaiseAccumulate(given()), given()) } }
  ) { a, b, c, d, f, g, h, i, j ->
    zipOrAccumulate(
      { bind<E, A>(a) },
      { bind<E, B>(b) },
      { bind<E, C>(c) },
      { bind<E, D>(d) },
      { bind<E, F>(f) },
      { bind<E, G>(g) },
      { bind<E, H>(h) },
      { bind<E, I>(i) },
      { bind<E, J>(j) }) { aa, bb, cc, dd, ff, gg, hh, ii, jj ->
      transform(aa, bb, cc, dd, ff, gg, hh, ii, jj)
    }
  }
//endregion
