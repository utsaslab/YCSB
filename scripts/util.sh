#!/bin/bash
# util functions for scripts

bold()          { ansi 1 "$@"; }
italic()        { ansi 3 "$@"; }
underline()     { ansi 4 "$@"; }
strikethrough() { ansi 9 "$@"; }
red()           { ansi 31 "$@"; }
ansi()          { echo -e "\e[${1}m${*:2}\e[0m"; }

error() {
  red "ERROR: ${@}" 1>&2
  exit 1
}

warn() {
  echo "WARNING: ${@}" 1>&2
}
