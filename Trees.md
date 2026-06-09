It is incredibly smart to get a bird's-eye view of the landscape before diving into the code. Trees are a favorite in interviews because they naturally test your grasp of recursion, edge-case handling, and time/space complexity.

Here is your essential cheat sheet on Trees before we start tackling problems.

### **1. The Core Terminology**

You need to know these terms inside and out so you don't hesitate when an interviewer uses them.

- **Root:** The topmost node of the tree.
    
- **Edge:** The link between a parent and a child node.
    
- **Leaf:** A node with no children.
    
- **Depth of a Node:** The number of edges from the root to that node.
    
- **Height of a Tree:** The number of edges on the longest path from the root to a leaf. (Note: A tree with just a root has a height of $0$, though some definitions say $1$. Always clarify with your interviewer).
    

### **2. Types of Trees You Will Encounter**

- **Binary Tree (BT):** A tree where every node has at most two children (left and right).
    
- **Binary Search Tree (BST):** A specialized binary tree. The left child is strictly less than the parent, and the right child is strictly greater. **Crucial property:** An in-order traversal of a BST yields a sorted array.
    
- **Balanced Trees (AVL / Red-Black):** Trees that automatically keep their height to $O(\log N)$ to ensure fast lookups. You rarely have to code these from scratch, but you must know they exist and why they are used.
    
- **Complete Binary Tree:** Every level is fully filled except possibly the last, which is filled from left to right. (This is the basis of a Heap).
    
- **Trie (Prefix Tree):** An $N$-ary tree used heavily for string search problems (like autocomplete).
    

### **3. The Two Pillars of Traversal**

Almost every tree problem is just a variation of how you visit the nodes.

|**Traversal Type**|**Approach**|**Common Use Case**|
|---|---|---|
|**Depth-First Search (DFS)**|Goes deep to the leaves before exploring siblings. Uses a Stack (usually the recursion call stack).|Pathfinding, counting depths, checking subtree properties.|
|_- Pre-order_|`Node -> Left -> Right`|Copying/serializing a tree.|
|_- In-order_|`Left -> Node -> Right`|Flattening a BST into sorted order.|
|_- Post-order_|`Left -> Right -> Node`|Deleting a tree, Bottom-up calculations (e.g., finding the height).|
|**Breadth-First Search (BFS)**|Explores level by level. Uses a Queue.|Finding the shortest path, level-order printing, right-side views.|

### **4. Standard Complexities**

Let $N$ be the number of nodes, $H$ be the height of the tree, and $W$ be the maximum width (max nodes at any level).

|**Operation**|**Time Complexity**|**Space Complexity (Auxiliary)**|
|---|---|---|
|**DFS (All Types)**|$O(N)$|$O(H)$ for the recursion stack.|
|**BFS (Level-Order)**|$O(N)$|$O(W)$ for the queue.|
|**Search in a BST**|$O(H)$|$O(1)$ iterative, $O(H)$ recursive.|

_Important Note on Space Complexity:_ In a perfectly balanced tree, $H = \log N$. In the worst-case scenario (a skewed tree that looks like a linked list), $H = N$. Therefore, the worst-case space complexity for DFS is $O(N)$.

### **5. Interview "Gotchas" & Pro-Tips**

1. **The Empty Tree:** Always start your code with `if not root: return ...`. It is the most common edge case.
    
2. **Assuming Balance:** Unless the interviewer explicitly states the tree is balanced, do not assume it is. Your worst-case time/space complexities must account for skewed trees.
    
3. **Global Variables:** Avoid them in tree recursion. Interviewers prefer helper functions that pass state via parameters or return values.
    

Now that we have the foundation laid out, should we start with a classic DFS pattern problem or a BFS pattern problem?





Switching gears to C++ is a great choice. In competitive programming and interviews (like on LeetCode), C++ is incredibly fast and gives you fine-grained control over memory.

Here is the standard, universally accepted representation of a Binary Tree node in modern C++.

### **The C++ `TreeNode` Structure**

In C++, a tree node is typically defined using a `struct` (though a `class` works too, `struct` makes all members public by default, which is easier for tree traversals).

C++

```
struct TreeNode {
    int val;              // The data stored in the node
    TreeNode* left;       // Pointer to the left child
    TreeNode* right;      // Pointer to the right child

    // 1. Default constructor
    TreeNode() : val(0), left(nullptr), right(nullptr) {}

    // 2. Constructor with just a value (Most commonly used)
    TreeNode(int x) : val(x), left(nullptr), right(nullptr) {}

    // 3. Constructor with value and specific left/right children
    TreeNode(int x, TreeNode* left, TreeNode* right) : val(x), left(left), right(right) {}
};
```

### **Key Takeaways for C++ Trees**

- **Pointers:** Notice the `*` (asterisk). Trees in C++ are built using pointers because the size of the tree is dynamic. You are linking separate blocks of memory together.
    
- **`nullptr` vs `NULL`:** Always use `nullptr` in modern C++ (C++11 and onwards) to represent an empty child. It is safer and prevents type-checking bugs that the older `NULL` macro can cause.
    
- **Initialization Lists:** The colons (`:`) in the constructors are member initializer lists. They are the most efficient way to assign values to your struct members when creating a new node.
    

### **How to Create Nodes (Example)**

If you ever need to manually build a small tree to test your code locally, here is how you use those constructors:

C++

```
// Building a simple tree:
//       1
//      / \
//     2   3

// Method A: Creating nodes one by one
TreeNode* root = new TreeNode(1);
root->left = new TreeNode(2);
root->right = new TreeNode(3);

// Method B: Using the advanced constructor for cleaner code
TreeNode* root = new TreeNode(1, new TreeNode(2), new TreeNode(3));
```

### **Your Updated C++ Standard Solution Template**

Since we are using C++, here is the updated template I will use for our problem-solving sessions moving forward:

C++

```
// APPROACH:
// 1. [Step-by-step breakdown of the logic]
// 2. [Base cases and recursive relations explained]
// 3. [How the result is built and returned]

class Solution {
public:
    int exampleTreeProblem(TreeNode* root) {
        // Edge case check is almost always the first line
        if (!root) return 0; 
        
        // Implementation goes here
        return 0;
    }
};

// Complexity Analysis:
// Time Complexity: O(N) ...
// Space Complexity: O(H) ...
```



Here is the complete breakdown for Preorder Traversal. Preorder follows the **Node -> Left -> Right** pattern. This is a Depth-First Search (DFS) approach.

