package StorageHandle

import (
	"fmt"
	"io"
	"mime/multipart"
	"os"
	"regexp"
	"time"
)

func CreateFileEmptytoFolder(namefile string, path string) (*os.File, error) {

	// file := fmt.Sprintf("%s",, namefile)
	//fmt.Println(path + "|" + namefile)
	return os.Create(path + "/" + namefile)
}

func CreateFileAutoFolder(filename string, file *multipart.FileHeader) (string, error) {
	src, err := file.Open()
	timenowstr := time.Now().Format("02012006")

	folderpath := "database/" + timenowstr
	if _, err := os.Stat(folderpath); os.IsNotExist(err) {
		if err := os.MkdirAll(folderpath, os.ModePerm); err != nil {
			fmt.Printf("[CreateFile] Create dir failed: %s\n", err.Error())
			return "", err
		}
	}

	//Get ext file
	re := regexp.MustCompile("(.+?)(\\.[^.]*$|$)")
	match := re.FindStringSubmatch(filename)
	ext := match[2]

	//Genneration hash name
	filenameHash := GenerationGUID() + timenowstr + ext

	out, err := os.Create(fmt.Sprintf("%s/%s", folderpath, filenameHash))

	if err != nil {
		return "", err
	}
	defer out.Close()

	_, err = io.Copy(out, src)
	return fmt.Sprintf("%s", filenameHash), err
}

func FindFileinStorage(filename string) string {

	re := regexp.MustCompile("(.+?)(\\.[^.]*$|$)")
	match := re.FindStringSubmatch(filename)
	name := match[1]

	a := []rune(name)
	infoFile := string(a[32:40])
	path := "database/" + infoFile + "/" + filename
	if _, err := os.Stat(path); os.IsNotExist(err) {
		return ""
	}
	return path
}
