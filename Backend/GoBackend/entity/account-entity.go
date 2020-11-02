package entity

type AccountEntity struct {
	Username string `json:"username"`
	Password string `json:"password"`
	Tel      string `json:"tel"`
	Keycode  string `json:"key"`
}
