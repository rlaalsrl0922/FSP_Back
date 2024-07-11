import yfinance as yf
import csv
import json
import pandas as pd

def csv_to_json(ticker):

    df = pd.read_csv(f"{ticker}.csv")
    json_data = df.to_json(orient='records')
    json_data = json.loads(json_data)
    json_data = json.dumps(json_data, indent=4)
    with open(f'{ticker}_price.json', 'w') as json_file:
        json_file.write(json_data)

def main():
    print("write ticker, start and end, ex) 'YYYY-MM-DD'")
    ticker,start,end=tuple(map(str,input().split()))
    
    df = yf.download(f'{ticker}',start=start,end=end)
    df.to_csv(f"{ticker}.csv")
    csv_to_json(ticker)

if __name__ =="__main__":
    main()