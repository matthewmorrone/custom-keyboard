### to do ###
https://developer.android.com/guide/topics/text/creating-input-method.html#java
https://customkeyboarddetails.blogspot.com/2019/02/available-resources-for-android.html

### things I know how to do ###
+ indentation
+ fonts

### things to put in ###
=======
+ wider selection of emoji
+ number input/slider combined for specification of keypress delay
+ custom key settings' popups should contain three inputs: one to change the label, and one to change the value, and one to change its longpress value
+ color selection/input to change key text, background, borders
+ popups when holding down key, disappear on release
+ changing row height with drag
+ voice button in suggestion row
+ cursor movement on space key drag
+ contact name suggestions
+ multiline popup
+ close popup on blur
+ use system languages
+ actions on keyboard and key gestures

## OpenBoard Settings ##
+ cursor movement on space key drag
+ contact name suggestions
+ multiline popup
+ close popup on blur
+ use system languages
+ Press and hold suggestion bar for more
+ Voice input key in suggestion row
+ Language Switch Key toggle
+ Key popup dismiss delay
+ Keypress vibration duration
+ Keypress sound volume
+ Key long press delay
+ Show app icon
+ Space bar trackpad
+ Delete swipe
+ Auto-capitalization
+ Double-space period
+ Vibrate on keypress
+ Sound on keypress
+ Popup on keypress
+ Voice input key

### spellcheck/autocomplete ###
+ reimplement
+ include contact names
+ learn new words
+ adding entries on the fly
+ autoreplace toggleable from keyboard predictions 
+ autocomplete capitalization

### clipboard ###
+ show clipboard contents after cut/copy
+ page containing clipboard history
+ choice of fifo or filo for clipboard history
+ button to access recent clipboard entries
+ instead of getting rid of last clipboard entry, send that one indefinitely
+ append clipboard contents to file on cut or copy

### customization ###
+ font color, background, border from settings
+ settings for numbers, colors, popupchars
+ press and hold custom keys to change them
+ macro customization
+ add and remove keys
+ hide and show specific keys, especially in the top or bottom
+ row with toggles from settings page
+ add setting that allows specification of longpress delay
+ add slots in the settings page for customizing the letters that appear on long press
+ add toggle for whether or not the first member of the popup is sent upon release
+ reorder layouts from settings: draggable for layout management
+ runtime modification/generation of layouts
+ generate keyboard programmatically
+ key codes and labels customizable

### functionality ###
+ create a popup with two inputs that allows arbitrary search and replace
+ press and hold to delete all space up to line end (while prev char is space)
+ move cursor and select text by dragging the space key
+ if no selection, un/indent saves cursor spot, highlights all, then puts it back ±4
+ selection doesn't work with 4space
+ close by clicking outside popup
+ delete autoinsertions by pressing once
+ swipe on spacebar + left and right swipe on spacebar
+ slideable bar

### under the hood ###
+ include tags ffs
+ script for autoreinstall
+ convenience method for setting checks
+ manually reimplement cut/copy/paste

### bugs ###
+ label size issue for high surrogates
+ bug when autoshift
+ wordfore only works for next word
+ linenext and linelast probably don't work
+ if there are too many entries in a keys popup, it doesn't render properly

### layouts ###
+ unicode input from exhaustive table
+ tabs on symbols page 
+ swiping, scrollable/swipeable areas

### nice to haves ###
+ password buttons
+ language detection
+ edittext popup for temporary stuff
+ change enter icon depending on input type
+ selection pad
+ selection moves while typing
+ select with manual cursor movement
+ get location as string, coordinates or address
+ format numbers
+ settings opens up a drawer
+ shortcuts with sigil
+ column from table
+ identify hash
+ color input + tocolor & fromcolor
+ disable context menu in edittexts
+ nonliteral tab
+ animations
+ popup contents should also be keys
+ layout of popup
+ make popup keys repeatable
+ release on popup for choice


##### Settings #####
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
    + height width offset
    + swipe left right up down

