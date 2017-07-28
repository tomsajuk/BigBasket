# ShopperDost
Our SDL project for third year Computer Science Engg. 
*******************************************************************************************************************************

## PROBLEM STATEMENT
  All around the world we have super markets sprouting at residential places, at malls and odd places. The customers roam around the market looking for what they want to look at the shelfs stacked with variety of products and brands to choose from. And then they end up queueing at the cash counter to pay the bill, waiting for their turn for long. This is a major problem in all supermarkets. 
There is a huge queue formed in front of the cash counter always and the owners end up adding more counters to ease the billing. 
We also have a lot of the customers roaming around the market looking for a product but they never can find it and end up leaving the place in disappointment. And there are some who forget to buy some items and end up coming back again to buy it. They have wasted their precious time because of this.

## PROPOSAL
  The huge queue formed in front of the counter is mainly because of the time it takes to identify the item, make the bill, and then the waiting time for the customer to take the cash from his purse. Some customers may have only one item while others may be shopping for apocalypse. Mainly it takes up a lot of time off the customers and the owner.  Our proposal is to have the process of making the bill digitally in the customers own smart phones as they shop using Android app. Also, customers can pay through Paytm if they don’t have the cash right away. This will help in reducing the queue. 
	People are humans and they tend to forget things. Likewise they may forget what to buy from the market. Proposal, the app already proposed would have an in-built shopping list that the customer can create whenever they remember an item to buy and the next time they go to the market they can just look up in their list and shop without ransacking their brain if they have forgot to buy a thing.
	We also propose a suggestion system which will suggest some product based on the items the customers brought. It will help the customer relate to items and also increase sales.

 ##### Submitted By :-      
 Shivam Singh 3343          *
 Shailendra Patel 3341      *
 Thomas Saju Koshy 3350     

*******************************************************************************************************************************
*******************************************************************************************************************************

## 1.0. Introduction

### 1.1. Purpose
  The purpose of this document is to present a detailed description of the Shopping Bill and Market Analysis System. It will explain the purpose and features of the system, the interfaces of the system, what the system will do, the constraints under which it must operate and how the system will react to external stimuli. 
  
### 1.2. Scope of Project
This software system will be a Shopping Bill creator for customer of the supermarket. This system will be designed to create the bill while the customer shops in the supermarket without waiting at for his turn at the counter. By digitally creating the bill in real-time, the time and space is not wasted.
More specifically, this system is designed to work as a Market Suggestion System which would give suggestion to the customer for the next item to buy. It will help the customer in remembering what they may forgot to buy and also increase the sales of the supermarket. 

*******************************************************************************************************************************

## 2. Requirement Specification

### 2.1 Software Requirement

  The software required for building the UI for the customer side using Android Studio to build an App. Using it the shopping bill is created as and when an item is brought by the customer. It will read the barcode and after decoding the code the item is inserted into the bill and the total is calculated. If needed the item can also deleted if the person decides to return it back.
	The App will also have a shopping list option wherein the person can note which item to buy and reminds the user whenever he starts shopping.
	The system also needs a Database Management System(MongoDB) where the product details with their barcode can be stored. Whenever the customer captures the barcode, the database sends the details related to that code which helps in the creation of the bill.
	The system needs a backend software that will help in sending and receiving requests from the App and the database. It will also send requests to the owner whenever the stocks are running low.
	The system will also require an R script that receives an item and outputs the most probable item that can be brought by the customer.

### 2.2 Hardware Requirement

  The system would require the customer to own an Android OS running smart phone which has a camera. Also, the phone should have Wi-Fi that can connect to the counter.
	The system will require the supermarket to have a central server with connectivity capability so that it can connect to the customers phone and receive the requests. The server will also store the database of the supermarket products.

*******************************************************************************************************************************

## 3. Overall Description

  The system will have a central server which is connected to a storage device mainly a PC which will have all the products available in the supermarket in a database. The server will also be connected to the customers smartphones and the PC at the counters and the owner using Wi-Fi.
When a customer enters the supermarket he will have to connect to the central Wi-Fi system. The customer will roam around the market and when he identifies the product he needs he can scan the bar code using his phone camera and it sends the code to the server through the network and the server sends back the details of the item for the creation of the bill. As the customer scans more items the bill grows. 
When the customer is ready to checkout he has two options, he can pay through online or using cash. As the bill is already generated in the phone the counter already has the details of the bill. The customer can just pay through online and proceed to exit or he can just flash his bill no and pay the cash on the counter.
The data analysis that is being done is with the items the customer buys. When an item is inserted into the bill, the server also sends a suggestion notification about the next item he may probably buy and where the item may be placed. In this way he can navigate without searching the whole place again for that item. 
In the server side, it will have the database details and the script for suggestive analysis. When a code is received it looks up in the database and sends the details back to the requestor. And then it runs the Suggestion Algorithm on that item and sends the next probable item detail he may buy.

*******************************************************************************************************************************
