/** In respect to part 1 - Groovy Language Specification */

// standalone single line comment
println "hello"

/** And this is an unbounded groovydoc */

/**
 * A Class description
 */
class Person {
    /** the name of the person */
    String name

    /**
     * Creates a greeting method for a certain person.
     *
     * @param otherPerson the person to greet
     * @return a greeting message
     */
    String greet(String otherPerson) {
        // As it can be seen here, the last statement is the return
        // TODO check this on the grammar (https://github.com/apache/groovy/blob/master/src/antlr/GroovyParser.g4)
        "Hello ${otherPerson}"
    }
}

Person p = new Person()
println(p.greet("Jane Doe"))

// There is runtime groovydoc - this means that it can be accessed via reflection!

/**@
 * Runtime groovydoc
 */
class Foo {
    /**@
     * Some method groovydoc for bar
     */
    void bar() {}
}

// Those below are currently failing! Even with groovy 3.0.9 installed
// assert Foo.class.groovydoc.content.contains("Runtime groovydoc")
// assert Foo.class.getMethod('bar', new Class[0]).groovydoc.content.contains('Some method groovydoc for bar')

// Quoted identifiers
def map = [:]

map."an identifier with a space and double quotes" = "ALLOWED"
map.'with-dash-signs-and-single-quotes' = 'ALLOWED'

assert map."an identifier with a space and double quotes" == "ALLOWED"
assert map.'with-dash-signs-and-single-quotes' == "ALLOWED"

// Numbers

// Pretty standard stuff. Some types I did not know beforehand, but I guess they are nothing Groovy-exclusive, but Java stuff.

// Already knew about BigInt:
BigInteger aBigNumber = 12398123871234871239483908
assert aBigNumber > 0

// But not about BigDecimal (albeit it was pretty predictable)
BigDecimal aBigDecimalNumber = 0.1234087348738974213987481374
assert aBigDecimalNumber < 1
assert aBigDecimalNumber > 0

// And better yet, Groovy uses java.lang.BigDecimal as its default decimal number type! You CAN use float/double, but it requires specific instantiation
def xdec = 3.14
assert xdec instanceof BigDecimal
assert xdec as Float instanceof Float

// One can also use suffixes on numbers to denote their types
assert 42I == new Integer('42')
assert 42i == new Integer('42')
assert 123L == new Long("123")
assert 3124324134087 == new Long('3124324134087') // Long because too large for an integer
assert 456G == new BigInteger('456')
assert 456g == new BigInteger('456')
assert 123.45 == new BigDecimal('123.45') // BigDecimal as default
assert 1.200065D == new Double('1.200065D')
assert 1.234F == new Float('1.234')
assert 1.23E23D == new Double('1.23E23')
assert 0b1111L.class == Long
assert 0xFFi.class == Integer
assert 034G.class == BigInteger

// Groovy lists are plain java.util.List (default java.util.ArrayList), and are defined just like in Python
def arrayList = [1, 2, 3]
assert arrayList instanceof java.util.ArrayList

// Just tried using it without def, it worked. Would that be just like JS var/let?
def linkedList = [2, 3, 4] as LinkedList
assert linkedList instanceof java.util.LinkedList

LinkedList otherLinked = [3, 4, 5, 'a', 4.3]
assert otherLinked instanceof java.util.LinkedList

// Just like Python again you can access ranges and use negative indexes for addressing end of the list
def letters = ['a', 'b', 'c', 'd']

assert letters[0] == 'a'
assert letters[-1] == 'd'

letters[2] = 'C'
assert letters[2] == 'C'

assert letters[1..3] == ['b', 'C', 'd']

// But one thing different: operator << for appending!
letters << 'e'
assert letters[4] == 'e'
assert letters[-1] == 'e'

// Groovy also allow for arrays. Just like lists, but need explicit type definition
String[] arrStr = ['Ananas', 'Banana', 'Kiwi']
assert arrStr instanceof String[]
assert !(arrStr instanceof List)

def numArr = [1, 2, 3] as int[]
assert numArr instanceof int[]
assert numArr.size() == 3

// Below is forbidden for arrays!
// numArr << 4

