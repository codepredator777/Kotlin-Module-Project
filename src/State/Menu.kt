enum class MenuList {
    CreateFile,
    Open,
    Back;

    companion object {
        fun selectMenu(menu: MenuList, action: () -> Unit) {
            when(menu) {
                CreateFile -> println("Пункт меню - создание файла\n")
                Open -> println("Происходит открытие файла\n")
                Back -> println("Выход из текущего меню\n")
            }

            action.invoke()
        }
    }
}



