package controllers

import models.Character
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals // checks two values match, e.g. count or object comparisons in tests below

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

    @Test
    fun `adding a Character to a populated list adds to ArrayList`() {
        val newCharacter = Character("Levi Ackerman", 5, "Attack on Titan", false)
        assertEquals(5, populatedCharacters!!.numberOfCharacters())
        assertTrue(populatedCharacters!!.add(newCharacter))
        assertEquals(6, populatedCharacters!!.numberOfCharacters())
        assertEquals(newCharacter, populatedCharacters!!.findCharacter(populatedCharacters!!.numberOfCharacters() - 1))
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