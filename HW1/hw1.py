"""
Foundations Of Artificial Intelligence
Homework 1-P
Author: @Yash Mahajan (ym9800)
"""
import sys
from queue import Queue


def difference_in_letters(word1, word2):
    difference = 0
    for count1, count2 in zip(word1, word2):
        if count1 != count2:
            difference += 1
    return difference == True

def create_graph(word_list):
    graph = {}
    for i in range(len(word_list)):
        graph[word_list[i]] = []
        for next_word in word_list:
            if difference_in_letters(word_list[i], next_word):
                graph[word_list[i]].append(next_word)
    return graph

def bfs(graph, start, end):
    queue = Queue()
    queue.put(start)
    predecessor = {}
    predecessor[start] = None

    while not queue.empty():
        current = queue.get()
        if current == end:
            break

        for neighbor in graph[current]:
            if neighbor not in predecessor:
                predecessor[neighbor] = current
                queue.put(neighbor)

    if end in predecessor:
        path = []
        current = end
        while current != start:
            path.insert(0, current)
            current = predecessor[current]
        path.insert(0, start)
        return path
    else:
        return None

def main(dictionary_path, first_word, second_word):
    words = set()
    with open(dictionary_path, 'r') as file:
        for word in file:
            words.add(word.strip().lower())
    list_of_words = [word for word in words if len(word) == len(first_word)]
    graph = create_graph(list_of_words)
    shortest_path = bfs(graph, first_word, second_word)
    if shortest_path:
        for word in shortest_path:
            print(word)
    else:
        print("No solution")

if __name__ == "__main__":
        main(sys.argv[1], sys.argv[2], sys.argv[3])