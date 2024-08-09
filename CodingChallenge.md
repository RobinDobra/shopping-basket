# Shopping-Basket

Implement a virtual shopping-basket in the programming language of your choice (Java, Ruby, Elixir, JavaScript, ...). It should be possible to add items to the shopping-basket. Implementing a graphical user interface (GUI) is not necessary - it's enough to show the behavior using test cases.
The following `API` shows the desired interface/behavior in pseudo-code. The final implementation can deviate from that.

## API
A warehouse has a set of products with fixed prices

```
INVENTORY = [["A0001", 12.99], ["A0002", 3.99], ...]
```

Each user has a shopping-basket

```
basket = Basket.new
```

It is possible to add items to the shopping-basket

```
basket.scan("A0001")
```

A user can check the total price of all items in his shopping basket at any given time

```
basket.total
=> 12.99 Euro
```

## Task
Additionally, certain sales deals shall be supported:

* Buy 1 get 1 free for a certain article

```
# Buy1Get1Free A0002
basket.scan("A0002")
basket.scan("A0001")
basket.scan("A0002")
basket.total
=> 16.98
```

* 10% off a given article

```
# 10Percent A0001
basket.scan("A0002")
basket.scan("A0001")
basket.scan("A0002")
basket.total
=> 19.67
```

