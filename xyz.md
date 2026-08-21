# DSA & OA Micro-Patterns — C++ Reference

A dense, problem-driven catalog of the small implementation idioms you learn by
solving problems. Each entry is: **when you see X → do Y**, with a tight C++ snippet.

Assumes you have this at the top of your file:

```cpp
#include <bits/stdc++.h>
using namespace std;
using ll = long long;
#define all(v) (v).begin(), (v).end()
const int MOD = 1e9 + 7;
```

---

## 1. Arrays & Strings

**Prefix sum for O(1) range sum**
```cpp
vector<ll> pre(n + 1, 0);
for (int i = 0; i < n; i++) pre[i + 1] = pre[i] + a[i];
// sum of a[l..r] inclusive:
ll s = pre[r + 1] - pre[l];
```

**Difference array for O(1) range update, then one pass to finalize**
```cpp
vector<ll> diff(n + 1, 0);
// add val to [l, r]:
diff[l] += val; diff[r + 1] -= val;
// materialize:
for (int i = 1; i < n; i++) diff[i] += diff[i - 1];
```

**2D prefix sum**
```cpp
// pre[i+1][j+1] = sum of submatrix (0,0)..(i,j)
pre[i+1][j+1] = a[i][j] + pre[i][j+1] + pre[i+1][j] - pre[i][j];
// rectangle (r1,c1)..(r2,c2):
ll s = pre[r2+1][c2+1] - pre[r1][c2+1] - pre[r2+1][c1] + pre[r1][c1];
```

**Variable-size sliding window skeleton** (expand right, shrink left)
```cpp
int l = 0; ll cur = 0, best = 0;
for (int r = 0; r < n; r++) {
    cur += a[r];
    while (cur > K) { cur -= a[l]; l++; }   // shrink until valid
    best = max(best, (ll)(r - l + 1));
}
```

**"Exactly K" = atMost(K) − atMost(K−1)** (subarray counting)
```cpp
auto atMost = [&](int k) -> ll {
    if (k < 0) return 0LL;
    int l = 0; ll cnt = 0, distinct = 0;
    unordered_map<int,int> f;
    for (int r = 0; r < n; r++) {
        if (f[a[r]]++ == 0) distinct++;
        while (distinct > k) { if (--f[a[l]] == 0) distinct--; l++; }
        cnt += r - l + 1;   // # of valid subarrays ending at r
    }
    return cnt;
};
ll exactlyK = atMost(K) - atMost(K - 1);
```

**Kadane's max subarray**
```cpp
ll best = a[0], cur = a[0];
for (int i = 1; i < n; i++) {
    cur = max((ll)a[i], cur + a[i]);   // extend or restart
    best = max(best, cur);
}
```

**Kadane circular** = max(normalKadane, total − minSubarray). Edge case: if all
negative, answer is the normal (non-circular) Kadane.
```cpp
ll total = accumulate(all(a), 0LL);
ll maxK = kadaneMax(a), minK = kadaneMin(a);
ll ans = (maxK < 0) ? maxK : max(maxK, total - minK);
```

**Dutch national flag (3-way partition, sort 0/1/2 in one pass)**
```cpp
int lo = 0, mid = 0, hi = n - 1;
while (mid <= hi) {
    if (a[mid] == 0) swap(a[lo++], a[mid++]);
    else if (a[mid] == 2) swap(a[mid], a[hi--]); // don't advance mid
    else mid++;
}
```

**Index-as-hash: mark seen by negation** (values in [1, n])
```cpp
for (int i = 0; i < n; i++) {
    int idx = abs(a[i]) - 1;
    if (a[idx] < 0) { /* a[idx] value is a duplicate */ }
    else a[idx] = -a[idx];
}
```

**Cyclic sort: place value x at index x−1** (find missing/duplicate in [1,n])
```cpp
for (int i = 0; i < n; i++)
    while (a[i] != a[a[i] - 1]) swap(a[i], a[a[i] - 1]);
// now first i where a[i] != i+1 is the missing one
```

