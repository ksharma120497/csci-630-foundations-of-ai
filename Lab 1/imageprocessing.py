from PIL import Image, ImageDraw


class ImageClass:
    """
    Represents a class for processing image data.
    """

    # Define slots to restrict attribute creation dynamically
    __slots__ = "terrain_image", "terrain_array", "output_image_file_name", "output_array"

    def __init__(self, terrain_image_location, output_image_file_name):
        """
        Initializes the ImageClass object with a terrain image file and an output image file name.

        Args:
            terrain_image_location (str): Location of the terrain image file.
            output_image_file_name (str): Name of the output image file to be created.
        """
        # Load the terrain image and convert it into a 2D array of RGB pixels
        self.output_image_file_name = output_image_file_name
        image = Image.open(terrain_image_location)
        grid = []
        for y in range(image.size[1]):
            row = []
            for x in range(image.size[0]):
                pixel = image.getpixel((x, y))
                rgb_pixel = pixel[:3]  # Extract RGB values from the pixel
                row.append(rgb_pixel)
            grid.append(row)
        self.terrain_image = image.copy()  # Copy the terrain image
        self.terrain_array = grid  # Store the 2D array of RGB pixels

    def transform_image_to_array(self):
        """
        Transforms the terrain image into a 2D array of RGB pixels.

        Returns:
            list: 2D array of RGB pixels.
        """
        return self.terrain_array

    def draw_line_on_output_image(self):
        """
        Draws a line on the output image based on the provided output array.

        The output array contains coordinates of the line segments to be drawn.
        """
        draw = ImageDraw.Draw(self.terrain_image)  # Initialize drawing on the terrain image
        reversed_image = []
        # Reverse the coordinates in the output array for drawing
        for x in self.output_array:
            row = []
            for y in x:
                row.append((y[1], y[0]))  # Swap x and y coordinates for drawing
            reversed_image.append(row)
        # Draw lines on the terrain image based on the reversed coordinates
        for point in reversed_image:
            draw.line(point, fill=(118, 63, 231), width=1)  # Draw a line segment with specified color and width
