# ☕ Java OOP Interview Mastery Guide
### For FAANG & Top Indian Startup Interviews
*Researched & Compiled — 2026*

---

## 📋 Table of Contents

1. [🏢 Section 1: Interview Landscape](#-section-1-interview-landscape)
   - [1.1 FAANG OOP Interview Style](#11-faang-oop-interview-style)
   - [1.2 Indian Startup OOP Interview Style](#12-indian-startup-oop-interview-style)
2. [🧱 Section 2: The Four Pillars — Deep Dive](#-section-2-the-four-pillars--deep-dive)
   - [2.1 Encapsulation](#21-encapsulation)
   - [2.2 Inheritance](#22-inheritance)
   - [2.3 Polymorphism](#23-polymorphism-compile-time--runtime)
   - [2.4 Abstraction](#24-abstraction)
3. [🔗 Section 3: Interfaces vs Abstract Classes](#-section-3-interfaces-vs-abstract-classes)
4. [🏗️ Section 4: SOLID Principles](#️-section-4-solid-principles)
5. [🎨 Section 5: Design Patterns (OOP-focused)](#-section-5-design-patterns-oop-focused)
6. [⚠️ Section 6: Tricky & Advanced OOP Questions](#️-section-6-tricky--advanced-oop-questions)
7. [💻 Section 7: OOP Coding Problems](#-section-7-oop-coding-problems)
8. [🔥 Section 8: FAANG-Specific Deep Questions](#-section-8-faang-specific-deep-questions)
9. [🚀 Section 9: Indian Startup Specific Questions](#-section-9-indian-startup-specific-questions)
10. [📝 Section 10: Quick Reference Cheat Sheet](#-section-10-quick-reference-cheat-sheet)
11. [🗺️ Section 11: Study Plan](#️-section-11-study-plan)
12. [📚 Section 12: Resources](#-section-12-resources)

---

## 🏢 Section 1: Interview Landscape

### 1.1 FAANG OOP Interview Style

FAANG companies (Google, Amazon, Meta, Apple, Netflix/Microsoft) treat OOP not as a standalone quiz topic but as a **design lens** woven into coding and system design rounds. According to [Amazon's official interview prep page](https://amazon.jobs/content/en/how-we-hire/interview-prep/software-development-topics), their SDE hiring process includes an online assessment with MCQs covering OOPs, DBMS, and OS, followed by multiple technical rounds focusing on real-world problem-solving ability.

**What FAANG specifically looks for:**
- Ability to **model real-world systems** using classes, interfaces, and hierarchies
- Understanding of **trade-offs** — when to use inheritance vs. composition, interface vs. abstract class
- Application of **SOLID principles** in code — not just definitions
- Design pattern usage in **system design** rounds (Singleton for connection pools, Observer for event systems, Strategy for payment processing)
- Clean, extensible, testable code that demonstrates OOP mastery organically

**How OOP questions appear at FAANG:**

| Format | Example | Frequency |
|--------|---------|-----------|
| Embedded in coding | "Design an LRU Cache class" | Very High |
| System design sub-problem | "How would you model the entities in a ride-sharing app?" | High |
| Standalone conceptual | "Explain polymorphism with an example" | Medium (more common at Amazon) |
| Code review / refactor | "What's wrong with this class hierarchy?" | Medium |

**Difficulty & depth expected:** FAANG interviewers expect you to go beyond textbook definitions. At Google, you might be asked to design a class hierarchy for a game and then justify every design choice. At Amazon, OOP questions are often tied to [Leadership Principles](https://amazon.jobs/content/en/how-we-hire/interview-prep/software-development-topics) — you'll explain *why* a design is maintainable, not just *how* it works. Meta's coding rounds, as seen on [interviewing.io mock sessions](https://interviewing.io/mocks/facebook-java-infinite-binary-print), focus on algorithmic problem-solving but expect clean OOP structure in your code.

💡 **Pro Tip:** At FAANG, never just recite definitions. Always follow up with "and the trade-off is..." or "I chose this over X because...". Interviewers reward design thinking over memorization.

### 1.2 Indian Startup OOP Interview Style

Top Indian startups — Razorpay, Zepto, Swiggy, CRED, Groww, PhonePe, Meesho, Zomato, Flipkart, and Paytm — place **heavy emphasis on OOP fundamentals**, often dedicating an entire interview round to OOP and Low-Level Design (LLD).

**What Indian startups focus on:**
- **Direct conceptual questions** — "What are the four pillars of OOP?" is still commonly asked ([Sprintzeal](https://www.sprintzeal.com/blog/java-oops-interview-questions))
- **LLD/Machine Coding rounds** — Design a Parking Lot, Splitwise, Snake & Ladder using proper OOP
- **SOLID principles** as a separate evaluation criterion
- **Design patterns** — Singleton, Factory, Strategy, Observer are expected knowledge

**How their approach differs from FAANG:**

| Aspect | FAANG | Indian Startups |
|--------|-------|-----------------|
| OOP as standalone topic | Rare (woven into other rounds) | Common (dedicated LLD round) |
| Definitions expected | No — show through code | Yes — explain + code |
| Design patterns | Applied implicitly | Asked explicitly |
| Machine coding round | Uncommon | Very common (45-90 min) |
| SOLID principles | Tested through code quality | Directly asked |

**Common patterns across Indian startups:**
- Flipkart and Swiggy are known for rigorous machine coding rounds where you build a working system in 60-90 minutes ([CodeJeet](https://codejeet.com/compare/swiggy-vs-zepto))
- Razorpay and CRED ask about design patterns in the context of payment systems
- Groww and PhonePe focus on concurrency + OOP (thread-safe Singleton, immutable objects)
- Zepto and Meesho test rapid OOP modeling under time pressure

💡 **Pro Tip:** For Indian startup interviews, practice machine coding. Build 5-6 LLD problems end-to-end with clean OOP, SOLID compliance, and at least one design pattern each. This single preparation step covers 70% of what's tested.

---

## 🧱 Section 2: The Four Pillars — Deep Dive

### 2.1 Encapsulation

**Definition:** Encapsulation is the mechanism of wrapping data (variables) and the code acting on that data (methods) together as a single unit, while restricting direct access to some of the object's components. In Java, this is achieved through access modifiers and getter/setter methods ([GeeksforGeeks](https://www.geeksforgeeks.org/java/oops-interview-questions-java-programming/)).

**Why interviewers ask about it:** Encapsulation is the foundation of data protection and API design. It tests whether a candidate understands information hiding, defensive programming, and how to build maintainable classes.

**Interview Questions:**

**Q1: What is encapsulation and how is it achieved in Java?** `[Easy]`

✅ **Model Answer:** Encapsulation binds data and methods into a single class and controls access using access modifiers. We make fields `private` and provide `public` getter/setter methods.

```java
public class BankAccount {
    private double balance; // hidden from outside
    private String accountHolder;

    public BankAccount(String accountHolder, double initialBalance) {
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
}
```

**Q2: What is the difference between encapsulation and abstraction?** `[Easy]`

✅ Encapsulation is about **data hiding** — controlling access to internal state. Abstraction is about **implementation hiding** — exposing only relevant details. Encapsulation is *how* you hide; abstraction is *what* you hide.

**Q3: Can encapsulation be achieved without getters and setters?** `[Medium]`

✅ Yes. You can expose behavior instead of data. Instead of `getBalance()` and `setBalance()`, you provide `deposit()` and `withdraw()`. This is actually *better* encapsulation — it's called the "Tell, Don't Ask" principle.

**Q4: What happens if you make all fields public? Is that still OOP?** `[Medium]`

✅ Technically the code compiles, but it violates encapsulation. Any class can modify the state directly, leading to invalid states (e.g., negative balance). It makes the class tightly coupled to all consumers.

**Q5: How does the `private` keyword enforce encapsulation? Can reflection break it?** `[Hard]`

✅ `private` restricts access at compile-time. However, Java Reflection API (`Field.setAccessible(true)`) can bypass this at runtime. This is why security-sensitive applications use a `SecurityManager`. In interviews, mention this as a nuance — it shows depth.

**Q6: Design an immutable class in Java. How does immutability relate to encapsulation?** `[Hard]`

✅ Immutability is the strongest form of encapsulation — you don't just control access, you prevent all modification after construction.

```java
public final class Money {
    private final String currency;
    private final double amount;

    public Money(String currency, double amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public String getCurrency() { return currency; }
    public double getAmount() { return amount; }

    public Money add(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Currency mismatch");
        }
        return new Money(this.currency, this.amount + other.amount);
    }
}
```

**Common traps/mistakes:**
- ❌ Thinking getters/setters = encapsulation. Exposing all fields via getters defeats the purpose.
- ❌ Returning mutable objects from getters (e.g., returning a `List` directly instead of `Collections.unmodifiableList()`).
- ❌ Confusing encapsulation with abstraction.

**Follow-up questions interviewers typically ask:**
- "How would you make a class with a `List` field truly encapsulated?"
- "What's defensive copying?"
- "How does encapsulation help in multi-threaded environments?"

---

### 2.2 Inheritance

**Definition:** Inheritance allows a class (child/subclass) to inherit properties and methods from another class (parent/superclass), promoting code reusability and establishing an IS-A relationship ([Hirist Blog](https://www.hirist.tech/blog/top-50-java-oops-interview-questions-and-answers/)).

**Why interviewers ask about it:** Inheritance tests understanding of class hierarchies, code reuse, and the candidate's ability to recognize when *not* to use it (favor composition over inheritance).

**Interview Questions:**

**Q1: What are the types of inheritance in Java?** `[Easy]`

✅ Java supports: Single, Multilevel, and Hierarchical inheritance through classes. Multiple and Hybrid inheritance are supported only through interfaces. Java does not support multiple class inheritance to avoid the diamond problem ([InterviewBit](https://www.interviewbit.com/oops-interview-questions/)).

**Q2: Why doesn't Java support multiple inheritance with classes?** `[Medium]`

✅ To avoid the **Diamond Problem** — if class C extends both A and B, and both A and B have a method `foo()`, the compiler cannot determine which `foo()` to inherit. Java solves this by allowing multiple inheritance only through interfaces, where the implementing class must provide its own implementation.

**Q3: What is the difference between `extends` and `implements`?** `[Easy]`

✅ `extends` is used for class-to-class (or interface-to-interface) inheritance. `implements` is used when a class implements an interface. A class can `extend` only one class but `implement` multiple interfaces.

**Q4: When should you use inheritance vs. composition?** `[Medium]` `[Amazon]` `[Google]`

✅ Use inheritance for IS-A relationships (Dog IS-A Animal). Use composition for HAS-A relationships (Car HAS-A Engine). **Effective Java** by Joshua Bloch advises: "Favor composition over inheritance" because inheritance breaks encapsulation — a subclass depends on the implementation details of its superclass.

```java
// ❌ Inheritance approach — fragile
class InstrumentedHashSet<E> extends HashSet<E> {
    private int addCount = 0;

    @Override
    public boolean add(E e) {
        addCount++;
        return super.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return super.addAll(c); // BUG: addAll internally calls add(), double-counting!
    }
}

// ✅ Composition approach — robust
class InstrumentedSet<E> {
    private final Set<E> set;
    private int addCount = 0;

    public InstrumentedSet(Set<E> set) { this.set = set; }

    public boolean add(E e) {
        addCount++;
        return set.add(e);
    }

    public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return set.addAll(c);
    }
}
```

**Q5: Can a constructor be inherited in Java?** `[Medium]`

✅ No. Constructors are not inherited. However, the subclass constructor implicitly calls `super()` (the parent's no-arg constructor) as its first statement. If the parent doesn't have a no-arg constructor, you must explicitly call `super(args)`.

**Q6: What is constructor chaining?** `[Medium]`

✅ Constructor chaining is calling one constructor from another using `this()` (same class) or `super()` (parent class). It ensures proper initialization up the hierarchy.

```java
class Employee {
    String name;
    int id;

    Employee() { this("Unknown", 0); }  // chains to parameterized constructor
    Employee(String name, int id) {
        this.name = name;
        this.id = id;
    }
}

class Manager extends Employee {
    String department;

    Manager(String name, int id, String department) {
        super(name, id);  // must be first statement
        this.department = department;
    }
}
```

**Q7: What is the output of this code?** `[Hard]`

```java
class Parent {
    Parent() { System.out.println("Parent"); }
}
class Child extends Parent {
    Child() { System.out.println("Child"); }
}
class GrandChild extends Child {
    GrandChild() { System.out.println("GrandChild"); }
}
// new GrandChild();
```

✅ Output: `Parent`, `Child`, `GrandChild` — constructors are called in top-down order (parent first) due to implicit `super()` calls ([InterviewBit](https://www.interviewbit.com/oops-interview-questions/)).

**Common traps/mistakes:**
- ❌ Overusing inheritance when composition is more appropriate
- ❌ Forgetting that `super()` must be the first statement in a constructor
- ❌ Not knowing that `private` members are inherited but not accessible in the subclass

**Follow-up questions:**
- "If private members aren't accessible, are they really inherited?"
- "What is the Liskov Substitution Principle and how does it relate to inheritance?"

---

### 2.3 Polymorphism (Compile-time & Runtime)

**Definition:** Polymorphism means "many forms" — it allows one interface to be used for a general class of actions. In Java, it manifests as **compile-time polymorphism** (method overloading) and **runtime polymorphism** (method overriding) ([Edureka](https://www.edureka.co/blog/interview-questions/oops-interview-questions/)).

**Why interviewers ask about it:** Polymorphism is the cornerstone of flexible, extensible design. It tests whether candidates can build systems that are open for extension but closed for modification.

**Interview Questions:**

**Q1: What is the difference between method overloading and method overriding?** `[Easy]`

| Feature | Overloading | Overriding |
|---------|-------------|------------|
| Binding | Compile-time (static) | Runtime (dynamic) |
| Where | Same class | Parent-child classes |
| Parameters | Must differ | Must be same |
| Return type | Can differ | Must be same or covariant |
| Access modifier | No restriction | Cannot be more restrictive |
| `static` methods | Can be overloaded | Cannot be overridden (hidden) |
| `final` methods | Can be overloaded | Cannot be overridden |

([GeeksforGeeks](https://www.geeksforgeeks.org/java/difference-between-method-overloading-and-method-overriding-in-java/))

**Q2: What is the output of this code?** `[Medium]`

```java
class Animal {
    void speak() { System.out.println("Animal speaks"); }
}
class Dog extends Animal {
    @Override
    void speak() { System.out.println("Dog barks"); }
}
class Cat extends Animal {
    @Override
    void speak() { System.out.println("Cat meows"); }
}

// In main:
Animal a = new Dog();
a.speak();  // Output: "Dog barks" — runtime polymorphism
```

✅ The reference type is `Animal`, but the actual object is `Dog`. At runtime, JVM invokes `Dog.speak()` — this is **dynamic method dispatch**.

**Q3: Can we override a static method in Java?** `[Medium]`

✅ No. Static methods belong to the class, not the instance. If a subclass defines a static method with the same signature, it's called **method hiding**, not overriding. The method called depends on the reference type, not the object type.

```java
class Parent {
    static void greet() { System.out.println("Hello from Parent"); }
}
class Child extends Parent {
    static void greet() { System.out.println("Hello from Child"); }
}

Parent p = new Child();
p.greet();  // Output: "Hello from Parent" — method hiding, NOT polymorphism
```

**Q4: What is a covariant return type?** `[Medium]`

✅ Since Java 5, an overriding method can return a subtype of the return type declared in the parent method. This is called a covariant return type.

```java
class Animal {
    Animal create() { return new Animal(); }
}
class Dog extends Animal {
    @Override
    Dog create() { return new Dog(); }  // Covariant return — Dog is subtype of Animal
}
```

**Q5: Method overloading — what is the priority when multiple methods match?** `[Hard]`

✅ The JVM follows this priority for overloaded methods: exact match → widening → autoboxing → varargs. For example, if you pass `10` (int):
1. `print(int)` — exact match ✅
2. `print(long)` — widening
3. `print(Integer)` — autoboxing
4. `print(int...)` — varargs (lowest priority)

([YouTube — Naveen Automation Labs](https://www.youtube.com/watch?v=PVrUEP87xTU))

**Q6: Can you overload methods by just changing the return type?** `[Easy]`

✅ No. Java cannot distinguish between methods solely based on return type. The parameter list must differ.

**Q7: Explain runtime polymorphism with the `@Override` annotation.** `[Medium]`

✅ `@Override` is a compile-time safety net. It tells the compiler: "I intend to override a method from my superclass." If the method doesn't actually override anything (e.g., wrong signature), the compiler throws an error. It doesn't affect runtime behavior — it's purely for correctness checking.

**Common traps/mistakes:**
- ❌ Confusing method hiding (static) with method overriding (instance)
- ❌ Thinking overloading is runtime polymorphism — it is resolved at compile-time
- ❌ Not understanding that fields are NOT polymorphic — only methods are

**Follow-up questions:**
- "Can you achieve polymorphism without inheritance?"
- "How does polymorphism work with interfaces?"
- "What is double dispatch and why doesn't Java support it natively?"

---

### 2.4 Abstraction

**Definition:** Abstraction is the concept of hiding implementation details and exposing only the essential features of an object. In Java, abstraction is achieved through **abstract classes** and **interfaces** ([AlmaBetter](https://www.almabetter.com/bytes/articles/java-oops-interview-questions)).

**Why interviewers ask about it:** Abstraction tests the ability to design clean APIs and separate "what" from "how" — a critical skill for building large-scale systems.

**Interview Questions:**

**Q1: What is the difference between abstraction and encapsulation?** `[Easy]`

✅ Abstraction hides **complexity** (what the user sees). Encapsulation hides **data** (how the object protects its state). Abstraction is achieved at design level (interfaces, abstract classes). Encapsulation is achieved at implementation level (access modifiers).

**Q2: Can an abstract class have a constructor?** `[Medium]`

✅ Yes. An abstract class can have a constructor, but it cannot be instantiated directly. The constructor is called via `super()` when a concrete subclass is instantiated. It's used to initialize common fields ([Javarevisited](https://javarevisited.blogspot.com/2013/05/10-abstract-class-interface-interview-questions-answers-java.html)).

```java
abstract class Shape {
    String color;
    Shape(String color) { this.color = color; }
    abstract double area();
}

class Circle extends Shape {
    double radius;
    Circle(String color, double radius) {
        super(color);  // calls abstract class constructor
        this.radius = radius;
    }
    @Override
    double area() { return Math.PI * radius * radius; }
}
```

**Q3: Can an abstract class be `final`?** `[Easy]`

✅ No. `final` prevents a class from being extended, but an abstract class *must* be extended to be useful. They are contradictory.

**Q4: Can an abstract class have non-abstract methods?** `[Easy]`

✅ Yes. An abstract class can have concrete (non-abstract) methods, constructors, static methods, and fields. This is one of its key advantages over interfaces (pre-Java 8).

**Q5: When would you use an abstract class over an interface?** `[Medium]` `[Razorpay]` `[Flipkart]`

✅ Use an abstract class when:
- You need to share code among closely related classes
- You need non-public members or mutable state
- You want to provide a common base with partial implementation

Use an interface when:
- You need a contract for unrelated classes
- You want multiple inheritance of type
- You're defining capabilities (e.g., `Serializable`, `Comparable`)

**Q6: What is the purpose of abstract methods?** `[Easy]`

✅ Abstract methods define a **contract** — "any subclass MUST implement this." They provide a skeleton that ensures consistency across implementations while allowing each subclass to customize behavior.

**Common traps/mistakes:**
- ❌ Thinking abstract classes cannot have concrete methods
- ❌ Forgetting that abstract classes can have constructors
- ❌ Not knowing that an abstract class can implement an interface without implementing all its methods

---

## 🔗 Section 3: Interfaces vs Abstract Classes

This is arguably the **most asked OOP question** across all companies and experience levels ([Javarevisited](https://javarevisited.blogspot.com/2013/05/10-abstract-class-interface-interview-questions-answers-java.html)).

### The Classic Comparison

| Feature | Abstract Class | Interface |
|---------|---------------|-----------|
| Methods | Abstract + concrete | Abstract (+ default/static since Java 8, + private since Java 9) |
| Variables | Instance variables (any type) | Only `public static final` constants |
| Constructors | Yes | No |
| Inheritance | Single (`extends`) | Multiple (`implements`) |
| Access modifiers | All four | Methods are `public` by default |
| Speed | Slightly faster | Slightly slower (method lookup) |
| Use case | IS-A with shared state | CAN-DO capability |

### Java 8+ Evolution

Java 8 blurred the line significantly by introducing **default methods** and **static methods** in interfaces:

```java
public interface PaymentProcessor {
    // Abstract method — must be implemented
    boolean processPayment(double amount);

    // Default method — provides a default implementation (Java 8+)
    default void logTransaction(double amount) {
        System.out.println("Transaction: " + amount);
    }

    // Static method — utility method on the interface (Java 8+)
    static PaymentProcessor createDefault() {
        return amount -> { System.out.println("Default payment"); return true; };
    }

    // Private method — helper for default methods (Java 9+)
    private void validateAmount(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Invalid amount");
    }
}
```

### The Diamond Problem in Java

```java
interface A {
    default void hello() { System.out.println("Hello from A"); }
}
interface B {
    default void hello() { System.out.println("Hello from B"); }
}
class C implements A, B {
    // MUST override to resolve ambiguity — compiler error otherwise
    @Override
    public void hello() {
        A.super.hello(); // explicitly choose A's implementation
    }
}
```

Java resolves the diamond problem by: (1) Class methods win over interface defaults; (2) More specific interfaces win; (3) If still ambiguous, the class must provide an explicit override ([DEV Community](https://dev.to/haraf/100-senior-java-developer-interview-questions-and-answers-2025-edition-4f6n)).

### Interview Questions on Interfaces vs Abstract Classes

**Q1: Why were default methods added to interfaces in Java 8?** `[Medium]`

✅ To allow **backward-compatible API evolution**. Before Java 8, adding a method to an interface broke all implementing classes. Default methods allow adding new methods without breaking existing implementations. Example: `forEach()` was added to `Iterable` as a default method.

**Q2: Can an interface extend another interface?** `[Easy]`

✅ Yes. An interface can extend one or more interfaces using `extends`. A class implementing the child interface must implement all methods from all parent interfaces.

**Q3: What happens when a class implements two interfaces with the same default method?** `[Medium]`

✅ The compiler forces the class to override the conflicting method. You can use `InterfaceName.super.method()` to delegate to a specific interface's default implementation.

**Q4: Can we have `main()` inside an interface?** `[Medium]`

✅ Yes, since Java 8 (static methods in interfaces). `public static void main(String[] args)` can be declared in an interface and the class can be run.

**Q5: Can an interface have a `private` method?** `[Medium]`

✅ Yes, since Java 9. Private methods in interfaces are helper methods for default methods — they avoid code duplication among default methods without exposing the helper to implementing classes.

**Q6: If abstract classes can now have abstract and concrete methods, and interfaces can have abstract and default methods, when exactly would you choose one over the other?** `[Hard]` `[Google]` `[Flipkart]`

✅ **Choose interface when:** designing a contract, needing multiple inheritance, defining cross-cutting capabilities. **Choose abstract class when:** sharing state (instance fields), controlling construction, providing a template (Template Method pattern), and maintaining close IS-A relationships. The fundamental distinction remains: **interfaces define a contract, abstract classes define a partial implementation**.

**Q7: What is a marker interface? Give examples.** `[Easy]`

✅ A marker interface has no methods — it "marks" a class as having a certain property. Examples: `Serializable`, `Cloneable`, `Remote`. Modern Java uses annotations (`@FunctionalInterface`) for the same purpose.

**Q8: What is a functional interface?** `[Medium]`

✅ An interface with exactly one abstract method. It can be used as a target for lambda expressions. Annotated with `@FunctionalInterface`. Examples: `Runnable`, `Callable`, `Comparator`, `Function<T,R>`.

```java
@FunctionalInterface
interface MathOperation {
    double operate(double a, double b);
}

// Lambda usage
MathOperation addition = (a, b) -> a + b;
MathOperation multiplication = (a, b) -> a * b;
```

**Q9: Can abstract classes implement interfaces?** `[Medium]`

✅ Yes, and they don't need to implement all methods. The remaining methods can stay abstract and must be implemented by concrete subclasses ([Javarevisited](https://javarevisited.blogspot.com/2013/05/10-abstract-class-interface-interview-questions-answers-java.html)).

**Q10: What is the difference between `Comparable` and `Comparator` interfaces?** `[Medium]`

✅ `Comparable` is implemented by the class itself (`compareTo()`) for natural ordering. `Comparator` is a separate class/lambda for custom ordering (`compare()`). Use `Comparable` for a single default sort; `Comparator` for multiple sort strategies.

---

## 🏗️ Section 4: SOLID Principles

SOLID principles are the backbone of maintainable OOP design and are heavily tested in both FAANG and Indian startup interviews ([ACTE](https://www.acte.in/oops-interview-questions-and-answers)).

### S — Single Responsibility Principle (SRP)

**One-line definition:** A class should have only one reason to change.

```java
// ❌ Violating SRP — class does too much
class Employee {
    void calculatePay() { /* payroll logic */ }
    void saveToDatabase() { /* persistence logic */ }
    void generateReport() { /* reporting logic */ }
}

// ✅ Following SRP — each class has one job
class Employee { String name; double salary; }
class PayrollCalculator { double calculatePay(Employee e) { return e.salary; } }
class EmployeeRepository { void save(Employee e) { /* DB logic */ } }
class ReportGenerator { void generate(Employee e) { /* report logic */ } }
```

**How violating it causes problems:** Changes to payroll logic force recompilation and testing of reporting and persistence code. Teams working on different features step on each other's toes.

**Interview question:** "You have a `UserService` class that handles registration, authentication, profile updates, and email notifications. How would you refactor it?" `[Medium]`

---

### O — Open/Closed Principle (OCP)

**One-line definition:** Software entities should be open for extension but closed for modification.

```java
// ❌ Violating OCP — adding a new shape requires modifying AreaCalculator
class AreaCalculator {
    double calculate(Object shape) {
        if (shape instanceof Circle c) return Math.PI * c.radius * c.radius;
        if (shape instanceof Rectangle r) return r.width * r.height;
        // Need to add Triangle? Must modify this class!
        return 0;
    }
}

// ✅ Following OCP — extend by adding new classes, not modifying existing ones
interface Shape {
    double area();
}
class Circle implements Shape {
    double radius;
    Circle(double radius) { this.radius = radius; }
    public double area() { return Math.PI * radius * radius; }
}
class Rectangle implements Shape {
    double width, height;
    Rectangle(double w, double h) { this.width = w; this.height = h; }
    public double area() { return width * height; }
}
// Adding Triangle? Just create a new class — no existing code changes!
class Triangle implements Shape {
    double base, height;
    Triangle(double b, double h) { this.base = b; this.height = h; }
    public double area() { return 0.5 * base * height; }
}
```

**Interview question:** "How would you design a notification system (email, SMS, push) that can easily add new channels without changing existing code?" `[Medium]` `[CRED]`

---

### L — Liskov Substitution Principle (LSP)

**One-line definition:** Objects of a superclass should be replaceable with objects of a subclass without altering the correctness of the program ([DEV Community](https://dev.to/haraf/100-senior-java-developer-interview-questions-and-answers-2025-edition-4f6n)).

```java
// ❌ Violating LSP — the classic Rectangle-Square problem
class Rectangle {
    protected int width, height;
    void setWidth(int w) { this.width = w; }
    void setHeight(int h) { this.height = h; }
    int area() { return width * height; }
}
class Square extends Rectangle {
    @Override void setWidth(int w) { this.width = w; this.height = w; }
    @Override void setHeight(int h) { this.width = h; this.height = h; }
}

// Test fails with Square — LSP violated!
void testArea(Rectangle r) {
    r.setWidth(5);
    r.setHeight(4);
    assert r.area() == 20; // FAILS for Square (returns 16)
}
```

**How violating it causes problems:** Substituting a Square for a Rectangle breaks the contract. Code that depends on Rectangle's behavior produces wrong results.

**Interview question:** "Is it valid for `Square` to extend `Rectangle`? Why or why not?" `[Hard]` `[Google]`

---

### I — Interface Segregation Principle (ISP)

**One-line definition:** Clients should not be forced to depend on interfaces they do not use.

```java
// ❌ Violating ISP — fat interface
interface Worker {
    void work();
    void eat();
    void sleep();
}
class Robot implements Worker {
    public void work() { /* works */ }
    public void eat() { /* Robots don't eat! */ }   // forced empty implementation
    public void sleep() { /* Robots don't sleep! */ }
}

// ✅ Following ISP — split into focused interfaces
interface Workable { void work(); }
interface Eatable { void eat(); }
interface Sleepable { void sleep(); }

class HumanWorker implements Workable, Eatable, Sleepable {
    public void work() { /* works */ }
    public void eat() { /* eats */ }
    public void sleep() { /* sleeps */ }
}
class RobotWorker implements Workable {
    public void work() { /* works */ }
}
```

**Interview question:** "You have a `Vehicle` interface with methods `fly()`, `drive()`, and `sail()`. How would you fix this for a `Car` class?" `[Easy]`

---

### D — Dependency Inversion Principle (DIP)

**One-line definition:** High-level modules should not depend on low-level modules. Both should depend on abstractions.

```java
// ❌ Violating DIP — high-level depends on low-level concrete class
class MySQLDatabase { void save(String data) { /* MySQL-specific */ } }
class UserService {
    private MySQLDatabase db = new MySQLDatabase(); // tightly coupled!
    void createUser(String name) { db.save(name); }
}

// ✅ Following DIP — depend on abstractions
interface Database { void save(String data); }
class MySQLDatabase implements Database {
    public void save(String data) { /* MySQL-specific */ }
}
class MongoDatabase implements Database {
    public void save(String data) { /* Mongo-specific */ }
}
class UserService {
    private final Database db;
    UserService(Database db) { this.db = db; } // dependency injection
    void createUser(String name) { db.save(name); }
}
```

**Interview question:** "How does Dependency Injection relate to Dependency Inversion? Are they the same?" `[Hard]` `[Amazon]`

✅ No. Dependency Inversion is a *principle* (depend on abstractions). Dependency Injection is a *technique* (provide dependencies externally). DI is one way to achieve DIP. Frameworks like Spring use DI to follow DIP.

---

## 🎨 Section 5: Design Patterns (OOP-focused)

### Creational Patterns

#### Singleton

**Intent:** Ensure a class has only one instance and provide global access.

```java
// Thread-safe Singleton using Bill Pugh's approach (recommended)
public class DatabaseConnection {
    private DatabaseConnection() {}

    private static class Holder {
        private static final DatabaseConnection INSTANCE = new DatabaseConnection();
    }

    public static DatabaseConnection getInstance() {
        return Holder.INSTANCE;
    }
}
```

**Thread-safety:** The inner static class is loaded lazily and guaranteed thread-safe by the JVM class loader. Other approaches: `enum` Singleton (simplest, recommended by Effective Java), `synchronized` method (slow), double-checked locking (verbose).

**Interview question:** "How do you prevent Singleton from being broken by Reflection or Serialization?" `[Hard]` `[Amazon]`

✅ **Reflection:** Throw exception in constructor if instance already exists. **Serialization:** Implement `readResolve()` to return the existing instance. **Best solution:** Use `enum` Singleton — immune to both.

#### Factory Method

**Intent:** Define an interface for creating objects, letting subclasses decide which class to instantiate.

```java
interface Notification { void send(String message); }

class EmailNotification implements Notification {
    public void send(String message) { System.out.println("Email: " + message); }
}
class SMSNotification implements Notification {
    public void send(String message) { System.out.println("SMS: " + message); }
}
class PushNotification implements Notification {
    public void send(String message) { System.out.println("Push: " + message); }
}

class NotificationFactory {
    public static Notification create(String type) {
        return switch (type.toLowerCase()) {
            case "email" -> new EmailNotification();
            case "sms"   -> new SMSNotification();
            case "push"  -> new PushNotification();
            default -> throw new IllegalArgumentException("Unknown type: " + type);
        };
    }
}
```

#### Builder

**Intent:** Construct complex objects step by step, separating construction from representation.

```java
public class HttpRequest {
    private final String url;
    private final String method;
    private final Map<String, String> headers;
    private final String body;

    private HttpRequest(Builder builder) {
        this.url = builder.url;
        this.method = builder.method;
        this.headers = builder.headers;
        this.body = builder.body;
    }

    public static class Builder {
        private final String url;     // required
        private String method = "GET"; // default
        private Map<String, String> headers = new HashMap<>();
        private String body;

        public Builder(String url) { this.url = url; }
        public Builder method(String method) { this.method = method; return this; }
        public Builder header(String key, String value) { headers.put(key, value); return this; }
        public Builder body(String body) { this.body = body; return this; }
        public HttpRequest build() { return new HttpRequest(this); }
    }
}

// Usage:
HttpRequest request = new HttpRequest.Builder("https://api.example.com")
    .method("POST")
    .header("Content-Type", "application/json")
    .body("{\"key\": \"value\"}")
    .build();
```

#### Prototype

**Intent:** Create new objects by cloning an existing object.

```java
public abstract class Shape implements Cloneable {
    String type;
    abstract void draw();

    @Override
    public Shape clone() {
        try { return (Shape) super.clone(); }
        catch (CloneNotSupportedException e) { throw new RuntimeException(e); }
    }
}
```

### Structural Patterns

#### Adapter

**Intent:** Convert the interface of a class into another interface clients expect.

```java
// Legacy system
class OldPaymentGateway {
    void makePayment(String xml) { System.out.println("Paid via XML: " + xml); }
}

// New interface
interface ModernPaymentProcessor {
    void pay(Map<String, String> jsonData);
}

// Adapter
class PaymentAdapter implements ModernPaymentProcessor {
    private final OldPaymentGateway legacy;
    PaymentAdapter(OldPaymentGateway legacy) { this.legacy = legacy; }

    public void pay(Map<String, String> jsonData) {
        String xml = convertToXml(jsonData); // transform
        legacy.makePayment(xml);
    }

    private String convertToXml(Map<String, String> data) {
        return "<payment>" + data.toString() + "</payment>";
    }
}
```

#### Decorator

**Intent:** Dynamically add responsibilities to objects without modifying their class.

```java
interface Coffee {
    double cost();
    String description();
}
class SimpleCoffee implements Coffee {
    public double cost() { return 50; }
    public String description() { return "Simple coffee"; }
}
class MilkDecorator implements Coffee {
    private final Coffee coffee;
    MilkDecorator(Coffee coffee) { this.coffee = coffee; }
    public double cost() { return coffee.cost() + 15; }
    public String description() { return coffee.description() + ", milk"; }
}
class SugarDecorator implements Coffee {
    private final Coffee coffee;
    SugarDecorator(Coffee coffee) { this.coffee = coffee; }
    public double cost() { return coffee.cost() + 5; }
    public String description() { return coffee.description() + ", sugar"; }
}

// Usage: Coffee c = new SugarDecorator(new MilkDecorator(new SimpleCoffee()));
// c.cost() = 70, c.description() = "Simple coffee, milk, sugar"
```

#### Facade

**Intent:** Provide a simplified interface to a complex subsystem.

```java
class OrderFacade {
    private InventoryService inventory = new InventoryService();
    private PaymentService payment = new PaymentService();
    private ShippingService shipping = new ShippingService();

    public boolean placeOrder(String product, String paymentMethod, String address) {
        if (!inventory.check(product)) return false;
        if (!payment.process(paymentMethod)) return false;
        shipping.ship(product, address);
        return true;
    }
}
```

#### Proxy

**Intent:** Provide a surrogate or placeholder for another object to control access.

```java
interface Image { void display(); }

class RealImage implements Image {
    private String filename;
    RealImage(String filename) {
        this.filename = filename;
        loadFromDisk(); // expensive operation
    }
    private void loadFromDisk() { System.out.println("Loading " + filename); }
    public void display() { System.out.println("Displaying " + filename); }
}

class ProxyImage implements Image {
    private RealImage realImage;
    private String filename;
    ProxyImage(String filename) { this.filename = filename; }
    public void display() {
        if (realImage == null) realImage = new RealImage(filename); // lazy loading
        realImage.display();
    }
}
```

### Behavioral Patterns

#### Strategy

**Intent:** Define a family of algorithms, encapsulate each one, and make them interchangeable.

```java
interface SortStrategy {
    void sort(int[] arr);
}
class BubbleSort implements SortStrategy {
    public void sort(int[] arr) { /* bubble sort logic */ }
}
class QuickSort implements SortStrategy {
    public void sort(int[] arr) { /* quick sort logic */ }
}
class MergeSort implements SortStrategy {
    public void sort(int[] arr) { /* merge sort logic */ }
}

class Sorter {
    private SortStrategy strategy;
    Sorter(SortStrategy strategy) { this.strategy = strategy; }
    void setStrategy(SortStrategy strategy) { this.strategy = strategy; }
    void performSort(int[] arr) { strategy.sort(arr); }
}
```

**Interview question:** "Design a payment processing system for Razorpay that supports UPI, Credit Card, and Net Banking." `[Medium]` `[Razorpay]` — Use Strategy pattern.

#### Observer

**Intent:** Define a one-to-many dependency so that when one object changes state, all dependents are notified.

```java
interface EventListener {
    void update(String event, String data);
}

class EventManager {
    private Map<String, List<EventListener>> listeners = new HashMap<>();

    void subscribe(String eventType, EventListener listener) {
        listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
    }

    void notify(String eventType, String data) {
        List<EventListener> subs = listeners.getOrDefault(eventType, List.of());
        subs.forEach(l -> l.update(eventType, data));
    }
}

class OrderService {
    EventManager events = new EventManager();

    void placeOrder(String orderId) {
        // ... order logic ...
        events.notify("ORDER_PLACED", orderId);
    }
}

class EmailAlert implements EventListener {
    public void update(String event, String data) {
        System.out.println("Email sent for " + event + ": " + data);
    }
}
```

#### Command

**Intent:** Encapsulate a request as an object, allowing parameterization and queuing.

```java
interface Command {
    void execute();
    void undo();
}
class LightOnCommand implements Command {
    private Light light;
    LightOnCommand(Light light) { this.light = light; }
    public void execute() { light.on(); }
    public void undo() { light.off(); }
}
```

#### Template Method

**Intent:** Define the skeleton of an algorithm, deferring some steps to subclasses.

```java
abstract class DataProcessor {
    // Template method — defines the algorithm skeleton
    public final void process() {
        readData();
        processData();
        writeData();
    }
    abstract void readData();
    abstract void processData();
    abstract void writeData();
}

class CSVProcessor extends DataProcessor {
    void readData() { System.out.println("Reading CSV"); }
    void processData() { System.out.println("Parsing CSV rows"); }
    void writeData() { System.out.println("Writing to database"); }
}
```

---

## ⚠️ Section 6: Tricky & Advanced OOP Questions

### Constructor Chaining and super() Rules

**Question:** What is the output? `[Hard]`

```java
class A {
    A() { this(10); System.out.println("A()"); }
    A(int x) { System.out.println("A(int): " + x); }
}
class B extends A {
    B() { System.out.println("B()"); }
}
// new B();
```

❌ **Common wrong answer:** "B(), A()"
✅ **Correct answer:** `A(int): 10`, `A()`, `B()`. B() implicitly calls super() → A() → this(10) → A(int).

---

### Static vs Instance Methods in OOP

**Question:** Can you call an instance method from a static context? `[Easy]`

❌ **Common wrong answer:** "No, it will cause a compilation error."
✅ **Correct answer:** You cannot directly call an instance method from a static method. However, you can create an object and call it through that object. The reason: static methods belong to the class, not an instance.

---

### Covariant Return Types

**Question:** Is this code valid? `[Medium]`

```java
class Base { Object getValue() { return new Object(); } }
class Derived extends Base { @Override String getValue() { return "Hello"; } }
```

✅ **Correct:** Yes, since Java 5. `String` is a subtype of `Object`, so this is a valid covariant return type.

---

### Method Hiding vs Method Overriding

**Question:** What is the output? `[Hard]`

```java
class Parent {
    static void print() { System.out.println("Parent static"); }
    void show() { System.out.println("Parent instance"); }
}
class Child extends Parent {
    static void print() { System.out.println("Child static"); }
    @Override void show() { System.out.println("Child instance"); }
}

Parent obj = new Child();
obj.print(); // ?
obj.show();  // ?
```

❌ **Common wrong answer:** "Child static, Child instance"
✅ **Correct answer:** "Parent static, Child instance". `print()` is static — resolved by reference type (Parent), not object type. This is **method hiding**. `show()` is an instance method — resolved at runtime (Child). This is **overriding**.

---

### Object Class Methods — OOP Perspective

**Question:** Why must you override `hashCode()` when you override `equals()`? `[Medium]`

✅ The contract states: if two objects are equal according to `equals()`, they must have the same `hashCode()`. If you break this, objects won't work correctly in hash-based collections (`HashMap`, `HashSet`).

```java
public class Student {
    private int id;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id && Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
```

---

### Immutability and OOP

**Question:** How do you create an immutable class in Java? What are all the rules? `[Hard]`

✅ Rules: (1) Make the class `final`; (2) Make all fields `private final`; (3) No setter methods; (4) Return defensive copies for mutable fields; (5) Initialize all fields via constructor.

---

### Inner Classes, Anonymous Classes, Lambda vs OOP

**Question:** What are the types of inner classes in Java? `[Medium]`

✅ Four types: (1) **Member inner class** — non-static, has access to outer class members; (2) **Static nested class** — doesn't need an outer instance; (3) **Local inner class** — defined inside a method; (4) **Anonymous inner class** — no name, commonly replaced by lambdas.

```java
// Anonymous class (pre-Java 8)
Comparator<String> comp = new Comparator<String>() {
    @Override
    public int compare(String a, String b) { return a.compareTo(b); }
};

// Lambda (Java 8+) — replaces anonymous class for functional interfaces
Comparator<String> compLambda = (a, b) -> a.compareTo(b);
```

---

### Generics and OOP

**Question:** What is the difference between `<? extends T>` and `<? super T>`? `[Hard]`

✅ **PECS: Producer Extends, Consumer Super.**
- `<? extends T>` — read from (produces T). Cannot add to it.
- `<? super T>` — write to (consumes T). Cannot read specific type from it.

```java
// Producer — reads elements
void printAll(List<? extends Number> list) {
    for (Number n : list) System.out.println(n); // ✅ reading
    // list.add(42); // ❌ compile error — can't add
}

// Consumer — writes elements
void addNumbers(List<? super Integer> list) {
    list.add(42);     // ✅ writing
    // Integer n = list.get(0); // ❌ compile error — returns Object
}
```

---

## 💻 Section 7: OOP Coding Problems

### Problem 1: Design a Parking Lot System

**Problem:** Design a parking lot system supporting multiple vehicle types and floors.
**Company:** `[Amazon]` `[Flipkart]` `[Google]`
**Difficulty:** `[Medium]`
**OOP Concept Tested:** Inheritance, Abstraction, Encapsulation
**Approach:** Model vehicles as a hierarchy, parking spots by size, and the lot as a composition of floors.

```java
enum VehicleSize { SMALL, MEDIUM, LARGE }

abstract class Vehicle {
    protected String licensePlate;
    protected VehicleSize size;
    Vehicle(String licensePlate, VehicleSize size) {
        this.licensePlate = licensePlate;
        this.size = size;
    }
    public VehicleSize getSize() { return size; }
    public String getLicensePlate() { return licensePlate; }
}
class Car extends Vehicle {
    Car(String plate) { super(plate, VehicleSize.MEDIUM); }
}
class Motorcycle extends Vehicle {
    Motorcycle(String plate) { super(plate, VehicleSize.SMALL); }
}
class Truck extends Vehicle {
    Truck(String plate) { super(plate, VehicleSize.LARGE); }
}

class ParkingSpot {
    private final int id;
    private final VehicleSize size;
    private Vehicle parkedVehicle;

    ParkingSpot(int id, VehicleSize size) { this.id = id; this.size = size; }

    boolean canFit(Vehicle v) { return parkedVehicle == null && v.getSize().ordinal() <= size.ordinal(); }
    boolean park(Vehicle v) { if (!canFit(v)) return false; parkedVehicle = v; return true; }
    Vehicle unpark() { Vehicle v = parkedVehicle; parkedVehicle = null; return v; }
    boolean isAvailable() { return parkedVehicle == null; }
}

class ParkingFloor {
    private final List<ParkingSpot> spots;
    ParkingFloor(List<ParkingSpot> spots) { this.spots = spots; }

    Optional<ParkingSpot> findSpot(Vehicle v) {
        return spots.stream().filter(s -> s.canFit(v)).findFirst();
    }
}

class ParkingLot {
    private final List<ParkingFloor> floors;
    ParkingLot(List<ParkingFloor> floors) { this.floors = floors; }

    public boolean parkVehicle(Vehicle v) {
        for (ParkingFloor floor : floors) {
            Optional<ParkingSpot> spot = floor.findSpot(v);
            if (spot.isPresent()) { spot.get().park(v); return true; }
        }
        return false;
    }
}
```

([GeeksforGeeks](https://www.geeksforgeeks.org/designing-a-parking-lot-using-object-oriented-principles/))

---

### Problem 2: Library Management System

**Problem:** Design a system to manage books, members, and borrowing.
**Company:** `[Swiggy]` `[Paytm]`
**Difficulty:** `[Medium]`
**OOP Concept Tested:** Encapsulation, Composition, SRP

```java
class Book {
    private final String isbn;
    private final String title;
    private boolean isAvailable = true;

    Book(String isbn, String title) { this.isbn = isbn; this.title = title; }
    boolean isAvailable() { return isAvailable; }
    void setAvailable(boolean available) { isAvailable = available; }
    String getIsbn() { return isbn; }
}

class Member {
    private final String memberId;
    private final String name;
    private final List<Book> borrowedBooks = new ArrayList<>();

    Member(String memberId, String name) { this.memberId = memberId; this.name = name; }

    boolean borrow(Book book) {
        if (borrowedBooks.size() >= 5 || !book.isAvailable()) return false;
        book.setAvailable(false);
        borrowedBooks.add(book);
        return true;
    }

    boolean returnBook(Book book) {
        if (borrowedBooks.remove(book)) { book.setAvailable(true); return true; }
        return false;
    }
}

class Library {
    private final Map<String, Book> catalog = new HashMap<>();

    void addBook(Book book) { catalog.put(book.getIsbn(), book); }
    Optional<Book> findBook(String isbn) { return Optional.ofNullable(catalog.get(isbn)); }
    List<Book> searchByTitle(String keyword) {
        return catalog.values().stream()
            .filter(b -> b.toString().contains(keyword))
            .collect(Collectors.toList());
    }
}
```

---

### Problem 3: Custom Iterator

**Problem:** Implement a custom iterator for a 2D array that iterates element by element.
**Company:** `[Google]` `[Microsoft]`
**Difficulty:** `[Medium]`
**OOP Concept Tested:** Interfaces, Encapsulation

```java
class Matrix2DIterator implements Iterator<Integer> {
    private final int[][] matrix;
    private int row = 0, col = 0;

    Matrix2DIterator(int[][] matrix) { this.matrix = matrix; }

    @Override
    public boolean hasNext() {
        while (row < matrix.length && col >= matrix[row].length) { row++; col = 0; }
        return row < matrix.length;
    }

    @Override
    public Integer next() {
        if (!hasNext()) throw new NoSuchElementException();
        return matrix[row][col++];
    }
}
```

---

### Problem 4: Notification System using Observer Pattern

**Problem:** Build a notification system where users subscribe to topics.
**Company:** `[CRED]` `[Razorpay]`
**Difficulty:** `[Medium]`
**OOP Concept Tested:** Observer Pattern, Interfaces, OCP

```java
interface Subscriber {
    void onNotification(String topic, String message);
}

class NotificationService {
    private final Map<String, List<Subscriber>> topicSubscribers = new HashMap<>();

    void subscribe(String topic, Subscriber sub) {
        topicSubscribers.computeIfAbsent(topic, k -> new ArrayList<>()).add(sub);
    }

    void publish(String topic, String message) {
        topicSubscribers.getOrDefault(topic, List.of())
            .forEach(s -> s.onNotification(topic, message));
    }
}

class EmailSubscriber implements Subscriber {
    private final String email;
    EmailSubscriber(String email) { this.email = email; }
    public void onNotification(String topic, String message) {
        System.out.println("Email to " + email + ": [" + topic + "] " + message);
    }
}

class SMSSubscriber implements Subscriber {
    private final String phone;
    SMSSubscriber(String phone) { this.phone = phone; }
    public void onNotification(String topic, String message) {
        System.out.println("SMS to " + phone + ": [" + topic + "] " + message);
    }
}
```

---

### Problem 5: Strategy Pattern for Payment Processing

**Problem:** Design a payment system supporting multiple payment methods.
**Company:** `[Razorpay]` `[PhonePe]` `[Paytm]`
**Difficulty:** `[Medium]`
**OOP Concept Tested:** Strategy Pattern, DIP, OCP

```java
interface PaymentStrategy {
    boolean pay(double amount);
}

class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    CreditCardPayment(String cardNumber) { this.cardNumber = cardNumber; }
    public boolean pay(double amount) {
        System.out.println("Paid ₹" + amount + " via Credit Card ending " + cardNumber.substring(12));
        return true;
    }
}

class UPIPayment implements PaymentStrategy {
    private String upiId;
    UPIPayment(String upiId) { this.upiId = upiId; }
    public boolean pay(double amount) {
        System.out.println("Paid ₹" + amount + " via UPI: " + upiId);
        return true;
    }
}

class PaymentContext {
    private PaymentStrategy strategy;
    void setStrategy(PaymentStrategy strategy) { this.strategy = strategy; }
    boolean executePayment(double amount) { return strategy.pay(amount); }
}
```

---

### Problem 6: ATM System

**Problem:** Design a basic ATM machine with OOP.
**Company:** `[Amazon]` `[Groww]`
**Difficulty:** `[Medium]`
**OOP Concept Tested:** Encapsulation, State Pattern, SRP

```java
class ATM {
    private final BankService bankService;
    private Account currentAccount;

    ATM(BankService bankService) { this.bankService = bankService; }

    boolean authenticate(String cardNumber, String pin) {
        currentAccount = bankService.authenticate(cardNumber, pin);
        return currentAccount != null;
    }

    double checkBalance() { return currentAccount.getBalance(); }

    boolean withdraw(double amount) {
        if (currentAccount.getBalance() >= amount) {
            currentAccount.debit(amount);
            return true;
        }
        return false;
    }

    void deposit(double amount) { currentAccount.credit(amount); }
    void logout() { currentAccount = null; }
}
```

---

### Problem 7: LRU Cache using OOP

**Problem:** Implement an LRU Cache with O(1) operations.
**Company:** `[Google]` `[Amazon]` `[Meta]`
**Difficulty:** `[Hard]`
**OOP Concept Tested:** Encapsulation, Composition

```java
class LRUCache<K, V> {
    private final int capacity;
    private final Map<K, Node<K, V>> map;
    private final DoublyLinkedList<K, V> dll;

    LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.dll = new DoublyLinkedList<>();
    }

    V get(K key) {
        if (!map.containsKey(key)) return null;
        Node<K, V> node = map.get(key);
        dll.moveToHead(node);
        return node.value;
    }

    void put(K key, V value) {
        if (map.containsKey(key)) {
            Node<K, V> node = map.get(key);
            node.value = value;
            dll.moveToHead(node);
        } else {
            if (map.size() == capacity) {
                Node<K, V> tail = dll.removeTail();
                map.remove(tail.key);
            }
            Node<K, V> newNode = new Node<>(key, value);
            dll.addToHead(newNode);
            map.put(key, newNode);
        }
    }

    private static class Node<K, V> {
        K key; V value; Node<K, V> prev, next;
        Node(K key, V value) { this.key = key; this.value = value; }
    }

    private static class DoublyLinkedList<K, V> {
        Node<K, V> head, tail;
        DoublyLinkedList() {
            head = new Node<>(null, null);
            tail = new Node<>(null, null);
            head.next = tail; tail.prev = head;
        }
        void addToHead(Node<K, V> node) {
            node.next = head.next; node.prev = head;
            head.next.prev = node; head.next = node;
        }
        void remove(Node<K, V> node) {
            node.prev.next = node.next; node.next.prev = node.prev;
        }
        void moveToHead(Node<K, V> node) { remove(node); addToHead(node); }
        Node<K, V> removeTail() {
            Node<K, V> node = tail.prev; remove(node); return node;
        }
    }
}
```

---

### Problem 8: Chess Game Class Hierarchy

**Problem:** Design the class hierarchy for a chess game.
**Company:** `[Google]` `[Microsoft]` `[Flipkart]`
**Difficulty:** `[Hard]`
**OOP Concept Tested:** Inheritance, Polymorphism, Abstraction

```java
enum Color { WHITE, BLACK }

class Position {
    final int row, col;
    Position(int row, int col) { this.row = row; this.col = col; }
}

abstract class Piece {
    protected Color color;
    protected Position position;

    Piece(Color color, Position position) { this.color = color; this.position = position; }
    abstract List<Position> validMoves(Board board);
    void moveTo(Position newPos) { this.position = newPos; }
}

class King extends Piece {
    King(Color color, Position pos) { super(color, pos); }
    List<Position> validMoves(Board board) {
        // King moves one square in any direction
        List<Position> moves = new ArrayList<>();
        int[][] dirs = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};
        for (int[] d : dirs) {
            Position p = new Position(position.row + d[0], position.col + d[1]);
            if (board.isValid(p) && !board.isOccupiedBySameColor(p, color)) moves.add(p);
        }
        return moves;
    }
}

class Rook extends Piece {
    Rook(Color color, Position pos) { super(color, pos); }
    List<Position> validMoves(Board board) {
        // Rook moves along rows and columns
        // ... implementation
        return new ArrayList<>();
    }
}
```

---

### Additional Problems (Brief)

| # | Problem | Company | Difficulty | Key OOP Concept |
|---|---------|---------|------------|-----------------|
| 9 | Design Splitwise (expense sharing) | `[Swiggy]` `[CRED]` | `[Hard]` | Composition, Strategy |
| 10 | Design Snake & Ladder | `[Flipkart]` `[Zepto]` | `[Medium]` | State management, Abstraction |
| 11 | Design Elevator System | `[Amazon]` `[Microsoft]` | `[Hard]` | State Pattern, Observer |
| 12 | Design File System | `[Google]` `[Meta]` | `[Hard]` | Composite Pattern, Inheritance |
| 13 | Design Tic-Tac-Toe | `[Amazon]` `[Swiggy]` | `[Easy]` | Encapsulation, Polymorphism |
| 14 | Design Vending Machine | `[Amazon]` `[Razorpay]` | `[Medium]` | State Pattern, SRP |
| 15 | Design Logger (Singleton + Strategy) | `[Groww]` `[PhonePe]` | `[Medium]` | Singleton, Strategy, ISP |

---

## 🔥 Section 8: FAANG-Specific Deep Questions

### Google

Google interviews emphasize **system design quality** and **clean abstractions**. OOP isn't asked directly — it's expected implicitly.

1. **"Design a type-ahead search system. How would you model the Trie node hierarchy?"** `[Hard]` — Expect discussion of abstract base node, leaf node with weight, and interface for search results.
2. **"You have different types of search results (web, image, video, news). Design a class hierarchy to render them uniformly."** `[Medium]` — Tests polymorphism and Strategy/Visitor pattern.
3. **"How would you design a plugin system that allows third-party extensions without recompiling the core?"** `[Hard]` — Tests OCP, DIP, and interface design.
4. **"Compare inheritance and composition. When would you use each in a large-scale distributed system?"** `[Medium]`
5. **"Design the class structure for Google Calendar events — recurring, all-day, timed events."** `[Hard]` — Tests Template Method, Builder pattern.

💡 **Pro Tip:** Google interviewers value **simplicity**. Don't over-engineer. Start with the simplest class hierarchy and extend only when needed.

### Amazon (Leadership Principles Angle on OOP)

Amazon uniquely ties OOP to Leadership Principles. You may be asked to justify design choices using principles like "Bias for Action" or "Invent and Simplify" ([Amazon Careers](https://amazon.jobs/content/en/how-we-hire/interview-prep/software-development-topics)).

1. **"Design an order management system. Apply SOLID principles and explain which Leadership Principle each supports."** `[Hard]`
2. **"How would you design the class hierarchy for Amazon's delivery fleet (trucks, drones, lockers)?"** `[Medium]` — Inheritance + Strategy.
3. **"Design a pricing engine that handles base price, discounts, taxes, and shipping. How do you make it extensible?"** `[Hard]` — Decorator + Strategy.
4. **"A junior developer wrote a 2000-line God class. How would you refactor it?"** `[Medium]` — SRP, Extract Class refactoring.
5. **"Design an inventory management system where items can be physical, digital, or subscription-based."** `[Medium]`

Amazon's online assessment also includes 20 MCQs covering OOPs, DBMS, OS, and DSA ([GeeksforGeeks](https://www.geeksforgeeks.org/dsa/amazon-sde-sheet-interview-questions-and-answers/)).

### Microsoft

Microsoft values **clean code and testability**. OOP questions focus on design for testing.

1. **"Design a document editor (like Word) class hierarchy: Document, Paragraph, Text, Image."** `[Hard]` — Composite pattern.
2. **"How would you design an extensible spell-checker that supports multiple languages?"** `[Medium]` — Strategy + Factory.
3. **"Design the class hierarchy for Visual Studio's debugger breakpoints (line, conditional, function)."** `[Hard]`
4. **"How do you design for testability? How does DIP help?"** `[Medium]`
5. **"Explain the difference between coupling and cohesion. Which do you prefer and why?"** `[Easy]`

### Meta/Facebook

Meta's coding rounds focus on algorithms but expect clean OOP structure in solutions ([interviewing.io](https://interviewing.io/mocks/facebook-java-infinite-binary-print)).

1. **"Design a social feed system. How do you model Post, Comment, Reaction with OOP?"** `[Hard]`
2. **"Implement a rate limiter. Design the class hierarchy for different limiting strategies (token bucket, sliding window)."** `[Hard]` — Strategy pattern.
3. **"Design a permission system for Facebook Groups (admin, moderator, member)."** `[Medium]` — Role hierarchy.
4. **"How would you model different types of content (text, image, video, story) in a news feed?"** `[Medium]`
5. **"Design an event-driven notification system using Observer pattern."** `[Medium]`

### Apple

Apple focuses on **API design elegance** and frameworks.

1. **"Design a media player that handles audio, video, and streaming content."** `[Hard]` — Inheritance + Strategy.
2. **"How would you design a constraint-based layout system (like Auto Layout) using OOP?"** `[Hard]`
3. **"Design a build system plugin architecture using interfaces."** `[Medium]`
4. **"How would you model accessibility features across different UI components?"** `[Medium]` — Decorator pattern.
5. **"Design an undo/redo system for a text editor."** `[Medium]` — Command pattern.

---

## 🚀 Section 9: Indian Startup Specific Questions

### Razorpay
1. **"Design a payment gateway supporting multiple payment methods (UPI, card, net banking, wallet)."** `[Hard]` — Strategy + Factory.
2. **"How would you model recurring subscriptions with different billing cycles?"** `[Medium]`
3. **"Design a settlement system — how do you model merchants, transactions, and payouts?"** `[Hard]`

### Zepto
1. **"Design a real-time order tracking system. Model the order state transitions."** `[Medium]` — State pattern.
2. **"Model the inventory system for a dark store with perishable and non-perishable items."** `[Medium]`

### Swiggy
Swiggy is known for rigorous machine coding rounds ([CodeJeet](https://codejeet.com/compare/swiggy-vs-zepto)).
1. **"Design a food delivery system — restaurants, menus, orders, delivery partners."** `[Hard]`
2. **"Implement Splitwise — track expenses between friends."** `[Hard]`
3. **"Design a coupon system supporting percentage, flat, and BOGO discounts."** `[Medium]` — Strategy pattern.

### CRED
1. **"Design a credit score calculation engine with pluggable scoring models."** `[Hard]` — Strategy + Template Method.
2. **"Design a rewards/cashback system with different reward types."** `[Medium]`

### Groww
1. **"Design a portfolio management system. Model stocks, mutual funds, and FDs."** `[Hard]`
2. **"Design a thread-safe Singleton for the market data service."** `[Medium]`

### PhonePe
1. **"Design UPI payment flow with request, collect, and pay modes."** `[Hard]`
2. **"Design a wallet system with multiple currency support."** `[Medium]`

### Meesho
1. **"Design a product catalog with categories, variants, and pricing."** `[Medium]`
2. **"Design a reseller commission system."** `[Medium]`

### Zomato
1. **"Design a restaurant search and filter system."** `[Medium]`
2. **"Model different order types: dine-in, delivery, takeaway."** `[Easy]` — Inheritance + Polymorphism.

### Flipkart
Flipkart is famous for its LLD/machine coding rounds.
1. **"Design a shopping cart with discounts, taxes, and multiple sellers."** `[Hard]`
2. **"Design a notification preference system (email, SMS, push, in-app)."** `[Medium]`
3. **"Design Snake & Ladder game — complete working code in 90 minutes."** `[Medium]`

### Paytm
1. **"Design a bill payment system supporting electricity, gas, DTH, mobile recharge."** `[Medium]`
2. **"Design a transaction ledger using immutable objects."** `[Medium]`

---

## 📝 Section 10: Quick Reference Cheat Sheet

### Key OOP Terms

| Term | One-Line Definition |
|------|-------------------|
| Class | Blueprint for creating objects |
| Object | Instance of a class with state and behavior |
| Encapsulation | Wrapping data and methods, restricting access |
| Inheritance | Child class acquiring properties of parent class |
| Polymorphism | Same interface, multiple implementations |
| Abstraction | Hiding implementation, showing only functionality |
| Composition | Has-a relationship (Car has an Engine) |
| Aggregation | Weak has-a (Department has Professors, but they exist independently) |
| Association | Relationship between two separate classes |
| Coupling | Degree of dependency between classes (lower is better) |
| Cohesion | Degree to which a class focuses on a single task (higher is better) |

### Interface vs Abstract Class

| Feature | Interface | Abstract Class |
|---------|-----------|---------------|
| Instantiation | ❌ | ❌ |
| Constructor | ❌ | ✅ |
| Multiple inheritance | ✅ (implements multiple) | ❌ (extends one) |
| Instance variables | ❌ (only constants) | ✅ |
| Default methods | ✅ (Java 8+) | ✅ (always had concrete methods) |
| Static methods | ✅ (Java 8+) | ✅ |
| Private methods | ✅ (Java 9+) | ✅ |
| Access modifiers for methods | `public` only (abstract) | Any |
| When to use | Define a contract/capability | Share code + state among related classes |

### Access Modifiers Scope

| Modifier | Same Class | Same Package | Subclass (diff pkg) | Everywhere |
|----------|-----------|-------------|---------------------|------------|
| `public` | ✅ | ✅ | ✅ | ✅ |
| `protected` | ✅ | ✅ | ✅ | ❌ |
| default (no keyword) | ✅ | ✅ | ❌ | ❌ |
| `private` | ✅ | ❌ | ❌ | ❌ |

([DEV Community](https://dev.to/sugumar_r_a5f301adf1fb49a/access-modifier-1mpf), [GeeksforGeeks](https://www.geeksforgeeks.org/java/access-modifiers-java/), [Scientech Easy](https://www.scientecheasy.com/2018/05/access-modifiers-interview-question-answer.html/))

### Overloading vs Overriding

| Aspect | Overloading | Overriding |
|--------|-------------|------------|
| Definition | Same name, different parameters | Same name, same parameters in subclass |
| Binding | Compile-time | Runtime |
| Return type | Can differ | Same or covariant |
| Exceptions | No restriction | Cannot throw broader checked exceptions |
| `static` | ✅ Can overload | ❌ Cannot override (only hide) |
| `final` | ✅ Can overload | ❌ Cannot override |
| `private` | ✅ Can overload | ❌ Not visible to subclass |
| `@Override` | Not applicable | Recommended |

([GeeksforGeeks](https://www.geeksforgeeks.org/java/difference-between-method-overloading-and-method-overriding-in-java/))

### Composition vs Inheritance Decision Guide

| Use Composition When... | Use Inheritance When... |
|------------------------|----------------------|
| You need HAS-A relationship | You need IS-A relationship |
| You want loose coupling | Classes share significant behavior |
| You need multiple "inheritances" | There's a clear hierarchical relationship |
| Behavior may change at runtime | Behavior is fixed at compile-time |
| You want to follow "Favor composition over inheritance" | You're modeling a true type hierarchy (Animal → Dog) |

---

## 🗺️ Section 11: Study Plan

### 1-Week Intensive Plan

| Day | Topic | Time | Activities |
|-----|-------|------|------------|
| **Day 1** | Four Pillars | 4 hrs | Study all four pillars with code examples. Write 10 programs demonstrating each. |
| **Day 2** | Interfaces, Abstract Classes, Access Modifiers | 4 hrs | Code all examples from Section 3. Practice tricky questions. |
| **Day 3** | SOLID Principles | 4 hrs | Study each principle. Refactor 3 "bad" code examples to follow SOLID. |
| **Day 4** | Design Patterns (Creational + Structural) | 5 hrs | Implement Singleton, Factory, Builder, Adapter, Decorator from scratch. |
| **Day 5** | Design Patterns (Behavioral) + Advanced Topics | 5 hrs | Implement Strategy, Observer, Template Method. Study tricky questions from Section 6. |
| **Day 6** | OOP Coding Problems | 6 hrs | Solve: Parking Lot, Library System, Payment Processing, LRU Cache. |
| **Day 7** | Mock Interviews + Review | 5 hrs | Do 2 mock LLD interviews. Review all cheat sheets. Revise weak areas. |

### 2-Week Comfortable Plan

**Week 1: Fundamentals**
- Day 1-2: Four Pillars (deep study + coding)
- Day 3: Interfaces vs Abstract Classes + Access Modifiers
- Day 4-5: SOLID Principles with refactoring exercises
- Day 6: Composition vs Inheritance, Generics, Inner Classes
- Day 7: Review + Mini quiz

**Week 2: Application & Practice**
- Day 8-9: Design Patterns (all 12 from Section 5)
- Day 10-11: OOP Coding Problems (5 per day)
- Day 12: Company-specific preparation (choose your target companies)
- Day 13: 2 full mock interviews
- Day 14: Final review, cheat sheets, rest

### Daily Practice Routine

1. **Morning (30 min):** Review one cheat sheet table from Section 10
2. **Afternoon (1.5 hrs):** Solve one LLD problem end-to-end
3. **Evening (1 hr):** Read through 10 interview questions and practice explaining answers out loud
4. **Before bed (15 min):** Review tricky questions from Section 6

💡 **Pro Tip:** Record yourself answering questions. Interviewers value clear verbal explanations as much as correct code ([Sprintzeal](https://www.sprintzeal.com/blog/java-oops-interview-questions)).

---

## 📚 Section 12: Resources

### Books
| Book | Author | Best For |
|------|--------|----------|
| *Effective Java* (3rd Edition) | Joshua Bloch | Advanced Java OOP best practices — **must read** |
| *Head First Design Patterns* | Eric Freeman | Visual, beginner-friendly pattern learning |
| *Design Patterns: Elements of Reusable OO Software* | Gang of Four | Comprehensive reference for all 23 patterns |
| *Clean Code* | Robert C. Martin | Writing maintainable OOP code |
| *Head First Object-Oriented Analysis and Design* | Brett McLaughlin | OOP fundamentals with engaging exercises |

### Websites
- [GeeksforGeeks — Java OOP Interview Questions](https://www.geeksforgeeks.org/java/oops-interview-questions-java-programming/) — Comprehensive question bank with solutions
- [InterviewBit — OOPs Interview Questions](https://www.interviewbit.com/oops-interview-questions/) — 40+ questions updated for 2026
- [Javarevisited](https://javarevisited.blogspot.com/2013/05/10-abstract-class-interface-interview-questions-answers-java.html) — Deep dives on abstract class and interface questions
- [DEV Community — 100+ Senior Java Questions](https://dev.to/haraf/100-senior-java-developer-interview-questions-and-answers-2025-edition-4f6n) — Senior-level comprehensive guide
- [LeetCode Discuss](https://leetcode.com/discuss/) — Real interview experiences from FAANG candidates
- [Refactoring.Guru](https://refactoring.guru/design-patterns) — Best visual guide for design patterns

### YouTube Channels
- **Java Guides** — [OOP Interview Questions playlist](https://www.youtube.com/watch?v=yL1D0opZwwA) — Covers 25+ questions with explanations
- **Naveen Automation Labs** — Tricky method overloading questions with JVM internals
- **Concept && Coding (by Shreyansh)** — Best for LLD/machine coding in Indian context
- **Sudocode** — System design with strong OOP focus

### GitHub Repositories
- [Devinterview-io/oop-interview-questions](https://github.com/Devinterview-io/oop-interview-questions) — 52 curated OOP questions for 2026
- [iluwatar/java-design-patterns](https://github.com/iluwatar/java-design-patterns) — Design patterns implemented in Java
- [tssovi/grokking-the-object-oriented-design-interview](https://github.com/tssovi/grokking-the-object-oriented-design-interview) — Solutions to Educative.io OOD course

### Practice Platforms
- **Educative.io** — "Grokking the Object-Oriented Design Interview" course
- **LeetCode** — OOP-based problems (Design HashMap, Design Browser History, etc.)
- **Workat.tech** — LLD problems specifically for Indian startup interviews
- **CodeZym** — Machine coding practice with timer

---

*This guide was compiled using research from [GeeksforGeeks](https://www.geeksforgeeks.org/java/oops-interview-questions-java-programming/), [InterviewBit](https://www.interviewbit.com/oops-interview-questions/), [Edureka](https://www.edureka.co/blog/interview-questions/oops-interview-questions/), [DEV Community](https://dev.to/haraf/100-senior-java-developer-interview-questions-and-answers-2025-edition-4f6n), [Javarevisited](https://javarevisited.blogspot.com/2013/05/10-abstract-class-interface-interview-questions-answers-java.html), [Hirist Blog](https://www.hirist.tech/blog/top-50-java-oops-interview-questions-and-answers/), [Sprintzeal](https://www.sprintzeal.com/blog/java-oops-interview-questions), [AlmaBetter](https://www.almabetter.com/bytes/articles/java-oops-interview-questions), [Scientech Easy](https://www.scientecheasy.com/2018/05/access-modifiers-interview-question-answer.html/), [Amazon Careers](https://amazon.jobs/content/en/how-we-hire/interview-prep/software-development-topics), and community discussions on [Reddit](https://www.reddit.com/r/developersIndia/comments/1euktpr/abstract_class_vs_interface_what_to_be_preferred/) and [interviewing.io](https://interviewing.io/mocks/facebook-java-infinite-binary-print). Last updated: March 2026.*
