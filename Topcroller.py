import requests
from bs4 import BeautifulSoup
from soup2dict import convert
import json

def main():
    url="https://companiesmarketcap.com/"
    response = requests.get(url)

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


        idx = 0
        for td in soup.find_all('td', class_='rh-sm'):
            today = td.find('span').text.strip()
            data[idx]['today'] = today
            idx += 1





        with open('company_data.json', 'w', encoding='utf-8') as json_file:
            json.dump(data, json_file, indent=4, ensure_ascii=False)
         
    else:
        print(response.status_code)
    


if __name__ == "__main__":
    main()