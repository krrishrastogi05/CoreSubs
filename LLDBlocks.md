# LLD Machine-Coding — Java Building-Block Challenges

Each problem below is a **single service or sub-component** carved out of a classic LLD
problem. You are *not* asked to model the whole system — just to implement the one piece
that carries the real logic. This is deliberate: it forces you to reach for the right Java
collection / time API and use it well, which is exactly the muscle that's underdeveloped
right after a C++ → Java migration.

**How to use this:**
- Read the `Java toolkit hint` comment on top of each skeleton. It names *which* tools are
  relevant but never *how* to wire them — that's your job.
- Implement only the stubbed methods. Don't model extra classes unless the problem needs them.
- Write the 3–4 test scenarios as a `main` and verify by hand before looking anything up.
- If you get stuck on a method name (`computeIfAbsent`? `floorKey`?), go to the companion
  file `java-collections-toolkit.md` — it lists every method and its role but does **not**
  tell you which problem uses it.

**Difficulty ladder:** P1–P6 warm-ups · P7–P13 core · P14–P18 spicy (concurrency / time / multi-map).

---

## P1 — Splitwise: Balance Sheet Service  *(from Splitwise)*

```java
// Java toolkit hint:
//   Map<String, Map<String, Double>>  — nested "who owes whom" ledger
//   computeIfAbsent / merge / getOrDefault — update without null checks
//   enum for split type
```

**Statement.** Implement the core of an expense tracker. When a user pays for a group,
record how much each participant owes the payer. Support `EQUAL`, `EXACT`, and `PERCENT`
splits. Maintain a net balance ledger so that if A owes B 100 and B later owes A 30, the
stored balance is A→B 70 (not two separate entries).

```java
enum SplitType { EQUAL, EXACT, PERCENT }

class BalanceSheet {
    // balances.get(a).get(b) = amount a owes b (positive). Keep it net.

    /** payer paid `amount`; split among `participants` (includes payer) per `type`.
     *  For EQUAL, `values` is ignored. For EXACT, values are amounts; for PERCENT, percentages. */
    void addExpense(String payer, double amount, List<String> participants,
                    SplitType type, List<Double> values) {
        // TODO
    }

    /** net amount `user` owes (negative means others owe `user`). */
    double netBalance(String user) {
        // TODO
        return 0;
    }

    /** all non-zero edges, e.g. "Alice owes Bob 70.0" */
    List<String> showBalances() {
        // TODO
        return null;
    }
}
```

**Test scenarios.**
1. Alice pays 900 EQUAL among {Alice, Bob, Carol} → Bob owes Alice 300, Carol owes Alice 300.
2. Bob then pays 300 EQUAL among {Alice, Bob} → Bob owes Alice 300−150=150; Alice owes Bob 0.
3. `netBalance("Alice")` should be negative (she is net owed).
4. EXACT split where values don't sum to amount → throw / reject.

---

## P2 — Splitwise: Debt Simplification  *(from Splitwise)*

```java
// Java toolkit hint:
//   Map<String, Integer/Double> net-balance per person (credit positive, debt negative)
//   PriorityQueue (max-heap of creditors, max-heap of debtors)
//   greedy matching to minimise transaction count
```

**Statement.** Given the *net* balance of every person (how much they are owed minus how
much they owe; sums to zero), output a minimal set of "X pays Y amount Z" transactions
that settles everyone. Repeatedly match the biggest debtor with the biggest creditor.

```java
class DebtSimplifier {
    /** netOf.get(p) > 0 → p should receive; < 0 → p should pay.
     *  return lines like "Bob pays Alice 150.0". */
    List<String> simplify(Map<String, Double> netOf) {
        // TODO: two heaps, settle min(|debt|, credit) each step
        return null;
    }
}
```

**Test scenarios.**
1. `{A:+100, B:-60, C:-40}` → `B pays A 60`, `C pays A 40` (2 txns).
2. `{A:+30, B:+20, C:-50}` → `C pays A 30`, `C pays B 20`.
3. Already settled `{A:0,B:0}` → empty list.

