# Java Collections & Time Toolkit — for LLD Machine Coding

A reference of the data structures, methods, and APIs that show up over and over in LLD
services. This is organised **by tool**, with each method's signature, what it does, and the
*role* it plays in a service. It deliberately does **not** tell you which problem needs which
tool — that mapping is the exercise.

Mental model for a C++ dev:
- `HashMap` ≈ `unordered_map`, `TreeMap` ≈ `map` (red-black tree, ordered).
- `HashSet` ≈ `unordered_set`, `TreeSet` ≈ `set`.
- `ArrayDeque` ≈ `deque`, `PriorityQueue` ≈ `priority_queue` (but **min-heap by default**).
- `ArrayList` ≈ `vector`. There is no `[]` — use `get(i)` / `set(i, v)`.
- Everything is a reference; there are no value semantics, no operator overloading.

---

## 1. HashMap<K,V>  — the workhorse

`unordered_map`. O(1) average get/put. No ordering guarantee. Allows one null key.

| Method | Role |
|---|---|
| `get(k)` | returns value or **null** if absent (no exception, unlike some langs). |
| `getOrDefault(k, def)` | value if present else `def` — kills most null checks. |
| `put(k, v)` | insert/overwrite; returns the **previous** value or null. |
| `putIfAbsent(k, v)` | only inserts if key absent; returns existing value if present. Atomic on ConcurrentHashMap. |
| `containsKey(k)` / `containsValue(v)` | membership tests. |
| `remove(k)` | delete; returns removed value. |
| `computeIfAbsent(k, key -> newVal)` | if absent, compute & store & return; else return existing. **The idiom for nested maps / multimaps.** |
| `compute(k, (key,old) -> newVal)` | recompute from old value (old may be null). |
| `merge(k, v, (old,v) -> combined)` | if absent put v; else combine old with v. **The idiom for counters / running sums.** |
| `keySet()` / `values()` / `entrySet()` | views for iteration; `entry.getKey()/getValue()`. |
| `size()` / `isEmpty()` / `clear()` | housekeeping. |

Counter idiom: `map.merge(key, 1, Integer::sum);`
Multimap idiom: `map.computeIfAbsent(key, k -> new ArrayList<>()).add(item);`
Nested map idiom: `outer.computeIfAbsent(a, k -> new HashMap<>()).merge(b, amt, Double::sum);`

---

## 2. LinkedHashMap<K,V>  — insertion- or access-ordered map

Iterates in a predictable order. Two superpowers:
- `new LinkedHashMap<>(cap, 0.75f, true)` — the `true` makes it **access-ordered**: any
  `get`/`put` moves the entry to the end (most-recently-used at the tail).
- Override `removeEldestEntry(eldest)` to return true when size exceeds capacity — the map
  auto-evicts the oldest/least-recently-used entry. This is a 5-line LRU cache.

| Method | Role |
|---|---|
| (everything from HashMap) | plus stable iteration order. |
| `removeEldestEntry(Map.Entry)` | override → return `size() > capacity` to auto-evict. |

---

## 3. TreeMap<K,V>  — sorted map (NavigableMap)

`std::map`. Keys kept sorted (natural order or a Comparator). O(log n) ops. The navigation
methods are what make it indispensable for ranges, "nearest", versions, intervals.

| Method | Role |
|---|---|
| `firstKey()` / `lastKey()` | smallest / largest key. |
| `floorKey(k)` / `floorEntry(k)` | greatest key **≤ k** (or null). "Most recent at-or-before." |
| `ceilingKey(k)` / `ceilingEntry(k)` | smallest key **≥ k**. "Next at-or-after." |
| `lowerKey(k)` / `higherKey(k)` | strictly **< k** / strictly **> k**. |
| `headMap(k)` | view of keys < k. `headMap(k, true)` includes k. |
| `tailMap(k)` | view of keys ≥ k. |
| `subMap(lo, hi)` | view of keys in `[lo, hi)`. |
| `descendingMap()` / `descendingKeySet()` | reverse-ordered view (e.g. largest-first greedy). |
| `pollFirstEntry()` / `pollLastEntry()` | remove & return smallest / largest. |

Interval-overlap idiom: to check if `[s,e)` overlaps anything, inspect `floorEntry(s)` and
`ceilingKey(s)` — only the immediate neighbours can collide.

