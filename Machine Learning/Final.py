# -*- coding: utf-8 -*-
"""
Created on Thu Apr 21 20:30:53 2022

@author: Kevin Cabral
"""

import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
from sklearn.model_selection import train_test_split
from wordcloud import WordCloud,STOPWORDS, ImageColorGenerator
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.decomposition import PCA
from sklearn.preprocessing import StandardScaler
from sklearn.model_selection import train_test_split
from sklearn.model_selection import cross_val_predict
from sklearn.model_selection import cross_val_score
from sklearn.metrics import precision_score
from sklearn.metrics import accuracy_score
from sklearn.metrics import recall_score
from sklearn.metrics import plot_confusion_matrix

data = pd.read_csv('cyberbullying_tweets.csv')

#The data set is balanced
balanced = data['cyberbullying_type'].value_counts()

data.info()

#clean the data of duplicates
data = data.drop_duplicates()

types = np.unique(data['cyberbullying_type'])

data = data[data["cyberbullying_type"]!="other_cyberbullying"]

x = data['tweet_text']
y = data['cyberbullying_type']



x_train, x_test, y_train, y_test = train_test_split(x, y, test_size = 0.2, random_state = 42)



# Naive Bayes
from sklearn.naive_bayes import MultinomialNB
from sklearn.pipeline import Pipeline

#The tf–idf value increases proportionally to the number of times a word appears in the document
Model = Pipeline([("tfidf", TfidfVectorizer()), ("clf", MultinomialNB())])

Model.fit(x_train, y_train) 

results = cross_val_score(Model, x_test, y_test, cv = 5)
predict = cross_val_predict(Model, x_test, y_test, cv = 5)

NBa = accuracy_score(predict, y_test)
NBp = precision_score(predict, y_test, average='macro')
NBr = recall_score(predict, y_test, average='macro')

plot_confusion_matrix(Model, x_test, y_test)



tfidf = TfidfVectorizer(max_features = 3000)

X_train_tfidf = tfidf.fit_transform(x_train)

X_test_tfidf = tfidf.transform(x_test)  

scaler = StandardScaler()

tfidf_array_train = X_train_tfidf.toarray()   # Converting the sparse matrix to a numpy array (dense matrix)

tfidf_array_test = X_test_tfidf.toarray()     # Converting the sparse matrix to a numpy array (dense matrix)

scaled_X_train = scaler.fit_transform(tfidf_array_train)  # Fitting on only training data to avoid data leakage from test data

scaled_X_test = scaler.transform(tfidf_array_test) # and then tranforming both training and testing data

'''
# Performing Dimensionality Reduction using Principal Component Analysis
from sklearn.decomposition import PCA
NUM_COMPONENTS = 100  # Total number of features
pca = PCA(NUM_COMPONENTS)
reduced = pca.fit(scaled_X_train)

final_pca = PCA(0.9)   
reduced_90 = final_pca.fit_transform(scaled_X_train)

reduced_90_test = final_pca.transform(scaled_X_test)
'''

from sklearn.tree import DecisionTreeClassifier

model = DecisionTreeClassifier(criterion = 'entropy', splitter = 'random')

model.fit(scaled_X_train, y_train)

results = cross_val_score(model, scaled_X_test, y_test, cv = 5)
predict = cross_val_predict(model, scaled_X_test, y_test, cv = 5)

RTa = accuracy_score(predict, y_test)
RTp = precision_score(predict, y_test, average='macro')
RTr = recall_score(predict, y_test, average='macro')


# Performing Dimensionality Reduction using Principal Component Analysis
from sklearn.decomposition import PCA
NUM_COMPONENTS = 3000  # Total number of features
pca = PCA(NUM_COMPONENTS)
reduced = pca.fit(scaled_X_train)

final_pca = PCA(0.9)   
reduced_90 = final_pca.fit_transform(scaled_X_train)

reduced_90_test = final_pca.transform(scaled_X_test)

#SVM
from sklearn.svm import SVC
model = SVC()

model.fit(scaled_X_train, y_train) 

results = cross_val_score(model, scaled_X_test, y_test, cv = 5)
predict = cross_val_predict(model, scaled_X_test, y_test, cv = 5)

SVa = accuracy_score(predict, y_test)
SVp = precision_score(predict, y_test, average='macro')
SVr = recall_score(predict, y_test, average='macro')


plt.figure()
plt.title('Models Precision')
plt.ylabel('Precision (%)')
plt.xlabel('Models')
plt.bar('Naive Bayes', NBp)
plt.bar('Random Trees', RTp)
plt.bar('Support Vector', SVp)
plt.show()

plt.figure()
plt.title('Models Recall')
plt.ylabel('Recall (%)')
plt.xlabel('Models')
plt.bar('Naive Bayes', NBp)
plt.bar('Random Trees', RTp)
plt.bar('Support Vector', SVp)
plt.show()

plt.figure()
plt.title('Models Accuracy')
plt.ylabel('Accuracy (%)')
plt.xlabel('Models')
plt.bar('Naive Bayes', NBp)
plt.bar('Random Trees', RTp)
plt.bar('Support Vector', SVp)
plt.show()