**Rolling hash (Rabin-Karp)**
```cpp
const ll B = 131, M = 1e9 + 7;
ll h = 0, power = 1;
for (int i = 0; i < len; i++) h = (h * B + s[i]) % M;      // window hash
for (int i = 0; i < len - 1; i++) power = power * B % M;    // B^(len-1)
// slide: remove s[i], add s[i+len]
h = ((h - s[i] * power % M + M) % M * B + s[i + len]) % M;
```

---

## 2. Sorting & Searching

**Binary search on the answer** — the single most common OA pattern. Find the
boundary where a monotone predicate flips false→true.
```cpp
int lo = minAns, hi = maxAns, ans = -1;
while (lo <= hi) {
    int mid = lo + (hi - lo) / 2;        // overflow-safe midpoint
    if (feasible(mid)) { ans = mid; hi = mid - 1; }  // want smallest feasible
    else lo = mid + 1;
}
```

**lower_bound / upper_bound semantics** (avoid off-by-one)
```cpp
// first index with a[i] >= x
int i = lower_bound(all(a), x) - a.begin();
// first index with a[i] > x
int j = upper_bound(all(a), x) - a.begin();
// count of x present: j - i
```

**Custom comparator — must be strict weak ordering** (return false on equal, or
you can get a runtime crash)
```cpp
sort(all(v), [](const auto& x, const auto& y) {
    if (x.first != y.first) return x.first < y.first;
    return x.second > y.second;   // tie-break, still strict
});
```

**Quickselect (kth smallest, avg O(n))** — or just use nth_element:
```cpp
nth_element(a.begin(), a.begin() + k, a.end());
int kth = a[k];   // 0-indexed kth smallest
```

**Sort indices, not values, when you need original positions**
```cpp
vector<int> idx(n); iota(all(idx), 0);
sort(all(idx), [&](int i, int j){ return a[i] < a[j]; });
```

---

## 3. Hashing

**Count with default-zero map**
```cpp
unordered_map<int,int> cnt;
for (int x : a) cnt[x]++;
```

**Prefix-sum + hashmap → "subarray sum equals K"** (classic OA)
```cpp
unordered_map<ll,int> seen{{0, 1}};   // empty prefix
ll sum = 0, ans = 0;
for (int x : a) {
    sum += x;
    ans += seen[sum - K];   // # of prefixes that make a window == K
    seen[sum]++;
}
```

**Custom hash for pairs / anti-collision seed**
```cpp
struct PairHash {
    size_t operator()(const pair<int,int>& p) const {
        return ((size_t)p.first << 32) ^ (size_t)(unsigned)p.second;
    }
};
unordered_map<pair<int,int>, int, PairHash> mp;
// Anti-hash for plain ints: add a random offset before hashing.
```

**Group by key (anagrams)**
```cpp
unordered_map<string, vector<string>> groups;
for (auto& s : words) { string k = s; sort(all(k)); groups[k].push_back(s); }
```

---

## 4. Stack & Queue

**Monotonic stack — next greater element**
```cpp
vector<int> nge(n, -1);
stack<int> st;                       // stores indices, values decreasing
for (int i = 0; i < n; i++) {
    while (!st.empty() && a[st.top()] < a[i]) { nge[st.top()] = a[i]; st.pop(); }
    st.push(i);
}
```

**Largest rectangle in histogram** — sentinel trick flushes the stack
```cpp
h.push_back(0);                      // sentinel forces final pops
stack<int> st; ll best = 0;
for (int i = 0; i < (int)h.size(); i++) {
    while (!st.empty() && h[st.top()] >= h[i]) {
        int height = h[st.top()]; st.pop();
        int left = st.empty() ? -1 : st.top();
        best = max(best, (ll)height * (i - left - 1));
    }
    st.push(i);
}
```

**Monotonic deque — sliding window maximum**
```cpp
deque<int> dq;                       // indices, values decreasing
for (int i = 0; i < n; i++) {
    if (!dq.empty() && dq.front() <= i - k) dq.pop_front();  // out of window
    while (!dq.empty() && a[dq.back()] <= a[i]) dq.pop_back();
    dq.push_back(i);
    if (i >= k - 1) ans.push_back(a[dq.front()]);
}
```

**Min stack (O(1) min)** — store min alongside
```cpp
stack<pair<int,int>> st;   // {value, min-so-far}
// push: int m = st.empty()? x : min(x, st.top().second); st.push({x,m});
```

---

