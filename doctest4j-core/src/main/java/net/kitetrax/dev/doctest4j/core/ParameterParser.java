package net.kitetrax.dev.doctest4j.core;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;

public class ParameterParser {

  private Document document;


  public ParameterParser(String resource) throws IOException {
    InputStream is = ParameterParser.class.getClassLoader().getResourceAsStream(resource);
    if (null == is) {
      throw new IOException("Resource not found: " + resource);
    }
    document = Jsoup.parseBodyFragment(IOUtils.toString(is, Charsets.UTF_8));
  }

  public Object getValue(Parameter parameter) {
    switch (parameter.type()) {
      case SINGLE_VALUE:
        return getSingleValue(parameter);
      case TABLE:
        break;
    }
    return null;
  }

  private Object getSingleValue(Parameter parameter) {
    return document.select(parameter.selector()).text();
  }

}
