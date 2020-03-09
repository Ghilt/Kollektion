package tests

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import reduceBasedOnNeighbors
import reduceBasedOnNeighborsCyclic

internal class ReductionKollektionListTest {

    @Test
    fun `reduceBasedOnNeighbors reduces to single value`() {
        val equalToNextValue = listOf(1, 1, 1, 2, 2, 2, 3, 3, 3, 3, 3)
            .reduceBasedOnNeighbors { prev, v, next -> v == prev && v == next }

        assertEquals(listOf(3), equalToNextValue)
    }

    @Test
    fun `reduceBasedOnNeighbors which should reduce to single value is stopped by max itr limit`() {
        val equalToNextValue = listOf(1, 1, 1, 2, 2, 2, 3, 3, 3, 3, 3)
            .reduceBasedOnNeighborsCyclic(maxIterations = 1) { prev, v, next -> v == prev && v == next }

        assertEquals(listOf(1, 2, 3, 3, 3), equalToNextValue)
    }

    @Test
    fun `reduceBasedOnNeighbors reduces to two values`() {
        val equalToNextValue = listOf(1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 3)
            .reduceBasedOnNeighbors(
                finalSize = 2,
                leftEdgeValue = 1,
                rightEdgeValue = 3
            ) { prev, v, next -> v == prev && v == next }

        assertEquals(listOf(1, 3), equalToNextValue)
    }

    @Test
    fun `reduceBasedOnNeighborsCyclic reduces to single value`() {
        val equalToNextValue = listOf(1, 1, 1, 2, 2, 2, 3, 3, 3, 3, 3)
            .reduceBasedOnNeighborsCyclic { prev, v, next -> v == prev && v == next }

        assertEquals(listOf(3), equalToNextValue)
    }

    @Test
    fun `reduceBasedOnNeighborsCyclic which should reduce to single value is stopped by max itr limit`() {
        val equalToNextValue = listOf(1, 1, 1, 2, 2, 2, 3, 3, 3, 3, 3)
            .reduceBasedOnNeighborsCyclic(maxIterations = 1) { prev, v, next -> v == prev && v == next }

        assertEquals(listOf(1, 2, 3, 3, 3), equalToNextValue)
    }
}