## 5. Linked Lists

**Dummy head — never special-case the head**
```cpp
ListNode dummy(0); dummy.next = head; ListNode* tail = &dummy;
// build/modify via tail->next, return dummy.next;
```

**Fast/slow pointers — find middle / detect cycle**
```cpp
ListNode *slow = head, *fast = head;
while (fast && fast->next) { slow = slow->next; fast = fast->next->next; }
// slow is middle; if fast==slow at any point, there's a cycle
```

**Reverse a list**
```cpp
ListNode *prev = nullptr, *cur = head;
while (cur) { ListNode* nxt = cur->next; cur->next = prev; prev = cur; cur = nxt; }
// prev is new head
```

**Reverse in groups of k** — count k nodes ahead first; if fewer than k remain,
leave as-is; otherwise reverse the block and recurse/iterate, reconnecting via a
dummy.

---

## 6. Trees (Binary / BST)

**Iterative inorder (stack)** — avoids recursion depth limits
```cpp
stack<TreeNode*> st; TreeNode* cur = root;
while (cur || !st.empty()) {
    while (cur) { st.push(cur); cur = cur->left; }
    cur = st.top(); st.pop();
    visit(cur->val);
    cur = cur->right;
}
```

**Validate BST — pass down (min, max) bounds**, not just compare with parent
```cpp
bool valid(TreeNode* r, long lo, long hi) {
    if (!r) return true;
    if (r->val <= lo || r->val >= hi) return false;
    return valid(r->left, lo, r->val) && valid(r->right, r->val, hi);
}
// call valid(root, LONG_MIN, LONG_MAX);
```

**Height + diameter in one pass** (return height, update global diameter)
```cpp
int diameter = 0;
int height(TreeNode* r) {
    if (!r) return 0;
    int L = height(r->left), R = height(r->right);
    diameter = max(diameter, L + R);      // path through this node
    return 1 + max(L, R);
}
```

**Max path sum with negatives** — clamp child contributions at 0
```cpp
int best = INT_MIN;
int gain(TreeNode* r) {
    if (!r) return 0;
    int L = max(0, gain(r->left));        // drop negative branches
    int R = max(0, gain(r->right));
    best = max(best, r->val + L + R);     // path that turns at r
    return r->val + max(L, R);            // path continuing upward
}
```

**LCA in a general binary tree**
```cpp
TreeNode* lca(TreeNode* r, TreeNode* p, TreeNode* q) {
    if (!r || r == p || r == q) return r;
    TreeNode* L = lca(r->left, p, q);
    TreeNode* R = lca(r->right, p, q);
    if (L && R) return r;                 // split point
    return L ? L : R;
}
```

**LCA in a BST** — walk down using value ordering
```cpp
while (root) {
    if (p->val < root->val && q->val < root->val) root = root->left;
    else if (p->val > root->val && q->val > root->val) root = root->right;
    else return root;                     // split → this is the LCA
}
```

**Level-order with level separation** — snapshot queue size per level
```cpp
queue<TreeNode*> q; q.push(root);
while (!q.empty()) {
    int sz = q.size();                    // fixes this level's node count
    for (int i = 0; i < sz; i++) {
        TreeNode* nd = q.front(); q.pop();
        if (nd->left) q.push(nd->left);
        if (nd->right) q.push(nd->right);
    }
}
```

**Morris inorder (O(1) space)** — thread the tree via predecessor links
```cpp
TreeNode* cur = root;
while (cur) {
    if (!cur->left) { visit(cur->val); cur = cur->right; }
    else {
        TreeNode* pre = cur->left;
        while (pre->right && pre->right != cur) pre = pre->right;
        if (!pre->right) { pre->right = cur; cur = cur->left; }   // thread
        else { pre->right = nullptr; visit(cur->val); cur = cur->right; } // unthread
    }
}
```

**Trie node**
```cpp
struct Trie {
    Trie* nxt[26] = {};
    bool end = false;
    void insert(const string& s) {
        Trie* t = this;
        for (char c : s) { int i = c - 'a'; if (!t->nxt[i]) t->nxt[i] = new Trie(); t = t->nxt[i]; }
        t->end = true;
    }
};
```

---

## 7. Heaps / Priority Queue

