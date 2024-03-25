package thirdContext

fun main() {
    val trie = Trie()
    val dictionary = readLine()!!.split(' ')
    val words = readLine()!!.split(" ")
    val resultStrings = mutableListOf<String>()

    for (word in dictionary) {
        trie.insert(word)
    }

    for (word in words) {
        val shortestPrefix = trie.findShortestPrefix(word)
        if (shortestPrefix.isNotEmpty()) {
            resultStrings.add(shortestPrefix)
        } else {
            resultStrings.add(word)
        }
    }

    println(resultStrings.joinToString(" "))
}

class TrieNode {
    var children = mutableMapOf<Char, TrieNode>()
    var isEndOfWord = false
}

class Trie {
    private val root = TrieNode()

    fun insert(word: String) {
        var current = root
        for (char in word) {
            current = current.children.getOrPut(char) { TrieNode() }
        }
        current.isEndOfWord = true
    }

    fun findShortestPrefix(word: String): String {
        var current = root
        var prefix = ""
        for (char in word) {
            if (!current.children.containsKey(char)) {
                prefix = ""
                break
            }
            prefix += char
            current = current.children[char]!!
            if (current.isEndOfWord) {
                break
            }
        }

        return prefix
    }
}
