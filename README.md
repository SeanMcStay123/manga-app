# Manga App

A console-based Kotlin application for managing a personal manga collection. Built while working through SETU's Software Development Tools module, reskinned from the standard "Notes App" coursework around a manga collection instead.

## Background

This project follows the module's Kotlin & JUnit labs step by step, adapting each stage of the original Notes App exercise to a manga-themed equivalent (characters instead of notes). It's a work in progress and grows with each lab.

## Features

- Menu-driven console interface with options to add, list, update, delete, save, load, and archive a character
- A `Character` model (name, rating 1–5, manga series, archived status) backed by a `CharacterAPI` collection
- List submenu: list all characters, list only active characters, list only archived characters
- List and count characters by a selected rating (1–5)
- Find the favorite (highest-rated) character
- Search and count characters by manga series
- Update a character's details, or delete a character from the collection
- Archive an active character
- Persist the character collection to XML or JSON — switching format requires changing only one line
- Robust console input reading — retries on invalid Int, Double, Float, or Char input instead of crashing
- Structured logging via kotlin-logging and slf4j-simple
- JUnit 5 test suite covering the CharacterAPI collection, organized into nested test classes

## Built With

- [Kotlin](https://kotlinlang.org/)
- Gradle (Kotlin DSL)
- [kotlin-logging-jvm](https://github.com/oshai/kotlin-logging)
- [slf4j-simple](https://www.slf4j.org/)
- [JUnit 5](https://junit.org/junit5/)
- [XStream](https://x-stream.github.io/) (XML/JSON persistence)
- [Jettison](https://github.com/codehaus/jettison) (JSON persistence)

## Development Workflow

This project follows GitHub Flow: features are tracked as GitHub Issues, developed on feature branches, and merged via pull requests before the branch is deleted. See the repo's closed Issues and closed pull requests for the full history.

## Getting Started

1. Clone the repo: https://github.com/SeanMcStay123/manga-app.git
2. Open the project folder in IntelliJ IDEA.
3. Run `Main.kt`.

## Releases

- **V1.0** — initial menu skeleton, logging, robust console input handling
- **V2.0** — Character model and collection, active/archived/rating/series filtering, JUnit test suite
- **V3.0** — update, delete, and archive a character, list submenu, and persistence to XML/JSON

## Status

V3.0 complete. All menu options (add, list, update, delete, save, load, archive) are fully wired to the character collection and covered by JUnit tests.
