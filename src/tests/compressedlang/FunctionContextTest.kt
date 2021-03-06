package tests.compressedlang

import compressedlang.*
import compressedlang.fncs.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class FunctionContextTest {

    @BeforeAll
    fun setupProgramEnvironment() {
        Du81ProgramEnvironment.initialize()
    }

    @Test
    fun `functionContext creates inner functions when it needs new inner contexts`() {
        val functionContext = FunctionContext(listOf("123".toList())).apply { put(currentListNilad) }
        functionContext.put(filterDyad)
        functionContext.put(startInnerFunctionControlFlow)
        functionContext.put(currentListNilad)
        functionContext.put(filterDyad)
        functionContext.put(startInnerFunctionControlFlow)
        functionContext.put(currentListNilad)
        functionContext.put(filterDyad)
        functionContext.put(startInnerFunctionControlFlow)
        functionContext.put(currentListNilad)
        functionContext.put(filterDyad)

        assertEquals("_F(_F(_F(_F)))", functionContext.diagnosticsString())
    }

    @Test
    fun `functionContext creates inner function`() {
        val functionContext = FunctionContext(listOf("123".toList())).apply { put(currentListNilad) }
        functionContext.put(filterDyad)

        functionContext.put(startInnerFunctionControlFlow)
        functionContext.put(currentListNilad)
        functionContext.put(filterDyad)
        functionContext.put(largerThanDyad)
        functionContext.put(1.toResolvedFunction())
        functionContext.put(endInnerFunctionControlFlow)

        functionContext.put(lengthMonad)
        functionContext.put(largerThanDyad)
        functionContext.put(2.toResolvedFunction())

        //             F_ F_ F_ F>1 L>2 L>3 L>4
        assertEquals("_F(_F>1)l>2", functionContext.diagnosticsString())
    }

    @Test
    fun `functionContext creates two inner functions`() {
        val functionContext = FunctionContext(listOf(listOf(1, 2, 3).toList())).apply { put(currentListNilad) }
        functionContext.put(filterDyad)

        functionContext.put(startInnerFunctionControlFlow)
        functionContext.put(currentListNilad)
        functionContext.put(filterDyad)

        functionContext.put(startInnerFunctionControlFlow)
        functionContext.put(currentListNilad)
        functionContext.put(filterDyad)
        functionContext.put(largerThanDyad)
        functionContext.put(1.toResolvedFunction())
        functionContext.put(endInnerFunctionControlFlow)

        functionContext.put(lengthMonad)
        functionContext.put(largerThanDyad)
        functionContext.put(2.toResolvedFunction())
        functionContext.put(endInnerFunctionControlFlow)

        functionContext.put(lengthMonad)
        functionContext.put(largerThanDyad)
        functionContext.put(3.toResolvedFunction())

        //             F_ F_ F_ F>1 L>2 L>3 L>4
        assertEquals("_F(_F(_F>1)l>2)l>3", functionContext.diagnosticsString())
    }

    @Test
    fun `functionContext creates inner functions`() {
        val functionContext = FunctionContext(listOf("123".toList())).apply { put(currentListNilad) }
        functionContext.put(filterDyad)

        functionContext.put(startInnerFunctionControlFlow)
        functionContext.put(currentListNilad)
        functionContext.put(filterDyad)

        functionContext.put(startInnerFunctionControlFlow)
        functionContext.put(currentListNilad)
        functionContext.put(filterDyad)

        functionContext.put(startInnerFunctionControlFlow)
        functionContext.put(currentListNilad)
        functionContext.put(filterDyad)
        functionContext.put(largerThanDyad)
        functionContext.put(1.toResolvedFunction())
        functionContext.put(endInnerFunctionControlFlow)

        functionContext.put(lengthMonad)
        functionContext.put(largerThanDyad)
        functionContext.put(2.toResolvedFunction())
        functionContext.put(endInnerFunctionControlFlow)

        functionContext.put(lengthMonad)
        functionContext.put(largerThanDyad)
        functionContext.put(3.toResolvedFunction())
        functionContext.put(endInnerFunctionControlFlow)

        functionContext.put(lengthMonad)
        functionContext.put(largerThanDyad)
        functionContext.put(4.toResolvedFunction())

        //             F_ F_ F_ F>1 L>2 L>3 L>4
        assertEquals("_F(_F(_F(_F>1)l>2)l>3)l>4", functionContext.diagnosticsString())
    }

    @Test
    fun `functionContext creates inner functions and sequential functions`() {
        val functionContext = FunctionContext(listOf("123".toList())).apply { put(currentListNilad) }
        functionContext.put(filterDyad)

        functionContext.put(startInnerFunctionControlFlow)
        functionContext.put(currentListNilad)
        functionContext.put(filterDyad)

        functionContext.put(largerThanDyad)
        functionContext.put(1.toResolvedFunction())

        functionContext.put(lengthMonad)
        functionContext.put(largerThanDyad)
        functionContext.put(2.toResolvedFunction())

        val willAcceptContextCreator = functionContext.willAccept(filterDyad)

        assertEquals(false, willAcceptContextCreator)
    }

    @Test
    fun `execute filter on index yields filtered list`() {
        val functionContext = FunctionContext(listOf("abcde".toList())).apply { put(currentListNilad) }
        functionContext.put(filterDyad)
        functionContext.put(largerThanDyad)
        functionContext.put(1.toResolvedFunction())

        val list = functionContext.execute(-1)

        assertEquals("_F>1", functionContext.diagnosticsString())
        assertEquals(TYPE.LIST_TYPE, list.type)
        assertEquals("c, d, e", (list.value as List<*>).joinToString())
    }
}
