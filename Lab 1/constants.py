"""
Speed Penalties
"""

CONSTANTS = {
    (248, 148, 18): 0.5,    # Open land
    (255, 192, 0): 0.2,     # Rough meadow
    (255, 255, 255): 0.4,   # Easy movement forest
    (2, 208, 60): 0.125,    # Slow run forest
    (2, 136, 40): 0.111,    # Walk forest
    (5, 73, 24): 1000,         # Impassible Vegetation
    (0, 0, 255): 7,       # Lake/ Swamp/ Marsh
    (71, 51, 3): 0.1,       # Paved Road
    (0, 0, 0): 0.8,         # Footpath
    (205, 0, 101): float('inf')  # Out of bounds
}

