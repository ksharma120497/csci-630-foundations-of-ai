"""
    Foundations of Algorithm: Lab 1

    @Author Kapil Sharma ks4643

    This program gives us an optimal path for an orienteering event based on the
    terrain type and the elevation associated with it

    This program uses A* search algorithm to find the optimal and least costly path
    to reach the destination

    It uses Euclidian distance to calculate the cost
"""

import sys
import imageprocessing
from astaralgorithm import simulate_a_star_algorithm
from datatransformation import (TextClass)
import math


class Input:
    """
    Represents input parameters for the program.
    """

    # Define slots to restrict attribute creation dynamically
    __slots__ = "terrain_image", "elevation_file", "path_file", "output_image_filename"

    def __init__(self, terrain_image, elevation_file, path_file, output_image_filename):
        """
        Initializes Input object with input file paths and output image filename.

        Args:
            terrain_image (str): File path for the terrain image.
            elevation_file (str): File path for the elevation data.
            path_file (str): File path for the path data.
            output_image_filename (str): Output filename for the processed image.
        """
        self.terrain_image = terrain_image
        self.elevation_file = elevation_file
        self.path_file = path_file
        self.output_image_filename = output_image_filename


def calculate_total_distance(terrain_object, elevation_array):
    """
    Calculates the total distance of the path based on terrain object and elevation array.

    Args:
        terrain_object (ImageClass): Object representing the terrain image.
        elevation_array (numpy.ndarray): Array representing elevation data.
    """
    total_distance = 0
    for i in range(len(terrain_object.output_array)):
        for j in range(len(terrain_object.output_array[i]) - 1):
            x = (math.fabs(terrain_object.output_array[i][j][0] - terrain_object.output_array[i][j + 1][0]) * 7.55)
            y = (math.fabs(terrain_object.output_array[i][j][1] - terrain_object.output_array[i][j + 1][1]) * 10.29)
            z = math.fabs(elevation_array[terrain_object.output_array[i][j][0]][terrain_object.output_array[i][j][1]] -
                          elevation_array[terrain_object.output_array[i][j + 1][0]][
                              terrain_object.output_array[i][j + 1][1]])
            total_distance += math.sqrt(((x ** 2) + (y ** 2) + (z ** 2)))
    print(total_distance)


def main():
    # Parse command line arguments to create Input object
    input_object = Input(sys.argv[1], sys.argv[2], sys.argv[3], sys.argv[4])
    # Initialize terrain object with terrain image and output filename
    terrain_object = imageprocessing.ImageClass(input_object.terrain_image, input_object.output_image_filename)
    # Transform terrain image to array
    terrain_array = terrain_object.transform_image_to_array()
    # Initialize data object with path file and elevation file
    data_object = TextClass(input_object.path_file, input_object.elevation_file)
    # Transform input data
    check_point_array = data_object.transform_input_data()
    elevation_array = data_object.transform_elevation_data()
    output_terrain_array = []
    # Iterate over each pair of checkpoints and find path between them
    for i in range(len(check_point_array)):
        if i < len(check_point_array) - 1:
            output_terrain_array.append(
                simulate_a_star_algorithm(terrain_array, check_point_array[i], check_point_array[i + 1],
                                          elevation_array))
    # Store output terrain array in terrain object
    terrain_object.output_array = output_terrain_array
    # Draw lines on output image based on terrain object
    terrain_object.draw_line_on_output_image()
    # Calculate total distance of the path
    calculate_total_distance(terrain_object, elevation_array)
    # Save the output image
    terrain_object.terrain_image.save(input_object.output_image_filename)


if __name__ == "__main__":
    main()
