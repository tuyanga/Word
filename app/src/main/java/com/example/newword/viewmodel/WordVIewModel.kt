package com.example.newword.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newword.data.Word
import com.example.newword.data.WordRepo
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class WordViewModel(private val repo: WordRepo) : ViewModel() {

    val wordList: StateFlow<List<Word>> = repo.getAllWordsStream()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _currentIndex = MutableStateFlow(0)

    val currentWord: StateFlow<Word?> = combine(wordList, _currentIndex) { words, index ->
        words.getOrNull(index)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    fun nextWord() {
        val list = wordList.value
        if (list.isNotEmpty()) {
            _currentIndex.value = (_currentIndex.value + 1) % list.size
        }
    }

    fun prevWord() {
        val list = wordList.value
        if (list.isNotEmpty()) {
            _currentIndex.value = (_currentIndex.value - 1 + list.size) % list.size
        }
    }

    fun deleteCurrentWord() {
        currentWord.value?.let { word ->
            viewModelScope.launch {
                repo.deleteWord(word)
            }
        }
    }

    fun insertWord(word: Word) {
        viewModelScope.launch {
            repo.insertWord(word)
        }
    }

    fun updateWord(word: Word) {
        viewModelScope.launch {
            repo.updateWord(word)
        }
    }

}
