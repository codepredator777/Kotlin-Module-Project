class ArchiveVault() {
    private val vaultForArchives = HashMap<String, Archive>()

    val archives: Map<String, String>
        get() = vaultForArchives.mapValues { it.value.name }

    fun createArchive(name: String) {
        println("\nАрхив \"${name.uppercase()}\" создан.\n")
        val archive = Archive(name)
        vaultForArchives["${vaultForArchives.count() + 1}"] = archive
    }

    fun openArchive(name: String): Archive? {
        println("\nидет поиск архива...\n")
        vaultForArchives.forEach {
            if (it.key == name) {
                println("Архив \"${it.value.name}\" найден!\n")
                return it.value
            }
        }
        println("Архив $name не найден.")
        return null
    }

    override fun toString(): String {
        return vaultForArchives.entries.joinToString(separator = "\n") {"${it.key}. ${it.value.name}"}
    }

}
