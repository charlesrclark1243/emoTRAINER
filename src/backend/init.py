import os
import json

ROOT_DIR = os.path.realpath(os.path.join(os.path.dirname(__file__), '..', '..'))
SRC_DIR = os.path.realpath(os.path.join(ROOT_DIR, 'src'))
DATA_DIR = os.path.realpath(os.path.join(ROOT_DIR, 'data'))
IMGS_DIR = os.path.realpath(os.path.join(DATA_DIR, 'imgs'))

if not os.path.exists(DATA_DIR):
    os.mkdir(DATA_DIR)
    os.mkdir(IMGS_DIR)
    
    json_dict = {
        'version' : '0.1.0',
        'options' : {
            'mtcnn' : True
        }
    }
    
    json_obj = json.dumps(json_dict, indent=4)

    with open(os.path.realpath(os.path.join(DATA_DIR, 'config.json')), 'w') as json_file:
        json_file.write(json_obj)
    
    print("1")
else:
    print("0")


