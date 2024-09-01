import numpy as np


class TextClass:
    """
    Represents a class for processing text data, specifically check point and elevation data.
    """

    # Define slots to restrict attribute creation dynamically
    __slots__ = "elevation_array", "elevation_file_path", "check_point_array", "check_point_file_path"

    def __init__(self, check_point_file_path, elevation_file_path):
        """
        Initializes the TextClass object with file paths for check point and elevation data.

        Args:
            check_point_file_path (str): File path for check point data.
            elevation_file_path (str): File path for elevation data.
        """
        self.check_point_array = []  # Array to store check point data
        self.check_point_file_path = check_point_file_path  # File path for check point data
        self.elevation_array = []  # Array to store elevation data
        self.elevation_file_path = elevation_file_path  # File path for elevation data

    def transform_input_data(self):
        """
        Transforms the check point data into a usable format.

        Reads the check point data from the file, converts it into a list of coordinates,
        and adjusts the coordinate format for further processing.

        Returns:
            list: Transformed check point data in the format [x, y].
        """
        with open(self.check_point_file_path, "r+") as path:
            file_content = path.readlines()  # Read all lines from the file
        for line in file_content:
            self.check_point_array.append(line.strip().split(" "))  # Split each line by space and append to the array
        for i in range(len(self.check_point_array)):
            # Adjust coordinate format to [x, y]
            self.check_point_array[i][0], self.check_point_array[i][1] = int(self.check_point_array[i][1]), int(
                self.check_point_array[i][0])
        return self.check_point_array

    def transform_elevation_data(self):
        """
        Transforms the elevation data into a usable format.

        Reads the elevation data from the file using NumPy's loadtxt function.

        Returns:
            numpy.ndarray: Transformed elevation data.
        """
        self.elevation_array = np.loadtxt(self.elevation_file_path)  # Load elevation data using NumPy
        return self.elevation_array