**Min-heap declaration** (default is max-heap)
```cpp
priority_queue<int, vector<int>, greater<int>> minHeap;
```

**Top-K largest — keep a min-heap of size K**
```cpp
priority_queue<int, vector<int>, greater<int>> pq;
for (int x : a) { pq.push(x); if ((int)pq.size() > K) pq.pop(); }
// pq now holds the K largest; pq.top() is the Kth largest
```

**Running median — two heaps** (max-heap for low half, min-heap for high half)
```cpp
priority_queue<int> lo;                              // max-heap
priority_queue<int, vector<int>, greater<int>> hi;   // min-heap
auto add = [&](int x) {
    lo.push(x);
    hi.push(lo.top()); lo.pop();            // balance value
    if (hi.size() > lo.size()) { lo.push(hi.top()); hi.pop(); } // sizes
};
// median = lo.size() > hi.size() ? lo.top() : (lo.top()+hi.top())/2.0;
```

**K-way merge (merge k sorted lists)** — heap of (value, listIdx, elemIdx)
```cpp
using T = tuple<int,int,int>;
priority_queue<T, vector<T>, greater<T>> pq;
for (int i = 0; i < k; i++) if (!lists[i].empty()) pq.push({lists[i][0], i, 0});
while (!pq.empty()) {
    auto [val, li, ei] = pq.top(); pq.pop();
    out.push_back(val);
    if (ei + 1 < (int)lists[li].size()) pq.push({lists[li][ei+1], li, ei+1});
}
```

**Lazy deletion** — when you can't remove from the middle of a heap, push a
"delete this later" marker and skip stale entries when they surface at the top.

---

## 8. Graphs

**Grid as graph — direction array**
```cpp
int dx[] = {-1, 1, 0, 0}, dy[] = {0, 0, -1, 1};
for (int d = 0; d < 4; d++) {
    int nx = x + dx[d], ny = y + dy[d];
    if (nx < 0 || ny < 0 || nx >= R || ny >= C) continue;   // bounds first
    // ... use (nx, ny)
}
// 8-directional: int dx[]={-1,-1,-1,0,0,1,1,1}, dy[]={-1,0,1,-1,1,-1,0,1};
```

**Encode cell as a single int** (for visited sets / DSU)
```cpp
int id = r * C + c;      // decode: r = id / C, c = id % C;
```

**Multi-source BFS** — push all sources at distance 0, expand together
(rotting oranges, nearest 0, etc.)
```cpp
queue<pair<int,int>> q;
for (all source cells) { q.push({r,c}); dist[r][c] = 0; }
while (!q.empty()) { auto [x,y] = q.front(); q.pop(); /* relax neighbors */ }
```

**Union-Find / DSU** — path compression + union by size
```cpp
struct DSU {
    vector<int> p, sz;
    DSU(int n): p(n), sz(n, 1) { iota(all(p), 0); }
    int find(int x) { return p[x] == x ? x : p[x] = find(p[x]); }
    bool unite(int a, int b) {
        a = find(a); b = find(b);
        if (a == b) return false;
        if (sz[a] < sz[b]) swap(a, b);
        p[b] = a; sz[a] += sz[b];
        return true;
    }
};
```

**Topological sort — Kahn's (BFS on in-degree)**; if fewer than n popped → cycle
```cpp
vector<int> indeg(n, 0), order;
for (auto& [u, v] : edges) indeg[v]++;
queue<int> q;
for (int i = 0; i < n; i++) if (!indeg[i]) q.push(i);
while (!q.empty()) {
    int u = q.front(); q.pop(); order.push_back(u);
    for (int v : adj[u]) if (--indeg[v] == 0) q.push(v);
}
bool hasCycle = (int)order.size() < n;
```

**Cycle detection in a directed graph (DFS colors)** — 0=white,1=gray,2=black
```cpp
vector<int> color(n, 0);
function<bool(int)> dfs = [&](int u) {
    color[u] = 1;                          // gray = on current path
    for (int v : adj[u]) {
        if (color[v] == 1) return true;    // back-edge → cycle
        if (color[v] == 0 && dfs(v)) return true;
    }
    color[u] = 2;                          // done
    return false;
};
```

