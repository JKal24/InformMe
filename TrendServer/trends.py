from pytrends.request import TrendReq

TrendReq()

pytrends = TrendReq()


def get_trends(country):
    trends = (pytrends.trending_searches(country)).values.tolist()
    return [t[0] for t in trends]
