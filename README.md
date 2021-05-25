TextFileStemmer
=================================================

![Points](../../blob/badges/points.svg)

For this homework assignment, you will create a class that is able to clean and parse text into stemmed words. Use `UTF8` and try-with-resources when writing your files. Do not use the [`java.io.File`](https://www.cs.usfca.edu/~cs212/javadoc/api/java.base/java/io/File.html) class.

The `TextParser` class is already provided for you.

## Hints ##

Below are some hints that may help with this homework assignment:

  - You need to have use the third-party [Apache OpenNLP](http://opennlp.apache.org/) library. The library should be automatically setup in Eclipse by maven. For how to use this library, see the [Apache OpenNLP Tools Javadoc](https://opennlp.apache.org/docs/1.9.3/apidocs/opennlp-tools/index.html) for details.

  - When working with files, you should use try-with-resources, the `UTF-8` character encoding, and buffered readers and writers.

  - Look for opportunities to reduce duplicate code. For example, you could create a new helper method that is reused in multiple other methods.

These hints are *optional*. There may be multiple approaches to solving this homework.