---

## 4. HashSet / LinkedHashSet / TreeSet<E>

- **HashSet** — `unordered_set`; O(1) `add/contains/remove`. Membership & dedup.
- **LinkedHashSet** — HashSet that remembers insertion order.
- **TreeSet** — sorted set with the same navigation methods as TreeMap:
  `first()`, `last()`, `floor(e)`, `ceiling(e)`, `lower(e)`, `higher(e)`,
  `headSet/tailSet/subSet`, `pollFirst()`, `pollLast()`.

Role: "free slots sorted by id", "active floors for an elevator", "unique tags". When you
need *both* sorted order and fast nearest-lookup but no value, reach for TreeSet.

---

## 5. ArrayDeque<E>  — stack + queue + sliding window

`deque`. Faster than `LinkedList` and than `Stack`. No nulls allowed.

| Method | Role |
|---|---|
| `addLast(e)` / `offerLast(e)` | enqueue at tail. |
| `addFirst(e)` / `offerFirst(e)` | push at head. |
| `pollFirst()` / `pollLast()` | dequeue head / tail, null if empty. |
| `peekFirst()` / `peekLast()` | inspect without removing. |
| `push(e)` / `pop()` | stack semantics (operate on the head). |
| `size()` / `isEmpty()` | count. |

Sliding-window idiom: `addLast(now)` on each event; `while(peekFirst() < now - window) pollFirst();`
then `size()` is the count in the window.

---

## 6. PriorityQueue<E>  — heap

`priority_queue`, but **min-heap by default** (smallest on top — opposite of C++).

| Method | Role |
|---|---|
| `new PriorityQueue<>(comparator)` | custom ordering. |
| `offer(e)` / `add(e)` | insert, O(log n). |
| `poll()` | remove & return smallest (per comparator), null if empty. |
| `peek()` | smallest without removing. |
| `size()` | count. |

Max-heap: `new PriorityQueue<>(Collections.reverseOrder())` or
`new PriorityQueue<>((a,b) -> b - a)`.
Two-heap idiom (debt/median/matching): one max-heap of "givers", one of "receivers", poll
both, settle the min, push back the remainder.
Top-k idiom: keep a min-heap of size k; if `size() > k` `poll()` the smallest — the heap
holds the k largest.

---

## 7. Comparators — ordering glue

```java
Comparator.comparingInt(P::score).reversed()          // score desc
          .thenComparing(P::id)                       // tie-break id asc
```

| Builder | Role |
|---|---|
| `Comparator.comparing(keyFn)` | sort by an extracted key. |
| `comparingInt/Long/Double` | primitive-specialised (no boxing). |
| `.reversed()` | flip direction. |
| `.thenComparing(...)` | secondary tie-breaker. |
| `Comparator.naturalOrder()` / `reverseOrder()` | the obvious two. |

Used by `PriorityQueue`, `TreeMap`/`TreeSet` constructors, `list.sort(...)`, `Collections.sort`.
**Gotcha:** never write `(a,b) -> a - b` for large ints — it overflows. Use
`Integer.compare(a,b)`.

---

## 8. Time APIs

### Epoch / monotonic (for durations, TTLs, rate limits)
| Call | Role |
|---|---|
| `System.currentTimeMillis()` | wall-clock ms since 1970. Good for TTLs/expiry. |
| `System.nanoTime()` | **monotonic** ns; only valid for measuring *elapsed* time, not wall clock. |
| `Instant.now()` | modern timestamp; `instant.toEpochMilli()`, `Instant.ofEpochMilli(ms)`. |

### Human dates (for due dates, business rules)
| Type | Role |
|---|---|
| `LocalDate` | date only. `LocalDate.of(2026,6,11)`, `.plusDays(14)`, `.isBefore(other)`. |
| `LocalDateTime` | date + time, no zone. `.plusHours(n)`, `.isAfter(...)`. |
| `Duration` | a span of time. `Duration.between(a,b)`, `.toMinutes()`, `.toHours()`, `.getSeconds()`. |
| `Period` | calendar span (years/months/days) between two `LocalDate`s. |
| `ChronoUnit` | `ChronoUnit.DAYS.between(d1,d2)`, `ChronoUnit.MINUTES.between(...)` — cleanest for "how many X between". |

