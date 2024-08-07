# nicklausw's babashka preloads

Preloads are, of course, found in [preloads.bb](./preloads.bb).

For nice command line tools, run [install.bb](./install.bb) and make sure `~/bin` is in your PATH.

Set BABASHKA_PRELOADS environment variable to `(load-file "/path/to/preloads.bb")` for this to work.

## Functions
* `(bulk-rename path = ".", before, after)` - execute `str/replace` on all file names in a directory.

## Command line tools
Matches up with the functions, except for some slight syntactical difference for regexes: start them with `reg:` instead of `#`.

Example:
```bash
bulk-rename reg:"bad tv show name episode (.*)\.mp4" "Proper TV Show Name S01E$1.mp4"
```
