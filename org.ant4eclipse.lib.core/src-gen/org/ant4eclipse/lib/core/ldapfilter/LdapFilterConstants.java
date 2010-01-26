/* Generated By:JavaCC: Do not edit this line. LdapFilterConstants.java */
package org.ant4eclipse.lib.core.ldapfilter;


/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface LdapFilterConstants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int EQUAL = 4;
  /** RegularExpression Id. */
  int APPROX = 5;
  /** RegularExpression Id. */
  int GREATER = 6;
  /** RegularExpression Id. */
  int LESS = 7;
  /** RegularExpression Id. */
  int AND = 8;
  /** RegularExpression Id. */
  int AND_ORG = 9;
  /** RegularExpression Id. */
  int OR = 10;
  /** RegularExpression Id. */
  int OR_ORG = 11;
  /** RegularExpression Id. */
  int NOT = 12;
  /** RegularExpression Id. */
  int NOT_ORG = 13;
  /** RegularExpression Id. */
  int BRACKET_OPEN = 14;
  /** RegularExpression Id. */
  int BRACKET_CLOSE = 15;
  /** RegularExpression Id. */
  int STRING = 16;
  /** RegularExpression Id. */
  int WILDCARD_STRING = 17;

  /** Lexical state. */
  int DEFAULT = 0;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "\"\\r\"",
    "\"\\n\"",
    "\"\\t\"",
    "\"=\"",
    "\"~=\"",
    "\">=\"",
    "\"<=\"",
    "\"AND\"",
    "\"&\"",
    "\"OR\"",
    "\"|\"",
    "\"NOT\"",
    "\"!\"",
    "\"(\"",
    "\")\"",
    "<STRING>",
    "<WILDCARD_STRING>",
  };

}