---

## P3 — LRU Cache  *(from any caching layer)*

```java
// Java toolkit hint:
//   Option A: LinkedHashMap with accessOrder=true + removeEldestEntry
//   Option B: HashMap<K,Node> + your own doubly-linked list (do this one for practice)
//   Every get/put must be O(1).
```

**Statement.** Fixed-capacity cache. `get` returns value or -1 and marks the key as most
recently used. `put` inserts/updates and evicts the least-recently-used key when full.

```java
class LRUCache {
    LRUCache(int capacity) { /* TODO */ }
    int get(int key) { /* TODO */ return -1; }
    void put(int key, int value) { /* TODO */ }
}
```

**Test scenarios** (capacity 2).
1. put(1,1), put(2,2), get(1)→1, put(3,3) evicts 2, get(2)→-1.
2. put(2,2) again, get(1)→-1 (1 was evicted), get(3)→3, get(2)→2.

> Build it both ways. The hand-rolled DLL version is the one interviewers love to watch.

---

## P4 — Cache with TTL (lazy expiry)  *(from in-memory KV store / Redis-lite)*

```java
// Java toolkit hint:
//   Map<K, value>  +  Map<K, Long expiryEpochMillis>   (or a small record holding both)
//   System.currentTimeMillis() for "now"
//   lazy eviction: check expiry on read; no background thread needed
```

**Statement.** Key-value store where each key can carry a time-to-live. `get` must behave
as if expired keys never existed. Implement *lazy* expiry (clean up on access), then add a
`size()` that reflects only live keys.

```java
class TtlCache<K, V> {
    void put(K key, V value, long ttlMillis) { /* TODO */ }
    void put(K key, V value)                 { /* no expiry */ }
    V get(K key)                             { /* TODO */ return null; }
    int size()                               { /* live keys only */ return 0; }
}
```

**Test scenarios.**
1. put("a","x",50); get→"x"; sleep 60ms; get→null; size→0.
2. put("b","y") (no ttl); after 100ms still present.
3. Overwriting a key with a new ttl resets its clock.

---

## P5 — Token-Bucket Rate Limiter  *(from API gateway / rate limiter)*

```java
// Java toolkit hint:
//   no collection needed — just doubles + a timestamp
//   System.nanoTime() or currentTimeMillis() to compute elapsed
//   refill lazily: tokens += elapsed * ratePerSec, capped at capacity
```

**Statement.** Allow at most `capacity` requests in a burst, refilling at `refillPerSec`
tokens/second. `allow()` returns true and consumes one token if available.

```java
class TokenBucket {
    TokenBucket(double capacity, double refillPerSec) { /* TODO */ }
    boolean allow() { /* TODO: refill based on elapsed time, then try to spend 1 */ return false; }
}
```

**Test scenarios.**
1. capacity 3, refill 1/s: first 3 `allow()` → true, 4th → false.
2. wait ~1s → exactly 1 more allowed.
3. Long idle does **not** let tokens exceed capacity.

---

## P6 — Sliding-Window Rate Limiter  *(from rate limiter)*

```java
// Java toolkit hint:
//   Map<String userId, Deque<Long> timestamps>
//   ArrayDeque: addLast(now), peekFirst() to drop stale, size() = current count
//   evict timestamps older than (now - windowMillis)
```

**Statement.** Per-user limiter: at most `maxReq` requests within a rolling
`windowMillis`. Unlike token bucket, this counts actual request timestamps.

```java
class SlidingWindowLimiter {
    SlidingWindowLimiter(int maxReq, long windowMillis) { /* TODO */ }
    boolean allow(String userId, long now) { /* TODO */ return false; }
}
```

**Test scenarios.**
1. max 2 / 1000ms: allow("u",0)→T, allow("u",500)→T, allow("u",900)→F.
2. allow("u",1100)→T (the t=0 request fell out of the window).
3. Two different users don't share a window.

---

## P7 — Parking Lot: Nearest-Slot Allocation  *(from Parking Lot)*

