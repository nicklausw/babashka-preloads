# nicklausw's babashka preloads

All in [preloads.bb](./preloads.bb).

Set BABASHKA_PRELOADS environment variable to `(load-file "/path/to/preloads.bb")` for this to work.

## Functions
* `(bulk-rename path = ".", before, after)` - execute `str/replace` on all file names in a directory.