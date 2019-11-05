+ font color, background, border from settings
+ settings for numbers, colors, popupchars
+ reorder layouts from settings: draggable for layout management
+ don't cancel popup if it has a long press action
+ more pinyin, combine with autocorrect
+ selection doesn't work with 4space
+ function to determine cursor position
+ delete all space up to line end (while prev char is space)
+ press and hold del/backspace for up to space
+ runtime modification/generation of layouts
+ generate keyboard programmatically
+ show clipboard contents after cut/copy
+ page containing clipboard history
+ choice of fifo or filo for clipboard history
+ button to access recent clipboard entries
+ personal dictionary settings link
+ press and hold custom keys to change them
+ close by clicking outside popup
+ delete autoinsertions by pressing once
+ colorize categories
+ settings use categories for hide/show
+ layout checkboxes use category
+ normalizer.normalize(x, normalizer.form.nfkc)
+ macro customization
+ stay on current layout
+ change space insertion to mod on full line length 
+ prefix/suffix lines, add line numbers
+ transparent keyboard
+ selection moves while typing
+ select with manual cursor movement
+ move cursor and select text by dragging the space key
+ if no selection, un/indent saves cursor spot, highlights all, then puts it back ±4
+ draggable resize right on the front
+ drag control for keyboard height
+ adjust key size
+ key height change
+ suggestion bar
+ include tags ffs
+ emoji 
+ scrollable/swipeable areas
+ swipe on spacebar + left and right swipe on spacebar
+ key codes and labels customizable
+ make popup keys repeatable
+ swiping
+ tabs on symbols page 
+ autoreplace toggleable from keyboard predictions 
+ adding entries on the fly
+ release on popup for choice
+ settings opens up a drawer
+ 2nd bar changeable
+ selection pad
+ slideable bar
+ shortcuts with sigil
+ bug when autoshift
+ convenience method for setting checks
+ column from table
+ identify hash
+ format numbers
+ press and hold space for layout page
+ instead of getting rid of last clipboard entry, send that one indefinitely
+ disable context menu in edittexts
+ color input + tocolor & fromcolor
+ nonliteral tab
+ you can use canvas to add a black layover to darken background
+ manually reimplement cut/copy/paste
+ math ⇄ numeric
+ hash out links
+ change enter icon depending on input type
+ wordfore only works for next word
+ convert between fonts
+ edittext popup for temporary stuff
+ 2d layout navigation
+ popup for arbitrary find/replace screen
+ swiping
+ separate out settings page for layouts and custom keys
+ layout of popup
+ popup contents should also be keys
+ if there are too many entries in a keys popup, it doesn't render properly
+ label size issue for high surrogates
+ animations
+ unicode input from exhaustive table
+ scrolling
+ add and remove keys
+ hide and show specific keys, especially in the top or bottom
+ row with toggles from settings page
+ add setting that allows specification of longpress delay
+ add toggle for whether or not the first member of the popup is sent upon release
+ add slots in the settings page for customizing the letters that appear on long press
+ settings screen for autoreplace configuration
+ script for autoreinstall
+ password buttons
+ calculation parsing
+ autocomplete capitalization
+ append clipboard contents to file on cut or copy
+ linenext and linelast probably don't work
+ language detection
+ get location as string, coordinates or address
+ include contact names
+ learn new words


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