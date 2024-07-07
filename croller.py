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

def csv_to_json(ticker):

    df = pd.read_csv(f"{ticker}.csv")
    json_data = df.to_json(orient='records')
    json_data = json.loads(json_data)
    json_data = json.dumps(json_data, indent=4)
    with open(f'{ticker}_price.json', 'w') as json_file:
        json_file.write(json_data)
    #os.remove(csv)

def main():

    # news data
    print("write ticker and number of newses")
    ticker,count=tuple(map(str,input().split()))

    # price data
    print("write start and end, ex) 'YYYY-MM-DD'")
    start,end=tuple(map(str,input().split()))

    df = yf.download(f'{ticker}',start=start,end=end)
    df.to_csv(f"{ticker}.csv")
    csv_to_json(ticker)


    url = f"https://api.tickertick.com/feed?q=z:{ticker}&n={count}"

    response = requests.get(url)


    if response.status_code == 200:
        html = response.text
        soup = BeautifulSoup(html, 'html.parser')
        dict_result = convert(soup)
        navigable_string = dict_result["navigablestring"][0]
        parsed_data = json.loads(navigable_string)

        update_time(parsed_data)

        # JSON 파일로 저장
        with open(f'{ticker}.json', 'w', encoding='utf-8') as output_file:
            json.dump(parsed_data, output_file, default=str,indent=2, ensure_ascii=False)

    else : 
        print(response.status_code)


    file_path = f'{ticker}.json'

    url = "http://localhost:8080/news/req"

    with open(file_path, 'r', encoding='utf-8') as file:
        data = json.load(file)

    # response = requests.post(url, json=data)

if __name__ =="__main__":
    main()