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
                'code': company_code,
                'logo': "https://companiesmarketcap.com/"+company_logo
            })
        with open('company_data.json', 'w', encoding='utf-8') as json_file:
            json.dump(data, json_file, indent=4, ensure_ascii=False)
         
    else:
        print(response.status_code)
    


if __name__ == "__main__":
    main()