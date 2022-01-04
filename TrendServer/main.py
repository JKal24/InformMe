import os
from flask import Flask, jsonify
from flask_cors import CORS, cross_origin

import twitter
import trends

app = Flask(__name__, instance_relative_config=True)
cors = CORS(app)
app.config['CORS_HEADERS'] = 'Content-Type'
app.config.from_mapping(SECRET_KEY=os.urandom(24))


@app.route('/trends/<string:country>', methods=["GET"])
@cross_origin()
def gather_trends(country):
    twitter_trends = (twitter.get_trends(country))[0:10]
    google_trends = (trends.get_trends(country))[0:10]
    json_data = jsonify(twitter_trends + google_trends)
    return json_data


if __name__ == "__main__":
    app.run(debug=True)
