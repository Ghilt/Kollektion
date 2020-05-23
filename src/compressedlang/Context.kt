package compressedlang

import compressedlang.fncs.Function
import compressedlang.fncs.ResolvedFunction
import compressedlang.fncs.currentListNilad

class Context(input: List<Any>) {

    private val targets: MutableList<List<Any>> = mutableListOf(input)
    private val functionContext = mutableListOf(FunctionContext(targets).apply { put(currentListNilad) })
    private val currentFunctionContext
        get() = functionContext[0]

    private val currentTarget
        get() = targets[0]

    fun prepareFor(function: Function): Boolean {
        return if (currentFunctionContext.willAccept(function)) {
            currentFunctionContext.put(function)
            true
        } else {
            currentFunctionContext.build()
            false
        }
    }

    fun isReadyForExecution() = currentFunctionContext.isBuilt

    fun endOfProgramReached() {
        currentFunctionContext.build()
        execute()
    }

    fun execute() {
        log("Du81, outer function ready for execution: ${currentFunctionContext.diagnosticsString()}")

        targets.add(0, wrapInListIfNeeded(currentFunctionContext.execute(-1)))
        functionContext.add(0, FunctionContext(targets).apply { put(currentListNilad) })
    }

    private fun wrapInListIfNeeded(result: ResolvedFunction): List<Any> {
        return if (result.type != TYPE.LIST_TYPE) {
            listOf(result.value)
        } else {
            @Suppress("UNCHECKED_CAST")
            result.value as List<Any>
        }
    }

    fun getResult(): List<List<Any>> = targets.toList()
}

// F>iF="hej"F<424