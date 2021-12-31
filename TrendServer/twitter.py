import json
import tweepy

with open('./resources/credentials.json') as f:
    params = json.load(f)

with open('countries.json') as c:
    countries = json.load(c)

auth = tweepy.AppAuthHandler(params['key'], params['secret'])
api = tweepy.API(auth)


def get_trends(country):
    trends = api.get_place_trends(countries[country])[0]['trends']
    return [t['name'] for t in trends]
