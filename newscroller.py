import tickertick as tt
import tickertick.query as query
import requests
from bs4 import BeautifulSoup
from soup2dict import convert
import json
from datetime import datetime
import yfinance as yf
import pandas as pd
import os
import time

def update_time(d):
    if isinstance(d, dict):
        for key, value in d.items():
            if key == 'time':
                d[key] = datetime.fromtimestamp(d[key]//1000)  # 새로운 값으로 변경 (예시로 10을 사용)
            elif isinstance(value, (dict, list)):
                update_time(value)
    elif isinstance(d, list):
        for item in d:
            update_time(item)

def split_ticker(d):
    for story in d['stories']:
        if isinstance(story['tickers'], list) and len(story['tickers']) == 1:
            story['tickers'] = story['tickers'][0]

def csv_to_json(ticker):

    df = pd.read_csv(f"{ticker}.csv")
    json_data = df.to_json(orient='records')
    json_data = json.loads(json_data)
    json_data = json.dumps(json_data, indent=4)
    with open(f'{ticker}_price.json', 'w') as json_file:
        json_file.write(json_data)
    #os.remove(csv)

def get_news(ticker,cnt):
    url = f"https://api.tickertick.com/feed?q=z:{ticker}&n={cnt}"

    response = requests.get(url)


    if response.status_code == 200:
        html = response.text
        soup = BeautifulSoup(html, 'html.parser')
        dict_result = convert(soup)
        navigable_string = dict_result["navigablestring"][0]
        parsed_data = json.loads(navigable_string)

        update_time(parsed_data)
        split_ticker(parsed_data)
        
        for story in parsed_data.get('stories', []):
            if 'description' in story:
                del story['description']
            if 'tags' in story:
                del story['tags']
            if 'site' in story:
                del story['site']
        if 'last_id' in parsed_data:        
            del parsed_data['last_id']

        # JSON 파일로 저장
        with open(f'{ticker}.json', 'w', encoding='utf-8') as output_file:
            json.dump(parsed_data, output_file, default=str,indent=2, ensure_ascii=False)

    else : 
        print(response.status_code)

    '''
    file_path = f'{ticker}.json'

    with open(file_path, 'r', encoding='utf-8') as file:
        data = json.load(file)
    '''
    return parsed_data

def extract_tickers_from_json(file_path):
    tickers = []
    with open(file_path, 'r', encoding='utf-8') as json_file:
        data = json.load(json_file)
        for company_data in data:
            tickers.append(company_data['ticker'])
    return tickers


def main():
    ticker_list=extract_tickers_from_json('Toplist.json')
    url = "http://localhost:8080/news"
    headers = {'Content-Type': 'application/json; charset=utf-8'}

    for i in ticker_list:
        data=get_news(i,10)
  

    '''response = requests.post(url, headers=headers, json=data)
    if response.status_code==204:
        print('complete')
    else:
        print('error')'''

if __name__ =="__main__":
    main()