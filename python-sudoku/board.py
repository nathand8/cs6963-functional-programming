class Board():
    """
    Sudoku board where each sub-square is m x n (width x height)
    """
    block_width = 0
    block_height = 0
    board_size = 0
    board = [[]]

    def __init__(self, m, n):
        self.block_width = m
        self.block_height = n
        self.board_size = self.block_width * self.block_height
    
    def generate(self):
        """
        Generate a 'random' board that is board_size x board_size
        """

        self.board = []
        for i, _ in enumerate(range(self.board_size)):
            column = []
            for y in range(self.board_size):
                column.append((y + i + 1) % self.board_size)
            self.board.append(column)
    
    def __str__(self):
        board_str = ""
        for row in self.board:
            row_str = ""
            for val in row:
                row_str += str(val).rjust(4)
            board_str += row_str
            board_str += "\n"
        return board_str
            
