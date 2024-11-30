package com.example.coursework.controllers;

import com.example.coursework.DbConnector;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.*;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdministraorViewController {
    public static void main(String[] args) throws Exception {
        // Step 1: Load data from MySQL
        List<String> contents = new ArrayList<>();
        List<String> categories = new ArrayList<>();

        contents = DbConnector.getNewsData("news","content");
        categories = DbConnector.getNewsData("news","category");


        // Step 2: Create Instances for Weka
        FastVector attributes = new FastVector();
        attributes.addElement(new Attribute("content", (FastVector) null)); // Text attribute
        FastVector classValues = new FastVector();
        for (String category : categories) {
            if (!classValues.contains(category)) {
                classValues.addElement(category);
            }
        }
        attributes.addElement(new Attribute("category", classValues)); // Rename "class" to "category"


        Instances dataset = new Instances("NewsDataset", attributes, contents.size());
        dataset.setClassIndex(1); // Class attribute index
        for (int i = 0; i < contents.size(); i++) {
            Instance instance = new DenseInstance(2);
            instance.setValue((Attribute) attributes.elementAt(0), contents.get(i));
            instance.setValue((Attribute) attributes.elementAt(1), categories.get(i));
            dataset.add(instance);
        }

        // Step 3: Preprocess text with StringToWordVector
        StringToWordVector filter = new StringToWordVector();
        filter.setInputFormat(dataset);
        Instances filteredData = Filter.useFilter(dataset, filter);

        // Step 4: Train a Classifier
        Classifier classifier = new NaiveBayes();
        classifier.buildClassifier(filteredData);

        // Step 5: Predict new articles
        String newArticle = "BAsketball team is going to win th match!";
        Instance newInstance = new DenseInstance(2);
        newInstance.setDataset(dataset);
        newInstance.setValue((Attribute) attributes.elementAt(0), newArticle);
        Instances testSet = new Instances(dataset, 0);
        testSet.add(newInstance);

        Instances filteredTestSet = Filter.useFilter(testSet, filter);
        double predictedIndex = classifier.classifyInstance(filteredTestSet.instance(0));
        String predictedCategory = dataset.classAttribute().value((int) predictedIndex);

        System.out.println("Predicted Category: " + predictedCategory);
    }
}