**Dijkstra (non-negative weights)** — lazy version with a min-heap
```cpp
vector<ll> dist(n, LLONG_MAX);
priority_queue<pair<ll,int>, vector<pair<ll,int>>, greater<>> pq;
dist[src] = 0; pq.push({0, src});
while (!pq.empty()) {
    auto [d, u] = pq.top(); pq.pop();
    if (d > dist[u]) continue;             // stale entry, skip
    for (auto [v, w] : adj[u])
        if (dist[u] + w < dist[v]) { dist[v] = dist[u] + w; pq.push({dist[v], v}); }
}
```

**0-1 BFS (weights only 0 or 1)** — deque; push_front for 0, push_back for 1
```cpp
deque<int> dq; dist[src] = 0; dq.push_front(src);
while (!dq.empty()) {
    int u = dq.front(); dq.pop_front();
    for (auto [v, w] : adj[u]) if (dist[u] + w < dist[v]) {
        dist[v] = dist[u] + w;
        if (w == 0) dq.push_front(v); else dq.push_back(v);
    }
}
```

**Bellman-Ford (negative edges, detect negative cycle)**
```cpp
vector<ll> dist(n, LLONG_MAX); dist[src] = 0;
for (int i = 0; i < n - 1; i++)
    for (auto& [u, v, w] : edges)
        if (dist[u] != LLONG_MAX && dist[u] + w < dist[v]) dist[v] = dist[u] + w;
// one more pass: any relaxation now ⇒ negative cycle
```

**Floyd-Warshall (all pairs)** — k loop MUST be outermost
```cpp
for (int k = 0; k < n; k++)
  for (int i = 0; i < n; i++)
    for (int j = 0; j < n; j++)
      if (d[i][k] + d[k][j] < d[i][j]) d[i][j] = d[i][k] + d[k][j];
```

**Kruskal MST** — sort edges, union greedily
```cpp
sort(all(edges));                          // by weight
DSU dsu(n); ll cost = 0;
for (auto& [w, u, v] : edges) if (dsu.unite(u, v)) cost += w;
```

**Bipartite check (2-coloring via BFS)**
```cpp
vector<int> col(n, -1);
for (int s = 0; s < n; s++) if (col[s] == -1) {
    queue<int> q; q.push(s); col[s] = 0;
    while (!q.empty()) {
        int u = q.front(); q.pop();
        for (int v : adj[u]) {
            if (col[v] == -1) { col[v] = col[u] ^ 1; q.push(v); }
            else if (col[v] == col[u]) { /* not bipartite */ }
        }
    }
}
```

**BFS with extra state** (keys/doors, remaining fuel) — state = (cell, mask).
Make `visited` keyed on the full state, not just the cell.
```cpp
// visited[r][c][mask]; queue holds {r, c, keysMask, steps}
```

---

## 9. Dynamic Programming

**Recognize the target complexity from constraints:**
`n ≤ 20` → bitmask DP / subset. `n ≤ 40` → meet-in-the-middle.
`n ≤ 500` → O(n³). `n ≤ 5000` → O(n²). `n ≤ 1e5` → O(n log n).

**House robber (1D, take-or-skip)**
```cpp
ll take = 0, skip = 0;
for (int x : a) { ll nt = skip + x; skip = max(skip, take); take = nt; }
ll ans = max(take, skip);
```

**0/1 knapsack — iterate weight DESCENDING to reuse each item once**
```cpp
vector<ll> dp(W + 1, 0);
for (int i = 0; i < n; i++)
    for (int w = W; w >= wt[i]; w--)          // descending!
        dp[w] = max(dp[w], dp[w - wt[i]] + val[i]);
```

**Unbounded knapsack / coin change — iterate weight ASCENDING**
```cpp
for (int i = 0; i < n; i++)
    for (int w = coin[i]; w <= W; w++)        // ascending → reuse allowed
        dp[w] = min(dp[w], dp[w - coin[i]] + 1);
```

**Subset-sum / partition feasibility — boolean DP**
```cpp
vector<char> dp(S + 1, 0); dp[0] = 1;
for (int x : a) for (int s = S; s >= x; s--) dp[s] |= dp[s - x];
```

