# jfilter
Filter (query), map (select) and reduce (aggregate) objects in a Java collection

#JFilter Documentation

Please note that word query and filter is used interchangeably in this document.

#Installation

Download latest JFilter version from download page.
Download Jackson core and mapper jars of version 1.9.0+ from http://wiki.fasterxml.com/JacksonDownload.
Add JFilter and Jackson jars in you project class path.
#Javadocs

JFilter

#Overview

JFilter library has only one class [gk.jfilter.JFilter] (http://code.google.com/p/jfilter/source/browse/trunk/jfilter/src/main/java/gk/jfilter/JFilter.java) to filter/query, map/select and reduce/aggregate objects in a collection. You should create only one instance of JFilter class for each collection object to be filtered. JFilter constructor has Iterable argument, so apart from collection you can filter any class object which implements Iterable interface.

##Following are different ways to execute filter.
```
Collection pets = new ArrayList(); //populate pets collection

JFilter jfilter = new JFilter(pets, Pet.class); Collection cats = filter.filter("{'type':'?1'}", "CAT").out(new ArrayList());

Collection pets = new ArrayList(); //populate pets collection

JFilter jfilter = new JFilter(pets, Pet.class);

Map parameters = new HashMap();

parameters.put("type", "CAT"); Collection cats = jfilter.filter("{'type':'?type'}", parameters).out(new ArrayList());

parameters.put("type", "DOG"); Collection dogs = jfilter.filter("{'type':'?type'}", parameters).out(new ArrayList());
```

1. [Sample programs] (https://github.com/khankamranali/jfilter/tree/master/src/main/java/gk/jfilter/JFilter.java)

2. Simple sample

3. Product sample

4. Sales order sample

5. Simple MapReduce sample

6. Product MapReduce sample

##How to write json filter

JFilter works on methods not on class properties, so if you want to filter on properties then there should be a corresponding getter methods.
For getter properties you can either give the property name or getter method name in the json filter. For example if a class has getName() method then you can write your json query like follows. Both produce same result.
{'name':'?1'}
{'getName':'?1}
java.util.Collection and java.util.Map type or any generic type properties should have parameterized type e.g. Map<String,List<String>>. JFilter ignores properties of generic type without parameterized type.

Filter can contains non property methods which are non void and has zero arguments, e.g {'toString':'?1'}.

Filter string should be in proper json format. You can use single quote ' instead of double quote ".

$eq (equals) is a default operator in the json filters, if you do not specify any comparison operator. For example "{'name':'?1'}" filter selects objects where name=?1. You can specify other operators listed below like "{'name':{'$ne':'?1}}", it selects objects where name!=?1.

JFilter supports properties inside properties till infinite level for example Product class can have Sku property (collection type) which again can have Price property. So if you want to filter Product objects which have a Sku price less than a given dollar. You can write json filter in following two ways, with dot (.) notation and without dot notation. For detail please see Product sample.

{'skus.price':{'$le':'?1'}}
{'skus':{'price':{'$le':'?1'}}
JFilter provides two special properties for Map type properties: "key" and "value". For example if you have a method Map getTaxes() then you can following json filters.

{'taxes.key':'?1'} - select objects where tax key is equals to given argument.
{'taxes.value':{'$le':'?1'}} - select objects where tax value is less than equals to given argument.
Supported operators

| Operator | Description | Supported types | Example | Comment| |:-------------|:----------------|:--------------------|:------------|:-----------| | $gt | Greater than | All | "{'length':{'$gt':'?length'}}"| | | $ge | Greater than equals to | All | "{'length':{'$ge':'?length'}}"| | | $lt | Less than | All | "{'length':{'$lt':'?length'}}" | | | $le | Less than equals to | All | "{'length':{'$le':'?length'}}"| | | $eq | Equals to | All | "{'name':{'$eq':'?name'}}"| | | $sw | Same as String startsWith | String | "{'name':{'$sw':'?name'}}" | This special operator is provided because JFilter supports methods without any arguments | | $ew | Same as String endsWith | String | "{'name':{'$ew':'?name'}}" | This special operator is provided because JFilter supports methods without any arguments | | $cts | Same as String contails| String | "{'name':{'$cts':'?name'}}" | This special operator is provided because JFilter supports methods without any arguments | | $in | In | Collection, Array and Map| "{'type':{'$in':'?type'}}"| | | $not | NOT | | "{'$not':[{'type':{'$in':'?type'}}]}"| | | $and | AND | | "{'$and':[{'type':{'$in':'?type'}}, {'color':{'$in':'?type'}}]}"| join operator | | $or | OR | | "{'$or':[{'type':{'$in':'?type'}}, {'color':{'$in':'?type'}}]}"| join operator |

Supported data types

All primitives, BigDecimal, BigInteger, Boolean, Byte, Calendar, Character, java.util.Date, Float, Integer, Long, Short, String, java.sql.Time, java.sql.Timestamp, URI, UUID

How to use different data types

See the code of Data type test program

Parameterized filters

Parameterized filters are when you do not provide attributes value in json filter string for example {'id':'?id'}. Question mark is used to indicate that value will be given as argument of JFilter.execute method. There are two type of parameterized filters:

Type 1: In this type Filter parameters are written as {'attribute':'?string'} where "string" is any string. Parameter values are given as Map argument where Map key is "string" and value is object of type "attribute" specified in the filter. In case of operator $in and $nin values are given as List.
Example: JFilter<SalesOrder> filter = new JFilter<SalesOrder>(orders, SalesOrder.class); Map<String, Integer> parameters = new HashMap<String, Integer>(1); parameters.put("id", 10); Collection<SalesOrder> fc = filter.filter("{ 'id':{'$le':'?id'}}", parameters).out(new ArrayList<SalesOrder>());

Type 2: In this type parameters are given as "?1", "?2" etc in the filter, starting from "?1" to "?n" where n is integer. Parameter values are are picked from corresponding argument position in the variable arguments of JFilter.execute method.
Example: JFilter<SalesOrder> filter = new JFilter<SalesOrder>(orders, SalesOrder.class); Collection<SalesOrder> fc = filter.filter("{ 'id':{'$le':'?1'}}", 10).out(new ArrayList<SalesOrder>());

JFilter does not support non parameterized queries.

Non property method in a filter

You can use non property methods in a filter, the method must be non void and should be of zero argument. Example: {'toString':'?1'}

Inherited properties and methods

JFilter supports inherited properties and method of any level. That means you can give inherited properties or methods in a filter. See in the following example getClass() method of Object class is used in a filter.

Example:

{'getClass.getName':'eg.MyClass'} - filter collection where class name is "eg.MyClass".
