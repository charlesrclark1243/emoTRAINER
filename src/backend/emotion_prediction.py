from fer import FER
from init import DATA_DIR
import sys
import json
import os
import cv2

IMG_FILE_PATH = str(sys.argv[1])
img = cv2.imread(IMG_FILE_PATH)
# img = cv2.imread(r'C:\Users\cr-cl\Git\emoTRAINER\data\imgs\happy-jpeg-test.jpg')

config_file = os.path.realpath(os.path.join(DATA_DIR, 'config.json'))

with open(config_file) as json_file:
    json_dict = json.load(json_file)

# print(json_dict)

detector = FER(mtcnn=json_dict['options']['mtcnn'])
emotions_dict = detector.detect_emotions(img=img)[0]['emotions']

emotions_keys = list(emotions_dict.keys())
emotions_vals = list(emotions_dict.values())
max_val_index = emotions_vals.index(max(emotions_vals))
most_likely_emotion = emotions_keys[max_val_index]
most_likely_emotion_prob = emotions_vals[max_val_index]

print(f'{most_likely_emotion}:{most_likely_emotion_prob}')