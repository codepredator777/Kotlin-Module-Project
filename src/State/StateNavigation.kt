class StateNavigation(private val stateChanged: (AppState) -> Unit) {
    private var currentState: AppState = AppState.Notes
    private var previousState: AppState = AppState.Notes

    fun changeState(newState: AppState) {
        previousState = currentState
        currentState = newState
        stateChanged(currentState)
    }

    fun getPrevious(): AppState {
        return previousState
    }

    fun checkCurrent(state: AppState): Boolean {
        return currentState == state
    }

}