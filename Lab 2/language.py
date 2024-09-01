class Clause:
    __slots__ = "predicates", "variables", "constants", "functions"

    def __init__(self, predicated=[], variable=[], constant=[], function=[], clause=None):
        # Initialize the Clause object with lists to store predicates, variables, constants, and functions
        self.predicates = predicated
        self.variables = variable
        self.constants = constant
        self.functions = function

        if clause:
            # Split the clause into individual predicates
            predicates = clause.split()
            for predicate in predicates:
                # Extract the predicate name
                index = len(predicate)
                if "(" in predicate:
                    index = predicate.index("(")
                pred_name = predicate[:index]

                # Extract the terms inside the parentheses
                terms = predicate[index + 1: -1].split(",")

                # Add the predicate to the list of predicates
                self.predicates.append(pred_name)

                # Iterate over the terms to classify them as variables, constants, or functions
                for term in terms:
                    if term.startswith("x"):
                        self.variables.append(term)
                    elif term.startswith("SKF"):
                        self.functions.append(term)
                    else:
                        self.constants.append(term)

    def __str__(self):
        # Return a string representation of the Clause object
        return "[ " + str(self.predicates) + " ], " + "[ " + str(self.variables) + " ], " + "[ " + str(
            self.constants) + " ], " + "[ " + str(self.functions) + " ]"


def parse_data(filename):
    # Function to parse data from a file and return lists of predicates, variables, constants, functions, and clauses
    predicates = []
    variables = []
    constants = []
    functions = []
    clauses = []
    with open(filename, 'r') as file:
        for line in file:
            line = line.strip()
            if not line:
                continue
            if line.startswith("Predicates:"):
                predicates.extend(line.split(":")[1].strip().split())
            elif line.startswith("Variables:"):
                variables.extend(line.split(":")[1].strip().split())
            elif line.startswith("Constants:"):
                constants.extend(line.split(":")[1].strip().split())
            elif line.startswith("Functions:"):
                functions.extend(line.split(":")[1].strip().split())
            elif line.startswith("Clauses:"):
                pass
            else:
                # Create a Clause object for each line representing a clause
                clauses.append(Clause(predicated=[], variable=list(), constant=list(), function=list(),
                                      clause=line.strip()))

    return predicates, variables, constants, functions, clauses
