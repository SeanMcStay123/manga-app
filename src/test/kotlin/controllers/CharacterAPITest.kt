package controllers

import models.Character
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals // checks two values match, e.g. count or object comparisons in tests below
import org.junit.jupiter.api.Nested // lets you group related tests into inner classes for cleaner test output

class CharacterAPITest {

    private var naruto: Character? = null
    private var luffy: Character? = null
    private var goku: Character? = null
    private var edward: Character? = null
    private var eren: Character? = null
    private var populatedCharacters: CharacterAPI? = CharacterAPI()
    private var emptyCharacters: CharacterAPI? = CharacterAPI()

    @BeforeEach
    fun setup() { // runs before every test, resets test data
        naruto = Character("Naruto Uzumaki", 5, "Naruto", false)
        luffy = Character("Monkey D. Luffy", 4, "One Piece", false)
        goku = Character("Son Goku", 5, "Dragon Ball", false)
        edward = Character("Edward Elric", 4, "Fullmetal Alchemist", false)
        eren = Character("Eren Yeager", 3, "Attack on Titan", false)

        //adding 5 Characters to the characters api
        populatedCharacters!!.add(naruto!!)
        populatedCharacters!!.add(luffy!!)
        populatedCharacters!!.add(goku!!)
        populatedCharacters!!.add(edward!!)
        populatedCharacters!!.add(eren!!)
    }

    @AfterEach
    fun tearDown() {
        naruto = null
        luffy = null
        goku = null
        edward = null
        eren = null
        populatedCharacters = null
        emptyCharacters = null
    }

    @Nested // groups both "add" tests together in the test results tree
    inner class AddCharacters {
        @Test
        fun `adding a Character to a populated list adds to ArrayList`() {
            val newCharacter = Character("Levi Ackerman", 5, "Attack on Titan", false)
            assertEquals(5, populatedCharacters!!.numberOfCharacters())
            assertTrue(populatedCharacters!!.add(newCharacter))
            assertEquals(6, populatedCharacters!!.numberOfCharacters())
            assertEquals(
                newCharacter,
                populatedCharacters!!.findCharacter(populatedCharacters!!.numberOfCharacters() - 1)
            )
        }

        @Test
        fun `adding a Character to an empty list adds to ArrayList`() {
            val newCharacter = Character("Levi Ackerman", 5, "Attack on Titan", false)
            assertEquals(0, emptyCharacters!!.numberOfCharacters())
            assertTrue(emptyCharacters!!.add(newCharacter))
            assertEquals(1, emptyCharacters!!.numberOfCharacters())
            assertEquals(newCharacter, emptyCharacters!!.findCharacter(emptyCharacters!!.numberOfCharacters() - 1))
        }

    }

    @Nested // groups both "listAllCharacters" tests together in the test results tree
    inner class ListCharacters { // "inner" keeps access to setup()'s properties above and not just class

        @Test
        fun `listAllCharacters returns No Characters Stored message when ArrayList is empty`() {
            assertEquals(0, emptyCharacters!!.numberOfCharacters())
            assertTrue(emptyCharacters!!.listAllCharacters().lowercase().contains("no characters"))
        }

        @Test
        fun `listAllCharacters returns Characters when ArrayList has characters stored`() {
            assertEquals(5, populatedCharacters!!.numberOfCharacters())
            val charactersString = populatedCharacters!!.listAllCharacters().lowercase()
            assertTrue(charactersString.contains("naruto uzumaki"))
            assertTrue(charactersString.contains("monkey d. luffy"))
            assertTrue(charactersString.contains("son goku"))
            assertTrue(charactersString.contains("edward elric"))
            assertTrue(charactersString.contains("eren yeager"))
        }

    }
    @Test
    fun `listActiveCharacters returns No Active Characters Stored message when ArrayList is empty`() { // confirms the empty-list edge case for the active filter
        assertEquals(0, emptyCharacters!!.numberOfActiveCharacters())
        assertTrue(emptyCharacters!!.listActiveCharacters().lowercase().contains("no active characters"))
    }
    @Test
    fun `listActiveCharacters returns Active Characters when ArrayList has active characters stored`() {  // proves the filter actually works and list has 5 characters, but none archived so the message should still show
        assertEquals(5, populatedCharacters!!.numberOfActiveCharacters())
        val activeString = populatedCharacters!!.listActiveCharacters().lowercase()
        assertTrue(activeString.contains("naruto uzumaki"))
        assertTrue(activeString.contains("eren yeager"))
    }
    @Test
    fun `listArchivedCharacters returns No Archived Characters Stored message when ArrayList is empty`() {
        assertEquals(0, emptyCharacters!!.numberOfArchivedCharacters())
        assertTrue(emptyCharacters!!.listArchivedCharacters().lowercase().contains("no archived characters"))
    }
    @Test
    fun `listArchivedCharacters returns No Archived Characters Stored message when populated list has no archived characters`() {
        assertEquals(0, populatedCharacters!!.numberOfArchivedCharacters())
        assertTrue(populatedCharacters!!.listArchivedCharacters().lowercase().contains("no archived characters"))
    }
}