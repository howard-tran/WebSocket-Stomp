package service

import (
	"GoBackend/utility"
	"errors"
	"fmt"
	"io/ioutil"
	"net/http"
	"strings"
)

var config utility.TelAuthServerStruct

func SendKeycode(tel string, keycode string) error {
	if config == (utility.TelAuthServerStruct{}) {
		LoadConfig()
	}
	url := config.URL_HOST
	method := "POST"

	query := fmt.Sprintf("Body=%s&From=%s&To=%s", "Key confirm tel: "+keycode, config.TEL_HOST, tel)
	payload := strings.NewReader(query)

	client := &http.Client{}
	req, err := http.NewRequest(method, url, payload)

	if err != nil {
		fmt.Printf("%s\n", err)
		return err
	}
	req.Header.Add("Authorization", fmt.Sprintf("Basic %s", config.AUTH_KEY))
	req.Header.Add("Content-Type", "application/x-www-form-urlencoded")

	res, err := client.Do(req)
	if err != nil {
		fmt.Printf("%s\n", err)
		return err
	}
	defer res.Body.Close()

	if res.StatusCode == 201 {
		return nil
	}
	body, err := ioutil.ReadAll(res.Body)
	if err != nil {
		fmt.Printf("%s\n", err)
		return err
	}
	fmt.Printf("%s\n", string(body))
	return errors.New(string(body))
}

func LoadConfig() {
	config = utility.GetConfigServerbyKey(utility.TelAuthServer).(utility.TelAuthServerStruct)
}