We will look at both the recursive and iterative methods, as interviewers frequently ask for the iterative version once you quickly solve the recursive one.

### **1. Recursive Preorder Traversal**

This is the most natural way to traverse a tree because trees are inherently recursive data structures.

**Pattern Identified:** DFS (Pre-order, Recursive)

C++

```
// APPROACH:
// 1. We need a helper function to maintain the state of our result vector during recursion.
// 2. Base case: If the current node is null, we simply return and do nothing.
// 3. Recursive relation: 
//    - Process the current node (add its value to the result).
//    - Recursively call the function on the left child.
//    - Recursively call the function on the right child.

#include <vector>

class Solution {
private:
    void traverse(TreeNode* node, std::vector<int>& result) {
        if (!node) return; // Base case
        
        result.push_back(node->val);      // Node
        traverse(node->left, result);     // Left
        traverse(node->right, result);    // Right
    }

public:
    std::vector<int> preorderTraversal(TreeNode* root) {
        std::vector<int> result;
        traverse(root, result);
        return result;
    }
};
```

#### **Complexity Analysis (Recursive)**

- **Time Complexity:** `O(N)`
    
    We visit every single node in the tree exactly once to append its value to our result array.
    
- **Space Complexity:** `O(H)` where `H` is the height of the tree.
    
    This represents the maximum number of frames on the system call stack at any given time. In the worst-case scenario (a skewed tree), this is `O(N)`. In a perfectly balanced tree, it is `O(log N)`. The space for the output vector is `O(N)`, but usually, output space isn't counted against auxiliary space complexity.
    

### **2. Iterative Preorder Traversal**

To convert a recursive algorithm to an iterative one, we must manually simulate the system's call stack using our own Stack data structure.

**Pattern Identified:** DFS (Pre-order, Iterative using Stack)

C++

```
// APPROACH:
// 1. Use a Stack to keep track of the nodes we need to visit.
// 2. Push the root node onto the stack to initialize the process.
// 3. Loop until the stack is empty:
//    - Pop the top node and add its value to our result (Process Node).
//    - Push the RIGHT child onto the stack first.
//    - Push the LEFT child onto the stack second.
// 4. Why right first? Because a Stack is Last-In-First-Out (LIFO). 
//    By pushing the right child first, the left child sits on top of the stack 
//    and gets popped (and processed) before the right child.

#include <vector>
#include <stack>

class Solution {
public:
    std::vector<int> preorderTraversal(TreeNode* root) {
        std::vector<int> result;
        if (!root) return result; // Edge case
        
        std::stack<TreeNode*> st;
        st.push(root);
        
        while (!st.empty()) {
            TreeNode* curr = st.top();
            st.pop();
            
            result.push_back(curr->val); // Node
            
            // Push right child first, then left child
            if (curr->right) st.push(curr->right);
            if (curr->left) st.push(curr->left);
        }
        
        return result;
    }
};
```

#### **Complexity Analysis (Iterative)**

- **Time Complexity:** `O(N)`
    
    We push and pop every node exactly once from our stack. Stack operations take `O(1)` time.
    
- **Space Complexity:** `O(H)`
    
    The stack stores nodes. At any given point, the stack contains right children of the nodes along the current left-most path. The maximum number of nodes in the stack will be proportional to the height of the tree `H`. In the worst case (a right-skewed tree), this approaches `O(N)`.



This is an excellent set of problems. These represent the "Bread and Butter" of tree traversal applications. They mostly rely on **Bottom-Up DFS (Post-order)** or **Simultaneous Traversal** patterns.

Let's break them down using our standard C++ template.

### **1. Maximum Depth / Height of Binary Tree**

**Pattern Identified:** DFS (Post-order / Bottom-Up)

_Concept:_ The maximum depth of a tree is simply $1$ (for the root) plus the maximum of the depths of its left and right subtrees.

C++

```
// APPROACH:
// 1. Base case: If the tree is empty (root is null), its depth is 0.
// 2. Recursively find the max depth of the left subtree.
// 3. Recursively find the max depth of the right subtree.
// 4. The depth of the current node is 1 + the maximum of the left and right depths.

#include <algorithm>

class Solution {
public:
    int maxDepth(TreeNode* root) {
        if (!root) return 0;
        
        int leftDepth = maxDepth(root->left);
        int rightDepth = maxDepth(root->right);
        
        return 1 + std::max(leftDepth, rightDepth);
    }
};
```

**Complexity Analysis:**

- **Time Complexity:** $O(N)$ – We visit every node exactly once.
    
- **Space Complexity:** $O(H)$ – Recursion stack space, where $H$ is the height of the tree. Worst case $O(N)$, average $O(\log N)$.
    

### **2. Check for Balanced Binary Tree**

**Pattern Identified:** DFS (Bottom-Up Post-order with a Sentinel Value)

_Concept:_ A tree is balanced if the heights of the left and right subtrees of _every_ node differ by no more than 1. Instead of computing height separately (which takes $O(N^2)$), we calculate height and check balance simultaneously. If a subtree is unbalanced, we bubble up `-1`.

C++

```
// APPROACH:
// 1. Create a helper function that returns the height of the tree.
// 2. Base case: null node returns height 0.
// 3. Get height of left subtree. If it returns -1 (unbalanced), bubble up -1 immediately.
// 4. Get height of right subtree. If it returns -1, bubble up -1 immediately.
// 5. Check the current node: if the absolute difference between left and right height is > 1, 
//    return -1 (flagging this subtree as unbalanced).
// 6. Otherwise, return the actual height: 1 + max(left, right).

#include <algorithm>
#include <cmath>

class Solution {
private:
    int checkHeight(TreeNode* root) {
        if (!root) return 0;
        
        int leftHeight = checkHeight(root->left);
        if (leftHeight == -1) return -1; // Left subtree is unbalanced
        
        int rightHeight = checkHeight(root->right);
        if (rightHeight == -1) return -1; // Right subtree is unbalanced
        
        if (std::abs(leftHeight - rightHeight) > 1) return -1; // Current node is unbalanced
        
        return 1 + std::max(leftHeight, rightHeight);
    }

public:
    bool isBalanced(TreeNode* root) {
        return checkHeight(root) != -1;
    }
};
```

**Complexity Analysis:**

- **Time Complexity:** $O(N)$ – Each node is processed once.
    
- **Space Complexity:** $O(H)$ – For the recursion stack.
    

