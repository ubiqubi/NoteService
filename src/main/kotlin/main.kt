// Создаем класс Node с общими полями для заметок и комментариев
open class Node(
    var id: Int,
    var isDeleting: Boolean = false, // Маркер удаления
    var userId: Int
)

// Класс Note наследуется от Node и содержит дополнительные поля для заметок
class Note(
    nid: Int = 0,
    isDeleting: Boolean,
    userId: Int,
    var title: String,
    var text: String,

    ) : Node(nid, isDeleting, userId) {
    // Вернуть только заголовок для удобства проверки
    override fun toString(): String {
        return title
    }
}

// Класс Comment наследуется от Node и содержит дополнительные поля для комментариев
class Comment(
    cid: Int = 0, isDeleting: Boolean, userId: Int, val nid: Int, var message: String
) : Node(cid, isDeleting, userId) {
    // Вернуть только текст комментария для удобства проверки
    override fun toString(): String {
        return message
    }
}

// Создаем класс NoteService для работы с заметками и комментариями
class NoteService {

    // Создаем массивы для хранения заметок и комментариев
    private val notes: MutableList<Note> = mutableListOf()
    private val comments: MutableList<Comment> = mutableListOf()

    // Функция добавления новой заметки
    fun add(
        title: String, text: String
    ): Note {
        val newNote = Note(
            notes.size + 1, false, getCurrentUserId(), title, text
        )
        notes.add(newNote)
        return newNote
    }

    // Функция создания нового комментария к заметке
    fun createComment(noteId: Int, message: String): Comment? {
        val note = getNoteById(noteId)
        if (note != null && !note.isDeleting) {
            val newComment = Comment(comments.size + 1, false, getCurrentUserId(), noteId, message)
            comments.add(newComment)
            return newComment
        } else {
            return null
        }
    }

    // Функция удаления заметки
    fun delete(noteId: Int): Int {
        val note = getNoteById(noteId)
        if (note != null) {
            note.isDeleting = true
            return 1
        } else {
            return -1
        }
    }


    // Функция редактирования заметки
    fun edit(noteId: Int, newTitle: String, newText: String): Note? {
        val note = getNoteById(noteId)
        if (note != null && !note.isDeleting) {
            note.title = newTitle
            note.text = newText
            return note
        } else {
            return null
        }
    }

    // Функция редактирование комментария
    fun editComment(commentId: Int, newMessage: String): Comment? {
        val comment = getCommentById(commentId)
        if (comment != null && !comment.isDeleting) {
            comment.message = newMessage
            return comment
        } else {
            return null
        }
    }

    // Функция получения списка заметок пользователя
    fun get(noteIds: String, offset: Int, count: Int, sort: Int): List<Note> {
        // Конвертируем текст в числа
        val ids = if (noteIds.isEmpty()) {
            emptyList()
        } else {
            noteIds.split(" ").map { it.toInt() }
        }
        // Сортируем список
        val sortedNotes = when (sort) {
            0 -> notes.sortedByDescending { it.id }
            1 -> notes.sortedBy { it.id }
            else -> notes
        }
        // Возвращаем список по noteIds или по count
        return if (ids[0] != 0) {
            sortedNotes
                .filter { it.id in ids }
                .filter { !it.isDeleting }
        } else {
            sortedNotes.drop(offset).take(count)
        }

    }


    // Функция получения заметки по её id
    fun getById(noteId: Int): Note? {
        val note = getNoteById(noteId)
        return if (note != null && !note.isDeleting) {
            note
        } else {
            null
        }
    }

    // Функция получения списка комментариев к заметке
    fun getComments(noteId: Int): List<Comment> {
        return comments.filter { it.nid == noteId && !it.isDeleting }
    }

    // Функция удаления комментария
    fun deleteComment(commentId: Int): Int {
        val comment = getCommentById(commentId)
        if (comment != null) {
            comment.isDeleting = true
            return 1
        } else {
            return -1
        }
    }

    // Функция восстановления удаленного комментария
    fun restoreComment(commentId: Int): Int {
        val comment = getCommentById(commentId)
        if (comment != null) {
            comment.isDeleting = false
            return 1
        } else {
            return -1
        }
    }

    // Функция получения списка заметок друзей пользователя
    fun getFriendsNotes(userId: Int): List<Note> {
        val friendsNotes = notes.filter { it.userId == userId && !it.isDeleting }
        return friendsNotes.toList()
    }

    // Вспомогательная функция для получения текущего id пользователя (здесь может быть реализация получения id из сессии или авторизации)
    private fun getCurrentUserId(): Int {
        return 1
    }

    // Вспомогательная функция для получения заметки по её id
    private fun getNoteById(noteId: Int): Note? {
        return notes.find { it.id == noteId && !it.isDeleting }
    }

    // Вспомогательная функция для получения комментария по его id
    fun getCommentById(commentId: Int): Comment? {
        return comments.find { it.id == commentId }
    }

}

// Пример использования всех возможных функций
fun main() {
    val noteService = NoteService()

    // Добавление новой заметки
    noteService.add("Заметка  1", "Текст заметки 1")
    noteService.add("Заметка  2", "Текст заметки 2")
    noteService.add("Заметка  3", "Текст заметки 2")
    noteService.add("Заметка  4", "Текст заметки 2")
    noteService.add("Заметка  5", "Текст заметки 2")
    noteService.add("Заметка  6", "Текст заметки 2")

    println("Получение заметки по её id")
    println(noteService.getById(1))
    println()

    println(" Получение списка заметок пользователя по id 2 3 4")
    val userNotes = noteService.get("2 4 3", 0, 10, 1)
    for (note in userNotes) {
        println(note)
    }
    println()

    println("Получение списка заметок пользователя по count")
    val userNotes1 = noteService.get("0", 0, 10, 1)
    for (note in userNotes1) {
        println(note)
    }
    println()
    println("Удаление заметки 1")
    noteService.delete(1)
    println(noteService.getById(1)?.title)
    println(noteService.getById(2)?.title)
    println()

    println("Создание нового комментария к заметке")
    val comm1 = noteService.createComment(2, "комментарий 1")
    val comm2 = noteService.createComment(2, "комментарий 2")
    println("$comm1 \n $comm2")
    println()

    println("Редактирование комментария")
    val editComment = noteService.editComment(1, "Отредактированный комментарий")
    println(editComment)
    println()

    println("Удаление комментария")
    noteService.deleteComment(1)
    println(noteService.getCommentById(1)?.isDeleting)
    println()

    println("Восстановление удаленного комментария")
    noteService.restoreComment(1)
    println(noteService.getCommentById(1)?.message)
    println()

    println("Редактирование заметки")
    val editedNote = noteService.edit(2, "Отредактированная заметка2", "Новый текст")
        println(editedNote)
    println()

    println("Получение списка комментариев к заметке")
    val comments = noteService.getComments(2)
    for (comment in comments) {
        println(comment)
    }
    println()

    println("Получение списка заметок друга")
    val friendsNotes = noteService.getFriendsNotes(1)
    for (notes in friendsNotes) {
        println(notes)
    }


}