```java
// Java toolkit hint:
//   per vehicle-size: a min-heap OR TreeSet<Integer> of FREE slot ids
//   PriorityQueue / TreeSet.first() gives the nearest (lowest id) slot in O(log n)
//   Map<ticketId, slotId> to find the slot again on exit
```

**Statement.** A flat lot has `n` slots (ids 0..n-1). `park` must return the **smallest**
free slot id (nearest to entrance) and issue a ticket. `leave(ticketId)` frees the slot so
it can be reused — and a freed low-id slot must be handed out before higher ones.

```java
class ParkingLot {
    ParkingLot(int n) { /* TODO */ }
    /** returns ticketId, or -1 if full */
    int park() { /* TODO */ return -1; }
    /** returns true if a valid ticket was released */
    boolean leave(int ticketId) { /* TODO */ return false; }
}
```

**Test scenarios.**
1. n=3: park→t0(slot0), park→t1(slot1), leave(t0), park→ uses slot0 again.
2. Fill all, park again → -1.
3. leave on unknown ticket → false.

---

## P8 — Parking Lot: Tiered Pricing  *(from Parking Lot)*

```java
// Java toolkit hint:
//   Duration.between(entry, exit) → minutes/hours
//   TreeMap<Integer hoursThreshold, Double ratePerHour> for tiers + floorEntry / ceilingEntry
//   round partial hours UP
```

**Statement.** Given entry/exit `LocalDateTime`, compute the fee. First 1 hour: ₹30 flat.
Hours 2–3: ₹20/hr. Beyond 3 hours: ₹10/hr. Any started hour is charged as a full hour.

```java
class ParkingPricer {
    long fee(java.time.LocalDateTime entry, java.time.LocalDateTime exit) {
        // TODO: ceil the duration to whole hours, then apply tiers
        return 0;
    }
}
```

**Test scenarios.**
1. 40 minutes → ₹30.
2. 2h 10m → ceil to 3h → 30 + 20 + 20 = ₹70.
3. 5h exactly → 30 + 20 + 20 + 10 + 10 = ₹90.

---

## P9 — Logger Rate Limiter  *(from logging / monitoring system)*

```java
// Java toolkit hint:
//   Map<String message, Integer nextAllowedTimestamp>
//   getOrDefault / put — print only if timestamp >= nextAllowed, then bump by 10
```

**Statement.** `shouldPrint(timestamp, message)` returns true only if that exact message
hasn't been printed in the last 10 seconds. (LeetCode 359 flavour — it shows up as the
throttling core of real loggers.)

```java
class LoggerRateLimiter {
    boolean shouldPrint(int timestamp, String message) { /* TODO */ return true; }
}
```

**Test scenarios.**
1. (1,"foo")→T, (2,"foo")→F, (11,"foo")→T.
2. (1,"foo")→T and (1,"bar")→T (independent).
3. (8,"foo") after (1,"foo") → F; (11,"foo") → T.

---

## P10 — Time-Based Key-Value Store  *(from versioned config / KV store)*

```java
// Java toolkit hint:
//   Map<String key, TreeMap<Integer timestamp, String value>>
//   TreeMap.floorEntry(t) → the most recent value at-or-before time t
//   computeIfAbsent to create the inner map
```

**Statement.** `set(key, value, timestamp)` stores a version. `get(key, timestamp)`
returns the value whose timestamp is the largest one `<= timestamp` (or "" if none).
Timestamps for a key are strictly increasing. (LeetCode 981, but it's the real engine
behind feature flags and config history.)

```java
class TimeKVStore {
    void set(String key, String value, int timestamp) { /* TODO */ }
    String get(String key, int timestamp) { /* TODO */ return ""; }
}
```

**Test scenarios.**
1. set("k","a",1); get("k",1)→"a"; get("k",3)→"a"; set("k","b",4); get("k",4)→"b"; get("k",3)→"a".
2. get on unknown key → "".
3. get before first version → "".

---

## P11 — Leaderboard / Ranking Service  *(from gaming, Stack Overflow reputation)*

