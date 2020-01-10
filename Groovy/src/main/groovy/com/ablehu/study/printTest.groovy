package com.ablehu.study

def readme = '../README.md'

new File(readme).eachLine { line -> println line }