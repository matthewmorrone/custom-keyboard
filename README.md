+ move themes to array or map
+ move layouts to map
+ morse keyboard with separate keys
+ com popup with other choices
+ manually reimplement cut/copy/paste
+ move lines up and down
+ move/swap keys up and down
+ press and hold del/backspace for up to space
+ reverse and sort lines
+ selection moves while typing
+ numbers on navpad
+ null color array
+ home toggles between start and first nonspace
+ math ⇄ numeric
+ hash out links
+ fix keyboard height change
+ varied layouts for layout list
+ color input
+ entities
+ use different fonts
+ adjust key size
+ change enter icon depending on input type
+ wordFore only works for next word
+ api demos
+ basic layout ordering
+ delete all space up to line end
+ add hold check for onpress
+ make layouts for fonts
+ function to determine cursor position on line
+ text area for temporary stuff
+ 2d layout navigation
+ enforce layout toggle dependecies
+ popup for arbitrary find/replace screen
+ autocorrect
+ autocomplete
+ swiping
+ prediction
+ add key for voice input
+ add key to access settings page
+ add key for access to keyboard management page in settings
+ button to access recent clipboard entries
+ personal dictionary settings link
+ press and hold custom keys to change them
+ separate out settings page for layouts and custom keys
+ select with manual cursor movement
+ move cursor and select text by dragging the space key
+ layout of popup
+ close by clicking outside popup
+ popup contents should also be keys
+ if there are too many entries in a keys popup, it doesn't render properly
+ the contents of keys whose contents are modified during runtime aren't printed on the keys until something is pressed
+ label size issue for high surrogates
+ display unicode information about key, or code
+ autocorrect with google
+ animations
+ unicode input from exhaustive table
+ show clipboard contents after cut/copy
+ scrolling
+ add and remove keys
+ open main activity
+ open other apps
+ row with toggles from settings page
+ font color, background, border from settings
+ reorder layouts from settings
+ drag control for keyboard height
+ add setting that allows specification of longpress delay
+ add toggle for whether or not the first member of the popup is sent upon release
+ add an option that adds the opposite of the current shifted state to the start of each key's popup
+ add slots in the settings page for customizing the letters that appear on long press
+ hide and show specific keys, especially in the top or bottom
+ settings screen for autoreplace configuration
+ debug mode: tons of toasts, selection indices
+ script for autoreinstall
+ password buttons
+ entity autoreplace
+ string escaping
+ calculation parsing
+ delete autoinsertions by pressing once
+ autocomplete slowing keyboard input
+ bug with unshifting from shifted layout
+ autocomplete capitalization
+ left and right swipe on spacebar
+ open activity from keyboard
+ append clipboard contents to file on cut or copy
+ lineNext and lineLast probably don't work
+ language detection



=== Settings ===
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