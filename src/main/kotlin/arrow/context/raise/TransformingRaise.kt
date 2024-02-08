package arrow.context.raise

import arrow.core.raise.Raise
import arrow.core.raise.RaiseDSL

public abstract class TransformingRaise<Error, OtherError>(private val raise: Raise<Error>) : Raise<OtherError> {
    public abstract fun transform(error: OtherError): Error

    @RaiseDSL
    final override fun raise(r: OtherError): Nothing = raise.raise(transform(r))
}
