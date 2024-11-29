import java.util.Scanner
import kotlin.system.exitProcess


class Navigation(archive: ArchiveVault) {
    private val scanner = Scanner(System.`in`)
    private var isWorking = true
    private val _archive = archive
    private var currentArchive: Archive? = null

    private val stateCounter: Int
        get() = if (stateManager.checkCurrent(AppState.Notes)) {
            currentArchive?.notes?.count()?.plus(1) ?: 1
        } else {
            _archive.archives.count().plus(1)
        }

    private var stateManager = StateNavigation { state ->
        when (state) {
            AppState.Vault -> {
                println("0. Создать архив")
                _archive.archives.forEach { (number, archive) ->
                    println("$number. $archive")
                }
                println("${_archive.archives.count().plus(1)}. Выход")
            }
            AppState.Notes -> {
                println("0.Создать заметку")
                currentArchive?.notes?.forEach { (number, note) ->
                    println("$number. $note")
                }
                println("${currentArchive?.notes?.count()?.plus(1)}. Назад")
            }
            AppState.exit -> {
                println("до свидания!")
                exitProcess(0)
            }
        }
    }

    fun start() {
        println("Выберите пункт меню:")
        stateManager.changeState(AppState.Vault)

        while (isWorking) {
            when (val searchingRequest = getValidInput()) {
                    "0" -> MenuList.selectMenu(MenuList.CreateFile) {
                        if (stateManager.checkCurrent(AppState.Vault)) {
                            createArchive()
                        } else if (stateManager.checkCurrent(AppState.Notes)) {
                            createNote()
                        }
                    }
                    "$stateCounter" -> MenuList.selectMenu(MenuList.Back) {
                        if (stateManager.checkCurrent(AppState.Vault)) {
                            stateManager.changeState(AppState.exit)
                        } else {
                            stateManager.changeState(AppState.Vault)
                        }
                    }
                    else -> {
                        val indexForSearching = searchingRequest.toInt()
                        if (stateManager.checkCurrent(AppState.Vault)) {
                            if (indexForSearching in 1.._archive.archives.count()) {
                                val archive = _archive.openArchive(indexForSearching.toString())
                                currentArchive = archive
                                stateManager.changeState(AppState.Notes)
                            }
                        } else if (stateManager.checkCurrent(AppState.Notes)) {
                            if (indexForSearching in 1..currentArchive?.notes?.count()!!) {
                                val note = currentArchive?.readNote(indexForSearching.toString())
                                println(note.toString())
                                stateManager.changeState(AppState.Notes)
                            }
                        }
                    }
                }
            }
    }

    fun getValidInput(): String {
        var validInput: String
        while (true) {
            validInput = scanner.nextLine().trim()
            if (validInput.isNotEmpty() && validInput.all { it.isDigit() }) {
                return validInput
            } else {
                println("Введите корректное число (не пустое и только цифры):")
            }
        }
    }

    private fun getInput(text: String): String {
        println(text)
        var input = scanner.nextLine()
        while(input.isEmpty()) {
            println("это поле не может быть пустым")
            println(text)
            input = scanner.nextLine()

        }
        return input
    }

    private fun createArchive() {
        val name = getInput("введите название архива...")
        _archive.createArchive(name)
        stateManager.changeState(AppState.Vault)
    }

    private fun createNote() {
        val newName = getInput("введите название заметки...")
        val newText = getInput("введите текст заметки...")

        currentArchive?.addNote(newName, newText)
        stateManager.changeState(AppState.Notes)
    }
}

enum class AppState {
    Vault,
    Notes,
    exit
}
