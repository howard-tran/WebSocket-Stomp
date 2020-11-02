package main

import "GoBackend/server"

func main() {
	app := server.NewServer()

	app.Run()
}