### **3. Check if Two Trees are Identical or Not**

**Pattern Identified:** DFS (Simultaneous Pre-order Traversal)

_Concept:_ Traverse both trees at the exact same time. If at any point the nodes are structurally different or have different values, they aren't identical.

C++

```
// APPROACH:
// 1. Base case 1: If both nodes are null, they are identical (return true).
// 2. Base case 2: If only ONE of the nodes is null, they are structurally different (return false).
// 3. Base case 3: If both exist but values mismatch, return false.
// 4. Recursively check if left subtrees are identical AND right subtrees are identical.

class Solution {
public:
    bool isSameTree(TreeNode* p, TreeNode* q) {
        if (!p && !q) return true;
        if (!p || !q) return false;
        if (p->val != q->val) return false;
        
        return isSameTree(p->left, q->left) && isSameTree(p->right, q->right);
    }
};
```

**Complexity Analysis:**

- **Time Complexity:** $O(N)$ – Where $N$ is the minimum number of nodes between the two trees.
    
- **Space Complexity:** $O(H)$ – Where $H$ is the minimum height of the two trees.
    

### **4. Symmetric Binary Tree**

**Pattern Identified:** DFS (Simultaneous Mirrored Traversal)

_Concept:_ A tree is symmetric if its left subtree is a mirror reflection of its right subtree. We use two pointers moving in opposite directions.

C++

```
// APPROACH:
// 1. Create a helper function comparing two nodes (left branch and right branch).
// 2. Base case: Both null -> true. One null -> false. Values differ -> false.
// 3. Recursive step is mirrored: 
//    Compare (left->left with right->right) AND (left->right with right->left).

class Solution {
private:
    bool isMirror(TreeNode* leftNode, TreeNode* rightNode) {
        if (!leftNode && !rightNode) return true;
        if (!leftNode || !rightNode) return false;
        if (leftNode->val != rightNode->val) return false;
        
        // Notice the mirrored parameters in the recursive calls
        return isMirror(leftNode->left, rightNode->right) && 
               isMirror(leftNode->right, rightNode->left);
    }

public:
    bool isSymmetric(TreeNode* root) {
        if (!root) return true;
        return isMirror(root->left, root->right);
    }
};
```

**Complexity Analysis:**

- **Time Complexity:** $O(N)$ – We visit at most $N$ nodes.
    
- **Space Complexity:** $O(H)$ – For the recursion stack.
    

### **5. Check Completeness of a Binary Tree**

**Pattern Identified:** BFS (Level-Order with Null Checking)

_Concept:_ In a complete binary tree, all nodes are as far left as possible. If we do a standard BFS (queue) and encounter a `null` node, there must **never** be a non-null node processed after it.

C++

```
// APPROACH:
// 1. Use a standard BFS queue, but push `null` children as well.
// 2. Maintain a boolean flag `pastNull` initially set to false.
// 3. Pop a node. If it is null, set `pastNull = true`.
// 4. If it is NOT null, check `pastNull`. If we have already seen a null previously, 
//    the tree is not complete (return false).
// 5. Push left and right children (even if they are null).

#include <queue>

class Solution {
public:
    bool isCompleteTree(TreeNode* root) {
        if (!root) return true;
        
        std::queue<TreeNode*> q;
        q.push(root);
        bool pastNull = false;
        
        while (!q.empty()) {
            TreeNode* curr = q.front();
            q.pop();
            
            if (curr == nullptr) {
                pastNull = true;
            } else {
                if (pastNull) return false; // Found a valid node after a null node
                
                q.push(curr->left);
                q.push(curr->right);
            }
        }
        
        return true;
    }
};
```

**Complexity Analysis:**

- **Time Complexity:** $O(N)$ – We process every node once.
    
- **Space Complexity:** $O(W)$ or $O(N)$ – Where $W$ is the max width. In a complete tree, the queue holds at most $N/2$ nodes at the leaf level.
    

### **6. Check Children Sum Property**

**Pattern Identified:** DFS (Bottom-Up Post-order)

_Concept:_ For every node, its value must be equal to the sum of its left and right children's values. Leaf nodes naturally satisfy this property.

C++

```
// APPROACH:
// 1. Base case: If node is null, or it's a leaf node, return true.
// 2. Initialize a sum variable to 0. Add left child's value if it exists, and right child's value.
// 3. If the current node's value != sum, return false.
// 4. Recursively check if the left subtree AND right subtree also follow the property.

class Solution {
public:
    bool isParentSum(TreeNode* root) {
        if (!root) return true;
        
        // Leaf nodes always satisfy the property
        if (!root->left && !root->right) return true;
        
        int sum = 0;
        if (root->left) sum += root->left->val;
        if (root->right) sum += root->right->val;
        
        // If current node violates property, return false
        if (root->val != sum) return false;
        
        // Check subtrees
        return isParentSum(root->left) && isParentSum(root->right);
    }
};
```

**Complexity Analysis:**

- **Time Complexity:** $O(N)$ – Every node is visited once.
    
- **Space Complexity:** $O(H)$ – For the recursion stack.


Here is the comprehensive playbook for advanced level-order modifications, tree views, and geometric traversals. These problems frequently appear in senior and FAANG interviews because they test your ability to track spatial coordinates, maintain state, and manipulate tree structural properties.

---

### **14. Zig Zag or Spiral Traversal**

**Pattern Identified:** BFS (Level-Order Variation with Direction Flip)

```cpp
// APPROACH:
// 1. Initialize a queue for standard BFS. Use a boolean flag 'leftToRight' to track direction.
// 2. Loop while the queue is not empty. Get the size of the current level.
// 3. Create a vector 'row' of size 'levelSize' to store nodes of the current level.
// 4. For each node, determine its insertion index based on the direction flag:
//    - If leftToRight is true: index = i
//    - If leftToRight is false: index = levelSize - 1 - i (fills backwards)
// 5. Push children to the queue as usual (left then right).
// 6. After processing the level, flip the 'leftToRight' flag and push 'row' to the result.

#include <vector>
#include <queue>

class Solution {
public:
    std::vector<std::vector<int>> zigzagLevelOrder(TreeNode* root) {
        std::vector<std::vector<int>> result;
        if (!root) return result;
        
        std::queue<TreeNode*> q;
        q.push(root);
        bool leftToRight = true;
        
        while (!q.empty()) {
            int size = q.size();
            std::vector<int> row(size);
            
            for (int i = 0; i < size; i++) {
                TreeNode* node = q.front();
                q.pop();
                
                // Determine position based on current traversal direction
                int index = leftToRight ? i : (size - 1 - i);
                row[index] = node->val;
                
                if (node->left) q.push(node->left);
                if (node->right) q.push(node->right);
            }
            
            leftToRight = !leftToRight; // Flip the direction
            result.push_back(row);
        }
        return result;
    }
};

```

