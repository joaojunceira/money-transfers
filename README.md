# Money Transfers API
## Requirements
1. keep it simple and to the point (e.g. no need to implement any authentication, assume the API is invoked by another  internal system/service);
1. use whatever frameworks/libraries you like (except Spring, sorry!) but don't forget about the requirement #1;
1. the datastore should run in-memory for the sake of this test;
1. the final result should be executable as a standalone program (should not require a pre-installed container/server);
1. demonstrate with tests that the API works as expected.
## API Design
* Transfer Money

  Transfer money between two accounts inside of the same bank.
  
  * Request:
  

    POST \transfer
    
    ```json
    { 
       "source":"NL20RABO0132682018",
       "destination":"NL96MHCB0630250242",
       "amount":{  
          "amount":59.95,
          "currency":"EUR"
       }
    }
    ```
    
  * Response:
  
   ** Success (200)
   
     ```json
     {
        "id":1200000,
        "balance":{
           "amount":"59.95",
           "currency":"EUR"
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

* Get Account details

* Create User

* Get User and Accounts