```java
// Java toolkit hint:
//   Map<Integer playerId, Integer score> for O(1) score lookups + updates
//   for top(k): either a PriorityQueue of size k, or a TreeMap<score, set<player>>
//   Comparator: score desc, tie-break by id asc
```

**Statement.** `addScore(playerId, delta)` adds to a player's running total (creating them
at 0 if new). `top(k)` returns the k highest total scores (sum of those scores).
`reset(playerId)` zeroes a player without removing them.

```java
class Leaderboard {
    void addScore(int playerId, int delta) { /* TODO */ }
    long top(int k) { /* TODO: sum of k largest scores */ return 0; }
    void reset(int playerId) { /* TODO */ }
}
```

**Test scenarios.**
1. add(1,73), add(2,56), add(3,49), add(1,5)→player1=78; top(1)=78; top(3)=78+56+49=183.
2. reset(1); top(1)=56.
3. top(k) where k > number of players → sum of all.

---

## P12 — URL Shortener: base62 encode/decode  *(from TinyURL)*

```java
// Java toolkit hint:
//   AtomicLong / long counter for the auto-increment id
//   Map<String code, String longUrl> and Map<String longUrl, String code> (bidirectional)
//   base62 = [0-9a-zA-Z]; convert id ↔ string
```

**Statement.** `shorten(longUrl)` returns a short code (`http://t.co/<code>`); calling it
twice on the same URL returns the **same** code. `expand(code)` returns the original URL.
Generate codes by base62-encoding a monotonically increasing counter.

```java
class UrlShortener {
    String shorten(String longUrl) { /* TODO */ return null; }
    String expand(String code)     { /* TODO */ return null; }
}
```

**Test scenarios.**
1. shorten("https://a.com/x") twice → identical code.
2. expand(thatCode) → "https://a.com/x".
3. 62 distinct URLs roll the code from length 1 to length 2.

---

## P13 — Meeting Scheduler: Conflict Detection  *(from Calendar / room booking)*

```java
// Java toolkit hint:
//   TreeMap<Integer start, Integer end> of booked intervals
//   floorKey(start) and ceilingKey(start) to find the neighbours that could overlap
//   book is O(log n), not O(n)
```

**Statement.** A single room. `book(start, end)` (half-open `[start, end)`) succeeds only
if it doesn't overlap an existing booking; return true/false. Must be `O(log n)` per call —
don't scan all bookings.

```java
class RoomCalendar {
    boolean book(int start, int end) { /* TODO */ return false; }
}
```

**Test scenarios.**
1. book(10,20)→T, book(15,25)→F (overlap), book(20,30)→T (touching is OK, half-open).
2. book(5,10)→T.
3. book(12,14)→F (fully inside 10–20).

---

## P14 — BookMyShow: Seat Lock Service  *(from movie ticket booking — concurrency)*

```java
// Java toolkit hint:
//   ConcurrentHashMap<String seatId, Lock record{userId, expiryMillis}>
//   putIfAbsent for atomic "claim if free"; treat expired locks as free on access
//   System.currentTimeMillis() + lockTtl for expiry; no background sweeper required
```

**Statement.** Temporarily lock seats for a user during checkout. `lock(seats, userId)`
succeeds only if **all** requested seats are currently free (no live lock); otherwise it
locks none and returns false. Locks auto-expire after `ttlMillis`. `confirm(seats, userId)`
permanently books seats the same user still holds. `unlock` releases early.

```java
class SeatLockService {
    SeatLockService(long ttlMillis) { /* TODO */ }
    boolean lock(List<String> seats, String userId, long now) { /* all-or-nothing */ return false; }
    boolean confirm(List<String> seats, String userId, long now) { /* TODO */ return false; }
    void unlock(List<String> seats, String userId) { /* TODO */ }
}
```

**Test scenarios.**
1. lock(["A1","A2"],"u1",0)→T; lock(["A2","A3"],"u2",10)→F (A2 held), and A3 stays free.
2. After ttl passes, u2 can lock A2.
3. confirm by a user who never held the seat → F.

---

