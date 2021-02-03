# sudoku

A Clojure library designed to generate and solve sudoku boards

## Usage

#### Sample Program Run

```
$ lein run

What block-width would you like?
3
What block-height would you like?
3
Would you like to use random generation? (Y/y/Yes/Anything or leave empty if not)
y
How many values would you like to remove from the solved board?
10
Here is the generated, completed board:

 6 5 3 8 2 1 4 9 7 
 1 9 7 4 3 6 8 2 5 
 2 4 8 9 5 7 6 3 1 
 4 3 1 7 6 9 2 5 8 
 8 6 5 1 4 2 3 7 9 
 7 2 9 3 8 5 1 4 6 
 5 1 6 2 7 4 9 8 3 
 9 8 4 5 1 3 7 6 2 
 3 7 2 6 9 8 5 1 4

Here is the puzzle board:

 6 5 3 _ 2 1 _ 9 _ 
 1 9 7 4 3 6 8 2 _ 
 2 4 _ 9 5 7 _ 3 _ 
 _ 3 1 7 6 9 2 5 8 
 8 6 5 1 4 2 3 7 9 
 7 2 9 3 8 5 1 4 6 
 5 1 6 2 7 4 9 _ 3 
 9 8 4 5 1 3 7 6 2 
 3 _ 2 6 9 8 5 1 4

Here is the solved board:

 6 5 3 8 2 1 4 9 7 
 1 9 7 4 3 6 8 2 5 
 2 4 8 9 5 7 6 3 1 
 4 3 1 7 6 9 2 5 8 
 8 6 5 1 4 2 3 7 9 
 7 2 9 3 8 5 1 4 6 
 5 1 6 2 7 4 9 8 3 
 9 8 4 5 1 3 7 6 2 
 3 7 2 6 9 8 5 1 4
```

## License

Copyright © 2021 Nathan Davis

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
