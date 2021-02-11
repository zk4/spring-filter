package com.torshid.compiler.token.matcher;

import java.util.regex.Pattern;

import com.torshid.compiler.Extensions;
import com.torshid.compiler.token.IToken;
import com.torshid.compiler.token.matcher.LiteralMatcher.ILiteral;

import lombok.experimental.ExtensionMethod;

@ExtensionMethod(Extensions.class)
public abstract class LiteralMatcher<T extends Enum<T> & IToken & ILiteral> extends Matcher<T> {

  @SuppressWarnings("unchecked")
  @Override
  public T match(StringBuilder input) {

    for (Enum<T> type : getEnumClass().getEnumConstants()) {

      ILiteral literal = (ILiteral) type;

      if (literal.getRegex() == null) {

        // match using literal

        if (input.length() >= literal.getLiteral().length()
            && input.substring(0, literal.getLiteral().length()).toLowerCase().equalsIgnoreCase(literal.getLiteral())) {

          input.take(literal.getLiteral().length());

          return (T) type;

        }

      } else {

        // match using regex

        String match = input.getMatch(Pattern.compile(literal.getRegex()));

        if (match != null) {
          return (T) type;
        }

      }

    }

    return null;

  }

  public abstract Class<T> getEnumClass();

  public interface ILiteral {

    String getLiteral();

    String getRegex();

  }

}
