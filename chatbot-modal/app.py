# libraries
import random
import numpy as np
import pickle
import requests
import json
from flask_json import FlaskJSON, JsonError, json_response, as_json
from flask import Flask, request, jsonify
import nltk
from flask_cors import CORS
from keras.models import load_model
from nltk.stem import WordNetLemmatizer
from py_eureka_client import eureka_client

lemmatizer = WordNetLemmatizer()
nltk.download('omw-1.4')
nltk.download("punkt")
nltk.download("wordnet")


model = load_model("chatbot_model_mql.h5")
intents = json.loads(open("intentsmql.json").read())
words = pickle.load(open("words_mql.pkl", "rb"))
classes = pickle.load(open("classes_mql.pkl", "rb"))

app = Flask(__name__)
FlaskJSON(app)
#CORS(app, origins=['http://localhost:3000'])

eureka_client.init(
    eureka_server="http://localhost:8761//eureka", 
    app_name="chatbot-modal",
    instance_port=5000, 
)
@as_json
@app.route("/get", methods=["POST"])
def chatbot_response():
    if not consume():
       return jsonify(dict(output = {
           "error" : "you can't use it"
       }))
    msg = request.form["msg"]
    if msg.startswith('my name is'):
        name = msg[11:]
        ints = predict_class(msg, model)
        res1 = getResponse(ints, intents)
        res =res1.replace("{n}",name)
    elif msg.startswith('hi my name is'):
        name = msg[14:]
        ints = predict_class(msg, model)
        res1 = getResponse(ints, intents)
        res =res1.replace("{n}",name)
    else:
        ints = predict_class(msg, model)
        res = getResponse(ints, intents)
    return dict(output=res)


def clean_up_sentence(sentence):
    sentence_words = nltk.word_tokenize(sentence)
    sentence_words = [lemmatizer.lemmatize(word.lower()) for word in sentence_words]
    return sentence_words


def bow(sentence, words, show_details=True):
    sentence_words = clean_up_sentence(sentence)
    bag = [0] * len(words)
    for s in sentence_words:
        for i, w in enumerate(words):
            if w == s:
                bag[i] = 1
                if show_details:
                    print("found in bag: %s" % w)
    return np.array(bag)


def predict_class(sentence, model):
    p = bow(sentence, words, show_details=False)
    res = model.predict(np.array([p]))[0]
    ERROR_THRESHOLD = 0.25
    results = [[i, r] for i, r in enumerate(res) if r > ERROR_THRESHOLD]
    results.sort(key=lambda x: x[1], reverse=True)
    return_list = []
    for r in results:
        return_list.append({"intent": classes[r[0]], "probability": str(r[1])})
    return return_list


def getResponse(ints, intents_json):
    tag = ints[0]["intent"]
    list_of_intents = intents_json["intents"]
    for i in list_of_intents:
        if i["tag"] == tag:
            result = random.choice(i["responses"])
            break
    return result


@app.route('/')
def hello_world():
    return 'Hello, Flask Eureka Client!'


#def get_solde():
#   authorization = request.headers.get('Authorization')
#   headers_data = {
#       'Authorization': authorization,
#   }
#   response = requests.get("http://localhost:8888/ACCOUNT-SERVICE/accounts/actualuser", headers=headers_data)
#   if response.status_code == 200:
#       solde = response.json().get('solde')
#       if solde >= 1 :
#           return True
#       else :
#           return False
#   else :
#       return False

def consume():
    authorization = request.headers.get('Authorization')
    headers_data = {
        'Authorization': authorization,
    }
    response = requests.get("http://localhost:8888/ACCOUNT-SERVICE/accounts/solde/consume", headers=headers_data)
    if response.status_code == 200:
        solde = response.json().get('solde')
        if solde >= 0 :
            return True
        else :
            return False
    else :
        return False

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)