**LIS in O(n log n)** — patience sorting; `tails[i]` = smallest tail of an
increasing subsequence of length i+1
```cpp
vector<int> tails;
for (int x : a) {
    auto it = lower_bound(all(tails), x);     // use upper_bound for non-strict
    if (it == tails.end()) tails.push_back(x);
    else *it = x;
}
int lis = tails.size();
```

**LCS / edit distance — 2D grid, fill from top-left**
```cpp
for (int i = 1; i <= n; i++)
  for (int j = 1; j <= m; j++)
    dp[i][j] = (s[i-1] == t[j-1]) ? dp[i-1][j-1] + 1
                                  : max(dp[i-1][j], dp[i][j-1]);
```

**Interval DP — grow by length, split at k** (matrix chain, burst balloons)
```cpp
for (int len = 2; len <= n; len++)
  for (int i = 0; i + len - 1 < n; i++) {
      int j = i + len - 1;
      for (int k = i; k < j; k++)
          dp[i][j] = min(dp[i][j], dp[i][k] + dp[k+1][j] + cost(i, k, j));
  }
```

**Bitmask DP (TSP)** — dp[mask][i] = min cost visiting `mask`, ending at i
```cpp
vector<vector<ll>> dp(1 << n, vector<ll>(n, LLONG_MAX));
dp[1][0] = 0;
for (int mask = 1; mask < (1 << n); mask++)
  for (int i = 0; i < n; i++) if (dp[mask][i] != LLONG_MAX && (mask >> i & 1))
    for (int j = 0; j < n; j++) if (!(mask >> j & 1))
      dp[mask | 1<<j][j] = min(dp[mask | 1<<j][j], dp[mask][i] + cost[i][j]);
```

**Digit DP skeleton** — state (pos, tight, ... ), iterate allowed digits
```cpp
// dp over positions of the number; `tight` restricts the current digit to <= num[pos]
ll go(int pos, bool tight, /* other state */) {
    if (pos == len) return 1;                 // one valid number formed
    int hi = tight ? num[pos] : 9;
    ll res = 0;
    for (int d = 0; d <= hi; d++)
        res += go(pos + 1, tight && (d == hi), /* update state */);
    return res;
}
```

---

## 10. Greedy

**Interval scheduling (max non-overlapping)** — sort by END time
```cpp
sort(all(iv), [](auto& a, auto& b){ return a.second < b.second; });
int end = INT_MIN, cnt = 0;
for (auto& [s, e] : iv) if (s >= end) { cnt++; end = e; }
```

**Merge intervals** — sort by START, extend or push
```cpp
sort(all(iv));
vector<pair<int,int>> out;
for (auto& [s, e] : iv) {
    if (!out.empty() && s <= out.back().second) out.back().second = max(out.back().second, e);
    else out.push_back({s, e});
}
```

**Meeting rooms II (min rooms)** — sweep starts/ends, or min-heap of end times
```cpp
priority_queue<int, vector<int>, greater<int>> pq;   // end times in use
sort(all(iv));
for (auto& [s, e] : iv) {
    if (!pq.empty() && pq.top() <= s) pq.pop();        // reuse a freed room
    pq.push(e);
}
int rooms = pq.size();
```

**Exchange-argument mindset** — when greedy correctness is unclear, ask: "if I
swap an out-of-order adjacent pair, does the answer improve or stay equal?" If
yes, the greedy sort order is justified. (e.g., sort jobs by a ratio/deadline.)

---

## 11. Backtracking

**Universal template — choose / explore / un-choose**
```cpp
void backtrack(vector<int>& path, int start) {
    record(path);                              // or check goal
    for (int i = start; i < n; i++) {
        path.push_back(a[i]);                  // choose
        backtrack(path, i + 1);                // explore (i+1 → no reuse)
        path.pop_back();                       // un-choose
    }
}
```

**Skip duplicates among siblings** (sorted input) — the `i > start` guard
```cpp
sort(all(a));
for (int i = start; i < n; i++) {
    if (i > start && a[i] == a[i - 1]) continue;   // dup as first pick OK, sibling not
    // choose a[i], recurse with i+1, un-choose
}
```

**Permutations with a used[] array**
```cpp
for (int i = 0; i < n; i++) {
    if (used[i]) continue;
    used[i] = true; path.push_back(a[i]);
    backtrack();
    path.pop_back(); used[i] = false;
}
// dedup permutations: also `if (i>0 && a[i]==a[i-1] && !used[i-1]) continue;`
```

