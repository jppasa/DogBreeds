package dev.jpvillegas.dogbreeds.domain.repository

sealed class FetchState<T>(
    val data: T? = null,
    val error: RepositoryError = RepositoryError.None
) {
    class Loading<T> : FetchState<T>()
    class Success<T>(data: T) : FetchState<T>(data)
    class Error<T>(error: RepositoryError) : FetchState<T>(error = error)
}

enum class RepositoryError {
    Network, Unknown, NoData, None
}