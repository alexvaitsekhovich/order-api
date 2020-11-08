# Order API #


![Java CI with Maven](https://github.com/alexvaitsekhovich/order-api/workflows/Java%20CI%20with%20Maven/badge.svg?branch=master) [![pipeline status](https://gitlab.com/alex.vaitsekhovich/order-api/badges/master/pipeline.svg)](https://gitlab.com/alex.vaitsekhovich/order-api//pipelines) [![Build Status](https://travis-ci.org/alexvaitsekhovich/order-api.svg?branch=master)](https://travis-ci.org/alexvaitsekhovich/order-api)  

[![codecov](https://codecov.io/gh/alexvaitsekhovich/order-api/branch/master/graph/badge.svg)](https://codecov.io/gh/alexvaitsekhovich/order-api) [![Codacy Badge](https://app.codacy.com/project/badge/Grade/bff1304d1d394d2187cb2274ed2e2a4a)](https://www.codacy.com/manual/alexvaitsekhovich/order-api?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=alexvaitsekhovich/order-api&amp;utm_campaign=Badge_Grade) [![Maintainability](https://api.codeclimate.com/v1/badges/998d493d1cffc9bac7cb/maintainability)](https://codeclimate.com/github/alexvaitsekhovich/order-api/maintainability)


#### Order API for ordering microservice system

Endpoints:

Endpoint | Method | Description
------------ | ------------ | -------------
/api/v1/orders/customer/{customerId} | GET | Get all orders for the customer
/api/v1/orders/order/{orderId} | GET | Get order
/api/v1/orders | POST | Create an order
/api/v1/orders/{orderId}/actions/{action} | PATCH | Change order status


##### Example post request:

```
{
    "paymentId":1,
    "customerId":1,
    "retailerId":1,
    "amount":1200,
    "discountAmount":0,
    "address" : {
        "zip":"142",
        "city":"Berlin",
        "street":"Chausseestr.",
        "nr":"1"
    },
    "orderParts":[
        {
            "itemId":3,
            "itemName":"Pizza",
            "count":1,
            "price":1000
        },
        {
            "itemId":4,
            "itemName":"Fanta",
            "count":1,
            "price":200
        }
    ]
}
```

##### Allowed order states in PATCH request:
* 0 - created
* 1 - approved
* 2 - delivered
* 3 - cancelled
* 4 - fake

