data class Note(val name: String, val text: String) {
    override fun toString(): String {
        return "\nНазвание заметки - $name \nТекст заметки: \n$text\n"
    }
}