#### **Complexity Analysis**

* **Time Complexity:** $O(N)$ — Every node is visited exactly once, and direct index assignment takes $O(1)$ time.
* **Space Complexity:** $O(W) = O(N)$ — The queue stores at most the maximum width of the tree.

---

### **15. Boundary Traversal**

**Pattern Identified:** Tri-Partite DFS (Left Boundary + Leaves + Right Boundary)

```cpp
// APPROACH:
// 1. Boundary traversal goes anti-clockwise: Root -> Left Boundary -> Leaves -> Right Boundary.
// 2. Base case: If the root is a leaf, just add it and return. Otherwise, add root to result.
// 3. Step 1 (Left Boundary): Traverse left children. If left doesn't exist, go right. 
//    Do not include leaf nodes. Add to result during top-down traversal.
// 4. Step 2 (Leaves): Perform a standard DFS (Inorder/Preorder). If a node is a leaf, add it.
// 5. Step 3 (Right Boundary): Traverse right children. If right doesn't exist, go left.
//    Do not include leaf nodes. Add to a temporary stack/vector and reverse it before adding 
//    to the main result (bottom-up accumulation).

#include <vector>

class Solution {
private:
    bool isLeaf(TreeNode* node) {
        return !node->left && !node->right;
    }
    
    void addLeftBoundary(TreeNode* root, std::vector<int>& res) {
        TreeNode* curr = root->left;
        while (curr) {
            if (!isLeaf(curr)) res.push_back(curr->val);
            if (curr->left) curr = curr->left;
            else curr = curr->right;
        }
    }
    
    void addLeaves(TreeNode* root, std::vector<int>& res) {
        if (isLeaf(root)) {
            res.push_back(root->val);
            return;
        }
        if (root->left) addLeaves(root->left, res);
        if (root->right) addLeaves(root->right, res);
    }
    
    void addRightBoundary(TreeNode* root, std::vector<int>& res) {
        TreeNode* curr = root->right;
        std::vector<int> temp;
        while (curr) {
            if (!isLeaf(curr)) temp.push_back(curr->val);
            if (curr->right) curr = curr->right;
            else curr = curr->left;
        }
        // Reverse to get anti-clockwise (bottom-up) order
        for (int i = temp.size() - 1; i >= 0; --i) {
            res.push_back(temp[i]);
        }
    }

public:
    std::vector<int> boundaryTraversal(TreeNode* root) {
        std::vector<int> result;
        if (!root) return result;
        
        if (!isLeaf(root)) result.push_back(root->val);
        
        addLeftBoundary(root, result);
        addLeaves(root, result);
        addRightBoundary(root, result);
        
        return result;
    }
};

```

#### **Complexity Analysis**

* **Time Complexity:** $O(N)$ — The left boundary takes $O(H)$, right boundary takes $O(H)$, and collecting leaves takes $O(N)$ since we visit every node. Overall time is linear.
* **Space Complexity:** $O(H)$ — For the recursion stack during leaf collection and the temporary array for the right boundary. Worst case is $O(N)$ for a skewed tree.

---

### **16. Vertical Order Traversal (LeetCode 987)**

**Pattern Identified:** Coordinate Mapping using BFS & Sorted Collections

```cpp
// APPROACH:
// 1. Think of the tree as a 2D coordinate system. Root is at (col=0, row=0).
//    Left child is at (col-1, row+1), Right child is at (col+1, row+1).
// 2. LeetCode requires sorting by column first, then by row. If nodes share the same row and column, 
//    sort them by value.
// 3. Use a nested map structure: map<col, map<row, multiset<val>>>. 
//    'map' automatically sorts columns and rows in ascending order; 'multiset' handles duplicate values in sorted order.
// 4. Perform a BFS. The queue stores pairs: {node, {col, row}}.
// 5. Pop each node, insert its value into the map structure, and queue its children with updated coordinates.
// 6. Iterate through the map to extract and compile the values into the final 2D array.

#include <vector>
#include <map>
#include <set>
#include <queue>

class Solution {
public:
    std::vector<std::vector<int>> verticalTraversal(TreeNode* root) {
        std::vector<std::vector<int>> result;
        if (!root) return result;
        
        // map<col, map<row, multiset<val>>>
        std::map<int, std::map<int, std::multiset<int>>> nodes;
        std::queue<std::pair<TreeNode*, std::pair<int, int>>> q; // {node, {col, row}}
        
        q.push({root, {0, 0}});
        
        while (!q.empty()) {
            auto p = q.front();
            q.pop();
            
            TreeNode* node = p.first;
            int col = p.second.first;
            int row = p.second.second;
            
            nodes[col][row].insert(node->val);
            
            if (node->left) q.push({node->left, {col - 1, row + 1}});
            if (node->right) q.push({node->right, {col + 1, row + 1}});
        }
        
        // Flatten the map into the final result structure
        for (auto pCol : nodes) {
            std::vector<int> colVector;
            for (auto pRow : pCol.second) {
                colVector.insert(colVector.end(), pRow.second.begin(), pRow.second.end());
            }
            result.push_back(colVector);
        }
        
        return result;
    }
};

```

#### **Complexity Analysis**

* **Time Complexity:** $O(N \log N)$ — Map and multiset insertions take logarithmic time. Traversing $N$ nodes results in $O(N \log N)$ time.
* **Space Complexity:** $O(N)$ — To store the elements inside the map tracking coordinates and the BFS queue.

---

### **17. Top View of Binary Tree**

**Pattern Identified:** BFS with First-Seen Column Tracking

