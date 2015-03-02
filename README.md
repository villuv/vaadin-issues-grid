Grid Bugs with Vaadin 7.4.0
===========================

Following problems reproduce with Firefox and Chromium

Problem 1: Column expansion (#16975)
------------------------------------

1. At start, there are no items, column heading is not expanded to full width
2. Add an item with "Add" button, column expansion looks correct now
3. Double click an item to edit, make all items "true"
4. Check "Filtered" checkbox to hide all items that are "true": Expansion is wrong again
5. Uncheck "Filtered" checkbox: Items are displayed but expansion stays wrong.

Problem 2: Row editing, checkbox (#16976)
-----------------------------------------

1. Add two items sugin "Add" button
2. Edit (double click) first item, check the box, save. Value is true
3. Edit second item (false), checkbox is checked. Don't touch checkbox, save. Value remains false.
4. Edit second item again, checkbox is checked. Uncheck the box, save. Value goes false.

Row editor checkbox state is not always correctly propagated to client side. Did some debugging, checkbox internal state looks OK.


