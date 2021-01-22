import sys
from board import Board

if __name__ == "__main__":
    dimension_arg = sys.argv[1]
    dest_file_arg = sys.argv[2]

    b = Board(2, 3)
    b.generate()
    print(b)