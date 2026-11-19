# CS5800 Homework 8 & 9

Ahmad Bishara

Four programs, one per question, each in its own folder with a `src/` directory.
All code is plain Java (no built-in hash tables, trees or collections used for the data structures).

**Requirements:** Java 17 or newer.

**How to run any question:** open a terminal in that question's folder, then compile and run:

```
javac -d out src/*.java
java -cp out <MainClass>
```

---

## Question 1: Hash table for text (`question1/`)

```
cd question1
javac -d out src/*.java
java -cp out QuestionOne                  # uses alice_in_wonderland.txt
java -cp out QuestionOne other_file.txt   # or any other text file
```

- **Tokenizing:** text is lowercased and split on every non-letter (`[^a-z]+`), so `"Alice,"` becomes `alice`.
- **Hash function:** each word is turned into a number with Horner's rule in base 31, reduced mod P = 1,000,000,007 after every character. That number goes through a universal hash function, `((a*k + b) mod P) mod m`, with `a` and `b` drawn once per table from a seeded random generator (seed 42, so results are reproducible).
- **Collisions:** separate chaining with my own linked lists (`Node`, `LinkedList`).
- **Operations:** `put` (insert), `delete`, `increase`, `findNode` (find), `listAllLists` (list all keys).
- **Output:** for each m in 30, 300 and 1000 it writes `counts_<m>.txt` (every word and its count) and prints n, the load factor alpha, the variance of the list lengths (next to the value expected for a uniform hash, alpha(1 - 1/m)), the histogram of list lengths, and the lengths of the longest 10% of lists.

## Question 2: Red-black tree (`question2/`)

```
cd question2
javac -d out src/*.java
java -cp out QuestionTwo                  # builds the tree from input.txt
java -cp out QuestionTwo numbers.txt      # or any other file of numbers
```

- The input file holds integers separated by spaces, newlines or commas. Each one is inserted with the red-black insert.
- Then it reads commands interactively and prints the tree height after each one:
  `insert x`, `delete x`, `search x`, `min`, `max`, `successor x`, `predecessor x`, `sort`, `quit`
- Insert uses the red-black fix-up with left and right rotations. Delete is the regular BST delete (CLRS TREE-DELETE with TRANSPLANT), without the red-black fix-up, as the assignment requires.
- Empty children and the root's parent are a single black sentinel node, as in CLRS.

## Question 3: Skip list (`question3/`)

```
cd question3
javac -d out src/*.java
java -cp out QuestionThree
```

- Commands: `insert x`, `delete x`, `lookup x`, `print`, `quit`. Several commands can go on one line, separated by commas, for example:
  `insert 20, insert 40, insert 10, insert 20, delete 20, lookup 40`
- After each operation the whole skip list is printed level by level, with each key in its own column.
- Tower heights come from fair coin flips (seeded, so runs are reproducible). One node per distinct key: inserting a key that is already present leaves the list unchanged.

## Question 4: Binomial heap (`question4/`)

```
cd question4
javac -d out src/*.java
java -cp out QuestionFour
```

- Linked representation: each node stores key, degree, parent, first child and next sibling. Roots are kept in increasing order of degree, children in decreasing order.
- Operations: Make-Heap (constructor), Insert, Minimum, Extract-Min, Union, Decrease-Key, Delete.
- The program first runs a small demo that prints the heap after every operation, then tests random arrays of 10, 50, 200 and 1000 keys. After every operation it checks that each tree is heap-ordered, that root degrees are unique and increasing, and that each B_k has exactly 2^k nodes. It also checks that repeated Extract-Min returns the keys in sorted order.
