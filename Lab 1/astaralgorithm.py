import heapq
from constants import CONSTANTS  # Importing predefined constants
import math

class GridNode:
    """
    Represents a node in the grid.
    """
    def __init__(self):
        self.parent_x = 0  # x-coordinate of the parent node
        self.parent_y = 0  # y-coordinate of the parent node
        self.total_cost = float('inf')  # Total cost of the node (g + h)
        self.cost_from_start = float('inf')  # Cost from the start node to this node (g)
        self.heuristic_cost = 0  # Heuristic cost from this node to the destination (h)


GRID_ROWS = 500  # Number of rows in the grid
GRID_COLS = 395  # Number of columns in the grid


def is_position_valid(x, y):
    """
    Checks if the given position (x, y) is within the grid boundaries.
    """
    return (x >= 0) and (x < GRID_ROWS) and (y >= 0) and (y < GRID_COLS)


def is_pixel_unblocked(coordinates):
    """
    Checks if the pixel at the given coordinates is unblocked.
    """
    if tuple(coordinates) in CONSTANTS:
        return CONSTANTS[tuple(coordinates)] != float('inf')  # Unblocked if not infinity
    return True  # Default unblocked if coordinates not found in constants


def is_destination_position(x, y, destination):
    """
    Checks if the given position (x, y) is the destination.
    """
    return x == destination[0] and y == destination[1]


def calculate_heuristic_value(x, y, destination):
    """
    Calculates the heuristic cost from the given position (x, y) to the destination.
    """
    return (((x - destination[0]) ** 2) + ((y - destination[1]) ** 2)) ** 0.5


def calculate_cost_from_start(coordinates, elevation):
    """
    Calculates the cost from the start node to the given node, considering elevation.
    """
    speed = CONSTANTS[tuple(coordinates)] if tuple(coordinates) in CONSTANTS else float('inf')
    cost = speed + math.fabs(elevation)
    return cost


def trace_path(node_details, destination):
    """
    Traces the path from the destination back to the start by following parent pointers.
    """
    path = []
    x = destination[0]
    y = destination[1]

    while not (node_details[x][y].parent_x == x and node_details[x][y].parent_y == y):
        path.append((x, y))
        temp_x = node_details[x][y].parent_x
        temp_y = node_details[x][y].parent_y
        x = temp_x
        y = temp_y

    path.append((x, y))
    path.reverse()
    return path


def a_star_search(grid, start, destination, elevation_array):
    """
    Performs A* search algorithm to find the optimal path from start to destination.
    """
    if not is_position_valid(start[0], start[1]) or not is_position_valid(destination[0], destination[1]):
        print("Start or destination position is invalid")
        return

    if not is_pixel_unblocked(grid[start[0]][start[1]]) or not is_pixel_unblocked(grid[destination[0]][destination[1]]):
        print("Start or destination position is blocked")
        return

    if is_destination_position(start[0], start[1], destination):
        print("Already at the destination")
        return

    closed_list = [[False for _ in range(GRID_COLS)] for _ in range(GRID_ROWS)]
    node_details = [[GridNode() for _ in range(GRID_COLS)] for _ in range(GRID_ROWS)]

    x = start[0]
    y = start[1]
    node_details[x][y].total_cost = 0
    node_details[x][y].cost_from_start = 0
    node_details[x][y].heuristic_cost = 0
    node_details[x][y].parent_x = x
    node_details[x][y].parent_y = y

    open_list = []
    heapq.heappush(open_list, (0.0, x, y))

    destination_found = False

    while len(open_list) > 0:
        p = heapq.heappop(open_list)

        x = p[1]
        y = p[2]
        closed_list[x][y] = True

        directions = [(0, 1), (0, -1), (1, 0), (-1, 0)]

        for direction in directions:
            new_x = x + direction[0]
            new_y = y + direction[1]

            if (is_position_valid(new_x, new_y) and is_pixel_unblocked(grid[new_x][new_y]) and
                    not closed_list[new_x][new_y]):
                if is_destination_position(new_x, new_y, destination):
                    node_details[new_x][new_y].parent_x = x
                    node_details[new_x][new_y].parent_y = y
                    path = trace_path(node_details, destination)
                    return path
                else:
                    cost_from_start = calculate_cost_from_start(grid[new_x][new_y], elevation_array[x][y] - elevation_array[new_x][new_y])
                    heuristic_cost = calculate_heuristic_value(new_x, new_y, destination)
                    total_cost = cost_from_start + heuristic_cost

                    if node_details[new_x][new_y].total_cost == float('inf') or node_details[new_x][new_y].total_cost > total_cost:
                        heapq.heappush(open_list, (total_cost, new_x, new_y))
                        node_details[new_x][new_y].total_cost = total_cost
                        node_details[new_x][new_y].cost_from_start = cost_from_start
                        node_details[new_x][new_y].heuristic_cost = heuristic_cost
                        node_details[new_x][new_y].parent_x = x
                        node_details[new_x][new_y].parent_y = y

    if not destination_found:
        print("Failed to find the destination position")


def simulate_a_star_algorithm(terrain_array, start_position, end_position, elevation_array):
    """
    Simulates A* algorithm to find the optimal path from start to end position.
    """
    return a_star_search(terrain_array, start_position, end_position, elevation_array)
