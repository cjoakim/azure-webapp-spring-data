#!/bin/bash

# Execute 'docker-compose xxx' with the necessary exported environment variables.
# The value of xxx is provided by the first command-line arg, or $1.
# Usage:
# $ ./compose.sh up
# $ ./compose.sh ps
# $ ./compose.sh down
#
# Chris Joakim, Microsoft, 2020/01/07

source app-env.sh display ; docker-compose $1
