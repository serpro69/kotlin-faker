#!/usr/bin/env python3
"""Load the adjacent engine without adding its directory to Python's search path.

Importing permits Python's normal, interpreter-specific bytecode cache to avoid
recompiling the engine on every hook. Rules, configuration and decisions are
still evaluated afresh. A missing or unwritable cache falls back to source;
unexpected loader failures propagate to guard.sh's fail-closed boundary.
"""

import importlib.util
import os
import sys


def main():
    path = os.path.join(os.path.dirname(os.path.abspath(__file__)), "guard.py")
    spec = importlib.util.spec_from_file_location("_kk_guard_engine", path)
    if spec is None or spec.loader is None:
        raise RuntimeError("cannot load the guard engine")
    engine = importlib.util.module_from_spec(spec)
    sys.modules[spec.name] = engine
    spec.loader.exec_module(engine)
    return engine.main()


if __name__ == "__main__":
    sys.exit(main())
