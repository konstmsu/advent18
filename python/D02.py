import os
import itertools
import timeit


def get_input():
    with open(os.path.join(os.path.dirname(__file__), "../_input/d02.txt")) as f:
        return f.read().splitlines()

def input2(lines):
    """
    >>> input2(get_input())
    'mxhwoglxgeauywfkztndcvjqr'
    """
    for i in range(len(lines[0])):
        changed = [l[:i] + l[i+1:] for l in lines]
        match = [k for k, g in itertools.groupby(sorted(changed)) if len(list(g)) > 1]
        if match:
            return match[0]


def input2a(lines2):
    """
    >>> input2a(get_input())
    'mxhwoglxgeauywfkztndcvjqr'
    """
    max_offset = len(lines2[0])
    def pro(ll, offset, skipped):
        if skipped and offset == max_offset:
            return ll[0][:skipped] + ll[0][skipped+1:]

        ch = lambda l: l[offset]
        gg = [list(g) for k, g in itertools.groupby(sorted(ll, key=ch), key=ch)]
        for g in gg:
            if len(g) > 1:
                v = pro(g, offset + 1, skipped)
                if v:
                    return v

        if skipped is None:
            return pro(ll, offset + 1, offset)

    return pro(lines2, 0, None)


def performance():
    """
    >>> performance()
    'OK'
    """
    assert 1.8 > timeit.timeit('D02.input2a(i)', setup='import D02; i=D02.get_input()', number=100)
    assert 0.8 > timeit.timeit('D02.input2(i)', setup='import D02; i=D02.get_input()', number=100)
    return "OK"

if __name__ == "__main__":
    import doctest
    doctest.testmod()