### bugs ###
=======
+ fix spellcheck
+ fix autocorrect
+ fix auto-capitalization
+ fix border radius
+ fix theme
+ selection doesn't work with 4space
+ save recents/favorites across installs
+ access recents/favorites in settings
+ use separator tildes for favorites


### features ###
=======
+ implement custom layout
+ include contact names suggestions 
+ learn new words + adding entries on the fly
+ use system languages
+ auto insertion of plus/minus/bullet
+ function to add or remove plus/minus/bullet
+ add hyphens and apostrophes to predictions
+ add words to dictionary, get stored in setting 
+ autoreplace toggleable from keyboard predictions 
+ choice of fifo or filo for clipboard history
+ instead of getting rid of last clipboard entry, send that one indefinitely
+ append clipboard contents to file on cut or copy
+ row with toggles from settings page
+ tabs on symbols page 
+ language detection
+ get location as string, coordinates or address
+ format numbers
+ shortcuts with sigil
+ column from table
+ identify hash
+ animations
+ autoconvert number bases
+ get suggestions for next word 
+ look up tutorial for glide typing
+ autofill service


### dev ###
=======
r copy
+ include tags ffs
+ script for autoreinstall
+ automated button pressing in monkeyrunner


### actions ###
=======
+ if prev character is a space, insertion of punctuation will attach it to the end of the word and insert another space
+ add setting to toggle repeatable
+ selection moves while typing
+ select with manual cursor movement
+ language switch key toggle
+ double-space period
+ if no selection, un/indent saves cursor spot, highlights all, then puts it back ±4
+ fix indentation buttons
+ comment togglers delete line if selection off
+ fonts turn off after one key press
+ font modes for typing
+ typing and deleting underscore and strikethrough
+ selection while typing
+ delete autoinsertions by pressing once


### customization ###
=======
+ look up tutorial to choose background photo
+ implement custom shortcuts
+ reorder layouts from settings: draggable for layout management
+ runtime modification/generation of layouts
+ generate keyboard programmatically
+ key codes and labels customizable
+ hide and show specific keys, especially in the top or bottom
+ macro customization
+ add and remove keys
+ add setting that allows specification of longpress delay
+ add slots in the settings page for customizing the letters that appear on long press
+ keypress vibration duration
+ vibrate on keypress
+ keypress sound volume
+ key long press delay


### gestures ###
=======
+ left and right swipe on spacebar
+ slideable bar
+ delete swipe
+ changing row height with drag
+ actions on keyboard and key gestures
+ cursor movement on space key drag
+ space bar trackpad
+ press and hold to delete all space up to line end (while prev char is space)
+ move cursor and select text by dragging the space key


### popup ###
=======
+ key popup dismiss delay
+ press and hold macros/custom keys to change them
+ custom key settings' popups should contain three inputs: one to change the label, and one to change the value, and one to change its longpress value
+ fix tld popup
+ multiline popup
+ if there are too many entries in a keys popup, it doesn't render properly
+ popups when holding down key, disappear on release
+ close popup on blur
+ unicode data display
+ press and hold suggestion bar for more
+ voice input key in suggestion row
+ create a popup with two inputs that allows arbitrary search and replace
+ close by clicking outside popup
+ edittext popup for temporary stuff
+ settings opens up a drawer
+ popup contents should also be keys
+ layout of popup
+ make popup keys repeatable
+ release on popup for choice
+ popup for examining characters
+ send find/replace result back to edittext
+ scratch textedit popup
+ unicode letters press on square not letter
+ popup outside click
+ popup multirow
+ search tab in emoji and unicode
+ find/replace closes when typing

##### settings #####
+ system settings enable keyboard
+ choose keyboard
+ generate keyboard from text string and vice versa
    + delimiter
    + shift
    + label
    + rows
    + offset
+ list of layouts, add, enabled, reorder, customize
+ add by duplication of other layouts
+ available templates from many languages
+ key props like shift chars cursor movement
+ layout
    + add key
    + move key
    + row number
    + height width
    + send on release or persist
    + swipe left right up down
+ row
    + height width offset
+ key
    + code
    + label or icon
    + color text background corner
    + repeatable
    + toggle, states
    + popup choices