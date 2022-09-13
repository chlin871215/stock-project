# 柏成箖 Project

## API

* Transaction

```=J
{
    "tradeDate":"20220908",//交易日期：限八碼
    "branchNo":"F62S",//公司代號：限四碼
    "custSeq":"00",//客戶代號：限七碼
    "docSeq":"ZZA45",//委託書號：限五碼
    "stock":"2222",//股票代號：限六碼
    "price":10,//股票價格：限六位數
    "qty":2000//股票數量：限九位數
}
```

* UnrealProfit

```=J
{
  "branchNo":"F62S",//公司代號：限四碼
  "custSeq":"00",//客戶代號：限七碼
  "stock":"",//股票代號：限六碼、留空表示找全部
  "upperLimit":99.00,//獲利率上限：上下限相等表示無限制範圍
  "lowerLimit":10.00//獲利率下限：上下限相等表示無限制範圍
}
```

* SumUnrealProfit

```=J
{
  "branchNo":"F62S",//公司代號：限四碼
  "custSeq":"00",//客戶代號：限七碼
  "stock":"",//股票代號：限六碼、留空表示找全部
  "upperLimit":99.00,//獲利率上限：上下限相等表示無限制範圍
  "lowerLimit":10.00//獲利率下限：上下限相等表示無限制範圍
}
```

* UpdatePrice

```=J
{
  "stock":"2222",//股票代號：限六碼
  "price":"11.0"//股票價格：限六位數
}
```

* TodayPay

```=J
{
  "branchNo":"F62S",//公司代號：限四碼
  "custSeq":"00"//客戶代號：限七碼
}
```

* Caching

```=J
{
    "stock":"2222"//股票代號：限六碼
}
```

