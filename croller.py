#import modules
import requests
from bs4 import BeautifulSoup
import pandas as pd
import yfinance
import FinanceDataReader as fdr
from datetime import datetime, date
import json
from os.path import exists


def main():
    
    
    ticker = input()

    aapl = yfinance.Ticker(ticker.upper())

    print(aapl.news)
    print(aapl.news[0]['link'])
    sub = aapl.news
    uuid,publisher,title,link=[],[],[],[]
    for i in range(len(aapl.news)):
        title.append(sub[i]['title'])
        link.append(sub[i]['link'])
        uuid.append(sub[i]['uuid'])
        publisher.append(sub[i]['publisher'])

    ''' Data Sample
    'uuid': '96ade2ca-d763-3676-9a1f-f9b160794bb0'
    'title': 'July 4th travel, S&P 500 broadening: Asking for a Trend'
    'publisher': 'Yahoo Finance Video'
    'link': 'https://finance.yahoo.com/video/july-4th-travel-p-500-215641022.html'
    '''    

    #Maximum 8
    print(title)
    print(link)

if __name__ == "__main__":
    main()