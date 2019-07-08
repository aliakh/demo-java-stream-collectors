# Predefined Java Stream Collectors

## Introduction

There are several ways to reduce Stream as a sequence of input elements into a single summary result. One of them is to use implementations of [Collector](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/stream/Collector.html) interface with [Stream.collect(collector)](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/stream/Stream.html#collect(java.util.stream.Collector)) method. It's possible to implement this interface explicitly, but it should start with studying its predefined implementations from [Collectors](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/stream/Collectors.html) class.

## Classification of predefined collectors

There are 44 _public static_ factory methods in [Collectors](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/stream/Collectors.html) class (up to Java 12) that return predefined implementations of [Collector](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/stream/Collector.html) interface. To understand them better, it's rational to divide them into categories, for example:

*   [collectors to collections](#collectors-to-collections)
    *   [regular collectors to collections](#regular-collectors-to-collections)
    *   [collectors to unmodifiable collections](#collectors-to-unmodifiable-collections)
*   [downstream-designed collectors](#downstream-designed-collectors)
    *   [analogs of stream _intermediate_ operations](#analogs-of-stream-intermediate-operations)
    *   [analogs of stream _terminal_ operations](#analogs-of-stream-terminal-operations)
    *   [analogs of stream _reduce_ operations](#analogs-of-stream-reduce-operations)
*   [collectors to maps](#collectors-to-maps)
    *   ["to-map" collectors to maps](#to-map-collectors-to-maps)
        *   [regular collectors to maps](#regular-collectors-to-maps)
        *   [collectors to unmodifiable maps](#collectors-to-unmodifiable-maps)
        *   [concurrent collectors to maps](#concurrent-collectors-to-maps)
    *   ["grouping-by" collectors to maps](#grouping-by-collectors-to-maps)
        *   [grouping collectors to maps](#grouping-collectors-to-maps)
        *   [partitioning collectors to maps](#partitioning-collectors-to-maps)
        *   [concurrent grouping collectors to maps](#concurrent-grouping-collectors-to-maps)
*   [other collectors](#other-collectors)

It's reasonable to use _static import_ from [Collectors](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/stream/Collectors.html) class to make source code more readable.

## Collectors to collections

Collectors to reduce input elements into collections are the simplest. They allow collecting streams into List, Set, and a specific Collection.

### Regular collectors to collections

To collect Stream to List it's possible to use a collector from _toList_ method. There are no guarantees about type, mutability, serializability, or thread-safety of the returned List.

```
List<Integer> list = Stream.of(1, 2, 3)
       .collect(toList());

assertThat(list)
       .hasSize(3)
       .containsOnly(1, 2, 3);
```

To collect Stream to Set it's possible to use a collector from _toSet_ method. There are no guarantees about type, mutability, serializability, or thread-safety of the returned Set.

```
Set<Integer> set = Stream.of(1, 1, 2, 2, 3, 3)
       .collect(toSet());

assertThat(set)
       .hasSize(3)
       .containsOnly(1, 2, 3);
```

To collect Stream into a specific Collection it's possible to use a collector from _toCollection(collectionFactory)_ method. Here is used constructor reference to ArrayList as a _factory_ to a specific Collection.

```
List<Integer> list = Stream.of(1, 2, 3)
       .collect(toCollection(ArrayList::new));

assertThat(list)
       .hasSize(3)
       .containsOnly(1, 2, 3)
       .isExactlyInstanceOf(ArrayList.class);
```

### Collectors to unmodifiable collections

Collections that do not support modification operations are referred to as _[unmodifiable](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/Collection.html#unmodifiable)_. Such collections cannot be modified by calling any mutator methods, they are guaranteed to throw UnsupportedOperationException. But only if elements of such collections are immutable, collections can be considered as _immutable_ itself.

To collect stream to _[unmodifiable List](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/List.html#unmodifiable)_ it's possible to use a collector from _toUnmodifiableList_ method (since Java 10). Elements of such lists cannot be added, removed, or replaced.

```
List<Integer> unmodifiableList = Stream.of(1, 2, 3)
       .collect(toUnmodifiableList());

assertThat(unmodifiableList)
       .hasSize(3)
       .containsOnly(1, 2, 3);

assertThatThrownBy(unmodifiableList::clear)
       .isInstanceOf(UnsupportedOperationException.class);
```

To collect Stream to _[unmodifiable Set](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/Set.html#unmodifiable)_ it's possible to use a collector from _toUnmodifiableSet_ method (since Java 10). Elements of these sets cannot be added or removed.

```
Set<Integer> unmodifiableSet = Stream.of(1, 1, 2, 2, 3, 3)
       .collect(toUnmodifiableSet());

assertThat(unmodifiableSet)
       .hasSize(3)
       .containsOnly(1, 2, 3);

assertThatThrownBy(unmodifiableSet::clear)
       .isInstanceOf(UnsupportedOperationException.class);
```

To collect streams to unmodifiable collections before Java 10, it's possible to use a collector from [described below](#other-collectors) _collectingAndThen_ method. 

## Downstream-designed collectors

There are collectors that have functionality _similar_ to some Stream operations. Indeed these сollectors were designed not to duplicate Stream functionality, but to be passed as arguments (_downstream_ collectors) to other сollectors to perform the multilevel reduction. 

Mentioned collectors and Stream operations often are _similar_ but not _equivalent_. They can have different types of parameters, return values, and different semantics.

In this section _downstream_ collectors are used in a primitive way just to show their functionality. Proper use of _downstream_ collectors is described below in section ["Grouping-by" collectors to maps](#grouping-by-collectors-to-maps).

### Analogs of stream _intermediate_ operations

There are collectors from _filtering, mapping, flatMapping_ methods that have functionality similar to _filter, map, flatMap_ Stream _intermediate_ operations. They all are designed to perform _filter-map_ steps in _filter-map-reduce_ functional pipeline.

To collect input elements that satisfy a [condition](https://en.wikipedia.org/wiki/Filter_(higher-order_function)) it's possible to use a collector from _filtering(predicate, downstream)_ method (since Java 9). Here is used the [Predicate](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/function/Predicate.html) `i -> i % 2 != 0` that filters odd numbers and _toList_ as a _downstream_ collector.

```
   List<Integer> listOfOddNumbers = Stream.of(1, 2, 3)
           .collect(filtering(i -> i % 2 != 0, toList()));

   assertThat(listOfOddNumbers)
           .hasSize(2)
           .containsOnly(1, 3);
```

To collect input elements that are subjected to a [one-to-one transformation](https://en.wikipedia.org/wiki/Map_(higher-order_function)) it's possible to use a collector from _mapping(mapper, downstream)_ method. Here is used the [Function](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/function/Function.html) `i -> i * i` that transforms numbers to their squares and _toList_ as a _downstream_ collector.

```
   List<Integer> listOfSquares = Stream.of(1, 2, 3)
           .collect(mapping(i -> i * i, toList()));

   assertThat(listOfSquares)
           .hasSize(3)
           .containsOnly(1, 4, 9);
```

To collect input elements that are subjected to a one-to-many transformation it's possible to use a collector from _flatMapping(mapper, downstream)_ method (since Java 9). Here is used the [Function](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/function/Function.html) `List::stream` that converts _stream of lists of elements_ to _stream of elements_ and _toList_ as a _downstream_ collector.

```
   List<Integer> list = Stream.of(
           List.of(1, 2),
           List.of(3, 4))
           .collect(flatMapping(List::stream, toList()));

   assertThat(list)
           .hasSize(4)
           .containsOnly(1, 2, 3, 4);
```

More simpler collector from _mapping_ method should be used, when each input element is converted into exactly _one_ element. More advanced collector from _flatMapping_ method should be used, when each input element can be converted into a Stream of _zero, one or many_ elements.

### Analogs of stream _terminal_ operations

There are collectors from _averaging(Int|Long|Double), counting, maxBy, minBy, summing(Int|Long|Double), summarizing(Int|Long|Double)_ methods that have functionality similar to _average, count, max, min, sum, summaryStatistics_ Stream _terminal_ operations. They all are designed to perform specialized _reduce_ steps in _filter-map-reduce_ functional pipeline.

To find the average (arithmetic mean) of _int, long, double_ input elements it's possible to use collectors from _averaging(Int|Long|Double)_ methods. It's necessary to pass a _mapper_ function that converts _object_ input elements to the _primitive_ ones, as the argument to the method (here is used [ToIntFunction](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/function/class-use/ToIntFunction.html)).

```
double average = Stream.of(1, 2, 3)
       .collect(averagingInt(i -> i));

assertThat(average).isEqualTo(2);
```

To count the number of input elements, it's possible to use a collector from _counting_ method.

```
long count = Stream.of(1, 2, 3)
       .collect(counting());

assertEquals(3L, count);
```

To find the maximal input element it's possible to use a collector from _maxBy(comparator)_ method. It's necessary to pass a _Comparator_ as the argument to the method (here is used _[Comparator.naturalOrder](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/Comparator.html#naturalOrder())_).

```
Optional<Integer> max = Stream.of(1, 2, 3)
       .collect(maxBy(Comparator.naturalOrder()));

assertThat(max)
       .isNotEmpty()
       .hasValue(3);
```

To find the minimal input element it's possible to use a collector from _minBy(comparator)_ method. It's necessary to pass a _Comparator_ as the argument to the method (here is used _[Comparator.naturalOrder](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/Comparator.html#naturalOrder())_).

```
Optional<Integer> min = Stream.of(1, 2, 3)
       .collect(minBy(Comparator.naturalOrder()));

assertThat(min)
       .isNotEmpty()
       .hasValue(1);
```

To find the sum of _int, long, double_ input elements it's possible to use collectors from _summing(Int|Long|Double)_ methods. It's necessary to pass a _mapper_ function that converts _object_ input elements to the _primitive_ ones, as the argument to the method (here is used [ToIntFunction](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/function/class-use/ToIntFunction.html)).

```
int sum = Stream.of(1, 2, 3)
       .collect(summingInt(i -> i));

assertThat(sum).isEqualTo(6);
```

To find all the numerical statistics described above (_average, count, max, min, sum_) of _int, long, double_ input elements it's possible to use collectors from _summarizing(Int|Long|Double)_ methods. It's necessary to pass a _mapper_ function that converts _object_ input elements to the _primitive_ ones, as the argument to the method (here is used [ToIntFunction](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/function/class-use/ToIntFunction.html)).

```
IntSummaryStatistics iss = Stream.of(1, 2, 3)
       .collect(summarizingInt(i -> i));

assertThat(iss.getAverage()).isEqualTo(2);
assertThat(iss.getCount()).isEqualTo(3);
assertThat(iss.getMax()).isEqualTo(3);
assertThat(iss.getMin()).isEqualTo(1);
assertThat(iss.getSum()).isEqualTo(6);
```

### Analogs of stream _reduce_ operations

There are collectors from overloaded _reducing_ methods that have functionality similar to _reduce_ Stream operations. They all are designed to perform general _reduce_ steps in _filter-map-reduce_ functional pipeline.

There are 3 overloaded _reduce_ methods that can have the following parameters:

*   _operator_ - a [BinaryOperator](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/function/BinaryOperator.html) to reduce input elements
*   _identity_ - the initial value for the reduction; it's returned as result value when there are no input elements
*   _mapper_ - a [Function](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/function/Function.html) to apply to each input element

Example of a collector from _reducing(operator)_ method to calculate the _sum_ of input elements. Because there is no _identity_ parameter, the result type is [Optional<Integer>](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/Optional.html) to handle the case when there are no input elements.

```
Optional<Integer> sumOptional = Stream.of(1, 2, 3)
       .collect(reducing(Integer::sum));

assertTrue(sumOptional.isPresent());
assertThat(sumOptional.get()).isEqualTo(6);
```

Example of a collector from _reducing(identity, operator)_ method to calculate the _sum_ of input elements. Because there is an _identity_ parameter, the result type is Integer.

```
Integer sum = Stream.of(1, 2, 3)
       .collect(reducing(0, Integer::sum));

assertThat(sum).isEqualTo(6);
```

Example of a collector from _reducing(identity, mapper, operator)_ method to calculate the _sum of squares_ of input elements.

```
Integer sumOfSquares = Stream.of(1, 2, 3)
       .collect(reducing(0, element -> element * element, Integer::sum));

assertThat(sumOfSquares).isEqualTo(14);
```

## Collectors to maps

Collectors to reduce input elements to maps are much more complicated than collectors to collections. There are two big categories of such collectors: 

*   collectors from "to-map" methods (_toMap, toUnmodifiableMap, toConcurrentMap)_
*   collectors from "grouping-by" methods (_groupingBy, partitioningBy, groupingByConcurrent)_

Each input element is converted into key and value, and multiple input elements can be associated with the same key. The difference between the two categories of collectors is in the handling of keys collisions.

There are methods to create collectors to maps that can have the following parameter:

*   _mapFactory_ - a [Supplier](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/function/Supplier.html) for new empty Map to collect results

If a collector is created from a method without _mapFactory_ parameter, then there are no guarantees on the type, mutability, serializability, or thread-safety of the returned Map.

### "To-map" collectors to maps

Collectors from "to-map" methods reduce input elements to maps whose keys and values are the results of applying _key-mapping_ and _value-mapping_ functions. If many input elements are associated with the same key, it's possible to use _merge_ function to return a single value by binary reduction.

There are overloaded _toMap, toUnmodifiableMap, toConcurrentMap_ methods that can have the following parameters:

*   _keyMapper_ - a [Function](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/function/Function.html) to convert input elements into map keys
*   _valueMapper_ - a [Function](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/function/Function.html) to convert input elements into map values
*   _mergeFunction_ - a [BinaryOperator](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/function/BinaryOperator.html) to resolve collisions between values when many input elements are associated with the same key
*   _mapFactory_ - a [Supplier](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/function/Supplier.html) for new empty Map to collect results

Examples in this section show reducing streams of words from _phonetic alphabets_ to maps when _keys_ are the first letters of the words (here it's used `s -> s.charAt(0))` and _values_ are the words themselves (here is used [Function.identity()](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/function/Function.html#identity())).

#### Regular collectors to maps

Example of a collector from _toMap(keyMapper, valueMapper)_ method, where no keys collisions are guaranteed.

```
   Map<Character, String> map = Stream.of("Alpha", "Bravo", "Charlie")
           .collect(toMap(s -> s.charAt(0), Function.identity()));

   assertThat(map)
           .hasSize(3)
           .containsEntry('A', "Alpha")
           .containsEntry('B', "Bravo")
           .containsEntry('C', "Charlie");
```

Example of a collector from _toMap(keyMapper, valueMapper)_ method. If two input elements are associated with the same key, an IllegalStateException is thrown.

```
   assertThrows(IllegalStateException.class, () -> {
       Stream.of(
               "Amsterdam", "Baltimore", "Casablanca",
               "Alpha", "Bravo", "Charlie")
               .collect(toMap(s -> s.charAt(0), Function.identity()));
   });
```

Example of a collector from _toMap(keyMapper, valueMapper, mergeFunction)_ method. If two input elements are associated with the same key, then _merge function_ `(v1, v2) -> v2` selects the new value.

```
   Map<Character, String> map = Stream.of(
           "Amsterdam", "Baltimore", "Casablanca",
           "Alpha", "Bravo", "Charlie")
           .collect(toMap(s -> s.charAt(0), Function.identity(), (v1, v2) -> v2));

   assertThat(map)
           .hasSize(3)
           .containsEntry('A', "Alpha")
           .containsEntry('B', "Bravo")
           .containsEntry('C', "Charlie");
```

Example of a collector from _toMap(keyMapper, valueMapper, mergeFunction, mapFactory)_ method. Here is used constructor reference to TreeMap as a _map factory_ to a specific Map. 

```
   SortedMap<Character, String> map = Stream.of(
           "Amsterdam", "Baltimore", "Casablanca",
           "Alpha", "Bravo", "Charlie")
           .collect(toMap(s -> s.charAt(0), Function.identity(), (v1, v2) -> v2, TreeMap::new));

   assertThat(map)
           .hasSize(3)
           .containsEntry('A', "Alpha")
           .containsEntry('B', "Bravo")
           .containsEntry('C', "Charlie")
           .isExactlyInstanceOf(TreeMap.class);
```

#### Collectors to unmodifiable maps

Maps that do not support modification operations are referred to as _unmodifiable_. Such maps cannot be modified by calling any mutator methods, they are guaranteed to throw UnsupportedOperationException. But only if keys and values of such maps are immutable, maps can be considered as _immutable_ itself.

To reduce stream to _[unmodifiable Map](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/Map.html#unmodifiable)_ it's possible to use collectors from _toUnmodifiableMap_ methods (since Java 10). Keys and values of such maps cannot be added, removed, or updated.

Example of a collector from _toUnmodifiableMap(keyMapper, valueMapper)_ method, where no keys collisions are guaranteed. 

```
   Map<Character, String> unmodifiableMap = Stream.of("Alpha", "Bravo", "Charlie")
           .collect(toUnmodifiableMap(s -> s.charAt(0), Function.identity()));

   assertThat(unmodifiableMap)
           .hasSize(3)
           .containsEntry('A', "Alpha")
           .containsEntry('B', "Bravo")
           .containsEntry('C', "Charlie");

   assertThatThrownBy(unmodifiableMap::clear)
           .isInstanceOf(UnsupportedOperationException.class);
```

Example of a collector from _toUnmodifiableMap(keyMapper, valueMapper)_ method. If two input elements are associated with the same key, an IllegalStateException is thrown.

```
   assertThrows(IllegalStateException.class, () -> {
       Stream.of(
               "Amsterdam", "Baltimore", "Casablanca",
               "Alpha", "Bravo", "Charlie")
               .collect(toUnmodifiableMap(s -> s.charAt(0), Function.identity()));
   });
```

Example of a collector from _toUnmodifiableMap(keyMapper, valueMapper, mergeFunction)_ method. If two input elements are associated with the same key, then _merge function_ `(v1, v2) -> v2` selects the new value.

```
   Map<Character, String> unmodifiableMap = Stream.of(
           "Amsterdam", "Baltimore", "Casablanca",
           "Alpha", "Bravo", "Charlie")
           .collect(toUnmodifiableMap(s -> s.charAt(0), Function.identity(), (v1, v2) -> v2));

   assertThat(unmodifiableMap)
           .hasSize(3)
           .containsEntry('A', "Alpha")
           .containsEntry('B', "Bravo")
           .containsEntry('C', "Charlie");

   assertThatThrownBy(unmodifiableMap::clear)
           .isInstanceOf(UnsupportedOperationException.class);
```

#### Concurrent collectors to maps

The difference between collectors from _toMap_ and _toConcurrentMap_ methods is in their behavior during parallel reduction. 

Collectors from _toMap_ methods create multiple result containers (e.g. HashMap) for each partition and then merge them. Merging key-value entries from one Map into another can be an expensive operation. 

Function calls inside non-parallel collectors:

*   _supplier_ - a function to create new _result container_ (**is called multiple times**)
*   _accumulator_ - a function to add a new element into a _result container_ (is called multiple times)
*   _combiner_ - a function to combine two _result containers_ into one (**is called multiple times**)

Collectors from _toConcurrentMap_ methods create single result container (e.g. ConcurrentMap) for all partitions. There are no merging key-value entries from one Map to another.

Function calls inside parallel collectors:

*   _supplier_ - a function to create new _result container_ (**is called only once**)
*   _accumulator_ - a function to add a new element into a _result container_ (is called multiple times)
*   _combiner_ - a function to combine two _result containers_ into one (**is never called**)

Parallel reduction is performed if all of the following are true:

*   the Stream is parallel
*   the Collector has the characteristic [CONCURRENT](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/stream/Collector.Characteristics.html#CONCURRENT)
*   either the Stream is unordered or the Collector has the characteristic [UNORDERED](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/stream/Collector.Characteristics.html#UNORDERED)

Parallel reduction _may_ have performance better than sequential reduction. However, the order of inserting key-value entries to maps during parallel reduction is not guaranteed. 

Examples in this section are used parallel streams created from method [BaseStream.parallel()](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/stream/BaseStream.html#parallel()).

Example of a collector from _toConcurrentMap(keyMapper, valueMapper)_ method, where no keys collisions are guaranteed.

```
   ConcurrentMap<Character, String> map = Stream.of("Alpha", "Bravo", "Charlie")
           .parallel()
           .collect(toConcurrentMap(s -> s.charAt(0), Function.identity()));

   assertThat(map)
           .hasSize(3)
           .containsEntry('A', "Alpha")
           .containsEntry('B', "Bravo")
           .containsEntry('C', "Charlie");
```

Example of a collector from _toConcurrentMap(keyMapper, valueMapper)_ method. If two input elements are converted into the same key, an IllegalStateException is thrown.

```
   assertThrows(IllegalStateException.class, () -> {
       Stream.of(
               "Amsterdam", "Baltimore", "Casablanca",
               "Alpha", "Bravo", "Charlie")
               .parallel()
               .collect(toConcurrentMap(s -> s.charAt(0), Function.identity()));
   });
```

Example of a collector from _toConcurrentMap(keyMapper, valueMapper, mergeFunction)_ method. If two input elements are converted into the same key, then _merge function_ `(v1, v2) -> v2` that selects the new value.

Because the order of inserting key-value entries in maps during parallel reduction is not guaranteed, here are verified only keys, not key-values.

```
   ConcurrentMap<Character, String> map = Stream.of(
           "Amsterdam", "Baltimore", "Casablanca",
           "Alpha", "Bravo", "Charlie")
           .parallel()
           .collect(toConcurrentMap(s -> s.charAt(0), Function.identity(), (v1, v2) -> v2));

   assertThat(map)
           .hasSize(3)
           .containsKey('A')
           .containsKey('B')
           .containsKey('C');
```

Example of a collector from _toConcurrentMap(keyMapper, valueMapper, mergeFunction, mapFactory)_ method. Here is used constructor reference to ConcurrentHashMap as a _map factory_ to a specific ConcurrentMap.

```
   ConcurrentMap<Character, String> map = Stream.of(
           "Amsterdam", "Baltimore", "Casablanca",
           "Alpha", "Bravo", "Charlie")
           .parallel()
           .collect(toConcurrentMap(s -> s.charAt(0), Function.identity(), (v1, v2) -> v2, ConcurrentHashMap::new));

   assertThat(map)
           .hasSize(3)
           .containsKey('A')
           .containsKey('B')
           .containsKey('C')
           .isExactlyInstanceOf(ConcurrentHashMap.class);
```

### "Grouping-by" collectors to maps

Collectors from "grouping-by" methods reduce input elements to maps whose key are groups by applying a _classification_ function. All values, associated with the same key group, are reduced by a _downstream_ collector into one value.

There are overloaded _groupingBy, partitioningBy, groupingByConcurrent_ methods that can have the following parameters:

*   _classifier_ - a [Function](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/function/Function.html) to collect element into key groups
*   _mapFactory_ - a [Supplier](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/function/Supplier.html) for new empty Map to collect results
*   _downstream_ - a [Collector](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/stream/Collector.html) to reduce values, associated with the same key group

Collectors from "grouping-by" methods are the place where _downstream_ collectors are designed to be used. As _downstream_ collectors can be used not only predefined collectors from [Collectors](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/stream/Collectors.html) class but also composite collectors that are combined from other collectors.

Examples in this section show reducing streams of the top 100 US cities by population. These objects have 3 levels of grouping: cities, areas (50 states and 1 federal district) and regions.

#### Grouping collectors to maps

Example a collector from _groupingBy(classifier)_ method. Here is implicitly used a _downstream_ collector to List.

```
   Map<Area, List<City>> citiesPerArea = USA.CITIES.stream()
           .collect(groupingBy(City::getArea));
```

Example collector from _groupingBy(classifier, downstream)_ method. Here is explicitly used a _downstream_ collector from _toSet_ method.

```
   Map<Area, Set<City>> citiesPerArea = USA.CITIES.stream()
           .collect(groupingBy(City::getArea, toSet()));
```

Example collector from _groupingBy(classifier, mapFactory, downstream)_ method. Here is used a _supplier_ function to the constructor call of EnumMap as a _map factory_ to a specific Map.

```
   EnumMap<Area, List<City>> citiesPerArea = USA.CITIES.stream()
           .collect(groupingBy(City::getArea, () -> new EnumMap<>(Area.class), toList()));
```

#### Partitioning collectors to maps

Collectors from _partitioningBy_ methods are a special case of collectors from _groupingBy_ methods. The first use more specific [Predicate](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/function/Predicate.html) as a _classifier_ function, the second use more general [Function](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/function/Function.html).

There are overloaded _partitioningBy_ methods that can have the following parameters:

*   _predicate_ - a [Predicate](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/function/Predicate.html) to collect element into two key groups
*   _downstream_ - a [Collector](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/stream/Collector.html) to reduce values, associated with the same key group

Collectors from _partitioningBy_ methods always produce values for both _true_ and _false_ keys, even if a value group is empty.

Examples in this section show reducing streams into two complementary collections by equality to zero the remainder from division input elements by a certain number.

Example of a collector from _partitioningBy(predicate)_ method. Here is implicitly used a _downstream_ collector to List.

```
Map<Boolean, List<Integer>> reminderFromDivisionBy2IsZero = Stream.of(1, 2, 3)
       .collect(partitioningBy(i -> i % 2 == 0));

assertThat(reminderFromDivisionBy2IsZero)
       .hasSize(2)
       .containsEntry(false, List.of(1, 3))
       .containsEntry(true, List.of(2));
```

Example of a collector from _partitioningBy(predicate, downstream)_ method. Here is explicitly used a _downstream_ collector from _toSet_ method.

```
Map<Boolean, Set<Integer>> reminderFromDivisionBy4IsZero = Stream.of(1, 2, 3)
       .collect(partitioningBy(i -> i % 4 == 0, toSet()));

assertThat(reminderFromDivisionBy4IsZero)
       .hasSize(2)
       .containsEntry(false, Set.of(1, 2, 3))
       .containsEntry(true, Set.of());
```

#### Concurrent grouping collectors to maps

There are collectors from _groupingByConcurrent_ methods that are designed for parallel reduction similar to collectors from  _toConcurrentMap_ methods.

Examples in this section are used parallel streams created from method [Collection.parallelStream()](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/Collection.html#parallelStream()).

Example of a collector from _groupingByConcurrent(classifier)_ method. Here is implicitly used a _downstream_ collector to List.

```
   ConcurrentMap<Area, List<City>> citiesPerArea = USA.CITIES
           .parallelStream()
           .collect(groupingByConcurrent(City::getArea));
```

Example of a collector from _groupingByConcurrent(classifier, downstream)_ method. Here is explicitly used a collector from _toSet_ _method_.

```
   ConcurrentMap<Area, Set<City>> citiesPerArea = USA.CITIES
           .parallelStream()
           .collect(groupingByConcurrent(City::getArea, toSet()));
```

Example of a collector from _groupingByConcurrent(classifier, mapFactory, downstream)_ method. Here is used constructor reference to ConcurrentHashMap as a _map factory_ to a specific ConcurrentMap.

```
   ConcurrentMap<Area, List<City>> citiesPerArea = USA.CITIES
           .parallelStream()
           .collect(groupingByConcurrent(City::getArea, ConcurrentHashMap::new, toList()));
```

## Other collectors 

Some collectors can't be assigned to any category described above.

Collector from _collectingAndThen(downstream, finisher)_ method was designed to perform additional finishing processing of the _summary result_. It was often used to produce _unmodifiable_ collections before introducing in Java 10 collectors from [described above](#collectors-to-unmodifiable-collections) _toUnmodifiableList, toUnmodifiableSet_ methods.

```
List<Integer> unmodifiableList = Stream.of(1, 2, 3)
       .collect(collectingAndThen(toList(), Collections::unmodifiableList));

assertThat(unmodifiableList)
       .hasSize(3)
       .containsOnly(1, 2, 3);

assertThatThrownBy(unmodifiableList::clear)
       .isInstanceOf(UnsupportedOperationException.class);
```

Collectors from overloaded _joining_ methods were designed to join input elements that are [CharSequence](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/lang/CharSequence.html) implementations (String, StringBuffer, StringBuilder etc) of into String summary result.

Example of a collector from _joining()_ method, that joins string without delimiter between them.

```
   String result = Stream.of(1, 2, 3)
           .map(String::valueOf)
           .collect(joining());

   assertThat(result).isEqualTo("123");
```

Example of a collector from _joining(delimiter)_ method, that joins string with a delimiter between them.

```
   String result = Stream.of(1, 2, 3)
           .map(String::valueOf)
           .collect(joining(","));

   assertThat(result).isEqualTo("1,2,3");
```

Example of a collector from _joining(delimiter, prefix, suffix)_ method, that joins string with a delimiter between them and a prefix and a suffix around the summary result.

```
   String result = Stream.of(1, 2, 3)
           .map(String::valueOf)
           .collect(joining(",", "[", "]"));

   assertThat(result).isEqualTo("[1,2,3]");
```

The _teeing_ collector was designed to compose of two downstream collectors at once (since Java 12). Example of a collector from _teeing_ method to find minimal and maximal values of input elements.

```
Map.Entry<Optional<Integer>, Optional<Integer>> limits = Stream.of(1, 2, 3)
       .collect(
               teeing(
                       minBy(Integer::compareTo),
                       maxBy(Integer::compareTo),
                       AbstractMap.SimpleImmutableEntry::new
               )
       );

assertNotNull(limits);

Optional<Integer> minOptional = limits.getKey();
assertThat(minOptional)
       .isNotEmpty()
       .hasValue(1);

Optional<Integer> maxOptional = limits.getValue();
assertThat(maxOptional)
       .isNotEmpty()
       .hasValue(3);
```

## Conclusion

_Extended_ code examples are available in the [GitHub repository](https://github.com/aliakh/demo-java-stream-collectors).
