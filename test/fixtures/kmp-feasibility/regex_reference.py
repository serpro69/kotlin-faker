"""Print independently expanded regex-choice vectors, without importing a regex parser.

Uses the arbitrary-precision Task 25 oracle. The AKIA-prefixed values are artificial
test strings, not credentials. Output is escaped for copying into Kotlin literals.
"""

import json

from random_reference import SEEDS, below, words


def regex_outputs(seed):
    stream = words(seed)
    # ([A-C]{2}|[0-2]{1,3})-[x-z]?
    if below(stream, 2) == 0:
        first = "".join("ABC"[below(stream, 3)] for _ in range(2))
    else:
        first = "".join("012"[below(stream, 3)] for _ in range(1 + below(stream, 3)))
    first += "-"
    if below(stream, 2):
        first += "xyz"[below(stream, 3)]
    # a{2,4}[^0-9]
    second = "a" * (2 + below(stream, 3))
    alphabet = "".join(chr(code) for code in range(32, 127) if not 48 <= code <= 57)
    second += alphabet[below(stream, len(alphabet))]
    # ^AKIA\S{4}$, x.*y, then a literal that consumes no words.
    third = "AKIA" + "".join(chr(33 + below(stream, 94)) for _ in range(4))
    fourth = "x" + "".join(chr(32 + below(stream, 95)) for _ in range(below(stream, 17))) + "y"
    return "\n".join([first, second, third, fourth, "東京😀", f"{next(stream):016x}"])


def main():
    for seed in SEEDS:
        print(seed, json.dumps(regex_outputs(seed), ensure_ascii=False).replace("$", "\\$"))


if __name__ == "__main__":
    main()
