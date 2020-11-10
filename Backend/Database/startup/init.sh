#!/bin/sh
tmux new-session -d -s "session" \
  && tmux send -t "session.0" mongod ENTER \
  && sleep 1 && mongo bet_store mongo-init.js \
  && tmux attach;