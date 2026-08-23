package utils

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertFalse

class InputValidationTest {

    @Test
    fun `isValidRating returns true for ratings between 1 and 5`() {
        assertTrue(isValidRating(1))
        assertTrue(isValidRating(3))
        assertTrue(isValidRating(5))
    }

    @Test
    fun `isValidRating returns false for ratings outside 1 to 5`() {
        assertFalse(isValidRating(0))
        assertFalse(isValidRating(6))
        assertFalse(isValidRating(-1))
    }

    @Test
    fun `isValidDescription returns false for blank or empty descriptions`() {
        assertFalse(isValidDescription(""))
        assertFalse(isValidDescription("   "))
    }

    @Test
    fun `isValidDescription returns true for a non-blank description`() {
        assertTrue(isValidDescription("A ninja who dreams of becoming Hokage"))
    }

    @Test
    fun `isValidPowerLevel returns false for negative values`() {
        assertFalse(isValidPowerLevel(-1.0))
        assertFalse(isValidPowerLevel(-100.5))
    }

    @Test
    fun `isValidPowerLevel returns true for zero or positive values`() {
        assertTrue(isValidPowerLevel(0.0))
        assertTrue(isValidPowerLevel(9500.5))
    }
}