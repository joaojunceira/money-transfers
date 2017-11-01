# API Design
* Transfer Money

  Transfer money between two accounts inside of the same bank.
  
  The API is using IBAN instead of Account Id to make API flexible for Interbank transfers in future.
  
  * Request:
  

    POST \transfer
    
    ```json
    {
    	"source": "LT6000000000000000000001",
    	"destination": "LT6000000000000000000002",
    	"amount": {
    		"amount": 50,
    		"currency": "USD"
    	}
    }
    ```
    
  * Response:
  
    ** Success (200)
   
     ```json
     {
     	"id": 1,
     	"source": "LT6000000000000000000001",
     	"destination": "LT6000000000000000000002",
     	"amount": {
     		"amount": 0,
     		"currency": "USD"
     	}
     }
     ```
      
     ** Bad request (400)


    Used to reject bad IBAN and Currency Codes format or non-suported Currency Codes. It might also apply to a non-existing IBAN. It will reply a message describing the failure.
    
    
     ```json
     {
         "message":"Invalid IBAN format"
     } 
     ```

     ** Forbidden (403)
   
      
    Not enough money to do transfer.
      
      
      
* Create Account
  
  POST \account
  
  Request:
  ```json
  {
    "userId":1,
    "currency":"USD"
  }
  ```
  
  Response:
  * Status Code 404 - User not found
  * Status Code 201 - Account created (See Location header)
  
* Get Account details
  
  GET \account\{iban}
    
  Response:
  * Status Code 404 - Account not found
  * Status Code 200 
  ```json
  {
    "iban":"LT6000000000000000000002",
    "balance":{
      "amount":100.0,
      "currency":"USD"
    },
    "userId":1,
    "lastMovement":[2017,11,1,0,40,45,406000000]
  }
  ```

* Create User
  
  POST \user
  
  Request:
  ```json
  {
    "firstName":"John",
    "lastName":"Smith",
    "birthDate":[1980,11,1]
  }
  ```
    
  Response:
  * Status Code 201 - User created (See Location header)
  
* Get User
  
  GET \user\{id}
    
  Response:
  * Status Code 404 - User not found
  * Status Code 200
  ```json
  {
    "id":1,
    "firstName":"John",
    "lastName":"Smith",
    "birthDate":[1980,11,1]
  }
  ```