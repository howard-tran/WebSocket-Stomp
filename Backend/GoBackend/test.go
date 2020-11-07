package main

// func main() {

// 	url := "https://api.twilio.com/2010-04-01/Accounts/AC8ccbba8fc9b9b05ead8ac831f6ed5945/Messages.json"
// 	method := "POST"

// 	payload := strings.NewReader("Body=chodat&From=+12053862330&To=+84963531747")

// 	client := &http.Client{}
// 	req, err := http.NewRequest(method, url, payload)

// 	if err != nil {
// 		fmt.Println(err)
// 		return
// 	}
// 	req.Header.Add("Authorization", "Basic QUM4Y2NiYmE4ZmM5YjliMDVlYWQ4YWM4MzFmNmVkNTk0NTpkYTQ5Njk0NjcxMThhZGMxM2Y3MTAyODM0M2RhZGY0Zg==")
// 	req.Header.Add("Content-Type", "application/x-www-form-urlencoded")

// 	res, err := client.Do(req)
// 	if err != nil {
// 		fmt.Println(err)
// 		return
// 	}
// 	defer res.Body.Close()

// 	body, err := ioutil.ReadAll(res.Body)
// 	if err != nil {
// 		fmt.Println(err)
// 		return
// 	}
// 	fmt.Println(string(body))
// }
