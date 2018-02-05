import pandas as pd
import numpy as np

dirty_dataset_html = pd.read_html("http://people.dbmi.columbia.edu/~friedma/Projects/DiseaseSymptomKB/index.html",header=0)
data_dirty_np = np.asarray(dirty_dataset_html[0])

dirty_dataset = pd.DataFrame(data_dirty_np)
dirty_dataset.to_csv("Datasets/dirty_dataset.csv")


disease = dirty_dataset[0][0]
count_of_disease = dirty_dataset[0][1]
symptom = dirty_dataset[0][2]




##print(disease + count_of_disease + symptom)
