# Week 8 — Quiz and Concept Answers

Written answers for the non-coding half of the Week 8 Category A practice set.
The five coding questions are implemented in `class_problems/`.

---

## Quiz Questions

| # | Answer | Why |
| --- | --- | --- |
| 1 | **C** — Abstraction and Runtime Polymorphism | The engine depends on a common transaction abstraction and dispatches to the concrete type at runtime, so a new transaction type needs no change to the engine. |
| 2 | **B, C, D** — Runtime Polymorphism, Inheritance, Dynamic Dispatch | `Car` and `Motorcycle` inherit from `Vehicle`, and the call is bound to the actual object's method at run time. Not overloading (one signature, not several) and not object identity. |
| 3 | **D** — State Diagram | Available and Borrowed are discrete states with guarded transitions between them, which is exactly what a state machine diagram models. |
| 4 | **C** — Composition | An `Order` cannot exist without its `Customer` and dies with it. That is a whole-part relationship with lifecycle ownership, which composition denotes. (Composition is a specialised form of association, so **A** is true but far less precise.) |
| 5 | **D** — Composition | `LineItem` objects are created, owned and destroyed by the `FoodOrder` and can never exist independently. |
| 6 | **C** — Abstraction | `PaymentProcessor` depends on the `IPaymentMethod` abstraction rather than on any concrete method, so adding one requires no change to the processor. |
| 7 | **C** — Sequence Diagram | The question is about the ordered exchange of messages between specific objects over time, which is what lifelines and arrows in a sequence diagram show. |
| 8 | **A, B, D** — Data Hiding, Encapsulation, Maintaining Valid Object State | The field is private (data hiding), reachable only through a method that bundles data with the rule (encapsulation), and that rule refuses a negative value so the object can never hold an invalid salary. No inheritance is involved. |
| 9 | **D** — Realization | A class implementing an interface is drawn as realization (dashed line, hollow triangle). Generalization is class-to-class inheritance, which is a different arrow. |
| 10 | **C** — Course 1 --- 0..50 Student, Student 1 --- 0..* Course | A course caps at 50 students and may currently have none; a student may be enrolled in any number of courses, including none. |

---

## Concept Questions

### 1. Invariants in a ShoppingCart

An invariant is a condition that must hold true for an object at every moment a caller
can observe it. For `ShoppingCart` the invariant is `totalPrice == sum(lineItem prices)`.

If `totalPrice` were a public field, any outside code could set it to 999 while the line
items still summed to 250. The object would then be in a state no valid sequence of
operations could have produced — it is not wrong data so much as a broken object, and
every later calculation built on it inherits the error.

Encapsulation is what prevents this. `totalPrice` becomes private with no setter, and the
only way to change the cart is through `addItem(...)` and `removeItem(...)`. Because those
methods own both halves of the change, they update the line items and recompute the total
in the same step, so the invariant is restored before the method returns. Better still,
`totalPrice` need not be stored at all: a `getTotalPrice()` that sums the line items on
demand makes the invariant impossible to violate rather than merely guarded. The general
rule is that every state change must run through a method that is responsible for leaving
the object valid, and no path may exist that skips it.

### 2. Why composition beats inheritance for SmartDevice capabilities

Modelling optional capabilities with inheritance forces one subclass per *combination*, not
per capability. Three capabilities already demand `SmartDeviceWithCamera`,
`SmartDeviceWithGps`, `SmartDeviceWithBluetooth`, `SmartDeviceWithCameraAndGps`, and so on —
2^n classes for n capabilities, and a fourth capability doubles the hierarchy again. Worse,
the combinations are fixed at compile time, and Java's single inheritance means a device can
never sit under two capability parents at once.

Composition inverts this. `SmartDevice` holds its capabilities as fields:

    class SmartDevice {
        private Camera camera;        // null when absent
        private GpsModule gps;
        private BluetoothRadio bluetooth;
    }

