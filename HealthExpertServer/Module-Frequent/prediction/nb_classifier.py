import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
from sklearn.naive_bayes import MultinomialNB
from sklearn.model_selection import train_test_split

data = pd.read_csv('sym_dis_matrix.csv')

print(data.shape)
data = data.fillna(0)
data.head(5)
cols = data.columns.tolist()
x = data[cols]
y = data.eye
x_train, x_test, y_train, y_test = train_test_split(x, y, test_size=0.33, random_state=42)
mnb = MultinomialNB()
mnb = mnb.fit(x_train, y_train)
mnb.score(x_test, y_test)

mnb_tot = MultinomialNB()
mnb_tot = mnb_tot.fit(x, y)
print(mnb_tot.score(x, y))
