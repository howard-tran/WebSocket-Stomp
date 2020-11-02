package utility

import (
	"crypto/rand"
	"fmt"
	"os"

	"github.com/BurntSushi/toml"
)

const CONFIG_PATH = "config-server.toml"

const (
	Database      = 110
	Auth          = 120
	TelAuthServer = 130
)

type JwtClaimStruct struct {
	Secretkey string
	Issuer    string
}

type DatabaseStruct struct {
	Stringconnection string
	NAME_DATABASE    string
}

type TelAuthServerStruct struct {
	AUTH_KEY string
	TEL_HOST string
	URL_HOST string
}

type RootStruct struct {
	Database      DatabaseStruct
	Auth          JwtClaimStruct
	TelAuthServer TelAuthServerStruct
}

// GetConfigServerbyKey Get config infomation from file config-server.toml
// Result return is struc interface for every type config
func GetConfigServerbyKey(key int) interface{} {
	var rootFile RootStruct
	if _, e := toml.DecodeFile(CONFIG_PATH, &rootFile); e != nil {
		dir, _ := os.Getwd()
		fmt.Printf("[ERROR] Read config file failure: %s   %s\n", e.Error(), dir)
	}
	if key == Database {
		return rootFile.Database
	} else if key == Auth {
		return rootFile.Auth
	} else if key == TelAuthServer {
		return rootFile.TelAuthServer
	} else {
		return nil
	}
}

func GenerateKeycode() string {
	RandomCrypto, _ := rand.Prime(rand.Reader, 64)
	str := fmt.Sprint(RandomCrypto)
	return string(str[2:8])
}

// func createErrorMsg(_msg string, _detail string) entity.MsgAPIEntity {

// }
