import org.junit.Assert.assertEquals
import org.junit.Test

class NoteServiceTest {

    @Test
    fun add() {
        // Добавляем новую заметку
        val noteService = NoteService()
        val note = noteService.add("Заметка 1", "Текст заметки 1")
        assertEquals(note.title, "Заметка 1")
    }

    @Test
    fun createComment() {
        // Добавляем новую заметку
        val noteService = NoteService()
        noteService.add("Заметка 1", "Текст заметки 1")
        // Добавляем новый комментарйи
        val comment = noteService.createComment(1, "комментарий 1")
        assertEquals(comment?.message,"комментарий 1" )
    }

    @Test
    fun delete() {
        // Добавляем новую заметку
        val noteService = NoteService()
        noteService.add("Заметка 1", "Текст заметки 1")
        noteService.delete(1)
        assertEquals(noteService.getById(1),null)
    }

    @Test
    fun edit() {
        // Добавляем новую заметку
        val noteService = NoteService()
        noteService.add("Заметка 1", "Текст заметки 1")
        val editedNote = noteService.edit(1, "Отредактированная заметка1", "Новый текст")
        assertEquals(editedNote?.title,"Отредактированная заметка1")

    }

    @Test
    fun editComment() {
        // Добавляем новую заметку
        val noteService = NoteService()
        noteService.add("Заметка 1", "Текст заметки 1")
        // Добавляем новый комментарйи
        noteService.createComment(1, "комментарий 1")
        // Редактируем комментарий
        val editComment = noteService.editComment(1, "Отредактированный комментарий")
        assertEquals(editComment?.message,"Отредактированный комментарий")
    }

    @Test
    fun getCount() {
        // Добавляем новую заметку
        val noteService = NoteService()
        noteService.add("Заметка 1", "Текст заметки 1")
        val userNotes1 = noteService.get("0", 0, 10, 1)
        assertEquals(userNotes1[0].title,"Заметка 1")
    }
    @Test
    fun getNoteId() {
        // Добавляем новую заметку
        val noteService = NoteService()
        noteService.add("Заметка 1", "Текст заметки 1")
        noteService.add("Заметка 2", "Текст заметки 2")
        val userNotes1 = noteService.get("2", 0, 0, 1)
        assertEquals(userNotes1[0].title,"Заметка 2")
    }

    @Test
    fun getById() {
        // Добавляем новую заметку
        val noteService = NoteService()
        noteService.add("Заметка 1", "Текст заметки 1")
        val note = noteService.getById(1)
        assertEquals(note?.title,"Заметка 1")

    }

    @Test
    fun getComments() {
        // Добавляем новую заметку
        val noteService = NoteService()
        noteService.add("Заметка 1", "Текст заметки 1")
        // Добавляем новый комментарйи
        noteService.createComment(1, "комментарий 1")
        assertEquals(noteService.getCommentById(1)?.message,"комментарий 1" )

    }

    @Test
    fun deleteComment() {
        // Добавляем новую заметку
        val noteService = NoteService()
        noteService.add("Заметка 1", "Текст заметки 1")
        // Добавляем новый комментарйи
        noteService.createComment(1, "комментарий 1")
        // Удаляем комментарий
        noteService.deleteComment(1)
        assertEquals(noteService.getCommentById(1)?.isDeleting, true)
    }

    @Test
    fun restoreComment() {
        // Добавляем новую заметку
        val noteService = NoteService()
        noteService.add("Заметка 1", "Текст заметки 1")
        // Добавляем новый комментарйи
        noteService.createComment(1, "комментарий 1")
        // Удаляем комментарий
        noteService.deleteComment(1)
        // Возвращаем комментарий
        noteService.restoreComment(1)
        assertEquals(noteService.getCommentById(1)?.message,"комментарий 1" )

    }

    @Test
    fun getFriendsNotes() {
        // Добавляем новую заметку
        val noteService = NoteService()
        noteService.add("Заметка 1", "Текст заметки 1")
        assertEquals(noteService.getFriendsNotes(1).elementAt(0).title,"Заметка 1")

    }

    @Test
    fun getCommentById() {
        // Добавляем новую заметку
        val noteService = NoteService()
        noteService.add("Заметка 1", "Текст заметки 1")
        // Добавляем новый комментарйи
        noteService.createComment(1, "комментарий 1")
        // Поиск комментария
        assertEquals(noteService.getCommentById(1)?.message,"комментарий 1" )
    }
}