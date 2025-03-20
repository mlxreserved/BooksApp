package com.example.booksapp.presentation.utils

sealed class SnackbarMessage(val message: String, val actionLabel: String) {
    data object SuccessAdd : SnackbarMessage(MESSAGE_ADD_SUCCESS, ACTION_LABEL_SUCCESS)
    data object SuccessDelete : SnackbarMessage(MESSAGE_DELETE_SUCCESS, ACTION_LABEL_SUCCESS)
    data object ErrorAdd : SnackbarMessage(MESSAGE_ADD_ERROR, ACTION_LABEL_ERROR)
    data object ErrorDelete : SnackbarMessage(MESSAGE_DELETE_ERROR, ACTION_LABEL_ERROR)

    companion object {
        const val ACTION_LABEL_SUCCESS = "success"
        const val ACTION_LABEL_ERROR = "error"

        const val MESSAGE_ADD_SUCCESS = "Книга успешно добавлена в избранное"
        const val MESSAGE_DELETE_SUCCESS = "Книга успешно удалена из избранного"
        const val MESSAGE_ADD_ERROR = "Ошибка добавления в избранное"
        const val MESSAGE_DELETE_ERROR = "Ошибка удаления избранной книги"
    }
}