```cpp
// APPROACH:
// 1. Establish a 1D horizontal coordinate (col). Root is 0, left is col-1, right is col+1.
// 2. A node is visible in the Top View if it is the FIRST node encountered at that column index.
// 3. Use BFS (Level Order) because it processes nodes from top to bottom, ensuring the topmost 
//    node of any column is reached first.
// 4. Maintain a map<int, int> to bind columns to node values. 
//    If a column index does not yet exist in the map, insert the current node's value.
// 5. Extract the map's elements sequentially into the output vector to keep columns sorted.

#include <vector>
#include <map>
#include <queue>

class Solution {
public:
    std::vector<int> topView(TreeNode* root) {
        std::vector<int> result;
        if (!root) return result;
        
        std::map<int, int> topNodeMap; // {col, val}
        std::queue<std::pair<TreeNode*, int>> q; // {node, col}
        
        q.push({root, 0});
        
        while (!q.empty()) {
            auto p = q.front();
            q.pop();
            
            TreeNode* node = p.first;
            int col = p.second;
            
            // If the column has not been filled yet, this is the top view node
            if (topNodeMap.find(col) == topNodeMap.end()) {
                topNodeMap[col] = node->val;
            }
            
            if (node->left) q.push({node->left, col - 1});
            if (node->right) q.push({node->right, col + 1});
        }
        
        for (auto p : topNodeMap) {
            result.push_back(p.second);
        }
        return result;
    }
};

```

#### **Complexity Analysis**

* **Time Complexity:** $O(N \log N)$ (or $O(N)$ if using an `unordered_map` paired with min/max column trackers). Standard `std::map` adds an $O(\log N)$ overhead per insertion.
* **Space Complexity:** $O(N)$ — For the tracking map and queue arrays.

---

### **18. Bottom View of Binary Tree**

**Pattern Identified:** BFS with Overwriting Column Tracking

```cpp
// APPROACH:
// 1. Similar to the Top View, establish a horizontal coordinate layout.
// 2. A node is visible in the Bottom View if it is the LAST node processed at its column index.
// 3. Perform a standard BFS. The queue stores pairs: {node, col}.
// 4. For every node, unconditionally assign its value to the map: topNodeMap[col] = node->val. 
//    This ensures that lower nodes naturally overwrite any values set by higher nodes.
// 5. Collect the map values into the final output vector.

#include <vector>
#include <map>
#include <queue>

class Solution {
public:
    std::vector<int> bottomView(TreeNode* root) {
        std::vector<int> result;
        if (!root) return result;
        
        std::map<int, int> bottomNodeMap; // {col, val}
        std::queue<std::pair<TreeNode*, int>> q; // {node, col}
        
        q.push({root, 0});
        
        while (!q.empty()) {
            auto p = q.front();
            q.pop();
            
            TreeNode* node = p.first;
            int col = p.second;
            
            // Overwrite continuously; the deepest/last level node wins
            bottomNodeMap[col] = node->val;
            
            if (node->left) q.push({node->left, col - 1});
            if (node->right) q.push({node->right, col + 1});
        }
        
        for (auto p : bottomNodeMap) {
            result.push_back(p.second);
        }
        return result;
    }
};

```

#### **Complexity Analysis**

* **Time Complexity:** $O(N \log N)$ — Due to map updates across $N$ elements.
* **Space Complexity:** $O(N)$ — To hold the tree's complete horizontal span in the tracking map.

---

### **19. Binary Tree Right / Left Side View (LeetCode 199)**

**Pattern Identified:** DFS (Modified Preorder: Root -> Right -> Left)

```cpp
// APPROACH (Right Side View):
// 1. While this can be solved using BFS by collecting the last node of each level, 
//    a recursive DFS is cleaner and uses less auxiliary memory ($O(H)$ vs $O(W)$).
// 2. Traversal Order: Root -> Right -> Left. This ensures the rightmost nodes of any level are hit first.
// 3. Track the current depth/level (starting at 0).
// 4. Condition for visibility: If the current 'level' matches the size of our 'result' vector, 
//    it means this is the first time we are visiting a node at this depth. Because we prioritize 
//    moving right, this node must belong to the right side view.
// 5. To solve for the Left Side View, simply change the traversal order to: Root -> Left -> Right.

#include <vector>

class Solution {
private:
    void recursion(TreeNode* root, int level, std::vector<int>& res) {
        if (!root) return;
        
        // If this is the first node of this level we encounter
        if (level == res.size()) {
            res.push_back(root->val);
        }
        
        // Prioritize right branch for Right Side View
        recursion(root->right, level + 1, res);
        recursion(root->left, level + 1, res);
    }

public:
    std::vector<int> rightSideView(TreeNode* root) {
        std::vector<int> result;
        recursion(root, 0, result);
        return result;
    }
};

```

#### **Complexity Analysis**

* **Time Complexity:** $O(N)$ — Every node is visited exactly once.
* **Space Complexity:** $O(H)$ — For the recursion call stack, which optimizes to $O(\log N)$ on balanced configurations.

---

### **20. Maximum Width of Binary Tree (LeetCode 662)**

**Pattern Identified:** BFS with Heap-Style Node Indexing

```cpp
// APPROACH:
// 1. Assign an index to every node using heap indexing properties: 
//    If a parent node has an index 'i', its left child is at '2*i' and its right child is at '2*i + 1'.
// 2. The width of any level is calculated as: (rightmost_node_index - leftmost_node_index + 1).
// 3. Run a BFS. The queue holds pairs: {node, index}.
// 4. To prevent integer overflow in deep skewed trees, normalize indices at the start of each level:
//    Subtract the leftmost node's index from all node indices within that level.
// 5. Maintain a global maximum width variable to track the highest value found.

#include <vector>
#include <queue>
#include <algorithm>

class Solution {
public:
    int widthOfBinaryTree(TreeNode* root) {
        if (!root) return 0;
        
        long long maxWidth = 0;
        std::queue<std::pair<TreeNode*, long long>> q; // {node, index}
        q.push({root, 0});
        
        while (!q.empty()) {
            int size = q.size();
            long long mMin = q.front().second; // Smallest index at the current level
            long long first = 0, last = 0;
            
            for (int i = 0; i < size; i++) {
                // Normalize index to prevent overflow
                long long curr_id = q.front().second - mMin;
                TreeNode* node = q.front().first;
                q.pop();
                
                if (i == 0) first = curr_id;
                if (i == size - 1) last = curr_id;
                
                if (node->left) q.push({node->left, curr_id * 2});
                if (node->right) q.push({node->right, curr_id * 2 + 1});
            }
            maxWidth = std::max(maxWidth, last - first + 1);
        }
        return maxWidth;
    }
};

```

#### **Complexity Analysis**

* **Time Complexity:** $O(N)$ — Linear scan via level-by-level BFS.
* **Space Complexity:** $O(N)$ — Queue size tracks the maximum width level.

---

### **21. Reverse Odd Levels of Binary Tree (LeetCode 2415)**