Round-up-hours idiom: `long hrs = (durationMinutes + 59) / 60;`

---

## 9. Concurrency essentials (for booking / locking services)

| Tool | Role |
|---|---|
| `ConcurrentHashMap<K,V>` | thread-safe map; `putIfAbsent`/`compute`/`merge` are **atomic** — use these instead of check-then-act. |
| `CopyOnWriteArrayList<E>` | thread-safe list cheap for many reads, few writes (subscriber lists). |
| `AtomicInteger` / `AtomicLong` | lock-free counters; `incrementAndGet()`, `getAndIncrement()`, `compareAndSet(exp,new)`. |
| `ReentrantLock` | explicit lock; `lock()` / `unlock()` in try-finally; supports `tryLock(timeout)`. |
| `synchronized (obj) { ... }` | simplest mutual exclusion around a critical section. |

Atomic-claim idiom (lock a resource if free):
`map.putIfAbsent(seat, lock) == null` → you won the claim; non-null → someone holds it.

---

## 10. Optional<T>  — "maybe a value"

Signals "might be absent" in a return type without using null.
`Optional.of(x)`, `Optional.empty()`, `opt.isPresent()`, `opt.orElse(def)`,
`opt.orElseThrow()`, `opt.map(fn)`, `opt.ifPresent(consumer)`. Good for service lookups that
may miss; don't overuse it for fields.

---

## 11. Stream API (sparingly, for aggregation)

Useful for one-liners over collections; don't force it where a plain loop is clearer.

| Snippet | Role |
|---|---|
| `list.stream().filter(p).map(f).collect(toList())` | transform + collect. |
| `.sorted(comparator).limit(k)` | top-k by ordering. |
| `.collect(Collectors.groupingBy(keyFn))` | bucket into `Map<K, List<V>>`. |
| `.collect(Collectors.toMap(k, v))` | build a map (throws on dup keys unless you pass a merge fn). |
| `.mapToInt(x -> x).sum()` | numeric reduce. |
| `.count()` / `.anyMatch(p)` / `.max(cmp)` | aggregates. |

`import static java.util.stream.Collectors.*;` to shorten.

---

## 12. Records & Enums (clean modelling)

```java
record Loan(String member, LocalDate dueDate) {}     // immutable data carrier, auto equals/hashCode/toString
enum SplitType { EQUAL, EXACT, PERCENT }             // fixed set of choices; can carry fields + methods
```

- **record** (Java 16+): perfect for small value objects (a lock record, a versioned value,
  a ticket). Auto-generates constructor, accessors (`loan.member()`), `equals`, `hashCode`.
- **enum**: closed set of constants; can hold fields and per-constant behaviour. Use for
  states, types, directions instead of String/int flags.

---

## 13. Quick "which container?" decision guide

- Need **fast key lookup**, order doesn't matter → `HashMap` / `HashSet`.
- Need **keys/elements sorted** or **nearest-key queries** → `TreeMap` / `TreeSet`.
- Need **most/least-recently-used** behaviour → `LinkedHashMap(accessOrder=true)`.
- Need **smallest/largest repeatedly** (and to remove it) → `PriorityQueue`.
- Need **FIFO / LIFO / sliding window** → `ArrayDeque`.
- Need **thread safety** under contention → `ConcurrentHashMap` + atomics.
- Need **insertion order preserved** → `LinkedHashMap` / `LinkedHashSet` / `ArrayList`.

---

## 14. C++ → Java gotchas that bite during machine coding

- `PriorityQueue` is a **min-heap** by default (C++ is a max-heap).
- No `[]` on lists/maps — `list.get(i)`, `map.get(k)`.
- `==` on objects compares references; use `.equals()` (and `.equals` on boxed `Integer`!).
- Boxed `Integer` caches only −128..127; beyond that `==` fails — always `.equals()` or compare primitives.
- Integer division and overflow are the same as C++ — `a - b` comparators overflow.
- Iterating a collection while modifying it throws `ConcurrentModificationException`; use an
  `Iterator.remove()` or collect-then-remove.
- Default capacity/growth is automatic; no `reserve`. But `new HashMap<>(expectedSize)` helps.
- Strings are immutable; build with `StringBuilder`.
- Autoboxing makes `map.get(k)` return `null` for a missing `int` key — NPE on unboxing if
  you don't `getOrDefault`.
