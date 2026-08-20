# Manga App

A console-based Kotlin application for managing a personal manga collection. Built while working through SETU's Software Development Tools module, reskinned from the standard "Notes App" coursework around a manga collection instead.

## Background

This project follows the module's Kotlin & JUnit labs step by step, adapting each stage of the original Notes App exercise to a manga-themed equivalent (characters instead of notes). It's a work in progress and grows with each lab.

## Features

- Menu-driven console interface with options to add, list, update, and delete a character
- Robust console input reading — retries on invalid Int, Double, Float, or Char input instead of crashing
- Structured logging via kotlin-logging and slf4j-simple

## Built With

- [Kotlin](https://kotlinlang.org/)
- Gradle (Kotlin DSL)
- [kotlin-logging-jvm](https://github.com/oshai/kotlin-logging)
- [slf4j-simple](https://www.slf4j.org/)

## Getting Started

1. Clone the repo: git clone https://github.com/SeanMcStay123/manga-app.git
2. Open the project folder in IntelliJ IDEA.
3. Run `Main.kt`.

## Status

Currently a work in progress. The menu options are functional but not yet connected to a real character collection — that's coming in the next stage of the module.
