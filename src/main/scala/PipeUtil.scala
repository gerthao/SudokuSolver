extension[T] (x: T)
  def |>[U](f: T => U): U = f(x)