**Pattern Identified:** Symmetric Two-Pointer DFS

```cpp
// APPROACH:
// 1. The problem specifies that the input is a **Perfect Binary Tree**.
// 2. Instead of standard level-by-level manipulation using arrays, a symmetric DFS 
//    can process paired mirror nodes simultaneously.
// 3. Create a helper function that takes two nodes: 'node1' (from the left side) and 'node2' (from the right side).
// 4. If the current level is odd, swap the values of 'node1' and 'node2'.
// 5. Recurse down by pairing their children symmetrically:
//    - Pair node1's left child with node2's right child.
//    - Pair node1's right child with node2's left child.

class Solution {
private:
    void traverseSymmetric(TreeNode* node1, TreeNode* node2, int level) {
        if (!node1 || !node2) return;
        
        // If the level is odd, swap values
        if (level % 2 != 0) {
            int temp = node1->val;
            node1->val = node2->val;
            node2->val = temp;
        }
        
        // Symmetric recursion calls
        traverseSymmetric(node1->left, node2->right, level + 1);
        traverseSymmetric(node1->right, node2->left, level + 1);
    }

public:
    TreeNode* reverseOddLevels(TreeNode* root) {
        if (!root) return nullptr;
        // Start recursion with the left and right children of the root at level 1
        traverseSymmetric(root->left, root->right, 1);
        return root;
    }
};

```

#### **Complexity Analysis**

* **Time Complexity:** $O(N)$ — We visit every node exactly once to perform structural processing.
* **Space Complexity:** $O(H) = O(\log N)$ — For a perfect binary tree, the height is strictly bounded logarithmically.

---

### **22. Minimum Number of Operations to Sort a Binary Tree by Level (LeetCode 2471)**

**Pattern Identified:** BFS Combined with Graph Cycle Sorting Minimums

```cpp
// APPROACH:
// 1. Run a level-order BFS to extract the node values of each level into a vector.
// 2. For each level vector, compute the minimum number of swaps required to sort it. 
//    This is a classic graph problem: "Minimum Swaps to Sort an Array".
// 3. To find minimum swaps for a vector:
//    - Pair each element with its initial index, then sort the vector by value.
//    - Track visited elements to discover permutation cycles.
//    - For any cycle of size 'K', the minimum number of swaps needed to fix it is 'K - 1'.
// 4. Accumulate the total swaps needed across all levels.

#include <vector>
#include <queue>
#include <algorithm>

class Solution {
private:
    int minSwapsToSort(std::vector<int>& arr) {
        int n = arr.size();
        std::vector<std::pair<int, int>> curPos(n);
        for (int i = 0; i < n; i++) {
            curPos[i] = {arr[i], i};
        }
        
        // Sort elements by value to find their target positions
        std::sort(curPos.begin(), curPos.end());
        
        std::vector<bool> visited(n, false);
        int swaps = 0;
        
        for (int i = 0; i < n; i++) {
            // If already processed or already in the correct position
            if (visited[i] || curPos[i].second == i) {
                continue;
            }
            
            // Calculate cycle size
            int cycle_size = 0;
            int j = i;
            while (!visited[j]) {
                visited[j] = true;
                j = curPos[j].second; // Move to the next index in the cycle
                cycle_size++;
            }
            
            if (cycle_size > 0) {
                swaps += (cycle_size - 1);
            }
        }
        return swaps;
    }

public:
    int minimumOperations(TreeNode* root) {
        if (!root) return 0;
        
        std::queue<TreeNode*> q;
        q.push(root);
        int totalOperations = 0;
        
        while (!q.empty()) {
            int size = q.size();
            std::vector<int> levelValues;
            
            for (int i = 0; i < size; i++) {
                TreeNode* curr = q.front();
                q.pop();
                levelValues.push_back(curr->val);
                
                if (curr->left) q.push(curr->left);
                if (curr->right) q.push(curr->right);
            }
            
            totalOperations += minSwapsToSort(levelValues);
        }
        
        return totalOperations;
    }
};

```

#### **Complexity Analysis**

* **Time Complexity:** $O(N \log N)$ — Extracting values via BFS takes $O(N)$ time, but sorting the level elements to calculate swap cycles introduces an $O(W \log W)$ factor per level. Summed up across all levels, this matches $O(N \log N)$.
* **Space Complexity:** $O(N)$ — To hold elements inside the queue and tracking arrays.

---

To help you visualize how these different views and traversals look when stepping through a tree, I've put together an interactive tree visualization tool below. You can change the tree layout and select different algorithms to see how the nodes are read spatially.


Tackling 11 problems at once is an absolute marathon, but it is the best way to burn these patterns into your memory. We can group these problems into three distinct conceptual buckets: **Root-to-Leaf Paths (DFS Backtracking)**, **Lowest Common Ancestor (DFS Postorder)**, and **Graph Conversions (DFS + BFS)**.

Here are the complete, structured solutions for all of them.

### **Part 1: Root-to-Leaf Path Patterns (DFS Backtracking)**

These problems require carrying a "state" (a path, a sum, or a string) down the tree and evaluating it when you hit a leaf.

#### **23. Print Root to Leaf Path in Binary Tree**

**Pattern Identified:** DFS (Pre-order with Backtracking)

C++

```
// APPROACH:
// 1. Traverse using DFS, passing the current path vector by reference to save memory.
// 2. Base case: If we hit a leaf, append the current path to our result.
// 3. Recursive step: Go left and right.
// 4. Backtracking: Pop the current node's value from the path before returning to the parent.

class Solution {
private:
    void dfs(TreeNode* node, vector<int>& path, vector<vector<int>>& res) {
        if (!node) return;
        
        path.push_back(node->val);
        
        // Leaf node check
        if (!node->left && !node->right) {
            res.push_back(path);
        } else {
            dfs(node->left, path, res);
            dfs(node->right, path, res);
        }
        
        path.pop_back(); // Backtrack
    }

public:
    vector<vector<int>> binaryTreePaths(TreeNode* root) {
        vector<vector<int>> res;
        vector<int> path;
        dfs(root, path, res);
        return res;
    }
};

// Time Complexity: O(N) to visit nodes, but O(N^2) worst case to copy paths to result in a skewed tree.
// Space Complexity: O(H) for the recursion stack and current path.
```

#### **24. Path Sum II (Leetcode 113)**

**Pattern Identified:** DFS (Pre-order with Backtracking & State)

C++

