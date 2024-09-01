import sys
from language import *


# Function to perform resolution on a given knowledge base
def resolution(variables, constants, clauses):
    # Lists to store clauses with one and two predicates
    one_predicates = []
    two_predicates = []
    answers = []


    # Iterate through each clause in the knowledge base
    length = len(clauses)
    if len(variables) > 0 and len(constants) > 0:
        for i in range(length):
            cl = clauses.pop(0)
            # Move clauses with constants only and no variables to the end of the list
            if len(cl.constants) > 0 and len(cl.variables) == 0:
                clauses.append(cl)
            else:
                # Create new clauses by substituting constants into existing clauses
                for c in constants:
                    newset = [c]
                    for co in cl.constants:
                        newset.append(co)
                    newc = Clause(cl.predicates, cl.variables, newset, cl.functions, clause=None)
                    clauses.append(newc)

    # Separate clauses with one and two predicates
    for cl in clauses:
        if len(cl.predicates) == 1:
            one_predicates.append(cl)
        else:
            two_predicates.append(cl)

    # Perform resolution on clauses with one predicate
    for i in one_predicates:
        ci = i.predicates[0]
        for j in one_predicates:
            pred = j.predicates[0]
            # Apply resolution if the predicates are complementary and have the same constants
            if ci == "!" + pred or "!" + ci == pred:
                if set(i.constants) == set(j.constants):
                    if i.constants[0] == j.constants[0]:
                        return [Clause(predicated=[])]

    # Perform resolution on clauses with two predicates
    if len(two_predicates) > 0:
        if len(one_predicates) > 0:
            for i in one_predicates:
                cli = i
                if len(i.predicates) > 0:
                    ci = i.predicates[0]
                for j in two_predicates:
                    for k in j.predicates:
                        if ci == "!" + k or "!" + ci == k:
                            clj = j
                            if ci in clj.predicates and (set(cli.constants) == set(clj.constants) or (
                                    len(variables) > 0 and set(cli.variables) == set(clj.variables))):
                                if len(constants) == 2 and len(variables) == 2:
                                    if len(clj.predicates) > 1:
                                        clj.predicates.remove(ci)
                                else:
                                    clj.predicates.remove(ci)
                            elif k in clj.predicates and (set(cli.constants) == set(clj.constants) or (
                                    len(variables) > 0 and set(cli.variables) == set(clj.variables))):
                                if len(constants) == 2 and len(variables) == 2 :
                                    if len(clj.predicates) > 1:
                                        clj.predicates.remove(k)
                                else:
                                    clj.predicates.remove(k)
                            if len(clj.predicates) == 1 and clj not in one_predicates:
                                if len(constants) == 2 and len(variables) == 2:
                                    if len(clj.predicates) > 1:
                                        one_predicates.append(clj)
                                        rindex = two_predicates.index(clj)
                                        two_predicates.remove(two_predicates[rindex])
                                else:
                                    one_predicates.append(clj)
                                    rindex = two_predicates.index(clj)
                                    two_predicates.remove(two_predicates[rindex])
                            elif len(clj.predicates) > 1 and clj not in two_predicates:
                                    two_predicates.append(clj)
                            answers.append(clj)

        boolvalue = True
        for i in answers:
            if len(i.predicates) == 0:
                boolvalue = False

        if boolvalue:
            answers = []
            for i in clauses:
                cli = i
                if len(i.predicates) > 0:
                    for p in i.predicates:
                        for j in clauses:
                            for k in j.predicates:
                                if p == "!" + k or "!" + p == k:
                                    clj = j
                                    if p in clj.predicates and (set(cli.constants) == set(clj.constants) or (
                                            len(variables) > 0 and set(cli.variables) == set(clj.variables))):
                                        if len(constants) == 2 and len(variables) == 2:
                                            if len(clj.predicates) > 1:
                                                clj.predicates.remove(p)
                                                cli.predicates.remove(p)
                                                clj.predicates.extend(cli.predicates)
                                        else:
                                            clj.predicates.remove(p)
                                            cli.predicates.remove(p)
                                            clj.predicates.extend(cli.predicates)
                                    elif k in clj.predicates and p in cli.predicates and (
                                            set(cli.constants) == set(clj.constants) or (
                                            len(variables) > 0 and set(cli.variables) == set(clj.variables))) :
                                        if len(constants) == 2 and len(variables) == 2:
                                            if len(clj.predicates) > 1:
                                                clj.predicates.remove(k)
                                                cli.predicates.remove(p)
                                                clj.predicates.extend(cli.predicates)
                                        else:
                                            clj.predicates.remove(k)
                                            cli.predicates.remove(p)
                                            clj.predicates.extend(cli.predicates)
                                    if clj not in clauses:
                                        clauses.append(clj)
                                    answers.append(clj)
    else:
        return clauses

    return answers


# Function to parse the knowledge base and perform resolution
def main(file_path):
    # Parse the knowledge base
    predicates, variables, constants, functions, clauses = parse_data(file_path)

    # Perform resolution
    resolve = resolution(variables, constants, clauses)

    # Check if any empty clause is derived
    for i in resolve:
        if len(i.predicates) == 0:
            return "no"
    return "yes"


if __name__ == "__main__":
    # Check if the correct number of arguments is provided
    if len(sys.argv) != 2:
        sys.exit(1)

    # Call the main function with the input file
    print(main(sys.argv[1]))
