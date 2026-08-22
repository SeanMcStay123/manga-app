package controllers

import models.Character
import org.junit.jupiter.api.AfterEach // runs after every test, used to reset/clear test data
import org.junit.jupiter.api.Assertions.assertTrue // checks something is true
import org.junit.jupiter.api.BeforeEach // sets up data before each test
import org.junit.jupiter.api.Test // marks a function as a test that JUnit will run
import org.junit.jupiter.api.Assertions.assertEquals // checks two values match
import org.junit.jupiter.api.Nested // groups related tests together
import org.junit.jupiter.api.Assertions.assertNull // checks that a value is null, e.g. deleting/updating something that doesn't exist
import org.junit.jupiter.api.Assertions.assertFalse // checks something is false
import persistence.JSONSerializer
import persistence.XMLSerializer
import java.io.File

class CharacterAPITest {

    private var naruto: Character? = null
    private var luffy: Character? = null
    private var goku: Character? = null
    private var edward: Character? = null
    private var eren: Character? = null
    private var populatedCharacters: CharacterAPI? = CharacterAPI(XMLSerializer(File("characters.xml")))
    private var emptyCharacters: CharacterAPI? = CharacterAPI(XMLSerializer(File("empty-characters.xml")))

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
    fun `listActiveCharacters returns Active Characters when ArrayList has active characters stored`() { // proves the filter actually works and list has 5 characters, but none archived so the message should still show
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

    @Test
    fun `listCharactersBySelectedRating returns No Characters message when no characters match that rating`() {
        assertEquals(0, populatedCharacters!!.numberOfCharactersByRating(1))
        assertTrue(populatedCharacters!!.listCharactersBySelectedRating(1).lowercase().contains("no characters"))
    }

    @Test
    fun `listCharactersBySelectedRating returns only Characters matching that rating`() {
        assertEquals(2, populatedCharacters!!.numberOfCharactersByRating(5))
        val ratingString = populatedCharacters!!.listCharactersBySelectedRating(5).lowercase()
        assertTrue(ratingString.contains("naruto uzumaki"))
        assertTrue(ratingString.contains("son goku"))
        assertTrue(!ratingString.contains("eren yeager"))
    }

    @Test
    fun `findFavoriteCharacter returns null when list is empty`() {
        assertEquals(null, emptyCharacters!!.findFavoriteCharacter())
    }

    @Test
    fun `findFavoriteCharacter returns a character with the highest rating`() {
        val favorite = populatedCharacters!!.findFavoriteCharacter()
        assertEquals(5, favorite!!.characterRating)
    }

    @Test
    fun `listCharactersByMangaSeries returns No Characters message when no series matches`() {
        assertEquals(0, populatedCharacters!!.numberOfCharactersInSeries("Bleach"))
        assertTrue(populatedCharacters!!.listCharactersByMangaSeries("Bleach").lowercase().contains("no characters found"))
    }

    @Test
    fun `listCharactersByMangaSeries returns only Characters from that series`() {
        assertEquals(1, populatedCharacters!!.numberOfCharactersInSeries("Naruto"))
        val seriesString = populatedCharacters!!.listCharactersByMangaSeries("Naruto").lowercase()
        assertTrue(seriesString.contains("naruto uzumaki"))
        assertTrue(!seriesString.contains("son goku"))
    }

    @Nested // groups both "delete" tests together in the test results terminal panel
    inner class DeleteCharacters {

        @Test
        fun `deleting a Character that does not exist, returns null`() {
            assertNull(emptyCharacters!!.deleteCharacter(0))
            assertNull(populatedCharacters!!.deleteCharacter(-1))
            assertNull(populatedCharacters!!.deleteCharacter(5))
        }

        @Test
        fun `deleting a character that exists deletes and returns deleted object`() {
            assertEquals(5, populatedCharacters!!.numberOfCharacters())
            assertEquals(eren, populatedCharacters!!.deleteCharacter(4))
            assertEquals(4, populatedCharacters!!.numberOfCharacters())
            assertEquals(naruto, populatedCharacters!!.deleteCharacter(0))
            assertEquals(3, populatedCharacters!!.numberOfCharacters())
        }
    }

    @Nested
    inner class UpdateCharacters {
        @Test
        fun `updating a character that does not exist returns false`(){
            assertFalse(populatedCharacters!!.updateCharacter(6, Character("Updating Character", 2, "Bleach", false)))
            assertFalse(populatedCharacters!!.updateCharacter(-1, Character("Updating Character", 2, "Bleach", false)))
            assertFalse(emptyCharacters!!.updateCharacter(0, Character("Updating Character", 2, "Bleach", false)))
        }

        @Test
        fun `updating a character that exists returns true and updates`() {
            //check character 5 (index 4) exists and check the contents
            assertEquals(eren, populatedCharacters!!.findCharacter(4))
            assertEquals("Eren Yeager", populatedCharacters!!.findCharacter(4)!!.characterName)
            assertEquals(3, populatedCharacters!!.findCharacter(4)!!.characterRating)
            assertEquals("Attack on Titan", populatedCharacters!!.findCharacter(4)!!.mangaSeries)

            //update character 5 with new information and ensure contents updated successfully
            assertTrue(populatedCharacters!!.updateCharacter(4, Character("Updating Character", 2, "Bleach", false)))
            assertEquals("Updating Character", populatedCharacters!!.findCharacter(4)!!.characterName)
            assertEquals(2, populatedCharacters!!.findCharacter(4)!!.characterRating)
            assertEquals("Bleach", populatedCharacters!!.findCharacter(4)!!.mangaSeries)
        }
    }

    @Nested
    inner class PersistenceTests {

        @Test
        fun `saving and loading an empty collection in XML doesn't crash app`() {
            // Saving an empty characters.xml file.
            val storingCharacters = CharacterAPI(XMLSerializer(File("characters.xml")))
            storingCharacters.store()

            //Loading the empty characters.xml file into a new object
            val loadedCharacters = CharacterAPI(XMLSerializer(File("characters.xml")))
            loadedCharacters.load()

            //Comparing the source of the characters (storingCharacters) with the XML loaded characters (loadedCharacters)
            assertEquals(0, storingCharacters.numberOfCharacters())
            assertEquals(0, loadedCharacters.numberOfCharacters())
            assertEquals(storingCharacters.numberOfCharacters(), loadedCharacters.numberOfCharacters())
        }

        @Test
        fun `saving and loading a loaded collection in XML doesn't lose data`() {
            // Storing 3 characters to the characters.xml file.
            val storingCharacters = CharacterAPI(XMLSerializer(File("characters.xml")))
            storingCharacters.add(naruto!!)
            storingCharacters.add(luffy!!)
            storingCharacters.add(goku!!)
            storingCharacters.store()

            //Loading characters.xml into a different collection
            val loadedCharacters = CharacterAPI(XMLSerializer(File("characters.xml")))
            loadedCharacters.load()

            //Comparing the source of the characters (storingCharacters) with the XML loaded characters (loadedCharacters)
            assertEquals(3, storingCharacters.numberOfCharacters())
            assertEquals(3, loadedCharacters.numberOfCharacters())
            assertEquals(storingCharacters.numberOfCharacters(), loadedCharacters.numberOfCharacters())
            assertEquals(storingCharacters.findCharacter(0), loadedCharacters.findCharacter(0))
            assertEquals(storingCharacters.findCharacter(1), loadedCharacters.findCharacter(1))
            assertEquals(storingCharacters.findCharacter(2), loadedCharacters.findCharacter(2))
        }

        @Test
        fun `saving and loading an empty collection in JSON doesn't crash app`() {
            // Saving an empty characters.json file.
            val storingCharacters = CharacterAPI(JSONSerializer(File("characters.json")))
            storingCharacters.store()

            //Loading the empty characters.json file into a new object
            val loadedCharacters = CharacterAPI(JSONSerializer(File("characters.json")))
            loadedCharacters.load()

            //Comparing the source of the characters (storingCharacters) with the JSON loaded characters (loadedCharacters)
            assertEquals(0, storingCharacters.numberOfCharacters())
            assertEquals(0, loadedCharacters.numberOfCharacters())
            assertEquals(storingCharacters.numberOfCharacters(), loadedCharacters.numberOfCharacters())
        }

        @Test
        fun `saving and loading a loaded collection in JSON doesn't lose data`() {
            // Storing 3 characters to the characters.json file.
            val storingCharacters = CharacterAPI(JSONSerializer(File("characters.json")))
            storingCharacters.add(naruto!!)
            storingCharacters.add(luffy!!)
            storingCharacters.add(goku!!)
            storingCharacters.store()

            //Loading characters.json into a different collection
            val loadedCharacters = CharacterAPI(JSONSerializer(File("characters.json")))
            loadedCharacters.load()

            //Comparing the source of the characters (storingCharacters) with the JSON loaded characters (loadedCharacters)
            assertEquals(3, storingCharacters.numberOfCharacters())
            assertEquals(3, loadedCharacters.numberOfCharacters())
            assertEquals(storingCharacters.numberOfCharacters(), loadedCharacters.numberOfCharacters())
            assertEquals(storingCharacters.findCharacter(0), loadedCharacters.findCharacter(0))
            assertEquals(storingCharacters.findCharacter(1), loadedCharacters.findCharacter(1))
            assertEquals(storingCharacters.findCharacter(2), loadedCharacters.findCharacter(2))
        }
    }

    @Nested
    inner class ArchiveCharacters { // copied the same layout as other nested functions but adapted it to the new archive function
        @Test
        fun `archiving a character that does not exist returns false`() {
            assertFalse(populatedCharacters!!.archiveCharacter(6))
            assertFalse(populatedCharacters!!.archiveCharacter(-1))
            assertFalse(emptyCharacters!!.archiveCharacter(0))
        }

        @Test
        fun `archiving an active character that exists returns true and archives it`() {
            assertFalse(populatedCharacters!!.findCharacter(0)!!.isCharacterArchived)
            assertTrue(populatedCharacters!!.archiveCharacter(0))
            assertTrue(populatedCharacters!!.findCharacter(0)!!.isCharacterArchived)
        }

        @Test
        fun `archiving a character that is already archived returns false`() {
            populatedCharacters!!.archiveCharacter(0) // archive it first
            assertFalse(populatedCharacters!!.archiveCharacter(0)) // second attempt should fail
        }
    }

        @Nested
        inner class CountingMethods { // new tests added following the lambdas section

            @Test
            fun numberOfCharactersCalculatedCorrectly() {
                assertEquals(5, populatedCharacters!!.numberOfCharacters())
                assertEquals(0, emptyCharacters!!.numberOfCharacters())
            }

            @Test
            fun numberOfArchivedCharactersCalculatedCorrectly() {
                assertEquals(0, populatedCharacters!!.numberOfArchivedCharacters())
                assertEquals(0, emptyCharacters!!.numberOfArchivedCharacters())
            }

            @Test
            fun numberOfActiveCharactersCalculatedCorrectly() {
                assertEquals(5, populatedCharacters!!.numberOfActiveCharacters())
                assertEquals(0, emptyCharacters!!.numberOfActiveCharacters())
            }

            @Test
            fun numberOfCharactersByRatingCalculatedCorrectly() {
                assertEquals(0, populatedCharacters!!.numberOfCharactersByRating(1))
                assertEquals(0, populatedCharacters!!.numberOfCharactersByRating(2))
                assertEquals(1, populatedCharacters!!.numberOfCharactersByRating(3))
                assertEquals(2, populatedCharacters!!.numberOfCharactersByRating(4))
                assertEquals(2, populatedCharacters!!.numberOfCharactersByRating(5))
                assertEquals(0, emptyCharacters!!.numberOfCharactersByRating(1))
            }
        }
    @Nested
    inner class SearchMethods {

        @Test
        fun `search returns no characters when no name matches`() {
            assertTrue(populatedCharacters!!.searchByCharacterName("zzz").isEmpty()) // no character has this name, should be empty
            assertTrue(emptyCharacters!!.searchByCharacterName("naruto").isEmpty()) // empty list, nothing to find
        }

        @Test
        fun `search returns matching characters when full name is given`() {
            val result = populatedCharacters!!.searchByCharacterName("Naruto Uzumaki").lowercase()
            assertTrue(result.contains("naruto uzumaki")) // full name match should work
        }

        @Test
        fun `search returns matching characters when partial name is given`() {
            val result = populatedCharacters!!.searchByCharacterName("goku").lowercase()
            assertTrue(result.contains("son goku")) // partial name match, since goku's full name is "Son Goku"
        }

        @Test
        fun `search is case insensitive`() {
            val result = populatedCharacters!!.searchByCharacterName("EDWARD").lowercase()
            assertTrue(result.contains("edward elric")) // uppercase search should still find a lowercase-stored name
        }
    }
    }