```
// APPROACH:
// 1. Identical to Problem 23, but we subtract the node's value from targetSum as we go down.
// 2. At a leaf node, if targetSum == node->val, we found a valid path.
// 3. Backtrack after exploring children.

class Solution {
private:
    void dfs(TreeNode* node, int target, vector<int>& path, vector<vector<int>>& res) {
        if (!node) return;
        
        path.push_back(node->val);
        if (!node->left && !node->right && target == node->val) {
            res.push_back(path);
        } else {
            dfs(node->left, target - node->val, path, res);
            dfs(node->right, target - node->val, path, res);
        }
        path.pop_back();
    }

public:
    vector<vector<int>> pathSum(TreeNode* root, int targetSum) {
        vector<vector<int>> res;
        vector<int> path;
        dfs(root, targetSum, path, res);
        return res;
    }
};

// Time Complexity: O(N) to visit, O(N^2) worst case due to path copying.
// Space Complexity: O(H) auxiliary space for recursion.
```

#### **25. Sum Root to Leaf Numbers (Leetcode 129)**

**Pattern Identified:** DFS (Pre-order with Running Total)

C++

```
// APPROACH:
// 1. Pass down a running sum.
// 2. Current sum at a node = (parent sum * 10) + current node value.
// 3. Return the sum when hitting a leaf. Sum left and right children.

class Solution {
private:
    int dfs(TreeNode* node, int currentSum) {
        if (!node) return 0;
        
        currentSum = currentSum * 10 + node->val;
        
        if (!node->left && !node->right) return currentSum;
        
        return dfs(node->left, currentSum) + dfs(node->right, currentSum);
    }

public:
    int sumNumbers(TreeNode* root) {
        return dfs(root, 0);
    }
};

// Time Complexity: O(N), we visit every node once.
// Space Complexity: O(H) for the recursion stack.
```

#### **26. Pseudo-Palindromic Paths in a Binary Tree (Leetcode 1457)**

**Pattern Identified:** DFS with Bitmasking (Frequency Parity)

C++

```
// APPROACH:
// 1. A path can form a palindrome if at most ONE digit has an odd frequency.
// 2. Instead of a hash map, use an integer bitmask where the i-th bit represents the parity of digit i.
// 3. Toggle the bit using XOR (^).
// 4. At a leaf, check if the mask has at most one set bit using `(mask & (mask - 1)) == 0`.

class Solution {
private:
    int dfs(TreeNode* node, int mask) {
        if (!node) return 0;
        
        mask ^= (1 << node->val); // Toggle the bit for this digit
        
        if (!node->left && !node->right) {
            // Check if at most one bit is set
            return (mask & (mask - 1)) == 0 ? 1 : 0;
        }
        
        return dfs(node->left, mask) + dfs(node->right, mask);
    }

public:
    int pseudoPalindromicPaths (TreeNode* root) {
        return dfs(root, 0);
    }
};

// Time Complexity: O(N). Bitwise operations are O(1).
// Space Complexity: O(H) for the recursion stack.
```

#### **27. Smallest String Starting From Leaf (Leetcode 988)**

**Pattern Identified:** DFS (Bottom-Up Comparison)

C++

```
// APPROACH:
// 1. Build the string from top to bottom, but prepend the character.
// 2. At a leaf, update the global minimum string.
// 3. Compare lexicographically using standard string comparison.

class Solution {
private:
    string smallest = "~"; // '~' is lexicographically greater than 'z'

    void dfs(TreeNode* node, string current) {
        if (!node) return;
        
        current = char('a' + node->val) + current;
        
        if (!node->left && !node->right) {
            if (current < smallest) smallest = current;
        }
        
        dfs(node->left, current);
        dfs(node->right, current);
    }

public:
    string smallestFromLeaf(TreeNode* root) {
        dfs(root, "");
        return smallest;
    }
};

// Time Complexity: O(N * H) string concatenation takes O(H) time at each step.
// Space Complexity: O(H^2) string copies on the call stack in worst case.
```

### **Part 2: Lowest Common Ancestor Patterns (DFS Postorder)**

These require bubbling information up from the leaves to the root.

#### **28. Lowest Common Ancestor of a Binary Tree (Leetcode 236)**

**Pattern Identified:** DFS (Post-order Search)

C++

```
// APPROACH:
// 1. If we find p or q, return that node immediately.
// 2. Search left and right subtrees.
// 3. If both left and right return non-null, the current node is the LCA.
// 4. If only one side returns non-null, pass that non-null node upwards.

class Solution {
public:
    TreeNode* lowestCommonAncestor(TreeNode* root, TreeNode* p, TreeNode* q) {
        if (!root || root == p || root == q) return root;
        
        TreeNode* left = lowestCommonAncestor(root->left, p, q);
        TreeNode* right = lowestCommonAncestor(root->right, p, q);
        
        if (left && right) return root; // Found LCA
        return left ? left : right;     // Bubble up the found target
    }
};

// Time Complexity: O(N).
// Space Complexity: O(H).
```

#### **29. Lowest Common Ancestor of Deepest Leaves (Leetcode 1123)**

**Pattern Identified:** DFS (Post-order with Multiple Returns)

C++

```
// APPROACH:
// 1. We need to know both the depth of the subtrees and the LCA.
// 2. Helper function returns a pair: {height, lca_node}.
// 3. If left height == right height, current node is the LCA for those deepest leaves.
// 4. Otherwise, the LCA is on the side with the greater height.

class Solution {
private:
    pair<int, TreeNode*> dfs(TreeNode* node) {
        if (!node) return {0, nullptr};
        
        auto left = dfs(node->left);
        auto right = dfs(node->right);
        
        if (left.first == right.first) {
            return {left.first + 1, node};
        }
        
        return {max(left.first, right.first) + 1, 
                left.first > right.first ? left.second : right.second};
    }

public:
    TreeNode* lcaDeepestLeaves(TreeNode* root) {
        return dfs(root).second;
    }
};

// Time Complexity: O(N).
// Space Complexity: O(H).
```

#### **30. Step-By-Step Directions From a Binary Tree Node to Another (Leetcode 2096)**

**Pattern Identified:** LCA + DFS String Path Search

C++