Now one class covers every combination, because the combination is data rather than type.
A device with a camera and GPS is the same class as one with neither, just built differently,
and a capability can even be fitted after construction. Adding NFC means writing one new
`NfcChip` class and one new field — no existing class changes and no subclass explodes.

The capability classes also become independently testable and reusable: the same `Camera`
can serve a phone, a doorbell and a dashcam. The honest test is the IS-A question — a
smart device with a camera is not a *kind of* smart device, it is a smart device that *has*
a camera, and "has a" is precisely what composition expresses.

### 3. Sequence diagram reasoning for the checkout flow

A sequence diagram places each participant — `CheckoutManager`, `PaymentGateway`,
`BankService` — at the top as a box with a dashed **lifeline** dropping down. The vertical
axis is time, so a message drawn lower happens later; nothing else in the diagram encodes
ordering, which is why the layout itself carries the meaning.

The flow reads as four messages:

1. `CheckoutManager` → `PaymentGateway`: `processPayment(amount)`, a solid arrow with a
   filled head (a synchronous call). An activation bar appears on the gateway's lifeline,
   showing it now holds control.
2. `PaymentGateway` → `BankService`: `authorize(cardDetails, amount)`, nested inside the
   first activation — visibly showing the gateway is still mid-call while it waits.
3. `BankService` → `PaymentGateway`: a **return message**, dashed with an open arrowhead,
   carrying the authorisation result. Its activation bar ends there.
4. `PaymentGateway` → `CheckoutManager`: a dashed return carrying the payment status, and
   the gateway's activation bar closes.

The nesting is the diagram's real payoff: it shows at a glance that the checkout manager is
blocked for the whole duration of the bank round-trip, and that it never talks to the bank
itself — an isolation you could not see in a class diagram, which shows only that the
association exists, not who calls whom, in what order, or how deep the call stack goes.
For debugging a distributed interaction, that ordering is usually the thing you need.

### 4. Abstraction and extensibility in a NotificationService

Define the abstraction first:

    interface NotificationChannel {
        void send(String recipient, String message);
    }

`EmailChannel`, `SmsChannel` and `PushChannel` each implement it. `NotificationService`
holds a `NotificationChannel` reference and never names a concrete class, so its code
contains no `if (type == EMAIL)` branch anywhere — the decision of *which* channel is made
once, where the service is constructed, and the service itself only knows the contract.

Adding a messaging-app integration then means writing `WhatsAppChannel implements
NotificationChannel` and registering it. `NotificationService` is not recompiled, not
retested and not even reopened, and the client calling `notify(...)` is entirely unaware
anything changed. That is the Open/Closed Principle in practice: open to new channels,
closed to modification.

The dependency direction is the key idea. Without the interface, the service depends on
concrete classes and every new channel edits a central `switch` — a file that grows forever
and where each edit risks the channels that already worked. With the interface, both the
service and the channels depend on the abstraction instead, so new behaviour arrives by
*addition* rather than by *modification*. It also makes testing trivial: a fake channel that
records calls substitutes for the real one with no network involved.

### 5. Aggregation versus composition — University, Department, Professor

Both are whole-part relationships; they differ only in **ownership and lifecycle
dependency**, which is why the same scenario needs both.

**University → Department is composition** (filled diamond at the University end). A
department has no meaning outside its university, is created by it, and is destroyed with
it. Close the university and "the Department of Physics" simply stops existing as an entity —
its lifetime is strictly contained within the whole's. A part in a composition also belongs
to exactly one whole: a department is never shared between two universities.

**Department → Professor is aggregation** (hollow diamond at the Department end). The
department groups professors, but it does not own them. A professor exists as a person and a
career independently, survives the department's closure, and can move to another institution —
so the lifetimes are merely linked, not nested. Aggregation also permits sharing: one
professor can be affiliated with two departments at once, which composition would forbid.

The test that decides it is: *if I delete the whole, must the part be deleted too?* Yes for
departments, so composition. No for professors, so aggregation. In code the distinction
shows up as who constructs what — a `University` typically builds its own `Department`
objects, whereas a `Department` receives already-existing `Professor` objects from outside.
