object Test {
  def foo[F[_], A](fa: F[A]): String =
    fa.toString

  foo { x: Int =>
    x * 2
  }
}
