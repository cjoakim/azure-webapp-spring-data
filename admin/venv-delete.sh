#!/bin/bash

# Delete the python virtual environment files in this directory.
# Chris Joakim, Microsoft, 2020/01/07

echo 'deleting previous venv...'
rm -rf bin/
rm -rf include/
rm -rf lib/
rm -rf man/

echo 'done'