## P15 — Library: Issue & Fine Calculation  *(from Library Management)*

```java
// Java toolkit hint:
//   Map<String bookId, Loan record{member, dueDate}>
//   LocalDate, Period / ChronoUnit.DAYS.between(due, returned) for overdue days
//   loan period 14 days; fine ₹5 per day late (0 if on time)
```

**Statement.** `issue(bookId, member, today)` records a loan due 14 days later (fail if
already on loan). `returnBook(bookId, today)` frees the book and returns the fine (₹5/day
past due, else 0).

```java
class LibraryDesk {
    boolean issue(String bookId, String member, java.time.LocalDate today) { return false; }
    long returnBook(String bookId, java.time.LocalDate today) { return 0; }
}
```

**Test scenarios.**
1. issue("b","m",2026-06-01); return on 2026-06-15 → due was 06-15 → 0 fine.
2. return on 2026-06-20 → 5 days late → ₹25.
3. issue a book already on loan → false.

---

## P16 — Notification Pub/Sub  *(from notification service / event bus)*

```java
// Java toolkit hint:
//   Map<String topic, List<Subscriber>>  (use CopyOnWriteArrayList if you fear concurrency)
//   computeIfAbsent to register; iterate to fan-out
//   Subscriber as a functional interface so you can pass lambdas
```

**Statement.** `subscribe(topic, subscriber)` registers a listener. `publish(topic, msg)`
delivers the message to every current subscriber of that topic, in subscription order.
`unsubscribe` removes one. Publishing to a topic with no subscribers is a no-op.

```java
interface Subscriber { void onMessage(String topic, String message); }

class PubSub {
    void subscribe(String topic, Subscriber s)   { /* TODO */ }
    void unsubscribe(String topic, Subscriber s) { /* TODO */ }
    void publish(String topic, String message)   { /* TODO */ }
}
```

**Test scenarios.**
1. Two subscribers on "sports"; publish → both receive, in order.
2. unsubscribe one; next publish → only the other receives.
3. publish to "weather" (no subs) → nothing happens, no error.

---

## P17 — Vending Machine: Change Dispenser  *(from Vending Machine)*

```java
// Java toolkit hint:
//   TreeMap<Integer denomination, Integer count> (navigableMap, descend with descendingKeySet)
//   greedy: take from largest denomination down; respect available counts
//   return the coins used, or signal "cannot make exact change"
```

**Statement.** The machine holds a finite count of each coin denomination. `dispense(amount)`
returns the coins that make up `amount` using a greedy largest-first strategy and
**decrements inventory**; if exact change can't be made from stock, change nothing and
return null.

```java
class CoinDispenser {
    CoinDispenser(java.util.Map<Integer,Integer> inventory) { /* TODO */ }
    /** returns map denom→count used, or null if impossible */
    java.util.Map<Integer,Integer> dispense(int amount) { /* TODO */ return null; }
}
```