**Prune with sort + break** (combination sum) — everything after is bigger
```cpp
sort(all(a));
for (int i = start; i < n; i++) {
    if (a[i] > remaining) break;               // no point continuing
    // choose, recurse with remaining - a[i], un-choose
}
```

**Leading-zero handling in partition problems** (restore IP, split-into-numbers)
```cpp
// segment s[i..j] valid only if single digit or first char != '0'
if (s[i] == '0' && j > i) break;               // "0X" is invalid, stop extending
```

**Word search / grid backtracking** — mutate then restore the cell
```cpp
char tmp = grid[x][y]; grid[x][y] = '#';       // mark visited
// explore 4 neighbors...
grid[x][y] = tmp;                              // restore on backtrack
```

---

## 12. Bit Manipulation

**Core idioms**
```cpp
x & (x - 1)            // clear lowest set bit
x & (-x)               // isolate lowest set bit
x | (1 << i)           // set bit i
x & ~(1 << i)          // clear bit i
x ^ (1 << i)           // toggle bit i
(x >> i) & 1           // read bit i
__builtin_popcount(x)  // # of set bits  (popcountll for long long)
__builtin_ctz(x)       // trailing zeros; __builtin_clz = leading zeros
```

**Check power of two**
```cpp
bool isPow2 = x > 0 && (x & (x - 1)) == 0;
```

**Iterate over all submasks of a mask** (subset-sum DP over subsets)
```cpp
for (int s = mask; s; s = (s - 1) & mask) { /* s is a non-empty submask */ }
```

**Single number (XOR cancels pairs)**
```cpp
int one = 0; for (int x : a) one ^= x;   // the unique unpaired element
```

**Enumerate all subsets of n elements**
```cpp
for (int mask = 0; mask < (1 << n); mask++)
    for (int i = 0; i < n; i++) if (mask >> i & 1) { /* i is in subset */ }
```

---

## 13. Math / Number Theory

**GCD / LCM** (watch overflow on LCM → cast to ll)
```cpp
ll g = __gcd(a, b);
ll l = a / g * b;      // divide first to avoid overflow
```

**Sieve of Eratosthenes**
```cpp
vector<char> isComp(N + 1, 0);
for (int i = 2; (ll)i * i <= N; i++)
    if (!isComp[i]) for (int j = i * i; j <= N; j += i) isComp[j] = 1;
```

**Modular arithmetic — keep everything non-negative**
```cpp
ll add(ll a, ll b){ return (a + b) % MOD; }
ll sub(ll a, ll b){ return ((a - b) % MOD + MOD) % MOD; }
ll mul(ll a, ll b){ return a % MOD * (b % MOD) % MOD; }
```

**Fast (binary) exponentiation**
```cpp
ll power(ll b, ll e, ll m) {
    ll r = 1; b %= m;
    while (e) { if (e & 1) r = r * b % m; b = b * b % m; e >>= 1; }
    return r;
}
```

**Modular inverse (prime MOD, Fermat)**
```cpp
ll inv(ll a) { return power(a, MOD - 2, MOD); }
```

**nCr mod p** — precompute factorials + inverse factorials
```cpp
vector<ll> fact(N), invf(N);
fact[0] = 1; for (int i = 1; i < N; i++) fact[i] = fact[i-1] * i % MOD;
invf[N-1] = inv(fact[N-1]);
for (int i = N-2; i >= 0; i--) invf[i] = invf[i+1] * (i+1) % MOD;
auto nCr = [&](int n, int r){ return r<0||r>n ? 0 : fact[n]*invf[r]%MOD*invf[n-r]%MOD; };
```

---

## 14. Intervals & Sweep Line

**Count max overlap (min platforms / max concurrent events)** — split into
+1/−1 events, sort, sweep
```cpp
vector<pair<int,int>> ev;                 // {time, +1 start / -1 end}
for (auto& [s, e] : iv) { ev.push_back({s, +1}); ev.push_back({e, -1}); }
sort(all(ev), [](auto& a, auto& b){
    return a.first != b.first ? a.first < b.first : a.second < b.second; // end before start on tie
});
int cur = 0, best = 0;
for (auto& [t, d] : ev) { cur += d; best = max(best, cur); }
```

