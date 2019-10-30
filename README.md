+ fix morse
+ don't cancel popup if it has a long press action
+ more pinyin, combine with autocorrect
+ popup for autocorrect
+ more unicode info
+ settings use categories for hide/show
+ generate keyboard programmatically
+ Normalizer.normalize(x, Normalizer.Form.NFKC)
+ macro customization
+ URI specific layout
+ stay on current layout
+ colorize categories
+ layout toggles on category
+ Draggable resize right on the front
+ drag control for keyboard height
+ adjust key size
+ resurrect key height change
+ and suggestion bar
+ function to determine cursor position for line
+ delete all space up to line end (while prev char is space)
+ runtime modification/generation of layouts
+ modification of xml attributes at runtime
+ settings for numbers, colors, popupchars
+ draggable for layout management
+ include tags FFS
+ use of layouts on keyboard
+ option for numbers on nav instead of repeatable, or combine with numeric
+ change space insertion to mod on full line length 
+ voice to text
+ emoji 
+ scrollable/swipeable areas
+ Swipe on spacebar + left and right swipe on spacebar
+ selection doesn't work with 4space
+ Key codes and labels customizable
+ make popup keys repeatable
+ Menu
+ Prediction bar
+ Swiping
+ Tabs on symbols page 
+ Autoreplace toggleable from keyboard predictions 
+ Adding entries on the fly
+ release on popup for choice
+ Settings opens up a drawer
+ 2nd bar changeable
+ Selection pad
+ slideable bar
+ shortcuts with sigil
+ bug when autoshift
+ convenience method for setting checks
+ Column from table
+ Identify Hash
+ Prefix/Suffix lines, add line numbers
+ Format Numbers
+ press and hold space for layout page
+ instead of getting rid of last clipboard entry, send that one indefinitely
+ disable context menu in edittexts
+ color input + toColor & fromColor
+ if no selection, un/indent saves cursor spot, highlights all, then puts it back ±4
+ page containing clipboard history
+ choice of FIFO or FILO for clipboard history
+ single line comments
+ nonliteral tab
+ you can use canvas to add a black layover to darken background
+ move layouts to map
+ full braille
+ com popup with other choices
+ manually reimplement cut/copy/paste
+ press and hold del/backspace for up to space
+ selection moves while typing
+ null color array
+ math ⇄ numeric
+ hash out links
+ use different fonts
+ change enter icon depending on input type
+ wordFore only works for next word
+ convert between fonts
+ make layouts for fonts
+ text area for temporary stuff
+ 2d layout navigation
+ popup for arbitrary find/replace screen
+ autocorrect
+ autocomplete
+ swiping
+ prediction
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
+ label size issue for high surrogates
+ autocorrect with google
+ animations
+ unicode input from exhaustive table
+ show clipboard contents after cut/copy
+ scrolling
+ add and remove keys
+ hide and show specific keys, especially in the top or bottom
+ row with toggles from settings page
+ font color, background, border from settings
+ reorder layouts from settings
+ add setting that allows specification of longpress delay
+ add toggle for whether or not the first member of the popup is sent upon release
+ add slots in the settings page for customizing the letters that appear on long press
+ settings screen for autoreplace configuration
+ script for autoreinstall
+ password buttons
+ calculation parsing
+ delete autoinsertions by pressing once
+ autocomplete capitalization
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