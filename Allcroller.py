import tickertick as tt
import tickertick.query as query
import requests
from bs4 import BeautifulSoup
from soup2dict import convert
import json
from datetime import datetime
import yfinance as yf
import pandas as pd

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
            del story['description']
            del story['tags']
            del story['site']


        # JSON 파일로 저장
        with open(f'{ticker}.json', 'w', encoding='utf-8') as output_file:
            json.dump(parsed_data, output_file, default=str,indent=2, ensure_ascii=False)

    else : 
        print(response.status_code)


    file_path = f'{ticker}.json'

    with open(file_path, 'r', encoding='utf-8') as file:
        data = json.load(file)
    return parsed_data

def extract_tickers_from_json(file_path):
    tickers = []
    with open(file_path, 'r', encoding='utf-8') as json_file:
        data = json.load(json_file)
        for company_data in data:
            tickers.append(company_data['ticker'])
    return tickers


def main():
    crollingurl="https://companiesmarketcap.com/"
    response = requests.get(crollingurl)

    if response.status_code == 200:
        html = response.text

        soup = BeautifulSoup(html, 'html.parser')

        data = []
        
        for td in soup.find_all('td', class_='name-td'):
            company_name = td.find('div', class_='company-name').text.strip()
            company_code = td.find('div', class_='company-code').text.strip()
            company_logo = td.find('img', class_='company-logo')['src'].strip()
            
            data.append({
                'name': company_name,
                'ticker': company_code,
                'logoUrl': "https://companiesmarketcap.com/"+company_logo
            })  

        idx = 0
        for td in soup.find_all('td', class_='td-right'):
            price = td.text.strip()
            if price.startswith('$') and not price[-1].isalpha():
                data[idx]['price'] = price
                idx+=1

        td_tags = soup.find_all('td', class_='rh-sm')

        idx=0
        for td_tag in td_tags:
            span_tag = td_tag.find('span')
            if span_tag:
                if 'percentage-red' in span_tag['class']:
                    today = '-' + span_tag.get_text(strip=True).split('-')[-1]
                elif 'percentage-green' in span_tag['class']:
                    today = '+' + span_tag.get_text(strip=True).split('+')[-1]
            data[idx]['today'] = today
            idx += 1



        with open('Toplist.json', 'w', encoding='utf-8') as json_file:
            json.dump(data, json_file, indent=4, ensure_ascii=False)
         
    else:
        print(response.status_code)
    


    top_url = 'http://localhost:8080/stocks/top100'
    headers = {'Content-Type': 'application/json; charset=utf-8'}
    post_response = requests.post(top_url,headers=headers,json=data)
    if post_response.status_code == 204:
        print('complete')
    else:
        print("error")



    ticker_list=extract_tickers_from_json('Toplist.json')
    news_url = "http://localhost:8080/news"

    cnt=0
    for i in ticker_list:
        data=get_news(i,10)

        response = requests.post(news_url, headers=headers, json=data)
        cnt+=1
        if cnt%10==0 and response.status_code==204:
            print('complete')
        else:
            print('error')


if __name__ == "__main__":
    main()