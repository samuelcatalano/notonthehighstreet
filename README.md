# Not On The High Street
Not On The High Street - Task

### notonthehighstreet.com is an online marketplace, here is a sample of some of the products available on our site:

| Product code | Name | Price |
|:-	|:-	|:-	|
|001 	|Travel Card Holder  	| £9.25 	|
|002 	|Personalised cufflinks  	| £45.00 	|
|003 	|Kids T-shirt  	| £19.95 	|


Our marketing team want to offer promotions as an incentive for
our customers to purchase these items.

> If you spend over £60, then you get 10% off your purchase  
> If you buy 2 or more travel card holders then the price drops to
£8.50.

Our check-out can scan items in any order, and because our
promotions will change, it needs to be flexible regarding our
promotional rules.
The interface to our checkout looks like this (shown in Java):

`Checkout co = new Checkout(promotionalRules)`  
`co.scan(item);`  
`co.scan(item);`  
`Double price = co.total();`

Implement a checkout system that fulfils these requirements.


### Tech Stack:
| Technology | Version |
|--|--|
| **Java** | 11.0.3-2018-01-14 |
| **Project Lombok** | 1.18.20 |
| **OpenCSV** | 5.2 |
| **Jupiter JUnit 5** | 5.7.1 |
| **Hamcrest Core** | 2.2 |
| **Gradle** | 7.0 |

### How to run the application:
> IDE (IntelliJ, Eclipse, NetBeans):
- Importing the project as Maven project on your favourite IDE.
- Build project using Java 11
- Run/Debug project from Main Application Class :: NotOnTheHighStreetMarketPlace

### Program arguments example:
> IDE (IntelliJ, Eclipse, NetBeans):

- You can just run the main class with the default arguments or change them randomly:
- `Arrays.asList("001", "003", "001")`

> Or you can set the program arguments like the image below:  

![Image description](https://i.imgur.com/yUXmsBE.png)

### Result example:
![Image description](https://i.imgur.com/yI3TswZ.png)


### How to run the tests:
- NotOnTheHighStreetMarketPlaceTest: `notonthehighstreet.NotOnTheHighStreetMarketPlaceTest`
- Or run `./gradlew build`

