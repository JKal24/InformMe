from pytrends.request import TrendReq

TrendReq()

pytrends = TrendReq()


def get_trends(country):
    country = country.lower().replace(" ", "_")
    trends = (pytrends.trending_searches(country)).values.tolist()
    return [t[0] for t in trends]
