package com.example.coursework;
import opennlp.tools.tokenize.SimpleTokenizer;
import weka.core.*;


import java.io.IOException;
import java.util.List;

public class CategorizationNLP {

    public String[] tokenize(String content_db) {
        SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
        return tokenizer.tokenize(content_db);
    }

    public Instances convertToTfIdf(List<String> contents) throws IOException {
        FastVector attributes = new FastVector();
        attributes.addElement(new Attribute("text",(FastVector) null));
        Instances data = new Instances("Articles",attributes,contents.size());
        for (String content : contents) {
            Instance instance = new DenseInstance(1);
            instance.setValue((Attribute) attributes.elementAt(0),content);
        }
    }
}
