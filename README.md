Bugs
- when using bold±italic modes, spaces and other non word characters should be unchanged
- sometimes when using the case-changing feature, it works, but afterwards the selection jumps to somewhere earlier in the text
- if there are too many entries in a key's popup, it doesn't render properly
- the template had gesture typing, get that back
- the template had autocorrect, get that back
- sometimes when cutting/copying/pasting large chunks of text, those keys will stop working until the keyboard is disabled and reenabled
- the contents of the custom keys aren't printed on the keys until something is pressed

Changes
- ∅ key and label don't match up
- label size issue for high surrogates
- layouts as array, enable and reorder from settings
- popup should close when tapping outside of it

Features
+ add setting that allows specification of longpress delay
+ add feature that toggles whether or not the first member of the popup is sent upon release
+ add key for voice input
+ add key to access settings page
+ add key for access to keyboard management page in settings
+ add key that toggles selection extension when moving the cursor
+ add keys for pageup, pagedown, home, end
+ add slots in the settings page for customizing the letters appear on long press
+ add an emoji keyboard
+ add a keyboard with frequently used Chinese characters
+ add a keyboard for IPA characters
+ add an option that adds the opposite of the current shifted state to the start of each key's popup
+ move cursor and select text by dragging the space key
+ swipe left and right to change layouts
+ swipe down to close keyboard
+ button to access recent clipboard entries
+ double space period
+ auto insertion of apostrophes for specific contractions
+ auto capitalization of "I" if /^i / or / i /
