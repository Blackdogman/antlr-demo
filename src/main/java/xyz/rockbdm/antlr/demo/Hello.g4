// file Hello.g4
// Define a grammar called Hello
grammar Hello; // 1. grammer name
@header { package xyz.rockbdm.antlr.parser; } // 2. java package
r  : ID2'hello'ID ;         // 3. match keyword hello followed by an identifier
ID : [a-z]+ ;             // match lower-case identifiers
ID2: [0-9]+ ;
WS : [ \t\r\n]+ -> skip ; // 4. skip spaces, tabs, newlines