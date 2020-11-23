package main

import "GoBackend/server"

func main() {
	app := server.NewServer()
	if app == nil {
		return
	}
	app.Run()
}