```
// APPROACH:
// 1. Find the path from the root to 'startValue' and 'destValue'.
// 2. Remove the common prefix from both paths (this effectively finds the LCA implicitly).
// 3. Every step in the remaining start path becomes 'U' (Up).
// 4. Append the remaining destination path.

class Solution {
private:
    bool getPath(TreeNode* node, int target, string& path) {
        if (!node) return false;
        if (node->val == target) return true;
        
        path.push_back('L');
        if (getPath(node->left, target, path)) return true;
        path.pop_back();
        
        path.push_back('R');
        if (getPath(node->right, target, path)) return true;
        path.pop_back();
        
        return false;
    }

public:
    string getDirections(TreeNode* root, int startValue, int destValue) {
        string startPath = "", destPath = "";
        getPath(root, startValue, startPath);
        getPath(root, destValue, destPath);
        
        int i = 0;
        // Skip common prefix (LCA logic)
        while (i < startPath.size() && i < destPath.size() && startPath[i] == destPath[i]) {
            i++;
        }
        
        // Convert remaining start path to 'U'
        string res(startPath.size() - i, 'U');
        // Add remaining dest path
        res += destPath.substr(i);
        
        return res;
    }
};

// Time Complexity: O(N) to find paths.
// Space Complexity: O(N) for string paths and call stack.
```

### **Part 3: Graph Conversions (Parent Mapping + BFS/DFS)**

Standard trees only point downwards. If you need to search "upwards" and "downwards" simultaneously by distance, map the parents first to treat it like a generic Graph.

#### **31. Print All Nodes at a Distance of K in Binary Tree (Leetcode 863)**

**Pattern Identified:** DFS (Parent Mapping) -> BFS (Level Order Search)

C++

```
// APPROACH:
// 1. Use DFS to populate an unordered_map linking each node to its parent.
// 2. Use BFS starting from the 'target' node, treating left, right, and parent as adjacent nodes.
// 3. Keep a 'visited' set to avoid infinite loops.
// 4. Stop BFS at level K and return the queue contents.

class Solution {
private:
    unordered_map<TreeNode*, TreeNode*> parentMap;
    
    void mapParents(TreeNode* node, TreeNode* parent) {
        if (!node) return;
        parentMap[node] = parent;
        mapParents(node->left, node);
        mapParents(node->right, node);
    }

public:
    vector<int> distanceK(TreeNode* root, TreeNode* target, int k) {
        mapParents(root, nullptr);
        
        queue<TreeNode*> q;
        unordered_set<TreeNode*> visited;
        
        q.push(target);
        visited.insert(target);
        int currentDistance = 0;
        
        while (!q.empty()) {
            if (currentDistance == k) break;
            int size = q.size();
            
            for (int i = 0; i < size; i++) {
                TreeNode* curr = q.front();
                q.pop();
                
                // Check left, right, and parent
                if (curr->left && visited.insert(curr->left).second) q.push(curr->left);
                if (curr->right && visited.insert(curr->right).second) q.push(curr->right);
                if (parentMap[curr] && visited.insert(parentMap[curr]).second) q.push(parentMap[curr]);
            }
            currentDistance++;
        }
        
        vector<int> res;
        while (!q.empty()) {
            res.push_back(q.front()->val);
            q.pop();
        }
        return res;
    }
};

// Time Complexity: O(N) for DFS + O(N) for BFS = O(N).
// Space Complexity: O(N) for map, visited set, and queue.
```

#### **32. Amount of Time for Binary Tree to Be Infected (Leetcode 2385)**

**Pattern Identified:** Graph Conversion + BFS (Identical logic to 31)

C++

```
// APPROACH:
// 1. This is exactly distance K, but we want the MAXIMUM distance from a start node.
// 2. Instead of returning at step K, we run BFS until the queue is empty.
// 3. The number of BFS levels - 1 is the total time to burn/infect.

class Solution {
public:
    int amountOfTime(TreeNode* root, int start) {
        unordered_map<int, vector<int>> graph;
        
        // Build Undirected Graph using BFS/DFS
        function<void(TreeNode*)> buildGraph = [&](TreeNode* node) {
            if (!node) return;
            if (node->left) {
                graph[node->val].push_back(node->left->val);
                graph[node->left->val].push_back(node->val);
                buildGraph(node->left);
            }
            if (node->right) {
                graph[node->val].push_back(node->right->val);
                graph[node->right->val].push_back(node->val);
                buildGraph(node->right);
            }
        };
        buildGraph(root);
        
        // BFS to find max depth
        queue<int> q;
        unordered_set<int> visited;
        q.push(start);
        visited.insert(start);
        
        int minutes = -1; // -1 because the 0th minute infects the start node
        
        while (!q.empty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                int curr = q.front();
                q.pop();
                
                for (int neighbor : graph[curr]) {
                    if (visited.insert(neighbor).second) {
                        q.push(neighbor);
                    }
                }
            }
            minutes++;
        }
        
        return minutes == -1 ? 0 : minutes;
    }
};

// Time Complexity: O(N) to build graph + O(N) BFS.
// Space Complexity: O(N) for adjacency list graph and queue.
```

#### **33. Number of Good Leaf Nodes Pairs (Leetcode 1530)**

**Pattern Identified:** DFS Postorder (Distance Array Return)

C++

```
// APPROACH:
// 1. Postorder DFS: return a list of distances of all leaves in a subtree relative to the current node.
// 2. At each node, compare the leaf distances from the left subtree with the right subtree.
// 3. If left_dist + right_dist <= distance, increment the 'good pairs' count.
// 4. Increment all distances by 1 before returning to the parent.

class Solution {
private:
    int result = 0;
    
    vector<int> dfs(TreeNode* node, int distance) {
        if (!node) return {};
        if (!node->left && !node->right) return {1}; // Leaf is distance 1 from its parent
        
        vector<int> leftDistances = dfs(node->left, distance);
        vector<int> rightDistances = dfs(node->right, distance);
        
        // Count pairs
        for (int l : leftDistances) {
            for (int r : rightDistances) {
                if (l + r <= distance) {
                    result++;
                }
            }
        }
        
        // Prepare distances for the parent node
        vector<int> currentDistances;
        for (int l : leftDistances) {
            if (l + 1 < distance) currentDistances.push_back(l + 1);
        }
        for (int r : rightDistances) {
            if (r + 1 < distance) currentDistances.push_back(r + 1);
        }
        
        return currentDistances;
    }

public:
    int countPairs(TreeNode* root, int distance) {
        dfs(root, distance);
        return result;
    }
};

// Time Complexity: O(N * D^2), where D is the given 'distance'. Because D <= 10 in the constraints, it's effectively O(N).
// Space Complexity: O(H) for recursion.
```


