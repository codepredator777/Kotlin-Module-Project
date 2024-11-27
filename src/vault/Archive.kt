class Archive(val name: String) {
    private val notesCollection = HashMap<String, Note>()

    val notes: Map<String, String>
        get() = notesCollection.mapValues { it.value.name }

    fun addNote(name: String, text: String) {
        val note = Note(name, text)
        println("заметка \"$name\" успешно создана.\n")
        this.notesCollection["${notesCollection.count() + 1}"] = note
    }

    fun readNote(name: String): Note? {
        println("идет поиск заметки...")
        notesCollection.forEach {
            if(it.key == name) {
                println("заметка найдена...")
                return it.value
            }
        }
        println("заметка не найдена...")
        return null
    }

    override fun toString(): String {
        return notesCollection.entries.joinToString(separator = "\n") {"${it.key}. ${it.value.name}"}
    }
}
