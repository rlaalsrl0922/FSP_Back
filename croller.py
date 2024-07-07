import tickertick as tt
import tickertick.query as query
import requests
from bs4 import BeautifulSoup
from soup2dict import convert
import json
from datetime import datetime


def Timeconverter(x):
    return datetime.fromtimestamp(x)

def update_time(d):
    if isinstance(d, dict):
        for key, value in d.items():
            if key == 'time':
                d[key] = Timeconverter(d[key]//1000)  # 새로운 값으로 변경 (예시로 10을 사용)
            elif isinstance(value, (dict, list)):
                update_time(value)
    elif isinstance(d, list):
        for item in d:
            update_time(item)

def main():
    print("write ticker and number of newses")

    ticker,count=tuple(map(str,input().split()))

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
        with open('output.json', 'w', encoding='utf-8') as output_file:
            json.dump(parsed_data, output_file, default=str,indent=2, ensure_ascii=False)


    else : 
        print(response.status_code)


if __name__ =="__main__":
    main()