---

## 15. Specialized Structures (harder OAs)

**Fenwick tree (BIT) — prefix sums with point update, O(log n)**
```cpp
struct BIT {
    int n; vector<ll> t;
    BIT(int n): n(n), t(n + 1, 0) {}
    void update(int i, ll v){ for (++i; i <= n; i += i & -i) t[i] += v; }
    ll query(int i){ ll s = 0; for (++i; i > 0; i -= i & -i) s += t[i]; return s; } // sum [0..i]
    ll range(int l, int r){ return query(r) - (l ? query(l - 1) : 0); }
};
```

**Iterative segment tree (point update, range query)**
```cpp
int sz; vector<ll> t;                     // size 2*sz, leaves at [sz, 2sz)
void build(vector<ll>& a){ sz = a.size(); t.assign(2*sz, 0);
    for (int i = 0; i < sz; i++) t[sz + i] = a[i];
    for (int i = sz - 1; i > 0; i--) t[i] = t[2*i] + t[2*i+1]; }
void update(int p, ll v){ for (t[p += sz] = v; p > 1; p >>= 1) t[p>>1] = t[p] + t[p^1]; }
ll query(int l, int r){ ll res = 0;                         // [l, r)
    for (l += sz, r += sz; l < r; l >>= 1, r >>= 1) {
        if (l & 1) res += t[l++];
        if (r & 1) res += t[--r];
    } return res; }
```

**Sparse table (static range min, O(1) query)**
```cpp
int LOG = 20;
vector<vector<int>> sp(LOG, vector<int>(n));
sp[0] = a;
for (int k = 1; k < LOG; k++)
  for (int i = 0; i + (1<<k) <= n; i++)
    sp[k][i] = min(sp[k-1][i], sp[k-1][i + (1<<(k-1))]);
// query [l, r]: k = log2(r-l+1); min(sp[k][l], sp[k][r-(1<<k)+1]);
```

**KMP prefix function (pattern matching)**
```cpp
vector<int> pi(m, 0);
for (int i = 1; i < m; i++) {
    int j = pi[i - 1];
    while (j && p[i] != p[j]) j = pi[j - 1];
    if (p[i] == p[j]) j++;
    pi[i] = j;
}
```

**Reservoir sampling (pick 1 uniformly from a stream)**
```cpp
int chosen, cnt = 0;
for (int x : stream) { cnt++; if (rand() % cnt == 0) chosen = x; }
```

---

## 16. OA Meta-Skills (what actually decides pass/fail)

**Fast I/O** — always, when input is large
```cpp
ios::sync_with_stdio(false); cin.tie(nullptr);
```

**Overflow discipline** — `int * int` overflows at ~2.1e9. Cast early:
```cpp
ll product = (ll)a * b;        // NOT (ll)(a * b) — the mult already overflowed
```

**Constraint → algorithm cheat sheet**
| n up to | intended complexity |
|---|---|
| ≤ 12 | O(n!) brute permutations |
| ≤ 20 | O(2ⁿ) or O(2ⁿ·n) bitmask |
| ≤ 40 | O(2^(n/2)) meet-in-the-middle |
| ≤ 500 | O(n³) |
| ≤ 5000 | O(n²) |
| ≤ 1e5–1e6 | O(n log n) or O(n) |
| ≤ 1e9 (single) | O(log n) or O(√n) — no array |

**Edge cases to always check:** empty input, n=1, all elements equal, all
negative, duplicates, integer overflow, first/last element, single-node graph,
disconnected graph, self-loops.

**Simulation problems** — most "medium" OA problems are just careful
implementation. Read the statement twice, model state explicitly, don't be
clever. Losing points here is usually a misread requirement, not an algorithm gap.

**Translating the statement** — the real skill is mapping wordy prose to a known
abstraction: "cities and roads" = graph, "minimum cost to reach" = shortest path
or DP, "can we achieve value X" = binary search on the answer, "at most / exactly
K" = sliding window, "arrange to maximize/minimize" = greedy or DP.

---

*End of reference. Keep this open during practice — most of these become muscle
memory after you hit each pattern 3–4 times.*
