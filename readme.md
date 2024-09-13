# nicklausw's babashka scripts

To install the command line tools, run [install.bb](./install.bb) and make sure `$HOME/bin` is in your PATH.

Note: to pass regexes on the command line, precede string quotes with `reg:`.

## scripts

### bulk-rename
```bash
bulk-rename path = ".", before, after
bulk-rename reg:"bad tv show name episode (.*)\.mp4" "Proper TV Show Name S01E\$1.mp4"
```
