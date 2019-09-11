Bugs
- sometimes when using the case-changing feature, it works, but afterwards the selection jumps to somewhere earlier in the text
- if there are too many entries in a key's popup, it doesn't render properly
- sometimes when cutting/copying/pasting large chunks of text, those keys will stop working until the keyboard is disabled and reenabled
- the contents of keys whose contents are modified during run-time aren't printed on the keys until something is pressed
- ensure proper layout iteration

Changes
- the template had gesture typing, get that back
- the template had autocorrect, get that back
- label size issue for high surrogates
- popup should close when tapping outside of it

Keys
+ duplicate lines
+ add key for voice input
+ add key to access settings page
+ add key for access to keyboard management page in settings
+ add key that toggles selection extension when moving the cursor
+ button to access recent clipboard entries
+ delete or jump by word
+ double space period
+ personal dictionary settings link
+ move cursor and select text by dragging the space key
+ popup contents should also be keys
+ press and hold custom keys to change them
+ forward delete functions as backward delete if cursor at end

Layouts
+ frequently used Chinese characters
+ preview of first one or two popup keys
+ keyboard for modifiers
+ display unicode information about key, or code

Settings
+ add setting that allows specification of longpress delay
+ add toggle for whether or not the first member of the popup is sent upon release
+ add an option that adds the opposite of the current shifted state to the start of each key's popup
+ add slots in the settings page for customizing the letters that appear on long press
+ hide and show specific keys, especially in the top or bottom

Features
+ font color, background, border from settings
+ separate out settings page for layouts and custom keys
+ replace screen for arbitrary
+ reorder layouts from settings
+ swipe down to close keyboard, or swipe up and down to change row amount 
+ auto insertion of apostrophes for specific contractions
+ auto capitalization of "I" if /^i / or / i /
+ drag control for keyboard height
+ autocorrect with google
+ change shift, (select, )bold and italic keys to reflect status  
+ height and width changes on layout change
+ animations
+ debug mode: tons of toasts, selection indices
+ unicode input from exhaustive table
+ if cursor start and end are not equal, delete does backspace


planned settings outline

+ settings
  + system settings enable keyboard
  + choose keyboard
  + list of layouts, add, enabled, reorder, customize
  + add by duplication of other layouts
  + available templates from many languages

+ layout settings
  + add key
  + move key
  + row number
  + height width
  + send on release or persist
  + swipe left right up down

+ row
  + height width offset

+ key settings
  + code
  + label or icon
  + color text background corner radius
  + repeatable
  + toggle, toggle states
  + popup choices
  + height width offset
  + swipe left right up down


<!-- Samsung
    top row
    .com and www. key
    switch between widgets and autocorrect
    settings shortcut
    emoji shortcut
    keyboard resizing realtime
    secondary key labels
    toggle selection while moving cursor
    hide popup on key release, send first
    autoreplace toggle
IPA keyboard
    key info at top
    full selection of IPA symbols
Phonetics
    English IPA only
    double click keys for selection
Simple
    Settings shortcut in popup menu
    Languages shortcut
CustomKey
    send key on longpress without popup
    number layout access
    caps lock image
    keyboard customization settings panel
    key appearance
    voice shortcut 
ai.type
    shortcuts
    draggable keyboard resize
    undo and redo
    Clipboard access
    Settings top bar panels 
    double space for period
Touchpal
    Prediction toggle
    Edit screen 
Swiftkey
    Google search
    Settings panel
Engineering
    symbol spread -->