**Test scenarios.**
1. inventory {10:2, 5:2, 1:5}; dispense(17) → {10:1, 5:1, 1:2}, inventory updated.
2. dispense(4) when only {5:1} left → null (can't make 4), inventory untouched.
3. Two successful dispenses in a row deplete stock correctly.

> Note the classic greedy trap: greedy isn't always optimal for arbitrary denominations
> (e.g. {1,3,4} making 6). Mention that caveat if asked — it's a good signal.

---

## P18 — Elevator: Request Scheduler (SCAN)  *(from Elevator System)*

```java
// Java toolkit hint:
//   TreeSet<Integer> upRequests, downRequests  (sorted floors)
//   ceiling()/floor() to find the next stop in the current direction
//   direction enum; flip direction when no more requests ahead
```

**Statement.** A single elevator using the SCAN ("elevator") algorithm. `request(floor)`
queues a stop. `step()` advances the car by one stop to the next floor in its current
direction; if none remain that way, it reverses. `currentFloor()` reports position.

```java
enum Dir { UP, DOWN, IDLE }

class Elevator {
    Elevator(int startFloor) { /* TODO */ }
    void request(int floor) { /* TODO */ }
    /** move to the next served floor; returns the floor it stopped at, or -1 if idle */
    int step() { /* TODO */ return -1; }
    int currentFloor() { /* TODO */ return 0; }
}
```

**Test scenarios.**
1. start 0; request(5), request(3), request(1): going UP serves 1,3,5 in order.
2. Then request(2): after reaching 5 with nothing above, reverse and serve 2.
3. No pending requests → step() returns -1 and direction becomes IDLE.

---

## Suggested practice order

1. **Map fluency first:** P1, P9, P10, P16 — these drill `computeIfAbsent`, `merge`,
   `getOrDefault`, nested maps. This is 70% of LLD Java.
2. **Ordered structures:** P7, P8, P11, P13, P17, P18 — `TreeMap`/`TreeSet`/`PriorityQueue`
   and their navigation methods (`floorKey`, `ceiling`, `pollFirst`).
3. **Time:** P4, P5, P6, P15 — `currentTimeMillis`, `Duration`, `LocalDate`, `ChronoUnit`.
4. **Hand-rolled / concurrency:** P3 (DLL), P12 (`AtomicLong`), P14 (`ConcurrentHashMap`,
   `putIfAbsent`, all-or-nothing claims).

Do each one *without* the toolkit file open first. Only after you've written a stub and hit
"what's the method called" should you flip to `java-collections-toolkit.md`. That's how the
API actually sticks.



# LLD Building Blocks — Addendum (Fresher Fundamentals)

These six problems close the gaps an audit of the main file surfaced. They cover the
*non-collection* fundamentals a fresher is still graded on: custom map keys, 2D grids,
named design patterns, state machines + exceptions, multi-criteria filtering, and recursion.
Same rules as before — implement from the top comment first, only then consult the toolkit.

> **Money fix (applies to the main file's P1/P2/P8/P15):** never store money as `double`.
> `0.1 + 0.2 != 0.3`. Use `long` (smallest unit, e.g. paise/cents) or `BigDecimal`. Redo those
> problems with `long paise` once you've done these — it's a free correctness point in interviews.

---

## A1 — Tic-Tac-Toe: Win Detection  *(from Tic-Tac-Toe / any board game)*

```java
// Java toolkit hint:
//   2D array char[][] board for the naive version
//   O(1)-per-move trick: int[] rowSum, int[] colSum, int diag, int antiDiag
//        +1 when player 1 marks, -1 when player 2 marks; a line wins when |sum| == n
//   no scan of the whole board per move
```

**Statement.** `n x n` board, two players (1 and 2). `move(row, col, player)` places a mark and
returns `player` if that move wins (completes a row, column, or either diagonal), else 0.
First do the simple scan, then redo it O(1) per move with running counters.

```java
class TicTacToe {
    TicTacToe(int n) { /* TODO */ }
    /** returns winning player (1 or 2) if this move wins, else 0 */
    int move(int row, int col, int player) { /* TODO */ return 0; }
}
```

**Test scenarios** (n=3).
1. P1 plays (0,0),(1,1),(2,2) interleaved with P2 elsewhere → the (2,2) move returns 1 (diagonal).
2. P2 fills a full column → that move returns 2.
3. No line complete → every call returns 0.

---

## A2 — Composite Map Key: equals() & hashCode()  *(from fare tables, route maps, grids)*

```java
// Java toolkit hint:
//   a key carrying TWO fields (from, to) used inside a HashMap
//   you MUST override BOTH equals() and hashCode() — overriding only one is a classic bug
//   Objects.equals(...) and Objects.hash(...) make this one line each
//   then redo the key as a `record` and observe it auto-generates both
```

**Statement.** `FareTable.setFare(from, to, paise)` and `getFare(from, to)` keyed on the pair
`(from, to)`. Implement the key class **once by hand** (manual `equals`/`hashCode`) and **once
as a record**, and prove both behave: two separately-constructed keys with the same fields
must hit the same map entry. Demonstrate to yourself what happens if you *forget* `hashCode`.

```java
// Hand-rolled version:
class RouteKey {
    final String from, to;
    RouteKey(String from, String to) { this.from = from; this.to = to; }
    // TODO: override equals(Object) and hashCode()
}

// Record version (uncomment to compare):
// record RouteKeyR(String from, String to) {}

class FareTable {
    void setFare(String from, String to, long paise) { /* TODO */ }
    long getFare(String from, String to) { /* TODO: -1 if unknown */ return -1; }
}
```

**Test scenarios.**
1. setFare("DEL","BOM",550000); getFare("DEL","BOM") → 550000 (built from a *new* key object).
2. getFare("BOM","DEL") → -1 (direction matters; order-sensitive key).
3. Comment out `hashCode()` → watch lookups start missing. Understand *why*.

> This single problem fixes the #1 silent bug C++ devs hit in Java. Don't skip it.

---

## A3 — Strategy Pattern: Ride Pricing  *(from Uber/Ola, parking, e-commerce discounts)*

```java
// Java toolkit hint:
//   interface PricingStrategy { long fare(double km, int minutes); }  + several implementations
//   Map<String, PricingStrategy> registry so selection is data-driven, NOT an if/else ladder
//   this IS the Strategy pattern — say its name in the interview
```

**Statement.** Compute a ride fare by type. `NORMAL` = base 5000 + 1200/km. `POOL` = NORMAL
fare × 0.7. `SURGE` = NORMAL fare × surgeMultiplier (configurable). Add new types **without
touching** the service method (open/closed).

```java
interface PricingStrategy { long fare(double km, int minutes); }

class FareService {
    void register(String type, PricingStrategy strategy) { /* TODO */ }
    long quote(String type, double km, int minutes) { /* TODO: throw if unknown type */ return 0; }
}
```

**Test scenarios.**
1. register("NORMAL", ...); quote("NORMAL", 10, 20) → 5000 + 12000 = 17000.
2. register("POOL", ...) wrapping NORMAL → quote("POOL",10,20) → 11900.
3. quote("BICYCLE", ...) with no strategy registered → throws a clear exception.

---

## A4 — State Machine + Custom Exception: Order Lifecycle  *(from e-commerce, vending machine)*

```java
// Java toolkit hint:
//   enum State { CREATED, PAID, SHIPPED, DELIVERED, CANCELLED }
//   Map<State, Set<State>> allowed  — declare legal transitions once, as data
//   a custom unchecked exception (extends RuntimeException) for illegal moves
//   this IS the State pattern
```

**Statement.** Model an order's lifecycle. Legal moves: CREATED→PAID→SHIPPED→DELIVERED;
CANCELLED is reachable only from CREATED or PAID. Any other `transition(to)` throws
`IllegalTransitionException` with a message naming both states. `state()` returns the current state.

```java
class IllegalTransitionException extends RuntimeException {
    IllegalTransitionException(String msg) { super(msg); }
}

enum State { CREATED, PAID, SHIPPED, DELIVERED, CANCELLED }

class Order {
    Order() { /* starts CREATED */ }
    State state() { /* TODO */ return null; }
    void transition(State to) { /* TODO: validate against the allowed-map or throw */ }
}
```

**Test scenarios.**
1. CREATED→PAID→SHIPPED→DELIVERED all succeed.
2. CREATED→SHIPPED throws (skips PAID).
3. DELIVERED→CANCELLED throws (terminal state).

---

## A5 — Multi-Criteria Search Service  *(from BookMyShow, product catalog, job board)*

```java
// Java toolkit hint:
//   build a List<Predicate<Movie>> from the *optional* filters, combine with Predicate::and
//   Comparator.comparing(...).reversed().thenComparing(...) for the sort
//   Stream: filter(combined).sorted(cmp).collect(toList())
//   null/empty filter means "don't filter on this field"
```

**Statement.** Given a list of movies (`title`, `city`, `genre`, `rating`), implement
`search(city, genreOrNull, minRatingOrNull, sortBy)` returning matches. `city` is required;
`genre` and `minRating` are optional. `sortBy` is `"rating"` (desc) or `"title"` (asc).

```java
record Movie(String title, String city, String genre, double rating) {}

class MovieSearch {
    MovieSearch(List<Movie> catalog) { /* TODO */ }
    List<Movie> search(String city, String genreOrNull, Double minRatingOrNull, String sortBy) {
        // TODO: compose predicates, then sort
        return null;
    }
}
```

**Test scenarios.**
1. search("Delhi", null, null, "rating") → all Delhi movies, highest rating first.
2. search("Delhi", "Action", 4.0, "title") → Delhi + Action + rating≥4.0, alphabetical.
3. search("Delhi", "Horror", null, "rating") with no horror in Delhi → empty list.

---

## A6 — In-Memory File System (recursion)  *(from file system, org hierarchy)*

```java
// Java toolkit hint:
//   a tree node: name + isDirectory + Map<String, Node> children + (for files) size
//   recursion for total size of a directory and for path resolution
//   split paths on "/" and walk/create children with computeIfAbsent
```

**Statement.** `mkdir(path)` creates intermediate dirs as needed. `addFile(path, size)` creates
a file (and parent dirs). `size(path)` returns total bytes under a directory (recursive) or the
file's size. `exists(path)` returns whether a path resolves.

```java
class FileSystem {
    void mkdir(String path) { /* e.g. "/a/b/c" */ }
    void addFile(String path, long size) { /* e.g. "/a/b/f.txt" */ }
    long size(String path) { /* recursive total under a dir, or file size */ return 0; }
    boolean exists(String path) { return false; }
}
```

**Test scenarios.**
1. addFile("/a/b/f.txt", 100); addFile("/a/g.txt", 50); size("/a") → 150.
2. size("/a/b") → 100.
3. exists("/a/b") → true; exists("/a/x") → false.

---

## Tier-2 nice-to-haves (build these once the six above are solid)

- **Cab matching — nearest driver.** `PriorityQueue` ordered by Euclidean distance to the
  rider; poll the closest free driver. (Distance-comparator heap; complements P7.)
- **Snake & Ladder — board movement.** `Map<Integer,Integer>` of jumps + a dice roll; advance a
  token, apply snake/ladder, detect win. (Map-driven game loop.)
- **Undo/Redo — text editor.** Two `Deque`s (undo stack + redo stack); each edit is a Command
  object you can invert. (Command pattern, the real reason to learn it.)
- **Builder pattern.** Construct a `Pizza`/`HttpRequest` with many optional fields via a fluent
  `Builder` instead of a telescoping constructor.
- **Thread-safe Singleton.** Double-checked locking with `volatile`, or the enum-singleton
  idiom. Know *why* the naive version is broken.
- **`Comparable` on your own class.** Make a `Player implements Comparable<Player>` and sort a
  list with the natural order — distinct from passing an external `Comparator`.
- **ATM / Wallet withdraw.** `withdraw(amount)` throws `InsufficientFundsException`; pair it
  with `AtomicLong` balance for the concurrency-aware version.

## Coverage scorecard after the addendum

| Fundamental | Covered by |
|---|---|
| Map fluency (merge/compute/getOrDefault) | main P1, P9, P10, P16 |
| TreeMap navigation (floor/ceiling) | main P10, P13, P17, P18 |
| Heaps / top-k | main P2, P11 |
| Deque / sliding window | main P6 |
| Time (millis / Duration / LocalDate) | main P4, P5, P8, P15 |
| Concurrency | main P14 + A4-extension, ATM |
| **equals/hashCode + custom keys** | **A2** |
| **2D grid / matrix** | **A1** |
| **Strategy pattern** | **A3** |
| **State machine + exceptions** | **A4** |
| **Predicate/Comparator composition** | **A5** |
| **Recursion / hierarchy** | **A6** |
| Comparable, Builder, Singleton, Command | Tier-2 list |

With the six additions, every basic a fresher SDE round expects has at least one dedicated
rep. The Tier-2 items are differentiators, not basics — do them if you have time.
