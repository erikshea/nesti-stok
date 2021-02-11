# Nesti Stock Management

Database settings and importable SQL files are in the "database_settings" folder in project root.

The first window that shows when opening the application is the login page:

![](src/main/resources/readme/loginPage.png)

Test user login: erik

Test user password: 1234

The user stays connected between app sessions, until the "disconnect" button is clicked:

![](src/main/resources/readme/disconnect.png)

After logging in, the main window is shown, with several open tabs each containing a directory of items (shown below is the articles directory).

![](src/main/resources/readme/directoryArticle.png)

A search bar can be found at the top of each directory tab. It is possible to filter the list of items by value for a single column

![](src/main/resources/readme/searchBar1.png)

...or to combine search filters for multiple columns:

![](src/main/resources/readme/searchBar2.png)

Table columns can be sorted by clicking on their headers:

![](src/main/resources/readme/sortList.png)

It is possible to add an article to the shopping cart

![](src/main/resources/readme/addToCart.png)

It can then be seen by clicking the "Shopping cart" tab. If it had already existed in the cart prior to being added, its quantity would've been incremented.

![](src/main/resources/readme/cartFull.png)

Both the article quantity and the chosen supplier can be changed from within the shopping cart:

![](src/main/resources/readme/CartChangeQuantity.png)
![](src/main/resources/readme/CartChangeSupplier.png)

The totals are automatically updated when any item in the cart changes:

![](src/main/resources/readme/CartSum.png)

Orders can be 

When clicking on an individual list item (for example "article"), editable fields are shown:

![](src/main/resources/readme/articleInformation.png)

When clicking on an individual list item (for example "article"), editable fields are shown:

![](src/main/resources/readme/articleInformation.png)

Invalid fields become orange in real-time as the user types:

![](src/main/resources/readme/articleInfoOrangeField.png)

If an item contains a list of associations, items can be added by clicking on the "plus" button:

![](src/main/resources/readme/articleInfoAddUnit.png)

...and deleted by clicking on the "minus" button:

![](src/main/resources/readme/articleInfoDeleteUnit.png)

Similar information tabs can be found for each directory. Shown here is an order's information:

![](src/main/resources/readme/orderInformation.png)

...a supplier's

![](src/main/resources/readme/supplierInformation.png)

...and an ingredient's:

![](src/main/resources/readme/ingredientInformation.png)


